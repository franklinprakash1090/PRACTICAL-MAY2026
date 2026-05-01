package com.mad.widgets;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // 1. Initialize Widgets
        final EditText textField = findViewById(R.id.myTextField);
        final Spinner spinner = findViewById(R.id.mySpinner);
        Button button = findViewById(R.id.myButton);

        // 2. Populate Spinner
        String[] options = {"Option A", "Option B", "Option C"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_spinner_dropdown_item,
                options
        );
        spinner.setAdapter(adapter);

        // 3. Button Click
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String input = textField.getText().toString();
                String selected = spinner.getSelectedItem().toString();

                Toast.makeText(MainActivity.this,
                        "Text: " + input + "\nSelected: " + selected,
                        Toast.LENGTH_SHORT).show();
            }
        });
    }
}