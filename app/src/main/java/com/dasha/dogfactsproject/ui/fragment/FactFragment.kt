package com.dasha.dogfactsproject.ui.fragment

import android.media.Image
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.viewModelScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.dasha.dogfactsproject.databinding.FactFragmentBinding
import com.dasha.dogfactsproject.ui.viewmodel.ViewModel
import kotlinx.coroutines.launch

class FactFragment : Fragment() {

    private lateinit var binding: FactFragmentBinding

    private lateinit var factTextView: TextView
    private lateinit var deleteFactFromDatabaseButton: ImageView
    private lateinit var viewModel: ViewModel
    private lateinit var backButton: ImageView

    val args: FactFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FactFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        factTextView = binding.factTextView
        deleteFactFromDatabaseButton = binding.deleteButton
        backButton = binding.backButton
        viewModel = ViewModel()

        val fact = args.fact

        factTextView.text = fact

        if (fact.contains("dalmatian", true)) {
            printImage(view, "https://cdn.britannica.com/47/236047-050-F06BFC5E/Dalmatian-dog.jpg")
        } else if (fact.contains("labrador", true)) {
            printImage(
                view,
                "https://upload.wikimedia.org/wikipedia/commons/thumb/3/34/Labrador_on_Quantock_%282175262184%29.jpg/640px-Labrador_on_Quantock_%282175262184%29.jpg"
            )
        } else if (fact.contains("lundehune", true)) {
            printImage(
                view,
                "http://cdn.akc.org/content/hero/Norwegian_Lundehund_Hero.jpg?cachebuster:62"
            )
        } else {
            printImage(view, "https://publish.purewow.net/wp-content/uploads/sites/2/2021/02/24-rare-dog-breeds.jpg")
        }

        deleteFactFromDatabaseButton.setOnClickListener {
            viewModel.viewModelScope.launch {
                viewModel.deleteFactFromDatabase(requireContext(), fact)
            }
            findNavController().navigate(FactFragmentDirections.toMenuFragment())
        }

        backButton.setOnClickListener {
            findNavController().popBackStack()
        }
    }

    private fun printImage(view: View, url: String) {
        Glide.with(view)
            .load(url)
            .centerCrop()
            .placeholder(binding.dogImageView.drawable)
            .into(binding.dogImageView)
    }
}