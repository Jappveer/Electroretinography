package com.example.electroretinography

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import com.amazonaws.mobileconnectors.s3.transferutility.TransferState

class UploadCSVActivity : Activity() {

    private val PICK_CSV_FILE_REQUEST = 100 // Lower request code

    private lateinit var awsWorker: AWSWorker

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_upload_csv)

        // Initialize AWSWorker
        awsWorker = AWSWorker(this)

        // Button to trigger file picker
        val uploadButton: Button = findViewById(R.id.upload_button)
        uploadButton.setOnClickListener {
            pickCSVFile()
        }
    }

    private fun pickCSVFile() {
        // Intent to pick a CSV file
        val intent = Intent(Intent.ACTION_OPEN_DOCUMENT).apply {
            addCategory(Intent.CATEGORY_OPENABLE)
            type = "text/*"
        }
        startActivityForResult(intent, PICK_CSV_FILE_REQUEST) // Use the smaller request code here
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (resultCode == RESULT_OK && requestCode == PICK_CSV_FILE_REQUEST) {
            // Get the URI of the chosen file
            data?.data?.let { uri ->
                // Call the AWSWorker to upload the file
                awsWorker.uploadFileToS3(uri)
            }
        }
    }
}
