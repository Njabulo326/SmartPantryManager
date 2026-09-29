package com.example.smartpantrymanager;

import android.database.Cursor;
import android.os.Bundle;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;


public class RecipeDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_detail);

        TextView tvTitle = findViewById(R.id.tv_recipe_title);
        TextView tvIngredients = findViewById(R.id.tv_recipe_ingredients);
        TextView tvSteps = findViewById(R.id.tv_recipe_steps);

        int recipeId = getIntent().getIntExtra("recipe_id", -1);
        DatabaseHelper dbHelper = new DatabaseHelper(this);

        // Fetch Recipe Parent Attributes
        Cursor recipeCursor = dbHelper.getReadableDatabase().rawQuery(
               "SELECT * FROM " + DatabaseHelper.TABLE_RECIPES + " WHERE " + DatabaseHelper.COL_ID + " = ?",
               new String[]{String.valueOf(recipeId)});

        if (recipeCursor.moveToFirst()) {
            tvTitle.setText(recipeCursor.getString(recipeCursor.getColumnIndexOrThrow(DatabaseHelper.COL_RECIPE_NAME)));
            tvSteps.setText(recipeCursor.getString(recipeCursor.getColumnIndexOrThrow(DatabaseHelper.COL_RECIPE_STEPS)));
        }
        recipeCursor.close();

        // Fetch Recipe Child Composition Items
        Cursor ingCursor = (Cursor) dbHelper.getRecipeIngredients(recipeId);
        StringBuilder builder = new StringBuilder();
        while (ingCursor.moveToNext()) {
            builder.append(". ")
                    .append(ingCursor.getString(ingCursor.getColumnIndexOrThrow(DatabaseHelper.COL_REQ_ING_NAME)))
        }
        ingCursor.close();
        tvIngredients.setText(builder.toString());
    }


}
