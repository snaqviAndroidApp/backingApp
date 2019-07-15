package nanodegree.dfw.perm.bakingapp.ui;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import nanodegree.dfw.perm.bakingapp.R;
import nanodegree.dfw.perm.bakingapp.data.handler.baking.RecipesHandler;

public class VersatileAdapter extends RecyclerView.Adapter<VersatileAdapter.MovieViewHolder> {

    private ArrayList<RecipesHandler> recipiesHForAdapter;
    private OnRecipesClickListener mRecipesClickListener;

    int adapterPosition = 0;
    String recipesStr = null;
    TextView tvRecipes;


    public interface OnRecipesClickListener {
        /**
         * Successful implementation of onClick Event Handling for MainActivity Posters
         * as well as for Favorite-Posters
         *
         * @param reipesId for MainPosters
         *                    that are being painted on same recyclerView that was
         *                    initially used for MainActivity posters that is
         *                    starting point for the App
         **/
        default void onRecipesSelected(RecipesHandler reipesId) { }
    }

    public VersatileAdapter(OnRecipesClickListener recipesItemsClickListener) {

        try{
            mRecipesClickListener = recipesItemsClickListener;
        }catch (ClassCastException e){
            throw new ClassCastException(recipesItemsClickListener.toString()
                    + "must implement OnRecipesClickListener");
        }

    }

    public class MovieViewHolder extends RecyclerView.ViewHolder
            implements View.OnClickListener {

        MovieViewHolder(@NonNull View itemView) {
            super(itemView);
            tvRecipes = itemView.findViewById(R.id.tvRecipes);
            tvRecipes.setBackground(null);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            adapterPosition = getAdapterPosition();
            mRecipesClickListener.onRecipesSelected(recipiesHForAdapter.get(adapterPosition));
        }
    }

    @NonNull
    @Override
    public MovieViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        Context vContext = viewGroup.getContext();
        int layoutIdForListItem = R.layout.movie_poster_item;
        LayoutInflater mainPosterInflaltor = LayoutInflater.from(vContext);
        boolean shouldAttachToParentImmediately = false;
        View viewPoster = mainPosterInflaltor.inflate(layoutIdForListItem,viewGroup,shouldAttachToParentImmediately);
        return new MovieViewHolder(viewPoster);
    }

    @Override
    public void onBindViewHolder(@NonNull MovieViewHolder movieViewHolder, int position) {
        RecipesHandler mRecipeStr = recipiesHForAdapter.get(position);
        recipesStr = mRecipeStr.getName();
        tvRecipes.setText(recipesStr);
    }

    @Override
    public int getItemCount() {
        if(null == recipiesHForAdapter) return 0;
        return recipiesHForAdapter.size();
    }


    /** Favorite Movies Handler
     * @param recipiesListIn brings in the List of recipes List
     **/
    public void setRecipes(ArrayList<RecipesHandler> recipiesListIn) {                         // Recipes
        recipiesHForAdapter = recipiesListIn;
        notifyDataSetChanged();
    }

}
