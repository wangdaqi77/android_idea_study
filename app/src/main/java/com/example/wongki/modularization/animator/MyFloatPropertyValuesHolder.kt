package com.example.wongki.modularization.animator

class MyFloatPropertyValuesHolder : MyPropertyValuesHolder {
    constructor(propertyName: String, vararg values: Float) : super(propertyName) {
        setFloatValues(*values)
    }


     override fun setFloatValues(vararg values: Float) {
        super.setFloatValues(*values)
    }
}