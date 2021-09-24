package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SignInActivity extends AppCompatActivity {

    DatabaseReference database;
    Button signInButton;
    TextView userIdEdit, passwordEdit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        database = FirebaseDatabase.getInstance().getReference(getResources().getString(R.string.firebase_users));
        userIdEdit = (TextView) findViewById(R.id.userId);
        passwordEdit = (TextView) findViewById(R.id.password);
        signInButton = (Button) findViewById(R.id.login);

        signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Login();
            }
        });
    }

    public void Login (){

        final String getPassword = passwordEdit.getText().toString();
        final String getUserId =  userIdEdit.getText().toString();
        database.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.child(getUserId).exists()){
                    if(!getUserId.isEmpty()){
                        User login = (User) dataSnapshot.child(getUserId).getValue(User.class);
                        if(login.getPassword().equals(getPassword)){
                            Toast.makeText(SignInActivity.this, "Success Login", Toast.LENGTH_LONG).show();

                            if(login.getUserType().equals("0")){
                                Intent intentMasterPage = new Intent(SignInActivity.this, MasterPageActivity.class);
                                startActivity(intentMasterPage);
                            }
                            else{
                                Intent intentStudentPage = new Intent(SignInActivity.this, StudentPageActivity.class);
                                intentStudentPage.putExtra("USER_ID", getUserId);
                                intentStudentPage.putExtra("PASSWORD", getPassword);
                                final ArrayList<LecturesOfStudents> lectures = new ArrayList<LecturesOfStudents>();
                                //intentStudentPage.putParcelableArrayListExtra("LECTURES", login.getLectures());
                                startActivity(intentStudentPage);
                            }



                        }
                        else {
                            Toast.makeText(SignInActivity.this, "Wrong Password", Toast.LENGTH_LONG).show();
                        }
                    }
                    else{
                        Toast.makeText(SignInActivity.this, "Enter UserId", Toast.LENGTH_LONG).show();
                    }
                }
                else{
                    Toast.makeText(SignInActivity.this, "UserId is not registered", Toast.LENGTH_LONG).show();
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    public void SingUp (View view){

        Intent intent = new Intent(this, SignUpActivity.class);
        startActivity(intent);
    }
}
