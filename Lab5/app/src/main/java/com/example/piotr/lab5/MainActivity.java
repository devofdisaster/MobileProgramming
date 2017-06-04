package com.example.piotr.lab5;

import android.app.DialogFragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import com.example.piotr.lab5.database.SQLiteDataManager;
import com.example.piotr.lab5.model.Student;
import com.example.piotr.lab5.students.DeleteStudentDialogFragment;
import com.example.piotr.lab5.students.StudentListItemAdapter;

public class MainActivity extends AppCompatActivity
        implements DeleteStudentDialogFragment.DeleteStudentDialogListener {

    SQLiteDataManager dbm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        dbm = ((Lab5App) getApplicationContext()).getDataManager();

        setupView();
    }

    private void setupView() {
        setContentView(R.layout.activity_main);

        ListView studentListView = (ListView) findViewById(R.id.listView);
        studentListView.setAdapter(new StudentListItemAdapter(
                this,
                R.layout.student_list_item,
                dbm.getStudents()
        ));

        ImageButton addButton = (ImageButton) findViewById(R.id.addButton);
        addButton.setClickable(true);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, EditStudentActivity.class));
            }
        });
    }

    @Override
    public void onClickConfirm(DialogFragment fragment) {
        ListView studentList = (ListView) findViewById(R.id.listView);

        Student student = dbm.getStudent(fragment.getArguments().getLong("studentId"));

        if (student != null) {
            dbm.deleteStudent(student);
            ((StudentListItemAdapter) studentList.getAdapter()).remove(student);
            Toast.makeText(this, R.string.ToastStudentDeleted, Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onClickCancel(DialogFragment fragment) {

    }
}
