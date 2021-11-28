package com.toyourstore.ui.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.RecyclerView
import com.toyourstore.R
import com.toyourstore.api.ApiCallingRequest
import com.toyourstore.model.Order
import com.toyourstore.preference.SessionData
import com.toyourstore.ui.adapter.PendingOrderListAdapter
import com.toyourstore.ui.adapter.PendingPaymentListAdapter
import com.toyourstore.utils.MyProgressDialog
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.IOException
import java.util.HashMap

class PendingPaymentListFragment : BaseFragment() {

    private var rootView: View? = null
    private var rvOrderList: RecyclerView? = null
    private var pendingPaymentListAdapter =PendingPaymentListAdapter()
    private lateinit var pd: MyProgressDialog
    var orders: ArrayList<Order> = ArrayList()
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        rootView = inflater.inflate(R.layout.fragment_payment_list, container, false)
        pd = MyProgressDialog(requireContext(), R.drawable.icons8_loader)
        rvOrderList = rootView?.findViewById(R.id.rvOrderList)
        apiCallingForPayments()
        return rootView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setTitle("Pending Payments")
        hideMenu()
        hideSearch()
        showBack()
        var listOforder = arrayListOf<String>()
        listOforder.add("one")
        listOforder.add("Two")
        listOforder.add("Three")


    }

    fun apiCallingForPayments() {
        pd.show()
        lifecycleScope.launch(Dispatchers.IO) {
            val apiCallingRequest = ApiCallingRequest()
            val params = HashMap<String, String>()
            params["user_id"] = SessionData.getInstance(requireContext()).getUserId()!!
            params["api_token"] = SessionData.getInstance(requireContext()).getToken()!!
            try {
                val responseOfOrderResponse =
                    apiCallingRequest.apiCallingForPaymentList(
                        params
                    )
                Log.v("@@@"," Date  : "+responseOfOrderResponse.toString())
                withContext(Dispatchers.Main) {
                    pd.cancel()
                    orders.clear()
                    orders = responseOfOrderResponse.orders as ArrayList<Order>
                    rvOrderList?.adapter = pendingPaymentListAdapter
                    pendingPaymentListAdapter.setData(orders, {
                        val order = orders.get(it)
                        val bundle = Bundle()
                        bundle.putParcelable("order", order)
                        val dire  = PendingPaymentListFragmentDirections.actionPaymentListToOrderDetails(order)
                        mainActivity.navController.navigate(dire)
                    })
                }
            } catch (apiEx: IOException) {
                withContext(Dispatchers.Main) {
                    pd.cancel()
                }
            }
        }

    }

}