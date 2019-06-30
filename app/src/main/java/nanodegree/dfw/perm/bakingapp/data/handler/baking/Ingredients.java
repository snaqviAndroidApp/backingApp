package nanodegree.dfw.perm.bakingapp.data.handler.baking;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

public class Ingredients {

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

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public String getMeasure() {
        return measure;
    }

    public void setMeasure(String measure) {
        this.measure = measure;
    }

    public String getIngredientsList() {
        return ingredientsList;
    }

    public void setIngredientsList(String ingredientsList) {
        this.ingredientsList = ingredientsList;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).append("quantity", quantity).append("measure", measure).append("ingredientsList", ingredientsList).toString();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(measure).append(ingredientsList).append(quantity).toHashCode();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof Ingredients) == false) {
            return false;
        }
        Ingredients rhs = ((Ingredients) other);
        return new EqualsBuilder().append(measure, rhs.measure).append(ingredientsList, rhs.ingredientsList).append(quantity, rhs.quantity).isEquals();
    }

}
