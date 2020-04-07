package com.criddam.covid_19criddam.placeutils;

import android.os.AsyncTask;
import android.util.Log;
import android.widget.SimpleAdapter;

import com.criddam.covid_19criddam.activity.MainActivity;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;

/*private class ParserTask extends AsyncTask<String, Integer, List<HashMap<String,String>>> {

    JSONObject jObject;
    private static final String TAG = "ParserTask";

    @Override
    protected List<HashMap<String, String>> doInBackground(String... jsonData) {

        List<HashMap<String, String>> places = null;

        PlaceJSONParser placeJsonParser = new PlaceJSONParser();

        try{
            jObject = new JSONObject(jsonData[0]);

            // Getting the parsed data as a List construct
            places = placeJsonParser.parse(jObject);

        }catch(Exception e){
            Log.d("Exception",e.toString());
        }
        return places;
    }

    @Override
    protected void onPostExecute(List<HashMap<String, String>> result) {

        String[] from = new String[] { "description"};
        int[] to = new int[] { android.R.id.text1 };

        // Creating a SimpleAdapter for the AutoCompleteTextView
        SimpleAdapter adapter = new SimpleAdapter(, result, android.R.layout.simple_list_item_1, from, to);

        // Setting the adapter
        atvPlaces.setAdapter(adapter);
    }
}*/

