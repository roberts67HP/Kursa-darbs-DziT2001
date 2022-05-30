package lv.roberts.kursa_darbs;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

public class LiteratureActivity extends LibItemLoadActivity {
    public static LibItemContainer libItemContainer = new LibItemContainer();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_literature);
        setTitle("Literature");

        AudioActivity.libItemContainer.loadLibItemsFromDB(LibItem.Type.Audio);
        LiteratureActivity.libItemContainer.loadLibItemsFromDB(LibItem.Type.Literature);
        if(LoginActivity.currentUser != null) {
            loadMenu(R.id.activity_literature);
            LoginActivity.currentUser.loadReservedItems();
            setUpList(LiteratureActivity.libItemContainer.getList());
            setUpOnClickListener(LibItem.Type.Literature, false);
            initSearchWidgets(LiteratureActivity.libItemContainer.getList());
        } else {
            finish();
            Intent loginActivity = new Intent (getApplicationContext(), LoginActivity.class);
            startActivity(loginActivity);
        }
    }
    @Override
    protected void onDestroy () {
        super.onDestroy();
    }
}