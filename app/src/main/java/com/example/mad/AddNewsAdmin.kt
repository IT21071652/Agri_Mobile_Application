package com.example.mad

import android.app.Activity
import android.app.DatePickerDialog
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Base64
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.example.mad.Database.DbHelperArticles
import com.example.mad.Model.NewsModal
import com.example.mad.databinding.ActivityAddNewsAdminBinding
import java.io.ByteArrayOutputStream
import java.text.SimpleDateFormat
import java.util.*

class AddNewsAdmin : AppCompatActivity() {


    private lateinit var binding: ActivityAddNewsAdminBinding
    private val REQUEST_IMAGE_GALLERY=132
    private lateinit var imageFilePath: Uri

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddNewsAdminBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val etStartDateNE = binding.newsDate
        etStartDateNE.isFocusable = false

        val calendar = Calendar.getInstance()
        val dateSetListener = DatePickerDialog.OnDateSetListener { _, year, month, dayOfMonth ->
            calendar.set(Calendar.YEAR, year)
            calendar.set(Calendar.MONTH, month)
            calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)
            val dateFormat = SimpleDateFormat("MM/dd/yyyy", Locale.US)
            etStartDateNE.setText(dateFormat.format(calendar.time))
        }

        etStartDateNE.setOnClickListener {
            val datePickerDialog = DatePickerDialog(
                this@AddNewsAdmin,
                dateSetListener,
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)
            )
            datePickerDialog.show()
        }



        binding.newsuploadImage.setOnClickListener{
            showAlertBox(this)
        }

        binding.button4.setOnClickListener {
            startActivity(Intent(this, NewsReadAdmin::class.java))
        }

        binding.newsSubmit.setOnClickListener{

            var db= DbHelperArticles(this)

            if(!validation()){
                return@setOnClickListener
            }

            val newsTitle = binding.newsType.text.toString()
            val newsDate = binding.newsDate.text.toString()
            val newsDescription = binding.newsDescription.text.toString()
            val newsSiteLink = binding.newsSitelink.text.toString()

            //convert imageview to bitmap
            val bitmap = (binding.newsuploadImage.drawable as BitmapDrawable).bitmap

            //convert bitmap to byte array
            val stream = ByteArrayOutputStream()
            bitmap.compress(Bitmap.CompressFormat.PNG,90,stream)

            val byteFormat= Base64.encodeToString(stream.toByteArray(), Base64.NO_WRAP)

            val news= NewsModal(newsTitle,newsDate,newsDescription,newsSiteLink,byteFormat)

            var success=db.addNews(news)

            if(success===true){
                Toast.makeText(this,"Added Success", Toast.LENGTH_LONG).show()
            }else{
                Toast.makeText(this,"Added UnSuccess", Toast.LENGTH_LONG).show()
            }

            startActivity(Intent(this, NewsReadAdmin::class.java))

        }



    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if(requestCode==REQUEST_IMAGE_GALLERY && resultCode== Activity.RESULT_OK && data!=null){
            binding.newsuploadImage.setImageURI(data.data)
            imageFilePath= data.data!!


        }
        else{
            Toast.makeText(this,"Somthing went wrong", Toast.LENGTH_LONG).show()
        }

        /*startActivity(Intent(this, NewsReadAdmin::class.java))*/
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
        if(binding.newsType.text.isNullOrEmpty()){
            binding.newsType.error="Please Enter the news type"
            return false;
        }
        if(binding.newsDate.text.isNullOrEmpty()){
            binding.newsDate.error="please Enter the date"
            return false
        }
        if(binding.newsDescription.text.isNullOrEmpty()){
            binding.newsDescription.error="Please Enter the content"
            return false
        }
        if(binding.newsSitelink.text.isNullOrEmpty()){
            binding.newsSitelink.error="Please Enter the content"
            return false
        }
        return true
    }

}