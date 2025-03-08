package com.example.lab5new;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class CustomRecyclerAdapter extends RecyclerView.Adapter<CustomRecyclerAdapter.ViewHolder>{
    private final LayoutInflater inflater;
    private final List<Image> images;
    CustomRecyclerAdapter(Context context, List<Image> images) {
        this.images = images;
        this.inflater = LayoutInflater.from(context);
    }
    public void onBindViewHolder(CustomRecyclerAdapter.ViewHolder holder, int position) {
        Image image = images.get(position);
        holder.text.setText(image.getName());
        holder.image.setImageResource(image.getImageResource());
    }
    @NonNull
    @Override
    public CustomRecyclerAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int
            viewType) {
        View view = inflater.inflate(R.layout.list_item, parent, false);
        return new ViewHolder(view);
    }
    @Override
    public int getItemCount() {
        return images.size();
    }
    public class ViewHolder extends RecyclerView.ViewHolder {
        final ImageView image;
        final TextView text;
        ViewHolder(View view){
            super(view);
            image = view.findViewById(R.id.imageView);
            text = view.findViewById(R.id.textView);
        }
    }
}
