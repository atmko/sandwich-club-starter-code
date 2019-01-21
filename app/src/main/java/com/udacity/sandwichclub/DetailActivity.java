package com.udacity.sandwichclub;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.udacity.sandwichclub.model.Sandwich;
import com.udacity.sandwichclub.utils.JsonUtils;

public class DetailActivity extends AppCompatActivity {

    public static final String EXTRA_POSITION = "extra_position";
    private static final int DEFAULT_POSITION = -1;

    TextView mAlso_known_tv;
    TextView mIngredients_tv;
    TextView mOrigin_tv;
    TextView mDescription_tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        ImageView ingredientsIv = findViewById(R.id.image_iv);
        mAlso_known_tv = findViewById(R.id.also_known_tv);
        mIngredients_tv = findViewById(R.id.ingredients_tv);
        mOrigin_tv = findViewById(R.id.origin_tv);
        mDescription_tv = findViewById(R.id.description_tv);

        Intent intent = getIntent();
        if (intent == null) {
            closeOnError();
        }

        int position = intent.getIntExtra(EXTRA_POSITION, DEFAULT_POSITION);
        if (position == DEFAULT_POSITION) {
            // EXTRA_POSITION not found in intent
            closeOnError();
            return;
        }

        String[] sandwiches = getResources().getStringArray(R.array.sandwich_details);
        String json = sandwiches[position];
        Log.i("JSON", json);
        Sandwich sandwich = JsonUtils.parseSandwichJson(json);
        if (sandwich == null) {
            // Sandwich data unavailable
            closeOnError();
            return;
        }

        populateUI(sandwich);
        Picasso.with(this)
                .load(sandwich.getImage())
                .into(ingredientsIv);

        setTitle(sandwich.getMainName());
    }

    private void closeOnError() {
        finish();
        Toast.makeText(this, R.string.detail_error_message, Toast.LENGTH_SHORT).show();
    }

    private void populateAlsoKnownAs(Sandwich sandwich) {
        int akaLength = sandwich.getAlsoKnownAs().size();

        //iterate through alsoKnownAs list
        for (String currentAka : sandwich.getAlsoKnownAs()) {
            mAlso_known_tv.append(currentAka);
            //if alsoKnownAs list not empty and the currentAka is not the last then add ", "
            //checking akaLength!=0 prevents "-1" value when checking if currentAka is last
            if (akaLength!=0 && !(sandwich.getAlsoKnownAs().get(akaLength -1).equals(currentAka))) {
                mAlso_known_tv.append(", ");

            }
        }
    }
    private void populateIngredients(Sandwich sandwich) {
        int ingredientLength = sandwich.getIngredients().size();

        //iterate through ingredients list
        for (String currentIngredient : sandwich.getIngredients()) {
            mIngredients_tv.append("-" + currentIngredient);
            //if the the currentIngredient is not the last then add "\n"
            if (!(sandwich.getIngredients()).get(ingredientLength -1).equals(currentIngredient)) {
                mIngredients_tv.append("\n");
            }

        }

    }

    private void populateUI(Sandwich sandwich) {
        //additional logic needed for populating also known as and ingredient views
        populateAlsoKnownAs(sandwich);
        populateIngredients(sandwich);
        //no additional complex logic needed for populating remaining views
        mOrigin_tv.setText(sandwich.getPlaceOfOrigin());
        mDescription_tv.setText(sandwich.getDescription());

    }
}
