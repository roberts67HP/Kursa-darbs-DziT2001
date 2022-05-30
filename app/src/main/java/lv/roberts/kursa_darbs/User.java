package lv.roberts.kursa_darbs;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class User {
    public String username = "";
    public String password = "";
    public LibItemContainer libItemContainer;
    public boolean adminMode;

    public User (String username, String password, boolean adminMode) {
        this.username = username;
        this.password = password;
        this.adminMode = adminMode;
        this.libItemContainer = new LibItemContainer();
    }
    public void loadReservedItems() {
        DatabaseReference databaseRef = FirebaseDatabase.getInstance().getReference();
        databaseRef.child("Users/"+username+"/Reserved").orderByValue()
                .addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot item_snapshot:dataSnapshot.getChildren()) {
                    int id = Integer.valueOf(item_snapshot.getKey());
                    LibItem item = AudioActivity.libItemContainer.get(id);
                    if(item == null)
                        item = LiteratureActivity.libItemContainer.get(id);
                    if(item != null) {
                        if(!libItemContainer.contains(id)) {
                            libItemContainer.insert(item);
                        }
                    }
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {}
        });
    }
}
