package com.example.smartpantrymanager;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SwitchCompat;
import androidx.fragment.app.Fragment;

public class SettingsFragment extends Fragment {

    @Nullable
    @Override
    public View on CreatView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstancesState) {
        View view = inflater.inflate(R.layout.fragment_settings, container, false);

        SwitchCompat toggleAlerts = view.findViewById(R.id.switch_alerts);
        toggleAlerts.setOnCheckedChangeListener((buttonView, isChecked) -> {
            String status = isChecked? "Alerts Enabled" : "Alerts Disabled";
            Toast.makeText(getContext(), status, Toast.LENGTH_SHORT).show();
        });

        return view;
    }

}
