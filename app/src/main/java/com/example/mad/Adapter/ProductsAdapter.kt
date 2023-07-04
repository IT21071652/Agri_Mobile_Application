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
import com.example.mad.Model.ProductModel
import com.example.mad.R
import java.io.ByteArrayInputStream

class ProductsAdapter(ProductModel:List<ProductModel>, internal var context: Context): RecyclerView.Adapter<ProductsAdapter.ProductsViewHolder>() {

    internal var productlist:List<ProductModel> = ArrayList()
    init{

        this.productlist=ProductModel;

    }

    inner class ProductsViewHolder(view: View): RecyclerView.ViewHolder(view){

        var productId: TextView =view.findViewById(R.id.productid)
        var productName: TextView =view.findViewById(R.id.productname)
        var productRegion: TextView =view.findViewById(R.id.productregion)
        var productPrice: TextView =view.findViewById(R.id.productprice)
        var productimage: ImageView =view.findViewById(R.id.productimage)


    }



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductsViewHolder {

        val view= LayoutInflater.from(context).inflate(R.layout.read_product,parent,false)

        return ProductsViewHolder(view)
    }

    override fun onBindViewHolder(holder: ProductsViewHolder, position: Int) {

        val products=productlist[position]

        holder.productId.text=products.id.toString()
        holder.productName.text=products.productName
        holder.productRegion.text=products.productRegion
        holder.productPrice.text=products.productPrice;



        // Example base64-encoded byte code string
        val byteCodeString = products.productImage

// Decode the byte code string into a byte array
        val imageBytes = Base64.decode(byteCodeString, Base64.DEFAULT)

// Create a new bitmap object
        val imageBitmap = BitmapFactory.decodeStream(ByteArrayInputStream(imageBytes))


        //println(users.email);
        // println(users.image);

        holder.productimage.setImageBitmap(imageBitmap);


    }
    override fun getItemCount(): Int {

        println(productlist.size);
        println("heloooooooooooooooooo")
        return productlist.size;

    }
}