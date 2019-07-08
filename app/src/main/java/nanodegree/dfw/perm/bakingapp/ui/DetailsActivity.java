
package nanodegree.dfw.perm.bakingapp.ui;

import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.webkit.WebView;
import android.widget.TextView;
import android.widget.Toast;

import static android.widget.LinearLayout.VERTICAL;
import static nanodegree.dfw.perm.bakingapp.data.Strings.INGREDIENTS_List;
import static nanodegree.dfw.perm.bakingapp.data.Strings.NAME;
import static nanodegree.dfw.perm.bakingapp.data.Strings.STEPS_List;

import java.text.MessageFormat;
import java.util.ArrayList;

import nanodegree.dfw.perm.bakingapp.R;
import nanodegree.dfw.perm.bakingapp.data.handler.DetailsDataHandler;
import nanodegree.dfw.perm.bakingapp.data.handler.baking.Ingredients;
import nanodegree.dfw.perm.bakingapp.data.handler.baking.Steps;


public class DetailsActivity extends AppCompatActivity
        implements TrailersAdapter.TrailersOnClickHandler {

    // Extra for the mdovie Id to be received in the Intent                           MovieApp Stage Two
    public static final String EXTRA_MOVIE_ID = "extrafMovieId";
    // Extra for the movie Id to be received after rotation
    public static final String INSTANCE_MOVIE_ID = "instancefMovieId";

    LinearLayoutManager reviewsLayoutManager;
    LinearLayoutManager gPostersLayoutManager;


    private RecyclerView mTrailerRecyclerView, mReviewsRecyclerView;
    private TrailersAdapter trailersAdapter;
    private IngredientsAdapter ingredientsAdapter;

    ArrayList<String> reviews = null;
    ArrayList<String> trailers = null;

    private ArrayList<Steps> _dStepsList;
    private ArrayList<Ingredients> _dIngredientsList;
    private String _dName;

    String checkIfValidTitle = null, checkIfVaildOverview = null;                   // Trailers-nullability parameters

    WebView wtrailersView;


    TextView vTitle;

    private Intent mIntent;
    private DetailsDataHandler inDetetails = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        _dStepsList = new ArrayList<>();
        _dIngredientsList = new ArrayList<>();
        _dName = null;

        initViews();
        mIntent = getIntent();


//        inDetetails = mIntent.getParcelableExtra("movieDetails");
//        reviews = new ArrayList<>();                                             // Movie Stage2, review and trailer data from MainActivity
//        trailers = new ArrayList<>();
//        checkIfValidTitle = inDetetails.getDetailAct_original_title();
//        checkIfVaildOverview = inDetetails.getDetailAct_overview();
//
//        if (checkIfValidTitle == null && checkIfVaildOverview == null) {        // A sneak -peak into if Trailer-n-Overview would be available
//            trailers = null;
//            reviews = null;                                                     // assuming the all-time non-null validity of the two fields, (not ideal app)
//        } else {
//            reviews = inDetetails.getDetailAct_reviewsInput();
//            trailers = inDetetails.getDetailAct_trailer();
//        }
//
////        vTitle.setText(checkIfValidTitle);
//        rcvd_backdrop_path = inDetetails.getDetailAct_backdrop_path();

//        vOverview.setText(checkIfVaildOverview);
//        vOverview.setText(inDetetails.getDetailAct_overview());

//        vReleaseDate.setText(String.format("%s%s", getString(R.string.releaseData_Prepending), inDetetails.getDetailAct_release_date()));

//        Picasso.get()
//                .load(rcvd_backdrop_path)
//                .fit()
//                .rotate(0)
//                .centerInside()
//                .placeholder(R.color.colorAccentAmended)
//                .error(R.drawable.ic_launcher_foreground)
//                .into(thumbNail);


        ArrayList<String> listIngredients = new ArrayList<>();
        _dIngredientsList = (ArrayList<Ingredients>) mIntent.getExtras().get(INGREDIENTS_List);
        _dName = mIntent.getExtras().get(NAME).toString();
        vTitle.setText(_dName);

        _dIngredientsList.forEach(dIN -> {
        listIngredients.add(
                "Quantity: " +  dIN.getQuantity()
                        + "\nIngredients: " +  dIN.getIngredientsList()
                        + "\nMeasure: " + dIN.getMeasure() + "\n"
        );
    });



        /** Ingredients Display **/
        reviewsLayoutManager = new LinearLayoutManager(this, VERTICAL, false);
        mReviewsRecyclerView.setLayoutManager(reviewsLayoutManager);
        ingredientsAdapter = new IngredientsAdapter();
        ingredientsAdapter.setValidReviews(listIngredients);
        mReviewsRecyclerView.setAdapter(ingredientsAdapter);
        /** Ingredients Display ENDS **/


        //        if (trailers != null && (trailers.size() != 0))
        gPostersLayoutManager = new LinearLayoutManager(this, VERTICAL, false);
        mTrailerRecyclerView.setLayoutManager(gPostersLayoutManager);


        _dStepsList = (ArrayList<Steps>) mIntent.getExtras().get(STEPS_List);

        ArrayList<String> listSteps = new ArrayList<>();

        trailersAdapter = new TrailersAdapter(this);
        trailersAdapter.setValidTrailers(_dStepsList);
        mTrailerRecyclerView.setAdapter(trailersAdapter);

//        if ((trailers == null || (trailers.size() == 0)) && (reviews == null)) {
//            Snackbar.make(getWindow().getDecorView().getRootView()
//                    , MessageFormat.format("No Trailers, Reviews Available", (Object) null)
//                    , Snackbar.LENGTH_LONG).setAction("Action", null).show();
//        } else if ((trailers != null) && (reviews != null)) {
//            trailersAdapter = new TrailersAdapter(this);
//            trailersAdapter.setValidTrailers(trailers);
//
//            mTrailerRecyclerView.setAdapter(trailersAdapter);
//            ingredientsAdapter = new IngredientsAdapter();
//            ingredientsAdapter.setValidReviews(reviews);
//            mReviewsRecyclerView.setAdapter(ingredientsAdapter);
//
//        } else if (trailers != null) {
//            mTrailerRecyclerView.setLayoutManager(gPostersLayoutManager);
//
//            mTrailerRecyclerView.setHasFixedSize(true);
//            trailersAdapter = new TrailersAdapter(this);
//            trailersAdapter.setValidTrailers(trailers);
//            mTrailerRecyclerView.setAdapter(trailersAdapter);
//            Snackbar.make(getWindow().getDecorView().getRootView()
//                    , MessageFormat.format("No Reviews Available", (Object) null)
//                    , Snackbar.LENGTH_LONG).setAction("Action", null).show();
//        } else {
//            mReviewsRecyclerView.setLayoutManager(reviewsLayoutManager);
//            mReviewsRecyclerView.setHasFixedSize(true);
//            ingredientsAdapter = new IngredientsAdapter();
//            ingredientsAdapter.setValidReviews(reviews);
//            mReviewsRecyclerView.setAdapter(ingredientsAdapter);
//            Snackbar.make(getWindow().getDecorView().getRootView()
//                    , MessageFormat.format("No Trailer Available", (Object) null)
//                    , Snackbar.LENGTH_LONG).setAction("Action", null).show();
//        }













    }

    private void initViews() {

//        wtrailersView = findViewById(R.id.wv_trailers);                    // Web-View

//        wtrailersView.setWebViewClient(new inWebView());
        vTitle = findViewById(R.id.tvOriginalTitle);

//        vOverview = findViewById(R.id.tvOverview);

        mTrailerRecyclerView = findViewById(R.id.recyclerview_trailer);      // Trailers
        mTrailerRecyclerView.setHasFixedSize(true);

        mReviewsRecyclerView = findViewById(R.id.recyclerVIngredients);      // Reviews d
        mReviewsRecyclerView.setHasFixedSize(true);
    }

