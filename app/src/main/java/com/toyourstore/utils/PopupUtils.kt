package com.toyourstore.utils

import android.app.Activity
import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.content.DialogInterface
import android.text.TextUtils
import android.view.Window
import android.view.WindowManager
import android.widget.EditText
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.toyourstore.R
import com.toyourstore.model.BusinessLineModel
import com.toyourstore.model.CityResponse
import com.toyourstore.model.GetLocation
import com.toyourstore.model.StateResponse
import com.toyourstore.model.businessline.Businessline
import com.toyourstore.model.distributor.BusinesslineDis
import com.toyourstore.ui.adapter.*


object PopupUtils {


    fun alertMessage(context: Context, msg: String) {
        val dialog = MaterialAlertDialogBuilder(context)
                .setIcon(R.mipmap.ic_launcher)
                .setTitle(R.string.app_name)
                .setMessage(msg)
                .setPositiveButton(
                        "Ok", null
                )
                .show()
    }

    fun alertMessageWithCallBack(context: Context, msg: String, callBack: (value: String) -> Unit) {
        val dialog = MaterialAlertDialogBuilder(context)
                .setIcon(R.mipmap.ic_launcher)
                .setTitle(R.string.app_name)
                .setMessage(msg)
                .setPositiveButton(
                        "Ok", object : DialogInterface.OnClickListener {
                      override fun onClick(p0: DialogInterface?, p1: Int) {
                          callBack.invoke("ok")
                    }
                }
                ).setCancelable(false)
                .show()
    }


