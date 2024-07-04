package com.computer.socialcampus.helper;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.computer.socialcampus.R;
import com.computer.socialcampus.group.Group;

import java.util.List;

public class GroupAdapter extends RecyclerView.Adapter<GroupAdapter.GroupViewHolder> {
    private List<Group> groupList;
    private OnItemClickListener listener;

    public interface OnItemClickListener {
        void onItemClick(Group group);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    public static class GroupViewHolder extends RecyclerView.ViewHolder {
        public TextView nameTextView;
        public TextView descriptionTextView;

        public GroupViewHolder(View itemView, final OnItemClickListener listener) {
            super(itemView);
            nameTextView = itemView.findViewById(R.id.group_name);
            descriptionTextView = itemView.findViewById(R.id.group_description);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            listener.onItemClick((Group) v.getTag());
                        }
                    }
                }
            });
        }
    }

    public GroupAdapter(List<Group> groupList) {
        this.groupList = groupList;
    }

    @Override
    public GroupViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.group_item, parent, false);
        return new GroupViewHolder(itemView, listener);
    }

    @Override
    public void onBindViewHolder(GroupViewHolder holder, int position) {
        Group currentGroup = groupList.get(position);
        holder.nameTextView.setText(currentGroup.getGroupName());
        holder.descriptionTextView.setText(currentGroup.getGroupDescription());
        holder.itemView.setTag(currentGroup);
    }

    @Override
    public int getItemCount() {
        return groupList.size();
    }
}
