package com.example.jennylee.proj399;


//retrieved from ParseXML from lab 5.

import java.util.ArrayList;
import java.io.IOException;
import android.app.Activity;
import android.util.Log;
import android.content.res.XmlResourceParser;
import org.xmlpull.v1.XmlPullParserException;

public class ParseXML {


    private static final String res = null;



    //raise try,
    public ArrayList<Element> parse(Activity mainActivity) throws XmlPullParserException, IOException {

        try {
            //Log.d("PARSEXML", "parseXML.java: parse trying...");
            XmlResourceParser res_parser = mainActivity.getResources().getXml(R.xml.data_xml);
            res_parser.next();
            return readItems(res_parser);
        } finally {}
    }

    //Items in the florence_xml
    private ArrayList<Element> readItems(XmlResourceParser res_parser) throws XmlPullParserException, IOException {
        //Log.d("PARSING", "parsing readItem entering...");

        ArrayList<Element> items = new ArrayList<>();

        int parserEventType = res_parser.getEventType();

        while (parserEventType != XmlResourceParser.END_DOCUMENT) {

            if (XmlResourceParser.START_TAG == res_parser.getEventType()) {
                String parserName = res_parser.getName();
                //Log.d("PARSING", "parserName: "+parserName);
                if (parserName.equals("item")) {
                    items.add(readItem(res_parser));
                    //Log.d("PARSING", "item tag found: called readItem()");
                }
            }

            parserEventType = res_parser.next();

        }
        //Log.d("PARSING", "item: " + items);
        return items;
    }

    //reading item in entries of Item - date, day, time, CM of tide and the type(high/low)
    private Element readItem(XmlResourceParser res_parser) throws XmlPullParserException, IOException {

        res_parser.require(XmlResourceParser.START_TAG, res, "item");

        //set all the variables as null
        String address = null;
        String price = null;

        //Log.d("BeforeREADITEM", date + " " + day + " " + time + "\n" +tideInCM + " " + type);
        while (XmlResourceParser.END_TAG != res_parser.next()) {
            if (res_parser.getEventType() != XmlResourceParser.START_TAG) {
                continue;
            }
            String item = res_parser.getName();

            switch (item) {
                case "address":
                    address = readAddress(res_parser);
                    break;
                case "price":
                    price = readPrice(res_parser);
                    break;

                default:
                    skip(res_parser);
            }
        }
        //Log.d("afterREADITEM", date + " " + day + " " + time + "\n" +tideInCM + " " + type);

        //calling Tide class to set constructor
        return new Element(address, price);

    }

    private String readAddress(XmlResourceParser res_parser) throws IOException, XmlPullParserException {
        res_parser.require(XmlResourceParser.START_TAG, res, "address");
        String address = readText(res_parser);
        res_parser.require(XmlResourceParser.END_TAG, res, "address");
        return address;
    }

    private String readPrice(XmlResourceParser res_parser) throws IOException, XmlPullParserException {
        res_parser.require(XmlResourceParser.START_TAG, res, "price");
        String price = readText(res_parser);
        res_parser.require(XmlResourceParser.END_TAG, res, "price");
        return price;
    }



    private String readText(XmlResourceParser res_parser) throws IOException, XmlPullParserException {
        String res = "";

        if ( XmlResourceParser.TEXT == res_parser.next()) {
            res = res_parser.getText();
            res_parser.nextTag();
        } //Log.d("readText", res);
        return res;
    }

    private void skip(XmlResourceParser res_parser) throws XmlPullParserException, IOException {
        if (res_parser.getEventType() != XmlResourceParser.START_TAG) {
            throw new IllegalStateException();
        }
        int depth = 1;
        while (depth != 0) {
            switch (res_parser.next()) {
                case XmlResourceParser.START_TAG:
                    depth++;
                    break;

                case XmlResourceParser.END_TAG:
                    depth--;
                    break;

            }
        }
    }
}


