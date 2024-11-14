package com.example.electroretinography

import android.content.ContentResolver
import android.content.Context
import android.net.Uri
import android.util.Log
import android.widget.Toast
import com.amazonaws.auth.BasicAWSCredentials
import com.amazonaws.mobileconnectors.s3.transferutility.TransferListener
import com.amazonaws.mobileconnectors.s3.transferutility.TransferState
import com.amazonaws.mobileconnectors.s3.transferutility.TransferUtility
import com.amazonaws.services.s3.AmazonS3Client
import java.io.File
import java.io.FileOutputStream

class AWSWorker(private val context: Context) {

    private val s3Client: AmazonS3Client by lazy {
        val credentials = BasicAWSCredentials(BuildConfig.AWS_ACCESS_KEY, BuildConfig.AWS_SECRET_KEY)
        AmazonS3Client(credentials)
    }

    private val transferUtility: TransferUtility by lazy {
        TransferUtility.builder()
            .context(context)
            .s3Client(s3Client)
            .defaultBucket("erg.csv")
            .build()
    }

    fun uploadFileToS3(uri: Uri) {
        val file = createFileFromUri(context, uri)
        file?.let {
            val uploadObserver = transferUtility.upload("csv/${it.name}", it)

            uploadObserver.setTransferListener(object : TransferListener {
                override fun onStateChanged(id: Int, state: TransferState) {
                    if (state == TransferState.COMPLETED) {
                        Log.i("UPLOAD", "Upload completed!")
                        Toast.makeText(context, "Upload completed!", Toast.LENGTH_SHORT).show()
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

    private fun createFileFromUri(context: Context, uri: Uri): File? {
        val contentResolver: ContentResolver = context.contentResolver
        val fileName = getFileName(contentResolver, uri) ?: return null
        val tempFile = File(context.cacheDir, fileName)

        return try {
            contentResolver.openInputStream(uri)?.use { inputStream ->
                FileOutputStream(tempFile).use { outputStream ->
                    inputStream.copyTo(outputStream)
                }
            }
            tempFile
        } catch (e: Exception) {
            Log.e("FILE", "Error creating file from URI", e)
            null
        }
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
}
