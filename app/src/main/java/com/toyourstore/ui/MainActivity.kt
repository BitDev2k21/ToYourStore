package com.toyourstore.ui

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.text.TextUtils
import android.util.Log
import android.view.Gravity
import android.view.Menu
import android.view.View
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import com.toyourstore.R
import com.toyourstore.api.ApiCallingRequest
import com.toyourstore.preference.SessionData
import com.toyourstore.ui.fragment.RegisterDistributorProcessFragment
import com.toyourstore.utils.MyProgressDialog
import com.toyourstore.utils.PopupUtils
import de.footprinttech.wms.db.DataBaseHelper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.IOException
import java.util.HashMap


class MainActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var txtAgencyDetail: TextView
    private lateinit var drawerLayout: DrawerLayout
    lateinit var navController: NavController
    private lateinit var txtAssociteCom: TextView

    //txtInventry
    private lateinit var txtInventry: TextView
    private lateinit var txtOrder: TextView
    private lateinit var txtLogOut: TextView
    private lateinit var rlProfile: RelativeLayout
    private lateinit var txtHome: TextView
    private lateinit var txtSales: TextView
    private lateinit var txtOperators: TextView

    //need to change in fragmnet
    lateinit var txtTitle: TextView
    lateinit var imgMenu: ImageView
    lateinit var imgBack: ImageView
    lateinit var imgSearch: ImageView
    lateinit var imgGift: ImageView
    lateinit var txtSideMenuName: TextView
    lateinit var txtSideMenyType: TextView
    lateinit var txtWorkForce: TextView
    private lateinit var pd: MyProgressDialog
    override fun onResume() {
        super.onResume()
        lifecycleScope.launch(Dispatchers.IO) {
            val user = DataBaseHelper.getDatabaseDao(this@MainActivity)
                    .getUser(SessionData.getInstance(this@MainActivity).getUserId()!!.toLong())
            withContext(Dispatchers.Main) {
                txtSideMenuName.text = user.name
                txtSideMenyType.text = user.userType
            }
        }

    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        drawerLayout = findViewById(R.id.drawer_layout)
        drawerLayout.setDrawerElevation(0.0f)
//        drawerLayout.setScrimColor(Color.TRANSPARENT)
        txtAgencyDetail = findViewById(R.id.txtAgencyDetail)
        imgMenu = findViewById(R.id.imgMenu)
        imgSearch = findViewById(R.id.imgSearch)
        txtAssociteCom = findViewById(R.id.txtAssociteCom)
        txtInventry = findViewById(R.id.txtInventry)
        txtOrder = findViewById(R.id.txtOrder)
        txtWorkForce = findViewById(R.id.txtWorkForce)
        txtLogOut = findViewById(R.id.txtLogOut)
        txtTitle = findViewById(R.id.txtTitle)
        imgBack = findViewById(R.id.imgBack)
        imgGift = findViewById(R.id.imgGift)
        rlProfile = findViewById(R.id.rlProfile)
        txtHome = findViewById(R.id.txtHome)
        txtSideMenuName = findViewById(R.id.txtSideMenuName)
        txtSideMenyType = findViewById(R.id.txtSideMenyType)
        txtSales = findViewById(R.id.txtSales)
        txtOperators = findViewById(R.id.txtOperators)
        navController = findNavController(R.id.nav_host_fragment)
        pd = MyProgressDialog(this, R.drawable.icons8_loader)
        imgMenu.setOnClickListener {
            drawerLayout.openDrawer(Gravity.LEFT)
        }
        txtHome.isSelected = true

        txtHome.setOnClickListener {
            selectedSideMenu(txtHome)
            navController.navigate(R.id.nav_home)
        }

        txtAgencyDetail.setOnClickListener {
            selectedSideMenu(txtAgencyDetail)
            navController.navigate(R.id.agencyDetailsFragment)
        }

        txtInventry.setOnClickListener {
            selectedSideMenu(txtInventry)
            navController.navigate(R.id.inventoryFragment)
        }

        txtAssociteCom.setOnClickListener {
            selectedSideMenu(txtAssociteCom)
            navController.navigate(R.id.associatedCompaniesFragment)
        }

        txtOrder.setOnClickListener {
            selectedSideMenu(txtOrder)
            navController.navigate(R.id.orderListFragment)
        }

        txtSales.setOnClickListener {
            selectedSideMenu(txtSales)
            navController.navigate(R.id.salesFragment)
        }

        txtOperators.setOnClickListener {
            selectedSideMenu(txtOperators)
            navController.navigate(R.id.operatorFragment)
        }

        txtLogOut.setOnClickListener {
            selectedSideMenu(txtLogOut)
            Handler().postDelayed({
                PopupUtils.confirmationDailg(this, {
                    if (it.equals("Yes", true)) {
                        SessionData.getInstance(this).clearData()
                        startActivity(Intent(this, LoginActivity::class.java))
                        finishAffinity()
                    }
                })
            }, 100)
        }

        imgSearch.setOnClickListener {
            navController.navigate(R.id.searchFragment)
        }

        imgBack.setOnClickListener {
            onBackPressed()
        }

        rlProfile.setOnClickListener {
            drawerLayout.closeDrawer(Gravity.LEFT)
            navController.navigate(R.id.profileFragment)
        }
        if(!SessionData.getInstance(this@MainActivity).isRegisterDis()){
            checkIfShopUpdated()
        }

        txtWorkForce.setOnClickListener {
            if (txtSales.isVisible) {
                txtSales.visibility = View.GONE
                txtOperators.visibility = View.GONE
            } else {
                txtSales.visibility = View.VISIBLE
                txtOperators.visibility = View.VISIBLE
            }
        }

    }


    fun getForegroundFragment(): Fragment? {
        val navHostFragment: Fragment? =
                supportFragmentManager.findFragmentById(R.id.nav_host_fragment)
        return if (navHostFragment == null) null else navHostFragment.getChildFragmentManager()
                .getFragments().get(0)
    }

    override fun onBackPressed() {
        if (getForegroundFragment() is RegisterDistributorProcessFragment) {
        } else {
            super.onBackPressed()
        }

    }

    fun selectedSideMenu(textView: TextView) {
        drawerLayout.closeDrawer(Gravity.LEFT)
        txtAgencyDetail.isSelected = false
        txtInventry.isSelected = false
        txtOrder.isSelected = false
        txtLogOut.isSelected = false
        txtHome.isSelected = false
        txtAssociteCom.isSelected = false
        txtSales.isSelected = false
        txtOperators.isSelected = false
        textView.isSelected = true
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.main, menu)
        return true
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }

    private fun checkIfShopUpdated() {
        pd.show()
        lifecycleScope.launch(Dispatchers.IO) {
            val apiCallingRequest = ApiCallingRequest()
            val apiToken = SessionData.getInstance(this@MainActivity).getToken()
            val userId = SessionData.getInstance(this@MainActivity).getUserId()
            val params = HashMap<String, String>()
            params["user_id"] = userId?:""
            params["api_token"] = apiToken?:""
            try {
                val responseOfLogin =
                    apiCallingRequest.getUser(
                        params
                    )
                Log.v("@@@","checkIfShopUpdated resp ${responseOfLogin.toString()} ")
                withContext(Dispatchers.Main) {
                    pd.cancel()
                    if(responseOfLogin!=null &&  responseOfLogin.user!=null && responseOfLogin.user.size>0 ){
                        val user = responseOfLogin.user.first()
                        if(user.distributor_details!=null){
                            val disDetails = user.distributor_details.first()
                            val name = disDetails.name
                            val owner_name = disDetails.owner_name
                            val contact = disDetails.contact
                            val working_hours = disDetails.working_hours
                            val gst_no = disDetails.gst_no
                            val routes = disDetails.routes
                            val credit_period = disDetails.credit_period
                            val address = disDetails.address
                            val user_id = disDetails.user_id
                            val state_id = disDetails.state_id
                            val city_id = disDetails.city_id
                            val status = disDetails.status
                            if(
                                name.isNullOrEmpty() ||owner_name.isNullOrEmpty() ||contact.isNullOrEmpty()||working_hours.isNullOrEmpty()||
                                gst_no.isNullOrEmpty() ||routes.isNullOrEmpty() ||credit_period.isNullOrEmpty()||address.isNullOrEmpty()||
                                user_id.isNullOrEmpty() ||state_id.isNullOrEmpty() ||city_id.isNullOrEmpty()||status.isNullOrEmpty()
                            ){
                                navController.navigate(R.id.registerDistributorProcessFragment)
                            }else{
                                navController.navigate(R.id.registerDistributorComplete)
                                SessionData.getInstance(this@MainActivity).saveIsRegister(true)
                                Toast.makeText(this@MainActivity, "Successfully Updated", Toast.LENGTH_SHORT).show()
                            }
                        }
                    }
                }
            } catch (apiEx: Exception) {
                Log.v("@@@","checkIfShopUpdated resp ${apiEx.message.toString()} ")
                withContext(Dispatchers.Main) {
                    pd.cancel()
                }
            }
        }
    }
}