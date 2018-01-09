package com.pyoinsoo.kotlin.design.support

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context


/**
 * Created by pyoinsoo on 2017-12-17
 * insoo.pyo@gmail.com
 */
class GirlsApplication : Application() {

    companion object {
        @SuppressLint("StaticFieldLeak")
        lateinit  var girlsContext : Context
    }
    override fun onCreate(){
        super.onCreate()
        girlsContext = this
    }
}