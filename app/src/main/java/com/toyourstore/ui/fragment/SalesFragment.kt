package com.toyourstore.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RelativeLayout
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.RecyclerView
import com.toyourstore.R
import com.toyourstore.api.ApiCallingRequest
import com.toyourstore.model.SalesTeamResponse
import com.toyourstore.preference.SessionData
import com.toyourstore.ui.adapter.SalesAdapter
import com.toyourstore.utils.MyProgressDialog
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.IOException
import java.util.HashMap

class SalesFragment : BaseFragment() {

    private var rootView: View? = null
    private var rvSales: RecyclerView? = null
    private var salesAdapter = SalesAdapter()
    private lateinit var rlPlus: RelativeLayout
    private lateinit var pd: MyProgressDialog


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        rootView = inflater.inflate(R.layout.fragment_sales, container, false)
        rvSales = rootView?.findViewById(R.id.rvSales)
        rlPlus = rootView?.findViewById(R.id.rlPlus)!!
        pd = MyProgressDialog(requireContext(), R.drawable.icons8_loader)
        return rootView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        rlPlus.setOnClickListener {
            mainActivity.navController.navigate(R.id.addSaleFragment)
        }
        rvSales?.adapter = salesAdapter
        setTitle("Sales")
        hideMenu()
        hideSearch()
        showBack()
        apiCallingForSales()
    }

    fun apiCallingForSales() {
        pd.show()
        lifecycleScope.launch(Dispatchers.IO) {
            val apiCallingRequest = ApiCallingRequest()
            val params = HashMap<String, String>()
            params["user_id"] = SessionData.getInstance(requireContext()).getUserId()!!
            params["api_token"] = SessionData.getInstance(requireContext()).getToken()!!
            try {
                val responseOfSales =
                    apiCallingRequest.apiCallingForSales(
                        params
                    )
                withContext(Dispatchers.Main) {
                    pd.cancel()
                    salesAdapter.setData(responseOfSales.salesTeam as ArrayList<SalesTeamResponse.SalesTeam>, {
                        mainActivity.navController.navigate(R.id.orderDetailsFragment)
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