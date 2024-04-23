package com.decoded.imagepoem

import android.content.ContentUris
import android.content.ContentValues.TAG
import android.content.Intent
import android.database.Cursor
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.drawable.Drawable
import android.graphics.drawable.DrawableWrapper
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.room.Room
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.decoded.imagepoem.data.AppDatabase

class PreviewActivity : AppCompatActivity(), Detector.DetectorListener {
    private lateinit var detector: Detector
    private val detectedObjects = mutableListOf<String>()
    //private lateinit var bitmap: Bitmap
    private var imageUri: Uri? = null
    private var imageName: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_preview)

        val imageUriString = intent.getStringExtra("imageUri")
        imageUri = Uri.parse(imageUriString)

        imageName = intent.getStringExtra("imageName")

        val imageView = findViewById<ImageView>(R.id.image_view)  // Your ImageView

        detector = Detector(baseContext, Constants.MODEL_PATH, Constants.LABELS_PATH, this)
        detector.setup()

        var bitmap: Bitmap? = null
        try {
            if (imageUri != null) {
                val inputStream = contentResolver.openInputStream(Uri.parse(imageUriString))
                bitmap = BitmapFactory.decodeStream(inputStream)
                inputStream?.close()
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }

          Glide.with(this) // Use the context of your Activity
            .load(imageUri) // Replace with your image URL or resource ID
            .error(R.drawable.image_not_found)
            .centerCrop()
            .into(imageView) // Replace with your ImageView ID

        val backCaptureButton = findViewById<Button>(R.id.back_capture_button)
        backCaptureButton.setOnClickListener {
            val intent = Intent(this, Camera2Activity::class.java)
            startActivity(intent)
        }

        val generateButton = findViewById<Button>(R.id.generate_button)
        generateButton.setOnClickListener {
            //val intent = Intent(this, ImageDetectorActivity::class.java)
            //intent.putExtra("imageUri", imageUriString)  // Pass Uri as a string
            //startActivity(intent)

            if (bitmap != null) {
                detector.detect(bitmap)
            }
//            if (detectedObjects.isNotEmpty()) {
//                val objectListString = detectedObjects.joinToString(", ")
//                Log.e(TAG, objectListString)
//            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        detector.clear()
    }

    override fun onEmptyDetect() {
        //Toast.makeText(baseContext, "No object detected!", Toast.LENGTH_SHORT).show()
        val intent = Intent(this, LimerickActivity::class.java)
        detectedObjects.add("chair")
        intent.putStringArrayListExtra("detectedWords", ArrayList(detectedObjects))
        intent.putExtra("imageUri", imageUri.toString())  // Pass URI as a string
        intent.putExtra("imageName", imageName.toString())  // Pass name as a string
        startActivity(intent)
    }

    override fun onDetect(boundingBoxes: List<BoundingBox>, inferenceTime: Long) {
        detectedObjects.clear() // Clear previous detections

        for (box in boundingBoxes) {
            detectedObjects.add(box.clsName) // Assuming 'category' holds the object name
        }

        val objectListString = detectedObjects.joinToString(", ")
        Log.e(TAG, objectListString)

        if (detectedObjects.isNotEmpty()) {
            val intent = Intent(this, LimerickActivity::class.java)
            intent.putStringArrayListExtra("detectedWords", ArrayList(detectedObjects))
            intent.putExtra("imageUri", imageUri.toString())  // Pass URI as a string
            intent.putExtra("imageName", imageName.toString())  // Pass name as a string
            startActivity(intent)
        }
    }
}