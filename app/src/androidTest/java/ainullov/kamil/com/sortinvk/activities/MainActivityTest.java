package ainullov.kamil.com.sortinvk.activities;

import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import ainullov.kamil.com.sortinvk.R;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.RootMatchers.withDecorView;
import static android.support.test.espresso.matcher.ViewMatchers.isClickable;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.core.IsNot.not;
import static org.hamcrest.core.StringStartsWith.startsWith;


@RunWith(AndroidJUnit4.class)
public class MainActivityTest {

    @Rule
    public ActivityTestRule<MainActivity> activityTestRule =
            new ActivityTestRule<>(MainActivity.class);


    @Test
    public void checkToastThenButtonStartPressed() {
        onView(withId(R.id.buttonStart)).perform(click());

        onView(withText(startsWith("Выберите группу "))).
                inRoot(withDecorView(
                        not(is(activityTestRule.getActivity().
                                getWindow().getDecorView())))).
                check(matches(isDisplayed()));
    }

    @Test
    public void checkMainLogic() throws InterruptedException {
        onView(withId(R.id.btnGroupSelection)).check(matches(isClickable()));
        onView(withId(R.id.btnGroupSelection)).perform(click());
        //Нажатие на RecyclerView
        onView(withId(R.id.recyclerViewGroupSelection))
                .perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));
        onView(withId(R.id.editTextPostsCount))
                .perform(typeText("2"), closeSoftKeyboard());
        onView(withId(R.id.buttonStart)).perform(click());

        Thread.sleep(5000);
        onView(withId(R.id.recyclerView)).check(matches(isDisplayed()));

    }
}