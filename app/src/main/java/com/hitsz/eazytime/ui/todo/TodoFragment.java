package com.hitsz.eazytime.ui.todo;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
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
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.hitsz.eazytime.R;
import com.hitsz.eazytime.model.FinishedTodo;
import com.hitsz.eazytime.model.Todo;
import com.hitsz.eazytime.ui.todo.AddTodoDialog;

import org.litepal.LitePal;
import org.litepal.tablemanager.Connector;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

public class TodoFragment extends Fragment implements View.OnClickListener {

    private TodoViewModel todoViewModel;

    public class VH extends RecyclerView.ViewHolder {
        TextView title,time;
        Button bt_delete;

        public VH(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.todo_item_title);
            time = itemView.findViewById(R.id.todo_item_time);
            bt_delete = itemView.findViewById(R.id.delete_todo_item);
        }
    }

    public class TodoItemAdapter extends RecyclerView.Adapter<VH> {//适配器

        private List<Todo> dataList;

        public TodoItemAdapter(){
            dataList=new ArrayList<>();
        }
        public TodoItemAdapter(List<Todo> dataList) {
            this.dataList = dataList;
        }

        @NonNull
        @Override
        public VH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return new VH(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_todo, parent, false));
        }

        @Override
        public void onBindViewHolder(@NonNull VH holder, int position) {
            Todo x = dataList.get(position);
            holder.title.setText(x.getTitle());
            ;
            holder.time.setText(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(x.getStartTime()));

            holder.bt_delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v){
                    FinishedTodo ft=new FinishedTodo(x.getTitle(),new Date(),x.getPriority());
                    ft.save();
                    LitePal.delete(Todo.class,x.getId());
                    refresh();
                }
            });
        }

        @Override
        public int getItemCount() {
            return dataList.size();
        }
        public void refresh() {
            dataList = LitePal.findAll(Todo.class);
            dataList.sort(Comparator.naturalOrder());
            notifyDataSetChanged();
        }
    }


    static TodoItemAdapter adapter;
    RecyclerView todoRV;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        todoViewModel =
                new ViewModelProvider(this).get(TodoViewModel.class);
        View root = inflater.inflate(R.layout.fragment_todo, container, false);

        adapter = new TodoItemAdapter(new ArrayList<>());
        adapter.refresh();
        todoRV = root.findViewById(R.id.todo_rv);
        todoRV.setAdapter(adapter);
        todoRV.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false));

        FloatingActionButton fab = root.findViewById(R.id.add_todo);
        fab.setOnClickListener(this);
        return root;
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.add_todo) {
            new AddTodoDialog().show(getFragmentManager(), "call from todo");
        }
    }
}