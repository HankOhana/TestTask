package com.example.tiprankstask.ui

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import android.webkit.WebChromeClient
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.example.tiprankstask.R
import com.example.tiprankstask.databinding.FragmentPostItemBinding

class PostItemFragment : Fragment(R.layout.fragment_post_item) {

    private val args: PostItemFragmentArgs by navArgs()
    lateinit var viewBinding: FragmentPostItemBinding


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewBinding = FragmentPostItemBinding.bind(view)
        setupView()
    }

    @SuppressLint("SetJavaScriptEnabled")
    private fun setupView() {
        viewBinding.webView.apply {
            settings.javaScriptEnabled = true
            webViewClient = object : WebViewClient() {
                override fun onPageFinished(view: WebView?, url: String?) {
                    viewBinding.contentProgress.hide()
                }
            }
            webChromeClient = object : WebChromeClient() {
                override fun onProgressChanged(view: WebView?, newProgress: Int) {
                    viewBinding.contentProgress.show()
                    viewBinding.contentProgress.progress = newProgress
                }
            }
            args.StringPostLinkArgument?.let { loadUrl(it) }
        }
    }
}