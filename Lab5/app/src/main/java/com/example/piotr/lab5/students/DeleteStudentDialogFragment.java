package com.example.piotr.lab5.students;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;

import com.example.piotr.lab5.R;


public class DeleteStudentDialogFragment extends DialogFragment {

    public interface DeleteStudentDialogListener {
        void onClickConfirm(DialogFragment dialog);
        void onClickCancel(DialogFragment dialog);
    }

    DeleteStudentDialogListener listener;

    public static DeleteStudentDialogFragment forStudentId(long id) {
        Bundle arguments = new Bundle();
        arguments.putLong("studentId", id);

        DeleteStudentDialogFragment dialog = new DeleteStudentDialogFragment();
        dialog.setArguments(arguments);

        return dialog;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        builder.setTitle(R.string.DeleteStudentDialogTitle)
                .setMessage(getString(R.string.DeleteStudentDialogMessage))
                .setPositiveButton(
                        R.string.DeleteDialogConfirm,
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                listener.onClickConfirm(DeleteStudentDialogFragment.this);
                            }
                        }
                )
                .setNegativeButton(
                        R.string.DeleteDialogCancel,
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                listener.onClickCancel(DeleteStudentDialogFragment.this);
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
            listener = (DeleteStudentDialogListener) context;
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
            listener = (DeleteStudentDialogListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString() +
                    " must implement DeleteStudentDialogListener");
        }
    }
}
