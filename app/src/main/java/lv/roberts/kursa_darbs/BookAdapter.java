package lv.roberts.kursa_darbs;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class BookAdapter extends ArrayAdapter<Book> {
    BookAdapter (Context context, int resource, List<Book> books) {
        super(context, resource, books);
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Book book = this.getItem(position);

        if(convertView == null) {
            convertView = LayoutInflater.from(
                    getContext()).inflate(R.layout.book_cell, parent, false);
        }
        TextView bookNameTV = convertView.findViewById(R.id.bookName);
        TextView bookAuthorTV = convertView.findViewById(R.id.bookAuthor);
        TextView bookReleaseTV = convertView.findViewById(R.id.bookReleaseYear);
        ImageView bookImageIV = convertView.findViewById(R.id.bookImage);

        bookNameTV.setText(book.title);
        bookAuthorTV.setText(book.author);
        bookReleaseTV.setText(book.publYear);
//        bookImageIV.setImageResource(Integer.valueOf(book.image));
        return convertView;
    }
}
