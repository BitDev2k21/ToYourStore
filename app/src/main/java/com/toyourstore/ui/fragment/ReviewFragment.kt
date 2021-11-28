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
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.lifecycle.lifecycleScope
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.toyourstore.R
import com.toyourstore.api.ApiCallingRequest
import com.toyourstore.model.CityResponse
import com.toyourstore.model.GetLocation
import com.toyourstore.model.StateResponse
import com.toyourstore.model.businessline.Businessline
import com.toyourstore.preference.SessionData
import com.toyourstore.service.CurrentLocationService
import com.toyourstore.utils.MyProgressDialog
import com.toyourstore.utils.PermissionUtils
import com.toyourstore.utils.PopupUtils
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.IOException
import java.util.HashMap

class ReviewFragment : BaseFragment() {

    private lateinit var txtConfirm: TextView
    private lateinit var txtCancel: TextView
    private var ownerName = ""
    private var partner = ""
    private var contactNum = ""
    private var agency = ""
    private var officeContact = ""
    private var operationArea = ""
    private var gstName = ""
    private var state = ""
    private var selectedCity: CityResponse.City? = null
    private var selectedState: StateResponse.State? = null
    private var selectedLocation: GetLocation.Location? = null
    private var city = ""
    private var area = ""
    private var creditDay = ""
    private var routes = ""
    private var areaCode = ""
    private var businessLine = ""
    private var selectedBusinessline: Businessline? = null
    private var address = ""

