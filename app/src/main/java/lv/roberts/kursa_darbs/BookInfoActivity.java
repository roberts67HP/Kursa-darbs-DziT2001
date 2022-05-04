package lv.roberts.kursa_darbs;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class BookInfoActivity extends NavigationMenu {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_info);
        loadMenu(R.id.activity_book_info);
        load();
    }
    private void load () {
        Intent previousIntent = getIntent();
        int id = Integer.valueOf(previousIntent.getStringExtra("id"));
        boolean userBookList = Boolean.valueOf(previousIntent.getStringExtra("user_booklist"));
        Button btnAction = findViewById(R.id.buttonAction);
        Button btnBack = findViewById(R.id.buttonBack);

        btnAction.setEnabled(true);
        btnBack.setOnClickListener(view -> finish());

        if(userBookList) {
            Book selectedBook = LoginActivity.currentUser.bookCont.getWithID(id);
            if(selectedBook != null) {
                btnAction.setText("Sūtīt atpakaļ");
                btnAction.setOnClickListener(view -> {
                    Book book = MainActivity.bookCont.getWithID(id);
                    book.amountAvailable++;
                    MainActivity.bookCont.updateBookOnDB(id);
                    LoginActivity.currentUser.bookCont.removeBookUsingIDAndUpdateDB(id, LoginActivity.currentUser);
                    finish();
                    Intent intent = new Intent (getApplicationContext(), ResBooksActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                });
                setValues(selectedBook);
            } else {
                btnAction.setEnabled(false);
            }
        } else {
            Book selectedBook = MainActivity.bookCont.getWithID(id);
            if(selectedBook != null) {
                btnAction.setText("Rezervēt");
                if(LoginActivity.currentUser.bookCont.containsWithID(id)) {
                    btnAction.setEnabled(false);
                } else {
                    btnAction.setOnClickListener(view -> {
                        selectedBook.amountAvailable--;
                        LoginActivity.currentUser.bookCont.insert(selectedBook);
                        MainActivity.bookCont.updateBookOnDB(id);
                        btnAction.setEnabled(false);
                    });
                    if(selectedBook.amountAvailable == 0) {
                        btnAction.setEnabled(false);
                    }
                }
                setValues(selectedBook);
            }
        }
    }
    private void setValues (Book selectedBook) {
        TextView bookNameTV = findViewById(R.id.bookName);
        ImageView bookImageIV = findViewById(R.id.bookImage);
        TextView bookAuthorTV = findViewById(R.id.bookAuthor);
        TextView bookReleaseTV = findViewById(R.id.bookReleaseYear);
        TextView bookAmountTV = findViewById(R.id.bookAmount);
        TextView bookDescrTV = findViewById(R.id.bookDescription);

        bookNameTV.setText(selectedBook.title);
        if(selectedBook.image != null) {
            bookImageIV.setImageBitmap(selectedBook.image);
        } else {
            bookImageIV.setImageResource(R.drawable.unknown);
        }
        bookAuthorTV.setText(selectedBook.author);
        bookReleaseTV.setText(selectedBook.publYear);
        bookAmountTV.setText(selectedBook.amountAvailable+" copies available");
        bookDescrTV.setText(selectedBook.description);
    }
    @Override
    protected void onDestroy () {
        super.onDestroy();
    }
}