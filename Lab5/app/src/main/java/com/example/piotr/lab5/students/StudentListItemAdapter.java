package com.example.piotr.lab5.students;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.piotr.lab5.EditStudentActivity;
import com.example.piotr.lab5.Lab5App;
import com.example.piotr.lab5.R;
import com.example.piotr.lab5.model.ProjectGroup;
import com.example.piotr.lab5.model.Student;

import java.util.ArrayList;

public class StudentListItemAdapter extends ArrayAdapter<Student> {

    private ArrayList<Student> students;
    private Context context;

    private class ViewHolder {
        TextView name;
        ImageButton button;
    }

    public StudentListItemAdapter(Context context, int resource, ArrayList<Student> students) {
        super(context, resource, students);
        this.context = context;
        this.students = students;
    }

    @NonNull
    @Override
    public View getView(final int position, @Nullable final View convertView, @NonNull ViewGroup parent) {
        ViewHolder holder;
        View v = convertView;
        final Student student = students.get(position);

        if (convertView == null) {
            // Inflate and setup listeners
            v = LayoutInflater.from(context).inflate(R.layout.student_list_item, parent, false);
            holder = new ViewHolder();
            holder.name = (TextView) v.findViewById(R.id.studentString);
            holder.name.setLongClickable(true);
            holder.name.setClickable(true);
            holder.button = (ImageButton) v.findViewById(R.id.editButton);
            holder.button.setClickable(true);

            holder.button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(context, EditStudentActivity.class);
                        intent.putExtra("studentId", student.getId());
                        context.startActivity(intent);
                    }
                });
            holder.name.setOnLongClickListener(new View.OnLongClickListener() {
                    @Override
                    public boolean onLongClick(View v) {
                        DeleteStudentDialogFragment dialog = DeleteStudentDialogFragment
                                .forStudentId(student.getId());
                        dialog.show(((Activity) context).getFragmentManager(), "delete-student");

                        return true;
                    }
                });
            holder.name.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    student.getProjectGroups().addAll(fetchGroupsForStudent(student));

                    ProjectGroupsDialogFragment dialog = ProjectGroupsDialogFragment
                            .forStudent(student);
                    dialog.show(((Activity) context).getFragmentManager(), "student-groups");
                }
            });

            v.setTag(holder);
        } else {
            holder = (ViewHolder) v.getTag();
        }

        if (null != student) {
            if (null != holder.name) {
                holder.name.setText(student.toString());
            }
        }

        return v;
    }

    private ArrayList<ProjectGroup> fetchGroupsForStudent(Student student) {
        return ((Lab5App) context.getApplicationContext())
                .getDataManager()
                .getProjectGroupsFor(student);
    }
}
