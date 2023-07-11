package com.example.auctionnearapp

// MenuAdapter.kt
import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class MenuAdapter(private val items: List<MenuItem>, private val context: Context) : RecyclerView.Adapter<MenuAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_menu, parent, false)
        return ViewHolder(view)
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = items[position]
        holder.titleTextView.text = item.title
        holder.itemView.isSelected = item.isSelected

        holder.itemView.setOnClickListener {
            // Handle item selection here
            item.isSelected = true
            notifyDataSetChanged()

            // Launch activity based on menu item
            when (position) {
                0 -> launchFineArtActivity()
                1 -> launchCollectiblesActivity()
                2 -> launchJewelryActivity()

                // Add cases for other menu items as needed
            }
        }
    }

    override fun getItemCount(): Int {
        return items.size
    }

    private fun launchFineArtActivity() {
        val intent = Intent(context, FineArtActivity::class.java)
        context.startActivity(intent)
    }

    private fun launchCollectiblesActivity() {
        val intent = Intent(context, CollectiblesActivity::class.java)
        context.startActivity(intent)
    }

    private fun launchJewelryActivity() {
        val intent = Intent(context, JewelryActivity::class.java)
        context.startActivity(intent)
    }

    // Add additional launch methods for other activities as needed

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val titleTextView: TextView = itemView.findViewById(R.id.titleTextView)
    }
}
