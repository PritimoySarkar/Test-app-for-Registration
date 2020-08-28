package com.p2ms.guideapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.p2ms.guideapp.R;
import com.p2ms.guideapp.model.Book;

import java.util.List;

public class RecyclerBookAdapter extends RecyclerView.Adapter<RecyclerBookAdapter.BookHolder> {

    private Context context;
    private List<Book> bookList;

    public RecyclerBookAdapter(Context context, List<Book> bookList) {
        this.context = context;
        this.bookList = bookList;
    }

    @NonNull
    @Override
    public RecyclerBookAdapter.BookHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View bookItemsView = layoutInflater.inflate(R.layout.recycler_items,parent,false);
        BookHolder holder = new BookHolder(bookItemsView);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerBookAdapter.BookHolder holder, int position) {
        //set the data into design
        Book book = bookList.get(position);
        String title =book.getBookTitle();
        //holder.bookTitle.setText();

        if(!book.getBookImageUri().isEmpty()){
            Glide.with(context)
                    .load(book.getBookImageUri())
                    .placeholder(android.R.drawable.ic_menu_rotate)
                    .into(holder.bookImage);
        }
    }

    @Override
    public int getItemCount() {
        return bookList.size();
    }

    public class BookHolder extends RecyclerView.ViewHolder {
        private ImageView bookImage;
        private TextView bookTitle;
        private LinearLayout bookLayout;
        public BookHolder(@NonNull View itemView) {
            super(itemView);
            bookImage = itemView.findViewById(R.id.item_image);
            bookLayout = itemView.findViewById(R.id.item_layout);
            bookTitle = itemView.findViewById(R.id.item_title);
        }
    }
}