package cafeio.startech.com.cafeiodemo;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class MainActivity extends AppCompatActivity{
    DatabaseHelper databaseHelper;
    EditText place;
    EditText price;
    Button insertButton,newPageButton;
    ListView listView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        databaseHelper = new DatabaseHelper(this);
        insertButton = (Button)findViewById(R.id.button2);
        newPageButton = (Button)findViewById(R.id.button3);
        place = (EditText)findViewById(R.id.editText3);
        price = (EditText)findViewById(R.id.editText4);
        listView = (ListView)findViewById(R.id.liste);
        PopulateButtonDelegates();
        ShowEnteries();

    }
    public void PopulateButtonDelegates(){
        insertButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              boolean isInserted = databaseHelper.insertData(place.getText().toString(),price.getText().toString());
                Toast.makeText(MainActivity.this,isInserted ? "Kayıt Edildi" : "Kayıt Edilemedi", Toast.LENGTH_SHORT)
                        .show();
                place.setText("");
                price.setText("");
                ShowEnteries();
            }
        });
        newPageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(MainActivity.this, SecondPage.class);
                MainActivity.this.startActivityForResult(myIntent,1);
            }
        });
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        // check if the request code is same as what is passed  here it is 2
        if(requestCode==1)
        {
            ShowEnteries();
        }
    }
    public void ShowEnteries(){
        long year = Calendar.getInstance().get(Calendar.YEAR);
        Cursor cursor = databaseHelper.returnFromDate((int)year-1900,Calendar.getInstance().get(Calendar.MONTH));
        listView.setAdapter(null);
        if(cursor.getCount()==0)
            return;
        List<String> veriler = new ArrayList<String>();
        final List<Integer> idler = new ArrayList<Integer>();
        Calendar calendar = Calendar.getInstance();

        while(cursor.moveToNext()) {
            calendar.setTimeInMillis(Long.parseLong(cursor.getString(3)));
            veriler.add(cursor.getString(1) + "<->" + cursor.getString(2) + "|||" +
                    (calendar.get(Calendar.DAY_OF_MONTH)<10 ? "0" : "") + calendar.get(Calendar.DAY_OF_MONTH)
            + "/" + calendar.get(Calendar.MONTH)+1 + "/" + calendar.get(Calendar.YEAR));
            idler.add(Integer.decode(cursor.getString(0)));
        }
        final String[] veriArray = new String[veriler.size()];
        veriler.toArray(veriArray);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,
                android.R.id.text1,veriArray);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                AlertDialog.Builder alert = new AlertDialog.Builder(MainActivity.this);
                alert.setCancelable(true);
                alert.setTitle("Silme Onayı");
                final long elementId = id;
                alert.setPositiveButton("EVET", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Integer deletedRowCount =  databaseHelper.removeData(idler.get((int)elementId).toString());
                        Toast.makeText(MainActivity.this, deletedRowCount>0 ? "Kayıt Silindi" : "Kayıt Silinemedi", Toast.LENGTH_SHORT).show();
                        ShowEnteries();
                    }
                });
                alert.setNegativeButton("HAYIR", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                alert.setMessage(veriArray[(int)id] + " kaydı silinsin mi ?").show();
            }
        });
    }
}
