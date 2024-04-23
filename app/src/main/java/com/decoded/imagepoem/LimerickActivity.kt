package com.decoded.imagepoem

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.decoded.imagepoem.LimerickGenerator
import java.util.ArrayList

class LimerickActivity : AppCompatActivity() {
    private val TAG = "Limerick"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_limerick)

        val intent = intent
        val detectedWords = intent.getStringArrayListExtra("detectedWords") as? ArrayList<String>

        if (detectedWords != null) {
            var limerickGenerator = LimerickGenerator()
            val limerickPoem = limerickGenerator.generateLimerick(detectedWords)

            val limerickTextView = findViewById<TextView>(R.id.limerickText)
            limerickTextView.text = limerickPoem

            Log.e(TAG, limerickPoem)
        }

        val button = findViewById<Button>(R.id.restart_capture)
        button.setOnClickListener {
            val intent = Intent(this, DetectorActivity::class.java)
            startActivity(intent)
        }
    }
}