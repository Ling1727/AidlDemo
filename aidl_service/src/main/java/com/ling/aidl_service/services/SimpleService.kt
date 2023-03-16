package com.ling.aidl_service.services

import android.app.Service
import android.content.Intent
import android.os.IBinder


class SimpleService : Service() {

    private val binder = object : ISimpleInterface.Stub() {

        override fun getMessage(mes: String?): String {
            return "服务端"
        }

    }


    override fun onBind(intent: Intent?): IBinder? {
        return binder
    }

}