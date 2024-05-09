package com.example.androidfinalproject;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

public class TypesFragment extends Fragment  {

    public TypesFragment() {
        super(R.layout.types_frag);
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        String type0 = requireArguments().getString("type0");
        String type1 = requireArguments().getString("type1");
        TextView tv0 = view.findViewById(R.id.type_0);
        tv0.setText(type0);
        TextView tv1 = view.findViewById(R.id.type_1);
        tv1.setText(type1);
    }
}
