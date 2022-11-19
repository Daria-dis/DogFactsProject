package com.dasha.dogfactsproject.ui.fragment

import android.opengl.Visibility
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.widget.SwitchCompat
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.viewModelScope
import androidx.navigation.fragment.findNavController
import com.dasha.dogfactsproject.R
import com.dasha.dogfactsproject.data.internet.DogsFactsApi
import com.dasha.dogfactsproject.data.internet.RetrofitClient
import com.dasha.dogfactsproject.databinding.MenuFragmentBinding
import com.dasha.dogfactsproject.ui.adapter.FactsAdapter
import com.dasha.dogfactsproject.ui.viewmodel.ViewModel
import kotlinx.coroutines.launch

class MenuFragment : Fragment() {

    private lateinit var binding: MenuFragmentBinding
    private lateinit var internetSwitch: SwitchCompat
    private lateinit var randomFactButton: Button
    private lateinit var getAllFactsButton: Button
    private lateinit var countAllFactsButton: Button
    private lateinit var numberOfFactsToShow: EditText
    private lateinit var countedFactsAmountTextView: TextView
    private lateinit var emptyListImage: ImageView

    lateinit var viewModel: ViewModel

    private var facts: List<String> = emptyList()

    private val adapter = FactsAdapter { pos ->
        findNavController().navigate(
            MenuFragmentDirections
                .toFactFragment(facts[pos])
        )
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = MenuFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        initViews()
        val context = this.requireContext()
        numberOfFactsToShow.setText("1")

        randomFactButton.setOnClickListener {
            binding.countAllFactsTextView.text = ""
            emptyListImage.visibility = View.INVISIBLE

            viewModel.viewModelScope.launch {

                try {
                    val factsAmount = numberOfFactsToShow.text.toString().toInt()
                    facts = if (internetSwitch.isChecked) {
                        viewModel.getRandomFactsFromInternet(context, factsAmount)
                    } else {
                        viewModel.getRandomFactsFromDatabase(context, factsAmount)
                    }
                    adapter.setList(facts)
                    if(facts.isEmpty()){
                        emptyListImage.visibility = View.VISIBLE
                    }

                } catch (exception: Exception) {
                    Toast.makeText(context, "Enter positive integer!", Toast.LENGTH_SHORT).show()
                }

            }
        }

        getAllFactsButton.setOnClickListener {
            binding.countAllFactsTextView.text = ""
            emptyListImage.visibility = View.INVISIBLE
            viewModel.viewModelScope.launch {
                facts = viewModel.getAllSavedFacts(context)
                if (facts.isEmpty()){
                    emptyListImage.visibility = View.VISIBLE
                }
                adapter.setList(facts)
            }

        }

        countAllFactsButton.setOnClickListener {
            binding.numberOfFactsToShow.setText("1")
            viewModel.viewModelScope.launch {
                var factsAmount = 0
                factsAmount = viewModel.countFactsInDatabase(context)
                countedFactsAmountTextView.text = factsAmount.toString()
            }
        }

        super.onViewCreated(view, savedInstanceState)

    }

    private fun initViews() {
        binding.recyclerView.adapter = adapter
        internetSwitch = binding.internetSwitch
        internetSwitch.setText(R.string.use_internet)

        randomFactButton = binding.getRandomFactButton
        getAllFactsButton = binding.getAllFactsButton
        countAllFactsButton = binding.countAllFactsButton

        countedFactsAmountTextView = binding.countAllFactsTextView
        viewModel = ViewModelProvider(this)[ViewModel::class.java]

        numberOfFactsToShow = binding.numberOfFactsToShow
        numberOfFactsToShow.setText("1")

        countedFactsAmountTextView = binding.countAllFactsTextView


        emptyListImage = binding.emptyListImage
        emptyListImage.visibility = View.INVISIBLE
    }

}