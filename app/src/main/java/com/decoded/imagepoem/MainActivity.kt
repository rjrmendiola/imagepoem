package com.decoded.imagepoem

import android.content.ContentValues.TAG
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.decoded.imagepoem.data.Poem
import com.decoded.imagepoem.data.PoemDao

class MainActivity : AppCompatActivity() {

//    private val poemDao: PoemDao by lazy { ImagePoem.database.poemDao() } // Access through application class
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val button = findViewById<Button>(R.id.start_capture)
        button.setOnClickListener {
            // Toast.makeText(this, "Button clicked!", Toast.LENGTH_SHORT).show()
//            val intent = Intent(this, CameraActivity::class.java)
            val intent = Intent(this, DetectorActivity::class.java)
//            val intent = Intent(this, CameraxOldActivity::class.java)
//            val intent = Intent(this, Camera2Activity::class.java)
//            val intent = Intent(this, ImageListActivity::class.java)
            startActivity(intent)
        }

//        val poem = Poem(
//            //imageUri = Uri.parse("content://media/external/images/media/12345"), // Replace with your actual image Uri
//            imageUri = "content://media/external/images/media/12345", // Replace with your actual image Uri
//            imageName = "My Poem.jpg",
//            imagePath = "/storage/emulated/0/Pictures/My Poem.jpg", // Replace with your actual image path
//            contents = """
//      The sun dips low, a fiery glow,
//      Painting the clouds in shades of gold.
//      A gentle breeze whispers through the trees,
//      A story of times yet untold.
//
//      Birds sing their songs, a sweet serenade,
//      As twilight descends upon the land.
//      Stars begin to peek, a shimmering cascade,
//      Guiding the way wiwth a gentle hand.
//
//      This is the time for dreams to take flight,
//      For hopes and wishes to ignite.
//      A moment of peace, a calming sight,
//      The beauty of night.
//  """.trimIndent(),
//            dateAdded = System.currentTimeMillis()
//        )
//        poemDao.insertPoem(poem)\
    }
}
