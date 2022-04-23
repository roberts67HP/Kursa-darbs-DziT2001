package lv.roberts.kursa_darbs;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class MainActivity extends BookLoadActivity {
    public static BookContainer bookCont = new BookContainer();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bookCont.updateFromDB();
        if(LoginActivity.currentUser != null) {
            loadMenu(R.id.activity_main);
            LoginActivity.currentUser.loadReservedBooks();
            setUpList(MainActivity.bookCont.getList());
            setUpOnClickListener(false);
            initSearchWidgets(MainActivity.bookCont.getList());
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