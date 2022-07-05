package ke.solvitlabs.calculator

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.widget.Toolbar

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val myToolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(myToolbar)
        supportActionBar?.title = ""
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

}