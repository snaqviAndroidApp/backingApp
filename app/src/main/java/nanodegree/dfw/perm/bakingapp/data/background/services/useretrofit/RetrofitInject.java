package nanodegree.dfw.perm.bakingapp.data.background.services.useretrofit;


import java.util.ArrayList;
import java.util.Objects;

import nanodegree.dfw.perm.bakingapp.data.handler.baking.RecipesHandler;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

/** Retrofit turns your HTTP API into a Java interface. **/

/** Target api
 *
 * URL protected static final String BAKING_URL_ = "https://d17h27t6h515a5.cloudfront.net/topher/2017/May/59121517_baking/baking.json";
 * Base_URL = "https://d17h27t6h515a5.cloudfront.net/"
 *
 */

public final class RetrofitInject {

    public interface RecipeRetrofit {
        @GET("{path_in}/{path_in1}/{path_in2}/{path_in3}/baking.json")
        Call<ArrayList<RecipesHandler>> getRecipesViaRetrofit(
                @Path("path_in") String  path_in,                       // topher
                @Path("path_in1") String  path_in1,                     // 2017
                @Path("path_in2") String  path_in2,                     // May
                @Path("path_in3") String  path_in3                      // 59121517_baking
        );
    }

}
