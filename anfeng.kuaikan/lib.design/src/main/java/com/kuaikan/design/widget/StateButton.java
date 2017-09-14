package com.kuaikan.design.widget;

import android.content.Context;
import android.util.AttributeSet;

/**
 * @author deadline
 * @time 2016-11-07
 */

public class StateButton extends StateTextView {
    public StateButton(Context context) {
        this(context, null);
    }

    public StateButton(Context context, AttributeSet attrs) {
        this(context, attrs, android.R.attr.buttonStyle);
    }

    public StateButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }
}
