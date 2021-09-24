package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.akexorcist.roundcornerprogressbar.RoundCornerProgressBar;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.firebase.ui.database.SnapshotParser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

public class ShowAttendanceActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private FirebaseRecyclerAdapter adapter;
    private String lectureCode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_attendance);

        lectureCode = getIntent().getStringExtra("LECTURE");

        Button startAttendanceButton = findViewById(R.id.startAttendance);
        startAttendanceButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startAttendance();
            }
        });

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

    public void startAttendance(){
        DatabaseReference database = FirebaseDatabase.getInstance().
                getReference(getResources().getString(R.string.firebase_active));
        database.removeValue();
        database.child("activeLecture").setValue(lectureCode);
    }

    private void fetch() {

        Query query = FirebaseDatabase.getInstance().
                getReference(getResources().getString(R.string.firebase_lectures)).child(lectureCode);

        FirebaseRecyclerOptions<PersonalAttendance> stundents =
                new FirebaseRecyclerOptions.Builder<PersonalAttendance>()
                        .setQuery(query, new SnapshotParser<PersonalAttendance>() {
                            @NonNull
                            @Override
                            public PersonalAttendance parseSnapshot(@NonNull DataSnapshot snapshot) {

                                return new PersonalAttendance(snapshot.child("studentID").getValue().toString(),
                                        Integer.parseInt(snapshot.child("attendance").getValue().toString()));
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

        };
        recyclerView.setAdapter(adapter);
    }

    /*DatabaseReference databaseLectures;
    DatabaseReference database;
    Button startAttendaceButton;
    TableLayout layout;
    String lectureCode;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_attendance);


        Intent intent = getIntent();


        lectureCode = intent.getStringExtra("LECTURE");

        databaseLectures = FirebaseDatabase.getInstance().
                getReference(getResources().getString(R.string.firebase_lectures));
        database = FirebaseDatabase.getInstance().
                getReference(getResources().getString(R.string.firebase_active));
        startAttendaceButton = (Button) findViewById(R.id.startAttendance);

        startAttendaceButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startAttendance();

            }
        });
        String buttonText = "Start Attendance for " + lectureCode;
        startAttendaceButton.setText(buttonText);
        getAttendance(lectureCode);


    }
    public void getAttendance(final String lectureCode){
        layout = findViewById(R.id.ShowAttendanceLayoutId);
        final ArrayList<PersonalAttendance> allAttendaces = new ArrayList<>();
        databaseLectures.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                if(dataSnapshot.child(lectureCode).exists()){
                    int i  = 0;
                    DataSnapshot d = dataSnapshot.child(lectureCode);
                    for(DataSnapshot a : d.getChildren()) {

                        PersonalAttendance attendance = new PersonalAttendance();
                        attendance = a.getValue(PersonalAttendance.class);
                        allAttendaces.add(attendance);
                        i++;
                    }
                }


            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }


        });
        final Handler handler = new Handler();
        final int delay = 1000; //milliseconds

        handler.postDelayed(new Runnable(){
            public void run(){
                if(!allAttendaces.isEmpty())//checking if the data is loaded or not
                {
                    showAttendance(allAttendaces);
                }
                else
                    handler.postDelayed(this, delay);
            }
        }, delay);
    }

    public void showAttendance(ArrayList<PersonalAttendance> allAttendances){


        int i = 0;
        for (PersonalAttendance item : allAttendances) {
            //final ProgressBar progress = new ProgressBar(this, null, android.R.attr.progressBarStyleHorizontal);
            RoundCornerProgressBar progress = new RoundCornerProgressBar(this, null);

            TableRow row= new TableRow(this);
            TableRow.LayoutParams lp = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT);
            progress.setProgress((item.attendance - 1) * 100 / 14);
            TextView numberText = new TextView(ShowAttendanceActivity.this);
            numberText.setText(item.studentID);
            row.addView(numberText);
            row.addView(progress);
            row.setLayoutParams(lp);
            layout.addView(row,i);
            Log.d("HAHAHAHA", item.studentID);
            ++i;
        }

    }

    public void startAttendance(){

        database.removeValue();
        database.child("activeLecture").setValue(lectureCode);
    }*/


}
