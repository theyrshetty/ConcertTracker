package com.example.ese3new_2340263

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout

class FindConcertActivity : AppCompatActivity() {

    // Concert card layouts
    private lateinit var concertLayouts: Map<String, ConstraintLayout?>

    // Ticket URLs for each concert
    private val ticketUrls = mapOf(
        "avenged" to "https://www.bandland.in/#/tickets",
        "extreme" to "https://www.bandland.in/#/tickets",
        "mrbig" to "https://www.bandsintown.com/e/106207723-mr.-big-at-bloomverse-express",
        "gunsnroses" to "https://in.bookmyshow.com/events/guns-n-roses-india-2025/ET00437140",
        "bangalore" to "https://www.bangaloreopenair.com/tickets",
        "animals" to "https://www.skillboxes.com/events/business/animals-as-leaders-parrhesia-india-tour",
        "aristocrats" to "https://www.skillboxes.com/events/livebox-feat-the-aristocrats-bangalore"
    )

    // Concert names and prices
    private val concertData = mapOf(
        "avenged" to Pair("Avenged Sevenfold Bandland 24", 3500),
        "extreme" to Pair("Extreme Bandland 24", 3500),
        "mrbig" to Pair("Mr. Big The Big Finish", 2500),
        "gunsnroses" to Pair("Guns N' Roses", 5999),
        "bangalore" to Pair("Bangalore Open Air", 15000),
        "animals" to Pair("Animals As Leaders Parrhesia India Tour", 1999),
        "aristocrats" to Pair("The Aristocrats Duck Tour", 2500)
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_find_concert)

        val highlightsButton = findViewById<Button>(R.id.highlightsButton)

        // Set click listener for the highlights button
        highlightsButton.setOnClickListener {
            // Create an intent to launch the HighlightsActivity
            val highlightsIntent = Intent(this, HighlightsActivity::class.java)
            startActivity(highlightsIntent)
        }

        // Initialize concert layouts
        initializeConcertLayouts()

        setupTicketButtons()
        setupShareButtons()
    }

    private fun initializeConcertLayouts() {
        try {
            concertLayouts = mapOf(
                "avenged" to findViewById(R.id.concertCard1),
                "extreme" to findViewById(R.id.concertCard2),
                "mrbig" to findViewById(R.id.concertCard3),
                "gunsnroses" to findViewById(R.id.concertCard4),
                "bangalore" to findViewById(R.id.concertCard5),
                "animals" to findViewById(R.id.concertCard6),
                "aristocrats" to findViewById(R.id.concertCard7)
            )

            // Verify that all layouts were found
            concertLayouts.forEach { (key, layout) ->
                if (layout == null) {
                    Log.e("FindConcertActivity", "Layout for $key is null")
                }
            }
        } catch (e: Exception) {
            Log.e("FindConcertActivity", "Error initializing concert layouts", e)
            Toast.makeText(this, "Error initializing concerts", Toast.LENGTH_SHORT).show()
        }
    }

    private fun setupTicketButtons() {
        try {
            // Set up click listeners for all buy ticket buttons
            findViewById<Button>(R.id.buyTicketsButton1)?.setOnClickListener {
                openTicketUrl(ticketUrls["avenged"] ?: "")
            }

            findViewById<Button>(R.id.buyTicketsButton2)?.setOnClickListener {
                openTicketUrl(ticketUrls["extreme"] ?: "")
            }

            findViewById<Button>(R.id.buyTicketsButton3)?.setOnClickListener {
                openTicketUrl(ticketUrls["mrbig"] ?: "")
            }

            findViewById<Button>(R.id.buyTicketsButton4)?.setOnClickListener {
                openTicketUrl(ticketUrls["gunsnroses"] ?: "")
            }

            findViewById<Button>(R.id.buyTicketsButton5)?.setOnClickListener {
                openTicketUrl(ticketUrls["bangalore"] ?: "")
            }

            findViewById<Button>(R.id.buyTicketsButton6)?.setOnClickListener {
                openTicketUrl(ticketUrls["animals"] ?: "")
            }

            findViewById<Button>(R.id.buyTicketsButton7)?.setOnClickListener {
                openTicketUrl(ticketUrls["aristocrats"] ?: "")
            }
        } catch (e: Exception) {
            Log.e("FindConcertActivity", "Error setting up ticket buttons", e)
        }
    }

    private fun setupShareButtons() {
        try {
            // Set up share buttons
            val shareInfo = mapOf(
                R.id.shareButton1 to "Check out Bandland 24 - Avenged Sevenfold at NICE GROUNDS on 23 Nov 2024! Tickets are ₹3500.",
                R.id.shareButton2 to "Check out Bandland 24 - Extreme at NICE GROUNDS on 24 Nov 2024! Tickets are ₹3500.",
                R.id.shareButton3 to "Check out The Big Finish - Mr. Big at Phoenix Marketcity on 16 Feb 2025! Tickets are ₹2500.",
                R.id.shareButton4 to "Check out Guns N' Roses at Mahalakshmi Racecourse, Mumbai on 17 May 2025! Tickets are ₹5999.",
                R.id.shareButton5 to "Check out Bangalore Open Air at The Royal Orchid Resort on 7 Feb 2026! Tickets are ₹15000.",
                R.id.shareButton6 to "Check out Animals As Leaders - Parrhesia India Tour on 20-25 Aug 2024! Tickets are ₹3000.",
                R.id.shareButton7 to "Check out The Duck Tour - The Aristocrats at Phoenix Mark on 11 May 2025! Tickets are ₹3000."
            )

            shareInfo.forEach { (buttonId, message) ->
                findViewById<ImageButton>(buttonId)?.setOnClickListener {
                    shareEventInfo(message)
                }
            }
        } catch (e: Exception) {
            Log.e("FindConcertActivity", "Error setting up share buttons", e)
        }
    }

    private fun openTicketUrl(url: String) {
        try {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
            startActivity(intent)
        } catch (e: Exception) {
            Log.e("FindConcertActivity", "Error opening ticket URL", e)
            Toast.makeText(this, "Cannot open ticket link", Toast.LENGTH_SHORT).show()
        }
    }

    private fun shareEventInfo(message: String) {
        try {
            val sendIntent = Intent().apply {
                action = Intent.ACTION_SEND
                putExtra(Intent.EXTRA_TEXT, message)
                type = "text/plain"
            }
            val shareIntent = Intent.createChooser(sendIntent, "Share concert details")
            startActivity(shareIntent)
        } catch (e: Exception) {
            Log.e("FindConcertActivity", "Error sharing event info", e)
            Toast.makeText(this, "Cannot share event details", Toast.LENGTH_SHORT).show()
        }
    }
}