package com.example.customview;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class TempertureView extends View {
    private static final String TAG = "huachen";
    private int width;
    private int height;
    private int arcRadius;
    Paint paint;
    Paint paintYouLiang;
    Paint tempPaint;
    Paint huanPaint;
    RectF rect;
    Rect r30;
    Rect r0;
    Rect rNormal;
    Rect r10;
    Rect rYou;
    Rect rLengKu;
    Paint centerImgPaint;
    float rotateAngle;
    int temperature = 10;
    int minTemp = -30;
    int maxTemp = 30;
    Bitmap centerImg = BitmapFactory.decodeResource(getResources(),
            R.drawable.center2);
    List<Integer> tempColors;
    public TempertureView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initTempColorsDatas();
        r30 = new Rect();
        r0 = new Rect();
        rNormal = new Rect();
        r10 = new Rect();
        rYou = new Rect();
        rLengKu = new Rect();
        centerImgPaint = new Paint();
        huanPaint = new Paint();
        huanPaint.setColor(tempColors.get(0));
        huanPaint.setStyle(Paint.Style.FILL);
        paint = new Paint();
        paint.setAntiAlias(true);
        paint.setTextSize(sp2px(15));
        paint.setStrokeWidth(3);
        paint.setColor(Color.parseColor("#B6B5B5"));
        paint.setStyle(Paint.Style.STROKE);
        paint.getTextBounds("-30°",0,"-30°".length(),r30);
        paint.getTextBounds("0°",0,"0°".length(),r0);
        paint.getTextBounds("中等",0,"中等".length(),rNormal);
        paint.getTextBounds("10°",0,"10°".length(),r10);
        paint.getTextBounds("优",0,"优".length(),rYou);
        paint.getTextBounds("冷库温度状况:",0,"冷库温度状况:".length(),rLengKu);
        paintYouLiang = new Paint();
        paintYouLiang.setAntiAlias(true);
        paintYouLiang.setTextSize(sp2px(12));
        paintYouLiang.setStrokeWidth(3);
        paintYouLiang.setColor(Color.parseColor("#B6B5B5"));
        paintYouLiang.setStyle(Paint.Style.STROKE);

        tempPaint = new Paint();
        tempPaint.setAntiAlias(true);
        tempPaint.setTextSize(sp2px(40));
        tempPaint.setColor(Color.parseColor("#E27A3F"));
        tempPaint.setStyle(Paint.Style.STROKE);
    }

    private void initTempColorsDatas() {
        tempColors = new ArrayList<>();
        tempColors.add(Color.parseColor("#46a012"));//-30
        tempColors.add(Color.parseColor("#46a012"));//-29
        tempColors.add(Color.parseColor("#46a012"));//-28
        tempColors.add(Color.parseColor("#46a012"));//-27
        tempColors.add(Color.parseColor("#46a012"));//-26
        tempColors.add(Color.parseColor("#46a012"));//-25
        tempColors.add(Color.parseColor("#46a012"));//-24
        tempColors.add(Color.parseColor("#5bab10"));//-23
        tempColors.add(Color.parseColor("#5bab10"));//-22
        tempColors.add(Color.parseColor("#84c40c"));//-21
        tempColors.add(Color.parseColor("#84c40c"));//-20
        tempColors.add(Color.parseColor("#9ed30b"));//-19
        tempColors.add(Color.parseColor("#b1de08"));//-18
        tempColors.add(Color.parseColor("#b1de08"));//-17
        tempColors.add(Color.parseColor("#c3e806"));//-16
        tempColors.add(Color.parseColor("#d6ef07"));//-15
        tempColors.add(Color.parseColor("#d6ef07"));//-14
        tempColors.add(Color.parseColor("#e7f103"));//-13
        tempColors.add(Color.parseColor("#e7f103"));//-12
        tempColors.add(Color.parseColor("#f1ef0c"));//-11
        tempColors.add(Color.parseColor("#fbef02"));//-10
        tempColors.add(Color.parseColor("#fbef02"));//-9
        tempColors.add(Color.parseColor("#fde101"));//-8
        tempColors.add(Color.parseColor("#fad50c"));//-7
        tempColors.add(Color.parseColor("#fdcb02"));//-6
        tempColors.add(Color.parseColor("#fcaa02"));//-5
        tempColors.add(Color.parseColor("#fd9901"));//-4
        tempColors.add(Color.parseColor("#fd9901"));//-3
        tempColors.add(Color.parseColor("#fd8401"));//-2
        tempColors.add(Color.parseColor("#fd7001"));//-1
        tempColors.add(Color.parseColor("#fd7001"));//0
        tempColors.add(Color.parseColor("#fd6301"));//1
        tempColors.add(Color.parseColor("#fc5601"));//2
        tempColors.add(Color.parseColor("#fb4801"));//3
        tempColors.add(Color.parseColor("#fb3701"));//4
        tempColors.add(Color.parseColor("#fd2101"));//5
        tempColors.add(Color.parseColor("#fc0f01"));//6
        tempColors.add(Color.parseColor("#f90301"));//7
        tempColors.add(Color.parseColor("#f90301"));//8
        tempColors.add(Color.parseColor("#f90301"));//9
        tempColors.add(Color.parseColor("#f90301"));//10
        tempColors.add(Color.parseColor("#f90301"));//11
        tempColors.add(Color.parseColor("#e80301"));//12
        tempColors.add(Color.parseColor("#d70301"));//13
        tempColors.add(Color.parseColor("#d70301"));//14
        tempColors.add(Color.parseColor("#c40401"));//15
        tempColors.add(Color.parseColor("#c40401"));//16
        tempColors.add(Color.parseColor("#b30602"));//17
        tempColors.add(Color.parseColor("#b30602"));//18
        tempColors.add(Color.parseColor("#97080c"));//19
        tempColors.add(Color.parseColor("#97080c"));//20
        tempColors.add(Color.parseColor("#8c0b1b"));//21
        tempColors.add(Color.parseColor("#8c0b1b"));//22
        tempColors.add(Color.parseColor("#850f2d"));//23
        tempColors.add(Color.parseColor("#850f2d"));//24
        tempColors.add(Color.parseColor("#7e103a"));//25
        tempColors.add(Color.parseColor("#7e103a"));//26
        tempColors.add(Color.parseColor("#7b1242"));//27
        tempColors.add(Color.parseColor("#7b1242"));//28
        tempColors.add(Color.parseColor("#7b1242"));//29
        tempColors.add(Color.parseColor("#7b1242"));//30
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
        drawHuan(canvas);
        drawBitmap(canvas);
        drawTemp(canvas);
        drawOthers(canvas);
    }

    private void drawHuan(Canvas canvas) {
        canvas.save();
        canvas.translate(getWidth()/2,getHeight()/2);
        canvas.rotate(45);//-30
        huanPaint.setColor(tempColors.get(0));
        canvas.drawRect(-15,275,3,325,huanPaint);
        canvas.rotate(4.5f);//-29
        huanPaint.setColor(tempColors.get(1));
        canvas.drawRect(-15,275,10,325,huanPaint);
        canvas.rotate(4.5f);//-28
        huanPaint.setColor(tempColors.get(2));
        canvas.drawRect(-15,275,10,325,huanPaint);
        canvas.rotate(4.5f);//-27
        huanPaint.setColor(tempColors.get(3));
        canvas.drawRect(-15,275,10,325,huanPaint);
        canvas.rotate(4.5f);//-26
        huanPaint.setColor(tempColors.get(4));
        canvas.drawRect(-15,275,10,325,huanPaint);
        canvas.rotate(4.5f);//-25
        huanPaint.setColor(tempColors.get(5));
        canvas.drawRect(-15,275,10,325,huanPaint);
        canvas.rotate(4.5f);//-24
        huanPaint.setColor(tempColors.get(6));
        canvas.drawRect(-15,275,10,325,huanPaint);
        canvas.rotate(4.5f);//-23
        huanPaint.setColor(tempColors.get(7));
        canvas.drawRect(-15,275,10,325,huanPaint);
        canvas.rotate(4.5f);//-22
        huanPaint.setColor(tempColors.get(8));
        canvas.drawRect(-15,275,10,325,huanPaint);
        canvas.rotate(4.5f);//-21
        huanPaint.setColor(tempColors.get(9));
        canvas.drawRect(-15,275,10,325,huanPaint);
        canvas.rotate(4.5f);//-20
        huanPaint.setColor(tempColors.get(10));
        canvas.drawRect(-15,275,10,325,huanPaint);
        canvas.rotate(4.5f);//-19
        huanPaint.setColor(tempColors.get(11));
        canvas.drawRect(-15,275,10,325,huanPaint);
        canvas.rotate(4.5f);//-18
        huanPaint.setColor(tempColors.get(12));
        canvas.drawRect(-15,275,10,325,huanPaint);
        canvas.rotate(4.5f);//-17
        huanPaint.setColor(tempColors.get(13));
        canvas.drawRect(-15,275,10,325,huanPaint);
        canvas.rotate(4.5f);//-16
        huanPaint.setColor(tempColors.get(14));
        canvas.drawRect(-15,275,10,325,huanPaint);
        canvas.rotate(4.5f);//-15
        huanPaint.setColor(tempColors.get(15));
        canvas.drawRect(-15,275,10,325,huanPaint);
        canvas.rotate(4.5f);//-14
        huanPaint.setColor(tempColors.get(16));
        canvas.drawRect(-15,275,10,325,huanPaint);
        canvas.rotate(4.5f);//-13
        huanPaint.setColor(tempColors.get(17));
        canvas.drawRect(-15,275,10,325,huanPaint);
        canvas.rotate(4.5f);//-12
        huanPaint.setColor(tempColors.get(18));
        canvas.drawRect(-15,275,10,325,huanPaint);
        canvas.rotate(4.5f);//-11
        huanPaint.setColor(tempColors.get(19));
        canvas.drawRect(-15,275,10,325,huanPaint);
        canvas.rotate(4.5f);//-10
        huanPaint.setColor(tempColors.get(20));
        canvas.drawRect(-15,275,10,325,huanPaint);
        canvas.rotate(4.5f);//-9
        huanPaint.setColor(tempColors.get(21));
        canvas.drawRect(-15,275,10,325,huanPaint);
        canvas.rotate(4.5f);//-8
        huanPaint.setColor(tempColors.get(22));
        canvas.drawRect(-15,275,10,325,huanPaint);
        canvas.rotate(4.5f);//-7
        huanPaint.setColor(tempColors.get(23));
        canvas.drawRect(-15,275,10,325,huanPaint);
        canvas.rotate(4.5f);//-6
        huanPaint.setColor(tempColors.get(24));
        canvas.drawRect(-15,275,10,325,huanPaint);
        canvas.rotate(4.5f);//-5
        huanPaint.setColor(tempColors.get(25));
        canvas.drawRect(-15,275,10,325,huanPaint);
        canvas.rotate(4.5f);//-4
        huanPaint.setColor(tempColors.get(26));
        canvas.drawRect(-15,275,10,325,huanPaint);
        canvas.rotate(4.5f);//-3
        huanPaint.setColor(tempColors.get(27));
        canvas.drawRect(-15,275,10,325,huanPaint);
        canvas.rotate(4.5f);//-2
        huanPaint.setColor(tempColors.get(28));
        canvas.drawRect(-15,275,10,325,huanPaint);
        canvas.rotate(4.5f);//-1
        huanPaint.setColor(tempColors.get(29));
        canvas.drawRect(-15,275,10,325,huanPaint);
        canvas.rotate(4.5f);//0
        huanPaint.setColor(tempColors.get(30));
        canvas.drawRect(-15,275,10,325,huanPaint);
        canvas.rotate(4.5f);//1
        huanPaint.setColor(tempColors.get(31));
        canvas.drawRect(-15,275,10,325,huanPaint);
        canvas.rotate(4.5f);//2
        huanPaint.setColor(tempColors.get(32));
        canvas.drawRect(-15,275,10,325,huanPaint);
        canvas.rotate(4.5f);//3
        huanPaint.setColor(tempColors.get(33));
        canvas.drawRect(-15,275,10,325,huanPaint);
        canvas.rotate(4.5f);//4
        huanPaint.setColor(tempColors.get(34));
        canvas.drawRect(-15,275,10,325,huanPaint);
        canvas.rotate(4.5f);//5
        huanPaint.setColor(tempColors.get(35));
        canvas.drawRect(-15,275,10,325,huanPaint);
        canvas.rotate(4.5f);//6
        huanPaint.setColor(tempColors.get(36));
        canvas.drawRect(-15,275,10,325,huanPaint);
        canvas.rotate(4.5f);//7
        huanPaint.setColor(tempColors.get(37));
        canvas.drawRect(-15,275,10,325,huanPaint);
        canvas.rotate(4.5f);//8
        huanPaint.setColor(tempColors.get(38));
        canvas.drawRect(-15,275,10,325,huanPaint);
        canvas.rotate(4.5f);//9
        huanPaint.setColor(tempColors.get(39));
        canvas.drawRect(-15,275,10,325,huanPaint);
        canvas.rotate(4.5f);//10
        huanPaint.setColor(tempColors.get(40));
        canvas.drawRect(-15,275,10,325,huanPaint);
        canvas.rotate(4.5f);//11
        huanPaint.setColor(tempColors.get(41));
        canvas.drawRect(-15,275,10,325,huanPaint);
        canvas.rotate(4.5f);//12
        huanPaint.setColor(tempColors.get(42));
        canvas.drawRect(-15,275,10,325,huanPaint);
        canvas.rotate(4.5f);//13
        huanPaint.setColor(tempColors.get(43));
        canvas.drawRect(-15,275,10,325,huanPaint);
        canvas.rotate(4.5f);//14
        huanPaint.setColor(tempColors.get(44));
        canvas.drawRect(-15,275,10,325,huanPaint);
        canvas.rotate(4.5f);//15
        huanPaint.setColor(tempColors.get(45));
        canvas.drawRect(-15,275,10,325,huanPaint);
        canvas.rotate(4.5f);//16
        huanPaint.setColor(tempColors.get(46));
        canvas.drawRect(-15,275,10,325,huanPaint);
        canvas.rotate(4.5f);//17
        huanPaint.setColor(tempColors.get(47));
        canvas.drawRect(-15,275,10,325,huanPaint);
        canvas.rotate(4.5f);//18
        huanPaint.setColor(tempColors.get(48));
        canvas.drawRect(-15,275,10,325,huanPaint);
        canvas.rotate(4.5f);//19
        huanPaint.setColor(tempColors.get(49));
        canvas.drawRect(-15,275,10,325,huanPaint);
        canvas.rotate(4.5f);//20
        huanPaint.setColor(tempColors.get(50));
        canvas.drawRect(-15,275,10,325,huanPaint);
        canvas.rotate(4.5f);//21
        huanPaint.setColor(tempColors.get(51));
        canvas.drawRect(-15,275,10,325,huanPaint);
        canvas.rotate(4.5f);//22
        huanPaint.setColor(tempColors.get(52));
        canvas.drawRect(-15,275,10,325,huanPaint);
        canvas.rotate(4.5f);//23
        huanPaint.setColor(tempColors.get(53));
        canvas.drawRect(-15,275,10,325,huanPaint);
        canvas.rotate(4.5f);//24
        huanPaint.setColor(tempColors.get(54));
        canvas.drawRect(-15,275,10,325,huanPaint);
        canvas.rotate(4.5f);//25
        huanPaint.setColor(tempColors.get(55));
        canvas.drawRect(-15,275,10,325,huanPaint);
        canvas.rotate(4.5f);//26
        huanPaint.setColor(tempColors.get(56));
        canvas.drawRect(-15,275,10,325,huanPaint);
        canvas.rotate(4.5f);//27
        huanPaint.setColor(tempColors.get(57));
        canvas.drawRect(-15,275,10,325,huanPaint);
        canvas.rotate(4.5f);//28
        huanPaint.setColor(tempColors.get(58));
        canvas.drawRect(-15,275,10,325,huanPaint);
        canvas.rotate(4.5f);//29
        huanPaint.setColor(tempColors.get(59));
        canvas.drawRect(-15,275,10,325,huanPaint);
        canvas.rotate(4.5f);//30
        huanPaint.setColor(tempColors.get(60));
        canvas.drawRect(0,275,10,325,huanPaint);
        canvas.restore();
    }

    private void drawOthers(Canvas canvas) {
        canvas.save();
        canvas.drawText("冷库温度状况:",getWidth()/2-centerImg.getWidth()/2,getHeight()/2+0.7f*arcRadius+70,paint);
        String str =null;
        if(temperature<0){
            if(temperature<minTemp){
                temperature=minTemp;
            }
            int abs = Math.abs(temperature);
            huanPaint.setColor(tempColors.get(maxTemp-abs));
            if(temperature<=-20){
                str = "优";
            }else if(temperature<=-10){
                str = "良";
            }else {
                str = "中等";
            }
        }else if(temperature==0){
            huanPaint.setColor(tempColors.get(maxTemp));
            str = "中等";
        }else if(temperature>0){
            if(temperature>maxTemp){
                temperature=maxTemp;
            }
            if(temperature>=20){
                str = "危险";
            }else if(temperature>=10){
                str = "过热";
            }else {
                str = "中等";
            }
            huanPaint.setColor(tempColors.get(maxTemp+temperature));
        }
        canvas.drawRect(getWidth()/2-centerImg.getWidth()/2+rLengKu.width()+20,getHeight()/2+0.7f*arcRadius+60-rLengKu.height()/3*2,getWidth()/2-centerImg.getWidth()/2+rLengKu.width()+100,getHeight()/2+0.7f*arcRadius+60+rLengKu.height()/3,huanPaint);

        canvas.drawText(str,getWidth()/2-centerImg.getWidth()/2+rLengKu.width()+120,getHeight()/2+0.7f*arcRadius+70,paint);
        canvas.restore();
    }

    private void drawTemp(Canvas canvas) {
        canvas.save();
        canvas.translate(getWidth() / 2, getHeight() / 2);

        float tempWidth = tempPaint.measureText(temperature + "");
        float tempHeight = (tempPaint.ascent() + tempPaint.descent()) / 2;
        if(temperature<0){
            if(temperature<minTemp){
                temperature=minTemp;
            }
            int abs = Math.abs(temperature);
            tempPaint.setColor(tempColors.get(maxTemp-abs));
        }else if(temperature==0){
            tempPaint.setColor(tempColors.get(maxTemp));
        }else if(temperature>0){
            if(temperature>maxTemp){
                temperature=maxTemp;
            }
            tempPaint.setColor(tempColors.get(maxTemp+temperature));
        }
        canvas.drawText(temperature + "°", -tempWidth / 2 - dp2px(5), -tempHeight, tempPaint);
        canvas.rotate(rotateAngle);
        tempPaint.setStyle(Paint.Style.FILL);
        canvas.drawCircle(0-centerImg.getWidth()/2+0.7f*125,0+centerImg.getWidth()/2-0.7f*125,10,tempPaint);
        canvas.restore();
    }

    private void caculateAngle(int currentTemp){
        if(currentTemp<0){
            if(currentTemp<minTemp){
                rotateAngle=0;
            }else {
                rotateAngle = (currentTemp-minTemp)*4.5f;
            }
        }else if(currentTemp==0){
            rotateAngle=135;
        }else if(currentTemp>0){
            if(currentTemp>maxTemp){
                rotateAngle=270;
            }else {
                rotateAngle = 270-(maxTemp-currentTemp)*4.5f;
            }
        }
    }

    private void drawBitmap(Canvas canvas) {
        canvas.save();
        Matrix matrix = new Matrix();
        matrix.setTranslate((getWidth()-centerImg.getWidth()) / 2, (getHeight()-centerImg.getHeight()) / 2);
        caculateAngle(temperature);
        matrix.postRotate(-22.5f+rotateAngle,getWidth()/2,getHeight()/2);
        canvas.drawBitmap(centerImg,matrix,centerImgPaint);
        canvas.restore();
    }


    private void draw2Line(Canvas canvas) {
        canvas.save();
        canvas.translate(getWidth()/2,getHeight()/2);
        canvas.rotate(-135);
        canvas.drawLine(0, -(getHeight()/2)+202, 0, -(getHeight()/2)+102, paint);
        canvas.rotate(-90);
        canvas.drawLine(0, -(getHeight()/2)+202, 0, -(getHeight()/2)+102, paint);
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
        canvas.drawText("0°",getWidth()/2-r0.width()/2,getHeight()/2-arcRadius-r0.height()-20,paint);
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
