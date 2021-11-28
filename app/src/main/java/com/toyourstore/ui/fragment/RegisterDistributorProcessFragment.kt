package com.toyourstore.ui.fragment

import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.core.widget.NestedScrollView
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.RecyclerView
import com.google.android.gms.common.api.ApiException
import com.toyourstore.R
import com.toyourstore.api.ApiCallingRequest
import com.toyourstore.model.BusinessLineModel
import com.toyourstore.model.CityResponse
import com.toyourstore.model.GetLocation
import com.toyourstore.model.StateResponse
import com.toyourstore.model.businessline.Businessline
import com.toyourstore.model.distributor.BusinesslineDis
import com.toyourstore.preference.SessionData
import com.toyourstore.utils.MyProgressDialog
import com.toyourstore.utils.PopupUtils
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.HashMap

class RegisterDistributorProcessFragment : BaseFragment() {

    private var rootView: View? = null
    private lateinit var llOkay: LinearLayout
    private lateinit var rlMulti: RelativeLayout
    private lateinit var txtBusinessLine: TextView
    private lateinit var txtCity: TextView
    private lateinit var txtCreditDay: TextView
    private lateinit var txtRoutine: TextView
    private lateinit var txtOperationArea: TextView
    private lateinit var scrollNest: NestedScrollView
    private var businessLineModels: ArrayList<Businessline>? = null
    private var stateNames: ArrayList<StateResponse.State>? = null
    private var cityNames: ArrayList<CityResponse.City>? = null
    private var selectedCity: CityResponse.City? = null
    private var selectedLocation: GetLocation.Location? = null
    private var selectedState: StateResponse.State? = null
    private var selectedBusinessline: Businessline? = null
    private var selectedCompanies: Businessline? = null
    private var creditDays: ArrayList<BusinessLineModel>? = null
    private var routines: ArrayList<BusinessLineModel>? = null
    private var operationAreas: ArrayList<BusinessLineModel>? = null

    private lateinit var rvMultiSelect: RecyclerView
    private lateinit var txtState: TextView
    private lateinit var edtOwnerName: EditText
    private lateinit var edtPartner: EditText
    private lateinit var edtContactNum: EditText
    private lateinit var edtAgency: EditText
    private lateinit var edtOfficeContact: EditText
    private lateinit var edtOpArea: EditText
    private lateinit var edtGstNumber: EditText
//    private lateinit var edtAreaCode: EditText
    private lateinit var edtAddress: EditText
    private lateinit var pd: MyProgressDialog

    private var ownerName = ""
    private var partner = ""
    private var contactNum = ""
    private var agency = ""
    private var officeContact = ""
    private var operationArea = ""
    private var gstName = ""
    private var state = ""
    private var city = ""
    private var area = ""
    private var creditDay = ""
    private var routes = ""
    private var areaCode = ""
    private var businessLine = ""
    private var address = ""


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        rootView = inflater.inflate(R.layout.fragment_register_distributor, container, false)
        return rootView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        businessLineModels = ArrayList()
        stateNames = ArrayList()
        cityNames = ArrayList()
        creditDays = ArrayList()
        routines = ArrayList()
        operationAreas = ArrayList()
        setTitle("Distributor Reg. Process")
        hideMenu()
        hideSearch()
        pd = MyProgressDialog(requireContext(), R.drawable.icons8_loader)
        llOkay = view.findViewById(R.id.llOkay)
        rlMulti = view.findViewById(R.id.rlMulti)
        txtBusinessLine = view.findViewById(R.id.txtBusinessLine)
        scrollNest = view.findViewById(R.id.scrollNest)
        rvMultiSelect = view.findViewById(R.id.rvMultiSelect)
        txtState = view.findViewById(R.id.txtState)
        txtCity = view.findViewById(R.id.txtCity)
        txtCreditDay = view.findViewById(R.id.txtCreditDay)
        txtRoutine = view.findViewById(R.id.txtRoutine)
        txtOperationArea = view.findViewById(R.id.txtOperationArea)
        edtOwnerName = view.findViewById(R.id.edtOwnerName)
        edtPartner = view.findViewById(R.id.edtPartner)
        edtContactNum = view.findViewById(R.id.edtContactNum)
        edtAgency = view.findViewById(R.id.edtAgency)
        edtOfficeContact = view.findViewById(R.id.edtOfficeContact)
        edtOpArea = view.findViewById(R.id.edtOpArea)
        edtGstNumber = view.findViewById(R.id.edtGstNumber)
//        edtAreaCode = view.findViewById(R.id.edtAreaCode)
        edtAddress = view.findViewById(R.id.edtAddress)
        creditDays?.add(BusinessLineModel("10 days", false))
        creditDays?.add(BusinessLineModel("20 days", false))
        creditDays?.add(BusinessLineModel("30 days", false))
        routines?.add(BusinessLineModel("Daily", false))
        routines?.add(BusinessLineModel("Weekly", false))
        routines?.add(BusinessLineModel("Monthly", false))
        operationAreas?.add(BusinessLineModel("Sola", false))
        operationAreas?.add(BusinessLineModel("Radio mirchi", false))
        operationAreas?.add(BusinessLineModel("GandhiNagar", false))
        operationAreas?.add(BusinessLineModel("prahlad nagar ahmedabad", false))

