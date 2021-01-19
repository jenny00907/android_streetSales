package com.example.jennylee.proj399;

import android.os.Bundle;
import android.app.Activity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.ImageView;
import android.preference.PreferenceManager;
import android.content.SharedPreferences;

import java.util.HashMap;
import java.util.ArrayList;

public class ListingActivity extends Activity implements AdapterView.OnItemClickListener{
    private ArrayList<Element> listElement;
    private int total = 0;
    private int price = 0;

    private TextView titleText;
    private ListView itemList;
    private ImageView locationImage;
    private SharedPreferences saved;


    @Override
    protected void onCreate(Bundle state){
        super.onCreate(state);
        setContentView(R.layout.activity_listing);

        titleText = findViewById(R.id.titleText);
        itemList = findViewById(R.id.itemList);
        locationImage = (ImageView) findViewById(R.id.locationImage);

        listElement = new ArrayList<>();

        itemList.setOnItemClickListener(this);
        itemList.setFastScrollEnabled(true);
        //locationImage.setImageDrawable(getDrawable(R.drawable.blue));

        saved = PreferenceManager.getDefaultSharedPreferences(this);

        readXMLFile();
        //Log.d("ONCEATE", "Oncreate: "+ saved.getString("location", ""));
        //displayImage(saved.getString("location","Patterson Street"));
    }


    public void readXMLFile(){
        //Log.d("READXMLFILE", "reading xml file");
        try{
            listElement = new ParseXML().parse(this);
        } catch (Exception e){
            //Log.d("READXMLFILE", "Error: "+ e.getMessage());
        }
        update();
    }
    public void update(){
        SharedPreferences.Editor editor = saved.edit();

        if (listElement ==null) {
            titleText.setText("Error: price is null");
            return;
        }
        ArrayList<HashMap<String, String>> array = new ArrayList<>();

        for (Element items:listElement){
            HashMap<String,String> hash = new HashMap<>();
            hash.put("location", items.getAddress());
            hash.put("price", Integer.toString(items.getPrice()));
            array.add(hash);
        }

        int layoutVar = R.layout.activity_listing_elements;
        String[] StringArray = {"location", "price"};
        int[] intArray = {R.id.locationText, R.id.priceText};



        SimpleAdapter ret = new SimpleAdapter(this, array, layoutVar, StringArray, intArray);
        itemList.setAdapter(ret);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id){

        Element priceInt = listElement.get(position);
        Toast.makeText(this, "For the element: value of $ " + String.valueOf(priceInt.getPrice()), Toast.LENGTH_LONG).show();

        price = (priceInt.getPrice());
        SharedPreferences.Editor editor = saved.edit();

        total += price;
        //Log.d("ONITEMCLICK", "total clicked" + total);
        editor.putString("cart", String.valueOf(total));
        editor.commit();
    }

}
