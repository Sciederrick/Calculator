package ke.solvitlabs.calculator

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.core.content.ContextCompat
import ke.solvitlabs.calculator.databinding.ActivityCalcBinding

class CalcActivity : AppCompatActivity() {
    private lateinit var binding: ActivityCalcBinding
    private var isOperator = false
    private var operators = 0
    private var isCalculable = false
    private var operatorCategories: HashMap<String, String> = HashMap<String, String> ()

    init {
        initializeOperatorCategories()
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCalcBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        setSupportActionBar(binding.toolbar)
        supportActionBar?.title = ""

        binding.btnShowMoreOperations.setOnClickListener {
            toggleExtraOperations()
        }

        binding.btnAC.setOnClickListener { clear() }
        binding.btnZero.setOnClickListener { appendToTextView("0") }
        binding.btnOne.setOnClickListener { appendToTextView("1") }
        binding.btnTwo.setOnClickListener { appendToTextView("2") }
        binding.btnThree.setOnClickListener { appendToTextView("3") }
        binding.btnFour.setOnClickListener { appendToTextView("4") }
        binding.btnFive.setOnClickListener { appendToTextView("5") }
        binding.btnSix.setOnClickListener { appendToTextView("6") }
        binding.btnSeven.setOnClickListener { appendToTextView("7") }
        binding.btnEight.setOnClickListener { appendToTextView("8") }
        binding.btnNine.setOnClickListener { appendToTextView("9") }
        binding.btnDot.setOnClickListener { appendToTextView(".") }
        binding.btnBackspace.setOnClickListener { backspace() }
        binding.btnAdd.setOnClickListener { appendToTextView("+") }
        binding.btnMinus.setOnClickListener { appendToTextView("-") }
        binding.btnDivide.setOnClickListener { appendToTextView("÷") }
        binding.btnMultiply.setOnClickListener { appendToTextView("×") }
        binding.btnModulus.setOnClickListener { appendToTextView("%") }

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.app_bar_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.actionHistory -> Toast.makeText(this,"History selected",Toast.LENGTH_SHORT).show()
            R.id.actionChooseTheme -> Toast.makeText(this,"Choose theme selected",Toast.LENGTH_SHORT).show()
            R.id.actionSendFeedback -> Toast.makeText(this, "Send feedback selected", Toast.LENGTH_SHORT).show()
            R.id.actionHelp -> Toast.makeText(this,"Help selected",Toast.LENGTH_SHORT).show()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun toggleExtraOperations() {
        binding.llMoreOperations.visibility = if (binding.llMoreOperations.visibility === View.VISIBLE) {
            toggleBtnIcon(View.GONE)
            View.GONE
        } else {
            toggleBtnIcon(View.VISIBLE)
            View.VISIBLE
        }
    }

    private fun toggleBtnIcon(visibility: Int) {
        binding.btnShowMoreOperations.background = if (visibility === View.VISIBLE) {
            ContextCompat.getDrawable(this, R.drawable.ic_baseline_expand_less)
        } else {
            ContextCompat.getDrawable(this, R.drawable.ic_baseline_expand_more)
        }
    }

    private fun initializeOperatorCategories() {
        operatorCategories["兀"] = "standalone"
        operatorCategories["e"] = "standalone"
        operatorCategories["sin"] = "prefix"
        operatorCategories["cos"] = "prefix"
        operatorCategories["tan"] = "prefix"
        operatorCategories["ln"] = "prefix"
        operatorCategories["log"] = "prefix"
        operatorCategories["√"] = "prefix"
        operatorCategories["!"] = "postfix"
        operatorCategories["⌃"] = "infix"
        operatorCategories["+"] = "infix"
        operatorCategories["−"] = "infix"
        operatorCategories["×"] = "infix"
        operatorCategories["%"] = "infix"
        operatorCategories["÷"] = "infix"
    }

    private fun clear() {
        binding.tvInput.text = ""
        isOperator = false
        operators = 0

    }

    private fun backspace() {
        var buffer = binding.tvInput.text
        var elementToDrop = buffer[buffer.length - 1]

        buffer = buffer.dropLast(1)
        binding.tvInput.text = buffer

        if (isOperator(elementToDrop.toString())) {
            if (operators > 0)
                operators--

            if (operators === 0)
                isOperator = false
        }

    }

    private fun appendToTextView(symbol:String) {
        binding.tvInput.append(symbol)

        if (isOperator(symbol)) {
            isOperator = true
            operators++
        }
    }

    private fun isOperator(symbol:String) :Boolean {
        return symbol == "+" || symbol == "-" || symbol == "÷" || symbol == "×" || symbol == "%"
    }

    private fun displayResult(result:String) {
        binding.tvResult.text = result
    }

    private fun isCalculable() {
        // TODO: perform check to establish if it has the correct format b4 calculating

    }

    private fun calculate() {

    }

}

