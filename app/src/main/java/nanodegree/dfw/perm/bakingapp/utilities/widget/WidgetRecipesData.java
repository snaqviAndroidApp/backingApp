package nanodegree.dfw.perm.bakingapp.utilities.widget;

public final class WidgetRecipesData {
    private String name;
    private String ingredientsAccumulated;

    public WidgetRecipesData(String name, String ingredientsAccumulated) {
        this.name = name;
        this.ingredientsAccumulated = ingredientsAccumulated;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIngredientsAccumulated() {
        return ingredientsAccumulated;
    }

    public void setIngredientsAccumulated(String ingredientsAccumulated) {
        this.ingredientsAccumulated = ingredientsAccumulated;
    }
}
