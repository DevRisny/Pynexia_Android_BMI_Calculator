package com.risny.bmicalculator

import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import java.util.Locale

/**
 * BMI Calculator - Pynexia Android Development Internship, Week 1.
 * Reads height (cm) and weight (kg), validates the input, calculates BMI and shows
 * the value with its health category (Underweight, Normal, Overweight, Obese).
 */
class MainActivity : AppCompatActivity() {

    private lateinit var heightLayout: TextInputLayout
    private lateinit var weightLayout: TextInputLayout
    private lateinit var heightInput: TextInputEditText
    private lateinit var weightInput: TextInputEditText
    private lateinit var resultCard: View
    private lateinit var bmiValue: TextView
    private lateinit var bmiCategory: TextView
    private lateinit var bmiAdvice: TextView

    enum class Category(val labelRes: Int, val adviceRes: Int, val colorRes: Int) {
        UNDERWEIGHT(R.string.cat_underweight, R.string.advice_underweight, R.color.cat_under),
        NORMAL(R.string.cat_normal, R.string.advice_normal, R.color.cat_normal),
        OVERWEIGHT(R.string.cat_overweight, R.string.advice_overweight, R.color.cat_over),
        OBESE(R.string.cat_obese, R.string.advice_obese, R.color.cat_obese)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        heightLayout = findViewById(R.id.heightLayout)
        weightLayout = findViewById(R.id.weightLayout)
        heightInput = findViewById(R.id.heightInput)
        weightInput = findViewById(R.id.weightInput)
        resultCard = findViewById(R.id.resultCard)
        bmiValue = findViewById(R.id.bmiValue)
        bmiCategory = findViewById(R.id.bmiCategory)
        bmiAdvice = findViewById(R.id.bmiAdvice)

        findViewById<Button>(R.id.calculateButton).setOnClickListener { calculate() }
        findViewById<Button>(R.id.resetButton).setOnClickListener { reset() }
    }

    private fun calculate() {
        heightLayout.error = null
        weightLayout.error = null

        val heightCm = heightInput.text?.toString()?.toDoubleOrNull()
        val weightKg = weightInput.text?.toString()?.toDoubleOrNull()

        var valid = true
        if (heightCm == null || heightCm !in 50.0..272.0) {
            heightLayout.error = getString(R.string.error_height)
            valid = false
        }
        if (weightKg == null || weightKg !in 2.0..500.0) {
            weightLayout.error = getString(R.string.error_weight)
            valid = false
        }
        if (!valid) {
            resultCard.visibility = View.GONE
            return
        }

        val bmi = bmiOf(weightKg!!, heightCm!!)
        val category = categoryOf(bmi)

        bmiValue.text = String.format(Locale.US, "%.1f", bmi)
        bmiCategory.text = getString(category.labelRes)
        bmiCategory.setTextColor(ContextCompat.getColor(this, category.colorRes))
        bmiAdvice.text = getString(category.adviceRes)
        resultCard.visibility = View.VISIBLE
        hideKeyboard()
    }

    private fun reset() {
        heightInput.text?.clear()
        weightInput.text?.clear()
        heightLayout.error = null
        weightLayout.error = null
        resultCard.visibility = View.GONE
        heightInput.requestFocus()
    }

    private fun hideKeyboard() {
        val imm = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
        currentFocus?.let { imm.hideSoftInputFromWindow(it.windowToken, 0) }
    }

    companion object {
        /** BMI = weight (kg) / height (m)^2 */
        fun bmiOf(weightKg: Double, heightCm: Double): Double {
            val m = heightCm / 100.0
            return weightKg / (m * m)
        }

        /** WHO adult categories. */
        fun categoryOf(bmi: Double): Category = when {
            bmi < 18.5 -> Category.UNDERWEIGHT
            bmi < 25.0 -> Category.NORMAL
            bmi < 30.0 -> Category.OVERWEIGHT
            else -> Category.OBESE
        }
    }
}
