package lv.roberts.kursa_darbs;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class ItemAdapter extends ArrayAdapter<LibItem> {
    ItemAdapter(Context context, int resource, List<LibItem> items) {
        super(context, resource, items);
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LibItem libItem = this.getItem(position);

        if(convertView == null) {
            convertView = LayoutInflater.from(
                    getContext()).inflate(R.layout.item_cell, parent, false);
        }
        TextView nameTV = convertView.findViewById(R.id.itemName);
        TextView authorTV = convertView.findViewById(R.id.itemAuthor);
        TextView releaseTV = convertView.findViewById(R.id.itemReleaseYear);
        ImageView imageIV = convertView.findViewById(R.id.itemImage);
        TextView typeTV = convertView.findViewById(R.id.itemType);

        nameTV.setText(libItem.title);
        authorTV.setText(libItem.author);
        releaseTV.setText(libItem.publYear);
        typeTV.setText(libItem.type.str);
        if(libItem.image != null) {
            imageIV.setImageBitmap(libItem.image);
        } else {
            imageIV.setImageResource(R.drawable.unknown);
        }
        return convertView;
    }
}
