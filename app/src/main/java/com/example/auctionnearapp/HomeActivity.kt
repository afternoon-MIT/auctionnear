package com.example.auctionnearapp

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView

class HomeActivity : AppCompatActivity() {
    lateinit var mcardaddauction:CardView
    lateinit var mcardviewauction:CardView
    lateinit var mcardviewpastauction:CardView
    lateinit var mcardbestseller:CardView
    lateinit var mcardauctionranking:CardView
    lateinit var mcardprofile:CardView
   override fun onCreate(savedInstanceState: Bundle?) {
       super.onCreate(savedInstanceState)
       setContentView(R.layout.activity_home)
       mcardaddauction = findViewById(R.id.mcardaddauctions)
       mcardviewauction = findViewById(R.id.mcardviewauctions)
       mcardviewpastauction = findViewById(R.id.mcardviewpastauctions)
       mcardbestseller = findViewById(R.id.mcardbestsellers)
       mcardauctionranking = findViewById(R.id.mcardauctionrankings)
       mcardprofile = findViewById(R.id.mcardprofile)
       mcardaddauction.setOnClickListener{
           startActivity(Intent(this, AddAuctionsActivity::class.java))
       }
       mcardviewauction.setOnClickListener{
           startActivity(Intent(this, ViewAuctionActivity::class.java))
       }
       mcardviewpastauction.setOnClickListener{
           startActivity(Intent(this, ViewPastActivity::class.java))
       }
       mcardbestseller.setOnClickListener{
           startActivity(Intent(this, BestsellersActivity::class.java))
       }
       mcardauctionranking.setOnClickListener{
           startActivity(Intent(this, AuctionRankingActivity::class.java))
       }
       mcardprofile.setOnClickListener{
           startActivity(Intent(this, ProfileActivity::class.java))
       }

   }
}
