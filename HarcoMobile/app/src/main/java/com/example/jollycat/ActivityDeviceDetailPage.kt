package com.example.jollycat

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.example.jollycat.databinding.ActivityDeviceDetailPageBinding

class ActivityCatsDetailPage : AppCompatActivity() {
    private lateinit var binding: ActivityDeviceDetailPageBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDeviceDetailPageBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val catID = intent.getStringExtra("CatID")
        val cat = Global.listCats.find { it.catID == catID }

        if (cat != null) {
            binding.detailCatDescTV.text = "Description:" + cat.catDescription
            binding.detailCatPriceTV.text = "" + cat.catPrice + " Robux"
            binding.detailCatNameTV.text = cat.catName
            Glide.with(this)
                .load(cat.catImage)
                .into(binding.catImageIV)
        } else {
            Toast.makeText(this, "Cat not found", Toast.LENGTH_SHORT).show()
            finish()
        }

        binding.purchaseBtn.setOnClickListener {
            val quantityET = binding.quantityET.text
            if (quantityET.isEmpty() || quantityET.toString().toInt() < 0) {
                Toast.makeText(this, "Please input the correct quantity", Toast.LENGTH_SHORT).show()
            } else {
                val cart = Cart(Global.createRandomID(), catID!!, 0.0, Global.currentSelectedUser, quantityET.toString().toInt())
                Global.listCart.add(cart)
                Log.i("ts", Global.listCart.toString())
                finish()
            }
        }
    }
}
