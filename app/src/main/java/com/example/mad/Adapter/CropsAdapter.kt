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

class CropsAdapter(CropModel:List<CropModel>,internal var context: Context): RecyclerView.Adapter<CropsAdapter.CropsViewHolder>() {

    internal var croplist:List<CropModel> = ArrayList()
    init{

        this.croplist=CropModel;
    }

    inner class CropsViewHolder(view: View): RecyclerView.ViewHolder(view){

        var cropId: TextView =view.findViewById(R.id.cropid)
        var cropName: TextView =view.findViewById(R.id.cropname)
        var cropRegion: TextView =view.findViewById(R.id.cropregion)
        var cropPrice: TextView =view.findViewById(R.id.cropprice)
        var cropimage: ImageView =view.findViewById(R.id.cropimage)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CropsViewHolder {

        val view= LayoutInflater.from(context).inflate(R.layout.read_crop,parent,false)

        return CropsViewHolder(view)
    }

    override fun onBindViewHolder(holder: CropsViewHolder, position: Int) {

        val crops=croplist[position]

        holder.cropId.text=crops.id.toString()
        holder.cropName.text=crops.cropName
        holder.cropRegion.text=crops.cropRegion
        holder.cropPrice.text=crops.cropPrice;


        // Example base64-encoded byte code string
        val byteCodeString = crops.cropImage

        // Decode the byte code string into a byte array
        val imageBytes = Base64.decode(byteCodeString, Base64.DEFAULT)

        // Create a new bitmap object
        val imageBitmap = BitmapFactory.decodeStream(ByteArrayInputStream(imageBytes))

        holder.cropimage.setImageBitmap(imageBitmap);
    }

    override fun getItemCount(): Int {

        println(croplist.size);
        //println("heloooooooooooooooooo")
        return croplist.size;
    }
}