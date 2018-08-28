package com.jl.mindmesh.widget;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;

public class CompletionFragment extends DialogFragment {
    int mNum;

    /**
     * Create a new instance of MyDialogFragment, providing "num"
     * as an argument.
     */
    public static CompletionFragment newInstance(int num) {
        CompletionFragment f = new CompletionFragment();

        // Supply num input as an argument.
        Bundle args = new Bundle();
        args.putInt("num", num);
        f.setArguments(args);

        return f;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mNum = getArguments().getInt("num");

        // Pick a style based on the num.
        int style = DialogFragment.STYLE_NORMAL, theme = 0;
        setStyle(style, theme);
		setCancelable(false) ;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
		/*Dialog d = new Dialog(getActivity());
		d.setTitle("Completed");
		return d;*/

        return new AlertDialog.Builder(getActivity())
			.setIcon(android.R.drawable.ic_dialog_alert)
			.setTitle("Completed")
			
			.setMessage("\nWell Done!\n")
			.setPositiveButton("Next",
			new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog, int whichButton) {
					//Activity a = getActivity();
					//((Options) a).doPositiveClick();
				}
			}
		)
			.setNeutralButton("Retry",
			new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog, int whichButton) {
					//Activity a = getActivity();
					//((Options) a).doPositiveClick();
				}
			}
		)
			.setNegativeButton("Exit",
			new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog, int whichButton) {
					//Activity a = getActivity();
					//((Options) a).doNegativeClick();
				}
			}
		)
			.create();
	}

    /*@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
							 Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.completed, container, false);
        View tv = v.findViewById(R.id.complete_text);
        ((TextView)tv).setText("Well Done");

        // Watch for button clicks.
        Button button = (Button)v.findViewById(R.id.show);
        button.setOnClickListener(new OnClickListener() {
				public void onClick(View v) {
					// When button is clicked, call up to owning activity.
					//((FragmentDialog)getActivity()).showDialog();
					//getActivity().showDialog();
				}
			});

        return v;
    }*/
}
