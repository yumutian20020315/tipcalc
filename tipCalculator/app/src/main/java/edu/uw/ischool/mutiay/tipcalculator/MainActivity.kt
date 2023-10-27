package edu.uw.ischool.mutiay.tipcalculator

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.AdapterView
import android.widget.EditText
import android.widget.Button
import android.widget.Spinner
import android.widget.Toast
import android.view.View

class MainActivity : AppCompatActivity() {
    private lateinit var amount: EditText
    private lateinit var tip: Button
    private lateinit var tipPercentageSpinner: Spinner
    private var selectedTipPercentage: Double = 0.15

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        amount = findViewById(R.id.Amount)
        tip = findViewById(R.id.Tip)
        tipPercentageSpinner = findViewById(R.id.tipPercentageSpinner)
        tipPercentageSpinner.setSelection(1)


        val watcher = object: TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                val str = s.toString().replace("$", "")
                val decimalIndex = str.indexOf(".")

                if (decimalIndex != -1) {
                    val decimalPart = str.substring(decimalIndex + 1)
                    if (decimalPart.length > 2) {
                        amount.setText(amount.text.substring(0,amount.text.length - 1))
                        amount.setSelection(amount.text.length)
                    }
                }

                tip.isEnabled = str.isNotEmpty()
            }

            override fun afterTextChanged(s: Editable?) {
                if (s.isNullOrEmpty()) return

                if (!s.toString().startsWith("$")) {
                    amount.setText("$${s.toString()}")
                    amount.setSelection(amount.text.length)
                }
            }
        }

        tipPercentageSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {
                val selectedItem = parent.getItemAtPosition(position).toString().replace("%", "").toDouble()
                selectedTipPercentage = selectedItem / 100
            }

            override fun onNothingSelected(parent: AdapterView<*>) {
            }
        }

        tip.setOnClickListener {
            val currentAmount = amount.text.toString().replace("$", "").toDoubleOrNull() ?: 0.0
            val tipValue = currentAmount * selectedTipPercentage
            val formattedTip = String.format("%.2f", tipValue)
            Toast.makeText(this, "Tip: $$formattedTip", Toast.LENGTH_SHORT).show()
        }


        amount.addTextChangedListener(watcher)
    }
}