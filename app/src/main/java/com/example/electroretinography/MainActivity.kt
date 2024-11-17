package com.example.electroretinography

import AWSWorker
import android.content.Intent
import android.os.Bundle
import android.widget.LinearLayout
import android.widget.Toast
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

        // Handle the TestSignal banner click to navigate to UploadCSVActivity
        val testSignalBanner = findViewById<LinearLayout>(R.id.TestSignal)
        testSignalBanner.setOnClickListener {
            val intent = Intent(this, UploadCSVActivity::class.java)
            startActivity(intent)
        }

        // Find Popular section layouts
        val selfTestLayout = findViewById<LinearLayout>(R.id.SelfTestLayout)
        val threeDLayout = findViewById<LinearLayout>(R.id.Layout3D)
        val eyeHealthLayout = findViewById<LinearLayout>(R.id.EyeHealthLayout)

        // Find My Eye Center section layouts
        val dryEyeScoreLayout = findViewById<LinearLayout>(R.id.DryEyeLayout)
        val appointmentReminderLayout = findViewById<LinearLayout>(R.id.ReminderLayout)
        val myMedicineLayout = findViewById<LinearLayout>(R.id.MedicineLayout)

        // Set click listeners for "Coming Soon..." functionality
        selfTestLayout.setOnClickListener { showComingSoonPopup() }
        threeDLayout.setOnClickListener { showComingSoonPopup() }
        eyeHealthLayout.setOnClickListener { showComingSoonPopup() }

        dryEyeScoreLayout.setOnClickListener { showComingSoonPopup() }
        appointmentReminderLayout.setOnClickListener { showComingSoonPopup() }
        myMedicineLayout.setOnClickListener { showComingSoonPopup() }
    }

    // Method to show the "Coming Soon..." popup as a Toast
    private fun showComingSoonPopup() {
        Toast.makeText(this, "Coming Soon...", Toast.LENGTH_SHORT).show()
    }
}
