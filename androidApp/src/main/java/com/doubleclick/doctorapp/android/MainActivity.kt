package com.doubleclick.doctorapp.android

import android.content.Intent
import android.graphics.Typeface
import android.os.Bundle
import android.widget.Toast
import com.doubleclick.doctorapp.android.Home.HomeActivity
import com.doubleclick.doctorapp.android.views.onboarder.AhoyOnboarderActivity
import com.doubleclick.doctorapp.android.views.onboarder.AhoyOnboarderCard
import io.ak1.pix.models.Options



class MainActivity : AhoyOnboarderActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_main)
        val pages: List<AhoyOnboarderCard> = listOf(
            AhoyOnboarderCard(
                "Find Trusted Doctors",
                "Contrary to popular belief, Lorem Ipsum is not simply random text. It has roots in a piece of it over 2000 years old",
                R.drawable.ellipse3,
                R.color.black_transparent
            ),
            AhoyOnboarderCard(
                "Choose Best Doctors",
                "Contrary to popular belief, Lorem Ipsum is not simply random text. It has roots in a piece of it over 2000 years old",
                R.drawable.ellipse4,
                R.color.black_transparent
            ),
            AhoyOnboarderCard(
                "Easy Appointments",
                "Contrary to popular belief, Lorem Ipsum is not simply random text. It has roots in a piece of it over 2000 years old",
                R.drawable.ellipse5,
                R.color.black_transparent
            )
        )

        for (page in pages) {
            page.setTitleColor(R.color.black)
            page.setDescriptionColor(R.color.grey_text)
        }

        setFinishButtonTitle("Sign in")
        showNavigationControls(true)
//        setGradientBackground()

        setColorBackground(
            listOf(
                R.color.white,
                R.color.white,
                R.color.white
            )
        )


        val face = Typeface.createFromAsset(assets, "fonts/Rubik-Black.ttf")
        setFont(face)

        setOnboardPages(pages)

    }

    override fun onFinishButtonPressed() {
        startActivity(Intent(this, HomeActivity::class.java))
    }
}

