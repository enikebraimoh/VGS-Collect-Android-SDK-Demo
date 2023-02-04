package com.vgsshow.androiddemo.samples.basic

import android.os.Bundle
import android.util.Log
import android.widget.FrameLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.verygoodsecurity.vgsshow.VGSShow
import com.verygoodsecurity.vgsshow.core.VGSEnvironment
import com.verygoodsecurity.vgsshow.core.listener.VGSOnResponseListener
import com.verygoodsecurity.vgsshow.core.network.client.VGSHttpMethod
import com.verygoodsecurity.vgsshow.core.network.model.VGSRequest
import com.verygoodsecurity.vgsshow.core.network.model.VGSResponse
import com.verygoodsecurity.vgsshow.widget.VGSTextView
import com.vgsshow.androiddemo.R
import java.time.LocalDate

class BasicUsageActivity : AppCompatActivity() {

    companion object {
        private const val VAULT_ID = "tntsgokx54b"
        private val ENVIRONMENT = VGSEnvironment.Sandbox()
        private const val PATH = "/post"
        private val CARD_NUMBER = "tok_sandbox_uTsYqMdZbjd8xgeSAivQxg"
        private val TAG : String = BasicUsageActivity::class.java.simpleName
    }

    val vgsShow = VGSShow.Builder(this, VAULT_ID).setEnvironment(
        VGSEnvironment.Sandbox()
    ).build()

    private val rootView: FrameLayout by lazy { findViewById(R.id.rootView) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_layout)


        // Setup view
        val tvCardNumber = VGSTextView(this)

        rootView.addView(tvCardNumber)

        tvCardNumber.setContentPath("vgs/card_number")
        tvCardNumber.setHint("Fetching card number...")
        tvCardNumber.setHintTextColor(ContextCompat.getColor(this, android.R.color.black))

        tvCardNumber.addOnCopyTextListener(
            object : VGSTextView.OnTextCopyListener {
                override fun onTextCopied(view: VGSTextView, format: VGSTextView.CopyTextFormat) {
                    Log.d(this@BasicUsageActivity::class.java.simpleName, "Text copied: $format")
                }
            }
        )

        Log.d(TAG, "yaaaayy ${tvCardNumber}")

        // Subscribe view
        vgsShow.subscribe(tvCardNumber)

        Log.d(TAG, "omoooo copyy ${VGSTextView.CopyTextFormat.RAW}")

        tvCardNumber.setOnClickListener {
            tvCardNumber.copyToClipboard(
                VGSTextView.CopyTextFormat.RAW
            )
            Log.d(TAG, "copy to clipboard called")

            Log.d(TAG, "copy to clipboard called")
        }


        // Handle response
        vgsShow.addOnResponseListener(object : VGSOnResponseListener {

            override fun onResponse(response: VGSResponse) {
                Log.d(TAG, response.toString())
            }
        })

        // Make request
        vgsShow.requestAsync(
            VGSRequest.Builder(PATH, VGSHttpMethod.POST)
                .body( mapOf(
                    "card_number" to CARD_NUMBER,
                ))
                .build()
        )
    }

    override fun onDestroy() {
        super.onDestroy()
        vgsShow.onDestroy()
    }
}