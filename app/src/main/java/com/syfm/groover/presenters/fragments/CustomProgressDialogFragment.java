package com.syfm.groover.presenters.fragments;

import android.app.Dialog;
import android.app.DialogFragment;
import android.app.ProgressDialog;
import android.os.Bundle;

/**
 * Created by lycoris on 2015/10/10.
 */
public class CustomProgressDialogFragment extends DialogFragment {
    private ProgressDialog progressDialog;

    public static CustomProgressDialogFragment newInstance(int title, int message, int max) {
        CustomProgressDialogFragment fragment = new CustomProgressDialogFragment();
        Bundle args = new Bundle();
        args.putInt("title", title);
        args.putInt("message", message);
        args.putInt("max", max);
        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public Dialog onCreateDialog(Bundle safedInstanceState) {
        int title = getArguments().getInt("title");
        int message = getArguments().getInt("message");
        int max = getArguments().getInt("max");

        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setTitle(title);
        progressDialog.setMessage(getResources().getText(message));
        progressDialog.setMax(max);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);

        return progressDialog;
    }

    /**
     * プログレス値の取得
     */
    public int getProgress() {
        return progressDialog.getProgress();
    }

    /**
     * プログレス値のセット
     */
    public void setProgress(int value) {
        progressDialog.setProgress(value);
    }

    public void setProgressMax(int max) {
        progressDialog.setMax(max);
    }
}
