package com.example.bangkitandroid

class RectangularBox(private val length: Double, private val width: Double, private val height: Double) {

    fun getVolume(): Double {
        return length * width * height
    }

}