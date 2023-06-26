package com.example.auctionnearapp

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
class HomeActivity : AppCompatActivity() {
    @SuppressLint("WrongViewCast")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        val mCardAddHouses = findViewById<Button>(R.id.mcardaddauctions)
        mCardAddHouses.setOnClickListener {
            startActivity(Intent(this, AddAuctionsActivity::class.java))
        }
        val mCardViewHouses = findViewById<Button>(R.id.mcardviewauctions)
        mCardViewHouses.setOnClickListener {
            startActivity(Intent(this, ViewAuctionActivity::class.java))
        }
        val mCardLearnSigns = findViewById<Button>(R.id.mcardviewpastauctions)
        mCardLearnSigns.setOnClickListener {
            startActivity(Intent(this, ViewAuctionActivity::class.java))
        }
        val mCardSettings = findViewById<Button>(R.id.mcardbestsellers)
        mCardSettings.setOnClickListener {
            startActivity(Intent(this, BestsellersActivity::class.java))
        }
        val mCardRateUs = findViewById<Button>(R.id.mcardauctionrankings)
        mCardRateUs.setOnClickListener {
            startActivity(Intent(this, AuctionRankingActivity::class.java))
        }
        val mCardLogOut = findViewById<Button>(R.id.mcardprofile)
        mCardLogOut.setOnClickListener {
            startActivity(Intent(this, ProfileActivity::class.java))
        }
    }
}