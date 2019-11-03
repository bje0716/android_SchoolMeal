package com.grapefruit.schoolmeal.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.grapefruit.schoolmeal.R;
import com.grapefruit.schoolmeal.databinding.ViewMainMealBinding;
import com.grapefruit.schoolmeal.datas.MealData;

public class MealAdapter extends RecyclerView.Adapter<MealAdapter.MealViewHolder> {

    private MealData data;

    public MealAdapter(MealData data) {
        this.data = data;
    }

    @NonNull
    @Override
    public MealViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_main_meal, parent, false);
        return new MealViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MealViewHolder holder, int position) {
        holder.binding.date.setText(data.getDate().get(position));
        holder.binding.lunch.setText(data.getFood().get(position));
    }

    @Override
    public int getItemCount() {
        return data.getDate().size();
    }

    public class MealViewHolder extends RecyclerView.ViewHolder {

        private ViewMainMealBinding binding;

        public MealViewHolder(@NonNull View itemView) {
            super(itemView);
            binding = DataBindingUtil.bind(itemView);

            if (binding.lunch.getText().toString().length() <= 0) binding.lunch.setText("오늘 급식은 없습니다.");
        }
    }
}
