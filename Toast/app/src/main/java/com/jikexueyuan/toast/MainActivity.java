package com.jikexueyuan.toast;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private Button toast;
    private Button longtosat;
    private Button showtoast;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toast = (Button) findViewById(R.id.toast);

        toast.setOnClickListener(new View.OnClickListener() {
                                     @Override
                                     public void onClick(View v) {
                                         Toast.makeText(MainActivity.this, "显示一个简短的toast", Toast.LENGTH_SHORT).show();
                                     }
                                 }
        );

        findViewById(R.id.longtoast).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast alongtoast = Toast.makeText(MainActivity.this,"显示个较长的toast",Toast.LENGTH_LONG);
                alongtoast.setGravity(Gravity.CENTER,0,0);
                //偏移量是像素
                alongtoast.show();
            }
        });


        findViewById(R.id.showtoast).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast imagetoast = Toast.makeText(MainActivity.this,"显示一个带有图片的toast",Toast.LENGTH_LONG);
                ImageView imageview = new ImageView(MainActivity.this);
                imageview.setImageResource(R.drawable.p1);
                imagetoast.setView(imageview);
                imagetoast.show();

            }
        });


    }
}
