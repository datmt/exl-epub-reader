package com.datmt.android.read.adapater;


import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.datmt.android.read.helper.FolioInstance;
import com.datmt.android.read.model.Book;

import androidx.recyclerview.widget.RecyclerView;

import com.datmt.android.read.R;

import java.util.List;

public class BookItemAdapter extends RecyclerView.Adapter<BookItemAdapter.ViewHolder> {

    private List<Book> bookList;

    private static final String LOG_TAG = BookItemAdapter.class.getName();
    /**
     * Provide a reference to the type of views that you are using
     * (custom ViewHolder).
     */
    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView bookTitle;
        private final ImageView bookThumbnail;
        private final TextView bookAuthor;
        private final TextView bookLocationOnDisk;
        private final LinearLayout singleBookItem;

        public ViewHolder(View view) {
            super(view);
            // Define click listener for the ViewHolder's View

            bookTitle = (TextView) view.findViewById(R.id.bookTitle);
            bookAuthor= (TextView) view.findViewById(R.id.bookAuthor);
            bookThumbnail= view.findViewById(R.id.bookThumbnail);
            singleBookItem = view.findViewById(R.id.singleBookItem);
            bookLocationOnDisk = view.findViewById(R.id.bookLocationOnDisk);
        }

        public TextView getBookTitle() {
            return bookTitle;
        }

        public ImageView getBookThumbnail() {
            return bookThumbnail;
        }

        public TextView getBookAuthor() {
            return bookAuthor;
        }

        public LinearLayout getSingleBookItem() {
            return singleBookItem;
        }

        public TextView getBookLocationOnDisk() {
            return bookLocationOnDisk;
        }
    }

    /**
     * Initialize the dataset of the Adapter.
     *
     * @param dataSet String[] containing the data to populate views to be used
     * by RecyclerView.
     */
    public BookItemAdapter(List<Book> dataSet) {
        bookList = dataSet;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        // Create a new view, which defines the UI of the list item
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.book_row_item, viewGroup, false);

        return new ViewHolder(view);
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int position) {

        // Get element from your dataset at this position and replace the
        // contents of the view with that element
        viewHolder.getBookTitle().setText(bookList.get(position).getBookTitle());
        viewHolder.getBookAuthor().setText(bookList.get(position).getBookAuthor());
        viewHolder.getBookLocationOnDisk().setText(bookList.get(position).getLocationOnDisk());
        viewHolder.singleBookItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FolioInstance instance = new FolioInstance(v.getContext());
                String bookPath = ((TextView)v.findViewById(R.id.bookLocationOnDisk)).getText().toString();
                Log.i(LOG_TAG, bookPath);
                instance.getReader().openBook(bookPath) ;

            }
        });
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return bookList.size();
    }
}
