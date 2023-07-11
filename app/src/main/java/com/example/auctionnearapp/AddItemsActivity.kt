package com.example.auctionnearapp

import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.auctionnearapp.databinding.ActivityAddItemsBinding
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import java.io.File
import java.io.IOException
import java.util.UUID

class AddItemsActivity : AppCompatActivity() {
 lateinit var binding:ActivityAddItemsBinding
 lateinit var database:DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddItemsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.btnadditems.setOnClickListener {
            val auctionitem = binding.edtauctionitem.text.toString()
            val itemDesc = binding.edtitemdesc.text.toString()
            val quantity = binding.edtquantity.text.toString()

            database = FirebaseDatabase.getInstance().getReference("Auction items")
            val item = items(auctionitem,itemDesc,quantity)
            database.child(auctionitem).setValue(auctionitem).addOnSuccessListener {
                binding.edtauctionitem.text.clear()
                binding.edtitemdesc.text.clear()
                binding.edtquantity.text.clear()
                Toast.makeText(this, "Successfully saved", Toast.LENGTH_SHORT).show()
            }.addOnFailureListener{
                Toast.makeText(this, "Failed", Toast.LENGTH_SHORT).show()
            }
        }


}
    }
class item : AppCompatActivity() {
    private val PICK_IMAGE_REQUEST = 1
    private val CAMERA_CAPTURE_REQUEST = 2
    private lateinit var capturedImageUri: Uri
    private lateinit var btnadditems:Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_items)

        // Button click event to choose an image or open the camera
       btnadditems.setOnClickListener {
            if (hasPermission()) {
                openImagePicker()
            } else {
                requestPermission()
            }
        }
    }

    // ...

    // ...

    private fun hasPermission(): Boolean {
        return ContextCompat.checkSelfPermission(
            this,
            "android.permission.CAMERA"
        ) == PackageManager.PERMISSION_GRANTED && ContextCompat.checkSelfPermission(
            this,
          "android.permission.WRITE_EXTERNAL_STORAGE"
        ) == PackageManager.PERMISSION_GRANTED
    }

// ...

// ...


    private fun requestPermission() {
        ActivityCompat.requestPermissions(
            this,
            arrayOf(
                "android.permission.CAMERA",
                "android.permission.WRITE_EXTERNAL_STORAGE",

            ),
            CAMERA_CAPTURE_REQUEST
        )
    }




    private fun openImagePicker() {
        val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        startActivityForResult(intent, PICK_IMAGE_REQUEST)
    }

    private fun openCamera() {
        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        val storageDir = getExternalFilesDir(null)
        try {
            val imageFile = File.createTempFile(
                "IMG_${System.currentTimeMillis()}",
                ".jpg",
                storageDir
            )
            capturedImageUri = Uri.fromFile(imageFile)
            intent.putExtra(MediaStore.EXTRA_OUTPUT, capturedImageUri)
            startActivityForResult(intent, CAMERA_CAPTURE_REQUEST)
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == CAMERA_CAPTURE_REQUEST) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                openImagePicker()
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data:Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK) {
            when (requestCode) {
                PICK_IMAGE_REQUEST -> {
                    val imageUri = data?.data
                    uploadImage(imageUri)
                }
                CAMERA_CAPTURE_REQUEST -> {
                    uploadImage(capturedImageUri)
                }
            }
        }
    }

    private fun uploadImage(imageUri: Uri?) {
        if (imageUri != null) {
            val storage = FirebaseStorage.getInstance()
            val storageRef = storage.reference

            val fileReference = storageRef.child("images/${UUID.randomUUID()}.jpg")

            val uploadTask = fileReference.putFile(imageUri)

            uploadTask.addOnSuccessListener { taskSnapshot ->
                fileReference.downloadUrl.addOnSuccessListener { downloadUri ->
                    val imageUrl = downloadUri.toString()

                    // Create an instance of MyClass and set the image property to the download URL
                    // Assuming you have the imageUrl as a String

// Convert the imageUrl string to a Uri
                    val imageUri: Uri = Uri.parse(imageUrl)

// Now you can pass the imageUri to the items function
                    val items = items(image = imageUri)

                    // Save the myObject to the Firebase Realtime Database
                    val database = FirebaseDatabase.getInstance()
                    val databaseReference = database.getReference("your_data_location")
                    databaseReference.push().setValue(items)
                        .addOnSuccessListener {
                            // Data saved successfully
                        }
                        .addOnFailureListener {
                            // An error occurred while saving the data
                            Toast.makeText(this, "Successfully saved", Toast.LENGTH_SHORT).show()
                        }
                }
            }.addOnFailureListener { exception ->
                // Image upload failed
                // Handle the failure
                Toast.makeText(this, "Failed", Toast.LENGTH_SHORT).show()
            }
        } else {
            // No image selected or captured
        }
    }
}
