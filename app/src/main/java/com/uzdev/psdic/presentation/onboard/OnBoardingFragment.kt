package com.uzdev.psdic.presentation.onboard

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.uzdev.psdic.R
import com.uzdev.psdic.data.preferences.PreferencesManager
import com.uzdev.psdic.databinding.FragmentOnBoardingBinding

class OnBoardingFragment : Fragment() {

    private var _binding: FragmentOnBoardingBinding? = null
    private val binding get() = _binding!!

    private lateinit var preferencesManager: PreferencesManager

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentOnBoardingBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        preferencesManager = PreferencesManager(requireContext())

        with(binding) {
            val fill = etFillRequired.text.toString()
            val yourLang = etYourLangMean.text.toString()

            btnSave.setOnClickListener {
                if (fill.isNotEmpty() || yourLang.isNotEmpty()) {
                    preferencesManager.apply {
                        fillRequired = fill
                        yourLanguage = yourLang
                    }
                }

            }
            btnNext.setOnClickListener {
                preferencesManager.isIntroduced = true
                findNavController().navigate(R.id.action_onBoardingFragment_to_listFragment)
            }

        }


    }


}