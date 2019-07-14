package nanodegree.dfw.perm.bakingapp.app;


import android.support.test.espresso.ViewInteraction;
import android.support.test.filters.LargeTest;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.hamcrest.core.IsInstanceOf;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import nanodegree.dfw.perm.bakingapp.R;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withClassName;
import static android.support.test.espresso.matcher.ViewMatchers.withContentDescription;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.is;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class InflateDetailsPosition0 {

    @Rule
    public ActivityTestRule<MainActivity> mActivityTestRule = new ActivityTestRule<>(MainActivity.class);

    @Test
    public void inflateDetailsPosition0() {
        ViewInteraction recyclerView = onView(
                allOf(withId(R.id.recyclerview_recipes),
                        childAtPosition(
                                withClassName(is("android.support.constraint.ConstraintLayout")),
                                0)));
        recyclerView.perform(actionOnItemAtPosition(0, click()));

        ViewInteraction textView = onView(
                allOf(withId(R.id.tv_IngDetails), withText("Quantity: 2\nIngredients: Graham Cracker crumbs\nMeasure: CUP\nQuantity: 6\nIngredients: unsalted butter, melted\nMeasure: TBLSP\nQuantity: 0\nIngredients: granulated sugar\nMeasure: CUP\nQuantity: 1\nIngredients: salt\nMeasure: TSP\nQuantity: 5\nIngredients: vanilla\nMeasure: TBLSP\nQuantity: 1\nIngredients: Nutella or other chocolate-hazelnut spread\nMeasure: K\nQuantity: 500\nIngredients: Mascapone Cheese(room temperature)\nMeasure: G\nQuantity: 1\nIngredients: heavy cream(cold)\nMeasure: CUP\nQuantity: 4\nIngredients: cream cheese(softened)\nMeasure: OZ\n"), withContentDescription("reviews"),
                        childAtPosition(
                                allOf(withId(R.id.background),
                                        childAtPosition(
                                                IsInstanceOf.<View>instanceOf(android.widget.ScrollView.class),
                                                0)),
                                0),
                        isDisplayed()));
        textView.check(matches(withText("Quantity: 2 Ingredients: Graham Cracker crumbs Measure: CUP Quantity: 6 Ingredients: unsalted butter, melted Measure: TBLSP Quantity: 0 Ingredients: granulated sugar Measure: CUP Quantity: 1 Ingredients: salt Measure: TSP Quantity: 5 Ingredients: vanilla Measure: TBLSP Quantity: 1 Ingredients: Nutella or other chocolate-hazelnut spread Measure: K Quantity: 500 Ingredients: Mascapone Cheese(room temperature) Measure: G Quantity: 1 Ingredients: heavy cream(cold) Measure: CUP Quantity: 4 Ingredients: cream cheese(softened) Measure: OZ ")));

        ViewInteraction textView2 = onView(
                allOf(withId(R.id.tv_IngDetails), withText("Quantity: 2\nIngredients: Graham Cracker crumbs\nMeasure: CUP\nQuantity: 6\nIngredients: unsalted butter, melted\nMeasure: TBLSP\nQuantity: 0\nIngredients: granulated sugar\nMeasure: CUP\nQuantity: 1\nIngredients: salt\nMeasure: TSP\nQuantity: 5\nIngredients: vanilla\nMeasure: TBLSP\nQuantity: 1\nIngredients: Nutella or other chocolate-hazelnut spread\nMeasure: K\nQuantity: 500\nIngredients: Mascapone Cheese(room temperature)\nMeasure: G\nQuantity: 1\nIngredients: heavy cream(cold)\nMeasure: CUP\nQuantity: 4\nIngredients: cream cheese(softened)\nMeasure: OZ\n"), withContentDescription("reviews"),
                        childAtPosition(
                                allOf(withId(R.id.background),
                                        childAtPosition(
                                                IsInstanceOf.<View>instanceOf(android.widget.ScrollView.class),
                                                0)),
                                0),
                        isDisplayed()));
        textView2.check(matches(withText("Quantity: 2 Ingredients: Graham Cracker crumbs Measure: CUP Quantity: 6 Ingredients: unsalted butter, melted Measure: TBLSP Quantity: 0 Ingredients: granulated sugar Measure: CUP Quantity: 1 Ingredients: salt Measure: TSP Quantity: 5 Ingredients: vanilla Measure: TBLSP Quantity: 1 Ingredients: Nutella or other chocolate-hazelnut spread Measure: K Quantity: 500 Ingredients: Mascapone Cheese(room temperature) Measure: G Quantity: 1 Ingredients: heavy cream(cold) Measure: CUP Quantity: 4 Ingredients: cream cheese(softened) Measure: OZ ")));
    }

    private static Matcher<View> childAtPosition(
            final Matcher<View> parentMatcher, final int position) {

        return new TypeSafeMatcher<View>() {
            @Override
            public void describeTo(Description description) {
                description.appendText("Child at position " + position + " in parent ");
                parentMatcher.describeTo(description);
            }

            @Override
            public boolean matchesSafely(View view) {
                ViewParent parent = view.getParent();
                return parent instanceof ViewGroup && parentMatcher.matches(parent)
                        && view.equals(((ViewGroup) parent).getChildAt(position));
            }
        };
    }
}
