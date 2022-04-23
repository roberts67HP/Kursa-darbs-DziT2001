package lv.roberts.kursa_darbs;

import android.content.Intent;
import android.widget.ListView;
import android.widget.SearchView;

import java.util.ArrayList;

public class BookLoadActivity extends NavigationMenu {
    private ListView listView;

    protected void setUpList (ArrayList<Book> bookList) {
        listView = findViewById(R.id.listview);
        BookAdapter adapter = new BookAdapter(getApplicationContext(), 0, bookList);
        listView.setAdapter(adapter);
    }
    protected void setUpOnClickListener (boolean userBookList) {
        listView.setOnItemClickListener((parent, view, position, id) -> {
            Book selBook = (Book) listView.getItemAtPosition(position);
            Intent bookInfoDetail = new Intent (getApplicationContext(), BookInfoActivity.class);
            bookInfoDetail.putExtra("id", String.valueOf(selBook.id));
            bookInfoDetail.putExtra("user_booklist", String.valueOf(userBookList));
            startActivity(bookInfoDetail);
        });
    }
    protected void initSearchWidgets (ArrayList<Book> bookList) {
        SearchView sView = findViewById(R.id.bookListSearchView);
        sView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                ArrayList<Book> filteredBooks = new ArrayList<>();

                newText = newText.toLowerCase();
                for(Book book : bookList) {
                    boolean nameApplies = book.title.toLowerCase().contains(newText);
                    boolean authorApplies = book.author.toLowerCase().contains(newText);
                    boolean publYearApplies = book.publYear.toLowerCase().contains(newText);
                    if(nameApplies || authorApplies || publYearApplies)
                        filteredBooks.add(book);
                }
                BookAdapter adapter = new BookAdapter(getApplicationContext(), 0, filteredBooks);
                listView.setAdapter(adapter);
                return false;
            }
        });
    }
    @Override
    public void onBackPressed() {}
}
