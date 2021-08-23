package com.hianuy.todolist.project.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.TextView
import com.hianuy.todolist.R
import com.hianuy.todolist.project.helper.CustomItems
import java.util.ArrayList

class SpinnerAdapter(context: Context, customList: ArrayList<CustomItems?>?) :
    ArrayAdapter<CustomItems?>(context, 0, customList!!) {


    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        return customView(position, convertView, parent)!!
    }

    override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup): View? {
        return customView(position, convertView, parent)
    }

    private fun customView(position: Int, convertView: View?, parent: ViewGroup): View? {


        val view by lazy {
            LayoutInflater.from(context)
                .inflate(R.layout.custom_spinner_layout, parent, false)
        }
        val items: CustomItems = getItem(position)!!
        val spinnerImage = view!!.findViewById<ImageView>(R.id.ivCustomSpinner)
        val spinnerName = view.findViewById<TextView>(R.id.tvCustomSpinner)
        spinnerImage.setImageResource(items.spinnerImage)
        spinnerName.text = items.spinnerText
        return view
    }
}