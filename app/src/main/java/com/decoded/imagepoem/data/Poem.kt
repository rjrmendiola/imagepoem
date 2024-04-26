package com.decoded.imagepoem.data

import android.net.Uri
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "poems")
data class Poem(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    @ColumnInfo(name = "image_uri") val imageUri: String,
    @ColumnInfo(name = "image_name") val imageName: String,
    @ColumnInfo(name = "image_path") val imagePath: String,
    @ColumnInfo(name = "contents") val contents: String,
    @ColumnInfo(name = "date_added") val dateAdded: Long
)