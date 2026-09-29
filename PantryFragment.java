package com.example.smartpantrymanager;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;



public class PantryFragment extends Fragment {

    private DatabaseHelper dbHelper;
    private PantryAdapter adapter;
    private RecyclerView recyclerView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_pantry, container, false);
        dbHelper = new DatabaseHelper(getContext());

        recyclerView = view.findViewById(R.id.pantry_recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        FloatingActionButton fab = view.findViewById(R.id.fab_add_ingredient);
        fab.setOnClickListener(v -> startActivity(new Intent(getActivity(), AddEditIngredientActivity.class)));

        loadPantryData();
        return view;

    }

    @Override
    public void onResume() {
        super.onResume();
        loadPantryData();
    }

    private void loadPantryData() {
        Cursor cursor = dbHelper.getAllPantryItems();
        if (adapter == null) {
            adapter = new PantryAdapter(getContext(), cursor);
            recyclerView.setAdapter(adapter);
        } else {
            adapter.swapCursor(cursor);
        }
    }

}
