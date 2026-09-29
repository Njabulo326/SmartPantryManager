package com.example.smartpantrymanager;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class AddEditActivity extends AppCompatActivity {

    private EditText etName, etQty, etUnit, etExpiry;
    private Button btnSave, btnDelete;
    private DatabaseHelper dbHelper;
    private int itemId = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_edit);

        etName = findViewById(R.id.et_ingredient_name);
        etQty = findViewById(R.id.et_quantity);
        etUnit = findViewById(R.id.et_unit);
        etExpiry = findViewById(R.id.et_expiry);
        btnSave = findViewById(R.id.btn_save);
        btnDelete = findViewById(R.id.btn_delete);

        dbHelper = new DatabaseHelper(this);

        // Check if updating item
        if (getIntent().hasExtra("id")) {
            itemId = getIntent().getIntExtra("id", -1);
            etName.setText(getIntent().getStringExtra("name"));
            etQty.setText(String.valueOf(getIntent().getDoubleExtra("qty", 0.0)));
            etUnit.setText(getIntent().getStringExtra("unit"));
            etExpiry.setText(getIntent().getStringExtra("expiry"));
            btnDelete.setVisibility(View.VISIBLE);
        }

        btnSave.setOnClickListener(v -> saveItem());
        btnDelete.setOnClickListener(v -> {
            if (itemId != -1) {
                dbHelper.deletePantryItem(itemId);
                Toast.makeText(this, "Item Deleted", Toast.LENGTH_SHORT).show();
                finish();
            }
        });


    }

    private void saveItem() {
        String name = etName.getText().toString().trim();
        String qtyStr = etQty.getText().toString().trim();
        String unit = etUnit.getText().toString().trim();
        String expiry = etExpiry.getText().toString().trim();

        // Strict Form Validation Rules
        if (TextUtils.isEmpty(name)) {
            etName.setError("Ingredient name required");
            return;
        }
        if (TextUtils.isEmpty(qtyStr)) {
            etQty.setError("Quantity required");
            return;
        }

        double quantity = Double.parseDouble(qtyStr);

        boolean success;
        if (itemId == -1) {
            success = dbHelper.addPantryItem(name, quantity, unit, expiry);
        } else {
            success =dbHelper.updatePantryItem(itemId, name, quantity, unit, expiry);
        }

        if (success) {
            Toast.makeText(this, "Success", Toast.LENGTH_SHORT).show();
            finish();
        } else {
            Toast.makeText(this, "Error saving item.", Toast.LENGTH_SHORT).show();
        }


    }



}
