package com.example.auctionnearapp

import android.net.Uri
import com.google.firebase.storage.FirebaseStorage
import java.util.UUID




data class items(
    var Auctionitem: String? = null,
    var ItemDescription: String? = null,
    var Quantity: String? = null,
    val uriString: String = "https://example.com/image.jpg",
    val image: Uri = Uri.parse(uriString)
)
fun uploadImageToFirebaseStorage(imageUri: Uri) {
    val storage = FirebaseStorage.getInstance()
    val storageRef = storage.reference

    val fileReference = storageRef.child("images/${UUID.randomUUID()}.jpg")

    val uploadTask = fileReference.putFile(imageUri)

    uploadTask.addOnSuccessListener { taskSnapshot ->
        // Image upload successful
        fileReference.downloadUrl.addOnSuccessListener { downloadUri ->
            val imageUrl = downloadUri.toString()
            // Create an instance of MyClass and set the image property to the download URL
            val items = items(image = Uri.parse(imageUrl))

            // Save the myObject to the Firebase Realtime Database or Firestore
            // ... Your code to save myObject to the database ...
        }
    }.addOnFailureListener { exception ->
        // Image upload failed
        // Handle the failure
    }
}
