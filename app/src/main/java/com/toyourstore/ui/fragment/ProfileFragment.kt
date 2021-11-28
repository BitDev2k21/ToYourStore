package com.toyourstore.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.lifecycle.lifecycleScope
import com.toyourstore.R
import com.toyourstore.model.User
import com.toyourstore.preference.SessionData
import de.footprinttech.wms.db.DataBaseHelper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ProfileFragment : BaseFragment() {

    lateinit var mUser: User
    lateinit var edtUser:EditText
    lateinit var et_contact_number:EditText
    lateinit var et_email:EditText
    lateinit var et_address:EditText
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        val rootView = inflater.inflate(R.layout.fragment_profile, container, false)
        edtUser = rootView.findViewById(R.id.edtUser)
        et_contact_number = rootView.findViewById(R.id.et_contact_number)
        et_email = rootView.findViewById(R.id.et_email)
        et_address = rootView.findViewById(R.id.et_address)
        return rootView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setTitle("Profile")
        hideBack()
        showMenu()
        hideSearch()
        fetchUser()
    }

    fun fetchUser(){
        lifecycleScope.launch(Dispatchers.IO) {
            val user = DataBaseHelper.getDatabaseDao(requireContext()).getUser(SessionData.getInstance(requireContext()).getUserId()!!.toLong())
            withContext(Dispatchers.Main) {
                if(user!=null){
                    mUser = user
                    populateUser()
                }
            }
        }
    }

    fun populateUser(){
        if(this::mUser.isInitialized){
            edtUser.setText(mUser.name)
            et_contact_number.setText(mUser.contact)
            et_email.setText(mUser.email)
            et_address.setText("")
        }
    }

}