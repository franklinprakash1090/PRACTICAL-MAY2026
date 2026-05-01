package com.mad.xmllayouts;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        EditText nameField = findViewById(R.id.edit_text_name);
        EditText emailField = findViewById(R.id.edit_text_email);
        Button submitButton = findViewById(R.id.button_submit);

        submitButton.setOnClickListener(v -> {
            String name = nameField.getText().toString().trim();
            String email = emailField.getText().toString().trim();

            if (!name.isEmpty() && !email.isEmpty()) {
                Intent intent = new Intent(MainActivity.this, FeedbackActivity.class);
                intent.putExtra("EXTRA_NAME", name);
                intent.putExtra("EXTRA_EMAIL", email);
                startActivity(intent);
            } else {
                Toast.makeText(MainActivity.this, "Please fill in all fields.",
                        Toast.LENGTH_SHORT).show();
            }
        });
    }
}