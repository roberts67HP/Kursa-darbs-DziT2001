package lv.roberts.kursa_darbs;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class LibItemInfoActivity extends NavigationMenu {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_info);
        loadMenu(R.id.activity_item_info);
        load();
    }
    private void load () {
        Intent previousIntent = getIntent();
        int id = (int) previousIntent.getSerializableExtra("id");
        LibItem.Type type = (LibItem.Type) previousIntent.getSerializableExtra("itemlist");
        boolean loadUserItemList = (boolean) previousIntent.getSerializableExtra("user_itemlist");

        Button btnAction = findViewById(R.id.buttonAction);
        Button btnBack = findViewById(R.id.buttonBack);

        btnAction.setEnabled(true);
        btnBack.setOnClickListener(view -> finish());

        if(loadUserItemList) {
            LibItem selItem = LoginActivity.currentUser.libItemContainer.get(id);
            if(selItem != null) {
                btnAction.setText("Send back");
                btnAction.setOnClickListener(view -> {
                    LibItem item = LiteratureActivity.libItemContainer.get(id);
                    if(type == LibItem.Type.Audio) {
                        item = AudioActivity.libItemContainer.get(id);
                    }
                    item.amountAvailable++;
                    DatabaseReference databaseRef = FirebaseDatabase.getInstance().getReference().child("LibraryItems/"+id);
                    databaseRef.child("Amount available").setValue(item.amountAvailable);
                    LoginActivity.currentUser.libItemContainer.remove(id);
                    FirebaseDatabase.getInstance().getReference()
                            .child("Users/"+LoginActivity.currentUser.username+"/Reserved/"+id).removeValue();
                    finish();
                    Intent intent = new Intent (getApplicationContext(), ResItemsActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                });
                setValues(selItem);
            } else {
                btnAction.setEnabled(false);
            }
        } else {
            LibItem selectedItem = LiteratureActivity.libItemContainer.get(id);
            if(type == LibItem.Type.Audio) {
                selectedItem = AudioActivity.libItemContainer.get(id);
            }
            if(selectedItem != null) {
                btnAction.setText("Reserve");
                if(LoginActivity.currentUser.libItemContainer.contains(id)) {
                    btnAction.setEnabled(false);
                } else {
                    LibItem finalSelectedItem = selectedItem;
                    btnAction.setOnClickListener(view -> {
                        deliveryDialog(finalSelectedItem, btnAction, id);
                    });
                    if(selectedItem.amountAvailable == 0) {
                        btnAction.setEnabled(false);
                    }
                }
                setValues(selectedItem);
            }
        }
    }
    private void deliveryDialog (LibItem selectedItem, Button btnAction, int id) {
        Dialog dialog = new Dialog (this);
        dialog.setContentView(R.layout.dialog_delivery);

        EditText editTxtPhone = dialog.findViewById(R.id.editTextPhone);
        EditText editTxtEmail = dialog.findViewById(R.id.editTextEmail);
        EditText editTxtAddress = dialog.findViewById(R.id.editTextAddress);
        Button btnDeliver = dialog.findViewById(R.id.buttonDeliver);

        dialog.show();

        btnDeliver.setOnClickListener(view -> {
            String phone = editTxtPhone.getText().toString();
            String email = editTxtEmail.getText().toString();
            String address = editTxtAddress.getText().toString();

            if(!phone.isEmpty() && !email.isEmpty() && !address.isEmpty()) {
                TextView textViewItemAmount = findViewById(R.id.itemAmount);

                selectedItem.amountAvailable--;
                DatabaseReference databaseRef = FirebaseDatabase.getInstance().getReference().child("LibraryItems/"+id);
                databaseRef.child("Amount available").setValue(selectedItem.amountAvailable);
                textViewItemAmount.setText(selectedItem.amountAvailable+" copies available");

                LoginActivity.currentUser.libItemContainer.insert(selectedItem);
                databaseRef = FirebaseDatabase.getInstance().getReference().child(
                        "Users/"+LoginActivity.currentUser.username+"/Reserved");
                databaseRef.child(Integer.toString(id)).setValue(id);
                btnAction.setEnabled(false);
                dialog.dismiss();
            } else {
                Toast incorrectInfoToast = Toast.makeText(
                        getApplicationContext(),"Information wasn't typed in correctly", Toast.LENGTH_SHORT);
                incorrectInfoToast.show();
            }
        });
    }
    private void setValues (LibItem selectedItem) {
        ImageView itemImageIV = findViewById(R.id.itemImage);
        TextView itemTypeTV = findViewById(R.id.itemType);
        TextView itemAuthorTV = findViewById(R.id.itemAuthor);
        TextView itemReleaseTV = findViewById(R.id.itemReleaseYear);
        TextView itemAmountTV = findViewById(R.id.itemAmount);
        TextView itemDescrTV = findViewById(R.id.itemDescription);

        setTitle(selectedItem.title);
        if(selectedItem.image != null) {
            itemImageIV.setImageBitmap(selectedItem.image);
        } else {
            itemImageIV.setImageResource(R.drawable.unknown);
        }
        itemTypeTV.setText(selectedItem.type.str);
        itemAuthorTV.setText(selectedItem.author);
        itemReleaseTV.setText(selectedItem.publYear);
        itemAmountTV.setText(selectedItem.amountAvailable+" copies available");
        itemDescrTV.setText(selectedItem.description);
    }
    @Override
    protected void onDestroy () {
        super.onDestroy();
    }
}