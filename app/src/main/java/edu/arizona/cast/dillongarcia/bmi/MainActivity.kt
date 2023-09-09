package edu.arizona.cast.dillongarcia.bmi

import BmiViewModel
import android.graphics.Color
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.textfield.TextInputEditText

class MainActivity : AppCompatActivity() {
    private lateinit var weightEditText: TextInputEditText
    private lateinit var heightEditText: TextInputEditText
    private lateinit var resultTextView: TextView

    // Declare and initialize the ViewModel
    private val viewModel by lazy {
        ViewModelProvider(this).get(BmiViewModel::class.java)
    }

    // ... (Rest of your code)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Initialize UI elements
        weightEditText = findViewById(R.id.weight)
        heightEditText = findViewById(R.id.height)
        resultTextView = findViewById(R.id.result)

        val calculateButton: Button = findViewById(R.id.btnCalculate)
        calculateButton.setOnClickListener {
            // Update ViewModel properties with user input
            viewModel.weight.value = weightEditText.text.toString()
            viewModel.height.value = heightEditText.text.toString()

            // Calculate BMI using ViewModel
            viewModel.calculateBMI()
        }

        val clearButton: Button = findViewById(R.id.btnClear)
        clearButton.setOnClickListener {
            weightEditText.text = null
            heightEditText.text = null
            resultTextView.text= ""
            viewModel.clearData()
            resultTextView.setTextColor(Color.WHITE)
        }

        calculateButton.setOnClickListener {
            val weightStr = weightEditText.text.toString()
            val heightStr = heightEditText.text.toString()

            if (weightStr.isEmpty() || heightStr.isEmpty()) {
                // Display a message to enter both weight and height
                resultTextView.text = "Please enter both weight and height."
                return@setOnClickListener
            }

            // Update ViewModel properties with user input
            viewModel.weight.value = weightStr
            viewModel.height.value = heightStr

            // Calculate BMI using ViewModel
            viewModel.calculateBMI()
        }

        // Observe LiveData properties and update UI elements
        viewModel.bmiResult.observe(this, { result ->
            resultTextView.text = result
        })

        viewModel.bmiCategoryColor.observe(this, { color ->
            resultTextView.setTextColor(color ?: Color.BLACK)
        })
    }
}
