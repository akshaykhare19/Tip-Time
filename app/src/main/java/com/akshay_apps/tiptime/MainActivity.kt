package com.akshay_apps.tiptime

import android.content.Context
import android.os.Bundle
import android.view.KeyEvent
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AppCompatActivity
import com.akshay_apps.tiptime.databinding.ActivityMainBinding
import java.text.NumberFormat

class MainActivity : AppCompatActivity() {

    //  Used for binding the views
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.calculate.setOnClickListener { calculateTip() }
        binding.costOfServiceEditText.setOnKeyListener { view, keyCode, _ ->
            handleKeyEvent(
                view,
                keyCode
            )
        }

    }

    //  Function to calculate tip
    private fun calculateTip() {

        val stringInTextField = binding.costOfServiceEditText.text.toString()
        val cost = stringInTextField.toDoubleOrNull()
        if (cost == null || cost == 0.0) {
            displayTip(0.0)
            return
        }

        val tipPercentage = when (binding.options.checkedRadioButtonId) {
            R.id.option_fifteen_percent -> 0.15
            R.id.option_eighteen_percent -> 0.18
            else -> 0.20
        }

        var tip = tipPercentage * cost
        if (binding.roundUpSwitch.isChecked) tip = kotlin.math.ceil(tip)

        displayTip(tip)


    }

    private fun displayTip(num: Double) {
        val formattedTip = NumberFormat.getCurrencyInstance().format(num)
        binding.tipAmount.text = getString(R.string.tip_amount, formattedTip)
    }

    private fun handleKeyEvent(view: View, keyCode: Int): Boolean {
        if (keyCode == KeyEvent.KEYCODE_ENTER) {
            //Hide the keyboard
            val inputMethodManager =
                getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
            return true
        }
        return false
    }


}