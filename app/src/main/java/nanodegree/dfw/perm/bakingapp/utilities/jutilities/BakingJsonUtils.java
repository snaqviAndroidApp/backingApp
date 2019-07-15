package nanodegree.dfw.perm.bakingapp.utilities.jutilities;

import android.content.Context;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import nanodegree.dfw.perm.bakingapp.data.handler.baking.Ingredients;
import nanodegree.dfw.perm.bakingapp.data.handler.baking.RecipesHandler;
import nanodegree.dfw.perm.bakingapp.data.handler.baking.Steps;

public class BakingJsonUtils {

    private static final String BAKING_ID = "id";
    private static final String BAKING_NAME = "name";
    private static final String BAKING_SERVINGS = "servings";
    private static final String BAKING_IMAGE = "image";
    private static final String BAKING_INGREDIENTS = "ingredients";
    private static final String BAKING_STEPS = "steps";

    private static final String BAKING_INGREDIENTS_ID_QUANTITY = "quantity";
    private static final String BAKING_INGREDIENTS_ID_MEASURE = "measure";
    private static final String BAKING_INGREDIENTS_ID_INGREDIENT_DETAILS = "ingredient";

    private static final String BAKING_STEPS_ID = "id";
    private static final String BAKING_STEPS_SHORTDESCRIPTION = "shortDescription";
    private static final String BAKING_STEPS_DESCRIPTION = "description";
    private static final String BAKING_STEPS_VIDEOURL = "videoURL";
    private static final String BAKING_STEPS_THUMBURL = "thumbnailURL";

    public static ArrayList<RecipesHandler> parseBakingJnData(Context context, String _rawJBakingData)
                throws JSONException {

        ArrayList<RecipesHandler> recipiesList = new ArrayList<>();
        Integer jObjectLength = new JSONArray(_rawJBakingData).length();
        ArrayList<JSONObject> _bakings = new ArrayList<>();
        ArrayList<Ingredients> interimIng;
        ArrayList<Steps> interimSteps;

        for (int num = 0; num < jObjectLength; num++) {
            _bakings.add((JSONObject) new JSONArray(_rawJBakingData).get(num));   // gives first of total 04 JSONArrays
        }

            for (JSONObject i : _bakings) {
                try {
                    interimIng = new ArrayList<>();
                    interimSteps = new ArrayList<>();
                    for (int ingNum = 0; ingNum < i.getJSONArray(BAKING_INGREDIENTS).length(); ingNum++) {
                        interimIng.add(new Ingredients(
                                i.getJSONArray(BAKING_INGREDIENTS).getJSONObject(ingNum).getInt(BAKING_INGREDIENTS_ID_QUANTITY),
                                i.getJSONArray(BAKING_INGREDIENTS).getJSONObject(ingNum).getString(BAKING_INGREDIENTS_ID_MEASURE),
                                i.getJSONArray(BAKING_INGREDIENTS).getJSONObject(ingNum).getString(BAKING_INGREDIENTS_ID_INGREDIENT_DETAILS)
                                ));
                    }
                    for (int ingNum = 0; ingNum < i.getJSONArray(BAKING_STEPS).length(); ingNum++) {
                        interimSteps.add(new Steps(
                                        i.getJSONArray(BAKING_STEPS).getJSONObject(ingNum).getInt(BAKING_STEPS_ID),
                                        i.getJSONArray(BAKING_STEPS).getJSONObject(ingNum).getString(BAKING_STEPS_SHORTDESCRIPTION),
                                        i.getJSONArray(BAKING_STEPS).getJSONObject(ingNum).getString(BAKING_STEPS_DESCRIPTION),
                                        i.getJSONArray(BAKING_STEPS).getJSONObject(ingNum).getString(BAKING_STEPS_VIDEOURL),
                                        i.getJSONArray(BAKING_STEPS).getJSONObject(ingNum).getString(BAKING_STEPS_THUMBURL)
                                )
                        );
                    }

                    recipiesList.add(
                            new RecipesHandler(
                                    i.getInt(BAKING_ID),
                                    i.getString(BAKING_NAME),
                                    interimIng,
                                    interimSteps,
                                    i.getInt(BAKING_SERVINGS),
                                    i.getString(BAKING_IMAGE)
                            )
                    );

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            return recipiesList;
         }
    }
