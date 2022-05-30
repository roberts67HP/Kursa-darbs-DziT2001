package lv.roberts.kursa_darbs;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;

public class LibItem {
    public static HashMap<String, Bitmap> images = new HashMap();

    public enum Type {
        Literature ("Literature"),
        Audio ("Audio"),
        None ("None");

        public String str;

        Type(String str) { this.str = str; }
        public static Type getType (String str) {
            if(Type.Audio.str.equals(str)) return Type.Audio;
            return Type.Literature;
        }
    }

    public int id;
    public Type type;
    public String author;
    public String title;
    public String imageName;
    public Bitmap image;
    public String publYear;
    public int amountAvailable;
    public String description;

    LibItem(int id, Type type, String title, String imageName, String author, String publYear, int amountAvailable,
            String description) {
        this.id = id;
        this.type = type;
        this.title = title;
        this.imageName = imageName;
        this.loadImage();
        this.author = author;
        this.publYear = publYear;
        this.amountAvailable = amountAvailable;
        this.description = description;
    }
    protected void loadImage () {
        if(!images.containsKey(this.imageName)) {
            StorageReference storageReference = FirebaseStorage.getInstance().getReference().child(this.imageName);
            try {
                String[] parts = this.imageName.split("\\.");
                final File file = File.createTempFile(parts[0], parts[1]);
                storageReference.getFile(file)
                        .addOnSuccessListener(taskSnapshot -> {
                            this.image = BitmapFactory.decodeFile(file.getAbsolutePath());
                            images.put(this.imageName, this.image);
                        });
                return;
            } catch (IOException e) {
                e.printStackTrace();
            }
            this.image = null;
        } else this.image = images.get(this.imageName);
    }
}
