package com.ling.aidl_service.services

interface IMessageGetListener {
    fun getMessage(): String

    fun clientRequestMessage(msg: String?)

    fun serviceSendLog(msg: String)
}