package com.example.auctionnearapp

import android.app.DatePickerDialog
import android.app.ProgressDialog
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import java.util.Calendar
import java.util.UUID

class AddAuctionsActivity : AppCompatActivity() {
    private lateinit var enterowner: EditText
    private lateinit var enterlocation: EditText
    private lateinit var btnauctionperiod: Button
    private lateinit var calendar: Calendar
    private lateinit var btnaddauction: Button
    private lateinit var progress: ProgressDialog
    private lateinit var firebaseStore: FirebaseStorage
    private lateinit var filepath: Uri
    private lateinit var storageReference: StorageReference
    private lateinit var mAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_auctions)
        enterowner = findViewById(R.id.enterowner)
        enterlocation = findViewById(R.id.enterlocation)
        btnauctionperiod = findViewById(R.id.btnauctionperiod)
        calendar = Calendar.getInstance()
        btnaddauction = findViewById(R.id.btnaddauction)
        progress = ProgressDialog(this)
        progress.setTitle("Loading")
        progress.setMessage("Please wait...")
        firebaseStore = FirebaseStorage.getInstance()
        storageReference = firebaseStore.getReference()
        mAuth = FirebaseAuth.getInstance()


        btnaddauction.setOnClickListener { it ->
            val owner = enterowner.text.toString().trim()
            val location = enterlocation.text.toString().trim()
            val userId = mAuth.currentUser?.uid
            var imageUri: String
            var imageid: String
            var auction: Auction
            val ref = storageReference.child("Auction/$userId")

            // Check if user is submitting empty fields
            if (owner.isEmpty()) {
                enterowner.setError("Please input field")
                enterowner.requestFocus()
            } else if (location.isEmpty()) {
                enterlocation.setError("Please input field")
                enterlocation.requestFocus()
            } else {
                ref.downloadUrl.addOnCompleteListener {
                    imageUri = it.toString()
                    imageid = System.currentTimeMillis().toString()
                    auction = Auction(imageid,
                        imageUri, mAuth.currentUser!!.uid)
                    val dbRef = FirebaseDatabase.getInstance()
                        .getReference().child("Auction/$imageid")
                    dbRef.setValue(auction)
                    Toast.makeText(this, "Upload successful", Toast.LENGTH_SHORT).show()
                }
            }


            startActivity(Intent(this, AddItemsActivity::class.java))
        }



        lateinit var selectedStartDate: String

        btnauctionperiod.setOnClickListener {
            showStartDatePicker()
        }

        fun uploadDatesToFirebase(startDate: String, endDate: String) {
            // Generate unique filenames for the start and end dates
            val startFilename = UUID.randomUUID().toString()
            val endFilename = UUID.randomUUID().toString()

            // Create references to the Firebase Realtime Database locations where the dates will be stored
            val startDbRef =
                FirebaseDatabase.getInstance().getReference().child("dates/$startFilename")
            startDbRef.setValue(startDate)

            val endDbRef = FirebaseDatabase.getInstance().getReference().child("dates/$endFilename")
            endDbRef.setValue(endDate)

            // TODO: Handle the completion of both upload tasks as per your requirements
            // You can remove the putBytes and addOnSuccessListener and addOnFailureListener calls
            // if you are not uploading any data to Firebase Storage

            // Handle the success case as per your requirements
            // TODO: Add your success handling code here

            // Handle the failure case and display an error message if necessary
            // TODO: Add your failure handling code here
        }

    }

    private fun Auction(imageid: String, imageUri: String, uid: String): Auction {
        val auction = Auction(
            imageid,
            imageUri,
            uid
        )
        return auction
    }


    private fun showStartDatePicker() {
            val calendar = Calendar.getInstance()

            val startDatePickerDialog = DatePickerDialog(
                this,
                { _, year, month, dayOfMonth ->
                    val startDate = "$year-${month + 1}-$dayOfMonth"

                    // Show the end date picker after selecting the start date
                    showEndDatePicker(startDate)
                },
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)
            )

            startDatePickerDialog.show()
        }

    private fun showEndDatePicker(startDate: String) {
        val calendar = Calendar.getInstance()

        val endDatePickerDialog = DatePickerDialog(
            this,
            { _, year, month, dayOfMonth ->
                val endDate = "$year-${month + 1}-$dayOfMonth"

                // Upload the selected start and end dates to Firebase Realtime Database
                uploadDatesToFirebase(startDate, endDate)
            },
            calendar.get(Calendar.YEAR),
            calendar.get(Calendar.MONTH),
            calendar.get(Calendar.DAY_OF_MONTH)
        )

        // Set the minimum date for the end date picker as the selected start date
        val selectedStartDateParts = startDate.split("-")
        val startYear = selectedStartDateParts[0].toInt()
        val startMonth = selectedStartDateParts[1].toInt() - 1
        val startDay = selectedStartDateParts[2].toInt()
        endDatePickerDialog.datePicker.minDate =
            calendar.apply { set(startYear, startMonth, startDay) }.timeInMillis

        endDatePickerDialog.show()
    }

    private fun uploadDatesToFirebase(startDate: String, endDate: String) {
        // Generate unique filenames for the start and end dates
        val startFilename = UUID.randomUUID().toString()
        val endFilename = UUID.randomUUID().toString()

        // Create references to the Firebase Realtime Database locations where the dates will be stored
        val startDbRef =
            FirebaseDatabase.getInstance().getReference().child("dates/$startFilename")
        startDbRef.setValue(startDate)

        val endDbRef = FirebaseDatabase.getInstance().getReference().child("dates/$endFilename")
        endDbRef.setValue(endDate)

        // TODO: Handle the completion of both upload tasks as per your requirements
        // You can remove the putBytes and addOnSuccessListener and addOnFailureListener calls
        // if you are not uploading any data to Firebase Storage

        // Handle the success case as per your requirements
        // TODO: Add your success handling code here

        // Handle the failure case and display an error message if necessary
        // TODO: Add your failure handling code here
    }

    }



