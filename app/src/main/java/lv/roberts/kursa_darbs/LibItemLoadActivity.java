package lv.roberts.kursa_darbs;

import android.content.Intent;
import android.widget.ListView;
import android.widget.SearchView;

import java.util.ArrayList;

public class LibItemLoadActivity extends NavigationMenu {
    private ListView listView;

    protected void setUpList (ArrayList<LibItem> itemList) {
        listView = findViewById(R.id.listview);
        ItemAdapter adapter = new ItemAdapter(getApplicationContext(), 0, itemList);
        listView.setAdapter(adapter);
    }
    protected void setUpOnClickListener (LibItem.Type libItemContainerType, boolean loadUserResList) {
        listView.setOnItemClickListener((parent, view, position, id) -> {
            LibItem selItem = (LibItem) listView.getItemAtPosition(position);
            Intent libResInfoIntent = new Intent (getApplicationContext(), LibItemInfoActivity.class);
            libResInfoIntent.putExtra("id", selItem.id);
            libResInfoIntent.putExtra("itemlist", libItemContainerType);
            libResInfoIntent.putExtra("user_itemlist", loadUserResList);
            startActivity(libResInfoIntent);
        });
    }
    protected void initSearchWidgets (ArrayList<LibItem> itemList) {
        SearchView sView = findViewById(R.id.bookListSearchView);
        sView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                ArrayList<LibItem> filteredItems = new ArrayList<>();

                newText = newText.toLowerCase();
                for(LibItem item : itemList) {
                    boolean nameApplies = item.title.toLowerCase().contains(newText);
                    boolean authorApplies = item.author.toLowerCase().contains(newText);
                    boolean publYearApplies = item.publYear.toLowerCase().contains(newText);
                    if(nameApplies || authorApplies || publYearApplies)
                        filteredItems.add(item);
                }
                ItemAdapter adapter = new ItemAdapter(getApplicationContext(), 0, filteredItems);
                listView.setAdapter(adapter);
                return false;
            }
        });
    }
    @Override
    public void onBackPressed() {}
}
