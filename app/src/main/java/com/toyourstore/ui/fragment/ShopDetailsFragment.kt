package com.toyourstore.ui.fragment

import android.Manifest
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.content.pm.PackageManager
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.widget.PopupMenu
import androidx.core.content.ContextCompat
import androidx.lifecycle.lifecycleScope
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.toyourstore.R
import com.toyourstore.api.ApiCallingRequest
import com.toyourstore.service.CurrentLocationService
import com.toyourstore.utils.MyProgressDialog
import com.toyourstore.utils.PermissionUtils
import com.toyourstore.utils.PopupUtils
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.IOException
import java.util.*


class ShopDetailsFragment : BaseFragment() {

    var pd: MyProgressDialog? = null
    private var isLastLocation = false
    private lateinit var txtSave: TextView
    private var rootView: View? = null
    private lateinit var edtShopName : EditText
    private lateinit var edtShopOwnerName : EditText
    private lateinit var edtContactNo : EditText
    private lateinit var edtWorkingH  : EditText
    private lateinit var edtArea : EditText
    private lateinit var edtCity : EditText
    private lateinit var edtSate : EditText
    private lateinit var edtBusinessLine : EditText



    private val mCurrentLocationReceiver: BroadcastReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) {
            // Get extra data included in the Intent
            val latitude = intent.getDoubleExtra("latitude", 0.0);
            val longitude = intent.getDoubleExtra("longitude", 0.0);
            val speed = intent.getFloatExtra("speed", 0.0f);
            Log.e("Location:", "lat + long " + latitude + ":" + longitude)
            if (!isLastLocation) {
                isLastLocation = true
                pd!!.cancel()
                stopServiceForCurrentLocation()
//                 apiCallingForSaveShop()
            }
        }
    }


    private fun apiCallingForSaveShop() {
        pd!!.show()
        lifecycleScope.launch(Dispatchers.IO) {
            val apiCallingRequest = ApiCallingRequest()
            val params = HashMap<String, String>()
//            params["email"] = email
//            params["password"] = password
            try {
                val responseOfLogin =
                        apiCallingRequest.apiCallingForLogin(
                                params
                        )
                val message = responseOfLogin.message
                withContext(Dispatchers.Main) {
                    pd!!.cancel()

                }
            } catch (apiEx: IOException) {
                withContext(Dispatchers.Main) {
                    pd!!.cancel()
                }
            }
        }
    }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        rootView = inflater.inflate(R.layout.fragment_shop_details, container, false)
        txtSave = rootView?.findViewById(R.id.txtSave)!!
        edtShopName =rootView?.findViewById(R.id.edtShopName)!!
        edtShopOwnerName = rootView?.findViewById(R.id.edtShopOwnerName)!!
        edtContactNo = rootView?.findViewById(R.id.edtContactNo)!!
        edtWorkingH = rootView?.findViewById(R.id.edtWorkingH)!!
        edtArea = rootView?.findViewById(R.id.edtArea)!!
        edtCity = rootView?.findViewById(R.id.edtCity)!!
        edtSate = rootView?.findViewById(R.id.edtSate)!!
        edtBusinessLine = rootView?.findViewById(R.id.edtBusinessLine)!!

        return rootView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        pd = MyProgressDialog(requireContext(), R.drawable.icons8_loader)
        setTitle("Shop Details")
        hideMenu()
        showBack()
        showSearch()
        LocalBroadcastManager.getInstance(requireContext()).registerReceiver(mCurrentLocationReceiver, IntentFilter("current-location-event"))

        txtSave.setOnClickListener {
             if (isValidData()) {
                if (PermissionUtils.isPermissionForLocation(requireContext())) {
                    startServiceForCurrentLocation()
                } else {
                    PermissionUtils.requestPermissions(this, pERMISSION_ID)
                }
             }
        }

        edtCity.setOnClickListener {
            val menu = PopupMenu(requireContext(), it)
            menu.getMenu().add("Ahemdabad")
            menu.getMenu().add("Delhi")
            menu.show()
            menu.setOnMenuItemClickListener {
                edtCity.setText(it.title)
                return@setOnMenuItemClickListener true
            }
          }


        edtSate.setOnClickListener {
            val menu = PopupMenu(requireContext(), it)
            menu.getMenu().add("Gujrat")
            menu.getMenu().add("Delhi")
            menu.show()
            menu.setOnMenuItemClickListener {
                edtSate.setText(it.title)
                return@setOnMenuItemClickListener true
            }
        }

        edtBusinessLine.setOnClickListener {
            val menu = PopupMenu(requireContext(), it)
            menu.getMenu().add("Test1")
            menu.getMenu().add("Test2")
            menu.show()
            menu.setOnMenuItemClickListener {
                edtBusinessLine.setText(it.title)
                return@setOnMenuItemClickListener true
            }
        }

    }



    fun isValidData(): Boolean {
       val shopname = edtShopName.text.toString().trim()
        val ShopOwnerName = edtShopOwnerName.text.toString().trim()
        val ContactNo = edtContactNo.text.toString().trim()
        val WorkingH = edtWorkingH.text.toString().trim()
        val Area = edtArea.text.toString().trim()
        val City = edtCity.text.toString().trim()
        val BusinessLine = edtBusinessLine.text.toString().trim()
         val state = edtSate.text.toString().trim()

        if(TextUtils.isEmpty(shopname)) {
            PopupUtils.alertMessage(requireContext(), "Please enter shopname")
            return false
        } else if(TextUtils.isEmpty(ShopOwnerName)) {
            PopupUtils.alertMessage(requireContext(), "Please enter ShopOwnerName")
            return false
        }else if(TextUtils.isEmpty(ContactNo)) {
            PopupUtils.alertMessage(requireContext(), "Please enter ContactNo")
            return false
        }else if(TextUtils.isEmpty(WorkingH)) {
            PopupUtils.alertMessage(requireContext(), "Please enter WorkingH")
            return false
        }else if(TextUtils.isEmpty(Area)) {
            PopupUtils.alertMessage(requireContext(), "Please enter Area")
            return false
        }else if(TextUtils.isEmpty(City)) {
            PopupUtils.alertMessage(requireContext(), "Please enter City")
            return false
        }else if(TextUtils.isEmpty(BusinessLine)) {
            PopupUtils.alertMessage(requireContext(), "Please enter BusinessLine")
            return false
         }  else if(TextUtils.isEmpty(state)) {
            PopupUtils.alertMessage(requireContext(), "Please enter BusinessLine")
            return false
          }
           else{
            return true
        }
    }


    fun startServiceForCurrentLocation() {
        pd!!.show()
        val serviceIntent = Intent(requireContext(), CurrentLocationService::class.java)
        serviceIntent.putExtra("inputExtra", "Running Serice...")
        mainActivity.startService(serviceIntent)
    }

    fun stopServiceForCurrentLocation() {
        val stopSer = Intent(requireContext(), CurrentLocationService::class.java)
        mainActivity.stopService(stopSer)
    }


    private val pERMISSION_ID = 42
    override fun onRequestPermissionsResult(
            requestCode: Int, permissions: Array<String>,
            grantResults: IntArray
    ) {
        when (requestCode) {
            pERMISSION_ID -> {
                if (grantResults.isNotEmpty() && grantResults[0] ==
                        PackageManager.PERMISSION_GRANTED
                ) {
                    if ((ContextCompat.checkSelfPermission(
                                    requireContext(),
                                    Manifest.permission.ACCESS_FINE_LOCATION
                            ) === PackageManager.PERMISSION_GRANTED)
                    ) {
                        Log.e("===", "Permission Granted")
                        startServiceForCurrentLocation()
                    }
                } else {
                    Log.e("===", "Permission Denied")
                }
                return
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        LocalBroadcastManager.getInstance(requireContext())
                .unregisterReceiver(mCurrentLocationReceiver)
        stopServiceForCurrentLocation()

    }


}