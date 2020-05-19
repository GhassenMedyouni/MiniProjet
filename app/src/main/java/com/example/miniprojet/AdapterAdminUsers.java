package com.example.miniprojet;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.List;

public class AdapterAdminUsers extends  RecyclerView.Adapter<AdapterAdminUsers.MyHolder> {

    Context context;

    public AdapterAdminUsers(Context context, List<ModelUser> userList2) {
        this.context = context;
        this.userList2 = userList2;
    }

    List<ModelUser> userList2;




    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_users_admin, parent, false);
        return new MyHolder(view );
    }

    @Override
    public void onBindViewHolder(@NonNull MyHolder holder, int i) {
        //get data
        final String hisUID = userList2.get(i).getUid();
        String userImage = userList2.get(i).getImage();
        String userName = userList2.get(i).getName();
        final String userEmail = userList2.get(i).getEmail();

        //set data
        holder.mNameTvAd.setText(userName);
        holder.mEmailTvAd.setText(userEmail);
        try {
            Picasso.get().load(userImage)
                    .placeholder(R.drawable.ic_default_img)
                    .into(holder.mAvatarIvAd);
        } catch (Exception e){

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

    @Override
    public int getItemCount() {
        return userList2.size();
    }

    class MyHolder extends RecyclerView.ViewHolder{

       ImageView mAvatarIvAd;
       TextView mNameTvAd, mEmailTvAd;
       ImageButton deleteBtn;

       public MyHolder(@NonNull View itemView) {
           super(itemView);

           //init views
           mAvatarIvAd = itemView.findViewById(R.id.avatarIvAd);
           mNameTvAd = itemView.findViewById(R.id.nameTvAd);
           mEmailTvAd = itemView.findViewById(R.id.emailTvAd);
       }
   }


}
