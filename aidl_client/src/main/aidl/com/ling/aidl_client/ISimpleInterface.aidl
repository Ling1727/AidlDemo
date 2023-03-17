// ISimpleInterface.aidl
package com.ling.aidl_client;

import com.ling.aidl_client.IReceiveMsgListener;

// Declare any non-default types here with import statements

interface ISimpleInterface {

    String getMessage(String mes);

    void registerReceiveListener(IReceiveMsgListener receiveListener);

    void unregisterReceiveListener(IReceiveMsgListener receiveListener);

}