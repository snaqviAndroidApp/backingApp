package nanodegree.dfw.perm.bakingapp.app;

import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;

import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import org.json.JSONException;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;
import java.net.URL;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;

import nanodegree.dfw.perm.bakingapp.R;
import nanodegree.dfw.perm.bakingapp.data.handler.PrimeMovieDataHandler;
import nanodegree.dfw.perm.bakingapp.data.db.MovieEntries;
import nanodegree.dfw.perm.bakingapp.data.db.MoviesDatabase;
import nanodegree.dfw.perm.bakingapp.data.handler.baking.RecipiesHandler;
import nanodegree.dfw.perm.bakingapp.ui.VersatileAdapter;
import nanodegree.dfw.perm.bakingapp.ui.frags.FragmentReceipesMain;
import nanodegree.dfw.perm.bakingapp.utilities.jutilities.BakingJsonUtils;
import nanodegree.dfw.perm.bakingapp.utilities.jutilities.MovieJsonUtils;
import nanodegree.dfw.perm.bakingapp.utilities.NetworkUtils;
import nanodegree.dfw.perm.bakingapp.utilities.jutilities.PhaseTwoJsonUtils;
import nanodegree.dfw.perm.bakingapp.utilities.PhaseTwoNetworkUtils;

import static java.util.Objects.requireNonNull;
import static java.util.concurrent.TimeUnit.SECONDS;

public class MainActivity extends AppCompatActivity implements VersatileAdapter.ListItemClickListener
, FragmentReceipesMain.OnFragmentInteractionListener {

    private static final int MOVIES_OFFSET = 545;
    private static final int NUM_OF_MOVIES = 14;
    public static final String POPULARITY = "popularity";
    public static final String RATING = "vote_average";

    private ArrayList<HashMap<Integer, PrimeMovieDataHandler>> moviesFromServer, moviesInputListToOrder;
    boolean menuItemEnabled = false;
    private RecyclerView mRecyclerView;

    VersatileAdapter versatileAdapter;
    private ProgressBar mLoadIndicator;
    private long schPeriod;
    private int threadCounts;
    private MoviesDatabase mdB_MainActivity;                                 // MovieApp Stage Two Database

    private boolean isTwoPane = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        determinePaneLayout();
        _initialize();
        initView();

        FragmentReceipesMain fragmentReceipesMain = new FragmentReceipesMain();                 // trying to replace MainAct-layout with a Fragment based
        FragmentManager fragMain = getSupportFragmentManager();
        fragMain.beginTransaction()
                .add(R.id.frMainRecipes_container, fragmentReceipesMain)
                .commit();
    }

    private void determinePaneLayout() {                    // checking for Second-Pane availability
        FrameLayout fragmentItemDetail = findViewById(R.id.frlDetailContainer);
        // If there is a second pane for details
        if (fragmentItemDetail != null) {
            isTwoPane = true;
        }
    }

    private void _initialize() {
        schPeriod = 1;
        threadCounts = 0;
        if (null != moviesFromServer) moviesFromServer.clear();
        if (null != moviesInputListToOrder) moviesInputListToOrder.clear();
        mdB_MainActivity = MoviesDatabase.getInstance(getApplicationContext());     // Movies-Db Initialize
    }

    private void initView() {
        mRecyclerView = findViewById(R.id.recyclerview_movie);
        mLoadIndicator = findViewById(R.id.mv_loading_indicator);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);

        mRecyclerView.setLayoutManager(linearLayoutManager);
        mRecyclerView.setHasFixedSize(true);

        new ConnectionUtilities().getConnCheckHanlde();                     // Checking Connectivity (internet)
    }

    public void getRecipes(boolean connection) {
        if(connection){
            new FetchRecipes().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, null);           // Apparently no difference
        }
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    /** Backing Recipes DataExtraction Threading: AsyncTask ENDS here works here **/
    public class FetchRecipes extends AsyncTask<String, Void, ArrayList<RecipiesHandler>> {

        ArrayList<RecipiesHandler> bakingJDataParsed;
        String _jrawBakingData;

        @Override
        protected void onPreExecute() {
            mLoadIndicator.setVisibility(View.VISIBLE);
        }

        @Override
        protected ArrayList<RecipiesHandler> doInBackground(String... param) {
            _jrawBakingData = null;

            URL bakingInput = NetworkUtils.buildBakingUrl();
            try {
                _jrawBakingData = NetworkUtils.getResponseFromHttpUrl(bakingInput);
                bakingJDataParsed = BakingJsonUtils.parseBakingJnData(MainActivity.this, _jrawBakingData);          // getting all Recipies - jsonData parsed successfully

            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }

            return bakingJDataParsed;
        }

        @Override
        protected void onPostExecute(ArrayList<RecipiesHandler> recipiesHandlers) {
            mLoadIndicator.setVisibility(View.INVISIBLE);
            sendRecipesPosters(recipiesHandlers);
        }
    }

    public void onRecipesItemClickListener(RecipiesHandler recipesId) {

        // get Details Recipies data to be sent to details-View (Activity / Landscape: Fragment)
        Toast.makeText(MainActivity.this, String.format("Recipie no: %d", recipesId.getId()),  Toast.LENGTH_SHORT).show();

    }

    private void sendRecipesPosters(ArrayList<RecipiesHandler> recipiesHandlers) {
        versatileAdapter = new VersatileAdapter(MainActivity.this);
        versatileAdapter.setRecipes(recipiesHandlers);                                     // Experimental definition for Recipes card pasting
        mRecyclerView.setAdapter(versatileAdapter);
    }


