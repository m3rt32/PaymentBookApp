package cafeio.startech.com.cafeiodemo;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Mert on 5.01.2018.
 */

public class EntryAdapter extends RecyclerView.Adapter<EntryAdapter.TakeViewHolder> {
    public List<Entry> entries;

    @Override
    public TakeViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View elementView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.entrylistrow,parent,false);
        return new TakeViewHolder(elementView);
    }

    @Override
    public void onBindViewHolder(TakeViewHolder holder, int position) {
        //Bu kısım her bir element için for döngüsü gibi sanırım
        final Entry entry =  entries.get(position);
        holder.entryTitle.setText(entry.getTitle());
        holder.entryDate.setText(entry.getDate());
        holder.entryCost.setText(entry.getCost());
        final TakeViewHolder hl = holder;
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(hl.itemView.getContext());
                builder.setCancelable(true);
                builder.setTitle("Silme Onayı");
                builder.setPositiveButton("SİL", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        ((SecondPage)hl.itemView.getContext()).ItemRemove(entry.getId());
                    }
                });
                builder.setNegativeButton("İPTAL", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                builder.setMessage("silinecek,okey misin?").show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return entries.size();
    }

    public class TakeViewHolder extends  RecyclerView.ViewHolder{
        public TextView entryTitle;
        public TextView entryDate;
        public TextView entryCost;
        public TakeViewHolder(View view){
            super(view);
            entryTitle = (TextView)view.findViewById(R.id.entrytitle);
            entryDate = (TextView)view.findViewById(R.id.entrydate);
            entryCost = (TextView)view.findViewById(R.id.entrycost);
        }
    }
    public EntryAdapter(List<Entry> entries){
        this.entries = entries;
    }
}
