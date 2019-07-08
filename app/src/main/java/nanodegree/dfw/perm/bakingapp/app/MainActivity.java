package nanodegree.dfw.perm.bakingapp.app;

import android.os.Bundle;

import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.widget.FrameLayout;
import nanodegree.dfw.perm.bakingapp.R;
import nanodegree.dfw.perm.bakingapp.data.db.MoviesDatabase;
import nanodegree.dfw.perm.bakingapp.ui.frags.FragmentReceipesMain;

public class MainActivity extends AppCompatActivity implements FragmentReceipesMain.OnFragmentInteractionListener {

    private MoviesDatabase mdB_MainActivity;                                 // MovieApp Stage Two Database
    public boolean isTwoPane;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        determinePaneLayout();
        if(!isTwoPane){
            FragmentReceipesMain fragmentReceipesMain = new FragmentReceipesMain();    // trying to replace MainAct-layout with a Fragment based
            FragmentManager fragMain = getSupportFragmentManager();
            fragMain.beginTransaction()
                    .add(R.id.frMainRecipes_container, fragmentReceipesMain)
                    .commit();
        }else {
            FragmentReceipesMain fragmentReceipesMainTwoPane = new FragmentReceipesMain().newInstance(true, "");
            FragmentManager fragMain = getSupportFragmentManager();
            fragmentReceipesMainTwoPane.getTwoPaneVal(true);
            fragMain.beginTransaction()
                    .add(R.id.frMainRecipes_container, fragmentReceipesMainTwoPane)
                    .commit();
        }
    }

    private void determinePaneLayout() {                    // checking for Second-Pane availability
        isTwoPane = false;
        FrameLayout fragmentItemDetail = findViewById(R.id.dummyFrame);
        // If there is a second pane for details
        if (fragmentItemDetail != null) {
            isTwoPane = true;
        }
    }

    @Override
    public void onFragmentInteraction(boolean uri) {

    }

}

