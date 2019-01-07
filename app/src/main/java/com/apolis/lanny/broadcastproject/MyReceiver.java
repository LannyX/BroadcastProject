package com.apolis.lanny.broadcastproject;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.telephony.SmsMessage;
import android.util.Log;
import android.widget.Toast;

public class MyReceiver extends BroadcastReceiver {
    // Get the object of SmsManager
    final SmsManager sms = SmsManager.getDefault();
    MediaPlayer mp;

    @Override
    public void onReceive(Context context, Intent intent) {
        // Retrieves a map of extended data from the intent.
        final Bundle bundle = intent.getExtras();

        try {
            if (bundle != null) {
                final Object[] pdusObj = (Object[]) bundle.get("pdus");
                SmsMessage[] messages = new SmsMessage[pdusObj.length];

                for (int i = 0; i < pdusObj.length; i++) {
                    SmsMessage currentMessage = SmsMessage.createFromPdu((byte[]) pdusObj[i]);
                    String phoneNumber = currentMessage.getDisplayOriginatingAddress();
                    String senderNum = phoneNumber;
                    String message = currentMessage.getDisplayMessageBody();

                    String[] splitText = message.split("\\s+");
                    Log.i("SmsReceiver", splitText[0]);
                    if(splitText[0].equals("play")  && splitText[1].equals("music") && senderNum.equals("6505551212") ){
                        mp = MediaPlayer.create(context, R.raw.jinglebell);
                        mp.start();
                    }


                    Log.i("SmsReceiver", "senderNum: "+ senderNum + "; message: " + message);
                    // Show alert
                    int duration = Toast.LENGTH_LONG;
                    Toast toast = Toast.makeText(context, "senderNum: "+ senderNum + ", message: " + message, duration);

                    toast.show();

                    context.startService(new Intent(context, MyTell.class));

                } // end for loop
            } // bundle is null

        } catch (Exception e) {
            Log.e("SmsReceiver", "Exception smsReceiver" +e);

        }

        //1. play music
        //2. read sms, do sth, get sender phone number and sms information
        //split the string in string[], check if "play music" in pos 0 and 1
        //3. Open other application

//        Intent i = new Intent(context, OtherActivity.class);
//        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//        context.startActivity(i);
        //intent.setComponent(new ComponentName("music","com.apolis.lanny.xmasapp"));

//        Intent i = new Intent(Intent.ACTION_MAIN);
//        i.addCategory(Intent.CATEGORY_APP_MESSAGING);
//        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//        context.startActivity(i);
    }
}
