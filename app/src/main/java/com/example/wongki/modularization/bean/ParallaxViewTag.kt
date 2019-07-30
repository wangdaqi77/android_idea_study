package com.example.wongki.modularization.bean

class ParallaxViewTag {
    //绑定每一个view对应的是哪一个下标的
     var index: Int = 0
    //x轴进入的速度
     var xIn: Float = 0.toFloat()
     var xOut: Float = 0.toFloat()
     var yIn: Float = 0.toFloat()
     var yOut: Float = 0.toFloat()
     var alphaIn: Float = 0.toFloat()
     var alphaOut: Float = 0.toFloat()
}