//    public void onTrailerItemClickListener(String trailerId, int adapterItem) {
//        adapterItem++;
//        String frameVideo = "<html><body> Trailer no." + adapterItem + " <br><iframe width=\"420\" height=\"315\" src=\"https://www.youtube.com/embed/" + trailerId + "\" frameborder=\"0\" allowfullscreen></iframe></body></html>";
//        WebSettings webSettings = wtrailersView.getSettings();
//        webSettings.setJavaScriptEnabled(true);
//        wtrailersView.loadData(frameVideo, "text/html", "utf-8");


//        Toast.makeText(this, "Inside Steps-recyvlerV", Toast.LENGTH_SHORT).show();


//        Snackbar.make(getWindow().getDecorView().getRootView()
//                    , MessageFormat.format("No Trailer Available", (Object) null)
//                    , Snackbar.LENGTH_LONG).setAction("Action", null).show();

//    }

//    private class inWebView extends WebViewClient {
//        @Override
//        public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
//            view.loadUrl(strTrailer);
//            return true;
//        }
//    }

//    public void onFavImageViewClicked(View view) {                              // ddset favorite movies over all
//        String favMovie = inDetetails.getDetailAct_backdrop_path();
//        Date date = new Date();
//        final MovieEntries movieEntries = new MovieEntries(favMovie, date);      // os 'run() could have access to Entities
//        AppExecutors.getInstance().diskIO().execute(new Runnable() {
//            @Override
//            public void run() {                                                 // implemented DiskIO to handle multi-thread (race) scenario using Singleton Approach (getInstance())
//                fav_mDb.dbFavoriteMoviesDao().insertFavorites(movieEntries);
//                finish();                                                           // returning to MainActivity
//            }
//        });
//        imageView.setImageResource(R.drawable.ic_favorite_full_24dp);
//        imageView.setEnabled(false);                                             // Favorite control button
//    }

}
