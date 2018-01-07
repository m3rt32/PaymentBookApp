package cafeio.startech.com.cafeiodemo;

import java.util.Calendar;

/**
 * Created by Mert on 6.01.2018.
 */

public class Entry {
    private String title,date,cost,id;
    public Entry(String title,String date,String cost,String id){
        this.title = title;
        //Tarihi ms den insani hale getir
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(Long.parseLong(date));
        this.date = (calendar.get(Calendar.DAY_OF_MONTH)<10 ? "0" :"") + calendar.get(Calendar.DAY_OF_MONTH) + "/"
        + calendar.get(Calendar.MONTH)+1 + "/" + calendar.get(Calendar.YEAR);
        this.cost = cost;
        this.id = id;
    }
    public String getTitle(){
        return title;
    }
    public String getDate(){
        return date;
    }
    public String getCost(){
        return cost;
    }
    public String getId() {return id;}
}
