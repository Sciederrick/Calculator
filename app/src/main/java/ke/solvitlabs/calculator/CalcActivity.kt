package ke.solvitlabs.calculator

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.core.content.ContextCompat
import ke.solvitlabs.calculator.databinding.ActivityCalcBinding
import kotlin.math.E
import kotlin.properties.Delegates

class CalcActivity : AppCompatActivity() {
    private lateinit var binding: ActivityCalcBinding
    private var isOperator = false
    private var numOperators = 0
    private var isCalculable = false
    private var operatorsWithPosition = ArrayList<Pair<String, Int>>()
    private var operatorsWithPriority = ArrayList<Pair<String, Int>>()
    private lateinit var expression: String
    private var operatorPosition: Int by Delegates.notNull()
    private var leftOperand:String? = null
    private var rightOperand:String? = null
    private var currentOperator: String? = null
    private var result: Double? = null


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
        binding.btnPercent.setOnClickListener { appendToTextView("%") }
        binding.btnEular.setOnClickListener { appendToTextView("e") }
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
        binding.llMoreOperations.visibility = if (binding.llMoreOperations.visibility == View.VISIBLE) {
            toggleBtnIcon(View.GONE)
            View.GONE
        } else {
            toggleBtnIcon(View.VISIBLE)
            View.VISIBLE
        }
    }

    private fun toggleBtnIcon(visibility: Int) {
        binding.btnShowMoreOperations.background = if (visibility == View.VISIBLE) {
            ContextCompat.getDrawable(this, R.drawable.ic_baseline_expand_less)
        } else {
            ContextCompat.getDrawable(this, R.drawable.ic_baseline_expand_more)
        }
    }

    private fun clear() {
        binding.tvInput.text = ""
        isOperator = false
        numOperators = 0
        isCalculable = false
        leftOperand = null
        rightOperand = null
        result = null
        operatorsWithPosition.clear()
        operatorsWithPriority.clear()
        binding.tvResult.text = ""
    }

    private fun backspace() {
        var buffer = binding.tvInput.text
        val elementToDrop = buffer[buffer.length - 1]

        buffer = buffer.dropLast(1)
        binding.tvInput.text = buffer

        if (isOperator(elementToDrop.toString())) {
            if (numOperators > 0)
                numOperators--

            if (numOperators == 0) {
                isOperator = false
                isCalculable = false
            }
        }

        // TODO: Check if expression is still calculable

    }

    private fun appendToTextView(symbol:String) {
        binding.tvInput.append(symbol)
        expression = binding.tvInput.text.toString()

        if (isOperator(symbol)) {
            isOperator = true
            numOperators++
        }

        if(isOperator && isCalculable()) calculate()
    }

    private fun isOperator(symbol:String) :Boolean {
        return CalcOperations.operations.containsKey(symbol)
    }

    private fun displayResult(result: String) {
        binding.tvResult.text = result
    }

    private fun isCalculable() :Boolean{
        // The last input entered can't be an infix or a prefix
        return when(CalcOperations.operations[expression]?.category) {
            "infix" -> false
            "prefix" -> false
            else -> true// "postfix", "standalone" operators & numbers 0..9
        }
    }

    private fun calculate() {
        // 1.Obtain operators & their positions
        for((index, ch) in expression.withIndex()) {
            val symbol = ch.toString()
           if (symbol in CalcOperations.operations.keys) {
               operatorsWithPosition.add(Pair(symbol, index))
               operatorsWithPriority.add(Pair(symbol, CalcOperations.operations[symbol]?.priority!!))
               numOperators++
           }

        }

        // 2.Sort operators according to their priority level
        if (operatorsWithPriority.size > 1) {
            operatorsWithPriority = operatorsWithPriority.sortedBy { (_, value) -> value }.toList() as ArrayList<Pair<String,Int>>
        }

        // 3.For each operator, find operand(s)
        lateinit var operator:String
        for (item in operatorsWithPriority) {
            operator = item.first
            currentOperator = operator
            operatorPosition = searchOperatorPosition(operator)

            // Check left and right of the operator until and stop at next/previous operator
            when (operatorPosition) {
                0 -> gatherRightOperand()
                in 1 until (expression.length.minus(1)) -> {
                    gatherLeftOperand()
                    gatherRightOperand()
                }
                expression.length - 1 -> gatherLeftOperand()
            }

            // 4.Evaluate
            result = evaluate(operand1 = leftOperand != null, operand2 = rightOperand != null)

            // 5. Reset operands
            rightOperand = null
            leftOperand = null

            // 6.Check operator category then create subexpression
//            subExpression = createSubExpression(operator)

            // 7.Modify original expression using subexpression
//            expression = expression.replaceFirst(subExpression, result.toString())

        }
        // 8.Display result
        if (result != null)
        displayResult(result.toString())


    }

    private fun gatherLeftOperand() {
        val count = operatorPosition - 1
        var element: String?
        for (i in count downTo 0) {
            element = expression[i].toString()
            leftOperand = if (CalcOperations.operations.containsKey(element)) {
                leftOperand?.reversed()
                break
            } else {
                // it's part of the operand
                if (leftOperand == null) leftOperand = ""
                leftOperand.plus(element)
            }

        }

    }
    private fun gatherRightOperand() {
        val count = operatorPosition + 1
        val lastElement = expression.length
        var element: String?
        for (i in count until lastElement) {
            element = expression[i].toString()
            rightOperand = if (CalcOperations.operations.containsKey(element)) {
                break
            } else {
                // it's part of the operand
                if (rightOperand == null) rightOperand = ""
                rightOperand.plus(element)
            }
        }
    }

    private fun evaluate(operand1: Boolean, operand2: Boolean): Double? {
        var result: Double? = null
        when (currentOperator) {
            "e" -> {
                result = if (operand1 && operand2) {
                    leftOperand?.toDouble()?.let { rightOperand?.toDouble()?.times(it)?.times(E) }
                } else if (operand1) {
                    leftOperand?.toDouble()?.times(E)
                } else if (operand2){
                    rightOperand?.toDouble()?.times(E)
                } else {
                    E
                }
            }
        }

        return result
    }

    private fun createSubExpression(operator: String) :String{        
        return if (leftOperand != null && rightOperand != null) {
            leftOperand.plus(operator).plus(rightOperand)
        } else if (leftOperand != null) {
            leftOperand.plus(operator)
        } else if (rightOperand != null) {
            operator.plus(rightOperand)
        } else {
            operator
        }
        
    }

    private fun searchOperatorPosition(operator: String) :Int{
        var position: Int by Delegates.notNull()
        for (item in operatorsWithPosition) {
            if (item.first == operator) {
                position = item.second
                break
            }
        }
        return position
    }

}

