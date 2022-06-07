package com.example.customview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;

import androidx.annotation.Nullable;

public class TempertureView extends View {
    private static final String TAG = "huachen";
    private int width;
    private int height;
    private int arcRadius;
    Paint paint;
    Paint paintYouLiang;
    RectF rect;
    Rect r30;
    Rect r0;
    Rect rNormal;
    Rect r10;
    Rect rYou;
    public TempertureView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        r30 = new Rect();
        r0 = new Rect();
        rNormal = new Rect();
        r10 = new Rect();
        rYou = new Rect();
        paint = new Paint();
        paint.setAntiAlias(true);
        paint.setTextSize(sp2px(15));
        paint.setStrokeWidth(3);
        paint.setColor(Color.parseColor("#B6B5B5"));
        paint.setStyle(Paint.Style.STROKE);
        paint.getTextBounds("-30°",0,"-30°".length(),r30);
        paint.getTextBounds("-0°",0,"-0°".length(),r0);
        paint.getTextBounds("中等",0,"中等".length(),rNormal);
        paint.getTextBounds("10°",0,"10°".length(),r10);
        paint.getTextBounds("优",0,"优".length(),rYou);
        paintYouLiang = new Paint();
        paintYouLiang.setAntiAlias(true);
        paintYouLiang.setTextSize(sp2px(12));
        paintYouLiang.setStrokeWidth(3);
        paintYouLiang.setColor(Color.parseColor("#B6B5B5"));
        paintYouLiang.setStyle(Paint.Style.STROKE);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        width = height = Math.min(h,w);
        arcRadius = width/2-dp2px(20)-dp2px(20);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        drawScale(canvas);
        drawArc(canvas);
        drawText(canvas);
        drawCircle(canvas);
        draw2Line(canvas);
    }

    private void draw2Line(Canvas canvas) {
        canvas.save();
        canvas.translate(getWidth()/2,getHeight()/2);
        canvas.rotate(-135);
        canvas.drawLine(0, -(width/2-dp2px(25)), 0, -(width/2-dp2px(30)) + dp2px(10), paint);
        canvas.restore();
    }

    private void drawCircle(Canvas canvas) {
        canvas.save();
        canvas.translate(getWidth()/2,getHeight()/2);
        canvas.drawCircle(0,0,arcRadius-100,paint);
        canvas.restore();
    }

    private void drawText(Canvas canvas) {
        canvas.save();
        canvas.drawText("-30°",getWidth()/2-0.7f*arcRadius-80,getHeight()/2+0.7f*arcRadius+70,paint);
        canvas.drawText("优",getWidth()/2-0.924f*arcRadius-60,getHeight()/2+0.383f*arcRadius+35,paintYouLiang);
        canvas.drawText("-20°",getWidth()/2-1.0f*arcRadius-35-r30.width(),getHeight()/2+r30.height()/2,paint);
        canvas.drawText("良",getWidth()/2-0.924f*arcRadius-60,getHeight()/2-0.383f*arcRadius-20,paintYouLiang);
        canvas.drawText("-10°",getWidth()/2-0.7f*arcRadius-80,getHeight()/2-0.7f*arcRadius-40,paint);
        canvas.drawText("中等",getWidth()/2-0.383f*arcRadius-rNormal.width(),getHeight()/2-0.924f*arcRadius-30,paintYouLiang);
        canvas.drawText("-0°",getWidth()/2-r0.width()/2,getHeight()/2-arcRadius-r0.height()-20,paint);
        canvas.drawText("中等",getWidth()/2+0.383f*arcRadius,getHeight()/2-0.924f*arcRadius-30,paintYouLiang);
        canvas.drawText("10°",getWidth()/2+0.7f*arcRadius+r10.width()/2,getHeight()/2-0.7f*arcRadius-40,paint);
        canvas.drawText("过热",getWidth()/2+0.924f*arcRadius+rNormal.width()/2,getHeight()/2-0.383f*arcRadius-20,paintYouLiang);
        canvas.drawText("20°",getWidth()/2+arcRadius+35,getHeight()/2+r30.height()/2,paint);
        canvas.drawText("危险",getWidth()/2+0.924f*arcRadius+rNormal.width()/2,getHeight()/2+0.383f*arcRadius+35,paintYouLiang);
        canvas.drawText("30°",getWidth()/2+0.7f*arcRadius+r10.width()/2,getHeight()/2+0.7f*arcRadius+70,paint);
        canvas.restore();
    }

    private void drawScale(Canvas canvas) {
        canvas.save();
        canvas.translate(getWidth()/2,getHeight()/2);
        canvas.rotate(-120);
        for(int i=0;i<17;i++){
            if(i==2||i==5||i==8||i==11||i==14){
                canvas.drawLine(0, -(width/2-dp2px(25)), 0, -(width/2-dp2px(30)) + dp2px(10), paint);
            }else {
                canvas.drawLine(0, -(width/2-dp2px(35)), 0, -(width/2-dp2px(30)) + dp2px(10), paint);
            }
            canvas.rotate(15f);
        }
        canvas.restore();
    }

    private void drawArc(Canvas canvas) {
        canvas.save();
        canvas.translate(getWidth()/2,getHeight()/2);
        canvas.rotate(135);
        if(rect==null){
            rect = new RectF(-arcRadius,-arcRadius,arcRadius,arcRadius);
        }
        canvas.drawArc(rect,0,270,false,paint);
        canvas.restore();
    }

    public int dp2px(float dp) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp,
                getResources().getDisplayMetrics());
    }

    private int sp2px(float sp) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, sp,
                getResources().getDisplayMetrics());
    }
}
