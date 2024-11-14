package com.example.electroretinography

import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.findNavController
import com.example.electroretinography.ui.theme.ElectroRetinographyTheme

class MainActivity : AppCompatActivity() {

    private lateinit var awsWorker: AWSWorker
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Initialize AWSWorker
        awsWorker = AWSWorker(this)

        // Remove action bar at the top
        supportActionBar?.hide()

        // Set the layout for the activity
        setContentView(R.layout.main_dashboard_activity)

    }
}
