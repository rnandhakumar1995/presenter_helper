package com.naaluzhavu.presenterlibrabry

import android.annotation.SuppressLint
import android.app.ProgressDialog
import android.content.DialogInterface
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import java.text.SimpleDateFormat
import java.util.*

@SuppressLint("Registered")
open class BaseViewActivity : AppCompatActivity() {
    fun showToast(msg: String) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
    }

    fun getText(tv: TextView?): String {
        if (tv != null) {
            return tv.text.toString()
        }
        return ""
    }

    fun isFilled(et: EditText?): Boolean {
        if (et == null) {
            return false
        }
        if (getText(et) == "" || getText(et).isEmpty()) {
            et.error = "Please complete me!"
            return false
        }
        return true
    }

    fun showAlert(msg: String, okText: String, cancelText: String, okBtnListener: DialogInterface.OnClickListener, cancelBtnListener: DialogInterface.OnClickListener, isCancelable: Boolean) {
        val alertDialog = AlertDialog.Builder(this).create()
        alertDialog.setMessage(msg)
        alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, okText, okBtnListener)
        alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, cancelText, cancelBtnListener)
        alertDialog.setCancelable(isCancelable)
        alertDialog.show()
    }

    fun showProgress(title: String?, msg: String = "Please wait..."): ProgressDialog {
        val progressDialog: ProgressDialog = ProgressDialog(this)
        if (title != null) {
            progressDialog.setTitle(title)
        }
        progressDialog.setMessage(msg)
        progressDialog.show()
        return progressDialog
    }

    fun hideProgress(progressDialog: ProgressDialog) {
        progressDialog.dismiss()
    }

    fun formatMongoDate(date: String): Date {
        return SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.ENGLISH).parse(date)
    }

    fun formatDate(date: Date): String {
        val calendar = Calendar.getInstance()
        calendar.time = date
        return calendar.get(Calendar.DATE).toString() + "-" +
                (calendar.get(Calendar.MONTH) + 1).toString() + "-" +
                calendar.get(Calendar.YEAR).toString()
    }
}