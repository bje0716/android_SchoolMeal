package com.grapefruit.schoolmeal.adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.grapefruit.schoolmeal.R;
import com.grapefruit.schoolmeal.datas.School;
import com.grapefruit.schoolmeal.databinding.ViewSchoolSearchResultBinding;
import com.grapefruit.schoolmeal.ui.activities.MainActivity;
import com.grapefruit.schoolmeal.utils.Constants;
import com.grapefruit.schoolmeal.utils.PreferenceHelper;

import java.util.ArrayList;

public class SchoolSearchResultAdapter extends RecyclerView.Adapter<SchoolSearchResultAdapter.SchoolSearchResultViewHolder> {

    private ArrayList<School> list;
    private PreferenceHelper helper;
    private Context context;

    public SchoolSearchResultAdapter(ArrayList<School> list, Context context) {
        this.list = list;
        this.context = context;
        helper = new PreferenceHelper(context);
    }

    @NonNull
    @Override
    public SchoolSearchResultViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_school_search_result, parent, false);
        return new SchoolSearchResultViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull SchoolSearchResultViewHolder holder, int position) {
        School item = list.get(position);
        holder.binding.kraOrgNm.setText(item.getKraOrgNm());
        holder.binding.zipAdres.setText(item.getZipAdres());

        holder.itemView.setOnClickListener(v -> {
            switch (holder.getAdapterPosition()) {
                default:
                    helper.putString(Constants.SCHOOL_NAME, item.getKraOrgNm());
                    helper.putString(Constants.SCHOOL_CODE, item.getSchulCode());
                    helper.putString(Constants.SCHOOL_KND, item.getSchulKndScCode());
                    helper.putString(Constants.SCHOOL_CRSE, item.getSchulCrseScCode());
                    helper.putString(Constants.SCHOOL_ADDRESS, item.getZipAdres());

                    ((Activity) context).startActivity(new Intent(holder.itemView.getContext(), MainActivity.class)
                            .putExtra(Constants.SCHOOL_NAME, item.getKraOrgNm()));
                    ((Activity) context).finish();
                    break;
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class SchoolSearchResultViewHolder extends RecyclerView.ViewHolder {

        private ViewSchoolSearchResultBinding binding;

        public SchoolSearchResultViewHolder(@NonNull View itemView) {
            super(itemView);
            binding = DataBindingUtil.bind(itemView);
        }
    }
}
