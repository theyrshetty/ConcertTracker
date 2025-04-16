package com.example.ese3new_2340263
import android.media.MediaPlayer
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AlertDialog
import android.content.Intent

class MainActivity : AppCompatActivity() {
    private lateinit var edtUsername: EditText
    private lateinit var edtPassword: EditText
    private lateinit var btnAuth: Button
    private lateinit var txtSwitch: TextView
    private var isLogin = true

    private lateinit var mediaPlayer: MediaPlayer // Declare media player

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Initialize views
        edtUsername = findViewById(R.id.edt_username)
        edtPassword = findViewById(R.id.edt_password)
        btnAuth = findViewById(R.id.btn_auth)
        txtSwitch = findViewById(R.id.txt_switch)

        updateUI(isLogin)

        txtSwitch.setOnClickListener {
            isLogin = !isLogin
            updateUI(isLogin)
        }

        btnAuth.setOnClickListener {
            handleAuth()
        }

        // In your onCreate method, after initializing the media player
        mediaPlayer = MediaPlayer.create(this, R.raw.background_music)
        if (mediaPlayer == null) {
            Toast.makeText(this, "Failed to load music file", Toast.LENGTH_SHORT).show()
        } else {
            mediaPlayer.isLooping = true
            mediaPlayer.setVolume(1.0f, 1.0f) // Set volume to maximum
            try {
                mediaPlayer.start()
                Toast.makeText(this, "Music started", Toast.LENGTH_SHORT).show() // Debug toast
            } catch (e: Exception) {
                Toast.makeText(this, "Error playing music: ${e.message}", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun updateUI(isLogin: Boolean) {
        if (isLogin) {
            btnAuth.text = "Login"
            txtSwitch.text = "Don't have an account? Register here"
        } else {
            btnAuth.text = "Register"
            txtSwitch.text = "Already have an account? Login here"
        }
    }

    private fun handleAuth() {
        val username = edtUsername.text.toString().trim()
        val password = edtPassword.text.toString().trim()

        if (username.isEmpty()) {
            edtUsername.error = "Username is required"
            edtUsername.requestFocus()
            Toast.makeText(this, "Please enter a username", Toast.LENGTH_SHORT).show()
            return
        }

        if (password.isEmpty()) {
            edtPassword.error = "Password is required"
            edtPassword.requestFocus()
            Toast.makeText(this, "Please enter a password", Toast.LENGTH_SHORT).show()
            return
        }

        if (isLogin) {
            // Perform login logic (e.g., check credentials)
            Toast.makeText(this, "Welcome, $username!", Toast.LENGTH_SHORT).show()

            // Create an intent to navigate to MenuActivity
            val intent = Intent(this, MenuActivity::class.java)

            // Optionally pass data to MenuActivity
            intent.putExtra("username", username)

            // Start the MenuActivity
            startActivity(intent)
        } else {
            // Perform registration logic
            Toast.makeText(this, "Registered successfully! Now you can log in.", Toast.LENGTH_SHORT).show()
            isLogin = true
            updateUI(isLogin)
        }
    }




    // Release the MediaPlayer when the activity is paused or destroyed
    override fun onPause() {
        super.onPause()
        if (mediaPlayer.isPlaying) {
            mediaPlayer.pause() // Pause the music when the activity is paused
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        if (mediaPlayer.isPlaying) {
            mediaPlayer.stop() // Stop the music when the activity is destroyed
            mediaPlayer.release() // Release the media player resources
        }
    }
}