        llOkay.setOnClickListener {
            if (isValidate()) {
//                apiCallingForDistributorProcess()
                val bundle = Bundle()
                bundle.putString("ownerName", ownerName)
                bundle.putString("partner", partner)
                bundle.putString("contactNum", contactNum)
                bundle.putString("agency", agency)

                bundle.putString("officeContact", officeContact)
                bundle.putString("operationArea", operationArea)
                bundle.putString("gstName", gstName)
                bundle.putSerializable("state", selectedState)

                bundle.putSerializable("city", selectedCity)
                bundle.putSerializable("area", selectedLocation)
                bundle.putString("creditDay", creditDay)
                bundle.putString("routes", routes)

//                bundle.putString("areaCode", areaCode)
                bundle.putSerializable("businessLine", selectedBusinessline)
                bundle.putString("address", address)
                mainActivity.navController.navigate(R.id.reviewFragment, bundle)
            }
        }

        txtBusinessLine.setOnClickListener {
            if (businessLineModels.isNullOrEmpty()) {
                apiCallingForBusinessLine()
            } else {
                PopupUtils.multiChoiceDailogForBusinessLine(
                    mainActivity,
                    "Select business line ",
                    businessLineModels,
                    { model ->
                        selectedBusinessline =model
                        txtBusinessLine.text = selectedBusinessline!!.name

                    })
            }
        }

        txtState.setOnClickListener {
            if (stateNames.isNullOrEmpty()) {
                apiCallingForState()
            } else {
                PopupUtils.singleChoiceDailogForState(
                    mainActivity,
                    "Select state ",
                    stateNames,
                    selectedState,
                    {
                        selectedState = it
                        txtState.text = selectedState?.name
                    })
            }
        }

        txtCity.setOnClickListener {
            if (selectedState == null) {
                Toast.makeText(requireContext(), "Please select state first", Toast.LENGTH_SHORT)
                    .show()
            } else {
                apiCallingForCity()
            }
        }

        txtCreditDay.setOnClickListener {
            PopupUtils.singleChoiceDailog(mainActivity, "Select Credit day ", creditDays, {
                txtCreditDay.text = it
            })
        }

        txtRoutine.setOnClickListener {
            PopupUtils.singleChoiceDailog(mainActivity, "Select Route ", routines, {
                txtRoutine.text = it
            })
        }

