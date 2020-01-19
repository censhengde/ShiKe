package com.ringle_ai.common.permission.manu.base

import android.content.Context
import android.content.Intent

interface IManu {

    fun getMenuIntent(context: Context):Intent
}