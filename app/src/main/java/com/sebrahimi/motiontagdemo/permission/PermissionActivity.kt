package com.sebrahimi.motiontagdemo.permission

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.sebrahimi.motiontagdemo.map.MapActivity
import com.sebrahimi.motiontagdemo.databinding.ActivityPermissionBinding

class PermissionActivity : AppCompatActivity() {

    private lateinit var binding: ActivityPermissionBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            checkPermission()
            binding = ActivityPermissionBinding.inflate(layoutInflater)
            setContentView(binding.root)
            binding.btnApprovePermission.setOnClickListener {
                requestPermissions()
            }
            binding.btnNextActivity.setOnClickListener {
                goToNextActivity()
            }
        }else{
            goToNextActivity()
        }
    }

    @RequiresApi(Build.VERSION_CODES.N)
    private fun requestPermissions() {
        ActivityCompat.requestPermissions(this,
            arrayOf(
                Manifest.permission.ACCESS_FINE_LOCATION,
            ),
            0)
    }

    private fun checkPermission(){
        val res = ActivityCompat.checkSelfPermission(this,Manifest.permission.ACCESS_FINE_LOCATION)
        if(res == PackageManager.PERMISSION_GRANTED)
            goToNextActivity()
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }

    private fun goToNextActivity(){
        startActivity(Intent(this, MapActivity::class.java))
        finish()
    }
}