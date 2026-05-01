package com.mad.crud;

import androidx.appcompat.app.AppCompatActivity;
import android.app.AlertDialog;
import android.database.Cursor;
import android.os.Bundle;
import android.widget.*;

public class MainActivity extends AppCompatActivity {

    DatabaseHelper myDb;
    EditText editName, editDesign, editId;
    Button btnAdd, btnView, btnUpdate, btnDelete, btnClear;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        myDb = new DatabaseHelper(this);

        editName = findViewById(R.id.et_name);
        editDesign = findViewById(R.id.et_desig);
        editId = findViewById(R.id.et_id);

        btnAdd = findViewById(R.id.btn_add);
        btnView = findViewById(R.id.btn_view);
        btnUpdate = findViewById(R.id.btn_update);
        btnDelete = findViewById(R.id.btn_delete);
        btnClear = findViewById(R.id.btn_clear);

        // CREATE
        btnAdd.setOnClickListener(v -> {
            boolean inserted = myDb.insertEmployee(
                    editName.getText().toString(),
                    editDesign.getText().toString()
            );
            Toast.makeText(this, inserted ? "Inserted" : "Failed", Toast.LENGTH_SHORT).show();
        });

        // READ
        btnView.setOnClickListener(v -> {
            Cursor res = myDb.getAllEmployees();

            if (res.getCount() == 0) {
                showMsg("Error", "No records found");
                return;
            }

            StringBuilder buffer = new StringBuilder();
            while (res.moveToNext()) {
                buffer.append("ID: ").append(res.getString(0)).append("\n")
                        .append("Name: ").append(res.getString(1)).append("\n")
                        .append("Designation: ").append(res.getString(2)).append("\n\n");
            }

            showMsg("Employees", buffer.toString());
        });

        // UPDATE
        btnUpdate.setOnClickListener(v -> {
            if (editId.getText().toString().isEmpty()) {
                Toast.makeText(this, "Enter ID", Toast.LENGTH_SHORT).show();
                return;
            }

            boolean updated = myDb.updateEmployee(
                    editId.getText().toString(),
                    editName.getText().toString(),
                    editDesign.getText().toString()
            );

            Toast.makeText(this, updated ? "Updated" : "Not Updated", Toast.LENGTH_SHORT).show();
        });

        // DELETE
        btnDelete.setOnClickListener(v -> {
            if (editId.getText().toString().isEmpty()) {
                Toast.makeText(this, "Enter ID", Toast.LENGTH_SHORT).show();
                return;
            }

            int deleted = myDb.deleteEmployee(editId.getText().toString());

            Toast.makeText(this, deleted > 0 ? "Deleted" : "ID Not Found", Toast.LENGTH_SHORT).show();
        });

        // CLEAR
        btnClear.setOnClickListener(v -> {
            editName.setText("");
            editDesign.setText("");
            editId.setText("");
            Toast.makeText(this, "Cleared", Toast.LENGTH_SHORT).show();
        });
    }

    public void showMsg(String title, String msg) {
        new AlertDialog.Builder(this)
                .setTitle(title)
                .setMessage(msg)
                .setCancelable(true)
                .show();
    }
}