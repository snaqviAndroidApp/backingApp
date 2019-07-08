
package nanodegree.dfw.perm.bakingapp.ui;

import android.content.Intent;
import android.net.Uri;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.webkit.WebView;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import static android.widget.LinearLayout.VERTICAL;
import static nanodegree.dfw.perm.bakingapp.data.Strings.INGREDIENTS_List;
import static nanodegree.dfw.perm.bakingapp.data.Strings.NAME;
import static nanodegree.dfw.perm.bakingapp.data.Strings.STEPS_List;

import java.text.MessageFormat;
import java.util.ArrayList;

import nanodegree.dfw.perm.bakingapp.R;
import nanodegree.dfw.perm.bakingapp.data.handler.baking.Ingredients;
import nanodegree.dfw.perm.bakingapp.data.handler.baking.Steps;
import nanodegree.dfw.perm.bakingapp.ui.frags.FragmentReceipesMain;
import nanodegree.dfw.perm.bakingapp.ui.frags.FragmentRecipesDetails;


public class DetailsActivity extends AppCompatActivity implements FragmentRecipesDetails.OnFragmentInteractionListener {

    WebView wtrailersView;
    private boolean isTwoPaneDetails;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        determinePaneLayoutDetails();
        if(isTwoPaneDetails){

            FragmentManager fragMain = getSupportFragmentManager();                        // Get FragmentManager

            FragmentRecipesDetails fragmentDetailsTwoPane = new FragmentRecipesDetails();
            fragmentDetailsTwoPane.getTwoPaneValDetail(true);
            fragMain.beginTransaction()
                    .add(R.id.frDetails_container_land_prime, fragmentDetailsTwoPane)
                    .commit();


            /**  Inflate the secondary_fragment in Tablet-firmWare **/
            FragmentRecipesDetails fragmentDetailsTwoPaneSec = new FragmentRecipesDetails();

            fragmentDetailsTwoPaneSec.getTwoPaneValDetail(true);

            fragMain.beginTransaction()
                    .add(R.id.frDetails_container_land_sec, fragmentDetailsTwoPaneSec)
                    .commit();
            /**  Inflate the secondary_fragment in Tablet-firmWare ENDS **/

        }else {
            FragmentRecipesDetails fragmentDetailsTwoPane = new FragmentRecipesDetails();
            FragmentManager fragMain = getSupportFragmentManager();
            fragMain.beginTransaction()
                    .add(R.id.frDetails_container, fragmentDetailsTwoPane)
                    .commit();
        }

    }

    private void determinePaneLayoutDetails() {                    // checking for Second-Pane availability
        isTwoPaneDetails = false;
        FrameLayout fragmentDetailsLand = findViewById(R.id.frDetails_container_land_sec);
        if (fragmentDetailsLand != null) {                               // If there is a second pane for details
            isTwoPaneDetails = true;
        }
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

//    public void onTrailerItemClickListener(String trailerId, int adapterItem) {
//        adapterItem++;
//
//        String frameVideo = "<html><body> Trailer no." + adapterItem + " <br><iframe width=\"420\" height=\"315\" src=\"https://www.youtube.com/embed/" + trailerId + "\" frameborder=\"0\" allowfullscreen></iframe></body></html>";
//        WebSettings webSettings = wtrailersView.getSettings();
//        webSettings.setJavaScriptEnabled(true);
//        wtrailersView.loadData(frameVideo, "text/html", "utf-8");
//
//
//        Toast.makeText(this, "Inside Steps-recyvlerV", Toast.LENGTH_SHORT).show();
//
//
//        Snackbar.make(getWindow().getDecorView().getRootView()
//                    , MessageFormat.format("No Trailer Available", (Object) null)
//                    , Snackbar.LENGTH_LONG).setAction("Action", null).show();
//
//    }
//
//
//    private class inWebView extends WebViewClient {
//        @Override
//        public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
//            view.loadUrl(strTrailer);
//            return true;
//        }
//    }


}
