package com.example.myapplication;

import android.view.View;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import com.akexorcist.roundcornerprogressbar.RoundCornerProgressBar;

public class PersonalAttendanceViewHolder extends RecyclerView.ViewHolder {

    private RoundCornerProgressBar attendanceProgress;
    private TextView txtTitle;
    private TextView txtPercentage;

    public PersonalAttendanceViewHolder(View itemView) {
        super(itemView);
        txtTitle = itemView.findViewById(R.id.list_title);
        txtPercentage = itemView.findViewById(R.id.attendancePercentage);
        attendanceProgress = itemView.findViewById(R.id.attendanceProgress);
    }

    public void setAttendance(PersonalAttendance personalAttendance)
    {
        int attendancePercentage = (int) ((personalAttendance.getAttendance() - 1) * 100.0f / 14.0f);

        txtTitle.setText(personalAttendance.getStudentID());
        txtPercentage.setText(String.format("%%%d", attendancePercentage));
        attendanceProgress.setMax(100);
        attendanceProgress.setProgress(attendancePercentage);
    }
}
