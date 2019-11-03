package com.grapefruit.schoolmeal.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.grapefruit.schoolmeal.R;
import com.grapefruit.schoolmeal.databinding.ViewEduLocationSelectBinding;
import com.grapefruit.schoolmeal.utils.Constants;

import java.util.ArrayList;
import java.util.List;

public class EduLocationAdapter extends RecyclerView.Adapter<EduLocationAdapter.EduLocationViewHolder> {

    private String location;
    private List<EduLocationItem> list = new ArrayList<>();
    private EduLocationItem item;
    private int selectedItem = -1;

    public EduLocationAdapter(String[] edu_location) {
        for (String s : edu_location) {
            item = new EduLocationItem().setTitle(s);
            list.add(item);
        }
    }

    @NonNull
    @Override
    public EduLocationViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_edu_location_select, parent, false);
        return new EduLocationViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull EduLocationViewHolder holder, int position) {
        EduLocationItem item = list.get(position);
        holder.binding.title.setText(item.getTitle());
        holder.binding.select.setChecked(position == selectedItem);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public class EduLocationViewHolder extends RecyclerView.ViewHolder {

        private static final int SEOUL = 0; // 서울
        private static final int GYEONGGI = 1; // 경기
        private static final int BUSAN = 2; // 부산
        private static final int INCHON = 3; // 인천
        private static final int DAEJEON = 4; // 대전
        private static final int DAEGU = 5; // 대구
        private static final int ULSAN = 6; // 울산
        private static final int GWANGJU = 7; // 광주
        private static final int KWANGWON = 8; // 강원
        private static final int CHUNGBUK = 9; // 충북
        private static final int CHUNGNAM = 10; // 충남
        private static final int GYEONGBUK = 11; // 경북
        private static final int GYEONGNAM = 12; // 경남
        private static final int JEJU = 13; // 제주
        private static final int JEONNAM = 14; // 전남
        private static final int JEONBUK = 15; // 전북
        private static final int SEJONG = 16; // 세종

        private ViewEduLocationSelectBinding binding;

        public EduLocationViewHolder(@NonNull View itemView) {
            super(itemView);
            binding = DataBindingUtil.bind(itemView);
            View.OnClickListener listener = view -> {
                selectedItem = getAdapterPosition();
                notifyDataSetChanged();
                switch (selectedItem) {
                    case SEOUL: setLocation(Constants.SEOUL); break;
                    case GYEONGGI: setLocation(Constants.GYEONGGI); break;
                    case BUSAN: setLocation(Constants.BUSAN); break;
                    case INCHON: setLocation(Constants.INCHON); break;
                    case DAEJEON: setLocation(Constants.DAEJEON); break;
                    case DAEGU: setLocation(Constants.DAEGU); break;
                    case ULSAN: setLocation(Constants.ULSAN); break;
                    case GWANGJU: setLocation(Constants.GWANGJU); break;
                    case KWANGWON: setLocation(Constants.KWANGWON); break;
                    case CHUNGBUK: setLocation(Constants.CHUNGBUK); break;
                    case CHUNGNAM: setLocation(Constants.CHUNGNAM); break;
                    case GYEONGBUK: setLocation(Constants.GYEONGBUK); break;
                    case GYEONGNAM: setLocation(Constants.GYEONGNAM); break;
                    case JEJU: setLocation(Constants.JEJU); break;
                    case JEONNAM: setLocation(Constants.JEONNAM); break;
                    case JEONBUK: setLocation(Constants.JEONBUK); break;
                    case SEJONG: setLocation(Constants.SEJONG); break;
                    default: setLocation(null); break;
                }
            };
            itemView.setOnClickListener(listener);
            binding.select.setOnClickListener(listener);
        }
    }

    public class EduLocationItem {

        private String title;

        public String getTitle() {
            return title;
        }

        public EduLocationItem setTitle(String title) {
            this.title = title;
            return this;
        }
    }
}
