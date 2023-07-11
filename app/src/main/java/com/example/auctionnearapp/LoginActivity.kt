package com.example.auctionnearapp
import android.annotation.SuppressLint
import android.app.ProgressDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.google.firebase.auth.FirebaseAuth

class LoginActivity : AppCompatActivity() {
    private lateinit var inputemail: EditText
    private lateinit var inputpassword: EditText
    private lateinit var forgotpassword: TextView
    private lateinit var btnlogin: Button
    private lateinit var textviewSignUp: TextView
    private lateinit var mAuth: FirebaseAuth
    private lateinit var progress: ProgressDialog

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
         inputemail= findViewById(R.id.inputEmail)
       inputpassword = findViewById(R.id.inputPassword)
        forgotpassword = findViewById(R.id.forgotPassword)
        btnlogin = findViewById(R.id.btnLogin)
       textviewSignUp = findViewById(R.id.textviewSignUp)
        mAuth = FirebaseAuth.getInstance()
        progress = ProgressDialog(this)
        progress.setTitle("Loading LOGIN")
        progress.setMessage("Please wait...")
        btnlogin.setOnClickListener {
            val email = inputemail.text.toString().trim()
            val password = inputpassword.text.toString().trim()

            // Check if the user is submitting empty files

            if (email.isEmpty()) {
                inputemail.error = "Please fill this input"
                inputemail.requestFocus()
            } else if (password.isEmpty()) {
                inputpassword.error = "Please fill the input"
               inputpassword.requestFocus()
            } else {
                // Proceed to register the user
                progress.show()
                mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener {
                    if (it.isSuccessful) {
                        Toast.makeText(this@LoginActivity, "Login  successful", Toast.LENGTH_SHORT).show()

                        startActivity(Intent(this, HomeActivity::class.java))
                        finish()

                    } else {
                        displaymessage("Error", it.exception!!.message.toString())
                    }
                }
            }
        }
        textviewSignUp.setOnClickListener {
            startActivity(Intent(this@LoginActivity, RegisterActivity::class.java))
            finish()
        }
        forgotpassword.setOnClickListener {

        }
    }

    private fun displaymessage(title: String, message: String) {
        val alertDialog = AlertDialog.Builder(this)
        alertDialog.setTitle(title)
        alertDialog.setMessage(message)
        alertDialog.setPositiveButton("Ok", null)
        alertDialog.create().show()
    }
}