    fun confirmationDailg(context: Context, callBack: (value: String) -> Unit) {
        val alertDialog: AlertDialog = AlertDialog.Builder(context).create()
        alertDialog.setTitle(context.getString(R.string.app_name))
        alertDialog.setMessage("Are you sure want to logout?")
        alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "Yes",
                { dialog, which ->
                    dialog.dismiss()
                    callBack.invoke("Yes")
                })
        alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, "No",
                { dialog, which -> dialog.dismiss() })
        alertDialog.show()
    }

    fun multiChoiceDailog(
            activity: Activity,
            title: String,
            businessLineModels: ArrayList<BusinessLineModel>? = null,
            callBack: (value: String) -> Unit) {
        lateinit var multiSelectionAdapter: MultiSelectionAdapter
        val dialog = Dialog(activity, android.R.style.Theme_Material_Dialog_Alert)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.window!!.setBackgroundDrawableResource(android.R.color.transparent)
        dialog.setCancelable(false)
        dialog.setContentView(R.layout.layout_recyler_view)
        val rvMultiSelect = dialog.findViewById<RecyclerView>(R.id.rvMultiSelect)
        val txtOK = dialog.findViewById<TextView>(R.id.txtOK)
        val txtTitle = dialog.findViewById<TextView>(R.id.txtTitle)
        txtTitle.text = title
        multiSelectionAdapter = MultiSelectionAdapter({
            val businessLineModel = businessLineModels?.get(it)
            if (businessLineModel?.isSelected!!) {
                businessLineModel.isSelected = false
            } else {
                businessLineModel.isSelected = true
            }
            businessLineModels?.set(it, businessLineModel)
            multiSelectionAdapter.notifyDataSetChanged()
        })
        rvMultiSelect.adapter = multiSelectionAdapter
        multiSelectionAdapter.setData(businessLineModels!!)
        txtOK.setOnClickListener {
            val newArrayList = ArrayList<String>()
            for (businesmodel in businessLineModels) {
                if (businesmodel.isSelected) {
                    newArrayList.add(businesmodel.name)
                }
            }
            val stringValue = TextUtils.join(",", newArrayList)
            callBack.invoke(stringValue)
            dialog.cancel()


        }
        dialog.window!!.setLayout(
                WindowManager.LayoutParams.WRAP_CONTENT,
                WindowManager.LayoutParams.WRAP_CONTENT
        )
        dialog.show()
    }

    fun searchDialogue(
        activity: Activity,
        title: String,
        callBack: (name: String) -> Unit) {
        val dialog = Dialog(activity, android.R.style.Theme_Material_Dialog_Alert)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.window!!.setBackgroundDrawableResource(android.R.color.transparent)
        dialog.setCancelable(false)
        dialog.setContentView(R.layout.layout_search_dialogue)
        val et_Search = dialog.findViewById<EditText>(R.id.et_Search)
        val txtOK = dialog.findViewById<TextView>(R.id.txtOK)
        val txtTitle = dialog.findViewById<TextView>(R.id.txtTitle)
        txtTitle.text = title
        txtOK.setOnClickListener {
            callBack.invoke(et_Search.text.toString())
            dialog.cancel()
        }
        dialog.window!!.setLayout(
            WindowManager.LayoutParams.WRAP_CONTENT,
            WindowManager.LayoutParams.WRAP_CONTENT
        )
        dialog.show()
    }

    fun multiChoiceDailogForBusinessLine(
            activity: Activity,
            title: String,
            businessLineModels: ArrayList<Businessline>? = null,
            callBack: (businessline: Businessline) -> Unit) {
        lateinit var multiSelectionAdapter: BusinessLineSelectionAdapter
        val dialog = Dialog(activity, android.R.style.Theme_Material_Dialog_Alert)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.window!!.setBackgroundDrawableResource(android.R.color.transparent)
        dialog.setCancelable(false)
        dialog.setContentView(R.layout.layout_recyler_view)
        val rvMultiSelect = dialog.findViewById<RecyclerView>(R.id.rvMultiSelect)
        val txtOK = dialog.findViewById<TextView>(R.id.txtOK)
        val txtTitle = dialog.findViewById<TextView>(R.id.txtTitle)
        txtTitle.text = title
        multiSelectionAdapter = BusinessLineSelectionAdapter({
            val businessLineModel = businessLineModels?.get(it)
            if (businessLineModel?.isSelected!!) {
                businessLineModel.isSelected = false
            } else {
                businessLineModel.isSelected = true
            }
            businessLineModels?.set(it, businessLineModel)
            multiSelectionAdapter.notifyDataSetChanged()
        })
        rvMultiSelect.adapter = multiSelectionAdapter
        multiSelectionAdapter.setData(businessLineModels!!)
        txtOK.setOnClickListener {
            val newArrayListOfName = ArrayList<String>()
            val newArrayListOfId = ArrayList<String>()
            var model :Businessline? = null
            for (businesmodel in businessLineModels) {
                if (businesmodel.isSelected) {
                    model = businesmodel
                    newArrayListOfName.add(businesmodel.name)
                    newArrayListOfId.add("" + businesmodel.id)
                }
            }
            val stringValueName = TextUtils.join(",", newArrayListOfName)
            val stringValueId = TextUtils.join(",", newArrayListOfName)
            if (model != null) {
                callBack.invoke(model)
            }
            dialog.cancel()
        }
        dialog.window!!.setLayout(
                WindowManager.LayoutParams.WRAP_CONTENT,
                WindowManager.LayoutParams.WRAP_CONTENT
        )
        dialog.show()
    }


    fun multiChoiceDailogForLocations(
            activity: Activity,
            title: String,
            businessLineModels: ArrayList<GetLocation.Location>? = null,
            callBack: (location: GetLocation.Location) -> Unit) {
        lateinit var multiSelectionAdapter: LocationSelectionAdapter
        val dialog = Dialog(activity, android.R.style.Theme_Material_Dialog_Alert)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.window!!.setBackgroundDrawableResource(android.R.color.transparent)
        dialog.setCancelable(false)
        dialog.setContentView(R.layout.layout_recyler_view)
        val rvMultiSelect = dialog.findViewById<RecyclerView>(R.id.rvMultiSelect)
        val txtOK = dialog.findViewById<TextView>(R.id.txtOK)
        val txtTitle = dialog.findViewById<TextView>(R.id.txtTitle)
        txtTitle.text = title
        multiSelectionAdapter = LocationSelectionAdapter({
            val businessLineModel = businessLineModels?.get(it)
            if (businessLineModel?.isSelected!!) {
                businessLineModel.isSelected = false
            } else {
                businessLineModel.isSelected = true
            }
            businessLineModels?.set(it, businessLineModel)
            multiSelectionAdapter.notifyDataSetChanged()
        })
        rvMultiSelect.adapter = multiSelectionAdapter
        multiSelectionAdapter.setData(businessLineModels!!)
        txtOK.setOnClickListener {
            val newArrayListOfName = ArrayList<String>()
            val newArrayListOfId = ArrayList<String>()
            var model :GetLocation.Location? = null
            for (businesmodel in businessLineModels) {
                if (businesmodel.isSelected) {
                    model = businesmodel
                    newArrayListOfName.add(businesmodel.name!!)
                    newArrayListOfId.add("" + businesmodel.id)
                }
            }
            val stringValueName = TextUtils.join(",", newArrayListOfName)
            val stringValueId = TextUtils.join(",", newArrayListOfName)
            if (model != null) {
                callBack.invoke(model)
            }
            dialog.cancel()
        }
        dialog.window!!.setLayout(
                WindowManager.LayoutParams.WRAP_CONTENT,
                WindowManager.LayoutParams.WRAP_CONTENT
        )
        dialog.show()
    }

    fun singleChoiceDailog(
            activity: Activity,
            title: String,
            businessLineModels: ArrayList<BusinessLineModel>? = null,
            callBack: (value: String) -> Unit) {
        lateinit var multiSelectionAdapter: MultiSelectionAdapter
        val dialog = Dialog(activity, android.R.style.Theme_Material_Dialog_Alert)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.window!!.setBackgroundDrawableResource(android.R.color.transparent)
        dialog.setCancelable(false)
        dialog.setContentView(R.layout.layout_recyler_view)
        val rvMultiSelect = dialog.findViewById<RecyclerView>(R.id.rvMultiSelect)
        val txtOK = dialog.findViewById<TextView>(R.id.txtOK)
        val txtTitle = dialog.findViewById<TextView>(R.id.txtTitle)
        txtTitle.text = title
        multiSelectionAdapter = MultiSelectionAdapter({
            for (i in 0..businessLineModels?.size!! - 1) {
                val businessLineModel = businessLineModels.get(i)
                if (i == it) {
                    businessLineModel.isSelected = true
                } else {
                    businessLineModel.isSelected = false
                }
                businessLineModels.set(i, businessLineModel)
            }
            multiSelectionAdapter.notifyDataSetChanged()
        })
        rvMultiSelect.adapter = multiSelectionAdapter
        multiSelectionAdapter.setData(businessLineModels!!)
        txtOK.setOnClickListener {
            val newArrayList = ArrayList<String>()
            for (businesmodel in businessLineModels) {
                if (businesmodel.isSelected) {
                    newArrayList.add(businesmodel.name)
                }
            }
            val stringValue = TextUtils.join(",", newArrayList)
            callBack.invoke(stringValue)
            dialog.cancel()


        }
        dialog.window!!.setLayout(
                WindowManager.LayoutParams.WRAP_CONTENT,
                WindowManager.LayoutParams.WRAP_CONTENT
        )
        dialog.show()
    }


    fun singleChoiceDailogForState(
            activity: Activity,
            title: String,
            businessLineModels: ArrayList<StateResponse.State>? = null,
            selectedState: StateResponse.State? = null,
            callBack: (value: StateResponse.State) -> Unit) {
        lateinit var stateSelectionAdapter: StateSelectionAdapter
        var stateResponse: StateResponse.State? = selectedState
        val dialog = Dialog(activity, android.R.style.Theme_Material_Dialog_Alert)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.window!!.setBackgroundDrawableResource(android.R.color.transparent)
        dialog.setCancelable(false)
        dialog.setContentView(R.layout.layout_recyler_view)
        val rvMultiSelect = dialog.findViewById<RecyclerView>(R.id.rvMultiSelect)
        val txtOK = dialog.findViewById<TextView>(R.id.txtOK)
        val txtTitle = dialog.findViewById<TextView>(R.id.txtTitle)
        txtTitle.text = title
        stateSelectionAdapter = StateSelectionAdapter({
            stateResponse = businessLineModels?.get(it)
            for (i in 0..businessLineModels?.size!! - 1) {
                val businessLineModel = businessLineModels.get(i)
                if (i == it) {
                    businessLineModel?.isSelected = true
                } else {
                    businessLineModel?.isSelected = false
                }
                businessLineModels.set(i, businessLineModel)
            }
            stateSelectionAdapter.notifyDataSetChanged()
        })
        rvMultiSelect.adapter = stateSelectionAdapter
        stateSelectionAdapter.setData(businessLineModels!!)
        txtOK.setOnClickListener {
            if (stateResponse != null) {
                callBack.invoke(stateResponse!!)
                dialog.cancel()
            }
        }
        dialog.window!!.setLayout(
                WindowManager.LayoutParams.WRAP_CONTENT,
                WindowManager.LayoutParams.WRAP_CONTENT
        )
        dialog.show()
    }

    fun singleChoiceDailogForCity(
            activity: Activity,
            title: String,
            businessLineModels: ArrayList<CityResponse.City>? = null,
            city: CityResponse.City? = null,
            callBack: (value: CityResponse.City) -> Unit) {
        lateinit var citySelectionAdapter: CitySelectionAdapter
        var selectedCity: CityResponse.City? = city
        val dialog = Dialog(activity, android.R.style.Theme_Material_Dialog_Alert)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.window!!.setBackgroundDrawableResource(android.R.color.transparent)
        dialog.setCancelable(false)
        dialog.setContentView(R.layout.layout_recyler_view)
        val rvMultiSelect = dialog.findViewById<RecyclerView>(R.id.rvMultiSelect)
        val txtOK = dialog.findViewById<TextView>(R.id.txtOK)
        val txtTitle = dialog.findViewById<TextView>(R.id.txtTitle)
        txtTitle.text = title
        citySelectionAdapter = CitySelectionAdapter({
            selectedCity = businessLineModels?.get(it)
            for (i in 0..businessLineModels?.size!! - 1) {
                val businessLineModel = businessLineModels.get(i)
                if (i == it) {
                    businessLineModel.isSelected = true
                } else {
                    businessLineModel.isSelected = false
                }
                businessLineModels.set(i, businessLineModel)
            }
            citySelectionAdapter.notifyDataSetChanged()
        })
        rvMultiSelect.adapter = citySelectionAdapter
        citySelectionAdapter.setData(businessLineModels!!)
        txtOK.setOnClickListener {
            if (selectedCity != null) {
                callBack.invoke(selectedCity!!)
                dialog.cancel()
            }
        }
        dialog.window!!.setLayout(
                WindowManager.LayoutParams.WRAP_CONTENT,
                WindowManager.LayoutParams.WRAP_CONTENT
        )
        dialog.show()
    }





}