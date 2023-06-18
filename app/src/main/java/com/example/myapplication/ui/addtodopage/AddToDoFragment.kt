package com.example.myapplication.ui.addtodopage

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import com.example.myapplication.core.AppDataBase
import com.example.myapplication.core.Routing
import com.example.myapplication.databinding.FragmentAddToDoBinding
import com.example.myapplication.ui.addtodopage.view.AddToDoView
import com.example.myapplication.viewmodel.AddToDoViewModel


class AddToDoFragment : Fragment() {

    companion object {
        fun newInstance() = AddToDoFragment()
    }
    private val viewModel: AddToDoViewModel by viewModels { AddToDoViewModel.Factory }
    //private lateinit var viewModel: AddToDoViewModel
    private var _binding: FragmentAddToDoBinding? = null
    private val binding get() = _binding!! // Helper Property
    private val dataBase by lazy {
        AppDataBase.getDatabase(this.requireContext())
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        //viewModel = ViewModelProvider(this).get(AddToDoViewModel::class.java)
        try {
            viewModel.handler=context as Routing
        }catch (e:Exception){}
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
         _binding = FragmentAddToDoBinding.inflate(inflater, container, false)
        return binding.root//inflater.inflate(R.layout.fragment_add_to_do, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        // TODO: Use the ViewModel
    }

     override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
         viewModel.getData()
        viewModel.taskDataMLiveData.observe(viewLifecycleOwner,AddToDoView(requireContext(),binding,viewModel.stateUpdater))
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}