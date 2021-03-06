package com.example.miniprojet;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.List;

public class AdapterUsers extends  RecyclerView.Adapter<AdapterUsers.MyHolder>{

    Context context;
    List<ModelUser> userList;

    //Constructor
    public AdapterUsers (Context context, List<ModelUser> userList){
        this.context = context;
        this.userList = userList;
    }




    @Override
    public int getItemCount() {
        return userList.size();
    }




    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        //inflate layout (row_user.xml)
        View view = LayoutInflater.from(context).inflate(R.layout.row_users, viewGroup, false);
        return new MyHolder(view );
    }

    @Override
    public void onBindViewHolder(@NonNull MyHolder holder, int i) {
        //get data
        final String hisUID = userList.get(i).getUid();
        String userImage = userList.get(i).getImage();
        String userName = userList.get(i).getName();
        final String userEmail = userList.get(i).getEmail();

        //set data
        holder.mNameTv.setText(userName);
        holder.mEmailTv.setText(userEmail);
        try {
            Picasso.get().load(userImage)
                    .placeholder(R.drawable.ic_default_img)
                    .into(holder.mAvatarIv);
        } catch (Exception e) {

        }


        //handle item click
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //show  dialog
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setItems(new String[]{"Profile"}, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (which==0){
                            //profile clicked
                            Intent intent = new Intent(context, ThereProfileActivity.class);
                            intent.putExtra("uid",hisUID);
                            context.startActivity(intent);
                        }

                    }
                });
                builder.create().show();
            }
        });

    }



    class MyHolder extends RecyclerView.ViewHolder{

        ImageView mAvatarIv;
        TextView mNameTv, mEmailTv;

        public MyHolder(@NonNull View itemView) {
            super(itemView);

            //init views
            mAvatarIv = itemView.findViewById(R.id.avatarIv);
            mNameTv = itemView.findViewById(R.id.nameTv);
            mEmailTv = itemView.findViewById(R.id.emailTv);

        }
    }
}
