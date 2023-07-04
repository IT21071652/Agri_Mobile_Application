package com.example.mad

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Base64
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.example.mad.Database.DbHelperCrop
import com.example.mad.Model.CropModel
import kotlinx.android.synthetic.main.activity_add_crop.*
import java.io.ByteArrayOutputStream

class AddCrop : AppCompatActivity() {

    private val REQUEST_IMAGE_GALLERY = 132
    private lateinit var imageFilePath:Uri

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_crop)

        cropImage.setOnClickListener {
            showAlertBox(this)
        }

        addCrop.setOnClickListener {

            val db = DbHelperCrop(this);

            if(!validation()){
                return@setOnClickListener
            }

            //val image=uploadImg.get
            val cropName = cropType.text.toString();
            val cropRegion = cropRegion.text.toString();
            val cropPrice = cropPrice.text.toString();


            //convert imageView to bitmap
            val bitmap = (cropImage.drawable as BitmapDrawable).bitmap

            //convert bitmap to byte array
            val stream = ByteArrayOutputStream()
            bitmap.compress(Bitmap.CompressFormat.PNG, 90, stream)

            // convert byte array to string
            val byteFormat = Base64.encodeToString(stream.toByteArray(), Base64.NO_WRAP)

            //println(firstname);
            println(byteFormat);

            val crop = CropModel(cropName, cropRegion, cropPrice,byteFormat)

            val success = db.addCrop(crop);

            if (success == true) {

                Toast.makeText(this, "Data inserted", Toast.LENGTH_LONG).show()
                //startActivity(Intent(this, JobsAdminRead::class.java))
                startActivity(Intent(this, cropreadadmin::class.java))

            } else {
                Toast.makeText(this, "Data not inserted", Toast.LENGTH_LONG).show()
                //startActivity(Intent(this, JobsAdminRead::class.java))
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if(requestCode==REQUEST_IMAGE_GALLERY && resultCode== Activity.RESULT_OK && data!=null){
            cropImage.setImageURI(data.data)
            imageFilePath= data.data!!

        }
        else{
            Toast.makeText(this,"Somthing went wrong", Toast.LENGTH_LONG).show()
        }
    }

    fun showAlertBox(context: Context) {


        val builder = AlertDialog.Builder(context)
        builder.setTitle("Select Image")
        builder.setMessage("Choose your Option")
        builder.setPositiveButton("Gallery"){
                dialog,which->dialog.dismiss()

            val intent= Intent(Intent.ACTION_PICK)
            intent.type="image/*"

            startActivityForResult(intent,REQUEST_IMAGE_GALLERY)
        }
        builder.setNegativeButton("Camera"){
                dialog,which->dialog.dismiss()

        }
        val dialog = builder.create()
        dialog.show()
    }

    private fun validation():Boolean{
        if(cropType.text.isNullOrEmpty()){
            cropType.error="Please Enter the Crop Name"
            return false;
        }
        if(cropRegion.text.isNullOrEmpty()){
            cropRegion.error="please Enter the Region"
            return false
        }
        if(cropPrice.text.isNullOrEmpty()){
            cropPrice.error="Please Enter the Price"
            return false
        }
        return true
    }
}