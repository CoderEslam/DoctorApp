package com.doubleclick.doctorapp.android.Home

import android.content.Intent
import android.os.Bundle
import android.preference.PreferenceManager
import android.view.Menu
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.PopupMenu
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.doubleclick.doctorapp.android.Adapters.AdapterNavigation
import com.doubleclick.doctorapp.android.Home.DoctorConfig.DoctorConfigActivity
import com.doubleclick.doctorapp.android.Home.Setting.SettingsActivity
import com.doubleclick.doctorapp.android.ItemNavigationListener
import com.doubleclick.doctorapp.android.Model.ItemNavigation
import com.doubleclick.doctorapp.android.Model.SideMenu
import com.doubleclick.doctorapp.android.R
import com.doubleclick.doctorapp.android.databinding.ActivityHomeBinding
import com.doubleclick.doctorapp.android.databinding.LogoutBinding
import com.doubleclick.doctorapp.android.utils.Constants
import com.doubleclick.doctorapp.android.utils.SessionManger.getCurrentUserEmail
import com.doubleclick.doctorapp.android.utils.SessionManger.getId
import com.doubleclick.doctorapp.android.utils.SessionManger.getName
import com.doubleclick.doctorapp.android.utils.SessionManger.logoutManger
import com.doubleclick.doctorapp.android.views.CircleImageView
import com.doubleclick.doctorapp.android.views.flowingdrawer.ElasticDrawer
import com.doubleclick.doctorapp.android.views.slidingrootnav.SlidingRootNav
import com.doubleclick.doctorapp.android.views.slidingrootnav.callback.LogoutListener
import com.google.zxing.client.android.Intents
import com.journeyapps.barcodescanner.ScanContract
import com.journeyapps.barcodescanner.ScanIntentResult
import io.ak1.pix.models.*
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch


var options = Options()

class HomeActivity : AppCompatActivity(), LogoutListener, ItemNavigationListener {

    private lateinit var navController: NavController
    private val barcodeLauncher = registerForActivityResult(
        ScanContract()
    ) { result: ScanIntentResult ->
        if (result.contents == null) {
            val originalIntent = result.originalIntent
            if (originalIntent == null) {
                Toast.makeText(
                    this@HomeActivity,
                    "Cancelled",
                    Toast.LENGTH_LONG
                ).show()
            } else if (originalIntent.hasExtra(Intents.Scan.MISSING_CAMERA_PERMISSION)) {
                Toast.makeText(
                    this@HomeActivity,
                    "Cancelled due to missing camera permission",
                    Toast.LENGTH_LONG
                ).show()
            }
        } else {
            Toast.makeText(
                this@HomeActivity,
                "${result.contents}",
                Toast.LENGTH_LONG
            ).show()
        }
    }


    lateinit var binding: ActivityHomeBinding

    @OptIn(DelicateCoroutinesApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val mainFragment = findViewById<View>(R.id.main_fragment)
        navController = Navigation.findNavController(this@HomeActivity, mainFragment.id);
        setSupportActionBar(binding.toolbar)
        val appBarConfiguration: AppBarConfiguration = AppBarConfiguration.Builder(
            R.id.homeFragment,
            R.id.favoriteFragment,
            R.id.bookFragment,
            R.id.profileFragment
        ).build()
        NavigationUI.setupActionBarWithNavController(
            this@HomeActivity,
            navController,
            appBarConfiguration
        );
        setupSmoothBottomMenu()
//        setStatusBackgroundColor()
//        hideActionBar()
        binding.drawerlayoutNormal.setTouchMode(ElasticDrawer.TOUCH_MODE_BEZEL)
//        binding.drawerlayoutNormal.toggleMenu()
        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)

        binding.openMenu.setOnClickListener {
            binding.drawerlayoutNormal.openMenu();
        }

