
package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;


public class SignUpActivity extends AppCompatActivity {

    TextView username;
    TextView password;
    TextView userId;
    Button signUpButton;
    Button lecturesButton;
    DatabaseReference databaseUsers;
    DatabaseReference databaseLectures;
    int defaultPosition = 0;
    String userType = "1";
    String[] listItems;
    boolean[] checkedItems;
    ArrayList<Integer> userItems = new ArrayList<Integer>();

        public interface SingleChoiceListener{
            void onPositiveButtonClicked(String[] list, int position);
            void onNegativeButtonClicked();
        }
        SingleChoiceListener mListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        databaseUsers = FirebaseDatabase.getInstance().
                getReference(getResources().getString(R.string.firebase_users));
        databaseLectures = FirebaseDatabase.getInstance().
                getReference(getResources().getString(R.string.firebase_lectures));
        username = (TextView) findViewById(R.id.username);
        password = (TextView) findViewById(R.id.password);
        userId = (TextView) findViewById(R.id.userId);
        signUpButton = (Button) findViewById(R.id.signUp);
        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addUser();
            }
        });

        listItems = getResources().getStringArray(R.array.lecture_selection);
        checkedItems = new boolean[listItems.length];
        lecturesButton = (Button) findViewById(R.id.Lectures);

        lecturesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addLectures();
            }
        });

    }

    private void addLectures(){
        AlertDialog.Builder builder = new AlertDialog.Builder(SignUpActivity.this);
        builder.setTitle("Choose Lectures");
        builder.setMultiChoiceItems(listItems, checkedItems, new DialogInterface.OnMultiChoiceClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which, boolean isChecked) {

                if(isChecked){
                    if (!userItems.contains(which)){
                        userItems.add(which);
                    }
                    else{
                        userItems.remove(which);
                    }

                }
                else {
                    userItems.remove(which);
                }

            }
        });
        builder.setCancelable(false);
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String item = "";
                for (int i = 0; i < userItems.size(); ++i){
                    item = item + listItems[userItems.get(i)];
                    if (i != userItems.size() - 1){
                        item = item + ", ";
                    }
                }

                if(listItems.length == 0){
                    lecturesButton.setText(getResources().getString(R.string.select_lectures));
                }
                else {
                    lecturesButton.setText(item);
                }
            }

        });
        builder.setNegativeButton("Dismiss", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.setNeutralButton("Clear", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                for (int i = 0; i < checkedItems.length; ++i){
                    checkedItems[i] = false;
                    userItems.clear();
                    lecturesButton.setText(getResources().getString(R.string.select_lectures));


                }
            }
        });

        AlertDialog mDialog = builder.create();
        mDialog.show();
    }
    private void addUser(){

        String addUserName = username.getText().toString().trim();
        String addPassword = password.getText().toString().trim();
        String addUserId =  userId.getText().toString().trim();

        User user = new User(addUserId, addUserName, addPassword, "1");

        //User user = new User(addUserId, addUserName, addPassword, "0");
        databaseUsers.child(addUserId).setValue(user);

        if (!TextUtils.isEmpty(addUserName)){
            Integer value = 1;
            ArrayList<LecturesOfStudents> allLectures = new ArrayList<>();
            for (int i = 0; i < userItems.size(); ++i){

                LecturesOfStudents lecture = new LecturesOfStudents();
                lecture.setLectureId(listItems[userItems.get(i)]);
                lecture.setAttendanceCount(1);
                allLectures.add(lecture);
                PersonalAttendance attendance = new PersonalAttendance(addUserId, 1);
                databaseLectures.child(listItems[userItems.get(i)]).child(addUserId).setValue(attendance);


                databaseUsers.child(addUserId).child(listItems[userItems.get(i)]).setValue(1);
            }



            Toast.makeText(this,"User Added", Toast.LENGTH_LONG).show();
        }
        else {
            Toast.makeText(this, "You must enter username!", Toast.LENGTH_LONG).show();
        }

    }

}
