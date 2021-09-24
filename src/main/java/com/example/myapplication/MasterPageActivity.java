package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;

public class MasterPageActivity extends AppCompatActivity {

    LinearLayout LinearLayoutId;
    LinearLayout layout;
    Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_master_page);
        createLayoutDynamically();
    }

    private void createLayoutDynamically() {
        final String[] allLectures = getResources().getStringArray(R.array.lecture_selection);
        layout = findViewById(R.id.MasterLinearLayoutId);
        for (int i = 0; i <= allLectures.length - 1; i++) {
            final int index = i;
            Button lectureButton = new Button(this);
            lectureButton.setText(allLectures[i]);
            lectureButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intentAttendancePage = new Intent(MasterPageActivity.this, ShowAttendanceActivity.class);
                    intentAttendancePage.putExtra("LECTURE", allLectures[index]);
                    startActivity(intentAttendancePage);
                }
            });
            layout.addView(lectureButton);

        }

    }
}
