package com.example.memberlist.ui.webview

import android.annotation.SuppressLint
import android.os.Bundle
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.ImageButton
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.memberlist.R

class WebViewActivity : AppCompatActivity() {
    private lateinit var webView: WebView
    private lateinit var titleTextView: TextView
    private lateinit var closeButton: ImageButton

    @SuppressLint("SetJavaScriptEnabled")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_webview)

        webView = findViewById(R.id.web_view)
        titleTextView = findViewById(R.id.title_text_view)
        closeButton = findViewById(R.id.close_button)

        val url = intent.getStringExtra(EXTRA_URL) ?: ""

        setupWebView(url)
        setupCloseButton()
    }

    private fun setupWebView(url: String) {
        webView.settings.javaScriptEnabled = true
        webView.webViewClient = object : WebViewClient() {
            override fun onPageFinished(view: WebView?, url: String?) {
                super.onPageFinished(view, url)
                titleTextView.text = view?.title
            }
        }
        webView.loadUrl(url)
    }

    private fun setupCloseButton() {
        closeButton.setOnClickListener {
            finish()
        }
    }

    companion object {
        const val EXTRA_URL = "url"
    }
}