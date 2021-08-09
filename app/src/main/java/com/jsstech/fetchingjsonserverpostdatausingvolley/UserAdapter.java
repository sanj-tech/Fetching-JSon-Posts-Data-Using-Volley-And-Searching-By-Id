package com.jsstech.fetchingjsonserverpostdatausingvolley;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.UserViewHolder> {
    List<UserModel> postList;

    public UserAdapter(Context applicationContext,List<UserModel> postList) {
        this.postList = postList;
    }


    @NonNull
    @Override
    public UserViewHolder onCreateViewHolder(@NonNull ViewGroup parent,int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_layout,parent,false);
        return new UserViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UserViewHolder holder,int position) {
        holder.textViewid.setText(Integer.toString(postList.get(position).getId()));
        holder.textViewuserid.setText(Integer.toString(postList.get(position).getUserId()));
        holder.textViewtitle.setText(postList.get(position).getTitle());
        holder.textViewbody.setText(postList.get(position).getPostDesc());
    }

    @Override
    public int getItemCount() {
        return postList.size();
    }

    public class UserViewHolder extends RecyclerView.ViewHolder {
        TextView textViewid,textViewuserid,textViewtitle,textViewbody;

        public UserViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewid=itemView.findViewById(R.id.id);
            textViewuserid=itemView.findViewById(R.id.userId);
            textViewtitle=itemView.findViewById(R.id.postTitle);
            textViewbody=itemView.findViewById(R.id.postDesc);



        }
    }
}
