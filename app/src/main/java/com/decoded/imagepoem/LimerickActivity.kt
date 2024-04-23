package com.decoded.imagepoem

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.room.ColumnInfo
import androidx.room.Room
import com.decoded.imagepoem.LimerickGenerator
import com.decoded.imagepoem.data.AppDatabase
import com.decoded.imagepoem.data.Poem
import java.util.ArrayList

class LimerickActivity : AppCompatActivity() {
    private val TAG = "Limerick"

    companion object {
        lateinit var database: AppDatabase
    }

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

        val imageUriString = intent.getStringExtra("imageUri")
        val imageUri = Uri.parse(imageUriString)

        val imageName = intent.getStringExtra("imageName") ?: ""
        val imagePath = intent.getStringExtra("imagePath") ?: ""

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

            Log.e(TAG, limerickPoem)
        }

        val button = findViewById<Button>(R.id.restart_capture)
        button.setOnClickListener {
            //val intent = Intent(this, DetectorActivity::class.java)
            val intent = Intent(this, Camera2Activity::class.java)
            startActivity(intent)
        }
    }
}