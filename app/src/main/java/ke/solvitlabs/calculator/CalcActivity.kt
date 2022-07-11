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

        binding.btnAC.setOnClickListener { binding.tvInput.text = "" }
        binding.btnZero.setOnClickListener { binding.tvInput.append("0") }
        binding.btnOne.setOnClickListener { binding.tvInput.append("1") }
        binding.btnTwo.setOnClickListener { binding.tvInput.append("2") }
        binding.btnThree.setOnClickListener { binding.tvInput.append("3") }
        binding.btnFour.setOnClickListener { binding.tvInput.append("4") }
        binding.btnFive.setOnClickListener { binding.tvInput.append("5") }
        binding.btnSix.setOnClickListener { binding.tvInput.append("6") }
        binding.btnSeven.setOnClickListener { binding.tvInput.append("7") }
        binding.btnEight.setOnClickListener { binding.tvInput.append("8") }
        binding.btnNine.setOnClickListener { binding.tvInput.append("9") }
        binding.btnDot.setOnClickListener { binding.tvInput.append(".") }
        binding.btnBackspace.setOnClickListener { backspace() }


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


    private fun backspace() {
        var buffer = binding.tvInput.text
        buffer = buffer.dropLast(1)
        binding.tvInput.text = buffer
    }

    private fun add() {

    }


}

