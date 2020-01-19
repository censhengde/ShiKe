package com.ringle_ai.common.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.NavHost
import androidx.navigation.Navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.info
import kotlin.math.log

abstract class BaseFragment : NavHostFragment(), AnkoLogger {
    abstract fun setContentView(): Int


    private lateinit var mNavController: NavController

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val contentView = inflater.inflate(setContentView(), container, false)

        mNavController = findNavController(this)
        info { "" }

        return contentView
    }


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
    }

}