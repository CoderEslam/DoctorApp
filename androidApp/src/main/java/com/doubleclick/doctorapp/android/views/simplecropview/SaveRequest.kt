package com.doubleclick.doctorapp.android.views.simplecropview

import android.graphics.Bitmap
import android.net.Uri
import com.doubleclick.doctorapp.android.views.simplecropview.callback.SaveCallback
import io.reactivex.Single

class SaveRequest(val cropImageView: CropImageView, val image: Bitmap) {
    private var compressFormat: Bitmap.CompressFormat? = null
    private var compressQuality = -1
    fun compressFormat(compressFormat: Bitmap.CompressFormat?): SaveRequest {
        this.compressFormat = compressFormat
        return this
    }

    fun compressQuality(compressQuality: Int): SaveRequest {
        this.compressQuality = compressQuality
        return this
    }

    private fun build() {
        if (compressFormat != null) {
            cropImageView.setCompressFormat(compressFormat)
        }
        if (compressQuality >= 0) {
            cropImageView.setCompressQuality(compressQuality)
        }
    }

    fun execute(saveUri: Uri?, callback: SaveCallback?) {
        build()
        cropImageView.saveAsync(saveUri!!, image, callback)
    }

    fun executeAsSingle(saveUri: Uri?): Single<Uri> {
        build()
        return cropImageView.saveAsSingle(image, saveUri!!)
    }
}
