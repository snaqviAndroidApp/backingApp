
package nanodegree.dfw.perm.bakingapp.ui;

import android.net.Uri;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.WebView;
import android.widget.FrameLayout;

import nanodegree.dfw.perm.bakingapp.R;
import nanodegree.dfw.perm.bakingapp.ui.frags.FragmentRecipesDetails;
import nanodegree.dfw.perm.bakingapp.ui.frags.tablet.StepClips;


public class DetailsActivity extends AppCompatActivity implements FragmentRecipesDetails.OnFragmentInteractionListener {
//        , StepsAdapter.StepsOnClickHandler {

    private boolean isTwoPaneDetails;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        determinePaneLayoutDetails();

        if(isTwoPaneDetails){
            FragmentManager fragDetails = getSupportFragmentManager();                        // Get FragmentManager
            FragmentRecipesDetails fragmentDetailsTwoPane = new FragmentRecipesDetails();
            fragmentDetailsTwoPane.getTwoPaneValDetail(true);
            fragDetails.beginTransaction()
                    .add(R.id.frDetails_container_land_prime, fragmentDetailsTwoPane)
                    .commit();

            /**  Inflate the secondary_fragment in Tablet-firmWare **/
//            StepClips tabletStepDetailsFrag = new StepClips();                // issue with inflating the ExoPlayer layout in Tablet
//            fragmentDetailsTwoPane.getTwoPaneValDetail(true);
//            fragDetails.beginTransaction()
//                    .add(R.id.frDetails_container_land_sec, tabletStepDetailsFrag)          // inflating the right-Tablet (2nd) Fragment
//                    .commit();
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

}
