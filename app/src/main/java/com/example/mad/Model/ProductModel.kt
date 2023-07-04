package com.example.mad.Model

class ProductModel {

    var id:Int=0;
    var productName:String="";
    var productRegion:String="";
    var productPrice:String="";
    var productImage:String="";

    constructor(id: Int, productName: String, productRegion: String, productPrice: String, productImage: String) {
        this.id = id
        this.productName = productName
        this.productRegion = productRegion
        this.productPrice = productPrice
        this.productImage = productImage
    }

    constructor(productName: String, productRegion: String, productPrice: String,productImage: String) {
        this.productName = productName
        this.productRegion = productRegion
        this.productPrice = productPrice
        this.productImage = productImage
    }


    constructor()
    constructor(id: Int, productName: String, productRegion: String, productPrice: String) {
        this.id = id
        this.productName = productName
        this.productRegion = productRegion
        this.productPrice = productPrice
    }


}