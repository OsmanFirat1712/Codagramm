package com.tailoredapps.codagram.ui.newStoryScreen

import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.lifecycle.ViewModel
import com.tailoredapps.codagram.remote.CodagramApi

class NewStoryViewModel(private val codagramApi: CodagramApi):ViewModel() {

    fun infoMessage(statusIcon: ImageView){
        statusIcon.setOnClickListener {
            Toast.makeText(context,"Group name have to more than 6 character", Toast.LENGTH_LONG).show()
        }
    }

    fun statusRules(groupEditText: String,statusIcon: ImageView){
        when{
            groupEditText.isNotEmpty() -> {
                Toast.makeText(context,"Group name cannot be Empty!",Toast.LENGTH_LONG).show()
                statusIcon.setImageDrawable()
            }
            else ->{
                Toast.makeText(context,"Successfully!",Toast.LENGTH_LONG).show()
                statusIcon.setImageDrawable()
            }

        }
    }

}