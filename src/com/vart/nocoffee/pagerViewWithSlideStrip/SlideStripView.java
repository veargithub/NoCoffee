package com.vart.nocoffee.pagerViewWithSlideStrip;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;

public class SlideStripView extends LinearLayout implements OnPageChangeListener{
	private int curTabIndex = 0;//��ǰ�����ĸ�tab
	private float curPositionOffset = 0f;
	private int nextTabIndex = 0;//��Ҫȥ�ĸ�tab
	private int[] tabsWidth;//ÿһ��tab�Ŀ��
	private int stripHeight = 2;//���ӵĸ߶�
	private Paint stripPaint;//���ӵ�paint
	private int stripColor = 0xFF666666;//���ӵ���ɫ
	private ViewPager pager;//�󶨵�view pager

	public SlideStripView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();
	}

	public SlideStripView(Context context) {
		super(context);
		init();
	}
	
	private void init() {
		this.setWillNotDraw(false);//���ó�false��viewgroup�Ż�ȥ����ondraw�������
		stripPaint = new Paint();
		stripPaint.setStyle(Style.FILL);
		stripPaint.setAntiAlias(true);
		stripPaint.setColor(stripColor);
	}

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		initTabsWidth();
//		int stripWidth = tabsWidth[curTabIndex];//TODO java.lang.ArrayIndexOutOfBoundsException: length=2; index=2
//		int left = 0;
//		for (int i = 0; i < curTabIndex; i++) {
//			left += tabsWidth[i];
//		}
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
		int right = left + stripWidth + (int)(widthDiff * curPositionOffset);//strip�ĳ���Ҫ����
		int height = this.getHeight();
		int top = height - stripHeight;
		int bottom = height;
		//Log.d(">>>>", left + " " + top + " " + right + " " + bottom + " " + offset);
		canvas.drawRect(left, top, right, bottom, stripPaint);
	}
	
	private void initTabsWidth() {
		if (tabsWidth == null) {
			int count = this.getChildCount();
			tabsWidth = new int[count];
			for (int i = 0; i < count; i++) {
				final int position = i;
				View tab = this.getChildAt(i);
				tabsWidth[i] = tab.getWidth();
				tab.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						Log.d(">>>>", position + "");
						SlideStripView.this.curTabIndex = position;
						SlideStripView.this.invalidate();
					}
					
				});
			}
		}
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
		curTabIndex = arg0;//arg0��Զ����ߵ��Ǹ�
		curPositionOffset = arg1;//arg1���ұ��Ǹ�view�Ŀ��������������ȵı�
		//curPositionOffset = 1.0f - curPositionOffset;
		Log.d(">>>>", "curTabIndex:" + curTabIndex + ", curPositionOffset:" + curPositionOffset);
		invalidate();
	}

	@Override
	public void onPageSelected(int arg0) {
		// TODO Auto-generated method stub
		
	}
}
