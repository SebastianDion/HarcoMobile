package com.example.jollycat

import android.app.Dialog
import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.jollycat.databinding.CartCardBinding

class CartAdapter(private val context: Context, val listCarts: MutableList<Cart>): RecyclerView.Adapter<CartAdapter.MainViewHolder>() {
    inner class MainViewHolder(private val itemBinding: CartCardBinding): RecyclerView.ViewHolder(itemBinding.root) {
        val cartImage = itemBinding.cartImageIV
        val cartName = itemBinding.cartNameTV
        val cartPrice = itemBinding.cartPriceTV
        val cartQuantity = itemBinding.cartQuantityTV
        val cartTotalPrice = itemBinding.cartTotalPriceTV
        fun bind(cart: Cart){
            itemBinding.cartDeleteBtn.setOnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    val cartID = cart.cartID
                    if (Global.deleteCart(cartID)) {
                        listCarts.removeAt(position)
                        notifyItemRemoved(position)
                        notifyItemRangeChanged(position, listCarts.size)
                    }
                }
            }
            itemBinding.cartUpdateBtn.setOnClickListener {
                val dialog = Dialog(context)
                dialog.setCanceledOnTouchOutside(true)
                dialog.setContentView(R.layout.dialog_cart)
                val cartUpdateET = dialog.findViewById<EditText>(R.id.updateDialogET)
                val confirmUpdateBtn = dialog.findViewById<Button>(R.id.confirmUpdateBtn)
                val cancelUpdateBtn = dialog.findViewById<Button>(R.id.cancelUpdateBtn)
                cartUpdateET.setText(cart.quantity.toString())
                confirmUpdateBtn.setOnClickListener {
                    if(cartUpdateET.text.isEmpty() || cartUpdateET.text.toString().toInt() <= 0) {
                        Toast.makeText(context, "Input the correct quantity which is more than 1", Toast.LENGTH_SHORT).show()
                    } else {
                        Global.updateCart(cartUpdateET.text.toString().toInt(), cart)
                        notifyDataSetChanged()
                        dialog.dismiss()
                    }
                }
                cancelUpdateBtn.setOnClickListener {
                    dialog.dismiss()
                }
                dialog.show()
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainViewHolder {
        return MainViewHolder(CartCardBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun getItemCount(): Int {
        return listCarts.size
    }

    override fun onBindViewHolder(holder: MainViewHolder, position: Int) {
        val cart = listCarts[position]
        val cat = Global.listCats.find { it.catID == cart.catID }
        if (cat != null) {
            Glide.with(context)
                .load(cat.catImage)
                .into(holder.cartImage)
            holder.cartName.text = cat.catName
            holder.cartPrice.text = "Price: " + cat.catPrice.toString()
            holder.cartQuantity.text = "Quantity: " + cart.quantity.toString()
            holder.cartTotalPrice.text = "Total Price: " + (cart.quantity * cat.catPrice).toString()
        } else {
            Log.e("CartAdapter", "Cat not found for ID: ${cart.catID}")
        }
        holder.bind(cart)
    }
}
