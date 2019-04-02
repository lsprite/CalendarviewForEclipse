package com.haibin.calendarview.activity;

import com.haibin.calendarview.Calendar;
import com.haibin.calendarview.MonthView;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;

/**
 * 高仿魅族日历布局 Created by huanghaibin on 2017/11/15.
 */

public class SimpleMonthView extends MonthView {
	private int mRadius;

	/**
	 * 自定义魅族标记的文本画笔
	 */
	private Paint mTextPaint = new Paint();

	/**
	 * 背景圆点
	 */
	private Paint mPointPaint = new Paint();

	/**
	 * 今天的背景色
	 */
	private Paint mCurrentDayPaint = new Paint();

	/**
	 * 圆点半径
	 */
	private float mPointRadius;

	/**
	 * 自定义魅族标记的圆形背景
	 */
	private Paint mSchemeBasicPaint = new Paint();

	//
	private Paint mSelectTextPaint = new Paint();

	public SimpleMonthView(Context context) {
		super(context);

		mTextPaint.setTextSize(dipToPx(context, 8));
		mTextPaint.setColor(0xffffffff);
		mTextPaint.setAntiAlias(true);
		mTextPaint.setFakeBoldText(true);

		mSchemeBasicPaint.setAntiAlias(true);
		mSchemeBasicPaint.setStyle(Paint.Style.FILL);
		mSchemeBasicPaint.setTextAlign(Paint.Align.CENTER);
		mSchemeBasicPaint.setFakeBoldText(true);
		mSchemeBasicPaint.setColor(Color.WHITE);

		mCurrentDayPaint.setAntiAlias(true);
		mCurrentDayPaint.setStyle(Paint.Style.FILL);
		mCurrentDayPaint.setColor(0xff94caee);

		mPointPaint.setAntiAlias(true);
		mPointPaint.setStyle(Paint.Style.FILL);
		mPointPaint.setTextAlign(Paint.Align.CENTER);
		mPointPaint.setColor(Color.RED);

		//
		mSelectTextPaint.setAntiAlias(true);
		mSelectTextPaint.setStyle(Paint.Style.FILL);
		mSelectTextPaint.setTextAlign(Paint.Align.CENTER);
		mSelectTextPaint.setColor(Color.WHITE);
		mSelectTextPaint.setFakeBoldText(true);
		mSelectTextPaint.setTextSize(mCurMonthTextPaint.getTextSize());
		//
		mPointRadius = dipToPx(context, 2);

		Paint.FontMetrics metrics = mSchemeBasicPaint.getFontMetrics();
		// mSchemeBaseLine = mCircleRadius - metrics.descent + (metrics.bottom -
		// metrics.top) / 2
		// + dipToPx(getContext(), 1);
		// // 兼容硬件加速无效的代码
		// setLayerType(View.LAYER_TYPE_SOFTWARE, mSelectedPaint);
		// // 4.0以上硬件加速会导致无效
		// mSelectedPaint.setMaskFilter(new BlurMaskFilter(28,
		// BlurMaskFilter.Blur.SOLID));
		//
		// setLayerType(View.LAYER_TYPE_SOFTWARE, mSchemeBasicPaint);
		// mSchemeBasicPaint.setMaskFilter(new BlurMaskFilter(28,
		// BlurMaskFilter.Blur.SOLID));
	}

	@Override
	protected void onPreviewHook() {
		mRadius = Math.min(mItemWidth, mItemHeight) / 11 * 5 - 2;
	}

	@Override
	protected void onLoopStart(int x, int y) {

	}

	@Override
	protected void onDrawSelected(Canvas canvas, Calendar calendar, int x, int y, boolean hasScheme) {
		int cx = x + mItemWidth / 2;
		int cy = y + mItemHeight / 2;
		canvas.drawCircle(cx, cy, mRadius, mSelectedPaint);
		// 点
		if (hasScheme) {
			mPointPaint.setColor(Color.RED);
			canvas.drawCircle(x + mItemWidth / 2, y + mItemHeight - mPointRadius, mPointRadius, mPointPaint);
		}
	}

	@Override
	protected void onDrawScheme(Canvas canvas, Calendar calendar, int x, int y) {
	}

	@Override
	protected void onDrawText(Canvas canvas, Calendar calendar, int x, int y, boolean hasScheme, boolean isSelected) {
		int cx = x + mItemWidth / 2;
		int cy = y + mItemHeight / 2;
		int top = y - mItemHeight / 6;

		if (calendar.isCurrentDay() && !isSelected) {
			mCurrentDayPaint.setStrokeWidth(2);
			mCurrentDayPaint.setStyle(Style.STROKE);
			canvas.drawCircle(cx, cy, mRadius, mCurrentDayPaint);
		}
		// 当然可以换成其它对应的画笔就不麻烦，
		mCurMonthTextPaint.setColor(0xff333333);
		mCurMonthLunarTextPaint.setColor(0xffCFCFCF);
		mSchemeTextPaint.setColor(0xff333333);
		mOtherMonthTextPaint.setColor(0xFFe1e1e1);
		mOtherMonthLunarTextPaint.setColor(0xFFe1e1e1);
		mCurDayTextPaint.setColor(0xff333333);
		if (isSelected) {
			canvas.drawText(String.valueOf(calendar.getDay()), cx, mTextBaseLine + top, mSelectTextPaint);
			mCurMonthLunarTextPaint.setColor(Color.WHITE);
			canvas.drawText(calendar.getLunar(), cx, mTextBaseLine + y + mItemHeight / 10, mCurMonthLunarTextPaint);
		} else if (hasScheme) {
			canvas.drawText(String.valueOf(calendar.getDay()), cx, mTextBaseLine + top,
					calendar.isCurrentMonth() ? mSchemeTextPaint : mOtherMonthTextPaint);
			canvas.drawText(calendar.getLunar(), cx, mTextBaseLine + y + mItemHeight / 10, mCurMonthLunarTextPaint);
			// 点
			mPointPaint.setColor(Color.RED);
			canvas.drawCircle(x + mItemWidth / 2, y + mItemHeight - mPointRadius, mPointRadius, mPointPaint);
		} else {
			canvas.drawText(String.valueOf(calendar.getDay()), cx, mTextBaseLine + top, calendar.isCurrentDay()
					? mCurDayTextPaint : calendar.isCurrentMonth() ? mCurMonthTextPaint : mOtherMonthTextPaint);
			canvas.drawText(calendar.getLunar(), cx, mTextBaseLine + y + mItemHeight / 10, mCurMonthLunarTextPaint);
		}
	}

	/**
	 * dp转px
	 *
	 * @param context
	 *            context
	 * @param dpValue
	 *            dp
	 * @return px
	 */
	private static int dipToPx(Context context, float dpValue) {
		final float scale = context.getResources().getDisplayMetrics().density;
		return (int) (dpValue * scale + 0.5f);
	}
}
