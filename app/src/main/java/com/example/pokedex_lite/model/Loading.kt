package com.example.pokedex_lite.model

import android.app.Activity
import android.app.AlertDialog
import com.example.pokedex_lite.R

class Loading(val mActivity:Activity) {
    private lateinit var isDialog:AlertDialog

    fun startLoading(){
        val inflater = mActivity.layoutInflater
        val dialogView = inflater.inflate(R.layout.progress_bar_loading,null)
        dialogView.height
        val builder = AlertDialog.Builder(mActivity)
        builder.setView(dialogView)
        builder.setCancelable(false)
        isDialog = builder.create()
        isDialog.show()
    }

    fun isDismiss(){
        isDialog.dismiss()
    }
}