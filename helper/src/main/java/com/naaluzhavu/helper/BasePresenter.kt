package com.naaluzhavu.helper

import android.content.Context
import android.util.Log
import com.google.gson.Gson
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import java.io.File
import java.text.SimpleDateFormat
import java.util.*

abstract class BasePresenter {
    private val TAG = BasePresenter::class.java.simpleName
    val REQUEST_CODE = 201;

    fun log(TAG: String, message: String) {
        Log.i(TAG, "nandhu " + message)
    }

    fun <T> getRetrofit(BASE_URL: String, targetClass: Class<T>): T {
        return Retrofit.Builder().baseUrl(BASE_URL).build().create(targetClass)
    }

    /*fun requestPermission(context: Context, coordinatorLayout: LinearLayout, message: String, permission: String) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(context as Activity,
                    permission)
                    != PackageManager.PERMISSION_GRANTED) {
                if (ActivityCompat.shouldShowRequestPermissionRationale(context,
                        permission)) {
//                    makeSnack(coordinatorLayout, message, "", permissionSnackBarListener())

                } else {
                    ActivityCompat.requestPermissions(context, arrayOf(permission), REQUEST_CODE)
                }
            } else {
                log(TAG, "requestPermission: " + "GRANTED BEFORE")
            }
        }
    }

    fun onPermissionRequestResult(coordinatorLayout: LinearLayout, message: String, requestCode: Int, permission: Array<out String>, grantResult: IntArray) {
        when (requestCode) {
            REQUEST_CODE -> {
                if (grantResult.size > 0
                        && grantResult[0] == PackageManager.PERMISSION_GRANTED) {
                    log(TAG, "onPermissionRequestResult: " + "GRANTED " + message + " " + requestCode)
                } else {
//                    makeSnack(coordinatorLayout, message, "", permissionSnackBarListener())
                }
            }
            else -> {
//                makeSnack(coordinatorLayout, message, "", permissionSnackBarListener())
            }
        }
    }

    private fun makeSnack(coordinatorLayout: LinearLayout, message: String, text: String, listener: View.OnClickListener) {
        val snackbar = Snackbar.make(coordinatorLayout, message, Snackbar.LENGTH_SHORT)
        snackbar.setAction(text, listener)
        snackbar.show()
    }

    fun permissionSnackBarListener(): View.OnClickListener {
        return View.OnClickListener {
        }
    }

    fun constructRequest(content: HashMap<String, String>): String {
        val gson = Gson()
        val json = gson.toJson(content)
        val request = JSONObject()
        val encryptedContent = AESCryptography.encryptAES(json.toString())
        request.put("content", encryptedContent)
        return request.toString()
    }

    fun destructResponse(respStr: String): JSONObject {
        val respObj = JSONObject(respStr)
        val decryptAES = AESCryptography.decryptAES(respObj.getString("content").toByteArray())
        return JSONObject(String(decryptAES))
    }*/

    fun <T> stringToPojo(info: String, targetClass: Class<Array<T>>): List<T> {
        val gson = Gson()
        val list = gson.fromJson(info, targetClass)
        if (list != null && list.size > 0) {
            return Arrays.asList(*list)
        } else {
            return ArrayList<T>()
        }
    }

    fun <T> stringToPojoObject(info: String, targetClass: Class<T>): T? {
        val gson = Gson()
        val obj = gson.fromJson(info, targetClass)
        if (obj != null) {
            return obj
        } else {
            return null
        }
    }

    fun <T> pojoToString(pojo: List<T>): String {
        return Gson().toJson(pojo)
    }

    fun writeToFile(context: Context, info: String, name: String) {
        File(context.filesDir, name).printWriter().use { out ->
            out.write(info)
        }
    }

    fun deleteFile(context: Context, name: String): Boolean {
        return File(context.filesDir, name).delete()
    }

    fun readFromFile(context: Context, name: String): String {
        return File(context.filesDir, name).bufferedReader().use { inR ->
            inR.readText()
        }
    }

    fun makeServerCall(call: Call<ResponseBody>, errorToastMessage: String, apiName: String) {
        call.enqueue(object : Callback<ResponseBody> {
            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                t.printStackTrace()
                onResponse(false, "Something went wrong while $errorToastMessage", apiName)
            }

            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                try {
                    log(TAG, errorToastMessage + " " + response.code())
                    if (response.isSuccessful) {
                        val respStr = response.body()!!.string()
                        onResponse(true, respStr, apiName)
                    } else {
                        onResponse(false, "Something went wrong while $errorToastMessage", apiName)

                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                    onResponse(false, "Something went wrong while $errorToastMessage", apiName)
                }
            }
        })
    }

    abstract fun onResponse(isSuccess: Boolean, respStr: String, apiName: String)

    fun formatMongoDate(date: String): Date {
        return SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.ENGLISH).parse(date)
    }
}