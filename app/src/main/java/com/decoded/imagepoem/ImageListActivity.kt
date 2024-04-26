package com.decoded.imagepoem

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ListView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class ImageListActivity : AppCompatActivity() {
    private lateinit var listView: ListView
    private lateinit var adapter: ImageListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_image_list)

        listView = findViewById(R.id.image_list_view)
        adapter = ImageListAdapter(this, ArrayList())
        listView.adapter = adapter

        val dbHelper = DbHelper(this, null)
        val images = getImagesFromDatabase(dbHelper)
        adapter.images.addAll(images)
        adapter.notifyDataSetChanged()

        val backButton = findViewById<Button>(R.id.back_button)
        backButton.setOnClickListener {
            val intent = Intent(this, DetectorActivity::class.java)
            startActivity(intent)
        }
    }

    private fun getImagesFromDatabase(dbHelper: DbHelper): ArrayList<Image> {
        val images = ArrayList<Image>()
        val db = dbHelper.readableDatabase
        val cursor = db.query(
            DbHelper.TABLE_NAME,  // Table name
            null,                   // Select all columns
            null,                   // No selection criteria
            null,                   // No selection arguments
            null,                   // No grouping
            null,                   // No filtering by row groups
            null                    // No sorting
        )
        while (cursor.moveToNext()) {
            val id = cursor.getInt(cursor.getColumnIndexOrThrow(DbHelper.ID_COL))
            val uri = cursor.getString(cursor.getColumnIndexOrThrow(DbHelper.URI_COL))
            val name = cursor.getString(cursor.getColumnIndexOrThrow(DbHelper.NAME_COL))
            val path = cursor.getString(cursor.getColumnIndexOrThrow(DbHelper.PATH_COL))
            val content = cursor.getString(cursor.getColumnIndexOrThrow(DbHelper.CONTENT_COL))
            images.add(Image(id, uri, name, path, content))
        }

        cursor.close()
        db.close()
        return images
    }
}