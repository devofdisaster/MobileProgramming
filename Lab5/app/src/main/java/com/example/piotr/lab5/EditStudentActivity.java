package com.example.piotr.lab5;

import android.app.DialogFragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.piotr.lab5.database.SQLiteDataManager;
import com.example.piotr.lab5.groups.AddGroupDialogFragment;
import com.example.piotr.lab5.groups.DeleteGroupDialogFragment;
import com.example.piotr.lab5.model.ProjectGroup;
import com.example.piotr.lab5.model.Student;

import java.util.ArrayList;

import static android.R.layout.simple_list_item_1;

public class EditStudentActivity extends AppCompatActivity
        implements DeleteGroupDialogFragment.DeleteGroupDialogListener,
        AddGroupDialogFragment.AddGroupDialogListener {

    private SQLiteDataManager dbm;
    private Student student;
    private ArrayList<ProjectGroup> groups;
    private ListView groupList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        dbm = ((Lab5App) getApplicationContext()).getDataManager();
        student = dbm.getStudent(getIntent().getLongExtra("studentId", 0));

        if (null != student) {
            groups = student.getProjectGroups();
        } else {
            groups = new ArrayList<>();
        }

        setupView();
    }

    private void setupView() {
        setContentView(R.layout.activity_edit_student);

        final TextView firstnameInput = (TextView) findViewById(R.id.nameInput);
        final TextView lastnameInput = (TextView) findViewById(R.id.surnameInput);
        final ImageButton addGroupButton = (ImageButton) findViewById(R.id.addGroupButton);
        final Button saveButton = (Button) findViewById(R.id.studentSaveButton);
        groupList = (ListView) findViewById(R.id.groupListView);

        if (null != student) {
            firstnameInput.setText(student.getFirstname());
            lastnameInput.setText(student.getLastname());
        }

        groupList.setAdapter(new ArrayAdapter<>(this, simple_list_item_1, groups));
        groupList.setLongClickable(true);
        groupList.setDividerHeight(1);
        groupList.setItemsCanFocus(true);
        groupList.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(
                    AdapterView<?> parent, View view, int position, long id
            ) {
                ProjectGroup group = (ProjectGroup) groupList.getAdapter().getItem(position);

                if (null != group) {
                    DeleteGroupDialogFragment dialog = DeleteGroupDialogFragment
                            .forGroupId(group.getId());
                    dialog.show(
                            EditStudentActivity.this.getFragmentManager(),
                            "remove-project-group"
                    );
                }

                return true;
            }
        });

        addGroupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ArrayList<ProjectGroup> available = dbm.getProjectGroups();
                available.removeAll(groups);

                AddGroupDialogFragment dialog = AddGroupDialogFragment.withGroups(available);
                dialog.show(EditStudentActivity.this.getFragmentManager(), "add-project-group");
            }
        });


        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (firstnameInput.getText().toString().isEmpty() ||
                        lastnameInput.getText().toString().isEmpty()) {

                    Toast.makeText(
                            EditStudentActivity.this,
                            R.string.NamesRequiredToast,
                            Toast.LENGTH_SHORT
                    ).show();
                } else {
                    String firstname = firstnameInput.getText().toString();
                    String lastname = lastnameInput.getText().toString();

                    if (null == student) {
                        student = new Student(firstname, lastname);
                    } else {
                        student.setFirstname(firstname);
                        student.setLastname(lastname);
                    }

                    student.getProjectGroups().retainAll(groups);

                    for (ProjectGroup group : groups) {
                        if (!student.getProjectGroups().contains(group)) {
                            student.addProjectGroup(group);
                        }
                    }

                    dbm.saveStudent(student);
                    startActivity(new Intent(EditStudentActivity.this, MainActivity.class));
                    Toast.makeText(
                            EditStudentActivity.this,
                            R.string.ToastStudentSaved,
                            Toast.LENGTH_SHORT
                    ).show();
                }
            }
        });
    }

    @Override
    public void onClickConfirm(DialogFragment fragment) {
        groups.remove(dbm.getProjectGroup(fragment.getArguments().getLong("groupId")));
        ((ArrayAdapter<ProjectGroup>) groupList.getAdapter()).notifyDataSetChanged();
        Toast.makeText(this, R.string.ToastGroupUnassigned, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onClickCancel(DialogFragment dialog) {

    }

    @Override
    public void onClickGroup(DialogFragment fragment, int which) {
        long[] ids = fragment.getArguments().getLongArray("ids");

        if (ids != null) {
            if (which >= 0 && which < ids.length) {
                ProjectGroup group = dbm.getProjectGroup(ids[which]);

                groups.add(group);
                ((ArrayAdapter<ProjectGroup>) groupList.getAdapter()).notifyDataSetChanged();
                Toast.makeText(this, R.string.ToastGroupAssigned, Toast.LENGTH_SHORT).show();
            }
        }
    }
}
