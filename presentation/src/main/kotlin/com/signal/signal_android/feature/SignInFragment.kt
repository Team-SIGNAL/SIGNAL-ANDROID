package com.signal.signal_android.feature

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.signal.signal_android.databinding.FragmentSignInBinding

class SignInFragment : Fragment() {

    private val binding by lazy {
        FragmentSignInBinding.inflate(layoutInflater)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        return binding.root
    }
}
