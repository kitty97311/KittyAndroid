package com.example.kittyandroid

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.kittyandroid.ui.theme.KittyAndroidTheme
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity(), View.OnClickListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        findViewById<Button>(R.id.requestButton).setOnClickListener(this);
    }

    override fun onClick(p0: View?) {
        if (p0 != null) {
            if (p0.id == R.id.requestButton) {
                val userId = findViewById<EditText>(R.id.userIdInput).text.toString()
                Toast.makeText(this, "$userId", Toast.LENGTH_LONG).show()
                if (userId == "") Toast.makeText(this, "Type your user id as number!!!", Toast.LENGTH_LONG).show()
                MainScope().launch {
                    findViewById<TextView>(R.id.resultText1).text = "Loading..."
                    val apiResponse = ApiService.fetchDataFromApi(
                        userId,
                        1
                    )
                    findViewById<TextView>(R.id.resultText1).text = "$apiResponse"
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    KittyAndroidTheme {
        Greeting("Android")
    }
}