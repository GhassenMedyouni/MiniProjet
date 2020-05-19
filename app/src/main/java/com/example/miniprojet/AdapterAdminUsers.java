package com.example.miniprojet;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
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
        final String userImage = userList2.get(i).getImage();
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

        holder.deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final  ProgressDialog pd = new ProgressDialog(context);
                pd.setMessage("Deleting");
                Query fquery = FirebaseDatabase.getInstance().getReference("Users").orderByChild("uid").equalTo(hisUID);
                fquery.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        for (DataSnapshot ds: dataSnapshot.getChildren()){
                            ds.getRef().removeValue();
                        }
                        Toast.makeText(context,"Deleted successfully", Toast.LENGTH_SHORT).show();
                        pd.dismiss();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }
        });

    }

 /**   private void beginDelete(String hisUID, String userImage) {
        if(userImage.equals("noImage")){
            deleteWithoutImage(hisUID);
        }
        else {
            deleteWithImage(hisUID, userImage);

        }
    }

    private void deleteWithoutImage(String hisUID) {
        final ProgressDialog pd = new ProgressDialog(context);
        pd.setMessage("Deleting");
        Query fquery = FirebaseDatabase.getInstance().getReference("Users").orderByChild("uid").equalTo(hisUID);
        fquery.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot ds: dataSnapshot.getChildren()){
                    ds.getRef().removeValue();
                }
                Toast.makeText(context,"Deleted successfully", Toast.LENGTH_SHORT).show();
                pd.dismiss();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
/**
    private void deleteWithImage(final String hisUID, String userImage) {
        final ProgressDialog pd = new ProgressDialog(context);
        pd.setMessage("Deleting");

        StorageReference picRef = FirebaseStorage.getInstance().getReferenceFromUrl(userImage);
        picRef.delete().addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Query fquery = FirebaseDatabase.getInstance().getReference("Users").orderByChild("uid").equalTo(hisUID);
                fquery.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        for (DataSnapshot ds: dataSnapshot.getChildren()){
                            ds.getRef().removeValue();
                        }
                        Toast.makeText(context,"Deleted successfully", Toast.LENGTH_SHORT).show();
                        pd.dismiss();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                pd.dismiss();
                Toast.makeText(context, ""+e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }  **/

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
           deleteBtn = itemView.findViewById(R.id.deleteBtn);
       }
   }


}
