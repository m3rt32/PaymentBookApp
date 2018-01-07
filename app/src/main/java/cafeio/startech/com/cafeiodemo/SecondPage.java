package cafeio.startech.com.cafeiodemo;

import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.android.internal.util.Predicate;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

/**
 * Created by Mert on 5.01.2018.
 */

public class SecondPage extends AppCompatActivity{
    protected  RecyclerView recyclerView;
    protected  RecyclerView.LayoutManager layoutManager;
    private DatabaseHelper helper;
    private EntryAdapter entryAdapter;
    private List<Entry> entries = new ArrayList<>();

    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.yonetimpaneli);
        helper = new DatabaseHelper(this);
        recyclerView = (RecyclerView)findViewById(R.id.sayfa2_list);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        entryAdapter = new EntryAdapter(entries);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(SecondPage.this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(entryAdapter);
        fetchEntryDataFromDatabase();
    }

    public void fetchEntryDataFromDatabase() {
        Cursor cursor = helper.returnData();

        if(cursor.getCount()==0) { // if there is no record dont do anything else
            entries = new ArrayList<>();
            entryAdapter = new EntryAdapter(entries); //Bunu yapmazsan listelemeden sonra adapter referansı bir şekilde kayboluyor,data change çağrılmıyor.
            recyclerView.setAdapter(entryAdapter);
            entryAdapter.notifyDataSetChanged();
            return;
        }
        Entry entry;
        entries.clear();
        while(cursor.moveToNext()){
            entry = new Entry(cursor.getString(1), cursor.getString(3), cursor.getString(2),cursor.getString(0));
            entries.add(entry);
        }
        entryAdapter.notifyDataSetChanged();
    }
    public void ItemRemove(String id){
        Integer aydi = helper.removeData(id);
        if(aydi>0)
        {
            fetchEntryDataFromDatabase();
            setResult(1);
        }
    }
}
