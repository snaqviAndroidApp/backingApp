package nanodegree.dfw.perm.bakingapp.data.handler.baking;

import android.os.Parcel;
import android.os.Parcelable;

import org.apache.commons.lang3.builder.ToStringBuilder;

public class Ingredients implements Parcelable{

    private Integer quantity;
    private String measure;
    private String ingredientsList;

    /**
     * No args constructor for use in serialization
     *
     */
    public Ingredients() {
    }

    /**
     *
     * @param measure
     * @param ingredient
     * @param quantity
     */
    public Ingredients(Integer quantity, String measure, String ingredient) {
        super();
        this.quantity = quantity;
        this.measure = measure;
        this.ingredientsList = ingredient;
    }

    protected Ingredients(Parcel in) {
        if (in.readByte() == 0) {
            quantity = null;
        } else {
            quantity = in.readInt();
        }
        measure = in.readString();
        ingredientsList = in.readString();
    }

    public static final Creator<Ingredients> CREATOR = new Creator<Ingredients>() {
        @Override
        public Ingredients createFromParcel(Parcel in) {
            return new Ingredients(in);
        }

        @Override
        public Ingredients[] newArray(int size) {
            return new Ingredients[size];
        }
    };

    public Integer getQuantity() {
        return quantity;
    }
    public String getMeasure() {
        return measure;
    }
    public String getIngredientsList() {
        return ingredientsList;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).append("quantity", quantity).append("measure", measure).append("ingredientsList", ingredientsList).toString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        if (quantity == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(quantity);
        }
        dest.writeString(measure);
        dest.writeString(ingredientsList);
    }




}
