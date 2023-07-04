package com.example.mad.Adapter

import android.content.Context
import android.graphics.BitmapFactory
import android.util.Base64
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.mad.Model.CropModel
import com.example.mad.R
import java.io.ByteArrayInputStream

class CropsUserAdapter(CropModel:List<CropModel>, internal var context: Context): RecyclerView.Adapter<CropsUserAdapter.CropsViewHolder>() {
    internal var cropsList:List<CropModel> = ArrayList()
    init{
        this.cropsList=CropModel;
    }
    inner class CropsViewHolder(view: View): RecyclerView.ViewHolder(view){
        //var ArticleId: TextView =view.findViewById(R.id.articleId)
        var CropName: TextView =view.findViewById(R.id.cropnameuser)
        var CropRegion: TextView =view.findViewById(R.id.cropregionuser)
        var CropPrice: TextView =view.findViewById(R.id.croppriceuser)
        var CropImage: ImageView= view.findViewById(R.id.cropimageuser)
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CropsViewHolder {
        val view= LayoutInflater.from(context).inflate(R.layout.cropsuserread,parent,false)
        return CropsViewHolder(view)
    }

    override fun onBindViewHolder(holder: CropsViewHolder, position: Int) {

        val crop=cropsList[position]
        //holder.ArticleId.text=articles.article_Id.toString()
        holder.CropName.text=crop.cropName
        holder.CropRegion.text=crop.cropRegion
        holder.CropPrice.text=crop.cropPrice;

        // Example base64-encoded byte code string
        val byteCodeString = crop.cropImage
// Decode the byte code string into a byte array
        val imageBytes = Base64.decode(byteCodeString, Base64.DEFAULT)
// Create a new bitmap object
        val imageBitmap = BitmapFactory.decodeStream(ByteArrayInputStream(imageBytes))
        holder.CropImage.setImageBitmap(imageBitmap);
    }
    override fun getItemCount(): Int {
        println(cropsList.size);
        println("heloooooooooooooooooo")
        return cropsList.size;
    }
}