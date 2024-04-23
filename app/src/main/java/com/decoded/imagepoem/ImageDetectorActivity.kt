package com.decoded.imagepoem

import android.content.ContentValues.TAG
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.ImageDecoder
import android.graphics.Matrix
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.ProgressBar
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.SimpleTarget

class ImageDetectorActivity : AppCompatActivity(), Detector.DetectorListener {
    private lateinit var imageView: ImageView
    private lateinit var progressBar: ProgressBar

    private lateinit var detector: Detector
    private val detectedObjects = mutableListOf<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_image_detector)

        imageView = findViewById(R.id.image_view)
        progressBar = findViewById(R.id.progress_bar)

        // Simulate loading
        progressBar.visibility = View.VISIBLE  // Show progress bar

        detector = Detector(baseContext, Constants.MODEL_PATH, Constants.LABELS_PATH, this)
        detector.setup()

        val imageUriString = intent.getStringExtra("imageUri")
        val imageUri = Uri.parse(imageUriString)

        // val uri = Uri.parse("content://media/external/images/media/1000000045")
        val uri = Uri.parse(imageUriString)
        // val projection = arrayOf(MediaStore.Images.Media.DATA) // Get the data column for file path

        // val cursor = contentResolver.query(uri, projection, null, null, null)
        val cursor = contentResolver.query(uri, null, null, null, null)

        if (cursor != null) {
            try {
                val columnIndex = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA)
                if (cursor.moveToFirst()) {
                    val imagePath = cursor.getString(columnIndex)
                    // val bitmap = BitmapFactory.decodeFile(imagePath, BitmapFactory.Options())
                    if (imageUriString != null) {
                        val rotatedBitmap = rotateBitmap(imageUriString)

                        if (rotatedBitmap != null) {
                            detector.detect(rotatedBitmap)
                        }
                    }
                }
            } finally {
                cursor.close()
            }
        } else {
            // Handle the case where cursor is null (e.g., image not found)
        }

        // After loading is complete
        imageView.visibility = View.VISIBLE  // Show image
        progressBar.visibility = View.GONE  // Hide progress bar
    }

    override fun onDestroy() {
        super.onDestroy()
        detector.clear()
    }

    override fun onEmptyDetect() {
        TODO("Not yet implemented")
    }

    override fun onDetect(boundingBoxes: List<BoundingBox>, inferenceTime: Long) {
        detectedObjects.clear() // Clear previous detections

        for (box in boundingBoxes) {
            detectedObjects.add(box.clsName) // Assuming 'category' holds the object name
        }

        // val objectListString = detectedObjects.joinToString(", ")

        if (detectedObjects.isNotEmpty()) {
            val intent = Intent(this, LimerickActivity::class.java)
            intent.putStringArrayListExtra("detectedWords", ArrayList(detectedObjects))
            startActivity(intent)
        }
    }

    private fun rotateBitmap(imagePath: String): Bitmap? {
        val options = BitmapFactory.Options()
        options.inSampleSize = 2 // Adjust inSampleSize as needed
        val bitmap = BitmapFactory.decodeFile(imagePath, options) ?: return null

        val matrix = Matrix().apply {
            // Assuming no Exif data available (similar to previous example without Exif)
            postRotate(90f) // Adjust rotation angle as needed based on your scenario

            // Always flip horizontally (set isFrontCamera to false)
            postScale(-1f, 1f, bitmap.width.toFloat(), bitmap.height.toFloat())
        }

        return Bitmap.createBitmap(bitmap, 0, 0, bitmap.width, bitmap.height, matrix, true)
    }
}