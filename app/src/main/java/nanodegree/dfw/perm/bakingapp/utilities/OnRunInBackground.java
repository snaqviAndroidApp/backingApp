package nanodegree.dfw.perm.bakingapp.utilities;

public interface OnRunInBackground<T> {

    void onSuccess(T object);
    void onFailure(Exception e);

}
