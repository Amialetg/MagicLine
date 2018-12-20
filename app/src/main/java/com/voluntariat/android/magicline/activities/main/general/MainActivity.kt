package com.voluntariat.android.magicline.activities.main.general

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import com.onesignal.OneSignal
import com.voluntariat.android.magicline.R
import com.voluntariat.android.magicline.activities.main.fragments.*
import com.voluntariat.android.magicline.data.MagicLineRepositoryImpl
import com.voluntariat.android.magicline.data.Result
import com.voluntariat.android.magicline.data.api.MagicLineAPI
import com.voluntariat.android.magicline.utils.ExampleNotificationOpenedHandler
import androidx.fragment.app.Fragment
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.voluntariat.android.magicline.activities.main.fragments.DonationsFragment
import com.voluntariat.android.magicline.activities.main.fragments.InfoFragment
import com.voluntariat.android.magicline.activities.main.fragments.MagicLineFragment
import com.voluntariat.android.magicline.activities.main.fragments.MapFragment
import com.voluntariat.android.magicline.activities.main.fragments.ScheduleFragment
import com.voluntariat.android.magicline.utils.transitionWithModalAnimation
import java.io.Serializable

class MainActivity : BaseActivity() {

    //Bottom Toolbar
    private lateinit var bottomBarView: com.ittianyu.bottomnavigationviewex.BottomNavigationViewEx
    private lateinit var bottomBarBtn: FloatingActionButton

    //The app starts at the magic line fragment
//    private var currentFragment: Fragment = MagicLineFragment()

    private lateinit var currentFragment: Fragment


//


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)

        initWidgets()

        initBottomBar()

        if (savedInstanceState == null) {
            //First time we open the app
            val transaction = this.supportFragmentManager.beginTransaction()
            transaction.replace(R.id.frame_layout, MagicLineFragment())
            transaction.commit()

            if (intent.hasExtra("From")) {
                navigateFromIntentExtra(intent.extras?.get("From") as Serializable?)
            }
        }
        initNavigation()
    }

    private fun initWidgets() {

        //BottomBar
        bottomBarView = findViewById(R.id.bottom_navigation)
        bottomBarBtn = findViewById(R.id.fab)
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

    private fun initBottomBar() {

        bottomBarView.enableShiftingMode(false)
        bottomBarView.enableItemShiftingMode(false)
        bottomBarView.setTextSize(9.0f)

    }

     fun initNavigation() {

        //Behaviour when clicked on a item different from map
        bottomBarView.setOnNavigationItemSelectedListener { item -> bottomBarBtn.setColorFilter(Color.argb(255,74,74,74))

            selectFragment(item)
            true
        }

        //Behaviour when clicked on the map item
        bottomBarBtn.setOnClickListener {

            bottomBarBtn.setColorFilter(Color.argb(255,237,53,37))

            //We set clicked on the none item in order to disable the rest of the items
            //but the fragment that is shown is the map fragment
            bottomBarView.menu.getItem(2).isChecked = true

            val transaction = this.supportFragmentManager.beginTransaction()
            transaction.replace(R.id.frame_layout, MapFragment())
            transaction.commit()
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        this.supportFragmentManager.popBackStack()
        return true
    }

    private fun selectFragment(item: MenuItem) {

        val newFragment: Fragment

        when (item.itemId) {
            R.id.magicline_menu_id -> {
                newFragment = MagicLineFragment()
                Log.d("Main Activity", "magic line")
            }
            R.id.donations_menu_id -> {
                newFragment = DonationsFragment()
                Log.d("Main Activity", "donations")
            }
            R.id.info_menu_id -> {

                newFragment = InfoFragment()
                Log.d("Main Activity", "info")
            }
            R.id.schedule_menu_id -> newFragment = ScheduleFragment()
            R.id.none -> return
            else -> newFragment = MagicLineFragment()
        }

        navigateToFragment(newFragment)
    }
    private fun navigateToFragment(newFragment: Fragment){

        val trans = this.supportFragmentManager.beginTransaction()
        trans.replace(R.id.frame_layout, newFragment)
//        trans.addToBackStack(newFragment.javaClass.canonicalName)
        trans.commit()

        currentFragment = newFragment

    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        setIntent(Intent().apply { putExtra("From", "ferDonacio") })
        val extra = intent?.getSerializableExtra("From")
        navigateFromIntentExtra(extra)
    }
    private fun navigateFromIntentExtra(extra: Serializable?) {
        when (extra) {
            "ultimaNoticia" ->{
                this.transitionWithModalAnimation(InviteFriendsFragment()) //navigation to the las news TODO: cambiar Fragment
            }
            "ferDonacio" -> {
                this.transitionWithModalAnimation(DonationsFragment())
            }
            "detallsEsdeveniments" -> {
                this.transitionWithModalAnimation(ScheduleFragment()) //Especificar qué Fragment
            }
            else -> {
                this.transitionWithModalAnimation(MagicLineFragment())
            }
        }
    }
}
