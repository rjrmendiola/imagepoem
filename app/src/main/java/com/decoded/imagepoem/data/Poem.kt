package com.decoded.imagepoem.data

import android.net.Uri
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "poem")
data class Poem(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    @ColumnInfo(name = "image_uri") val imageUri: Uri,
    @ColumnInfo(name = "image_name") val imageName: String,
    @ColumnInfo(name = "image_path") val imagePath: String,
    @ColumnInfo(name = "contents") val contents: String
)