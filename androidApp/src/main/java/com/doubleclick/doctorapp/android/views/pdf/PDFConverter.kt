package com.doubleclick.smarthealth.android.pdf

import android.annotation.SuppressLint
import android.app.Activity
import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.drawable.Drawable
import android.graphics.pdf.PdfDocument
import android.net.Uri
import android.os.Build
import android.os.Environment
import android.util.DisplayMetrics
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.FileProvider
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import com.doubleclick.doctorapp.android.CreatePDF
import com.doubleclick.doctorapp.android.R
import java.io.File
import java.io.FileNotFoundException
import java.io.FileOutputStream
import java.text.SimpleDateFormat
import java.util.*


private const val TAG = "PDFConverter"

object PDFConverter {


    private fun createBitmapFromView(
        context: Context,
        view: View,
        pdfDetails: String?,
        activity: Activity,
    ): Bitmap {
        val doctor_name: TextView = view.findViewById(R.id.doctor_name);
        val specialization_name: TextView = view.findViewById(R.id.specialization_name);
        val patient_name: TextView = view.findViewById(R.id.patient_name);
        val date: TextView = view.findViewById(R.id.date);
        val prescription: ImageView = view.findViewById(R.id.prescription);
        doctor_name.text = pdfDetails


        Glide.with(view).asFile()
            .load("https://firebasestorage.googleapis.com/v0/b/fireapp-5b798.appspot.com/o/default_group_profile.png?alt=media&token=bd06dfb2-0970-4ffc-ab8b-e6fc455b6726")
            .into(object : CustomTarget<File>() {
                override fun onResourceReady(resource: File, transition: Transition<in File>?) {
                    //   =>   /data/user/0/com.doubleclick.smarthealth/cache/image_manager_disk_cache/a92237d32bf1c57aeb0fd39c11e099be166574c3dc25078bd51f40cf848f7379.0

                }

                override fun onLoadCleared(placeholder: Drawable?) {

                }

            });
        return createBitmap(context, view, activity)
    }


    private fun createBitmap(
        context: Context,
        view: View,
        activity: Activity,
    ): Bitmap {
        val displayMetrics = DisplayMetrics()
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            context.display?.getRealMetrics(displayMetrics)
            displayMetrics.densityDpi
        } else {
            activity.windowManager.defaultDisplay.getMetrics(displayMetrics)
        }
        view.measure(
            View.MeasureSpec.makeMeasureSpec(
                displayMetrics.widthPixels, View.MeasureSpec.EXACTLY
            ),
            View.MeasureSpec.makeMeasureSpec(
                displayMetrics.heightPixels, View.MeasureSpec.EXACTLY
            )
        )
        view.layout(0, 0, displayMetrics.widthPixels, displayMetrics.heightPixels)
        val bitmap = Bitmap.createBitmap(
            view.measuredWidth,
            view.measuredHeight, Bitmap.Config.ARGB_8888
        )

        val canvas = Canvas(bitmap)
        view.draw(canvas)
        /* resolution in bitmap */
        return Bitmap.createScaledBitmap(bitmap, 5000, 8500, true)
    }

    private fun convertBitmapToPdf(
        bitmap: Bitmap,
        pdfDetails: String?,
        context: Context,
        createPDF: CreatePDF
    ) {
        val pdfDocument = PdfDocument()
        val pageInfo = PdfDocument.PageInfo.Builder(bitmap.width, bitmap.height, 1).create()
        val page = pdfDocument.startPage(pageInfo)
        page.canvas.drawBitmap(bitmap, 0F, 0F, null)
        pdfDocument.finishPage(page)
        try {
            val filePath = File(
                Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).absoluteFile.toString() + "/Doctor/pdf"
            )
            if (!filePath.exists()) {
                filePath.mkdirs()
            }
            val file =
                File(
                    filePath,
                    "SmartHealth " + " " + System.currentTimeMillis()
                        .toString() + ".pdf"
                )
            pdfDocument.writeTo(FileOutputStream(file))
            pdfDocument.close()
            renderPdf(context, file, createPDF)
        } catch (e: FileNotFoundException) {
            Log.e(TAG, "convertBitmapToPdf: " + e.message);
        }

    }

    fun createPdf(
        context: Context,
        pdfDetails: String?,
        activity: Activity,
        createPDF: CreatePDF
    ) {
        createPDF.onStartPDF()
        val view = LayoutInflater.from(context).inflate(R.layout.pdf_page_sheet, null)
        try {
            val bitmap = createBitmapFromView(context, view, pdfDetails, activity)
            convertBitmapToPdf(bitmap, pdfDetails, activity, createPDF)
        } catch (e: IndexOutOfBoundsException) {
            Log.e(TAG, "createPdf: " + e.message);
        } catch (_: NullPointerException) {

        }

    }


    private fun renderPdf(context: Context, filePath: File, createPDF: CreatePDF) {
        val uri = FileProvider.getUriForFile(
            context,
            context.applicationContext.packageName + ".provider",
            filePath
        )
        val intent = Intent(Intent.ACTION_VIEW)
        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
        intent.setDataAndType(uri, "application/pdf")
        try {
            createPDF.onFinishPDF()
            context.startActivity(intent)
        } catch (e: ActivityNotFoundException) {
            Toast.makeText(context, "${e.message}", Toast.LENGTH_SHORT).show()
        }
    }
}