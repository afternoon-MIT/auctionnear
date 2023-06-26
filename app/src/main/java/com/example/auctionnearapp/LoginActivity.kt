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
    lateinit var inputemail: EditText
    lateinit var inputpassword: EditText
    lateinit var btnlogin: Button
    lateinit var textviewsignup: TextView
    lateinit var forgotpassword: TextView
    lateinit var mAuth: FirebaseAuth
    lateinit var progress: ProgressDialog

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        inputemail = findViewById(R.id.inputEmail)
        inputpassword = findViewById(R.id.inputPassword)
        btnlogin = findViewById(R.id.btnLogin)
        textviewsignup = findViewById(R.id.textviewSignUp)
        forgotpassword = findViewById(R.id.forgotPassword)
        mAuth = FirebaseAuth.getInstance()
        progress = ProgressDialog(this)
        progress.setTitle("Loading")
        progress.setMessage("Please wait...")
        btnlogin.setOnClickListener {
            var email = inputemail.text.toString().trim()
            var password = inputpassword.text.toString().trim()

            // Check if the user is submitting empty files

            if (email.isEmpty()) {
                inputemail.setError("Please fill this input")
                inputemail.requestFocus()
            } else if (password.isEmpty()) {
                inputpassword.setError("Please fill the input")
                inputpassword.requestFocus()
            } else {
                // Proceed to register the user
                progress.show()
                mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener {
                    progress.dismiss()
                    if (it.isSuccessful) {
                        Toast.makeText(
                            this, "Registration  successful",
                            Toast.LENGTH_SHORT
                        ).show()
                        mAuth.signOut()
                        startActivity(Intent(this, LoginActivity::class.java))
                        finish()

                    } else {
                        displaymessage("Error", it.exception!!.message.toString())
                    }
                }
            }
        }
        textviewsignup.setOnClickListener {
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        }
        forgotpassword.setOnClickListener {

        }
    }

    fun displaymessage(title: String, message: String) {
        var alertDialog = AlertDialog.Builder(this)
        alertDialog.setTitle(title)
        alertDialog.setMessage(message)
        alertDialog.setPositiveButton("Ok", null)
        alertDialog.create().show()
    }
}