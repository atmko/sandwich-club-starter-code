package com.udacity.sandwichclub.utils;

import com.udacity.sandwichclub.model.Sandwich;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class JsonUtils {

    public static Sandwich parseSandwichJson(String json) {

        try {
            JSONObject jsonObject = new JSONObject(json);
            JSONObject name = jsonObject.getJSONObject("name");
            String mainName = name.getString("mainName");
            //--------
            JSONArray alsoKnownAsRaw = name.getJSONArray("alsoKnownAs");
            //convert JSONArray to list
            List<String> alsoKnownAs = convertJSONArrayToList(alsoKnownAsRaw);
            //--------
            String placeOfOrigin = jsonObject.getString("placeOfOrigin");
            String description = jsonObject.getString("description");
            String image = jsonObject.getString("image");
            //--------
            JSONArray ingredientsRaw = jsonObject.getJSONArray("ingredients");
            //convert JSONArray to list
            List<String> ingredients = convertJSONArrayToList(ingredientsRaw);
            //--------
            //return sandwich
            return new Sandwich(mainName, alsoKnownAs, placeOfOrigin, description, image, ingredients);

        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }

    }

    private static List<String> convertJSONArrayToList(JSONArray jsonArray) {
        List<String> list = new ArrayList<>();
        int length = jsonArray.length();

        //iterate through JSONArray
        for (int position = 0 ; position < length ; position++) {
            try {
                //add items individually to list
                list.add((String) jsonArray.get(position));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        return list;
    }
}
