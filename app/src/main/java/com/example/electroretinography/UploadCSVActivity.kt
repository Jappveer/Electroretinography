package com.example.electroretinography

import AWSWorker
import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import com.amazonaws.mobileconnectors.s3.transferutility.TransferState
import org.json.JSONObject

class UploadCSVActivity : Activity() {

    private val PICK_CSV_FILE_REQUEST = 100
    private lateinit var awsWorker: AWSWorker

    // TextViews for displaying results
    private lateinit var disorderResultText: TextView
    private lateinit var confidenceResultText: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_upload_csv)

        // Initialize AWSWorker
        awsWorker = AWSWorker(this)

        // Initialize TextViews
        disorderResultText = findViewById(R.id.disorder_result)
        confidenceResultText = findViewById(R.id.confidence_result)

        // Upload Button
        val uploadButton: Button = findViewById(R.id.upload_button)
        uploadButton.setOnClickListener {
            pickCSVFile()
        }

        // Download Button
        val downloadButton: Button = findViewById(R.id.download_button)
        downloadButton.setOnClickListener {
            awsWorker.downloadResultJsonFromS3()
        }
    }

    private fun pickCSVFile() {
        val intent = Intent(Intent.ACTION_OPEN_DOCUMENT).apply {
            addCategory(Intent.CATEGORY_OPENABLE)
            type = "text/*"
        }
        startActivityForResult(intent, PICK_CSV_FILE_REQUEST)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (resultCode == RESULT_OK && requestCode == PICK_CSV_FILE_REQUEST) {
            // Get the URI of the chosen file
            data?.data?.let { uri ->
                // Call AWSWorker to upload the file
                awsWorker.uploadFileToS3(uri)

                // After upload, make the download button visible
                val downloadButton: Button = findViewById(R.id.download_button)
                downloadButton.visibility = View.VISIBLE
            }
        }
    }

    // Method to update UI with disorder and confidence values
    fun updateResults(disorder: String, confidence: Double) {
        disorderResultText.text = "Predicted Disorder: $disorder"
        confidenceResultText.text = "Confidence: $confidence%"
    }
}
