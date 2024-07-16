package com.example.assignment1

import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private lateinit var editTextValue: EditText
    private lateinit var spinnerUnitType: Spinner
    private lateinit var spinnerFromUnit: Spinner
    private lateinit var spinnerToUnit: Spinner
    private lateinit var buttonConvert: Button
    private lateinit var textViewResult: TextView

    private val unitTypes = arrayOf("Length", "Weight", "Temperature")
    private val lengthUnits = arrayOf("Meters", "Feet", "Inches")
    private val weightUnits = arrayOf("Kilograms", "Pounds", "Ounces")
    private val tempUnits = arrayOf("Celsius", "Fahrenheit", "Kelvin")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        editTextValue = findViewById(R.id.editTextValue)
        spinnerUnitType = findViewById(R.id.spinnerUnitType)
        spinnerFromUnit = findViewById(R.id.spinnerFromUnit)
        spinnerToUnit = findViewById(R.id.spinnerToUnit)
        buttonConvert = findViewById(R.id.buttonConvert)
        textViewResult = findViewById(R.id.textViewResult)

        val unitTypeAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, unitTypes)
        unitTypeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinnerUnitType.adapter = unitTypeAdapter

        spinnerUnitType.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                updateUnitSpinners(position)
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }

        buttonConvert.setOnClickListener {
            val inputValue = editTextValue.text.toString().toDouble()
            val fromUnit = spinnerFromUnit.selectedItem.toString()
            val toUnit = spinnerToUnit.selectedItem.toString()
            val result = performConversion(inputValue, fromUnit, toUnit)
            textViewResult.text = "Converted Value: %.2f".format(result)
        }
    }

    private fun updateUnitSpinners(position: Int) {
        val unitAdapter: ArrayAdapter<String> = when (position) {
            0 -> ArrayAdapter(this, android.R.layout.simple_spinner_item, lengthUnits)
            1 -> ArrayAdapter(this, android.R.layout.simple_spinner_item, weightUnits)
            2 -> ArrayAdapter(this, android.R.layout.simple_spinner_item, tempUnits)
            else -> ArrayAdapter(this, android.R.layout.simple_spinner_item, emptyArray())
        }
        unitAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinnerFromUnit.adapter = unitAdapter
        spinnerToUnit.adapter = unitAdapter
    }

    private fun performConversion(value: Double, fromUnit: String, toUnit: String): Double {
        return when {
            fromUnit == "Meters" && toUnit == "Feet" -> value * 3.28084
            fromUnit == "Feet" && toUnit == "Meters" -> value / 3.28084
            fromUnit == "Meters" && toUnit == "Inches" -> value * 39.3701
            fromUnit == "Inches" && toUnit == "Meters" -> value / 39.3701
            fromUnit == "Feet" && toUnit == "Inches" -> value * 12
            fromUnit == "Inches" && toUnit == "Feet" -> value / 12
            fromUnit == "Kilograms" && toUnit == "Pounds" -> value * 2.20462
            fromUnit == "Pounds" && toUnit == "Kilograms" -> value / 2.20462
            fromUnit == "Kilograms" && toUnit == "Ounces" -> value * 35.274
            fromUnit == "Ounces" && toUnit == "Kilograms" -> value / 35.274
            fromUnit == "Pounds" && toUnit == "Ounces" -> value * 16
            fromUnit == "Ounces" && toUnit == "Pounds" -> value / 16
            fromUnit == "Celsius" && toUnit == "Fahrenheit" -> (value * 9 / 5) + 32
            fromUnit == "Fahrenheit" && toUnit == "Celsius" -> (value - 32) * 5 / 9
            fromUnit == "Celsius" && toUnit == "Kelvin" -> value + 273.15
            fromUnit == "Kelvin" && toUnit == "Celsius" -> value - 273.15
            fromUnit == "Fahrenheit" && toUnit == "Kelvin" -> (value - 32) * 5 / 9 + 273.15
            fromUnit == "Kelvin" && toUnit == "Fahrenheit" -> (value - 273.15) * 9 / 5 + 32
            else -> value
        }
    }
}
