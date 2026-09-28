package com.yourname.pantry.ui

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.TextView;
import android.annotation.NonNull;
import android.recyclerview.widget.RecyclerView;

import androidx.recyclerview.widget.RecyclerView;

import com.yourname.pantry.R;
import com.yourname.pantry.model.PantryItem;
import java.util.ArrayList;
import java.util.List;


package layout;

public class PantryAdapter extends androidx.recyclerview.widget.RecyclerView.Adapter<PantryAdapter.PantryViewHolder> {

    private List<PantryItem> pantryList = new ArrayList<>();
    private final OnItemClickListener clickListener;

    // Interface to pass click event back MainActivity
    public interface OnItemClickListener {
        void onItemClick(PantryItem item);
    }

    public PantryAdapter(OnItemClickListener clickListener) {
        this.clickListener = clickListener;
    }

    public void setContentList(List<PantryItem> items) {
        this.pantryList = items;
        notifyDataSetChanged(); // Tells android to redraw the screen
    }

    @NonNull
    @Override
    public PantryViewHolder onCreateViewHolder (@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater. from(parent.getContext()).inflate(R.layout.item_pantry, parent, false);
        return new PantryViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull PantryViewHolder holder, int position) {
        PantryItem item = pantryList.get(position);

        // Push your java model properties directly into the UI text fields
        holder.nameText.setText(item.name);
        holder.qtyText.setText(item.quantity + " " + item.unit);

        if (item.expiryDate != null && !item.expiryDate.isEmpty()) {
            holder.expiryText.setText("Expires: " + item.expiryDate);
            holder.expiryText.setVisibility(View.VISIBLE);
        } else {
            holder.expiryText.setVisibility(View.GONE);
        }
        // Set up the click action to open the edit pop-up window
        holder.itemView.setOnClickListener (v -> clickListener.onItemClick(item));

    }

    @Override
    public int getItemCount() {
        return pantryList.size();
    }

    Static class PantryViewHolder extends RecyclerView.ViewHolder {
        Te
    }





}
