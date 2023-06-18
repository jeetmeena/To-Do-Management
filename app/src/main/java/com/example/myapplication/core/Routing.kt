package com.example.myapplication.core

import android.os.Bundle

interface Routing {
    fun executePage(id:String,bundle: Bundle=Bundle())
    fun  executeFun(id:String,bundle: Bundle= Bundle())
}