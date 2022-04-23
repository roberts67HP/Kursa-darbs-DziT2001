package lv.roberts.kursa_darbs;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class LoginActivity extends AppCompatActivity {
    private EditText editTextUsername, editTextPassword;
    private Button buttonLogin;

    public static User currentUser = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        editTextUsername = findViewById(R.id.editTextUsername);
        editTextPassword = findViewById(R.id.editTextPassword);
        buttonLogin = findViewById(R.id.buttonLogin);

        buttonLogin.setOnClickListener(view -> {
            String username = editTextUsername.getText().toString();
            String password = editTextPassword.getText().toString();
            checkUserValidity(username, password);
        });
    }
    private void checkUserValidity (String username, String password) {
        DatabaseReference databaseRef = FirebaseDatabase.getInstance().getReference();
        databaseRef.child("Users").orderByValue().addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(ifUserIsValid(dataSnapshot, username, password)) {
                    Intent mainActivity = new Intent (getApplicationContext(), MainActivity.class);
                    startActivity(mainActivity);
                } else {
                    Toast incorrectInfoToast = Toast.makeText(
                            getApplicationContext(),"Username or password is wrong", Toast.LENGTH_SHORT);
                    incorrectInfoToast.show();
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {}
        });
    }
    private boolean ifUserIsValid (DataSnapshot dataSnapshot, String username, String password) {
        boolean retVal = false;

        for(DataSnapshot item_snapshot:dataSnapshot.getChildren()) {
            String tempUsername = item_snapshot.getKey();
            String tempPassword = item_snapshot.child("Password").getValue().toString();

            if(username.equals(tempUsername) && password.equals(tempPassword)) {
                LoginActivity.currentUser = new User (username, password, tempUsername.equals("admin"));
                retVal = true;
                break;
            }
        }
        return retVal;
    }
    @Override
    public void onBackPressed() {}
}