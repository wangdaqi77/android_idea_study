package com.example.wongki.commonlib.obersever

abstract class HasParamHasReturnFunction< in P, out R>(override var functionName: String) : AbsFunction {
    abstract fun invoke(param: P): R
}