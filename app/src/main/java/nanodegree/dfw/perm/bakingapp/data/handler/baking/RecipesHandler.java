package nanodegree.dfw.perm.bakingapp.data.handler.baking;

import android.os.Parcel;
import android.os.Parcelable;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.util.ArrayList;

public class RecipesHandler implements Parcelable{

        private Integer id;
        private String name;
        private ArrayList<Ingredients> ingredients = new ArrayList<Ingredients>();
        private ArrayList<Steps> steps = new ArrayList<Steps>();
        private Integer servings;
        private String image;

        /**
         * No args constructor for use in serialization
         *
         */
        public RecipesHandler() {
        }

        /**
         * @param ingredients
         * @param id
         * @param servings
         * @param name
         * @param image
         * @param steps
         */
        public RecipesHandler(Integer id, String name, ArrayList<Ingredients> ingredients, ArrayList<Steps> steps, Integer servings, String image) {
            super();
            this.id = id;
            this.name = name;
            this.ingredients = ingredients;
            this.steps = steps;
            this.servings = servings;
            this.image = image;
        }

    protected RecipesHandler(Parcel in) {
        if (in.readByte() == 0) {
            id = null;
        } else {
            id = in.readInt();
        }
        name = in.readString();
        if (in.readByte() == 0) {
            servings = null;
        } else {
            servings = in.readInt();
        }
        image = in.readString();
    }

    public static final Creator<RecipesHandler> CREATOR = new Creator<RecipesHandler>() {
        @Override
        public RecipesHandler createFromParcel(Parcel in) {
            return new RecipesHandler(in);
        }

        @Override
        public RecipesHandler[] newArray(int size) {
            return new RecipesHandler[size];
        }
    };
        public Integer getId() {
            return id;
        }
        public String getName() {
            return name;
        }
        public ArrayList<Ingredients> getIngredients() {
            return ingredients;
        }
        public ArrayList<Steps> getSteps() {
            return steps;
        }
        public Integer getServings() {
            return servings;
        }
        public String getImage() {
            return image;
        }

        @Override
        public String toString() {
            return new ToStringBuilder(this).append("id", id).append("name", name).append("ingredients", ingredients).append("steps", steps).append("servings", servings).append("image", image).toString();
        }

        @Override
        public int hashCode() {
            return new HashCodeBuilder().append(ingredients).append(id).append(servings).append(name).append(image).append(steps).toHashCode();
        }

        @Override
        public boolean equals(Object other) {
            if (other == this) {
                return true;
            }
            if ((other instanceof RecipesHandler) == false) {
                return false;
            }
            RecipesHandler rhs = ((RecipesHandler) other);
            return new EqualsBuilder().append(ingredients, rhs.ingredients).append(id, rhs.id).append(servings, rhs.servings).append(name, rhs.name).append(image, rhs.image).append(steps, rhs.steps).isEquals();
        }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        if (id == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(id);
        }
        dest.writeString(name);
        if (servings == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(servings);
        }
        dest.writeString(image);
    }
}
