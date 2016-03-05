package com.jikexueyuan.gesturelock;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.util.List;

public class SettingActivity extends AppCompatActivity {
    List<Integer> passList;
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        final GestureLock lock = (GestureLock) findViewById(R.id.lock);
        Button btn_reset = (Button) findViewById(R.id.btn_reset);
        Button btn_save = (Button) findViewById(R.id.btn_save);

        lock.setOnDrawFinishedListener(new GestureLock.OnDrawFinishedListener() {
                                           @Override
                                           public boolean OnDrawFinished(List<Integer> passList) {


                                               if (passList.size() < 3) {
                                                   Toast.makeText(SettingActivity.this, "密码不能少于三个点", Toast.LENGTH_SHORT).show();
                                                   return false;

                                               } else

                                               {
                                                   SettingActivity.this.passList = passList;
                                                   return true;


                                               }
                                           }

                                       }
        );


        btn_reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                lock.resetPoints();
            }

        });
        btn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (passList != null) {
                    StringBuilder sb = new StringBuilder();
                    for (Integer i : passList) {
                        sb.append(i);
                    }

                    //保存
                    SharedPreferences sp = SettingActivity.this.getSharedPreferences("password", SettingActivity.this.MODE_PRIVATE);
                    SharedPreferences.Editor editor = sp.edit();

                    editor.putString("password", sb.toString());
                    editor.commit();
                    Toast.makeText(SettingActivity.this, "保存完成", Toast.LENGTH_SHORT).show();


                }
            }

        });

    }
}