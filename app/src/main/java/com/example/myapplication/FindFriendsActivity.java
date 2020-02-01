package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.widget.Toolbar;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;

public class FindFriendsActivity extends AppCompatActivity {

    private Toolbar mToolbar;
    private ImageButton SearchButton;
    private EditText SearchInputText;

    private DatabaseReference allUsersDatabaseRef;


    private RecyclerView SearchResultList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_friends);

        mToolbar = findViewById(R.id.find_friends_appbar_layout);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Update Post");

        allUsersDatabaseRef = FirebaseDatabase.getInstance().getReference().child("Users");

        SearchResultList = findViewById(R.id.search_result_list);
        SearchResultList.setHasFixedSize(true);
        SearchResultList.setLayoutManager(new LinearLayoutManager(this));

        SearchButton = findViewById(R.id.search_people_frieds_button);
        SearchInputText = findViewById(R.id.search_box_input);

        SearchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                String SearchBoxInput= SearchInputText.getText().toString();
                SearchPeopleAndFriends(SearchBoxInput);
            }
        });

    }

    private void SearchPeopleAndFriends(String searchBoxInput)
    {
       /* Toast.makeText(this, "Searching...", Toast.LENGTH_LONG).show();

        Query searchPeopleandFriendsQuery = allUsersDatabaseRef.orderByChild("fullname")
                .startAt(searchBoxInput).endAt(searchBoxInput +"\uf8ff");

        FirebaseRecyclerAdapter<FindFriends,FindFriendsViewHolder> firebaseRecyclerAdapter
                = new FirebaseRecyclerAdapter<FindFriends, FindFriendsViewHolder>
                (
                        FindFriends.class,
                        R.layout.all_users_display_layout,
                        FindFriendsViewHolder.class,
                        searchPeopleandFriendsQuery
                )
        {
            @Override
            protected void populateViewHolder(FindFriendsViewHolder viewHolder, FindFriends model, int position)
            {
                viewHolder.setFullname(model.getFullname());
                viewHolder.setStatus(model.getStatus());
                viewHolder.setProfileimage(model.getProfileimage());

            }
        };
        SearchResultList.setAdapter(firebaseRecyclerAdapter);*/
        Query searchPeopleandFriendsQuery = allUsersDatabaseRef.orderByChild("fullname")
                .startAt(searchBoxInput).endAt(searchBoxInput +"\uf8ff");
        FirebaseRecyclerOptions<FindFriends> options = new FirebaseRecyclerOptions.Builder<FindFriends>().setQuery(searchPeopleandFriendsQuery, FindFriends.class).build();
        FirebaseRecyclerAdapter adapter = new FirebaseRecyclerAdapter<FindFriends, FindFriendsViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull FindFriendsViewHolder postsViewHolder, int position, @NonNull FindFriends posts) {


                postsViewHolder.setFullname(posts.getFullname());
                postsViewHolder.setStatus(posts.getStatus());
                postsViewHolder.setProfileimage(posts.getProfileimage());


            }
            @NonNull
            @Override
            public FindFriendsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.all_users_display_layout,parent,false);
                return new FindFriendsViewHolder(view);
            }
        };
        SearchResultList.setAdapter(adapter);
        adapter.startListening();

    }

    public static class FindFriendsViewHolder extends RecyclerView.ViewHolder
    {
        View mView;

        public FindFriendsViewHolder(@NonNull View itemView) {
            super(itemView);

            mView = itemView;
        }

        public void setProfileimage(String profileimage)
        {
            CircleImageView myImage = mView.findViewById(R.id.all_users_profile_image);
            Picasso.get().load(profileimage).placeholder(R.drawable.profile).into(myImage);
        }

        public void setFullname(String fullname)
        {
            TextView myName = mView.findViewById(R.id.all_users_profile_full_name);
            myName.setText(fullname);
        }

        public void setStatus(String status)
        {
            TextView myStatus = mView.findViewById(R.id.all_users_status);
            myStatus.setText(status);
        }
    }
}