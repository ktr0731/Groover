package com.syfm.groover.view.fragments;

import android.app.Dialog;
import android.app.DialogFragment;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.Window;

/**
 * Created by lycoris on 2015/10/10.
 */
public class ProgressDialogFragment extends DialogFragment {

    private ProgressDialog dialog;

    public static ProgressDialogFragment newInstance(int message) {
        ProgressDialogFragment fragment = new ProgressDialogFragment();
        Bundle args = new Bundle();
        args.putInt("message", message);
        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        int message = getArguments().getInt("message");

        dialog = new ProgressDialog(getActivity());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setMessage(getResources().getString(message));
        dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);

        return dialog;
    }

    public void changeMessage(String message) {
        dialog.setMessage(message);
    }


}
