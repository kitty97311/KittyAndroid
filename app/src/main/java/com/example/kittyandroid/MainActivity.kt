package com.example.kittyandroid

import android.app.AlertDialog
import android.content.Context
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
import org.json.JSONArray
import org.json.JSONObject

class MainActivity : ComponentActivity(), View.OnClickListener {

    lateinit var responseText1: String
    lateinit var responseText2: String
    lateinit var responseText3: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        responseText1 = "No response yet"
        responseText2 = "No response yet"
        responseText3 = "No response yet"
        findViewById<Button>(R.id.requestButton).setOnClickListener(this);
        findViewById<Button>(R.id.responseButton1).setOnClickListener(this);
        findViewById<Button>(R.id.responseButton2).setOnClickListener(this);
        findViewById<Button>(R.id.responseButton3).setOnClickListener(this);
    }

    override fun onClick(p0: View?) {
        if (p0 != null) {
            if (p0.id == R.id.requestButton) {
                val userId = findViewById<EditText>(R.id.userIdInput).text.toString()
                if (userId == "") {
                    Toast.makeText(this, "Type your user id as number!!!", Toast.LENGTH_LONG).show()
                    return
                }
                MainScope().launch {
                    try {
                        for (k in 1..3) {
                            if (k == 1) {
                                findViewById<TextView>(R.id.resultText1).text = "Loading..."
                                val apiResponse = ApiService.fetchDataFromApi(
                                    userId,
                                    k
                                )
                                val responseJson =
                                    JSONObject(apiResponse).getJSONObject("attributes")
                                val idText = "id: " + responseJson.getString("id") + "\n"
                                val nameText = "name: " + responseJson.getString("name") + "\n"
                                var accountText = "account ids: "
                                val accountIdsArray = responseJson.getJSONArray("account_ids")
                                for (i in 0 until accountIdsArray.length()) {
                                    accountText += accountIdsArray.getString(i) + if (i == accountIdsArray.length() - 1) "" else ", "
                                }
                                findViewById<TextView>(R.id.resultText1).text =
                                    idText + nameText + accountText
                                responseText1 = apiResponse
                            } else if (k == 2) {
                                findViewById<TextView>(R.id.resultText2).text = "Loading..."
                                val apiResponse = ApiService.fetchDataFromApi(
                                    userId,
                                    k
                                )

                                val responseJson = JSONArray(apiResponse)
                                var attributesText = ""
                                for (i in 0 until responseJson.length()) {
                                    val attributeJson =
                                        responseJson.getJSONObject(i)
                                            .getJSONObject("attributes")
                                    val m = i + 1
                                    attributesText += "A t t r i b u t e  $m\n" + "id: " + attributeJson.getString(
                                        "id"
                                    ) + "\n" + "user id: " + attributeJson.getString("user_id") + "\n" + "user name: " + attributeJson.getString(
                                        "name"
                                    ) + "\n" + "user name: " + attributeJson.getString("balance") + if (k == responseJson.length()) "" else "\n\n"
                                }
                                findViewById<TextView>(R.id.resultText2).text = attributesText
                                responseText2 = apiResponse
                            } else if (k == 3) {
                                findViewById<TextView>(R.id.resultText3).text = "Loading..."
                                val apiResponse = ApiService.fetchDataFromApi(
                                    userId,
                                    k
                                )
                                val responseJson =
                                    JSONObject(apiResponse).getJSONObject("attributes")
                                val idText = "id: " + responseJson.getString("id") + "\n"
                                val userIdText =
                                    "user id: " + responseJson.getString("user_id") + "\n"
                                val nameText = "name: " + responseJson.getString("name") + "\n"
                                val balanceText =
                                    "balance: " + responseJson.getString("balance") + "\n"
                                findViewById<TextView>(R.id.resultText3).text =
                                    idText + userIdText + nameText + balanceText
                                responseText3 = apiResponse
                            }
                        }
                    } catch (e: Exception) {
                        Toast.makeText(this@MainActivity, "Response is malformed", Toast.LENGTH_LONG).show()
                    }
                }
            } else if (p0.id == R.id.responseButton1) {
                showDialog(responseText1)
            } else if (p0.id == R.id.responseButton2) {
                showDialog(responseText2)
            } else if (p0.id == R.id.responseButton3) {
                showDialog(responseText3)
            }
        }
    }

    fun showDialog(text: String) {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Response Text")
        builder.setMessage(text)
        builder.setPositiveButton("OK") { dialog, which ->
            dialog.dismiss()
        }
        builder.setNegativeButton("Cancel") { dialog, which ->
            dialog.dismiss()
        }
        val dialog: AlertDialog = builder.create()
        dialog.show()
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