package com.mad.xmllayouts;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class FeedbackActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback);

        TextView feedbackTextView = findViewById(R.id.text_view_feedback);
        Button backButton = findViewById(R.id.button_back);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            String name = extras.getString("EXTRA_NAME");
            String email = extras.getString("EXTRA_EMAIL");
            feedbackTextView.setText("Thank you, " + name +
                    "! Your submission with email " + email + " has been received.");
        }

        backButton.setOnClickListener(v -> finish());
    }
}