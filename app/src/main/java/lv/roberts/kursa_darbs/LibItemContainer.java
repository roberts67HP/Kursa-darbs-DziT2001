package lv.roberts.kursa_darbs;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class LibItemContainer {
    private ArrayList<LibItem> libItemList = new ArrayList<>();

    public ArrayList<LibItem> getList () {
        return libItemList;
    }
    public LibItem get(int id) {
        for (LibItem currItem : libItemList)
            if (currItem.id == id)
                return currItem;
        return null;
    }
    public void insert(LibItem item) {
        libItemList.add(item);
    }
    public boolean contains(int id) {
        for (LibItem currItem : libItemList)
            if (currItem.id == id)
                return true;
        return false;
    }
    public void remove(int id) {
        for (LibItem currItem : libItemList) {
            if (currItem.id == id) {
                libItemList.remove(currItem);
                break;
            }
        }
    }
    public void loadLibItemsFromDB(LibItem.Type resType) {
        DatabaseReference databaseRef = FirebaseDatabase.getInstance().getReference();
        databaseRef.child("LibraryItems").orderByValue().addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot item_snapshot:dataSnapshot.getChildren()) {
                    int id = Integer.valueOf(item_snapshot.getKey());
                    String type = item_snapshot.child("Type").getValue().toString();
                    String title = item_snapshot.child("Title").getValue().toString();
                    String image = item_snapshot.child("Image").getValue().toString();
                    String author = item_snapshot.child("Author").getValue().toString();
                    String publYear = item_snapshot.child("Year").getValue().toString();
                    int amount = Integer.valueOf(item_snapshot.child("Amount available").getValue().toString());
                    String description = item_snapshot.child("Description").getValue().toString();

                    if (type.equals(resType.str)) {
                        LibItem currLibItem = get(id);
                        if (currLibItem != null) {
                            currLibItem.title = title;
                            currLibItem.type = resType;
                            currLibItem.imageName = image;
                            currLibItem.author = author;
                            currLibItem.publYear = publYear;
                            currLibItem.amountAvailable = amount;
                            currLibItem.description = description;
                        } else {
                            insert(new LibItem(id, resType, title, image, author, publYear, amount, description));
                        }
                    }
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {}
        });
    }
}