package com.ling.aidl_client

import android.content.ComponentName
import android.content.Intent
import android.content.ServiceConnection
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.IBinder
import android.view.View
import android.widget.Toast
import com.example.myaidl.R
import com.example.myaidl.databinding.ActivityMainBinding
import com.xurui.ktx.property.viewBinding

class MainActivity : AppCompatActivity() {

    private val binding by viewBinding(ActivityMainBinding::bind)

    private var simpleInterface: ISimpleInterface? = null

    private val requestStr = "远程服务"

    private val serviceAction = "com.ling.SimpleService"

    private val servicePackage = "com.ling.aidl_service"

    private val messageStringBuilder = StringBuilder()

    private val receiveMsgListener by lazy {
        object : IReceiveMsgListener.Stub() {
            override fun onReceive(msg: String?) {
                messageChange("Receive:$msg")
            }
        }
    }

    private val serviceConnection by lazy {
        object : ServiceConnection {
            override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
                messageChange("Service:connected!")
                simpleInterface = ISimpleInterface.Stub.asInterface(service)
                binding.serviceAction = "unbind"
                simpleInterface?.registerReceiveListener(receiveMsgListener)
            }

            override fun onServiceDisconnected(name: ComponentName?) {
                messageChange("Service:Disconnected!")
                simpleInterface = null
                binding.serviceAction = "binding"
                simpleInterface?.unregisterReceiveListener(receiveMsgListener)
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        binding.serviceAction = "binding"
        binding.binding = View.OnClickListener {
            if (simpleInterface != null) {
                simpleInterface?.unregisterReceiveListener(receiveMsgListener)
                simpleInterface = null
                binding.serviceAction = "binding"
                messageChange("Service:Disconnected!")
                unbindService(serviceConnection)
            } else {
                bindService(Intent().apply {
                    action = serviceAction
                    setPackage(servicePackage)
                }, serviceConnection, BIND_AUTO_CREATE)
            }
        }

        binding.request = View.OnClickListener {
            if (simpleInterface == null) {
                Toast.makeText(this, "请先绑定服务！", Toast.LENGTH_SHORT).show()
                return@OnClickListener
            }
            messageChange("服务端返回：${simpleInterface?.getMessage(requestStr)}")
        }
    }

    fun messageChange(str: String) {
        messageStringBuilder.append("${str}\n")
        binding.message = messageStringBuilder.toString()
    }


    override fun onDestroy() {
        super.onDestroy()
        simpleInterface?.unregisterReceiveListener(receiveMsgListener)
        unbindService(serviceConnection)
    }
}