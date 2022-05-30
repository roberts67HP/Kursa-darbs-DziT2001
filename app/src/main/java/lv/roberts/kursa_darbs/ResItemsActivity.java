package lv.roberts.kursa_darbs;

import android.content.Intent;
import android.os.Bundle;

public class ResItemsActivity extends LibItemLoadActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_res_items);
        setTitle("Reserved items");

        AudioActivity.libItemContainer.loadLibItemsFromDB(LibItem.Type.Audio);
        LiteratureActivity.libItemContainer.loadLibItemsFromDB(LibItem.Type.Literature);
        if(LoginActivity.currentUser != null) {
            loadMenu(R.id.activity_res_books);
            LoginActivity.currentUser.loadReservedItems();
            setUpList(LoginActivity.currentUser.libItemContainer.getList());
            setUpOnClickListener(LibItem.Type.None, true);
            initSearchWidgets(LoginActivity.currentUser.libItemContainer.getList());
        } else {
            Intent loginActivity = new Intent (getApplicationContext(), LoginActivity.class);
            startActivity(loginActivity);
        }
    }
    @Override
    protected void onDestroy () {
        super.onDestroy();
    }
}