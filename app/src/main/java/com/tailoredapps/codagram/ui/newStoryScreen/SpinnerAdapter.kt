package com.tailoredapps.codagram.ui.newStoryScreen

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.BaseAdapter
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.tailoredapps.codagram.R
import com.tailoredapps.codagram.models.Group
import com.tailoredapps.codagram.remoteModels.GroupList


class SpinnerAdapter(context: Context,val data:ArrayList<GroupList>): BaseAdapter(){
    private val infalate:LayoutInflater

    init {
        infalate = LayoutInflater.from(context)
    }


    override fun getView(postition: Int, convertView: View?, parent: ViewGroup?): View? {
        var viewHolder:ViewHolder
        var view = convertView

        if (view == null){
            view = infalate.inflate(R.layout.custom_spinner_adapter,parent,false)
            viewHolder = ViewHolder(view)

        }else{
            viewHolder = view.tag as ViewHolder

        }
        view?.tag = viewHolder
        viewHolder.tvGroupName.text = data[postition].groups.toString()

        return view
    }

    override fun getItem(postition: Int): Any =data[postition]


    override fun getItemId(postition: Int): Long = postition.toLong()


    override fun getCount(): Int = data.size

    class ViewHolder(view:View){
        lateinit var tvGroupName:TextView

        init {
            tvGroupName = view.findViewById(R.id.nameTextView)
        }
    }



}