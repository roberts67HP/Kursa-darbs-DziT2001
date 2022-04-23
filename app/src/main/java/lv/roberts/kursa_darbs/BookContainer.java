package lv.roberts.kursa_darbs;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class BookContainer {
    private ArrayList<Book> bookList = new ArrayList<>();

    public ArrayList<Book> getList () {
        return bookList;
    }
    public Book getWithID (int id) {
        for (Book currBook : bookList)
            if (currBook.id == id)
                return currBook;
        return null;
    }
    public void insert(Book book) {
        bookList.add(book);
    }
    public boolean containsWithID(int id) {
        for (Book currBook : bookList)
            if (currBook.id == id)
                return true;
        return false;
    }
    public void removeBookUsingIDAndUpdateDB(int id, User user) {
        for (Book currBook : bookList) {
            if (currBook.id == id) {
                String idStr = String.valueOf(id);
                FirebaseDatabase.getInstance().getReference()
                        .child("Users/"+user.username+"/Books/"+idStr).removeValue();
                bookList.remove(currBook);
            }
        }

//        for (Book book : bookCont.getList()) {
//            String idStr = String.valueOf(book.id);
//            DatabaseReference bookRef = databaseRef.child(idStr);
//            if(bookRef == null) {
//                databaseRef.child(idStr).setValue(idStr);
//            }
//        }
    }
    public void updateFromDB () {
        DatabaseReference databaseRef = FirebaseDatabase.getInstance().getReference();
        databaseRef.child("Books").orderByValue().addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot item_snapshot:dataSnapshot.getChildren()) {
                    int id = Integer.valueOf(item_snapshot.getKey());
                    String title = item_snapshot.child("Title").getValue().toString();
                    String image = item_snapshot.child("Image").getValue().toString();
                    String author = item_snapshot.child("Author").getValue().toString();
                    String publYear = item_snapshot.child("Year").getValue().toString();
                    int amount = Integer.valueOf(item_snapshot.child("Amount available").getValue().toString());
                    String description = item_snapshot.child("Description").getValue().toString();

                    Book currentBook = getWithID(id);
                    if(currentBook != null) {
                        currentBook.title = title;
                        currentBook.image = image;
                        currentBook.author = author;
                        currentBook.publYear = publYear;
                        currentBook.amountAvailable = amount;
                        currentBook.description = description;
                    } else {
                        insert(new Book(id, title, image, author, publYear, amount, description));
                    }
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {}
        });
    }
    public void updateToDB () {
        for (Book book : bookList) {
            DatabaseReference databaseRef = FirebaseDatabase.getInstance().getReference().child("Books/"+book.id);
            databaseRef.child("Title").setValue(book.title);
            databaseRef.child("Image").setValue(book.image);
            databaseRef.child("Author").setValue(book.author);
            databaseRef.child("Year").setValue(book.publYear);
            databaseRef.child("Amount available").setValue(book.amountAvailable);
            databaseRef.child("Description").setValue(book.description);
        }
    }
    public void updateBookOnDB (int id) {
        DatabaseReference databaseRef = FirebaseDatabase.getInstance().getReference().child("Books/"+id);
        Book currBook = getWithID(id);

        databaseRef.child("Title").setValue(currBook.title);
        databaseRef.child("Image").setValue(currBook.image);
        databaseRef.child("Author").setValue(currBook.author);
        databaseRef.child("Year").setValue(currBook.publYear);
        databaseRef.child("Amount available").setValue(currBook.amountAvailable);
        databaseRef.child("Description").setValue(currBook.description);
    }
}