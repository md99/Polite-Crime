package polite.crime.service;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.Toast;

import org.androidannotations.annotations.EService;

/**
 * Created by admin on 7/6/2017.
 */
public class AlertReceiver extends BroadcastReceiver {
    private static int countPowerOff = 0;

    public AlertReceiver() {

    }

    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction().equals(Intent.ACTION_SCREEN_OFF)) {
            Log.e("In on receive", "In Method:  ACTION_SCREEN_OFF");
            countPowerOff++;
        } else if (intent.getAction().equals(Intent.ACTION_SCREEN_ON)) {
            Log.e("In on receive", "In Method:  ACTION_SCREEN_ON");
        } else if (intent.getAction().equals(Intent.ACTION_USER_PRESENT)) {
            Log.e("In on receive", "In Method:  ACTION_USER_PRESENT");
            if (countPowerOff > 2) {
                countPowerOff = 0;
                Toast.makeText(context, "MAIN ACTIVITY IS BEING CALLED ", Toast.LENGTH_LONG).show();
                Log.d("asdqwe", "MAIN ACTIVITY IS BEING CALLED");
            }
        }
    }
}
