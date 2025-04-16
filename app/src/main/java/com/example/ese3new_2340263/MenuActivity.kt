package com.example.ese3new_2340263

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import android.widget.LinearLayout
import android.widget.RatingBar

class MenuActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menu)

        // Find the buttons
        val highlightsButton = findViewById<Button>(R.id.highlights_button)
        val findConcertButton = findViewById<Button>(R.id.find_concert_button)

        // Set up click listeners for navigation
        highlightsButton.setOnClickListener {
            val intent = Intent(this, HighlightsActivity::class.java)
            startActivity(intent)
        }

        findConcertButton.setOnClickListener {
            val intent = Intent(this, FindConcertActivity::class.java)
            startActivity(intent)
        }

        // Setup the toolbar/actionbar
        setSupportActionBar(findViewById(R.id.toolbar))
    }

    // Inflate menu
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        Toast.makeText(this, "Menu inflated", Toast.LENGTH_SHORT).show() // Debug toast
        return true
    }

    // Handle menu item clicks
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_about -> {
                // Create and show an AlertDialog instead of a Toast
                val aboutDialog = AlertDialog.Builder(this)
                    .setTitle("About Concert Tracker")
                    .setMessage("Concert Finder helps you discover live music events near you. Browse upcoming concerts, explore artists, and book tickets effortlessly. Stay updated on your favorite bands and never miss a show! \uD83C\uDFB6✨. Software Tested by: G. Prabhanjana.")
                    .setPositiveButton("OK", null)
                    .create()
                aboutDialog.show()
                true
            }
            R.id.rate -> {
                showRateDialog()
                true
            }
            R.id.action_logout -> {
                // Exit the app when logout is selected
                finish()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun showRateDialog() {
        val ratingBar = RatingBar(this).apply {
            numStars = 5 // Set the max stars to 5
            stepSize = 1.0f // Allow only whole numbers
            rating = 5.0f // Default rating (optional)
            layoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            ).apply {
                setMargins(50, 20, 50, 20) // Add some padding
            }
        }
        val layout = LinearLayout(this).apply {
            orientation = LinearLayout.VERTICAL
            setPadding(50, 20, 50, 20)
            addView(ratingBar)
        }
        AlertDialog.Builder(this)
            .setTitle("Rate App")
            .setMessage("Please rate this app:")
            .setView(layout) // Add the RatingBar inside a properly formatted layout
            .setPositiveButton("Submit") { _, _ ->
                val rating = ratingBar.rating
                Toast.makeText(this, "You rated: $rating stars", Toast.LENGTH_SHORT).show()
            }
            .setNegativeButton("Cancel", null)
            .show()
    }
}