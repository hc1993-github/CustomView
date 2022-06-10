package com.example.customview;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Shader;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class TempertureView extends View {
    Paint paint;
    Paint paintScale;
    Paint paintArc;
    Paint paintText;
    Paint paintCircle;
    Paint paint2Line;
    Paint paintHuan;
    Paint paintBitmap;
    Paint paintTemp;
    Paint paintOthers;
    RectF rect;
    Rect r30;
    Rect r0;
    Rect rNormal;
    Rect r10;
    Rect rYou;
    Rect rLengKu;
    float rotateAngle;
    int width;
    int arcRadius;
    int temperature = 0;
    int minTemp = -30;
    int maxTemp = 30;
    int middleTemp = 0;
    int precent2Temp = 20;
    int precent02Temp = -20;
    int precent1Temp = 10;
    int precent01Temp = -10;
    int imgWidth;
    int imgHeight;
    int mWidth;
    int mHeight;
    int mWidthHalf;
    int mHeightHalf;
    Bitmap centerImg;
    List<Integer> tempColors;
    List<Integer> paintColors;
    String stringDegree = "°";
    String string030;
    String string020;
    String string010;
    String string0;
    String string10;
    String string20;
    String string30;
    String stringZD = "中等";
    String stringY = "优";
    String stringL = "良";
    String stringGR = "过热";
    String stringWX = "危险";
    String stringLKWDZK = "冷库温度状况:";
    String stringEmpty = "";
    String stringCurrentState;
    LinearGradient linearGradient;
    Matrix matrix;

    public TempertureView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initOtherDatas();
        initTempColorsDatas();
        initPaints();
    }

    private void initOtherDatas() {
        centerImg = BitmapFactory.decodeResource(getResources(), R.drawable.center);
        imgWidth = centerImg.getWidth();
        imgHeight = centerImg.getHeight();
        matrix = new Matrix();

        string030 = minTemp + stringDegree;
        string020 = precent02Temp + stringDegree;
        string010 = precent01Temp + stringDegree;
        string0 = middleTemp + stringDegree;
        string10 = precent1Temp + stringDegree;
        string20 = precent2Temp + stringDegree;
        string30 = maxTemp + stringDegree;
    }

    private void initPaints() {
        paint = new Paint();
        paint.setAntiAlias(true);
        paint.setTextSize(sp2px(15));
        paint.setStrokeWidth(3);
        paint.setColor(paintColors.get(0));
        paint.setStyle(Paint.Style.FILL);

        paintScale = new Paint();
        paintScale.setAntiAlias(true);
        paintScale.setTextSize(sp2px(15));
        paintScale.setStrokeWidth(3);
        paintScale.setColor(paintColors.get(1));
        paintScale.setStyle(Paint.Style.STROKE);

        paintArc = new Paint();
        paintArc.setAntiAlias(true);
        paintArc.setTextSize(sp2px(15));
        paintArc.setStrokeWidth(3);
        paintArc.setColor(paintColors.get(1));
        paintArc.setStyle(Paint.Style.STROKE);

        paintText = new Paint();
        paintText.setAntiAlias(true);
        paintText.setTextSize(sp2px(12));
        paintText.setStrokeWidth(3);
        paintText.setColor(paintColors.get(2));
        paintText.setStyle(Paint.Style.FILL);

        paintCircle = new Paint();
        paintCircle.setStyle(Paint.Style.FILL);

        paint2Line = new Paint();
        paint2Line.setAntiAlias(true);
        paint2Line.setTextSize(sp2px(15));
        paint2Line.setStrokeWidth(3);
        paint2Line.setColor(paintColors.get(3));
        paint2Line.setStyle(Paint.Style.STROKE);

        paintHuan = new Paint();
        paintHuan.setColor(tempColors.get(0));
        paintHuan.setStyle(Paint.Style.FILL);

        paintBitmap = new Paint();

        paintTemp = new Paint();
        paintTemp.setAntiAlias(true);
        paintTemp.setTextSize(sp2px(40));
        paintTemp.setColor(paintColors.get(4));
        paintTemp.setStyle(Paint.Style.FILL);

        paintOthers = new Paint();
        paintOthers.setAntiAlias(true);
        paintOthers.setTextSize(sp2px(15));
        paintOthers.setStrokeWidth(3);
        paintOthers.setColor(paintColors.get(5));
        paintOthers.setStyle(Paint.Style.FILL);

        r30 = new Rect();
        r0 = new Rect();
        rNormal = new Rect();
        r10 = new Rect();
        rYou = new Rect();
        rLengKu = new Rect();
        paintScale.getTextBounds(string030, 0, string030.length(), r30);
        paintScale.getTextBounds(string0, 0, string0.length(), r0);
        paintScale.getTextBounds(stringZD, 0, stringZD.length(), rNormal);
        paintScale.getTextBounds(string10, 0, string10.length(), r10);
        paintScale.getTextBounds(stringY, 0, stringY.length(), rYou);
        paintScale.getTextBounds(stringLKWDZK, 0, stringLKWDZK.length(), rLengKu);
    }

    private void initTempColorsDatas() {
        paintColors = new ArrayList<>();
        paintColors.add(Color.parseColor("#6f6f6f"));
        paintColors.add(Color.parseColor("#ebebeb"));
        paintColors.add(Color.parseColor("#cccccc"));
        paintColors.add(Color.parseColor("#f4f3f3"));
        paintColors.add(Color.parseColor("#E27A3F"));
        paintColors.add(Color.parseColor("#b3b3b3"));
        paintColors.add(Color.parseColor("#FFFFFFFF"));
        paintColors.add(Color.parseColor("#e7e7e7"));
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
        width = Math.min(h, w);
        arcRadius = width / 2 - dp2px(40);
        mWidth = getWidth();
        mHeight = getHeight();
        mWidthHalf = mWidth / 2;
        mHeightHalf = mHeight / 2;
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

    private void drawHuan(Canvas canvas) { //画渐变色圈
        canvas.save();
        int leftTopX = (int) (0.036f * imgWidth);
        int leftTopY = (int) (0.655f * imgWidth);
        int rightBottomX = (int) (0.024f * imgWidth);
        int rightBottomY = (int) (0.774f * imgWidth);
        canvas.translate(mWidthHalf, mHeightHalf);

        canvas.rotate(45f);
        paintHuan.setColor(tempColors.get(0));
        canvas.drawRect(-leftTopX - 10, leftTopY, 0.007f * imgWidth, rightBottomY, paintHuan);

        for (int j = 1; j < 60; j++) {
            canvas.rotate(4.5f);
            paintHuan.setColor(tempColors.get(j));
            canvas.drawRect(-leftTopX - 10, leftTopY, rightBottomX, rightBottomY, paintHuan);
        }

        canvas.rotate(4.5f);
        paintHuan.setColor(tempColors.get(60));
        canvas.drawRect(0, leftTopY, rightBottomX, rightBottomY, paintHuan);

        canvas.restore();
    }

    private void drawOthers(Canvas canvas) { //画中间温度状况
        canvas.save();
        canvas.drawText(stringLKWDZK, mWidthHalf - 0.5f * imgWidth, mHeightHalf + 0.7f * arcRadius + 0.146f * imgWidth, paintOthers);
        if (temperature < middleTemp) {
            if (temperature < minTemp) {
                temperature = minTemp;
            }
            int abs = Math.abs(temperature);
            paintOthers.setColor(tempColors.get((int) ((maxTemp - abs) * (30f / maxTemp))));
            if (temperature <= precent02Temp) {
                stringCurrentState = stringY;
            } else if (temperature <= precent01Temp) {
                stringCurrentState = stringL;
            } else {
                stringCurrentState = stringZD;
            }
        } else if (temperature == middleTemp) {
            paintOthers.setColor(tempColors.get(30));
            stringCurrentState = stringZD;
        } else {
            if (temperature > maxTemp) {
                temperature = maxTemp;
            }
            if (temperature >= precent2Temp) {
                stringCurrentState = stringWX;
            } else if (temperature >= precent1Temp) {
                stringCurrentState = stringGR;
            } else {
                stringCurrentState = stringZD;
            }
            paintOthers.setColor(tempColors.get(60 - (int) ((maxTemp - temperature) * (30f / maxTemp))));
        }
        canvas.drawRect(mWidthHalf - 0.458f * imgWidth + rLengKu.width(), mHeightHalf + 0.7f * arcRadius + 0.125f * imgWidth - 0.667f * rLengKu.height(), mWidthHalf - 0.29f * imgWidth + rLengKu.width(), mHeightHalf + 0.7f * arcRadius + 0.125f * imgWidth + 0.333f * rLengKu.height(), paintOthers);
        paintOthers.setColor(paintColors.get(5));
        canvas.drawText(stringCurrentState, mWidthHalf - 0.25f * imgWidth + rLengKu.width(), mHeightHalf + 0.7f * arcRadius + 0.146f * imgWidth, paintOthers);
        canvas.restore();
    }

    private void drawTemp(Canvas canvas) { //画中间温度和小圆点
        canvas.save();
        canvas.translate(mWidthHalf, mHeightHalf);
        float tempWidth = paintTemp.measureText(temperature + stringEmpty);
        float tempHeight = 0.5f * (paintTemp.ascent() + paintTemp.descent());
        if (temperature < middleTemp) {
            if (temperature < minTemp) {
                temperature = minTemp;
            }
            int abs = Math.abs(temperature);
            paintTemp.setColor(tempColors.get((int) ((maxTemp - abs) * (30f / maxTemp))));
        } else if (temperature == middleTemp) {
            paintTemp.setColor(tempColors.get(30));
        } else {
            if (temperature > maxTemp) {
                temperature = maxTemp;
            }
            paintTemp.setColor(tempColors.get(60 - (int) ((maxTemp - temperature) * (30f / maxTemp))));
        }
        canvas.drawText(temperature + stringDegree, -0.5f * tempWidth - dp2px(5), -tempHeight, paintTemp);
        canvas.rotate(rotateAngle);
        canvas.drawCircle(-0.281f * imgWidth, 0.281f * imgWidth, 0.021f * imgWidth, paintTemp);
        canvas.restore();
    }

    private void caculateAngle(int currentTemp) { //根据温度计算角度
        if (currentTemp < middleTemp) {
            if (currentTemp < minTemp) {
                rotateAngle = middleTemp;
            } else {
                rotateAngle = (currentTemp - minTemp) * (135f / maxTemp);
            }
        } else if (currentTemp == middleTemp) {
            rotateAngle = 135;
        } else {
            if (currentTemp > maxTemp) {
                rotateAngle = 270;
            } else {
                rotateAngle = 270 - (maxTemp - currentTemp) * (135f / maxTemp);
            }
        }
    }

    private void drawBitmap(Canvas canvas) { //画中间圆
        canvas.save();
        matrix.setTranslate(0.5f * (mWidth - imgWidth), 0.5f * (mHeight - imgHeight));
        caculateAngle(temperature);
        matrix.postRotate(rotateAngle - 22.5f, mWidthHalf, mHeightHalf);
        canvas.drawBitmap(centerImg, matrix, paintBitmap);
        canvas.restore();
    }

    private void draw2Line(Canvas canvas) { //画两边封闭线段
        canvas.save();
        canvas.translate(mWidthHalf, mHeightHalf);
        canvas.rotate(-135f);
        canvas.drawLine(0, -mHeightHalf + 0.481f * imgWidth, 0, -mHeightHalf + 0.243f * imgWidth, paint2Line);
        canvas.rotate(-90f);
        canvas.drawLine(0, -mHeightHalf + 0.481f * imgWidth, 0, -mHeightHalf + 0.243f * imgWidth, paint2Line);
        canvas.restore();
    }

    private void drawCircle(Canvas canvas) { //画中间阴影圆
        canvas.save();
        canvas.translate(mWidthHalf, mHeightHalf);
        if (linearGradient == null) {
            linearGradient = new LinearGradient(mWidthHalf, mHeightHalf - arcRadius + 0.238f * imgWidth, mWidthHalf, mHeightHalf + arcRadius - 0.238f * imgWidth, paintColors.get(6), paintColors.get(7), Shader.TileMode.MIRROR);
            paintCircle.setShader(linearGradient);
        }
        canvas.drawCircle(0, 0, arcRadius - 0.262f * imgWidth, paintCircle);
        canvas.restore();
    }

    private void drawText(Canvas canvas) { //画温度刻度
        canvas.save();
        canvas.drawText(string030, mWidthHalf - 0.7f * arcRadius - 0.19f * imgWidth, mHeightHalf + 0.7f * arcRadius + 0.167f * imgWidth, paint);
        canvas.drawText(stringY, mWidthHalf - 0.924f * arcRadius - 0.143f * imgWidth, mHeightHalf + 0.383f * arcRadius + 0.083f * imgWidth, paintText);
        canvas.drawText(string020, mWidthHalf - 1.0f * arcRadius - 0.083f * imgWidth - r30.width(), mHeightHalf + 0.5f * r30.height(), paint);
        canvas.drawText(stringL, mWidthHalf - 0.924f * arcRadius - 0.143f * imgWidth, mHeightHalf - 0.383f * arcRadius - 0.048f * imgWidth, paintText);
        canvas.drawText(string010, mWidthHalf - 0.7f * arcRadius - 0.19f * imgWidth, mHeightHalf - 0.7f * arcRadius - 0.095f * imgWidth, paint);
        canvas.drawText(stringZD, mWidthHalf - 0.383f * arcRadius - rNormal.width(), mHeightHalf - 0.924f * arcRadius - 0.07f * imgWidth, paintText);
        canvas.drawText(string0, mWidthHalf - 0.5f * r0.width(), mHeightHalf - arcRadius - r0.height() - 0.048f * imgWidth, paint);
        canvas.drawText(stringZD, mWidthHalf + 0.383f * arcRadius, mHeightHalf - 0.924f * arcRadius - 0.07f * imgWidth, paintText);
        canvas.drawText(string10, mWidthHalf + 0.7f * arcRadius + 0.5f * r10.width(), mHeightHalf - 0.7f * arcRadius - 0.095f * imgWidth, paint);
        canvas.drawText(stringGR, mWidthHalf + 0.924f * arcRadius + 0.5f * rNormal.width(), mHeightHalf - 0.383f * arcRadius - 0.048f * imgWidth, paintText);
        canvas.drawText(string20, mWidthHalf + arcRadius + 0.083f * imgWidth, mHeightHalf + 0.5f * r30.height(), paint);
        canvas.drawText(stringWX, mWidthHalf + 0.924f * arcRadius + 0.5f * rNormal.width(), mHeightHalf + 0.383f * arcRadius + 0.083f * imgWidth, paintText);
        canvas.drawText(string30, mWidthHalf + 0.7f * arcRadius + 0.5f * r10.width(), mHeightHalf + 0.7f * arcRadius + 0.167f * imgWidth, paint);
        canvas.restore();
    }

    private void drawScale(Canvas canvas) { //画刻度
        canvas.save();
        canvas.translate(mWidthHalf, mHeightHalf);
        canvas.rotate(-120f);
        for (int i = 0; i < 17; i++) {
            if (i == 2 || i == 5 || i == 8 || i == 11 || i == 14) {
                canvas.drawLine(0, -arcRadius, 0, -arcRadius - dp2px(10), paintScale);
            } else {
                canvas.drawLine(0, -arcRadius, 0, -arcRadius - dp2px(5), paintScale);
            }
            canvas.rotate(15f);
        }
        canvas.restore();
    }

    private void drawArc(Canvas canvas) { //画最外圈弧
        canvas.save();
        canvas.translate(mWidthHalf, mHeightHalf);
        canvas.rotate(135f);
        if (rect == null) {
            rect = new RectF(-arcRadius, -arcRadius, arcRadius, arcRadius);
        }
        canvas.drawArc(rect, 0, 270f, false, paintArc);
        canvas.restore();
    }

    private int dp2px(float dp) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, getResources().getDisplayMetrics());
    }

    private int sp2px(float sp) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, sp, getResources().getDisplayMetrics());
    }

    public void setTemperature(int temp) { //设置当前温度
        temperature = temp;
        postInvalidate();
    }

    public void setPrecentTemp(int[] temps) { //设置最大最小左2左1右2右1温度
        if (temps.length < 6) {
            throw new IllegalArgumentException("请设置6个温度刻度");
        }
        minTemp = temps[0];
        maxTemp = temps[1];
        precent2Temp = temps[2];
        precent02Temp = temps[3];
        precent1Temp = temps[4];
        precent01Temp = temps[5];
        string030 = minTemp + stringDegree;
        string30 = maxTemp + stringDegree;
        string20 = precent2Temp + stringDegree;
        string020 = precent02Temp + stringDegree;
        string10 = precent1Temp + stringDegree;
        string010 = precent01Temp + stringDegree;
        postInvalidate();
    }

}
