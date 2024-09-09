package com.example.jollycat

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.volley.Request
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.Volley
import com.example.jollycat.databinding.FragmentHomeBinding
import org.json.JSONArray
import org.json.JSONException

class FragmentHome : Fragment() {

    private lateinit var binding: FragmentHomeBinding
    private lateinit var catAdapter: DevAdapter
    private var catList = ArrayList<Cats>()
    private var filteredCatList = ArrayList<Cats>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentHomeBinding.inflate(inflater, container, false)

        val recyclerView = binding.catsRV
        recyclerView.layoutManager = LinearLayoutManager(context)
        catAdapter = DevAdapter(requireContext(), filteredCatList)
        recyclerView.adapter = catAdapter

        fetchCatData()

        binding.searchEditText.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                filter(s.toString())
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        })

        return binding.root
    }

    private fun fetchCatData() {
        val url = "https://api.npoint.io/c9e4ca3ec5befe0e2b63"
        val requestQueue = Volley.newRequestQueue(context)

        val jsonArrayRequest = JsonArrayRequest(
            Request.Method.GET, url, null,
            { response ->
                Log.d("FragmentHome", "Response received: $response")
                catList.clear() // Clear the list before adding new items
                catList.addAll(parseJSON(response))
                filteredCatList.clear()
                filteredCatList.addAll(catList) // Initialize filtered list
                catAdapter.notifyDataSetChanged()// Notify adapter of data change
                Global.listCats = catList
            },
            { error ->
                Log.e("FragmentHome", "Error: ${error.message}")
                error.printStackTrace()
            }
        )

        requestQueue.add(jsonArrayRequest)
    }

    private fun parseJSON(jsonArray: JSONArray): ArrayList<Cats> {
        val catList = ArrayList<Cats>()
        try {
            for (i in 0 until jsonArray.length()) {
                val catObject = jsonArray.getJSONObject(i)
                val catID = catObject.getString("CatID")
                val catName = catObject.getString("CatName")
                val catImage = catObject.getString("CatImage")
                val catPrice = catObject.getInt("CatPrice")
                val catDescription = catObject.getString("CatDescription")

                Log.d("FragmentHome", "Parsed cat: $catName")
                catList.add(Cats(catID, catName, catDescription, catImage, catPrice))
            }
        } catch (e: JSONException) {
            Log.e("FragmentHome", "JSON Parsing error: ${e.message}")
            e.printStackTrace()
        }

        return catList
    }

    private fun filter(query: String) {
        val filteredList = catList.filter {
            it.catName.contains(query, ignoreCase = true) ||
                    it.catDescription.contains(query, ignoreCase = true)
        }
        filteredCatList.clear()
        filteredCatList.addAll(filteredList)
        catAdapter.updateList(filteredCatList)
    }
}
