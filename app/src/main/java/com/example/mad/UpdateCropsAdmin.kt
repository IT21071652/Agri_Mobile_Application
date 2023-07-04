package com.example.mad

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.mad.Database.DbHelperCrop
import com.example.mad.Model.CropModel
import kotlinx.android.synthetic.main.activity_update_crops_admin.*

class UpdateCropsAdmin : AppCompatActivity() {

    var crop: CropModel = CropModel();
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_update_crops_admin)
//fetch data
        val value = intent.getStringExtra("id")
        val id = value!!.toInt()
        val db = DbHelperCrop(this)

        crop = db.getCrop(id)

        cropTypeEdit.setText(crop.cropName)
        cropregionEdit.setText(crop.cropRegion)
        croppriceedit.setText(crop.cropPrice)

        //update
        cropUpdate.setOnClickListener {

            var title = cropTypeEdit.text.toString()
            var date = cropregionEdit.text.toString()
            var discription = croppriceedit.text.toString()

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