//    public class MovieTasking extends AsyncTask<String, Void, ArrayList<HashMap<Integer, PrimeMovieDataHandler>>> {
//        HashMap<Integer, PrimeMovieDataHandler> parsedJMovieData;
////        ArrayList<String> parsedJMovieReviews;
//        String jsonMovieResponse,
//                rawTrailers,
//                rawReviews;
//
//
//        @Override
//        protected void onPreExecute() {
//            mLoadIndicator.setVisibility(View.VISIBLE);
//        }
//
//        @Override
//        synchronized protected ArrayList<HashMap<Integer, PrimeMovieDataHandler>> doInBackground(String... strings) {
//            moviesFromServer = new ArrayList<>();
//            moviesInputListToOrder = new ArrayList<>();
//            if (strings.length == 0) {
//                return null;
//            }
//            String moviesToOrder = strings[0];
//            if (moviesToOrder.equals(POPULARITY) || moviesToOrder.equals(RATING)) {
//                jsonMovieResponse = null;
//                parsedJMovieData = null;
//                rawReviews = null;
//                rawTrailers = null;
//                URL movieRequestUrl = NetworkUtils.buildToOrderPostersUrl(moviesToOrder);
//                try {
//                    jsonMovieResponse = NetworkUtils.getResponseFromHttpUrl(movieRequestUrl);
//                    moviesInputListToOrder = MovieJsonUtils.getOrderingMoviesStrings(MainActivity.this, jsonMovieResponse, null, null);
//                } catch (Exception e) {
//                    e.printStackTrace();
//                    System.out.printf("error occurred: %s", e.getStackTrace().toString());
//                    return null;
//                }
//                return moviesInputListToOrder;
//            } else {
//                Integer totalMovies = Integer.valueOf(strings[0]);
//                for (int i = 0; i < totalMovies; i++) {
//                    parsedJMovieData = null;
//                    jsonMovieResponse = null;
//                    URL movieRequestUrl = NetworkUtils.buildUrl(String.valueOf((MOVIES_OFFSET + i)));
//                    URL movieRequestReviewsUrl = PhaseTwoNetworkUtils.buildSecLevelDetailedUrl(String.valueOf((MOVIES_OFFSET + i)), "reviews");
//                    URL movieRequestTrailerUrl = PhaseTwoNetworkUtils.buildSecLevelDetailedUrl(String.valueOf((MOVIES_OFFSET + i)), "videos");
//                    try {
//                        jsonMovieResponse = NetworkUtils.getResponseFromHttpUrl(movieRequestUrl);                                                           // MovieApp Two Implementation
//                        rawReviews = NetworkUtils.getResponseFromHttpUrl(movieRequestReviewsUrl);
//                        rawTrailers = NetworkUtils.getResponseFromHttpUrl(movieRequestTrailerUrl);
//                        ArrayList<String> movieReviewExt = PhaseTwoJsonUtils.getPhaseTwoJsonData(MainActivity.this, rawReviews, "reviews");
//                        ArrayList<String> movieTrailerExt = PhaseTwoJsonUtils.getPhaseTwoJsonData(MainActivity.this, rawTrailers, "videos");
//                        if ((movieReviewExt.size() != 0) && (movieTrailerExt.size() != 0)) {                                                                // both Review & Trailer as
//                            parsedJMovieData = MovieJsonUtils.getMoviesStringsFromJson(MainActivity.this, jsonMovieResponse, movieReviewExt,
//                                    movieTrailerExt);                                                                                                       //sending 1st value
//                        } else if ((movieReviewExt.size() == 0) && (movieTrailerExt.size() != 0)) {
//                            parsedJMovieData = MovieJsonUtils.getMoviesStringsFromJson(MainActivity.this, jsonMovieResponse,
//                                    null, movieTrailerExt);
//                        } else {
//                            parsedJMovieData = MovieJsonUtils.getMoviesStringsFromJson(MainActivity.this, jsonMovieResponse,
//                                    movieReviewExt, null);
//                        }
//                    } catch (Exception e) {
//                        e.printStackTrace();
//                        System.out.printf("error occured: %s", e.getStackTrace().toString());
//                        if (e.getClass().getName() == "java.io.FileNotFoundException") {
//                            parsedJMovieData = new HashMap<>();
//                            parsedJMovieData.put((MOVIES_OFFSET + i),
//                                    new PrimeMovieDataHandler(
//                                            null,
//                                            null,
//                                            null,
//                                            null,
//                                            null,
//                                            0.0,
//                                            0.0,
//                                            "The resource requested could not be found",
//                                            null,
//                                            null
//                                    )
//                            );
//                            moviesFromServer.add(parsedJMovieData);
//                            continue;
//                        }
//                        return null;
//                    }
//                    moviesFromServer.add(parsedJMovieData);
//                }
//                return moviesFromServer;
//            }
//        }
//
//        @Override
//        protected void onPostExecute(ArrayList<HashMap<Integer, PrimeMovieDataHandler>> movieDataListIn) {
//            mLoadIndicator.setVisibility(View.INVISIBLE);
//            menuItemEnabled = true;
////            sendMovieData(movieDataListIn);
//            invalidateOptionsMenu();
//        }
//    }

