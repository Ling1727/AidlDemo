package com.ling.aidl_service.services

import android.app.Service
import android.content.Intent
import android.os.IBinder
import android.os.RemoteCallbackList
import android.os.RemoteException
import com.ling.aidl_client.IReceiveMsgListener
import com.ling.aidl_client.ISimpleInterface
import java.util.concurrent.Executors
import java.util.concurrent.ScheduledExecutorService
import java.util.concurrent.TimeUnit


class SimpleService : Service() {

    //AIDL不支持正常的接口回调，使用RemoteCallbackList实现接口回调
    private val mReceiveListener = RemoteCallbackList<IReceiveMsgListener>();

    private val simpleBinder by lazy {
        SimpleBinder()
    }

    override fun onBind(intent: Intent?): IBinder {
        return simpleBinder
    }


    inner class SimpleBinder : ISimpleInterface.Stub() {

        private var scheduledExecutorService: ScheduledExecutorService? = null

        private var messageGetListener: IMessageGetListener? = null

        private var num = 0

        private var count = 0;

        override fun getMessage(mes: String?): String {
            if (messageGetListener == null) {
                return "服务端"
            }
            messageGetListener!!.clientRequestMessage(msg = mes)
            val sendMsg = messageGetListener!!.getMessage()
            messageGetListener!!.serviceSendLog(msg = sendMsg)
            return sendMsg
        }

        override fun registerReceiveListener(receiveListener: IReceiveMsgListener?) {
            mReceiveListener.register(receiveListener)
        }

        override fun unregisterReceiveListener(receiveListener: IReceiveMsgListener?) {
            mReceiveListener.unregister(receiveListener)
        }


        fun setMessageGetListener(messageGetListener: IMessageGetListener) {
            this.messageGetListener = messageGetListener
        }

        fun generateOrStopMessage() {
            if (scheduledExecutorService == null) {
                scheduledExecutorService = Executors.newScheduledThreadPool(1)
                scheduledExecutorService!!.scheduleAtFixedRate({
                    num++
                    //通知Callback循环开始,返回N为实现mReceiveListener回调的个数
                    count = mReceiveListener.beginBroadcast()
                    for (index in 0 until count) {
                        val listener = mReceiveListener.getBroadcastItem(index)
                        if (listener != null) {
                            try {
                                listener.onReceive(num.toString());
                            } catch (e: RemoteException) {
                                e.printStackTrace();
                            }
                        }
                    }
                    //通知通知Callback循环结束
                    mReceiveListener.finishBroadcast();
                }, 1000L, 1000L, TimeUnit.MILLISECONDS)
            } else {
                scheduledExecutorService?.shutdown()
                scheduledExecutorService=null
            }
        }

    }

}
