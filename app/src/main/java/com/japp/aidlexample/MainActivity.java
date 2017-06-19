package com.japp.aidlexample;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.japp.aidlclienteexample.R;

public class MainActivity extends AppCompatActivity {

    private static final  String TAG = "JGA::";
    IRemoteService remoteService;
    RemoteServiceConnection connection;

    EditText value1;
    EditText value2;
    TextView result;


    class RemoteServiceConnection implements ServiceConnection  {

        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            remoteService = IRemoteService.Stub.asInterface(service);
            Log.i(TAG, "onServiceConnected(): Connected");
            Toast.makeText(MainActivity.this, "AIDLExample Service connected", Toast.LENGTH_LONG).show();
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            remoteService = null;
            Log.i(TAG, "onServiceDisconnected(): Disconnected");
            Toast.makeText(MainActivity.this, "AIDLExample Service Connected", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        value1	= (EditText) findViewById(R.id.parm1);
        value2  = (EditText) findViewById(R.id.parm2);
        result = (TextView) findViewById(R.id.result);

        initService();
    }

    /** This is our function which binds our activity(MainActivity) to our service(AddService). */
    private void initService() {
        Log.i(TAG, "initService()" );
        connection = new RemoteServiceConnection();
        Intent i = new Intent();
        i.setClassName("com.japp.aidlexample", "com.japp.aidlexample.RemoteService");
        boolean ret = bindService(i, connection, Context.BIND_AUTO_CREATE);
        Log.i(TAG, "initService() bound value: " + ret);
    }

    /** This is our function to un-binds this activity from our service. */
    private void releaseService() {
        unbindService(connection);
        connection = null;
        Log.d(TAG, "releaseService(): unbound.");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        releaseService();
    }

    public void gotoRemoteAddService(View view) {
        int n1 =0, n2 =0, res = -1;

        n1 = Integer.parseInt(value1.getText().toString());
        n2 = Integer.parseInt(value2.getText().toString());
        try {
            res = remoteService.add(n1, n2);
        } catch (RemoteException e) {
            Log.i(TAG, "Data fetch failed with: " + e);
            e.printStackTrace();
        }
        result.setText(new Integer(res).toString());
    }

    public void gotoRemoteSubService(View view) {
        int n1 =0, n2 =0, res = -1;

        n1 = Integer.parseInt(value1.getText().toString());
        n2 = Integer.parseInt(value2.getText().toString());
        try {
            res = remoteService.sub(n1, n2);
        } catch (RemoteException e) {
            Log.i(TAG, "Data fetch failed with: " + e);
            e.printStackTrace();
        }
        result.setText(new Integer(res).toString());
    }

    public void gotoRemoteMultService(View view) {

        int n1 =0, n2 =0, res = -1;

        n1 = Integer.parseInt(value1.getText().toString());
        n2 = Integer.parseInt(value2.getText().toString());
        try {
            res = remoteService.mult(n1, n2);
        } catch (RemoteException e) {
            Log.i(TAG, "Data fetch failed with: " + e);
            e.printStackTrace();
        }
        result.setText(new Integer(res).toString());
    }



}
