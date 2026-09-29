package com.example.smartpantrymanager;

import android.content.ContentValues;
import android.content.Context;
import android.content.ReceiverCallNotAllowedException;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;


public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "pantry.db";
    private static final int DATABASE_VERSION = 1;

    // Table Names
    public static final String TABLE_PANTRY = "pantry";
    public static final String TABLE_RECIPES = "recipes";
    public static final String TABLE_RECIPE_INGREDIENTS = "recipe_ingredients";

    // Common Column
    public static final String COL_ID = "id";

    // Pantry Columns
    public static final String COL_PANTRY_NAME = "name";
    public static final String COL_PANTRY_QTY = "quantity";
    public static final String COL_PANTRY_UNIT = "unit";

    // Recipe Columns
    public static final String COL_RECIPE_NAME = "name";
    public static final String COL_RECIPE_STEPS = "steps";

    // Recipe Ingredients Columns
    public static final String COL_RI_RECIPE_ID = "recipe_id";
    public static final String COL_RI_NAME = "ingredient_name";

    public DatabaseHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Create Tables
        db.execSQL("CREATE TABLE " + TABLE_PANTRY + " (" +
                COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COL_PANTRY_NAME + " TEXT UNIQUE, " +
                COL_PANTRY_QTY + " REAL, " +
                COL_PANTRY_UNIT + " TEXT)");

        db.execSQL("CREATE TABLE " + TABLE_RECIPES + " (" +
                COL_ID + " INTERGER PRIMARY KEY AUTOINCREMENT, " +
                COL_RECIPE_NAME + " TEXT, " +
                COL_RECIPE_STEPS + " TEXT)");

        db.execSQL("CREATE TABLE " + TABLE_RECIPE_INGREDIENTS + " (" +
                COL_ID + " INTERGER PRIMARY KEY AUTOINCREMENT, " +
                COL_RI_RECIPE_ID + " INTERGER, " +
                COL_RI_NAME + "TEXT)");

        seedRecipes(db);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PANTRY);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_RECIPES);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_RECIPE_INGREDIENTS);
        onCreate(db);

    }

    // ---Pantry CRUD Operations ---
    public boolean addPantryItem(String name, double qty, String unit) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(COL_PANTRY_NAME, name.trim().toLowerCase());
        cv.put(COL_PANTRY_QTY, qty);
        cv.put(COL_PANTRY_UNIT, unit);
        long result = db.insertWithOnConflict(TABLE_PANTRY, null, cv, SQLiteDatabase.CONFLICT_REPLACE);
        return result != -1;
    }

    public void deletePantryItem(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_PANTRY, COL_ID + "=?", new String[]{String.valueOf(id)});
    }

    public Cursor getAllPantryItems() {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("SELECT * FROM " + TABLE_PANTRY + " ORDER BY " + COL_PANTRY_NAME + " ASC", null);
    }

    // ---Core Strict-Matching Business Logic ---
    public List<Recipe> getStrictMatchingRecipes() {
        List<Recipe> matchingRecipes = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        // Subquery checks if any required recipe ingredient is missing from the user's pantry
        String query = "SELECT * FROM " + TABLE_RECIPES + " r WHERE NOT EXISTS (" +
                "SELECT 1 FROM " + TABLE_RECIPE_INGREDIENTS + " ri " +
                "WHERE ri." + COL_RI_RECIPE_ID + " = r." + COL_ID + " " +
                "AND ri." + COL_RI_NAME + "NOT IN (SELECT " + COL_PANTRY_NAME + " FROM " + TABLE_PANTRY + "))";

        Cursor cursor = db.rawQuery(query, null);
        if (cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(cursor.getColumnIndexOrThrow(COL_ID));
                String name = cursor.getString(cursor.getColumnIndexOrThrow(COL_RECIPE_NAME));
                String steps = cursor.getString(cursor.getColumnIndexOrThrow(COL_RECIPE_STEPS));
                matchingRecipes.add(new Recipe(id, name, steps));
            } while (cursor.moveToNext());
        }
        cursor.close();
        return matchingRecipes;

    }

    public List<String> getRecipeIngredients(int recipeId) {
        List<String> ingredients = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT " + COL_RI_NAME + " FROM " + TABLE_RECIPE_INGREDIENTS + " WHERE " + COL_RI_RECIPE_ID + " = ?", new String[]{String.valueOf(recipeId)});
        if (cursor.moveToFirst()) {
            do {
                ingredients.add(cursor.getString(0));

            } while (cursor.moveToNext());
        }
        cursor.close();
        return ingredients;
    }

    // ---Pre-seed 15 Recipes ---
    private void seedRecipes(SQLiteDatabase db) {
        String[][] recipes = {
                {"Scrambled Eggs", "Egg, Butter", "Melt butter in a pan. Whisk eggs and pour in. Stir gently over low heat until cooked."},
                {"Grilled Cheese", "Bread, Cheese, Butter", "Butter the bread. Place cheese between slices. Grill on a skillet until golden brown."},
                {"Pancakes", "Flour, Milk, Egg, Sugar", "Mix dry and wet ingredients. Pour batter onto a hot griddle. Flip when bubbles form."},
                {"Omelet", "Egg, Cheese, Butter, Pepper", "Whisk egg. Cook in melted butter. Add cheese and pepper, then fold over."},
                {"French Toast", "Bread, Egg, Milk, Cinnamon", "Whisk egg, milk, and cinnamon. Dip bread into mixture. Fry until golden on both sides."},
                {"Quesadilla", "Tortilla, Cheese, Chicken", "Place chicken and cheese on a tortilla. Fold in half and toast on a pan until melted."},
                {"Pasta e Olio", "Pasta, Olive Oil, Garlic", "Boil pasta. Saute minced garlic in olive oil. Toss pasta in the garlic oil."},
                {"Caprese Salad", "Tomato, Mozzarella, Olive Oil", "Slice tomato and mozzarella. Layer them alternatively. Drizzle with olive oil."},
                {"Rice and Beans", "Rice, Beans, Onion", "Cook rice. Saute onion and add beans. Mix together and season to taste."},
                {"Simple Salad", "Lettuce, Tomato, Cucumber", "Chop ingredients. Toss together in a large bowl with your preferred seasoning."},
                {"Banana Smoothie", "Banana, Milk, Yogurt", "Blend banana, milk, and yogurt together until smooth."},
                {"PB&J Sandwich", "Bread, Peanut Butter, Jelly", "Spread peanut butter on one slice and jelly on the other. Put together."},
                {"Mashed Potatoes", "Potato, Butter, Milk", "Boil potatoes until soft. Mash thoroughly while mixing in butter and milk."},
                {"Garlic Bread", "Bread, Butter, Garlic", "Mix minced garlic with butter. Spread on bread slices and bake until crispy."}


        };

        for (String[] r : recipes) {
            ContentValues cv = new ContentValues();
            cv.put(COL_RECIPE_NAME, r[0]);
            cv.put(COL_RECIPE_STEPS, r[2]);
            long recipeId = db.insert(TABLE_RECIPES, null, cv);

            String[] ingredients = r[1].split(", ");
            for (String ing : ingredients) {
                ContentValues civ = new ContentValues();
                civ.put(COL_RI_RECIPE_ID, recipeId);
                civ.put(COL_RECIPE_NAME, ing.trim().toLowerCase());
                db.insert(TABLE_RECIPE_INGREDIENTS, null, civ);
            }
        }

    }

}
