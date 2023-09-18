package com.doubleclick.doctorapp.android

import android.app.Application
import android.content.Context
//import android.webkit.PermissionRequest


/**
 * Created By Eslam Ghazy on 7/7/2022
 */

class BaseApplication : Application() {

    init {
        instance = this
    }

    override fun onCreate() {
        super.onCreate()

        val context: Context = applicationContext()

//        EmojiManager.install(GoogleEmojiProvider())
//        Dexter.withContext(this).withPermissions(
//            Manifest.permission.ACCESS_NETWORK_STATE,
//            Manifest.permission.INTERNET,
//            Manifest.permission.CAMERA,
//            Manifest.permission.READ_CONTACTS,
//            Manifest.permission.RECORD_AUDIO,
//            Manifest.permission.ACCESS_FINE_LOCATION,
//            Manifest.permission.ACCESS_WIFI_STATE,
//            Manifest.permission.ACCESS_COARSE_LOCATION,
//            Manifest.permission.WRITE_EXTERNAL_STORAGE,
//            Manifest.permission.READ_EXTERNAL_STORAGE
//        ).withListener(object : MultiplePermissionsListener {
//            override fun onPermissionsChecked(multiplePermissionsReport: MultiplePermissionsReport) {}
//            override fun onPermissionRationaleShouldBeShown(
//                p0: MutableList<PermissionRequest>?,
//                p1: PermissionToken?
//            ) {
//            }
//        }).check()
    }

    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(base)

    }

    companion object {
        private var instance: BaseApplication? = null

        fun applicationContext(): Context {
            return instance!!.applicationContext
        }


    }


}