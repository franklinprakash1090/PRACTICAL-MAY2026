package com.mad.firebase;

import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;
import com.mad.firebase.R;

public class MainActivity extends AppCompatActivity {
    EditText phone, message;
    Button smsBtn, emailBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        phone    = findViewById(R.id.et_phone);
        message  = findViewById(R.id.et_message);
        smsBtn   = findViewById(R.id.btn_sms);
        emailBtn = findViewById(R.id.btn_email);

        // Send SMS
        smsBtn.setOnClickListener(v -> {
            SmsManager smsManager;
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.S) {
                smsManager = this.getSystemService(SmsManager.class);
            } else {
                smsManager = SmsManager.getDefault();
            }
            if (smsManager != null) {
                smsManager.sendTextMessage(
                        phone.getText().toString(),
                        null,
                        message.getText().toString(),
                        null,
                        null
                );
                Toast.makeText(this, "SMS Sent", Toast.LENGTH_SHORT).show();
            }
        });

        // Send Email
        emailBtn.setOnClickListener(v -> {
            Intent intent = new Intent(Intent.ACTION_SEND);
            intent.setType("message/rfc822");
            intent.putExtra(Intent.EXTRA_EMAIL, new String[]{"example@gmail.com"});
            intent.putExtra(Intent.EXTRA_SUBJECT, "Test Email");
            intent.putExtra(Intent.EXTRA_TEXT, message.getText().toString());
            startActivity(Intent.createChooser(intent, "Send Email"));
        });
    }
}