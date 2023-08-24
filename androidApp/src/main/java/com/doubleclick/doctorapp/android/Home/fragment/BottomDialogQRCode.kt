package com.doubleclick.doctorapp.android.Home.fragment

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Point
import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.ImageView
import android.widget.Toast
import com.doubleclick.doctorapp.android.databinding.MyQrCodeBinding
import com.doubleclick.doctorapp.android.views.qrgenearator.QRGContents
import com.doubleclick.doctorapp.android.views.qrgenearator.QRGEncoder
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class BottomDialogQRCode(val code: String) : BottomSheetDialogFragment() {

    private lateinit var binding: MyQrCodeBinding
    private lateinit var bitmap: Bitmap
    private lateinit var qrgEncoder: QRGEncoder

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = MyQrCodeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (TextUtils.isEmpty(code)) {
            // if the edittext inputs are empty then execute
            // this method showing a toast message.
            Toast.makeText(
                requireContext(),
                "Enter some text to generate QR Code",
                Toast.LENGTH_SHORT
            ).show()
        } else {
            binding.qrImage.visibility = View.VISIBLE
            // below line is for getting
            // the windowmanager service.
            val manager =
                requireActivity().getSystemService(Context.WINDOW_SERVICE) as WindowManager

            // initializing a variable for default display.
            val display = manager.defaultDisplay

            // creating a variable for point which
            // is to be displayed in QR Code.
            val point = Point()
            display.getSize(point)

            // getting width and
            // height of a point
            val width = point.x
            val height = point.y

            // generating dimension from width and height.
            var dimen = if (width < height) width else height
            dimen = dimen * 3 / 4

            // setting this dimensions inside our qr code
            // encoder to generate our qr code.
            qrgEncoder = QRGEncoder(code, null, QRGContents.Type.TEXT, dimen)
            // getting our qrcode in the form of bitmap.
            bitmap = qrgEncoder.bitmap
            // the bitmap is set inside our image
            // view using .setimagebitmap method.
            binding.qrImage.setImageBitmap(bitmap)
        }

    }

}