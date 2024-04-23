package com.decoded.imagepoem

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
//        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
//            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
//            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
//            insets
//        }

        val button = findViewById<Button>(R.id.start_capture)
        button.setOnClickListener {
            // Toast.makeText(this, "Button clicked!", Toast.LENGTH_SHORT).show()
//            val intent = Intent(this, CameraActivity::class.java)
//            val intent = Intent(this, DetectorActivity::class.java)
//            val intent = Intent(this, CameraxOldActivity::class.java)
            val intent = Intent(this, Camera2Activity::class.java)
            startActivity(intent)
        }
    }
}
