package lv.roberts.kursa_darbs;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

public class ResBooksActivity extends BookLoadActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_res_books);

        if(LoginActivity.currentUser != null) {
            loadMenu(R.id.activity_res_books);
            LoginActivity.currentUser.loadReservedBooks();
            setUpList(LoginActivity.currentUser.bookCont.getList());
            setUpOnClickListener(true);
            initSearchWidgets(LoginActivity.currentUser.bookCont.getList());
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