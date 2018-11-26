package cn.aipiti.arcimageview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;

/**
 * 项目名称：ArcImageView
 * 类名称：ArcImageView
 * 类描述：实现图片上下左右弧形、弧形方向可向内、向外
 * 创建人：aipiti.cn
 *    QQ：67706724
 * 创建时间：2018年11月23日上午10:43:17
 * 修改人：
 * 修改时间：2018年11月23日上午10:43:17
 * 修改备注： https://github.com/67706724/ArcImageView.git
 * @version V1.0
 */
public class ArcImageView extends android.support.v7.widget.AppCompatImageView {
    private int mWidth;
    private int mHeight;
    /**
     * 弧形高度
     */
    private int mArcHeight;
    /**
     * 背景颜色
     */
    private int mBColor;
    /**
     * 弧出现的位置  左、上、右、下
     */
    private int mPosition;
    /**
     * 玄弧 方向 向内、向外
     */
    private int mDirection;

    private Paint mPaint;

    public ArcImageView(Context context) {
        this(context, null);
    }

    public ArcImageView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ArcImageView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.ArcImageView);
        mArcHeight = typedArray.getDimensionPixelSize(R.styleable.ArcImageView_arcHeight, 0);
        mBColor = typedArray.getColor(R.styleable.ArcImageView_bColor, Color.parseColor("#ffffff"));
        mPosition = typedArray.getInt(R.styleable.ArcImageView_mPosition, 0);
        mDirection = typedArray.getInt(R.styleable.ArcImageView_mDirection, 0);
        typedArray.recycle();

        mPaint = new Paint();
    }
    //测量控件大小
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);

        if (widthMode == MeasureSpec.EXACTLY) {
            mWidth = widthSize;
        }
        if (heightMode == MeasureSpec.EXACTLY) {
            mHeight = heightSize;
        }
        setMeasuredDimension(mWidth, mHeight);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //画笔
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setFlags(Paint.ANTI_ALIAS_FLAG);
        mPaint.setColor(mBColor);
        RectF rectF;//方形
        Path path = new Path();
        Path pathArc = new Path();
        switch (mPosition){
            case 0: //左边 弧形-------------------
                if(mDirection==0) {
                    rectF = new RectF(0, 0, mArcHeight, mHeight);
                    path.addRect(rectF, Path.Direction.CCW);
                    pathArc.moveTo(mArcHeight, 0);
                    pathArc.quadTo(0, mHeight / 2, mArcHeight, mHeight);
                }else {
                    pathArc.moveTo(0, 0);
                    pathArc.quadTo(mArcHeight, mHeight / 2, 0, mHeight);
                }
                break;
            case 1: //上边 弧形-------------------
                if(mDirection==0) {
                    rectF = new RectF(0, 0, mWidth, mArcHeight);
                    path.addRect(rectF, Path.Direction.CCW);
                    pathArc.moveTo(0, mArcHeight);
                    pathArc.quadTo(mWidth / 2, 0, mWidth, mArcHeight);
                }else{
                    pathArc.moveTo(0, 0);
                    pathArc.quadTo(mWidth / 2, mArcHeight, mWidth, 0);
                }
                break;
            case 2: //右边 弧形-------------------
                if(mDirection==0) {
                    rectF = new RectF(mWidth - mArcHeight, 0, mWidth, mHeight);
                    path.addRect(rectF, Path.Direction.CCW);
                    pathArc.moveTo(mWidth - mArcHeight, 0);
                    pathArc.quadTo(mWidth, mHeight / 2, mWidth - mArcHeight, mHeight);
                }
                else{
                    pathArc.moveTo(mWidth, 0);
                    pathArc.quadTo(mWidth - mArcHeight, mHeight / 2, mWidth, mHeight);
                }
                break;
            default: //下边 弧形-------------------
                if(mDirection==0) {
                    rectF = new RectF(0, mHeight - mArcHeight, mWidth, mHeight);
                    path.addRect(rectF, Path.Direction.CCW);
                    pathArc.moveTo(0, mHeight - mArcHeight);
                    pathArc.quadTo(mWidth / 2, mHeight, mWidth, mHeight - mArcHeight);
                }else {
                    pathArc.moveTo(0, mHeight);
                    pathArc.quadTo(mWidth / 2, mHeight - mArcHeight, mWidth, mHeight);
                }
                break;
        }

        if(mDirection==0) {
            path.op(pathArc, Path.Op.DIFFERENCE);
            canvas.drawPath(path, mPaint);
        }else{
            canvas.drawPath(pathArc, mPaint);
        }
    }
}
