package com.example.piotr.lab5.groups;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;

import com.example.piotr.lab5.R;
import com.example.piotr.lab5.model.ProjectGroup;

import java.util.ArrayList;


public class AddGroupDialogFragment extends DialogFragment {

    private AddGroupDialogListener listener;

    public interface AddGroupDialogListener {
        void onClickGroup(DialogFragment fragment, int which);
    }

    public static AddGroupDialogFragment withGroups(ArrayList<ProjectGroup> groups) {
        AddGroupDialogFragment dialog = new AddGroupDialogFragment();
        dialog.setArguments(buildArgumentsFromGroups(groups));

        return dialog;
    }

    private static Bundle buildArgumentsFromGroups(ArrayList<ProjectGroup> groups) {
        ArrayList<String> names = new ArrayList<>();
        long[] ids = new long[groups.size()];
        int index = 0;

        for (ProjectGroup group : groups) {
            names.add(group.getName());
            ids[index++] = group.getId();
        }

        Bundle arguments = new Bundle();
        arguments.putStringArray("groups", names.toArray(new String[index]));
        arguments.putLongArray("ids", ids);

        return arguments;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        builder.setTitle(R.string.ChooseGroupDialogTitle)
                .setItems(
                        getArguments().getStringArray("groups"),
                        new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                listener.onClickGroup(AddGroupDialogFragment.this, which);
                            }
                        });

        return builder.create();
    }

    /**
     * For future compatibility
     * @param context
     */
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        try {
            listener = (AddGroupDialogListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() +
                    " must implement AddGroupDialogListener");
        }
    }

    /**
     * @param activity
     * @deprecated
     */
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        try {
            listener = (AddGroupDialogListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString() +
                    " must implement AddGroupDialogListener");
        }
    }
}
