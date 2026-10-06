package com.example.mad

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.mad.Database.DbHelperCrop
import com.example.mad.Model.CropModel
import com.example.mad.databinding.ActivityUpdateCropsAdminBinding

class UpdateCropsAdmin : AppCompatActivity() {

    private lateinit var binding: ActivityUpdateCropsAdminBinding
    var crop: CropModel = CropModel();
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUpdateCropsAdminBinding.inflate(layoutInflater)
        setContentView(binding.root)
//fetch data
        val value = intent.getStringExtra("id")
        val id = value!!.toInt()
        val db = DbHelperCrop(this)

        crop = db.getCrop(id)

        binding.cropTypeEdit.setText(crop.cropName)
        binding.cropregionEdit.setText(crop.cropRegion)
        binding.croppriceedit.setText(crop.cropPrice)

        //update
        binding.cropUpdate.setOnClickListener {

            var title = binding.cropTypeEdit.text.toString()
            var date = binding.cropregionEdit.text.toString()
            var discription = binding.croppriceedit.text.toString()

            crop = CropModel(id, title, date, discription)

            var success = db.updateCrop(crop)

            if (success == true) {
                Toast.makeText(this, "Update Succesfully", Toast.LENGTH_LONG).show()
                startActivity(Intent(this, cropreadadmin::class.java))
            } else {
                Toast.makeText(this, "Update Unsuccesfully", Toast.LENGTH_LONG).show()
                startActivity(Intent(this, cropreadadmin::class.java))
            }

        }

    }
}