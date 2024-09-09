package com.example.jollycat

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.telephony.SmsManager
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.jollycat.databinding.FragmentCartBinding

class FragmentCart : Fragment() {

    companion object {
        private const val SMS_PERMISSION_CODE = 1001
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding: FragmentCartBinding = FragmentCartBinding.inflate(inflater, container, false)

        val recyclerView = binding.cartRV
        recyclerView.layoutManager = LinearLayoutManager(context)
        val newList: MutableList<Cart> = Global.listCart.filter { cart -> cart.userID == Global.currentSelectedUser } as MutableList<Cart>
        val adapter = CartAdapter(requireContext(), listCarts = newList)

        recyclerView.adapter = adapter
        if (Global.getUserCart().size <= 0) {
            binding.checkOutBtn.isEnabled = false
        }
        binding.checkOutBtn.setOnClickListener {
            Global.listCart.removeIf { cart -> cart.userID == Global.currentSelectedUser }
            Log.i("global", Global.listCart.toString())
            val checkoutID = Global.createRandomID()
            Toast.makeText(context, "Checkout ID Created: $checkoutID", Toast.LENGTH_LONG).show()
            sendSms(checkoutID)
            adapter.notifyDataSetChanged()
            binding.checkOutBtn.isEnabled = false
        }

        return binding.root
    }

    private fun sendSms(checkoutID: Int) {
        if (context?.let { ContextCompat.checkSelfPermission(it, Manifest.permission.SEND_SMS) } != PackageManager.PERMISSION_GRANTED) {
            requestSmsPermission()
        } else {
            val user = Global.listUser.find { it.userID == Global.currentSelectedUser }
            user?.let {
                val phoneNumber = it.phoneNumber
                val message = "Your checkout ID is $checkoutID"
                try {
                    val smsManager: SmsManager = SmsManager.getDefault()
                    smsManager.sendTextMessage(phoneNumber, null, message, null, null)
                    Toast.makeText(context, "SMS sent.", Toast.LENGTH_SHORT).show()
                } catch (e: Exception) {
                    Toast.makeText(context, "SMS failed, please try again.", Toast.LENGTH_SHORT).show()
                    e.printStackTrace()
                }
            }
        }
    }

    private fun requestSmsPermission() {
        requestPermissions(arrayOf(Manifest.permission.SEND_SMS), SMS_PERMISSION_CODE)
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == SMS_PERMISSION_CODE) {
            if ((grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                // Permission granted, proceed with sending the SMS
                Toast.makeText(context, "Permission granted. Please click checkout again to send SMS.", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(context, "Permission denied.", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
