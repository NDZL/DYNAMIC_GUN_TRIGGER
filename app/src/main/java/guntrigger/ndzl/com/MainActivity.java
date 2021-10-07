package guntrigger.ndzl.com;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.Timer;


    //(C)2021 by cxnt48
    public class MainActivity extends Activity{

    Timer tim;


    TextView tvOut;
    TextView tvBattery;

    // DZLReceiver mIntent_Receiver;
    IntentFilter mIntentFilter;
    Intent iBattStat;

    Button btLaunch;
    Button virtKeyb;
    static public boolean bvirtualoff = true;

    Button btCamera;
    public static Intent service_is;
    public static Context main_context;
    Button btSpeak;
    Button btWait;
    EditText etPTT;
    EditText etABC;
    Button btCPUtest;
    Button btReboot;

    String androidId;
    LocationManager locationManager;


        Intent i_startscan;
        Intent i_stopscan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        main_context = this;


        String DeviceSERIALNumber = Build.SERIAL;
        TelephonyManager telephonyManager = (TelephonyManager) getSystemService(this.TELEPHONY_SERVICE);
        String DeviceIMEI = null;
        try {
            DeviceIMEI = telephonyManager.getDeviceId();
        } catch (SecurityException se) {
            DeviceIMEI="NO_IMEI";
        }
        androidId = Settings.Secure.getString(this.getContentResolver(), Settings.Secure.ANDROID_ID);

        tvOut = (TextView) findViewById(R.id.tvOutput);

        etPTT = (EditText) findViewById(R.id.etPTT);
        etABC = (EditText) findViewById(R.id.etABC);

        //tvOut.setText("S/N: " + DeviceSERIALNumber + "\nIMEI: " + DeviceIMEI + "\nSecure.ANDROID_ID: "+androidId  );  //rem on lollipop!
        //tvOut.setText("Attivare il BT di questo terminale");


        btCPUtest= (Button) findViewById(R.id.cputest);
        btCPUtest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        i_startscan = new Intent();
        i_startscan.setAction("com.motorolasolutions.emdk.datawedge.api.ACTION_SOFTSCANTRIGGER");
        i_startscan.putExtra("com.motorolasolutions.emdk.datawedge.api.EXTRA_PARAMETER", "START_SCANNING");

        i_stopscan = new Intent();
        i_stopscan.setAction("com.motorolasolutions.emdk.datawedge.api.ACTION_SOFTSCANTRIGGER");
        i_stopscan.putExtra("com.motorolasolutions.emdk.datawedge.api.EXTRA_PARAMETER", "STOP_SCANNING");

    }




    /*  ET1:
    *   Battery Pack Rechargeable Lithium Ion 3.7V, 4620 mAh or 5640 mAh Smart battery.
        Backup Battery NiMH battery (rechargeable) 15 mAh 3.6 V (not user accessible).
    * */


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


        final int REQUEST_IMAGE_CAPTURE = 1;
    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode==10036) {
            if (etPTT.isFocused()) {
                tvOut.setText("CONTEXT #1 SELECTED");
                sendBroadcast(i_startscan);
            } else if (etABC.isFocused()) {
                tvOut.setText("CONTEXT #2 SELECTED");
                dispatchTakePictureIntent();
            } else
                tvOut.setText("NO CONTEXT SELECTED");

        }
        return super.onKeyDown(keyCode, event);
    }
        //MC22+EC30 CENTRAL  BUTTON KEYCODE=10036 KeyEvent { action=ACTION_DOWN, keyCode=KEYCODE_SCAN, scanCode=310, metaState=0, flags=0x8, repeatCount=0, eventTime=179957, downTime=179957, deviceId=5, source=0x701, displayId=-1 }
        //MC22 PROGRAMMABLE KEY "P1" KEYCODE=104 KeyEvent { action=ACTION_DOWN, keyCode=KEYCODE_BUTTON_L2, scanCode=312, metaState=0, flags=0x8, repeatCount=0, eventTime=251990, downTime=251990, deviceId=5, source=0x701, displayId=-1 }
        //"P2" WAS PROGRAMMED VIA KEY PROGRAMMER AS APP_SWITCH => NOT CAPTURED BY onkEYdOWN
        //BUT AFTER SETTING "P2" TO NONE, KEYCODE=10038 KeyEvent { action=ACTION_DOWN, keyCode=KEYCODE_NONE, scanCode=511, metaState=0, flags=0x8, repeatCount=0, eventTime=409922, downTime=409922, deviceId=7, source=0x701, displayId=-1 }
        //EC30: KEYCODE=24 <= VOLUME UP


        @Override
    protected void onDestroy() {
        super.onDestroy();

    }


    @Override
    public void onStart() {
        super.onStart();// ATTENTION: This was auto-generated to implement the App Indexing API.

    }

    @Override
    public void onStop() {
        super.onStop();// ATTENTION: This was auto-generated to implement the App Indexing API.



    }




}





