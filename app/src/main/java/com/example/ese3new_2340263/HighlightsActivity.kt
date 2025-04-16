package com.example.ese3new_2340263

import android.Manifest
import android.content.pm.PackageManager
import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.widget.GridLayout
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.google.android.material.floatingactionbutton.FloatingActionButton
import android.content.Intent

class HighlightsActivity : AppCompatActivity() {

    private lateinit var galleryGrid: GridLayout
    private lateinit var uploadFab: FloatingActionButton
    private val imageUris = ArrayList<Uri>()

    // Permission request for older Android versions
    private val requestPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted: Boolean ->
        if (isGranted) {
            openGallery()
        } else {
            Toast.makeText(this, "Storage permission is needed to select images", Toast.LENGTH_LONG).show()
        }
    }

    // Image picker with proper activity result handling
    private val pickImageLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == RESULT_OK) {
            val uri = result.data?.data
            uri?.let {
                // Just add the URI directly to our list and display it
                addImageToGallery(it)
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_highlights)

        // Initialize views
        galleryGrid = findViewById(R.id.gallery_grid)
        uploadFab = findViewById(R.id.upload_fab)

        // Set up FAB click listener
        uploadFab.setOnClickListener {
            checkPermissionAndPickImage()
        }
    }

    private fun checkPermissionAndPickImage() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            openGallery()
        } else {
            when {
                ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)
                        == PackageManager.PERMISSION_GRANTED -> {
                    openGallery()
                }
                ActivityCompat.shouldShowRequestPermissionRationale(
                    this, Manifest.permission.READ_EXTERNAL_STORAGE
                ) -> {
                    Toast.makeText(this, "Storage permission is required to access images", Toast.LENGTH_SHORT).show()
                    requestPermissionLauncher.launch(Manifest.permission.READ_EXTERNAL_STORAGE)
                }
                else -> {
                    requestPermissionLauncher.launch(Manifest.permission.READ_EXTERNAL_STORAGE)
                }
            }
        }
    }

    private fun openGallery() {
        val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        pickImageLauncher.launch(Intent.createChooser(intent, "Select Picture"))
    }

    private fun addImageToGallery(imageUri: Uri) {
        // Only try to take persistable permission if the URI scheme is content
        if (imageUri.scheme == "content") {
            try {
                val flags = Intent.FLAG_GRANT_READ_URI_PERMISSION
                // Check if this URI offers persistable permission
                val persistableFlags = contentResolver.persistedUriPermissions.find {
                    it.uri == imageUri
                }?.isReadPermission

                if (persistableFlags == true) {
                    contentResolver.takePersistableUriPermission(imageUri, flags)
                }
            } catch (e: SecurityException) {
                // Handle the exception but don't crash
                Toast.makeText(this, "Cannot access this image permanently", Toast.LENGTH_SHORT).show()
            }
        }

        imageUris.add(imageUri)
        displayImageInGrid(imageUri)
    }

    // Display image in GridLayout
    private fun displayImageInGrid(imageUri: Uri) {
        val imageView = ImageView(this).apply {
            layoutParams = GridLayout.LayoutParams().apply {
                val sizeInPx = (120 * resources.displayMetrics.density).toInt()
                width = sizeInPx
                height = sizeInPx

                val marginInPx = (8 * resources.displayMetrics.density).toInt()
                setMargins(marginInPx, marginInPx, marginInPx, marginInPx)
            }
            scaleType = ImageView.ScaleType.CENTER_CROP
            try {
                setImageURI(imageUri)
            } catch (e: Exception) {
                setBackgroundColor(Color.LTGRAY)
                Toast.makeText(this@HighlightsActivity, "Error displaying image", Toast.LENGTH_SHORT).show()
            }

            // Orange border
            background = GradientDrawable().apply {
                setColor(Color.TRANSPARENT)
                setStroke(2.dpToPx(), Color.parseColor("#FF5722"))
                cornerRadius = 4.dpToPx().toFloat()
            }

            setOnClickListener {
                Toast.makeText(this@HighlightsActivity, "Image clicked", Toast.LENGTH_SHORT).show()
            }
        }

        galleryGrid.addView(imageView)
    }

    private fun Int.dpToPx(): Int {
        return (this * resources.displayMetrics.density).toInt()
    }
}