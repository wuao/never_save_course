package com.demo.course.never_save_course;

import android.app.Service;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Binder;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;
import android.widget.Toast;

import com.demo.course.aidl.PeocessService;

/**
 * Created by wuao on 2017/2/6.
 */

public class LocationServer extends Service {

     private  myBinder2 myBinder2;
     private  MyBinder binder;
     private  Myconn conn;


    @Override
    public void onCreate() {
        super.onCreate();
        binder=new MyBinder();
        if (conn==null){
            conn=new Myconn();
        }
    }

    @Override
    public IBinder onBind(Intent intent) {
        return binder;
    }





    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        LocationServer.this.bindService(new Intent(LocationServer.this,RemoteServer.class),conn, Context.BIND_IMPORTANT);

        return super.onStartCommand(intent, flags, startId);
    }

    class MyBinder extends  PeocessService.Stub{


        @Override
        public String getServiceName() throws RemoteException {
            return "LocationServer";
        }
    }

   class  Myconn implements ServiceConnection {
       @Override
       public void onServiceDisconnected(ComponentName componentName) {
           Log.i("INFO","连接远程服务进程成功");
       }

       @Override
       public void onServiceConnected(ComponentName componentName, IBinder iBinder) {

           Toast.makeText(LocationServer.this,"远程服务被杀死",Toast.LENGTH_SHORT).show();
           LocationServer.this.startService(new Intent(LocationServer.this,RemoteServer.class));
           LocationServer.this.bindService(new Intent(LocationServer.this,RemoteServer.class),conn, Context.BIND_IMPORTANT);

       }
   }
    class myBinder2 extends Binder {

        public LocationServer getService(){
            return LocationServer.this;
        }

    }


}
