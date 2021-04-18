package com.hitsz.eazytime.ui.home;

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
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.hitsz.eazytime.R;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;

    private class VH extends RecyclerView.ViewHolder {
        TextView tv1;
        TextView tv2;
        TextView bt_delete;

        public VH(@NonNull View itemView) {
            super(itemView);
            tv1 = itemView.findViewById(R.id.test_tv1);
            tv2 = itemView.findViewById(R.id.test_tv2);
            bt_delete = itemView.findViewById(R.id.test_bt);
        }
    }
    private class LetterAdapter extends RecyclerView.Adapter<VH> {//适配器

        private List<Character> dataList;

        public LetterAdapter(List<Character> dataList) {
            this.dataList = dataList;
        }

        //需要实现的三个方法：onCreateViewHolder,onBindViewHolder,getItemCount
        @NonNull
        @Override
        public VH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return new VH(LayoutInflater.from(parent.getContext()).inflate(R.layout.test_item, parent, false));
        }

        @Override
        public void onBindViewHolder(@NonNull VH holder, int position) {
            Character c = dataList.get(position);
            holder.tv1.setText(c.toString());
            holder.tv2.setText(String.valueOf(Integer.valueOf(c)));

            holder.bt_delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v){
                    if (dataList.size()<=1) {
                        Snackbar.make(v, "connot delete this item", Snackbar.LENGTH_SHORT).show();
                    }
                    else{
                        removeItem(position);
                    }
                }
            });
        }

        @Override
        public int getItemCount() {
            return dataList.size();
        }

        public void addItem(int position) {
            dataList.add(position,'.');
            notifyItemInserted(position);
        }

        public void removeItem(int pos) {
            dataList.remove(pos);
            notifyItemRemoved(pos);
            notifyDataSetChanged();
        }
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_home, container, false);

        List<Character> characterList = new ArrayList<>();
        for (char c = 'A'; c <= 'C'; c++) {
            characterList.add(c);
        }//下面四句实现了RecycleView
        LetterAdapter mLetterAdapter = new LetterAdapter(characterList);
        RecyclerView letterReView = root.findViewById(R.id.test_rv);
        letterReView.setAdapter(mLetterAdapter);
        letterReView.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false));

        FloatingActionButton fab = root.findViewById(R.id.test_fab);
        fab.setOnClickListener(new View.OnClickListener() { //
            @Override
            public void onClick(View view) {
//                Snackbar.make(view, "Here's a Snackbar", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
                //new MyDialog().show(getFragmentManager(), "dialog_fragment");
                mLetterAdapter.addItem(mLetterAdapter.getItemCount());
            }
        });
        return root;
    }
}