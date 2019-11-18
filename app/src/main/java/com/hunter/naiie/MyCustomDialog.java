package com.hunter.naiie;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

public class MyCustomDialog extends DialogFragment {

    private static final String TAG = "MyCustomDialog";

    EditText b_contact_no;
    EditText b_new_pass;
    Button b_update_pass;

    public interface OnInputListner {
        void sendUpdateData(String contact, String pass);
    }

    public OnInputListner mOnInputListner;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.custom_dialog, container, false);

        b_contact_no = view.findViewById(R.id.b_f_number);
        b_new_pass = view.findViewById(R.id.b_new_pass);
        b_update_pass = view.findViewById(R.id.b_update_pass);

        b_update_pass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String bContactNo = b_contact_no.getText().toString().trim();
                String bNewPass = b_new_pass.getText().toString().trim();

                if (bContactNo.isEmpty()) {
                    b_contact_no.setError("Invalid");
                    b_contact_no.requestFocus();

                } else if (bNewPass.isEmpty()) {
                    b_new_pass.setError("Invalid");
                    b_new_pass.requestFocus();
                } else {
                    mOnInputListner.sendUpdateData(bContactNo, bNewPass);
                }

                getDialog().dismiss();
            }
        });

        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        try {

            mOnInputListner = (OnInputListner) getActivity();
        } catch (ClassCastException e) {
            Log.e(TAG, "On Attach" + e.getMessage());
        }
    }
}
