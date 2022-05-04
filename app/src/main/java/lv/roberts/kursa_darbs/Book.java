package lv.roberts.kursa_darbs;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.File;
import java.io.IOException;

public class Book {
    public int id;
    public String title;
    public String imageName;
    public Bitmap image;
    public String author;
    public String publYear;
    public int amountAvailable;
    public String description;

    Book (int id, String title, String imageName, String author, String publYear, int amountAvailable,
          String description) {
        this.id = id;
        this.title = title;
        this.imageName = imageName;
        this.loadImage();
        this.author = author;
        this.publYear = publYear;
        this.amountAvailable = amountAvailable;
        this.description = description;
    }
    private void loadImage () {
        if(!MainActivity.images.containsKey(this.imageName)) {
            StorageReference storageReference = FirebaseStorage.getInstance().getReference().child(this.imageName);
            try {
                String[] parts = this.imageName.split("\\.");
                final File file = File.createTempFile(parts[0], parts[1]);
                storageReference.getFile(file)
                        .addOnSuccessListener(taskSnapshot -> {
                            this.image = BitmapFactory.decodeFile(file.getAbsolutePath());
                            MainActivity.images.put(this.imageName, this.image);
                        });
                return;
            } catch (IOException e) {
                e.printStackTrace();
            }
            this.image = null;
        } else this.image = MainActivity.images.get(this.imageName);
    }
}