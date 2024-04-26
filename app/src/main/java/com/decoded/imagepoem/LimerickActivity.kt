package com.decoded.imagepoem

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.room.ColumnInfo
import androidx.room.Room
import com.bumptech.glide.Glide
import com.decoded.imagepoem.LimerickGenerator
import com.decoded.imagepoem.data.AppDatabase
import com.decoded.imagepoem.data.Poem
import java.util.ArrayList

class LimerickActivity : AppCompatActivity() {
    private val TAG = "Limerick"

//    companion object {
//        lateinit var database: AppDatabase
//    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        database = Room.databaseBuilder(
//            applicationContext,
//            AppDatabase::class.java,
//            "imagepoem_db"
//        ).build()

        setContentView(R.layout.activity_limerick)

        val intent = intent
        val detectedWords = intent.getStringArrayListExtra("detectedWords") as? ArrayList<String>

        val imageId = intent.getStringExtra("imageId") ?: ""

        //val imageUriString = intent.getStringExtra("imageUri")
        //val imageUri = Uri.parse(imageUriString)

        //val imageName = intent.getStringExtra("imageName") ?: ""
        //val imagePath = intent.getStringExtra("imagePath") ?: ""

        if (detectedWords != null) {
            var limerickGenerator = LimerickGenerator()
            val limerickPoem = limerickGenerator.generateLimerick(detectedWords)

            val limerickTextView = findViewById<TextView>(R.id.limerickText)
            limerickTextView.text = limerickPoem

//            val newPoem = Poem(imageUri = imageUri,
//                imageName = imageName,
//                imagePath = imagePath,
//                contents = limerickPoem)
//            database.poemDao().insertPoem(newPoem)

            val dbHelper = DbHelper(baseContext, null)
            var imageRecord = dbHelper.getImageRecordById(imageId.toInt())
            if (imageRecord != null) {
                val rowsUpdated = dbHelper.updateImageContent(imageRecord, limerickPoem)
                if (rowsUpdated == 1) {
                    Log.e(TAG, limerickPoem)
                    Toast.makeText(this, "Image poem recorded successfully", Toast.LENGTH_SHORT).show()
                } else {
                    Log.e(TAG, limerickPoem)
                    Toast.makeText(this, "Image poem recording failed", Toast.LENGTH_SHORT).show()
                }

                val imageView = findViewById<ImageView>(R.id.image_preview)
//                var bitmap: Bitmap? = null
//                try {
//                    if (imageRecord.uri != null) {
//                        val inputStream = contentResolver.openInputStream(Uri.parse(imageRecord.uri))
//                        bitmap = BitmapFactory.decodeStream(inputStream)
//                        inputStream?.close()
//                    }
//                } catch (e: Exception) {
//                    e.printStackTrace()
//                }

                Glide.with(this) // Use the context of your Activity
                    .load(imageRecord.uri) // Replace with your image URL or resource ID
                    .error(R.drawable.image_not_found)
                    .centerCrop()
                    .into(imageView) // Replace with your ImageView ID
            }
        }

        val restartButton = findViewById<Button>(R.id.restart_capture)
        restartButton.setOnClickListener {
            //val intent = Intent(this, DetectorActivity::class.java)
            val intent = Intent(this, Camera2Activity::class.java)
            startActivity(intent)
        }

        val viewImagesButton = findViewById<Button>(R.id.view_images_button)
        viewImagesButton.setOnClickListener {
            val intent = Intent(this, ImageListActivity::class.java)
            startActivity(intent)
        }


    }
}