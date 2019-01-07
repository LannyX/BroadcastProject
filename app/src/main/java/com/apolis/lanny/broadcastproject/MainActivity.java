package com.apolis.lanny.broadcastproject;

import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    TextView tv;
    Handler handler;
    int count = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tv = findViewById(R.id.textView);
        handler = new Handler();

    }

    public void broadcastCustomIntent(View view) {
//        Intent intent = new Intent("MyCustomIntent");
//
//        EditText et = (EditText)findViewById(R.id.extraIntent);
//        // add data to the Intent
//        //intent.putExtra("message", et.getText().toString());
//        intent.putExtra("message", et.getText().toString());
//
//        intent.setAction("com.apolis.lanny.A_CUSTOM_INTENT");
//
//        sendBroadcast(intent);

        //the data is recycled by os, always with the system: sendStickyBroadcast(Intent);
        Thread thread = new Thread(){
            @Override
            public void run() {
                super.run();

                for(int i = 0; i < 10; i++){
                    try{
                        count = i;
                        sleep(1000);

                        handler.post(new Runnable() {
                            @Override
                            public void run() {
                                tv.setText(" " + count);
                            }
                        });


                    }catch (InterruptedException e){
                        e.printStackTrace();
                    }
                }

                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        tv.setText("This msg is set after 5 sec");
                        Toast.makeText(MainActivity.this, "From worker thread", Toast.LENGTH_SHORT).show();
                    }
                }, 5000);

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(MainActivity.this, "From UI thread", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        };

        thread.start();
    }
}
