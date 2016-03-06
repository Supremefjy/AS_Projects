package com.jikexueyuan.notification;

import android.app.Notification;
import android.app.NotificationManager;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    public static final Integer NOTIFICATION_ID = 1200;
    private Button button;
    private int counter = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                counter++;
                NotificationCompat.Builder builder = new NotificationCompat.Builder(MainActivity.this);
                builder.setSmallIcon(R.drawable.normal);

                builder.setContentTitle("哇哦,女神来了"+counter+"个消息");

                builder.setContentText("你成功了");

                Notification notification = builder.build();

                NotificationManager manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
                manager.notify(NOTIFICATION_ID, notification);


            }


        });


    }
}
