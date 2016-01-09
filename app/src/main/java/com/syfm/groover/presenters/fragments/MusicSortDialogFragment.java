package com.syfm.groover.presenters.fragments;

import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;

import com.syfm.groover.R;

/**
 * Created by lycoris on 2016/01/09.
 */
public class MusicSortDialogFragment extends DialogFragment {

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Dialog dialog = new Dialog(getActivity(), android.R.style.Theme_Holo_Light_Dialog);
        dialog.setTitle("Sort");
        dialog.setContentView(R.layout.dialog_music_sort);

        return dialog;
    }
}
