package com.example.electroretinography

import android.widget.Toast
import android.content.ContentResolver
import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.amazonaws.auth.BasicAWSCredentials
import com.amazonaws.mobileconnectors.s3.transferutility.TransferListener
import com.amazonaws.mobileconnectors.s3.transferutility.TransferState
import com.amazonaws.mobileconnectors.s3.transferutility.TransferUtility
import com.amazonaws.services.s3.AmazonS3Client
import com.example.electroretinography.ui.theme.ElectroRetinographyTheme
import java.io.File
import java.io.FileOutputStream

class MainActivity : ComponentActivity() {

    private lateinit var s3Client: AmazonS3Client

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Initialize AWS S3 Client with credentials
        val credentials = BasicAWSCredentials(BuildConfig.AWS_ACCESS_KEY, BuildConfig.AWS_SECRET_KEY)
        s3Client = AmazonS3Client(credentials)

        setContent {
            ElectroRetinographyTheme {
                // Create a NavController to manage navigation
                val navController = rememberNavController()

                // Set up NavHost with navigation routes
                NavHost(navController = navController, startDestination = "home") {
                    composable("home") {
                        HomeScreen(
                            onNavigateToSignalTest = {
                                navController.navigate("signalTest")
                            }
                        )
                    }
                    composable("signalTest") {
                        SignalTestScreen(onFileSelected = { uri ->
                            handleFileAndUpload(uri)
                        })
                    }
                }
            }
        }
    }

    private fun handleFileAndUpload(uri: Uri) {
        // Convert Uri to File
        val file = createFileFromUri(this, uri)

        // Upload file to S3 if successful
        file?.let {
            uploadFileToS3(it)
        }
    }

    private fun createFileFromUri(context: Context, uri: Uri): File? {
        val contentResolver: ContentResolver = context.contentResolver
        val fileName = getFileName(contentResolver, uri) ?: return null
        val tempFile = File(context.cacheDir, fileName)

        try {
            contentResolver.openInputStream(uri)?.use { inputStream ->
                FileOutputStream(tempFile).use { outputStream ->
                    inputStream.copyTo(outputStream)
                }
            }
        } catch (e: Exception) {
            Log.e("FILE", "Error creating file from URI", e)
            return null
        }

        return tempFile
    }

    private fun getFileName(contentResolver: ContentResolver, uri: Uri): String? {
        var fileName: String? = null
        val cursor = contentResolver.query(uri, null, null, null, null)
        cursor?.use {
            if (it.moveToFirst()) {
                fileName = it.getString(it.getColumnIndex(android.provider.OpenableColumns.DISPLAY_NAME))
            }
        }
        return fileName
    }

    private fun uploadFileToS3(file: File) {
        val transferUtility = TransferUtility.builder()
            .context(applicationContext)
            .s3Client(s3Client)
            .defaultBucket("erg.csv")
            .build()

        val uploadObserver = transferUtility.upload("csv/${file.name}", file)

        uploadObserver.setTransferListener(object : TransferListener {
            override fun onStateChanged(id: Int, state: TransferState) {
                if (state == TransferState.COMPLETED) {
                    Log.i("UPLOAD", "Upload completed!")
                    Toast.makeText(applicationContext, "Upload completed!", Toast.LENGTH_SHORT).show()

                }
            }

            override fun onProgressChanged(id: Int, bytesCurrent: Long, bytesTotal: Long) {
                val progress = (bytesCurrent.toDouble() / bytesTotal) * 100
                Log.i("UPLOAD", "Progress: $progress%")
            }

            override fun onError(id: Int, ex: Exception) {
                Log.e("UPLOAD", "Error uploading file", ex)
            }
        })
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    ElectroRetinographyTheme {
        HomeScreen(onNavigateToSignalTest = {})
    }
}