        txtOperationArea.setOnClickListener {
            apiCallingForLocation()


//            PopupUtils.singleChoiceDailog(mainActivity, "Select operation area ", operationAreas, {
//                txtOperationArea.text = it
//            })


        }
    }

    private fun apiCallingForCity() {
        pd.show()
        lifecycleScope.launch(Dispatchers.IO) {
            val apiCallingRequest = ApiCallingRequest()
            val params = HashMap<String, String>()
            params["state_id"] = "" + selectedState?.id
            params["api_token"] = SessionData.getInstance(requireContext()).getToken()!!
            try {
                val responseOfCity = apiCallingRequest.apiCallingForCityResponse(params)
                cityNames = responseOfCity.cities as ArrayList<CityResponse.City>
                withContext(Dispatchers.Main) {
                    pd.cancel()
                    PopupUtils.singleChoiceDailogForCity(
                        mainActivity,
                        "Select City ",
                        cityNames,
                        selectedCity,
                        {
                            selectedCity = it
                            txtCity.text = selectedCity?.name
                        })
                }
            } catch (apiEx: ApiException) {
                apiEx.printStackTrace()
                withContext(Dispatchers.Main) {
                    pd.cancel()
                }
            }
        }
    }


    private fun apiCallingForLocation() {
        pd.show()
        lifecycleScope.launch(Dispatchers.IO) {
            val apiCallingRequest = ApiCallingRequest()
            val params = HashMap<String, String>()
            params["state_id"] = "" + selectedState?.id
            params["city_id"] = "" + selectedCity?.id
            params["api_token"] = SessionData.getInstance(requireContext()).getToken()!!
            try {
                val responseOfCity = apiCallingRequest.getLocationList(params)
                withContext(Dispatchers.Main) {
                    pd.cancel()
                    PopupUtils.multiChoiceDailogForLocations(
                        mainActivity,
                        "Select Area ",
                        responseOfCity.locations as ArrayList<GetLocation.Location>,
                        { it ->
                            selectedLocation = it
                            txtOperationArea.text = selectedLocation?.name.toString()
                        })
                }
            } catch (apiEx: ApiException) {
                apiEx.printStackTrace()
                withContext(Dispatchers.Main) {
                    pd.cancel()
                }
            }
        }
    }

    private fun apiCallingForState() {
        pd.show()
        lifecycleScope.launch(Dispatchers.IO) {
            val apiCallingRequest = ApiCallingRequest()
            val params = HashMap<String, String>()
            params["api_token"] = SessionData.getInstance(requireContext()).getToken()!!
            try {
                val responseOfState = apiCallingRequest.apiCallingForSateResponse(params)
                stateNames = responseOfState.states as ArrayList<StateResponse.State>?
                withContext(Dispatchers.Main) {
                    pd.cancel()
                    PopupUtils.singleChoiceDailogForState(
                        mainActivity,
                        "Select state ",
                        stateNames,
                        selectedState,
                        {
                            selectedState = it
                            txtState.text = selectedState?.name
                        })
                }
            } catch (apiEx: ApiException) {
                apiEx.printStackTrace()
                withContext(Dispatchers.Main) {
                    pd.cancel()
                }
            }
        }
    }

    private fun apiCallingForBusinessLine() {
        pd.show()
        lifecycleScope.launch(Dispatchers.IO) {
            val apiCallingRequest = ApiCallingRequest()
            val params = HashMap<String, String>()
            params["api_token"] = SessionData.getInstance(requireContext()).getToken()!!
            try {
                val responseOfBusinessResponse =
                    apiCallingRequest.apiCallingBusinessLine(
                        params
                    )
                withContext(Dispatchers.Main) {
                    businessLineModels =
                        responseOfBusinessResponse.businessline as ArrayList<Businessline>
                    pd.cancel()
                    PopupUtils.multiChoiceDailogForBusinessLine(
                        mainActivity,
                        "Select business line ",
                        businessLineModels,
                        { model ->
                            selectedBusinessline = model
                            txtBusinessLine.text = selectedBusinessline!!.name
                        })
                    Log.e("Response", "" + responseOfBusinessResponse)
                }
            } catch (apiEx: ApiException) {
                withContext(Dispatchers.Main) {
                    pd.cancel()
                    mainActivity.navController.popBackStack()
                }
            }
        }
    }


    private fun isValidate(): Boolean {
        ownerName = edtOwnerName.text.toString().trim()
        partner = edtPartner.text.toString().trim()
        contactNum = edtContactNum.text.toString().trim()
        agency = edtAgency.text.toString().trim()
        officeContact = edtOfficeContact.text.toString().trim()
        operationArea = edtOpArea.text.toString().trim()
        gstName = edtGstNumber.text.toString().trim()
        state = txtState.text.toString().trim()
        city = txtCity.text.toString().trim()
        area = txtOperationArea.text.toString().trim()
        address = edtAddress.text.toString().trim()
        creditDay = txtCreditDay.text.toString().replace("days","").trim()
        routes = txtRoutine.text.toString().trim()
//        areaCode = edtAreaCode.text.toString().trim()
        businessLine = txtBusinessLine.text.toString().trim()

        if (TextUtils.isEmpty(ownerName)) {
            PopupUtils.alertMessage(requireContext(), "Enter Ownwer name")
            return false
        }
//        else if (TextUtils.isEmpty(partner)) {
//            PopupUtils.alertMessage(requireContext(), "Enter patner name")
//            return false
//        }
        else if (TextUtils.isEmpty(contactNum)) {
            PopupUtils.alertMessage(requireContext(), "Enter Contact number")
            return false
        } else if (TextUtils.isEmpty(agency)) {
            PopupUtils.alertMessage(requireContext(), "Enter agency number")
            return false
        } else if (TextUtils.isEmpty(officeContact)) {
            PopupUtils.alertMessage(requireContext(), "Enter office contact")
            return false
        }
//        else if (TextUtils.isEmpty(operationArea)) {
//            PopupUtils.alertMessage(requireContext(), "Enter operation area")
//            return false
//        }
        else if (TextUtils.isEmpty(gstName)) {
            PopupUtils.alertMessage(requireContext(), "Enter gst number")
            return false
        }
        else if (gstName.length!=15) {
            PopupUtils.alertMessage(requireContext(), "please check gst number, it must be 15 digits")
            return false
        } else if (TextUtils.isEmpty(state)) {
            PopupUtils.alertMessage(requireContext(), "Select state")
            return false
        } else if (TextUtils.isEmpty(city)) {
            PopupUtils.alertMessage(requireContext(), "Select city")
            return false
        } else if (TextUtils.isEmpty(area)) {
            PopupUtils.alertMessage(requireContext(), "Enter area")
            return false
        } else if (TextUtils.isEmpty(address)) {
            PopupUtils.alertMessage(requireContext(), "Enter address")
            return false
        } else if (TextUtils.isEmpty(creditDay)) {
            PopupUtils.alertMessage(requireContext(), "select credit day")
            return false
        } else if (TextUtils.isEmpty(routes)) {
            PopupUtils.alertMessage(requireContext(), "select routes")
            return false
        }
//        else if (TextUtils.isEmpty(areaCode)) {
//            PopupUtils.alertMessage(requireContext(), "select area code")
//            return false
//        }
        else {
            return true
        }


    }


}



