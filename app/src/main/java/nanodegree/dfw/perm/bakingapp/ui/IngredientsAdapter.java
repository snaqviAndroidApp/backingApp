
package nanodegree.dfw.perm.bakingapp.ui;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import nanodegree.dfw.perm.bakingapp.R;

public class IngredientsAdapter extends RecyclerView.Adapter<IngredientsAdapter.ReviewsViewHolder> {

    /** the code that works fine with StringBuilder
     * however needs hardcoding for getItemCount() to be '1'
     *
     */

    private static final String TAG = TrailersAdapter.class.getSimpleName();
    private ArrayList<String> mReviewsClicked;
    private int colorNum = 0;

    /** trying to convert ArrayList to String / StrinbBuilder **/


    public class ReviewsViewHolder extends RecyclerView.ViewHolder {
        TextView t_ReviewText;

        public ReviewsViewHolder(@NonNull View itemView) {
            super(itemView);
            t_ReviewText = itemView.findViewById(R.id.tv_IngDetails);
        }

        public void bind(String inStr) {
            t_ReviewText.setText(inStr);
            t_ReviewText.setTextColor(ContextCompat.getColor(itemView.getContext(), R.color.colorReview));
        }
    }

    public ReviewsViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        Context context = viewGroup.getContext();
        int layoutIdForListItem = R.layout.movie_reviews_item;
        LayoutInflater inflater = LayoutInflater.from(context);
        boolean shouldAttachToParentImmediately = false;
        View view = inflater.inflate(layoutIdForListItem, viewGroup, shouldAttachToParentImmediately);
        ReviewsViewHolder reviewsViewHolder = new ReviewsViewHolder(view);
        return reviewsViewHolder;
    }

    @Override
    public void onBindViewHolder(ReviewsViewHolder pholder, int position) {
        if(getItemCount() != 0){
            pholder.bind(mReviewsClicked.get(position));
            colorNum += 10;
        }
    }

    @Override
    public int getItemCount() {
        if(null == mReviewsClicked) return 0;
        return mReviewsClicked.size();
    }

    public void setValidReviews(ArrayList<String> reviewsIn) {
        mReviewsClicked = new ArrayList<>();
        StringBuilder stringBuilder = new StringBuilder();
        reviewsIn.forEach(inTemp -> {
           stringBuilder.append(inTemp);
        });
        mReviewsClicked.add(stringBuilder.toString());
        notifyDataSetChanged();
    }
}



