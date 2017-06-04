package com.example.piotr.lab5.students;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;

import com.example.piotr.lab5.model.ProjectGroup;
import com.example.piotr.lab5.model.Student;

import java.util.ArrayList;


public class ProjectGroupsDialogFragment extends DialogFragment {

    public static ProjectGroupsDialogFragment forStudent(Student student) {
        ProjectGroupsDialogFragment fragment = new ProjectGroupsDialogFragment();
        Bundle arguments = buildArgumentsFromGroups(student.getProjectGroups());
        arguments.putString("student", student.toString());
        fragment.setArguments(arguments);

        return fragment;
    }

    private static Bundle buildArgumentsFromGroups(ArrayList<ProjectGroup> groups) {
        String[] names = new String[groups.size()];
        int index = 0;

        for (ProjectGroup group : groups) {
            names[index++] = group.getName();
        }

        Bundle arguments = new Bundle();
        arguments.putStringArray("groups", names);

        return arguments;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        builder.setTitle(getArguments().getString("student"))
                .setItems(
                        getArguments().getStringArray("groups"),
                        new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        }
                );


        return builder.create();
    }
}
