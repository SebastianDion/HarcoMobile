package com.example.jollycat

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.jollycat.database.DatabaseHelper
import com.example.jollycat.databinding.FragmentProfileBinding

class FragmentProfile : Fragment() {

    private lateinit var databaseHelper: DatabaseHelper

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding: FragmentProfileBinding = FragmentProfileBinding.inflate(inflater, container, false)
        databaseHelper = DatabaseHelper(requireContext())

        val profileNameTV = binding.profileNameTV
        val profilePhoneTV = binding.profilePhoneTV
        val logOutBtn = binding.logOutBtn
        val deleteAccBtn = binding.DeleteAccBtn

        val currentUser = Global.listUser.find { it.userID == Global.currentSelectedUser }
        currentUser?.let { user ->
            profileNameTV.text = "Username: ${user.userName}"
            profilePhoneTV.text = "Phone Number: ${user.phoneNumber}"
        } ?: run {
            Log.e("FragmentProfile", "Current user not found in list.")
        }

        logOutBtn.setOnClickListener {
            activity?.finish()
            val intent = Intent(context, ActivityLogin::class.java)
            startActivity(intent)
        }

        deleteAccBtn.setOnClickListener {
            deleteCurrentUser()
        }

        return binding.root
    }

    private fun deleteCurrentUser() {
        Global.currentSelectedUser.takeIf { it != -1 }?.let { userId ->
            databaseHelper.deleteUser(userId)
            Global.listUser.removeAll { it.userID == userId }
            Global.currentSelectedUser = -1
            activity?.finish()
            val intent = Intent(context, ActivityLogin::class.java)
            startActivity(intent)
        }
    }
}
