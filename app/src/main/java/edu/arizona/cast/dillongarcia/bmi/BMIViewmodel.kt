
import android.graphics.Color
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel


class BmiViewModel : ViewModel() {
    // Define LiveData properties to store UI data
    val weight = MutableLiveData<String>()
    val height = MutableLiveData<String>()
    val bmiResult = MutableLiveData<String>()
    val bmiCategoryColor = MutableLiveData<Int>()

    fun clearData() {
        weight.value = null
        height.value = null
        bmiResult.value = null
        bmiCategoryColor.value = null
    }

    // Function to calculate BMI and update LiveData properties
    fun calculateBMI() {
        val weightValue = weight.value?.toDoubleOrNull() ?: return
        val heightValue = height.value?.toDoubleOrNull() ?: return

        val bmi = calculateBMIValue(weightValue, heightValue)
        val roundedBMI = String.format("%.1f", bmi)

        // Determine BMI category and text color
        val category = calculateBMICategory(bmi)
        val textColor = calculateBMICategoryColor(category)

        // Update LiveData properties
        bmiResult.postValue("BMI: $roundedBMI\n$category")
        bmiCategoryColor.postValue(textColor)
    }

    // Helper function to calculate BMI value
    private fun calculateBMIValue(weight: Double, height: Double): Double {
        return weight * 703 / (height * height)
    }

    // Helper function to calculate BMI category
    private fun calculateBMICategory(bmi: Double): String {
        return when {
            bmi < 18.5 -> "Underweight"
            bmi >= 18.5 && bmi < 24.9 -> "Normal"
            bmi >= 24.9 && bmi < 30 -> "Overweight"
            else -> "Obese"
        }
    }

    // Helper function to calculate BMI category color
    private fun calculateBMICategoryColor(category: String?): Int {
        return when (category) {
            "Underweight" -> Color.YELLOW
            "Normal" -> Color.GREEN
            "Overweight" -> Color.YELLOW
            else -> Color.RED
        }
    }
}