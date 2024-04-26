package com.decoded.imagepoem

import android.content.Context
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView

class ImageListAdapter(context: Context, public val images: ArrayList<Image>) : BaseAdapter() {

    private val inflater: LayoutInflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

    override fun getCount(): Int = images.size

    override fun getItem(position: Int): Image = images[position]

    override fun getItemId(position: Int): Long = position.toLong()

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val view = inflater.inflate(R.layout.image_list_item, parent, false)
        val imageView = view.findViewById<ImageView>(R.id.image_view)
        val imageNameTextView = view.findViewById<TextView>(R.id.image_name_text_view)

        val image = getItem(position)
        // Load image from Uri (replace with your image loading logic)
        imageView.setImageURI(Uri.parse(image.uri))
        imageNameTextView.text = image.name
        return view
    }
}
