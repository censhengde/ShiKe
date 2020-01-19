package com.ringle_ai.common.permission

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.core.app.ActivityCompat
import com.ringle_ai.common.permission.PermissionUtil.isNeedPermission
import com.ringle_ai.common.permission.PermissionUtil.shouldShowRequestPermissionRationale
import com.ringle_ai.common.permission.PermissionUtil.verifyPermission

private const val PARAM_REQST_CODE = "param_code"
private const val PARAM_PERMISSION = "param_permission"
private const val TAG = "PermissionActivity"

/*
*因为与权限相关的API都在Activity里面,所以需要声明
* 一个用户无感知的，不进入回退栈的Activity
*
* */
@Suppress("NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")
class PermissionActivity : Activity() {
    companion object {
        private var sPermission: IPermission? = null


        fun requestPermission(
            context: Context?,
            permissions: Array<String>,
            requestCode: Int,
            iPermission: IPermission
        ) {
            sPermission = iPermission
            val intent = Intent(context, PermissionActivity::class.java)
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK and Intent.FLAG_ACTIVITY_CLEAR_TOP)
            val bundle = Bundle()
            bundle.putStringArray(PARAM_PERMISSION, permissions)
            bundle.putInt(PARAM_REQST_CODE, requestCode)

            intent.putExtras(bundle)

            context?.startActivity(intent)
            if (context is Activity) {
                context.overridePendingTransition(0, 0)
            }

        }

    }


    private lateinit var mPermissions: Array<String>

    private var mRequestCode: Int = 0


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.mPermissions = intent.getStringArrayExtra(PARAM_PERMISSION)
        this.mRequestCode = intent.getIntExtra(PARAM_REQST_CODE, -1)
        if (mRequestCode < 0) {
            finish()
            return
        }
        //如果不再需要授权
        if (!isNeedPermission(mPermissions)) {
            sPermission?.granted()
            finish()
            return
        }

        //发起权限申请
        ActivityCompat.requestPermissions(this, this.mPermissions, mRequestCode)


    }
    /*
    * 权限申请结果回调
    * */

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        //权限请求成功
        if (verifyPermission(grantResults)) {
            sPermission?.granted()
            Log.e(TAG, "权限请求成功/已经授权")
            finish()
            return
        }

        //用户点击取消
        if (shouldShowRequestPermissionRationale(permissions)) {
            Log.e(TAG, "点击了拒绝")
            sPermission?.canceled()
            finish()
            return
        }
        //用户点击了不再提示
        Log.e(TAG, "点击了拒绝,不再提示")
        sPermission?.denied()
        finish()


    }

    override fun finish() {
        super.finish()
        //屏蔽进出动画
        overridePendingTransition(0, 0)
        sPermission = null//手动置空,避免内存泄漏
    }


}