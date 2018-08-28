package com.jl.mindmesh.widget;

import android.content.*;
import android.os.*;
import android.support.v4.app.DialogFragment;
import android.app.Dialog;
import android.app.AlertDialog;
import android.app.Activity;

import com.jl.mindmesh.*;

public class ConfirmFragment extends DialogFragment {

    public static ConfirmFragment newInstance(int title) {
        ConfirmFragment frag = new ConfirmFragment();
        Bundle args = new Bundle();
        args.putInt("title", title);
        frag.setArguments(args);
        return frag;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        int title = getArguments().getInt("title");

        return new AlertDialog.Builder(getActivity())
			.setIcon(android.R.drawable.ic_dialog_alert)
			.setTitle(title)
			.setMessage("\nThis data cannot be recovered.\n")
			.setPositiveButton("Ok",
			new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog, int whichButton) {
					Activity a = getActivity();
					((Options) a).doPositiveClick();
				}
			}
		)
			.setNegativeButton("Cancel",
			new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog, int whichButton) {
					Activity a = getActivity();
					((Options) a).doNegativeClick();
				}
			}
		)
			.create();
    }
}
