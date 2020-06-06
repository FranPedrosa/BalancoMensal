package com.example.balancomensal;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    int i = 3;
    Button button2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        button2 = (Button) findViewById(R.id.button2);
    }

    public void apertarBotao(View v){
        if(i % 2 == 0){
            i = i/2 + 1;
        }else{
            i = 3*i + 1;
        }
        button2.setText(i+"");
    }

    public void outroBotao(View v){
        i = (int)(Math.random() * 100);
        button2.setText(i+"");
    }

}
