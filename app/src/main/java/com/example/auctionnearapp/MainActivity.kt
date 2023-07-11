package com.example.auctionnearapp


import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var menuAdapter: MenuAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        recyclerView = findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)

        val menuItems = listOf(
            MenuItem("Fine Art Auctions", isSelected = true),
            MenuItem("Collectibles Auctions"),
            MenuItem("Jewelry and Luxury Auctions")
        )

        menuAdapter = MenuAdapter(menuItems, this) // Pass 'this' as the context
        recyclerView.adapter = menuAdapter
    }
}

// Example activity classes to launch
class FineArtActivity : AppCompatActivity()

class CollectiblesActivity : AppCompatActivity()
class JewelryActivity : AppCompatActivity()
