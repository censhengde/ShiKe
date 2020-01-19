package com.ringle_ai.common.permission

import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import androidx.collection.SimpleArrayMap
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.ringle_ai.common.permission.manu.*
import com.ringle_ai.common.permission.manu.base.IManu
import kotlin.Exception
import kotlin.reflect.KClass

internal object PermissionUtil {
    const val DEFAULT_REQUEST_CODE = 200

    private val MIN_SDK_PERMISSIONS by lazy(LazyThreadSafetyMode.NONE) {
        SimpleArrayMap<String, Int>(
            8
        )
    }

    private val PERMISSION_MENU by lazy(LazyThreadSafetyMode.NONE) { SimpleArrayMap<String, KClass<out IManu>>() }

    init {
        MIN_SDK_PERMISSIONS.put("com.android.voicemail.permission.ADD_VOICEMAIL", 14);
        MIN_SDK_PERMISSIONS.put("android.permission.BODY_SENSORS", 20);
        MIN_SDK_PERMISSIONS.put("android.permission.READ_CALL_LOG", 16);
        MIN_SDK_PERMISSIONS.put("android.permission.READ_EXTERNAL_STORAGE", 16);
        MIN_SDK_PERMISSIONS.put("android.permission.USE_SIP", 9);
        MIN_SDK_PERMISSIONS.put("android.permission.WRITE_CALL_LOG", 16);
        MIN_SDK_PERMISSIONS.put("android.permission.SYSTEM_ALERT_WINDOW", 23);
        MIN_SDK_PERMISSIONS.put("android.permission.WRITE_SETTINGS", 23);

    }

    private const val MANU_HUAWEI = "huawei"
    private const val MANU_XIAOMI = "xiaomi"
    private const val MANU_OPPO = "oppo"
    private const val MANU_VIVO = "vivo"
    private const val MANU_SAMSUNG = "samsung"
    private const val MANU_ZTE = "zte"
    private const val MANU_DEFAULT = "default"

    init {
        PERMISSION_MENU.put(MANU_DEFAULT, Default::class)
        PERMISSION_MENU.put(MANU_XIAOMI, XiaoMi::class)
        PERMISSION_MENU.put(MANU_OPPO, OPPO::class)
        PERMISSION_MENU.put(MANU_HUAWEI, HuaWei::class)
        PERMISSION_MENU.put(MANU_VIVO, VIVO::class)
        PERMISSION_MENU.put(MANU_ZTE, ZTE::class)
        PERMISSION_MENU.put(MANU_SAMSUNG, SumSung::class)
    }

    /*
    * 检查权限组是否存在未授权
    * reture:true 存在,需要授权,false 不存在,不需要授权
    * */
    fun Context.isNeedPermission(permissions: Array<String>): Boolean {
        //遍历
        permissions.forEach {
            //如果当前权限存在并且未授权
            if (permissionExists(it) && !hasSelfPermission(it)) return true
        }
        return false
    }

    /*
    *
    *  检查某个权限是否存在
    *
    * */
    private fun permissionExists(permission: String): Boolean {
        val minVersion = MIN_SDK_PERMISSIONS[permission]
        return minVersion == null || Build.VERSION.SDK_INT >= minVersion
    }

    /*
    *
    *  检查某个权限是否已经授权
    *
    * */
    private fun Context.hasSelfPermission(permission: String): Boolean = try {
        ContextCompat.checkSelfPermission(this, permission) == PackageManager.PERMISSION_GRANTED
    } catch (e: Exception) {
        false
    }

    /*
    * 检查是否全部已授权
    * */
    fun Context.verifyPermission(grantedResults: IntArray): Boolean {

        if (grantedResults.isEmpty()) return true
        grantedResults.forEach {
            //Granted:译:已经确认的
            if (it != PackageManager.PERMISSION_GRANTED) return false
        }
        return true
    }


    /*
    * 是否弹窗
    * */
    fun Activity.shouldShowRequestPermissionRationale(permissions: Array<out String>): Boolean {
        permissions.forEach {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, it)) return true

        }
        return false
    }
}