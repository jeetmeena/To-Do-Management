package com.example.myapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.example.myapplication.core.Routing
import com.example.myapplication.ui.todolistpage.ToDoListFragment
import com.example.myapplication.util.FunctionId

class MainActivity : AppCompatActivity(),Routing {
    private lateinit var navController: NavController
    private lateinit var navHostFragment: NavHostFragment
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        /*if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, ToDoListFragment.newInstance())
                .commitNow()
        }*/
        navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        navController = navHostFragment.navController

    }

    override fun executePage(id: String, bundle: Bundle) {
        TODO("Not yet implemented")
    }

    override fun executeFun(id: String, bundle: Bundle) {
        when(id){
            FunctionId.BACK_PRESS.value->navController.navigateUp()
        }
    }
}