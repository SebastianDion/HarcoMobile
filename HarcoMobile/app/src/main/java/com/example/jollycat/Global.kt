package com.example.jollycat

object Global {
    var listUser: MutableList<User> = mutableListOf()
    var listCart: MutableList<Cart> = mutableListOf()
    var currentSelectedUser: Int = -1

    fun createRandomID(): Int {
        return (Math.random() * Int.MAX_VALUE).toInt()
    }

    var listCats: MutableList<Cats> = mutableListOf()

    fun getUserCart(): MutableList<Cart> {
        val carts = mutableListOf<Cart>()
        for (cart in listCart) {
            if (cart.userID == currentSelectedUser) {
                carts.add(cart)
            }
        }
        return carts
    }

    fun updateCart(quantity: Int, cart: Cart) {
        for ((index, it) in listCart.withIndex()) {
            if (cart.cartID == it.cartID) {
                listCart[index].quantity = quantity
            }
        }
    }

    fun deleteCart(id: Int): Boolean {
        val iterator = listCart.iterator()
        while (iterator.hasNext()) {
            val cart = iterator.next()
            if (cart.cartID == id) {
                iterator.remove()
                return true
            }
        }
        return false
    }
}
