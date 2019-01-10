package com.example.pagedlistdemo;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.pagedlistdemo.model.Item;

import androidx.annotation.NonNull;
import androidx.paging.PagedListAdapter;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

public class ItemAdapter extends PagedListAdapter<Item, ItemAdapter.ItemViewHolder> {
    private static DiffUtil.ItemCallback<Item> diffCallback = new DiffUtil.ItemCallback<Item>() {
        @Override
        public boolean areItemsTheSame(Item oldItem, Item newItem) {
            return oldItem.question_id == newItem.question_id;
        }

        @Override
        public boolean areContentsTheSame(Item oldItem, Item newItem) {
            return oldItem.equals(newItem);
        }
    };
    private Context mContext;

    protected ItemAdapter(Context mContext) {
        super(diffCallback);
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.user_item, parent, false);
        return new ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder holder, int position) {
        Item item = getItem(position);
        if (item != null) {
            holder.textViewName.setText(item.owner.display_name);
            Glide.with(mContext).load(item.owner.profile_image).into(holder.imageView);
            holder.textViewRep.setText(Integer.toString(item.owner.reputation));
            holder.textView.setText(Integer.toString(position));
        } else {
            Toast.makeText(mContext, "Empty at " + position, Toast.LENGTH_SHORT).show();
        }
    }

    class ItemViewHolder extends RecyclerView.ViewHolder {

        TextView textViewName;
        TextView textViewRep;
        ImageView imageView;
        TextView textView;

        public ItemViewHolder(View itemView) {
            super(itemView);
            textViewName = itemView.findViewById(R.id.username);
            imageView = itemView.findViewById(R.id.userimage);
            textViewRep = itemView.findViewById(R.id.userrep);
            textView = itemView.findViewById(R.id.index);
        }
    }
}
