package cn.aipiti.arcimageview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.util.AttributeSet;

/**
 * 圆形、椭圆形图片控件
 */
public class CircleImageView extends android.support.v7.widget.AppCompatImageView {
    private int mWidth;
    private int mHeight;
    /**
     * 正圆、椭圆
     */
    private int mHowMuch;
    /**
     * 背景颜色
     */
    private int mBColor;

    private Paint mPaint;

    public CircleImageView(Context context) {
        this(context, null);
    }

    public CircleImageView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CircleImageView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.CircleImageView);
        mHowMuch = typedArray.getInt(R.styleable.CircleImageView_howMuch, 0);
        mBColor = typedArray.getColor(R.styleable.CircleImageView_bColor, Color.parseColor("#ffffff"));
        typedArray.recycle();

        mPaint = new Paint();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        mWidth = getMeasuredWidth();
        mHeight = getMeasuredHeight();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //画笔
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setFlags(Paint.ANTI_ALIAS_FLAG);
        mPaint.setColor(mBColor);


        if(mHowMuch == 0) {
            float radius = Math.min(mWidth, mHeight) / 2.0f;

            Path pathArc = new Path();
            pathArc.moveTo(0,0);
            pathArc.addCircle(mWidth/2, mHeight/2, radius, Path.Direction.CCW);

            Path path = new Path();
            RectF rectF = new RectF(0, 0, mWidth, mHeight);
            path.addRect(rectF, Path.Direction.CCW);

            path.op(pathArc, Path.Op.DIFFERENCE);

            canvas.drawPath(path, mPaint);
        }else{
            float radius = Math.min(mWidth, mHeight) / 2.0f;

            Path pathArc = new Path();
            RectF rectF = new RectF(0, 0, mWidth, mHeight);
            pathArc.moveTo(0,0);
            pathArc.addOval(rectF, Path.Direction.CCW);

            Path path = new Path();
           // RectF rectF = new RectF(0, 0, mWidth, mHeight);
            path.addRect(rectF, Path.Direction.CCW);

            path.op(pathArc, Path.Op.DIFFERENCE);

            canvas.drawPath(path, mPaint);

        }


    }


}
