package com.example.electroretinography

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private lateinit var awsWorker: AWSWorker

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Initialize AWSWorker
        awsWorker = AWSWorker(this)

        // Remove action bar at the top
        supportActionBar?.hide()

        // Set the layout for the activity
        setContentView(R.layout.main_dashboard_activity)

        // Find the "TestSignal" LinearLayout by its ID
        val testSignalBanner = findViewById<LinearLayout>(R.id.TestSignal)

        // Set an OnClickListener on the "TestSignal" banner
        testSignalBanner.setOnClickListener {
            // When clicked, navigate to UploadCSVActivity
            val intent = Intent(this, UploadCSVActivity::class.java)
            startActivity(intent)
        }
    }
}
