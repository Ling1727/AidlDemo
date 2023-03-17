package com.ling.aidl_service

import android.content.ComponentName
import android.content.Intent
import android.content.ServiceConnection
import android.os.Bundle
import android.os.IBinder
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.ling.aidl_client.IReceiveMsgListener
import com.ling.aidl_service.databinding.ActivityMainBinding
import com.ling.aidl_service.services.IMessageGetListener
import com.ling.aidl_service.services.SimpleService
import com.xurui.ktx.property.viewBinding

class MainActivity : AppCompatActivity() {

    private val binding by viewBinding(ActivityMainBinding::bind)

    private var simpleBinder: SimpleService.SimpleBinder? = null

    private val out = java.lang.StringBuilder()

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
                simpleBinder = service as SimpleService.SimpleBinder
                simpleBinder?.setMessageGetListener(object : IMessageGetListener {
                    override fun getMessage(): String {
                        return binding.etIput.text.toString()
                    }

                    override fun clientRequestMessage(msg: String?) {
                        messageChange("Client:${msg}")
                    }

                    override fun serviceSendLog(msg: String) {
                        messageChange("Service_Send:${msg}")
                    }

                })
                binding.serviceAction = "unbind service"
                messageChange("Service:Connected!")

                simpleBinder?.registerReceiveListener(receiveMsgListener)
            }

            override fun onServiceDisconnected(name: ComponentName?) {
                simpleBinder?.registerReceiveListener(receiveMsgListener)
                simpleBinder = null
                messageChange("Service:Disconnected!")
                binding.serviceAction = "binding service"
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        binding.message = "服务端"
        binding.serviceAction = "binding service"
        binding.bindService = View.OnClickListener {
            if (simpleBinder != null) {
                simpleBinder = null
                unbindService(serviceConnection)
                binding.serviceAction = "binding service"
                return@OnClickListener
            }
            bindService(Intent(this, SimpleService::class.java), serviceConnection, BIND_AUTO_CREATE)
        }
        binding.request = View.OnClickListener {
            if (simpleBinder == null) {
                Toast.makeText(this, "请先绑定服务！", Toast.LENGTH_SHORT).show()
                return@OnClickListener
            }
            simpleBinder?.getMessage("本地服务")
        }
        binding.generateAction = View.OnClickListener {
            if (simpleBinder == null) {
                Toast.makeText(this, "请先绑定服务！", Toast.LENGTH_SHORT).show()
                return@OnClickListener
            }
            simpleBinder?.generateOrStopMessage()
        }
    }

    fun messageChange(msg: String) {
        out.append("${msg}\n")
        binding.out = out.toString()
    }

    override fun onDestroy() {
        super.onDestroy()
        simpleBinder?.registerReceiveListener(receiveMsgListener)
        unbindService(serviceConnection)
    }

}