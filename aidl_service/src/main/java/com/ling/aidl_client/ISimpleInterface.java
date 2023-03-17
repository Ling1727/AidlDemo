/*
 * This file is auto-generated.  DO NOT MODIFY.
 */
package com.ling.aidl_client;
// Declare any non-default types here with import statements

public interface ISimpleInterface extends android.os.IInterface
{
  /** Default implementation for ISimpleInterface. */
  public static class Default implements ISimpleInterface
  {
    @Override public String getMessage(String mes) throws android.os.RemoteException
    {
      return null;
    }
    @Override public void registerReceiveListener(IReceiveMsgListener receiveListener) throws android.os.RemoteException
    {
    }
    @Override public void unregisterReceiveListener(IReceiveMsgListener receiveListener) throws android.os.RemoteException
    {
    }
    @Override
    public android.os.IBinder asBinder() {
      return null;
    }
  }
  /** Local-side IPC implementation stub class. */
  public static abstract class Stub extends android.os.Binder implements ISimpleInterface
  {
    private static final String DESCRIPTOR = "com.ling.aidl_client.ISimpleInterface";
    /** Construct the stub at attach it to the interface. */
    public Stub()
    {
      this.attachInterface(this, DESCRIPTOR);
    }
    /**
     * Cast an IBinder object into an com.ling.aidl_client.ISimpleInterface interface,
     * generating a proxy if needed.
     */
    public static ISimpleInterface asInterface(android.os.IBinder obj)
    {
      if ((obj==null)) {
        return null;
      }
      android.os.IInterface iin = obj.queryLocalInterface(DESCRIPTOR);
      if (((iin!=null)&&(iin instanceof ISimpleInterface))) {
        return ((ISimpleInterface)iin);
      }
      return new Proxy(obj);
    }
    @Override public android.os.IBinder asBinder()
    {
      return this;
    }
    @Override public boolean onTransact(int code, android.os.Parcel data, android.os.Parcel reply, int flags) throws android.os.RemoteException
    {
      String descriptor = DESCRIPTOR;
      switch (code)
      {
        case INTERFACE_TRANSACTION:
        {
          reply.writeString(descriptor);
          return true;
        }
        case TRANSACTION_getMessage:
        {
          data.enforceInterface(descriptor);
          String _arg0;
          _arg0 = data.readString();
          String _result = this.getMessage(_arg0);
          reply.writeNoException();
          reply.writeString(_result);
          return true;
        }
        case TRANSACTION_registerReceiveListener:
        {
          data.enforceInterface(descriptor);
          IReceiveMsgListener _arg0;
          _arg0 = IReceiveMsgListener.Stub.asInterface(data.readStrongBinder());
          this.registerReceiveListener(_arg0);
          reply.writeNoException();
          return true;
        }
        case TRANSACTION_unregisterReceiveListener:
        {
          data.enforceInterface(descriptor);
          IReceiveMsgListener _arg0;
          _arg0 = IReceiveMsgListener.Stub.asInterface(data.readStrongBinder());
          this.unregisterReceiveListener(_arg0);
          reply.writeNoException();
          return true;
        }
        default:
        {
          return super.onTransact(code, data, reply, flags);
        }
      }
    }
    private static class Proxy implements ISimpleInterface
    {
      private android.os.IBinder mRemote;
      Proxy(android.os.IBinder remote)
      {
        mRemote = remote;
      }
      @Override public android.os.IBinder asBinder()
      {
        return mRemote;
      }
      public String getInterfaceDescriptor()
      {
        return DESCRIPTOR;
      }
      @Override public String getMessage(String mes) throws android.os.RemoteException
      {
        android.os.Parcel _data = android.os.Parcel.obtain();
        android.os.Parcel _reply = android.os.Parcel.obtain();
        String _result;
        try {
          _data.writeInterfaceToken(DESCRIPTOR);
          _data.writeString(mes);
          boolean _status = mRemote.transact(Stub.TRANSACTION_getMessage, _data, _reply, 0);
          if (!_status && getDefaultImpl() != null) {
            return getDefaultImpl().getMessage(mes);
          }
          _reply.readException();
          _result = _reply.readString();
        }
        finally {
          _reply.recycle();
          _data.recycle();
        }
        return _result;
      }
      @Override public void registerReceiveListener(IReceiveMsgListener receiveListener) throws android.os.RemoteException
      {
        android.os.Parcel _data = android.os.Parcel.obtain();
        android.os.Parcel _reply = android.os.Parcel.obtain();
        try {
          _data.writeInterfaceToken(DESCRIPTOR);
          _data.writeStrongBinder((((receiveListener!=null))?(receiveListener.asBinder()):(null)));
          boolean _status = mRemote.transact(Stub.TRANSACTION_registerReceiveListener, _data, _reply, 0);
          if (!_status && getDefaultImpl() != null) {
            getDefaultImpl().registerReceiveListener(receiveListener);
            return;
          }
          _reply.readException();
        }
        finally {
          _reply.recycle();
          _data.recycle();
        }
      }
      @Override public void unregisterReceiveListener(IReceiveMsgListener receiveListener) throws android.os.RemoteException
      {
        android.os.Parcel _data = android.os.Parcel.obtain();
        android.os.Parcel _reply = android.os.Parcel.obtain();
        try {
          _data.writeInterfaceToken(DESCRIPTOR);
          _data.writeStrongBinder((((receiveListener!=null))?(receiveListener.asBinder()):(null)));
          boolean _status = mRemote.transact(Stub.TRANSACTION_unregisterReceiveListener, _data, _reply, 0);
          if (!_status && getDefaultImpl() != null) {
            getDefaultImpl().unregisterReceiveListener(receiveListener);
            return;
          }
          _reply.readException();
        }
        finally {
          _reply.recycle();
          _data.recycle();
        }
      }
      public static ISimpleInterface sDefaultImpl;
    }
    static final int TRANSACTION_getMessage = (android.os.IBinder.FIRST_CALL_TRANSACTION + 0);
    static final int TRANSACTION_registerReceiveListener = (android.os.IBinder.FIRST_CALL_TRANSACTION + 1);
    static final int TRANSACTION_unregisterReceiveListener = (android.os.IBinder.FIRST_CALL_TRANSACTION + 2);
    public static boolean setDefaultImpl(ISimpleInterface impl) {
      // Only one user of this interface can use this function
      // at a time. This is a heuristic to detect if two different
      // users in the same process use this function.
      if (Proxy.sDefaultImpl != null) {
        throw new IllegalStateException("setDefaultImpl() called twice");
      }
      if (impl != null) {
        Proxy.sDefaultImpl = impl;
        return true;
      }
      return false;
    }
    public static ISimpleInterface getDefaultImpl() {
      return Proxy.sDefaultImpl;
    }
  }
  public String getMessage(String mes) throws android.os.RemoteException;
  public void registerReceiveListener(IReceiveMsgListener receiveListener) throws android.os.RemoteException;
  public void unregisterReceiveListener(IReceiveMsgListener receiveListener) throws android.os.RemoteException;
}
