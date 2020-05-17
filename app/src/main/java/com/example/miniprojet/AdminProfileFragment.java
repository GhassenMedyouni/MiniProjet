package com.example.miniprojet;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import static com.google.firebase.storage.FirebaseStorage.getInstance;


/**
 * A simple {@link Fragment} subclass.
 */
public class AdminProfileFragment extends Fragment {

    //firebase
    FirebaseAuth firebaseAuth;
    FirebaseUser admin;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;

    //views
    ImageView avatarIv, coverIv;
    TextView nameTv, emailTv, phoneTv;
    FloatingActionButton fab ;

    public AdminProfileFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_admin_profile, container, false);

        //init firebase
        firebaseAuth = FirebaseAuth.getInstance();
        admin = firebaseAuth.getCurrentUser();
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("Admins");
       // storageReference = getInstance().getReference(); //firebase storage reference

        //init views
        avatarIv = view.findViewById(R.id.avatarIvAd);
        coverIv = view.findViewById(R.id.coverIvAd);
        nameTv = view.findViewById(R.id.nameTvAd);
        emailTv = view.findViewById(R.id.emailTvAd);
        phoneTv = view.findViewById(R.id.phoneTvAd);

        Query query = databaseReference.orderByChild("email").equalTo(admin.getEmail());
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                //check until required data get
                for (DataSnapshot ds : dataSnapshot.getChildren()){
                    //get data
                    String name = ""+ ds.child("name").getValue();
                    String email = ""+ ds.child("email").getValue();
                    String phone = ""+ ds.child("phone").getValue();
                    String image = ""+ ds.child("image").getValue();
                    String cover = ""+ ds.child("cover").getValue();


                    //set data
                    nameTv.setText(name);
                    emailTv.setText(email);
                    phoneTv.setText(phone);
                    try {
                        //if image is recieved then set
                        Picasso.get().load(image).into(avatarIv);
                    }
                    catch (Exception e) {
                        //if there is any exception while getting image then set default
                        Picasso.get().load(R.drawable.ic_default_img_white).into(avatarIv);
                    }

                    try {
                        //if image is recieved then set
                        Picasso.get().load(cover).into(coverIv);
                    }
                    catch (Exception e) {
                        //if there is any exception while getting image then set default

                    }
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        return view;
    }
}
