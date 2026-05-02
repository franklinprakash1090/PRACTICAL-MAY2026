package com.mad.filenotify;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.content.ContextCompat;
import java.io.*;
import java.nio.charset.StandardCharsets;

public class MainActivity extends AppCompatActivity {
    private EditText editText;
    private final String fileName = "sample.txt";

    private final ActivityResultLauncher<String> notificationPermissionLauncher =
            registerForActivityResult(
                    new ActivityResultContracts.RequestPermission(),
                    granted -> {
                        if (!granted) {
                            Toast.makeText(this,
                                    "Notification permission denied", Toast.LENGTH_SHORT).show();
                        }
                    }
            );

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editText   = findViewById(R.id.et_text);
        Button btnWrite   = findViewById(R.id.btn_write);
        Button btnRead    = findViewById(R.id.btn_read);
        Button btnNotify  = findViewById(R.id.btn_notify);
        Button btnDelayed = findViewById(R.id.btn_delayed);

        createNotificationChannel();

        btnWrite.setOnClickListener(v -> writeFile());
        btnRead.setOnClickListener(v -> readFile());

        btnNotify.setOnClickListener(v -> {
            requestNotificationPermission();
            showNotification("Persistent Notification", true);
        });

        btnDelayed.setOnClickListener(v -> {
            requestNotificationPermission();
            new Handler(Looper.getMainLooper()).postDelayed(() ->
                    showNotification("Delayed Notification", false), 5000);
        });
    }

    private void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(
                    "notify_channel", "Notifications",
                    NotificationManager.IMPORTANCE_DEFAULT
            );
            NotificationManager manager = getSystemService(NotificationManager.class);
            if (manager != null) {
                manager.createNotificationChannel(channel);
            }
        }
    }

    private void requestNotificationPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (ContextCompat.checkSelfPermission(this,
                    android.Manifest.permission.POST_NOTIFICATIONS)
                    != PackageManager.PERMISSION_GRANTED) {
                try {
                    notificationPermissionLauncher.launch(
                            android.Manifest.permission.POST_NOTIFICATIONS);
                } catch (Exception e) {
                    Log.e("MainActivity", "Error launching permission request", e);
                }
            }
        }
    }

    private void writeFile() {
        File dir = getExternalFilesDir(null);
        if (dir == null) {
            Toast.makeText(this, "Storage not available", Toast.LENGTH_SHORT).show();
            return;
        }
        try (FileOutputStream fos = new FileOutputStream(new File(dir, fileName))) {
            fos.write(editText.getText().toString().getBytes(StandardCharsets.UTF_8));
            Toast.makeText(this, "File Written", Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            Toast.makeText(this, "Error writing file", Toast.LENGTH_SHORT).show();
            Log.e("MainActivity", "Error writing file", e);
        }
    }

    private void readFile() {
        File dir = getExternalFilesDir(null);
        if (dir == null) {
            Toast.makeText(this, "Storage not available", Toast.LENGTH_SHORT).show();
            return;
        }
        File file = new File(dir, fileName);
        if (!file.exists()) {
            Toast.makeText(this, "File not found", Toast.LENGTH_SHORT).show();
            return;
        }
        try (BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(file), StandardCharsets.UTF_8))) {
            StringBuilder data = new StringBuilder();
            String currentLine;
            while (true) {
                currentLine = br.readLine();
                if (currentLine == null) break;
                data.append(currentLine).append("\n");
            }
            if (data.length() > 0) {
                data.setLength(data.length() - 1); // Remove last newline
            }
            editText.setText(data.toString());
            Toast.makeText(this, "File Read", Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            Toast.makeText(this, "Error reading file", Toast.LENGTH_SHORT).show();
            Log.e("MainActivity", "Error reading file", e);
        }
    }

    private void showNotification(String message, boolean persistent) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.POST_NOTIFICATIONS)
                    != PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "Permission required", Toast.LENGTH_SHORT).show();
                return;
            }
        }

        Intent intent = new Intent(this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent,
                PendingIntent.FLAG_IMMUTABLE);

        NotificationCompat.Builder builder =
                new NotificationCompat.Builder(this, "notify_channel")
                        .setContentTitle("My App")
                        .setContentText(message)
                        .setSmallIcon(android.R.drawable.ic_dialog_info)
                        .setOngoing(persistent)
                        .setAutoCancel(!persistent)
                        .setContentIntent(pendingIntent)
                        .setPriority(NotificationCompat.PRIORITY_DEFAULT);

        NotificationManager manager = getSystemService(NotificationManager.class);
        if (manager != null) {
            manager.notify(persistent ? 1 : 2, builder.build());
        }
    }
}