//    private void sendMovieData(ArrayList<HashMap<Integer, PrimeMovieDataHandler>> localMovieData) {
//        moviesToView = new ArrayList<>();
//        if (localMovieData != null) {
//            localMovieData.forEach(m -> {
//                moviesToView.add(m.get(requireNonNull(m.keySet().toArray())[0]));
//            });
//        }
//        versatileAdapter = new VersatileAdapter(this, null, null);      // new implementation
//        versatileAdapter.setMoviePosters(moviesToView, "");
//        mRecyclerView.setAdapter(versatileAdapter);
//    }

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        MenuInflater mainMInflator = getMenuInflater();
//        mainMInflator.inflate(R.menu.main_menu, menu);
//        if (menuItemEnabled) {
//            menu.findItem(R.id.menuItem_sortby_popularity).setEnabled(true);
//            menu.findItem(R.id.menuItem_sortby_rate).setEnabled(true);
//            menu.findItem(R.id.menuItem_favorites).setEnabled(true);
//        }
//        return true;
//    }

//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        switch (item.getItemId()) {
//            case R.id.menuItem_sortby_rate: {
//                new MovieTasking().execute("vote_average");
//                try {
//                    Thread.sleep(320);
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
//                versatileAdapter.setMoviePosters(sortMoviesBy(moviesInputListToOrder, "vote_average"), RATING);
//                break;
//            }
//            case R.id.menuItem_sortby_popularity: {
//                new MovieTasking().execute("popularity");
//                try {
//                    Thread.sleep(320);
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
//                versatileAdapter.setMoviePosters(sortMoviesBy(moviesInputListToOrder, "popularity"), POPULARITY);
//                break;
//            }
//            case R.id.menuItem_favorites: {
////                versatileAdapter = new VersatileAdapter(this, this);
////                versatileAdapter = new VersatileAdapter(null, this, null);
//                _listMoviesRetrieved = new ArrayList<>();
//                ArrayList<String> _favList = new ArrayList<>();
//                AppExecutors.getInstance().diskIO().execute(new Runnable() {
//                    @Override
//                    public void run() {
//                        _listMoviesRetrieved.addAll(mdB_MainActivity.dbFavoriteMoviesDao().loadAllDbFavorite());
//                        if (_listMoviesRetrieved != null) {
//                            for (int favCount = 0; favCount < _listMoviesRetrieved.size(); favCount++) {
//                                _favList.add(_listMoviesRetrieved.get(favCount).getBfavorite_room());                   // Original passing statement
//                            }
//                            versatileAdapter.setMovieFavPosters(_favList, "favoritesList");
//                        }
//                        runOnUiThread(new Runnable() {                                              // favorite movies poster populates the same recylcerView
//                            @Override
//                            public void run() {
//                                try {
//                                    Thread.sleep(1400);
//                                } catch (InterruptedException e) {
//                                    e.printStackTrace();
//                                }
//                                if (versatileAdapter.getItemCount() == 0) {
//                                    Snackbar.make(Objects.requireNonNull(getCurrentFocus())
//                                            , MessageFormat.format("No movie designated as favorite", (Object) null)
//                                            , Snackbar.LENGTH_LONG).setAction("Action", null).show();
//                                }
//                                mRecyclerView.setAdapter(versatileAdapter);
//                            }
//                        });
//                    }
//                });
//                break;
//            }
//        }
//        return super.onOptionsItemSelected(item);
//    }

