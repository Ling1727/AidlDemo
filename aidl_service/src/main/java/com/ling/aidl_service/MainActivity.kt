package com.ling.aidl_service

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.ling.aidl_service.databinding.ActivityMainBinding
import com.xurui.ktx.property.viewBinding

class MainActivity : AppCompatActivity() {

    private val binding by viewBinding(ActivityMainBinding::bind)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        binding.send= View.OnClickListener {

        }
    }

}