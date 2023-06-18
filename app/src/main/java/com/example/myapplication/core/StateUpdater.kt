package com.example.myapplication.core

import android.os.Bundle

abstract class StateUpdater {
    abstract val handler:Routing
    fun processPage(id: String){
        handler.executePage(id)
    }
    fun processFun(id: String,bundle: Bundle=Bundle()){
        handler.executeFun(id,bundle)
    }

}