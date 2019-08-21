package nanodegree.dfw.perm.bakingapp.app;

import android.databinding.DataBindingUtil;
import android.os.Bundle;

import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.widget.FrameLayout;

import nanodegree.dfw.perm.bakingapp.R;
import nanodegree.dfw.perm.bakingapp.data.background.services.BuildConfig;
import nanodegree.dfw.perm.bakingapp.data.db.MoviesDatabase;
import nanodegree.dfw.perm.bakingapp.ui.frags.FragmentRecipesMain;
import timber.log.Timber;

public class MainActivity extends AppCompatActivity implements FragmentRecipesMain.OnFragmentInteractionListener {

    private MoviesDatabase mdB_MainActivity;                                           // MovieApp Stage Two Database
    public boolean isTwoPane;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (BuildConfig.DEBUG) {
            Timber.plant(new Timber.DebugTree());
        }
        Timber.d("MainActivity received name ");

        determinePaneLayout();
        if(!isTwoPane){
            FragmentRecipesMain fragmentReceipesMain = new FragmentRecipesMain();
            FragmentManager fragMain = getSupportFragmentManager();
            fragMain.beginTransaction()
                    .add(R.id.frMainRecipes_container, fragmentReceipesMain)
                    .commit();
        }else {
            FragmentRecipesMain fragmentRecipesMainTwoPane = new FragmentRecipesMain();
            FragmentManager fragMain = getSupportFragmentManager();
            fragmentRecipesMainTwoPane.getTwoPaneVal(true);
            fragMain.beginTransaction()
                    .add(R.id.frMainRecipes_container, fragmentRecipesMainTwoPane)
                    .commit();
        }
    }

    private void determinePaneLayout() {                                            // checking for Second-Pane availability
        isTwoPane = false;
        FrameLayout fragmentItemDetail = findViewById(R.id.dummyFrame);
        if (fragmentItemDetail != null) {                                           // If there is a second pane for details
            isTwoPane = true;
        }
    }

    @Override
    public void onFragmentInteraction(boolean uri) {

    }
}

