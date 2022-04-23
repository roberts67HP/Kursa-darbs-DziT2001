package lv.roberts.kursa_darbs;

import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class User {
    public String username = "";
    public String password = "";
    public BookContainer bookCont;
    public boolean adminMode;

    public User (String username, String password, boolean adminMode) {
        this.username = username;
        this.password = password;
        this.adminMode = adminMode;
        this.bookCont = new BookContainer();
    }
    public void loadReservedBooks () {
        DatabaseReference databaseRef = FirebaseDatabase.getInstance().getReference();
        databaseRef.child("Users/"+username+"/Books").orderByValue().addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot item_snapshot:dataSnapshot.getChildren()) {
                    int id = Integer.valueOf(item_snapshot.getKey());
                    Book currentBook = MainActivity.bookCont.getWithID(id);
                    if(currentBook != null) {
                        if(!bookCont.containsWithID(id)) {
                            bookCont.insert(currentBook);
                        }
                    }
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {}
        });
    }
//    public void updateReservedBookInfoInDB () {
//        DatabaseReference databaseRef = FirebaseDatabase.getInstance().getReference().child("Users/"+username+"/Books");
//        for (Book book : bookCont.getList()) {
//            String idStr = String.valueOf(book.id);
//            DatabaseReference bookRef = databaseRef.child(idStr);
//            if(bookRef == null) {
//                databaseRef.child(idStr).setValue(idStr);
//            }
//        }
//    }
}
