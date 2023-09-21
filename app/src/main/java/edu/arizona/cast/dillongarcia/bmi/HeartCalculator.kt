package edu.arizona.cast.dillongarcia.bmi

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider


class HeartCalculator : AppCompatActivity() {

    private val viewModel: HeartRateViewModel by lazy {
        ViewModelProvider(this).get(HeartRateViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_heart_calculator)

        // Calculate heart rates based on user input (age)
        val ageEditText = findViewById<EditText>(R.id.age)
        val heartRateTextView = findViewById<TextView>(R.id.heartRateTextView)
        val calculateButton = findViewById<Button>(R.id.btnCalculateHeart)
        val clearButton = findViewById<Button>(R.id.btnClear)
        val backbttn = findViewById<Button>(R.id.backbttn)

        calculateButton.setOnClickListener {
            val ageStr = ageEditText.text.toString()

            if (ageStr.isNotEmpty()){
                val age = ageStr.toInt()

                //val sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
                //val editor = sharedPreferences.edit()
                //editor.putInt("userAge", age)
                //editor.apply()

                viewModel.age = age

                viewModel.calculateHeartRate()

                val maxHeartRate = viewModel.maxHeartRate
                val heartRateHigh = viewModel.heartRateHigh
                val heartRateLow = viewModel.heartRateLow

                val resultText = "Maximum: $maxHeartRate\nTarget: $heartRateHigh High End - $heartRateLow Low End"
                heartRateTextView.text = resultText
            }
            else
            {
                Toast.makeText(this, "Please enter your age.", Toast.LENGTH_SHORT).show()
            }
        }

        clearButton.setOnClickListener {
            ageEditText.text.clear()

            heartRateTextView.text = ""

            viewModel.resultText = ""
        }

        backbttn.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

        viewModel.resultText?.let {
                resultText -> heartRateTextView.text = resultText
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        // Save the resultText in the ViewModel when the activity is paused
        viewModel.resultText = findViewById<TextView>(R.id.heartRateTextView).text.toString()
    }
}