//    private ArrayList<PrimeMovieDataHandler> sortMoviesBy(ArrayList<HashMap<Integer, PrimeMovieDataHandler>> unOrderedMovies, String sortBy) {
//        moviesSortedByRating = new ArrayList<>();
//        if (sortBy.equals("popularity")) {
//            unOrderedMovies.sort((o1, o2) -> {
//                Double k1 = o1.get(o1.keySet().toArray()[0]).getPopularity();
//                Double k2 = o2.get(o2.keySet().toArray()[0]).getPopularity();
//                return k1.compareTo(k2);
//            });
//        } else {
//            unOrderedMovies.sort((o1, o2) -> {
//                Double k1 = o1.get(o1.keySet().toArray()[0]).getVote_average();
//                Double k2 = o2.get(o2.keySet().toArray()[0]).getVote_average();
//                return k1.compareTo(k2);
//            });
//        }
//        if (unOrderedMovies != null) {
//            unOrderedMovies.forEach(m -> {
//                moviesSortedByRating.add(m.get(m.keySet().toArray()[0]));
//            });
//        }
//        return moviesSortedByRating;
//    }

    public final class ConnectionUtilities {
        private ScheduledExecutorService scheduler =
                Executors.newScheduledThreadPool(1);
        final Runnable internNetCheck = new Runnable() {
            @Override
            public void run() {
                if (checkConnection()) {
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            while (threadCounts < 1) {
//                                getPrimaryMoviesList(true);
                                getRecipes(true);
                                threadCounts++;
                            }
                        }
                    });
                }
            }
        };
        final ScheduledFuture<?> connCheckHanlde =
//                scheduler.scheduleAtFixedRate(internNetCheck, 5, schPeriod, SECONDS);
                scheduler.scheduleAtFixedRate(internNetCheck, 2, schPeriod, SECONDS);     // see if shorter 'initial delay works

        public ScheduledFuture<?> getConnCheckHanlde() {
            return connCheckHanlde;
        }

        private boolean checkConnection() {
            try {
                int timeoutMs = 5500;
                Socket sock = new Socket();
                SocketAddress socketAddress = new InetSocketAddress("8.8.8.8", 53);
                sock.connect(socketAddress, timeoutMs);
                sock.close();
                return true;
            } catch (IOException e) {
                Snackbar.make(requireNonNull(getCurrentFocus())
                        , MessageFormat.format("Ah, no internet connetion", (Object) null)
                        , Snackbar.LENGTH_LONG).setAction("Action", null).show();
                threadCounts = 0;

                return false;
            }
        }
    }
}

