package com.vart.nocoffee.pagerViewWithSlideStrip;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.widget.LinearLayout;

public class SlideStripView extends LinearLayout implements OnPageChangeListener{
	private int curTabIndex = 0;//当前正在哪个tab
	private float curPositionOffset = 0f;
	//private int nextTabIndex = 0;//将要去哪个tab
	//private int[] tabsWidth;//每一个tab的宽度
	private Paint stripPaint;//带子的paint
	private Paint dividerPaint;//tab间的分割线
	private ViewPager pager;//绑定的view pager
	private boolean showSeparator;//是否显示分割线
	private int stripHeight = 2;//带子的高度
	private int separatorWidth = 1;//分割线的宽度
	private int separatorPadding = 10;//分割线的padding
	private int stripColor = 0xFF666666;//带子的颜色
	private int separatorColor = 0x1A000000;//分割线的颜色

	public SlideStripView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();
	}

	public SlideStripView(Context context) {
		super(context);
		init();
	}
	
	private void init() {
		this.setWillNotDraw(false);//设置成false，viewgroup才会去调用ondraw这个方法
		stripPaint = new Paint();
		stripPaint.setStyle(Style.FILL);//填充rect
		stripPaint.setAntiAlias(true);
		stripPaint.setColor(stripColor);
		dividerPaint = new Paint();
		dividerPaint.setAntiAlias(true);
		dividerPaint.setStrokeWidth(separatorWidth);
		DisplayMetrics dm = getResources().getDisplayMetrics();
		stripHeight = (int)TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, stripHeight, dm);//dip to px
		separatorWidth = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, separatorWidth, dm);
		showSeparator = true;
	}

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		initTabsWidth();
		final View currentTab = this.getChildAt(curTabIndex);
		final int stripWidth = currentTab.getWidth();
		int widthDiff = 0;
		if (curTabIndex < this.getChildCount() - 1) {
			final View nextTab = this.getChildAt(curTabIndex + 1);
			widthDiff = nextTab.getWidth() - stripWidth;
		}
		Log.d(">>>>", "diff:" + widthDiff);
		int offset = (int)(stripWidth * curPositionOffset);
		int left = currentTab.getLeft() + offset;
		int right = left + stripWidth + (int)(widthDiff * curPositionOffset);//strip的长度要调整
		int height = this.getHeight();
		int top = height - stripHeight;
		int bottom = height;
		//Log.d(">>>>", left + " " + top + " " + right + " " + bottom + " " + offset);
		canvas.drawRect(left, top, right, bottom, stripPaint);
		if (showSeparator) {
			dividerPaint.setColor(separatorColor);
			for (int i = 0; i < this.getChildCount() - 1; i++) {
				View tab = this.getChildAt(i);
				canvas.drawLine(tab.getRight(), separatorPadding, tab.getRight(), height - separatorPadding, dividerPaint);
			} 
		}
	}
	
	private void initTabsWidth() {
		//if (tabsWidth == null) {
			int count = this.getChildCount();
			//tabsWidth = new int[count];
			for (int i = 0; i < count; i++) {
				final int position = i;
				View tab = this.getChildAt(i);
				//tabsWidth[i] = tab.getWidth();
				tab.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						Log.d(">>>>", position + "");
						SlideStripView.this.pager.setCurrentItem(position);
					}
					
				});
			}
		//}
	}

	public void setCurrentTab(int position) {
		this.curTabIndex = position;
	}
	
	public void setViewPager(ViewPager viewPager) {
		this.pager = viewPager;
		pager.setOnPageChangeListener(this);
		final PagerAdapter adapter = pager.getAdapter();
		if (adapter != null) {
			if (adapter.getCount() != this.getChildCount()) {
				throw new IllegalStateException("the count of pager must be the same as strips'");
			}
		}
	}

	@Override
	public void onPageScrollStateChanged(int arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onPageScrolled(int arg0, float arg1, int arg2) {
		curTabIndex = arg0;//arg0永远是左边的那个
		curPositionOffset = arg1;//arg1是右边那个view的宽度与它的容器宽度的比
		//curPositionOffset = 1.0f - curPositionOffset;
		Log.d(">>>>", "curTabIndex:" + curTabIndex + ", curPositionOffset:" + curPositionOffset);
		invalidate();
	}

	@Override
	public void onPageSelected(int arg0) {
		// TODO Auto-generated method stub
		
	}
	/**
	 * 设置下面那根线的高度，单位px
	 */
	public void setStripHeight(int height) {
		this.stripHeight = height;
	}
	/**
	 * 设置分割线宽度，单位px
	 */
	public void setSeparatorWidth(int separatorWidth) {
		this.separatorWidth = separatorWidth;
	}
	/**
	 * 设置分割线上下padding，单位px
	 */
	public void setSeparatorPadding(int separatorPadding) {
		this.separatorPadding = separatorPadding;
	}
	/**
	 * 设置下面那根线的颜色，单位px
	 */
	public void setStripColor(int stripColor) {
		this.stripColor = stripColor;
	}
	/**
	 * 设置分割线的颜色，单位px
	 */
	public void setSeparatorColor(int separatorColor) {
		this.separatorColor = separatorColor;
	}
	/**
	 * 是否显示分割线
	 */
	public void setShowSeparator(boolean showSeparator) {
		this.showSeparator = showSeparator;
	}
	
}
