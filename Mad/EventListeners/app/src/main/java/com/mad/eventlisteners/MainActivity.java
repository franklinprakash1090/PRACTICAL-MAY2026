package com.mad.eventlisteners;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    Button b1, b2;
    ImageButton imgBtn;
    TextView txtView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        b1 = findViewById(R.id.button);
        b2 = findViewById(R.id.button2);
        imgBtn = findViewById(R.id.imageButton);
        txtView = findViewById(R.id.textView);

        b1.setOnClickListener(v -> txtView.setTextSize(25));
        b2.setOnClickListener(v -> txtView.setTextSize(55));
    }
}