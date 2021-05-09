package com.hitsz.eazytime.ui.focus;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.hitsz.eazytime.R;

public class FocusFragment extends Fragment {

    private FocusViewModel focusViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        focusViewModel =
                new ViewModelProvider(this).get(FocusViewModel.class);
        View root = inflater.inflate(R.layout.fragment_focus, container, false);
        final TextView textView = root.findViewById(R.id.text_focus);
        focusViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });
        Button focusing = root.findViewById(R.id.focusing);
        focusing.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intofocusing = new Intent ("com.excample.focusing.ACTION_START");
                startActivity(intofocusing);
            }
        });
        Button tomato_work_in = root.findViewById(R.id.tomato_work_in);
        tomato_work_in.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intotomato = new Intent ("com.example.tomato.ACTION_START");
                startActivity(intotomato);
            }
        });
        return root;
    }
}