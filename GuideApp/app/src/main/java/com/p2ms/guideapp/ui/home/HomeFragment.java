package com.p2ms.guideapp.ui.home;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ThrowOnExtraProperties;
import com.google.firebase.database.ValueEventListener;
import com.p2ms.guideapp.R;
import com.p2ms.guideapp.adapter.RecyclerBookAdapter;
import com.p2ms.guideapp.keys.StaticData;
import com.p2ms.guideapp.model.Book;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;
    private RecyclerView rv;
    GridLayoutManager gridManager;
    FirebaseDatabase fireDB;
    DatabaseReference dbRef;
    private List<Book> bookList = new ArrayList<>();
    private RecyclerBookAdapter adapter;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                ViewModelProviders.of(this).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_home, container, false);

        rv = root.findViewById(R.id.recycler_view);
        gridManager = new GridLayoutManager(getContext(),2);
        rv.setLayoutManager(gridManager);

        fireDB = FirebaseDatabase.getInstance();
        dbRef=fireDB.getReference("Books");
        fetchBooks();
        return root;
    }
    private void fetchBooks(){
        final ProgressDialog dialog = new ProgressDialog(getActivity());
        dialog.setTitle("Fetching details");
        dialog.setMessage("Loading...");
        dialog.setCancelable(false);
        dialog.show();
        dbRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot:
                     snapshot.getChildren()) {
                    Map<String,String> map = (Map<String, String>) dataSnapshot.getValue();
                    Book book = new Book();
                    book.setBookAuthorName(map.get(StaticData.BOOK_AUTHOR));
                    book.setBookDesc(map.get(StaticData.BOOK_DESC));
                    book.setBookId(map.get(StaticData.BOOK_ID));
                    book.setBookImageUri(map.get(StaticData.BOOK_IMAGE));
                    book.setBookPrice(map.get(StaticData.BOOK_PRICE));
                    book.setBookTitle(map.get(StaticData.BOOK_NAME));
                    bookList.add(book);
                }
                adapter=new RecyclerBookAdapter(getActivity(),bookList);
                rv.setAdapter(adapter);
                dialog.dismiss();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getActivity(),error.toString(),Toast.LENGTH_LONG).show();
                dialog.dismiss();
            }
        });
    }
}