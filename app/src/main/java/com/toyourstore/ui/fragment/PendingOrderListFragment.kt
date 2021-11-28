package com.toyourstore.ui.fragment

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.RecyclerView
import com.toyourstore.R
import com.toyourstore.api.ApiCallingRequest
import com.toyourstore.model.Order
import com.toyourstore.model.PendingOrderResponse
import com.toyourstore.preference.SessionData
import com.toyourstore.ui.MainActivity
import com.toyourstore.ui.adapter.PendingOrderListAdapter
import com.toyourstore.utils.MyProgressDialog
import com.toyourstore.utils.PopupUtils
import de.footprinttech.wms.db.DataBaseHelper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.IOException
import java.util.HashMap

class PendingOrderListFragment : BaseFragment() {

    private var rootView: View? = null
    private var rvOrderList: RecyclerView? = null
    private var orderListAdapter = PendingOrderListAdapter()
    private lateinit var pd: MyProgressDialog
    var orders: ArrayList<Order> = ArrayList()


    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        rootView = inflater.inflate(R.layout.fragment_order_list, container, false)
        rvOrderList = rootView?.findViewById(R.id.rvOrderList)
        return rootView

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        pd = MyProgressDialog(requireContext(), R.drawable.icons8_loader)
        setTitle("Pending Orders")
        hideMenu()
        hideSearch()
        showBack()
        rvOrderList?.adapter = orderListAdapter
        apiCallingForOrders()
    }

    fun apiCallingForOrders() {
        pd.show()
        lifecycleScope.launch(Dispatchers.IO) {
            val apiCallingRequest = ApiCallingRequest()
            val params = HashMap<String, String>()
            params["user_id"] = SessionData.getInstance(requireContext()).getUserId()!!
            params["api_token"] = SessionData.getInstance(requireContext()).getToken()!!
            try {
                val responseOfOrderResponse =
                        apiCallingRequest.apiCallingForOrderList(
                                params
                        )
                withContext(Dispatchers.Main) {
                    pd.cancel()
                    orders.clear()
                    orders = responseOfOrderResponse.orders as ArrayList<Order>
                    orderListAdapter.setData(orders, {
                        val order = orders.get(it)
                        val bundle = Bundle()
                        bundle.putParcelable("order", order)
//                        mainActivity.navController.navigate(R.id.orderDetailsFragment)
                        val dire  = PendingOrderListFragmentDirections.actionPendingOrderToOrderDetails(order)
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