package com.toyourstore.ui.fragment

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.toyourstore.ui.MainActivity
import com.toyourstore.utils.PopupUtils

open class BaseFragment : Fragment() {

    protected lateinit var mainActivity: MainActivity

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mainActivity = activity as MainActivity
    }

    fun setTitle(title: String) {
        mainActivity.txtTitle.text = title
    }

    fun hideMenu() {
        mainActivity.imgMenu.visibility = View.GONE

    }

    fun hideGiftIcon() {
        mainActivity.imgGift.visibility = View.GONE

    }

    fun showGiftIcon() {
        mainActivity.imgGift.visibility = View.VISIBLE

    }

    fun hideBack() {
        mainActivity.imgBack.visibility = View.GONE

    }

    fun hideSearch() {
        mainActivity.imgSearch.visibility = View.GONE

    }

    fun showMenu() {
        mainActivity.imgMenu.visibility = View.VISIBLE

    }

    fun showBack() {
        mainActivity.imgBack.visibility = View.VISIBLE

    }

    fun showSearch() {
        mainActivity.imgSearch.visibility = View.VISIBLE
    }

    fun showSearchDialogue(callBack: (name: String) -> Unit){
        mainActivity.imgSearch.setOnClickListener {
            PopupUtils.searchDialogue(
                mainActivity,
                "Search Inventory",
                { name ->
                    callBack.invoke(name)
                })
        }
    }

}