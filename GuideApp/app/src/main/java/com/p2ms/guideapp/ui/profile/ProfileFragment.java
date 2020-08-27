package com.p2ms.guideapp.ui.profile;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.firestore.DocumentSnapshot;
import com.mikhaellopez.circularimageview.CircularImageView;
import com.p2ms.guideapp.model.User;
import com.p2ms.guideapp.session.*;
import com.p2ms.guideapp.keys.*;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import com.p2ms.guideapp.keys.*;
import com.google.firebase.firestore.FirebaseFirestore;
import com.p2ms.guideapp.R;

import java.util.Map;

public class ProfileFragment extends Fragment {
    ProgressDialog progressDialog;
    CircularImageView iv;
    TextInputEditText edName,edEmail,edContact;
    FloatingActionButton fab;
    ImageButton btnSave;
    private ProfileViewModel profileViewModel;
    private FirebaseFirestore db;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        //profileViewModel =
                //ViewModelProviders.of(this).get(ProfileViewModel.class);
        View root = inflater.inflate(R.layout.fragment_profile, container, false);
        iv = root.findViewById(R.id.profileImage);
        edName = root.findViewById(R.id.edName);
        progressDialog = new ProgressDialog(getContext());
        progressDialog.setTitle("Loading...");
        progressDialog.setMessage("Please wait...!");
        progressDialog.setCancelable(false);
        progressDialog.show();
        edEmail = root.findViewById(R.id.edEmail);
        edContact = root.findViewById(R.id.edContact);
        fab = root.findViewById(R.id.floatingActionButton);
        btnSave = root.findViewById(R.id.uploadBtn);
        db = FirebaseFirestore.getInstance();
        String currentUserId = new LocalSessionStore(getContext()).getData(StaticData.USER_ID);
        showData(currentUserId);
        return root;
    }

    private void showData(String userId) {
        db.collection("Users")
        .document(userId)
        .get()
        .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if(task.isSuccessful()){
                    DocumentSnapshot snapshot =task.getResult();
                    Map<String,Object> map = snapshot.getData();
                    Log.i("ProfileFragment",String.valueOf(map));
                    User user = new User(
                      String.valueOf(map.get(StaticData.USER_NAME)),
                      String.valueOf(map.get(StaticData.USER_EMAIL)),
                      String.valueOf(map.get(StaticData.USER_CONTACT)),
                      String.valueOf(map.get(StaticData.USER_DP))
                    );
                    edName.setText(user.getUserName());
                    edEmail.setText(user.getUserEmail());
                    edContact.setText(user.getUserContact());
                    if(!user.getUserDp().isEmpty()) {
                        Glide.with(getContext()).load(user.getUserDp()).into(iv);
                    }
                }
                else{
                    Log.w("tag","Error getting documents",task.getException());
                }
                progressDialog.dismiss();
            }
        })
        .addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getContext(),
                        e.getMessage(),
                        Toast.LENGTH_LONG).show();
                progressDialog.dismiss();
            }

        });

    }
}