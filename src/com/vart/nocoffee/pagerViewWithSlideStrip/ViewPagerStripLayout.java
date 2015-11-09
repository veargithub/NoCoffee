package com.vart.nocoffee.pagerViewWithSlideStrip;

import android.content.Context;
import android.graphics.Paint;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.widget.FrameLayout;

/**
 * Created by 3020mt on 2015/11/6.
 */
public class ViewPagerStripLayout extends FrameLayout {

    private int curTabIndex = 0;
    private float curPositionOffset = 0f;
    private Paint stripPaint;
    private Paint dividerPaint;
    private ViewPager pager;
    private boolean showSeparator;
    private int stripHeight = 2;
    private int separatorWidth = 1;
    private int separatorPadding = 10;
    private int stripColor = 0xFF666666;
    private int separatorColor = 0x1A000000;

    public ViewPagerStripLayout(Context context) {
        super(context);
        init();
    }

    public ViewPagerStripLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public ViewPagerStripLayout(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }
}