        binding.recyclerLeftSide.adapter =
            AdapterNavigation(
                this@HomeActivity,
                listOf(
                    ItemNavigation(R.drawable.home, R.string.home, 0),
                    ItemNavigation(R.drawable.help_center, R.string.help, 1),
                    ItemNavigation(R.drawable.setting, R.string.settings, 2),
                    ItemNavigation(R.drawable.ic_baseline_manage_accounts_24, R.string.doctor, 3),
                )
            )

        GlobalScope.launch(Dispatchers.Main) {
            findViewById<TextView>(R.id.name).text = getName()
            findViewById<TextView>(R.id.user_contact).text = getCurrentUserEmail()
            Glide.with(this@HomeActivity).load(
                "${Constants.IMAGE_URL}${getName()}_${getId()}.jpg"
            ).diskCacheStrategy(DiskCacheStrategy.NONE)
                .skipMemoryCache(true)
                .into(findViewById<CircleImageView>(R.id.image_profile))

        }
    }


    private fun setStatusBackgroundColor() {
        window.decorView.setSystemUiVisibility(
            View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                    or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
        )
        window.statusBarColor = ContextCompat.getColor(this, R.color.black_transparent)
    }

    private fun hideActionBar() {
        if (supportActionBar != null) {
            supportActionBar!!.hide()
        }
    }

    private fun setupSmoothBottomMenu() {
        val popupMenu = PopupMenu(this@HomeActivity, binding.root)
        popupMenu.inflate(R.menu.menu_bottom)
        val menu: Menu = popupMenu.menu
        binding.bottomBar.setupWithNavController(menu, navController)
    }


    override fun onResume() {
        super.onResume()
        options = getOptionsByPreference(this)
    }

    private fun getOptionsByPreference(mActivity: HomeActivity): Options {
        val sp = PreferenceManager.getDefaultSharedPreferences(mActivity)
        return Options().apply {
            isFrontFacing = sp.getBoolean("frontFacing", false)
            ratio = when (sp.getString("ratio", "0")) {
                "1" -> Ratio.RATIO_4_3
                "2" -> Ratio.RATIO_16_9
                else -> Ratio.RATIO_AUTO
            }
            flash = when (sp.getString("flash", "0")) {
                "1" -> Flash.Disabled
                "2" -> Flash.On
                "3" -> Flash.Off
                else -> Flash.Auto
            }
            mode = when (sp.getString("mode", "0")) {
                "1" -> Mode.Picture
                "2" -> Mode.Video
                else -> Mode.All
            }
            videoOptions = VideoOptions().apply {
                videoDurationLimitInSeconds = try {
                    sp.getString("videoDuration", "30")?.toInt() ?: 30
                } catch (e: Exception) {
                    sp.apply {
                        edit().putString("videoDuration", "30").commit()
                    }
                    30
                }
            }
            count = try {
                sp.getString("count", "5")?.toInt() ?: 5
            } catch (e: Exception) {
                sp.apply {
                    edit().putString("count", "5").commit()
                }
                5
            }
            spanCount = sp.getString("spanCount", "4")?.toInt() ?: 4
        }
    }

    override fun onBackPressed() {
        val count = supportFragmentManager.backStackEntryCount
        if (count == 0) {
            super.onBackPressed()
            //additional code
        } else {
            supportFragmentManager.popBackStack()
        }
    }

    @OptIn(DelicateCoroutinesApi::class)
    override fun logout() {
        val builder = AlertDialog.Builder(this)
        val view = LogoutBinding.inflate(layoutInflater)
        view.ok.setOnClickListener {
            GlobalScope.launch(Dispatchers.Main) {
                logoutManger()
                finish()
            }
        }
        builder.setView(view.root)
        builder.show()

    }

    override fun itemNavigation(index: Int) {
        when (index) {
            0 -> {

            }
            1 -> {

            }
            2 -> {
                startActivity(Intent(this@HomeActivity, SettingsActivity::class.java))
            }
            3 -> {
                startActivity(Intent(this@HomeActivity, DoctorConfigActivity::class.java))
            }
        }
    }

}