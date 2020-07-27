package com.example.yogacommunity;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

import java.sql.ClientInfoStatus;
import java.util.List;

public class CommunityAdapter extends RecyclerView.Adapter<CommunityAdapter.ViewHolder> {

    public List<Blog> blogList;
    public Context context;

    public CommunityAdapter(List<Blog> blogList){

        this.blogList= blogList;

    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.home_card, parent, false);
        context=parent.getContext();
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        String Desc_Data= blogList.get(position).getDiscription();
        holder.setDescText(Desc_Data);

        String image_url= blogList.get(position).getDownloadLink();
        holder.setPost_Image(image_url);

        String userid= blogList.get(position).getUserID();
        holder.setProfiledata(userid);

        String date=blogList.get(position).getTimeStamp();
        holder.getDate(date);


    }

    @Override
    public int getItemCount() {
        return blogList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private View mview;
        private TextView DescView;
        private ImageView post_Image;
        private ImageView ProfilePic;
        private TextView username;
        private TextView date;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            mview=itemView;
        }
        public void setDescText(String DescText){

            DescView= mview.findViewById(R.id.cardDescription);
            DescView.setText(DescText);

        }
        public void setPost_Image(String downloadUri){
            post_Image=mview.findViewById(R.id.cardImage);
            Glide.with(context).load(downloadUri).into(post_Image);


        }
        public void setProfiledata(String user_id){

            final FirebaseDatabase database= FirebaseDatabase.getInstance();
            DatabaseReference myRef= database.getReference().child("Users")
                    .child(user_id);
            ProfilePic=mview.findViewById(R.id.cardProfilepic);
            username=mview.findViewById(R.id.cardUsername);
            myRef.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    String profile_uri = dataSnapshot.child("ProfilePic").getValue().toString();
                    Glide.with(context).load(profile_uri).into(ProfilePic);

                    String Username= dataSnapshot.child("username").getValue().toString();
                    username.setText(Username);
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });

        }

        public void  getDate(String TimeStamp){

            String RequiredStr= (String) TimeStamp.subSequence(4,16);
            date=mview.findViewById(R.id.cardDate);
            date.setText(RequiredStr);


        }
    }
}
