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
import org.json.JSONObject
import java.io.File
import java.io.FileOutputStream
import java.io.InputStream
import com.example.electroretinography.BuildConfig
import com.example.electroretinography.UploadCSVActivity
import org.json.JSONArray


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

    // Upload Function (Already Provided)
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

    // Download Function to fetch result.json
    fun downloadResultJsonFromS3() {
        val key = "results/result.json" // Path to the result file in S3
        val localFile = File(context.cacheDir, "result.json") // Local file to save the result

        val downloadObserver = transferUtility.download("erg.csv", key, localFile)

        downloadObserver.setTransferListener(object : TransferListener {
            override fun onStateChanged(id: Int, state: TransferState) {
                if (state == TransferState.COMPLETED) {
                    Log.i("DOWNLOAD", "Download completed!")
                    Toast.makeText(context, "Download completed!", Toast.LENGTH_SHORT).show()

                    // Once download is complete, read the JSON file and process it
                    processDownloadedJson(localFile)
                }
            }

            override fun onProgressChanged(id: Int, bytesCurrent: Long, bytesTotal: Long) {
                val progress = (bytesCurrent.toDouble() / bytesTotal) * 100
                Log.i("DOWNLOAD", "Progress: $progress%")
            }

            override fun onError(id: Int, ex: Exception) {
                Log.e("DOWNLOAD", "Error downloading file", ex)
            }
        })
    }

    // Function to read the JSON content from the downloaded file
    private fun processDownloadedJson(file: File) {
        try {
            val jsonContent = file.readText() // Read the content of the JSON file
            val jsonArray = JSONArray(jsonContent)

            // Assuming the JSON is an array and we're interested in the first item
            val jsonObject = jsonArray.getJSONObject(0)

            // Extract disorder and confidence
            val disorder = jsonObject.getString("predicted_disorder")
            val confidence = jsonObject.getDouble("confidence")

            // Send results back to activity
            (context as? UploadCSVActivity)?.updateResults(disorder, confidence)

        } catch (e: Exception) {
            Log.e("JSON_PROCESSING", "Error processing JSON", e)
        }
    }


    private fun displayResults(jsonObject: JSONObject) {
        try {
            // Assuming the disorder and confidence are directly in the root of the JSON object
            val disorder = jsonObject.getString("predicted_disorder")
            val confidence = jsonObject.getDouble("confidence")

            // Display the results (you can modify this UI part as per your app's needs)
            Toast.makeText(context, "Disorder: $disorder\nConfidence: $confidence%", Toast.LENGTH_LONG).show()
        } catch (e: Exception) {
            Log.e("DISPLAY_RESULTS", "Error displaying results", e)
        }
    }


    private fun createFileFromUri(context: Context, uri: Uri): File? {
        val contentResolver = context.contentResolver
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