    private lateinit var txtOwnerName: TextView
    private lateinit var txtPatnerName: TextView
    private lateinit var txtContactName: TextView
    private lateinit var txtEmail: TextView
    private lateinit var txtGstNo: TextView
    private lateinit var txtState: TextView
    private lateinit var txtAgencyName: TextView
    private lateinit var txtcity: TextView
    private lateinit var txtAddress: TextView
    private lateinit var txtCreditPeriod: TextView
    private lateinit var txtRoutine: TextView
    private lateinit var txtOperationArea: TextView
    private lateinit var txtOPerationCode: TextView
    private lateinit var txtBusinessLine: TextView
    private lateinit var pd: MyProgressDialog
    private var isLastLocation = false

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
                apiCallingForDistributorProcess(latitude = latitude.toString(),longitude = longitude.toString())
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        LocalBroadcastManager.getInstance(requireContext())
            .unregisterReceiver(mCurrentLocationReceiver)
        stopServiceForCurrentLocation()
    }

    fun stopServiceForCurrentLocation() {
        val stopSer = Intent(requireContext(), CurrentLocationService::class.java)
        mainActivity.stopService(stopSer)
    }

    fun startServiceForCurrentLocation() {
        pd!!.show()
        val serviceIntent = Intent(requireContext(), CurrentLocationService::class.java)
        serviceIntent.putExtra("inputExtra", "Running Serice...")
        mainActivity.startService(serviceIntent)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        ownerName = arguments?.getString("ownerName", "")!!
        partner = arguments?.getString("partner", "")!!
        contactNum = arguments?.getString("contactNum", "")!!
        agency = arguments?.getString("agency", "")!!
        officeContact = arguments?.getString("officeContact", "")!!
        operationArea = arguments?.getString("operationArea", "")!!
        gstName = arguments?.getString("gstName", "")!!
        selectedState = (arguments?.getSerializable("state") as StateResponse.State?)!!
        selectedCity = arguments?.getSerializable("city") as CityResponse.City?
        selectedLocation = arguments?.getSerializable("area") as GetLocation.Location?
        creditDay = arguments?.getString("creditDay", "")!!
        routes = arguments?.getString("routes", "")!!
//        areaCode = arguments?.getString("areaCode", "")!!
         selectedBusinessline = arguments?.getSerializable("businessLine") as Businessline?
        address = arguments?.getString("address", "")!!
    }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        val root = inflater.inflate(R.layout.fragment_review, container, false)
        txtOwnerName = root.findViewById(R.id.txtOwnerName)
        txtPatnerName = root.findViewById(R.id.txtPatnerName)
        txtContactName = root.findViewById(R.id.txtContactName)
        txtEmail = root.findViewById(R.id.txtEmail)
        txtGstNo = root.findViewById(R.id.txtGstNo)
        txtState = root.findViewById(R.id.txtState)
        txtAgencyName = root.findViewById(R.id.txtAgencyName)
        txtcity = root.findViewById(R.id.txtcity)
        txtAddress = root.findViewById(R.id.txtAddress)
        txtCreditPeriod = root.findViewById(R.id.txtCreditPeriod)
        txtRoutine = root.findViewById(R.id.txtRoutine)
        txtOperationArea = root.findViewById(R.id.txtOperationArea)
        txtOPerationCode = root.findViewById(R.id.txtOPerationCode)
        txtBusinessLine = root.findViewById(R.id.txtBusinessLine)
        pd = MyProgressDialog(requireContext(), R.drawable.icons8_loader)

        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        txtConfirm = view.findViewById(R.id.txtConfirm)
        txtCancel = view.findViewById(R.id.txtCancel)
        setTitle("Review")
        showBack()
        hideSearch()
        hideMenu()
        setDefaultValue()
        txtConfirm.setOnClickListener {

            if (PermissionUtils.isPermissionForLocation(requireContext())) {
                if(PermissionUtils.checkLocationEnabled(requireContext(),requireActivity())){
                    startServiceForCurrentLocation()
                }
            } else {
                PermissionUtils.requestPermissions(this, pERMISSION_ID)
            }
        }
        LocalBroadcastManager.getInstance(requireContext()).registerReceiver(mCurrentLocationReceiver, IntentFilter("current-location-event"))
    }

    private fun setDefaultValue() {
        txtOwnerName.text = ownerName
        txtPatnerName.text = partner
        txtContactName.text = contactNum
        txtGstNo.text = gstName
        txtState.text = selectedState!!.name
        txtAgencyName.text = agency
        txtcity.text = selectedCity!!.name
        txtAddress.text = address
        txtCreditPeriod.text = creditDay
        txtRoutine.text = routes
        txtOperationArea.text = selectedLocation?.name
        txtOPerationCode.text = operationArea
        txtBusinessLine.text = selectedBusinessline!!.name
    }

    private fun apiCallingForDistributorProcess(latitude:String,longitude:String) {
        pd.show()
        lifecycleScope.launch(Dispatchers.IO) {
            val apiCallingRequest = ApiCallingRequest()
            val params = HashMap<String, String>()
            params["user_id"] = SessionData.getInstance(requireContext()).getUserId()!!
            params["api_token"] = SessionData.getInstance(requireContext()).getToken()!!
            params["name"] = agency
            params["owner_name"] = ownerName
            params["contact"] = contactNum
            params["working_hours"] = "9-8"
            params["gst_no"] = gstName
            params["routes"] = routes
            params["credit_period"] = creditDay
            params["address"] = address
            params["state_id"] = selectedState!!.id.toString()
            params["city_id"] = selectedCity!!.id.toString()
            params["latitude"] = latitude
            params["longitude"] = longitude
            params["companies"] = "1,2"
            params["businesslines"] = selectedBusinessline?.id.toString()
            params["locations"] = selectedLocation?.id.toString()
            try {
                val responseOfLogin =
                        apiCallingRequest.apiCallingForUpdateDist(
                                params
                        )

                if(responseOfLogin!=null){
                    withContext(Dispatchers.Main) {
                        pd.cancel()
                        mainActivity.navController.navigate(R.id.registerDistributorComplete)
                        SessionData.getInstance(requireContext()).saveIsRegister(true)
                        Toast.makeText(requireContext(), "Successfully Updated", Toast.LENGTH_SHORT).show()
//                    if(responseOfLogin.message!=null && responseOfLogin.message.toString().length>2){
//                        val message = responseOfLogin.message
//                        PopupUtils.alertMessage(requireContext(), message!!)
//                    }else{
//
//                    }
                    }
                }
            } catch (apiEx: Exception) {
                Log.v("@@@","exception : ${apiEx.printStackTrace()}")
                withContext(Dispatchers.Main) {
                    pd.cancel()
                    Toast.makeText(requireContext(), "Something went wrong..", Toast.LENGTH_SHORT).show()
//                    mainActivity.navController.navigate(R.id.registerDistributorComplete)
                }
            }
        }
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


}