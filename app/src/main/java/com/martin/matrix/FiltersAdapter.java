package com.martin.matrix;

import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.martin.matrix.entity.FilterInfo;

import java.io.File;
import java.util.List;

public class FiltersAdapter extends RecyclerView.Adapter<FiltersAdapter.MyViewHolder> {
    private LayoutInflater mInflater;
    private List<FilterInfo> filters;
    private String path;

    public FiltersAdapter(LayoutInflater mInflater, List<FilterInfo> filters) {
        this.mInflater = mInflater;
        this.filters = filters;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new MyViewHolder(mInflater.inflate(R.layout.list_item, parent, false));
    }

    @Override
    public int getItemCount() {
        return filters.size();
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        if (TextUtils.isEmpty(path)) {
            holder.imageView.setImageResource(R.mipmap.test1);
        } else {
            holder.imageView.setImageURI(Uri.fromFile(new File(path)));
        }
        holder.imageView.setColorFilter(new ColorMatrixColorFilter(new ColorMatrix(filters.get(position).getColorMatrix())));
        holder.titleText.setText(filters.get(position).getName());
    }

    public void setImagePath(String path) {
        this.path = path;
        notifyDataSetChanged();
    }

    static class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView titleText;

        public MyViewHolder(View view) {
            super(view);
            imageView = view.findViewById(R.id.img);
            titleText = view.findViewById(R.id.title);
        }
    }

}


