package com.example.myapplication.ui.todolistpage

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.example.myapplication.R
import com.example.myapplication.adapter.TaskItemAdapter
import com.example.myapplication.databinding.ToDoListPageBinding
import com.example.myapplication.ui.todolistpage.view.ToDoListView
import com.example.myapplication.viewmodel.AddToDoViewModel
import com.example.myapplication.viewmodel.MainViewModel

class ToDoListFragment : Fragment() {

    companion object {
        fun newInstance() = ToDoListFragment()
    }
    private val viewModel: MainViewModel by viewModels { MainViewModel.Factory }
    //private lateinit var viewModel: MainViewModel
    private var _binding: ToDoListPageBinding? = null
    private val binding get() = _binding!! // Helper Property
    private var taskItemAdapter:TaskItemAdapter?=null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = ToDoListPageBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
       // viewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        // TODO: Use the ViewModel
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.todoListMLiveData.observe(viewLifecycleOwner, ToDoListView(viewModel.stateUpdater,binding))
    }




    override fun onDestroy() {
        super.onDestroy()
        _binding=null
    }
}