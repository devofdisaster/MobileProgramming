package com.example.piotr.lab5.groups;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;

import com.example.piotr.lab5.R;


public class DeleteGroupDialogFragment extends DialogFragment {

    public interface DeleteGroupDialogListener {
        void onClickConfirm(DialogFragment dialog);
        void onClickCancel(DialogFragment dialog);
    }

    DeleteGroupDialogListener listener;

    public static DeleteGroupDialogFragment forGroupId(long id) {
        Bundle arguments = new Bundle();
        arguments.putLong("groupId", id);

        DeleteGroupDialogFragment dialog = new DeleteGroupDialogFragment();
        dialog.setArguments(arguments);

        return dialog;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        builder.setTitle(R.string.UnassignGroupDialogTitle)
                .setMessage(getString(R.string.UnassignGroupDialogMessage))
                .setPositiveButton(
                        R.string.DeleteDialogConfirm,
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                listener.onClickConfirm(DeleteGroupDialogFragment.this);
                            }
                        }
                )
                .setNegativeButton(
                        R.string.DeleteDialogCancel,
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                listener.onClickCancel(DeleteGroupDialogFragment.this);
                            }
                        }
                );

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
            listener = (DeleteGroupDialogListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() +
                    " must implement DeleteStudentDialogListener");
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
            listener = (DeleteGroupDialogListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString() +
                    " must implement DeleteStudentDialogListener");
        }
    }
}
