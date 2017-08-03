package com.anfeng.wuhao.anfengkuaikan.activity;

import android.test.suitebuilder.annotation.LargeTest;

import com.anfeng.wuhao.anfengkuaikan.R;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * ============================
 * 作者： 吴浩
 * 时间： 2017/7/3.
 * 描述：
 * =============================
 */
@RunWith(AndroidJUnit4.class)
@LargeTest
public class UnitActivityTest  {
    private static final String STRING_TO_BE_TYPED = "Peter";

    @Rule
    public ActivityTestRule<MainActivity> mActivityRule = new ActivityTestRule<>(
            MainActivity.class);

    @Test
    public void sayHello(){
        onView(withId(R.id.editText)).perform(typeText(STRING_TO_BE_TYPED), closeSoftKeyboard()); //line 1
        onView(withText("Say hello!")).perform(click()); //line 2
        String expectedText = "Hello, " + STRING_TO_BE_TYPED + "!";
        onView(withId(R.id.textView)).check(matches(withText(expectedText))); //line 3
    }
}