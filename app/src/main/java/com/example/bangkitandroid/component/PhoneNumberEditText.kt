package com.example.bangkitandroid.component

import android.content.Context
import android.text.Editable
import android.text.TextWatcher
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatEditText
import com.bumptech.glide.Glide.init

class PhoneNumberEditText: AppCompatEditText {
    constructor(context: Context) : super(context) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    ) {
        init()
    }

    private fun init() {
        addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                val isValidPhoneNumber = isValidNumberPhone(s.toString())
                if (isValidPhoneNumber) {
                    error = null
                } else {
                    showEmailError()
                }

            }
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        })
    }
    private fun isValidNumberPhone(phoneNumber: String): Boolean {
        // Kriteria validasi nomor telepon
        val validNumberPhoneRegex = Regex("^0\\d{10,12}\$")

        return validNumberPhoneRegex.matches(phoneNumber)
    }

    private fun showEmailError() {
        error = "Format Nomor Telephone Salah"
    }
}