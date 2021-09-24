package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.firebase.ui.database.SnapshotParser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

import java.util.ArrayList;
import java.util.List;

public class StudentPageActivity extends AppCompatActivity {

    private List<String> ignoredKeys = new ArrayList<>();
    private RecyclerView recyclerView;
    private FirebaseRecyclerAdapter adapter;
    private String userID;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_page);

        ignoredKeys.add("password");
        ignoredKeys.add("userId");
        ignoredKeys.add("userName");
        ignoredKeys.add("userType");

        Intent intent = getIntent();
        userID = intent.getStringExtra("USER_ID");

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);

        recyclerView = findViewById(R.id.personalAttendanceView);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setHasFixedSize(true);
        fetch();
    }

    @Override
    protected void onStart() {
        super.onStart();
        adapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        adapter.stopListening();
    }

    private void fetch() {
        Query query = FirebaseDatabase.getInstance().
                getReference(getResources().getString(R.string.firebase_users)).child(userID);


        FirebaseRecyclerOptions<PersonalAttendance> stundents =
                new FirebaseRecyclerOptions.Builder<PersonalAttendance>()
                        .setQuery(query, new SnapshotParser<PersonalAttendance>() {
                            @NonNull
                            @Override
                            public PersonalAttendance parseSnapshot(@NonNull DataSnapshot snapshot) {
                                String key = snapshot.getKey();
                                if (!ignoredKeys.contains(String.valueOf(key))) {
                                    return new PersonalAttendance(snapshot.getKey(),
                                            Integer.parseInt(snapshot.getValue().toString()));
                                }

                                return new PersonalAttendance("", 0);
                            }
                        })
                        .build();

        adapter = new FirebaseRecyclerAdapter<PersonalAttendance, PersonalAttendanceViewHolder>(stundents) {
            @Override
            public PersonalAttendanceViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.personal_attendance_list_item, parent, false);

                return new PersonalAttendanceViewHolder(view);
            }


            @Override
            protected void onBindViewHolder(PersonalAttendanceViewHolder holder, final int position, PersonalAttendance model) {
                holder.setAttendance(model);
            }

            @Override
            public int getItemCount() {
                return super.getItemCount() - ignoredKeys.size();
            }
        };
        recyclerView.setAdapter(adapter);
    }
}