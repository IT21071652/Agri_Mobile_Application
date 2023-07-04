package com.example.mad.Model

import android.media.Image

class CropModel {

    var id:Int=0;
    var cropName:String="";
    var cropRegion:String="";
    var cropPrice:String="";
    var cropImage:String="";

    constructor()

    constructor(cropName: String, cropRegion: String, cropPrice: String,cropImage: String) {
        this.cropName = cropName
        this.cropRegion = cropRegion
        this.cropPrice = cropPrice
        this.cropImage = cropImage
    }

    constructor(id: Int, cropName: String, cropRegion: String, cropPrice: String) {
        this.id = id
        this.cropName = cropName
        this.cropRegion = cropRegion
        this.cropPrice = cropPrice
    }

    constructor(id: Int, cropName: String, cropRegion: String, cropPrice: String, cropImage: String) {
        this.id = id
        this.cropName = cropName
        this.cropRegion = cropRegion
        this.cropPrice = cropPrice
        this.cropImage = cropImage
    }
}