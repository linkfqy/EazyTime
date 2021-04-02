package com.hitsz.eazytime.ui.todo;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.hitsz.eazytime.R;
import com.hitsz.eazytime.ui.todo.AddTodoDialog;

public class TodoFragment extends Fragment implements View.OnClickListener{

    private TodoViewModel todoViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        todoViewModel =
                new ViewModelProvider(this).get(TodoViewModel.class);
        View root = inflater.inflate(R.layout.fragment_todo, container, false);
        final TextView textTodo = root.findViewById(R.id.text_todo);
        todoViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textTodo.setText(s);
            }
        });

        FloatingActionButton fab = root.findViewById(R.id.add_todo);
        fab.setOnClickListener(this);
        return root;
    }
    @Override
    public void onClick(View view) {
//                Snackbar.make(view, "搞个DialogFragment出来", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
        new AddTodoDialog().show(getFragmentManager(),"call from todo");
    }
}