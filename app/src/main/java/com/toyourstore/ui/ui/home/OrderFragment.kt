package com.toyourstore.ui.ui.home

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.toyourstore.R
import com.toyourstore.api.ApiCallingRequest
import com.toyourstore.preference.SessionData
import com.toyourstore.ui.MainActivity
import com.toyourstore.ui.fragment.BaseFragment
import com.toyourstore.utils.DateUtils
import com.toyourstore.utils.MyProgressDialog
import com.toyourstore.utils.PopupUtils
import de.footprinttech.wms.db.DataBaseHelper
import kotlinx.android.synthetic.main.layout_side_menu.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.IOException
import java.util.HashMap

class OrderFragment : BaseFragment() {

    private lateinit var homeViewModel: HomeViewModel
    private lateinit var pd: MyProgressDialog
    private lateinit var txtPendingOrder: TextView
    private lateinit var txtPendingPayment: TextView
    private lateinit var rlPendingOrder: RelativeLayout
    private lateinit var rlPendingPayment: RelativeLayout
    private lateinit var txtName: TextView
    private lateinit var txtType: TextView
    private lateinit var tv_current_date: TextView



    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        homeViewModel =
                ViewModelProvider(this).get(HomeViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_order, container, false)
        txtPendingOrder = root.findViewById(R.id.txtPendingOrder)
        txtPendingPayment = root.findViewById(R.id.txtPendingPayment)
        rlPendingOrder = root.findViewById(R.id.rlPendingOrder)
        rlPendingPayment = root.findViewById(R.id.rlPendingPayment)
        txtName = root.findViewById(R.id.txtName)
        txtType = root.findViewById(R.id.txtType)
        tv_current_date = root.findViewById(R.id.tv_current_date)

        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        pd = MyProgressDialog(requireContext(), R.drawable.icons8_loader)
        hideBack()
        showMenu()
        hideSearch()
        setTitle(" ")
        mainActivity.selectedSideMenu(mainActivity.txtHome)
        rlPendingOrder.setOnClickListener {
            mainActivity.navController.navigate(R.id.action_nav_home_to_orderListFragment)
        }
        rlPendingPayment.setOnClickListener {
            mainActivity.navController.navigate(R.id.action_nav_home_to_pendingPaymentListFragment)
        }


        lifecycleScope.launch(Dispatchers.IO) {
            val user = DataBaseHelper.getDatabaseDao(requireContext()).getUser(SessionData.getInstance(requireContext()).getUserId()!!.toLong())
            withContext(Dispatchers.Main) {
                txtName.text = user.name
                txtType.text = user.userType
            }
        }


//        if (SessionData.getInstance(requireContext()).isRegisterDis()) {
//            apiCallingForDashBoard()
//        }

        tv_current_date.text = DateUtils.getCurrentDate()

    }

    override fun onResume() {
        super.onResume()

        if (SessionData.getInstance(requireContext()).isRegisterDis()) {
            apiCallingForDashBoard()
        }
    }

    private fun apiCallingForDashBoard() {
        pd.show()
        lifecycleScope.launch(Dispatchers.IO) {
            val apiCallingRequest = ApiCallingRequest()
            val params = HashMap<String, String>()
            params["user_id"] = SessionData.getInstance(requireContext()).getUserId()!!
            params["api_token"] = SessionData.getInstance(requireContext()).getToken()!!
            try {
                val dashBoardResponse =
                        apiCallingRequest.apiCallingForDashBoard(
                                params
                        )
                withContext(Dispatchers.Main) {
                    pd.cancel()
                    txtPendingOrder.text = "" + dashBoardResponse?.data?.pendingOrders!!
                    txtPendingPayment.text = "" + dashBoardResponse?.data?.pendingPayments!!
                }
            } catch (apiEx: IOException) {
                withContext(Dispatchers.Main) {
                    pd.cancel()
                }
            }
        }
    }


}