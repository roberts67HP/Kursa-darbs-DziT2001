package lv.roberts.kursa_darbs;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;

import java.util.HashMap;

public class AudioActivity extends LibItemLoadActivity {
    public static LibItemContainer libItemContainer = new LibItemContainer();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_audio);
        setTitle("Audio");

        AudioActivity.libItemContainer.loadLibItemsFromDB(LibItem.Type.Audio);
        LiteratureActivity.libItemContainer.loadLibItemsFromDB(LibItem.Type.Literature);
        if(LoginActivity.currentUser != null) {
            loadMenu(R.id.activity_audio);
            LoginActivity.currentUser.loadReservedItems();
            setUpList(AudioActivity.libItemContainer.getList());
            setUpOnClickListener(LibItem.Type.Audio, false);
            initSearchWidgets(AudioActivity.libItemContainer.getList());
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