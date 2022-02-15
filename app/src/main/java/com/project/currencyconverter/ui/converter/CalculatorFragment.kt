package com.project.currencyconverter.ui.converter

import android.graphics.PorterDuff
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import com.project.currencyconverter.data.util.Result
import androidx.lifecycle.ViewModelProvider
import com.project.currencyconverter.data.model.converter.ConverterRequest
import com.project.currencyconverter.data.util.Progress
import com.project.currencyconverter.databinding.FragmentCalculatorBinding
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager

import com.skydoves.powerspinner.OnSpinnerItemSelectedListener

import com.skydoves.powerspinner.DefaultSpinnerAdapter


class CalculatorFragment : Fragment() {

    private lateinit var converterViewModel: ConverterViewModel
    private var _binding: FragmentCalculatorBinding? = null
    private lateinit var progress: Progress

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        converterViewModel =
            ViewModelProvider(this)[ConverterViewModel::class.java]

        _binding = FragmentCalculatorBinding.inflate(inflater, container, false)
        val root: View = binding.root


        progress = Progress(requireContext())

        val currencyList: MutableList<String> = ArrayList()
        currencyList.add("EUR")
        currencyList.add("AED")
        currencyList.add("ALL")
        currencyList.add("AMD")
        currencyList.add("PLN")
        currencyList.add("USD")


        //first spinner implementation
        val currencyOneAdapter = DefaultSpinnerAdapter(binding.currencyOne)
        currencyOneAdapter.setItems(currencyList)
        binding.currencyOne.setSpinnerAdapter(currencyOneAdapter)
        binding.currencyOne.setOnSpinnerItemSelectedListener(
            OnSpinnerItemSelectedListener { oldIndex: Int, oldItem: String?, newIndex: Int, newItem: String? ->
                binding.firstCurrencyCode.text = newItem
            })

        binding.currencyOne.setOnClickListener { v ->
            binding.currencyOne.showOrDismiss()
            binding.currencyTwo.dismiss()
        }

        binding.currencyOne.dismissWhenNotifiedItemSelected = true

        binding.currencyOne.getSpinnerRecyclerView().layoutManager =
            LinearLayoutManager(requireContext())

        //second spinner implementation
        val currencyTwoAdapter = DefaultSpinnerAdapter(binding.currencyTwo)
        currencyTwoAdapter.setItems(currencyList)
        binding.currencyTwo.setSpinnerAdapter(currencyOneAdapter)
        binding.currencyTwo.setOnSpinnerItemSelectedListener(OnSpinnerItemSelectedListener { oldIndex: Int, oldItem: String?, newIndex: Int, newItem: String? ->
            binding.secondCurrencyCode.text = newItem
        })

        binding.currencyTwo.setOnClickListener { v ->
            binding.currencyTwo.showOrDismiss()
            binding.currencyOne.dismiss()
        }

        binding.currencyTwo.dismissWhenNotifiedItemSelected = true

        binding.currencyTwo.getSpinnerRecyclerView().layoutManager =
            LinearLayoutManager(requireContext())


        binding.convert.setOnClickListener {
            converter()
        }

        return root
    }

    private fun converter() {
        try {
            when {
                binding.firstCurrencyValue.text!!.isEmpty() -> {
                    binding.firstCurrencyValue.error = "This field is required!"
                }
                else -> {
                    converterViewModel.convertNow(
                        ConverterRequest(
                            binding.firstCurrencyCode.text.toString().trim(),
                            binding.secondCurrencyCode.text.toString().trim(),
                            binding.firstCurrencyValue.text.toString().trim()
                        )
                    )

                    converterViewModel.converterResponse.observe(viewLifecycleOwner,
                        { event ->
                            event.getContentIfNotHandled()?.let { response ->
                                when (response) {
                                    is Result.Success -> {
                                        progress.dismiss()
                                        response.data?.let {
                                            binding.secondCurrencyValue.setText(
                                                it.result.toString(),
                                                TextView.BufferType.EDITABLE
                                            )
                                        }

                                    }

                                    is Result.Error -> {
                                        progress.dismiss()
                                        response.message?.let { message ->
                                            toast(message)
                                        }
                                    }

                                    is Result.Loading -> {
                                        progress.show()
                                    }
                                }
                            }
                        })
                }
            }

        }catch(e: Exception) {
            e.printStackTrace()
        }
        }

    private fun toast(message: String) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT)
            .show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}