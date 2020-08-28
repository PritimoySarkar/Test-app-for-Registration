package com.p2ms.guideapp.ui.profile;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.p2ms.guideapp.R;

public class ModalBottomScreen extends BottomSheetDialogFragment {
    BottomListener mListener;
    private TextView txtCamera,txtGallery;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //as its a fragment we need to specify the layout to use
        View bottomSheet = inflater.inflate(R.layout.modal_layout,container,false);

        //mapping with layout .xml
        txtCamera=bottomSheet.findViewById(R.id.txtOpenCamera);
        txtGallery=bottomSheet.findViewById(R.id.txtOpenGallery);

        //action against the view
        txtCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mListener.onButtonClick(R.string.choose_camera);
                dismiss();
            }
        });
        txtGallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mListener.onButtonClick(R.string.choose_gallery);
                dismiss();
            }
        });

        return bottomSheet;
    }

    public interface BottomListener{
        void onButtonClick(int text);
        }

        //code to attaching from fragment
    @Override
    public void onAttachFragment(@NonNull Fragment childFragment) {
        super.onAttachFragment(childFragment);
        mListener =(BottomListener) childFragment;
    }
    //Below code is for attaching from activity
//    @Override
//    public void onAttach(@NonNull Context context) {
//        super.onAttach(context);
//        mListener = (BottomListener) context;
//    }
}


