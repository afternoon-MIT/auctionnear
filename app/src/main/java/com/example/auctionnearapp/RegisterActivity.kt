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

class RegisterActivity : AppCompatActivity() {
    lateinit var inputname:EditText
    lateinit var inputemail:EditText
    lateinit var inputpassword:EditText
    lateinit var confirmpassword:EditText
    lateinit var btnregister:Button
    lateinit var txtlogin:TextView
    lateinit var progress:ProgressDialog
    lateinit var mAuth:FirebaseAuth

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
        inputname = findViewById(R.id.inputName)
        inputemail = findViewById(R.id.inputEmail)
        inputpassword = findViewById(R.id.inputPassword)
        confirmpassword = findViewById(R.id.confirmPassword)
        btnregister = findViewById(R.id.btnRegister)
        txtlogin = findViewById(R.id.TxtLogin)
        mAuth = FirebaseAuth.getInstance()
        progress = ProgressDialog(this,)
        progress.setTitle("Loading")
        progress.setMessage("Please wait ...")
        btnregister.setOnClickListener {

            var name = inputname.text.toString().trim()
            var email = inputemail.text.toString().trim()
            var password = inputpassword.text.toString().trim()
            var confirmpassword = confirmpassword.text.toString().trim()

            // Check if the user is submitting empty files
            if (name.isEmpty()){
                inputemail.setError("Please fill this input")
                inputname.requestFocus()
            }else if (email.isEmpty()){
                inputemail.setError("Please fill this input")
                inputemail.requestFocus()
            }else if(password.isEmpty()){
                inputpassword.setError("Please fill the input")
                inputpassword.requestFocus()
            }else if(password.length <6){
                inputpassword.setError("Password is too short")
                inputpassword.requestFocus()
            }
            else{
                // Proceed to register the user
                progress.show()
                mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener {
                    progress.dismiss()
                    if (it.isSuccessful){
                        Toast.makeText(this, "Registrationn successful",
                            Toast.LENGTH_SHORT).show()
                        mAuth.signOut()
                        startActivity(Intent(this, LoginActivity::class.java))
                        finish()

                    }else{
                        displaymessage("Error", it.exception!!.message.toString())
                    }
                }
            }
        }
        txtlogin.setOnClickListener {
            startActivity(Intent(this,LoginActivity::class.java))
        }
    }
    fun displaymessage(title:String, message:String){
        var alertDialog = AlertDialog.Builder(this)
        alertDialog.setTitle(title)
        alertDialog.setMessage(message)
        alertDialog.setPositiveButton("Ok",null)
        alertDialog.create().show()
    }
}