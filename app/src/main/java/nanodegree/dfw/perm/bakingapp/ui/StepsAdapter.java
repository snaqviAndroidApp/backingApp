package nanodegree.dfw.perm.bakingapp.ui;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.ArrayList;

import nanodegree.dfw.perm.bakingapp.R;
import nanodegree.dfw.perm.bakingapp.data.handler.baking.Steps;

public class StepsAdapter extends RecyclerView.Adapter<StepsAdapter.PostersViewHolder> {

    private static final String TAG = StepsAdapter.class.getSimpleName();
    private ArrayList<Steps> mStepsClickedList;
    final private StepsOnClickHandler mStepsClickHandler;

    public interface StepsOnClickHandler {
        default void onTrailerItemClickListener(Steps stepClicked, int adapterPos) { }
    }

    public StepsAdapter(StepsOnClickHandler stepsClickHandler) {
        mStepsClickHandler = stepsClickHandler;
    }

    public class PostersViewHolder extends RecyclerView.ViewHolder
            implements View.OnClickListener {

        ImageView b_TrailerPlayer;
        public PostersViewHolder(@NonNull View itemView) {
            super(itemView);
            b_TrailerPlayer = itemView.findViewById(R.id.playButton);
            itemView.setOnClickListener(this);
        }

        public void bind(int position) { }

        @Override
        public void onClick(View v) {
            int stepsAdapterPosition = getAdapterPosition();
            Toast.makeText(v.getContext(), "Hi in Steps no: " + stepsAdapterPosition, Toast.LENGTH_SHORT).show();

            mStepsClickHandler.onTrailerItemClickListener(mStepsClickedList.get(stepsAdapterPosition), stepsAdapterPosition);
            Log.d("_tClick", "trailer: " + stepsAdapterPosition + " clicked");
        }
    }

    public PostersViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        Context context = viewGroup.getContext();
        int layoutIdForListItem = R.layout.movie_trailerbutton_item;
        LayoutInflater inflater = LayoutInflater.from(context);
        boolean shouldAttachToParentImmediately = false;
        View view = inflater.inflate(layoutIdForListItem, viewGroup, shouldAttachToParentImmediately);
        PostersViewHolder trailersViewHolder = new PostersViewHolder(view);
        return trailersViewHolder;
    }

    @Override
    public void onBindViewHolder(PostersViewHolder pholder, int position) {
        if(getItemCount() != 0){
            pholder.bind(position);
        }
    }

    @Override
    public int getItemCount() {
        if (null == mStepsClickedList) return 0;
        return mStepsClickedList.size();
    }

    public void setValidTrailers(ArrayList<Steps> stepsRcvdList) {
        mStepsClickedList = stepsRcvdList;
        notifyDataSetChanged();
    }
}