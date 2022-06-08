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
    private static final String TAG = "huachen";
    private int width;
    private int height;
    private int arcRadius;
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
    int temperature = 0;
    int minTemp = -30;
    int maxTemp = 30;
    int middleTemp = 0;
    int precent2Temp = 20;
    int precent02Temp = -20;
    int precent1Temp = 10;
    int precent01Temp = -10;
    Bitmap centerImg = BitmapFactory.decodeResource(getResources(),R.drawable.center);
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
    LinearGradient linearGradient;
    Matrix matrix;
    public TempertureView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        matrix = new Matrix();
        ininStrings();
        initTempColorsDatas();
        initPaints();
        initRects();
    }

    private void ininStrings() {
        string030 = minTemp+stringDegree;
        string020 = precent02Temp+stringDegree;
        string010 = precent01Temp+stringDegree;
        string0 = middleTemp+stringDegree;
        string10 = precent1Temp+stringDegree;
        string20 = precent2Temp+stringDegree;
        string30 = maxTemp+stringDegree;
    }

    private void initRects() {
        r30 = new Rect();
        r0 = new Rect();
        rNormal = new Rect();
        r10 = new Rect();
        rYou = new Rect();
        rLengKu = new Rect();
        paintScale.getTextBounds(string030,0,string030.length(),r30);
        paintScale.getTextBounds(string0,0,string0.length(),r0);
        paintScale.getTextBounds(stringZD,0,stringZD.length(),rNormal);
        paintScale.getTextBounds(string10,0,string10.length(),r10);
        paintScale.getTextBounds(stringY,0,stringY.length(),rYou);
        paintScale.getTextBounds(stringLKWDZK,0,stringLKWDZK.length(),rLengKu);
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
    }

    private void initTempColorsDatas() {
        paintColors = new ArrayList<>();
        paintColors.add(Color.parseColor("#6f6f6f"));
        paintColors.add(Color.parseColor("#ebebeb"));
        paintColors.add(Color.parseColor("#cccccc"));
        paintColors.add(Color.parseColor("#f4f3f3"));
        paintColors.add(Color.parseColor("#E27A3F"));
        paintColors.add(Color.parseColor("#b3b3b3"));
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

    private void drawHuan(Canvas canvas) { //画渐变色圈
        canvas.save();
        canvas.translate(getWidth()/2,getHeight()/2);
        canvas.rotate(45);//-30
        paintHuan.setColor(tempColors.get(0));
        canvas.drawRect(-(centerImg.getWidth()/2*15/210)-10,(centerImg.getWidth()/2*275/210),(centerImg.getWidth()/2*3/210),(centerImg.getWidth()/2*325/210),paintHuan);
        canvas.rotate(4.5f);//-29
        paintHuan.setColor(tempColors.get(1));
        canvas.drawRect(-(centerImg.getWidth()/2*15/210)-10,(centerImg.getWidth()/2*275/210),(centerImg.getWidth()/2*10/210),(centerImg.getWidth()/2*325/210),paintHuan);
        canvas.rotate(4.5f);//-28
        paintHuan.setColor(tempColors.get(2));
        canvas.drawRect(-(centerImg.getWidth()/2*15/210)-10,(centerImg.getWidth()/2*275/210),(centerImg.getWidth()/2*10/210),(centerImg.getWidth()/2*325/210),paintHuan);
        canvas.rotate(4.5f);//-27
        paintHuan.setColor(tempColors.get(3));
        canvas.drawRect(-(centerImg.getWidth()/2*15/210)-10,(centerImg.getWidth()/2*275/210),(centerImg.getWidth()/2*10/210),(centerImg.getWidth()/2*325/210),paintHuan);
        canvas.rotate(4.5f);//-26
        paintHuan.setColor(tempColors.get(4));
        canvas.drawRect(-(centerImg.getWidth()/2*15/210)-10,(centerImg.getWidth()/2*275/210),(centerImg.getWidth()/2*10/210),(centerImg.getWidth()/2*325/210),paintHuan);
        canvas.rotate(4.5f);//-25
        paintHuan.setColor(tempColors.get(5));
        canvas.drawRect(-(centerImg.getWidth()/2*15/210)-10,(centerImg.getWidth()/2*275/210),(centerImg.getWidth()/2*10/210),(centerImg.getWidth()/2*325/210),paintHuan);
        canvas.rotate(4.5f);//-24
        paintHuan.setColor(tempColors.get(6));
        canvas.drawRect(-(centerImg.getWidth()/2*15/210)-10,(centerImg.getWidth()/2*275/210),(centerImg.getWidth()/2*10/210),(centerImg.getWidth()/2*325/210),paintHuan);
        canvas.rotate(4.5f);//-23
        paintHuan.setColor(tempColors.get(7));
        canvas.drawRect(-(centerImg.getWidth()/2*15/210)-10,(centerImg.getWidth()/2*275/210),(centerImg.getWidth()/2*10/210),(centerImg.getWidth()/2*325/210),paintHuan);
        canvas.rotate(4.5f);//-22
        paintHuan.setColor(tempColors.get(8));
        canvas.drawRect(-(centerImg.getWidth()/2*15/210)-10,(centerImg.getWidth()/2*275/210),(centerImg.getWidth()/2*10/210),(centerImg.getWidth()/2*325/210),paintHuan);
        canvas.rotate(4.5f);//-21
        paintHuan.setColor(tempColors.get(9));
        canvas.drawRect(-(centerImg.getWidth()/2*15/210)-10,(centerImg.getWidth()/2*275/210),(centerImg.getWidth()/2*10/210),(centerImg.getWidth()/2*325/210),paintHuan);
        canvas.rotate(4.5f);//-20
        paintHuan.setColor(tempColors.get(10));
        canvas.drawRect(-(centerImg.getWidth()/2*15/210)-10,(centerImg.getWidth()/2*275/210),(centerImg.getWidth()/2*10/210),(centerImg.getWidth()/2*325/210),paintHuan);
        canvas.rotate(4.5f);//-19
        paintHuan.setColor(tempColors.get(11));
        canvas.drawRect(-(centerImg.getWidth()/2*15/210)-10,(centerImg.getWidth()/2*275/210),(centerImg.getWidth()/2*10/210),(centerImg.getWidth()/2*325/210),paintHuan);
        canvas.rotate(4.5f);//-18
        paintHuan.setColor(tempColors.get(12));
        canvas.drawRect(-(centerImg.getWidth()/2*15/210)-10,(centerImg.getWidth()/2*275/210),(centerImg.getWidth()/2*10/210),(centerImg.getWidth()/2*325/210),paintHuan);
        canvas.rotate(4.5f);//-17
        paintHuan.setColor(tempColors.get(13));
        canvas.drawRect(-(centerImg.getWidth()/2*15/210)-10,(centerImg.getWidth()/2*275/210),(centerImg.getWidth()/2*10/210),(centerImg.getWidth()/2*325/210),paintHuan);
        canvas.rotate(4.5f);//-16
        paintHuan.setColor(tempColors.get(14));
        canvas.drawRect(-(centerImg.getWidth()/2*15/210)-10,(centerImg.getWidth()/2*275/210),(centerImg.getWidth()/2*10/210),(centerImg.getWidth()/2*325/210),paintHuan);
        canvas.rotate(4.5f);//-15
        paintHuan.setColor(tempColors.get(15));
        canvas.drawRect(-(centerImg.getWidth()/2*15/210)-10,(centerImg.getWidth()/2*275/210),(centerImg.getWidth()/2*10/210),(centerImg.getWidth()/2*325/210),paintHuan);
        canvas.rotate(4.5f);//-14
        paintHuan.setColor(tempColors.get(16));
        canvas.drawRect(-(centerImg.getWidth()/2*15/210)-10,(centerImg.getWidth()/2*275/210),(centerImg.getWidth()/2*10/210),(centerImg.getWidth()/2*325/210),paintHuan);
        canvas.rotate(4.5f);//-13
        paintHuan.setColor(tempColors.get(17));
        canvas.drawRect(-(centerImg.getWidth()/2*15/210)-10,(centerImg.getWidth()/2*275/210),(centerImg.getWidth()/2*10/210),(centerImg.getWidth()/2*325/210),paintHuan);
        canvas.rotate(4.5f);//-12
        paintHuan.setColor(tempColors.get(18));
        canvas.drawRect(-(centerImg.getWidth()/2*15/210)-10,(centerImg.getWidth()/2*275/210),(centerImg.getWidth()/2*10/210),(centerImg.getWidth()/2*325/210),paintHuan);
        canvas.rotate(4.5f);//-11
        paintHuan.setColor(tempColors.get(19));
        canvas.drawRect(-(centerImg.getWidth()/2*15/210)-10,(centerImg.getWidth()/2*275/210),(centerImg.getWidth()/2*10/210),(centerImg.getWidth()/2*325/210),paintHuan);
        canvas.rotate(4.5f);//-10
        paintHuan.setColor(tempColors.get(20));
        canvas.drawRect(-(centerImg.getWidth()/2*15/210)-10,(centerImg.getWidth()/2*275/210),(centerImg.getWidth()/2*10/210),(centerImg.getWidth()/2*325/210),paintHuan);
        canvas.rotate(4.5f);//-9
        paintHuan.setColor(tempColors.get(21));
        canvas.drawRect(-(centerImg.getWidth()/2*15/210)-10,(centerImg.getWidth()/2*275/210),(centerImg.getWidth()/2*10/210),(centerImg.getWidth()/2*325/210),paintHuan);
        canvas.rotate(4.5f);//-8
        paintHuan.setColor(tempColors.get(22));
        canvas.drawRect(-(centerImg.getWidth()/2*15/210)-10,(centerImg.getWidth()/2*275/210),(centerImg.getWidth()/2*10/210),(centerImg.getWidth()/2*325/210),paintHuan);
        canvas.rotate(4.5f);//-7
        paintHuan.setColor(tempColors.get(23));
        canvas.drawRect(-(centerImg.getWidth()/2*15/210)-10,(centerImg.getWidth()/2*275/210),(centerImg.getWidth()/2*10/210),(centerImg.getWidth()/2*325/210),paintHuan);
        canvas.rotate(4.5f);//-6
        paintHuan.setColor(tempColors.get(24));
        canvas.drawRect(-(centerImg.getWidth()/2*15/210)-10,(centerImg.getWidth()/2*275/210),(centerImg.getWidth()/2*10/210),(centerImg.getWidth()/2*325/210),paintHuan);
        canvas.rotate(4.5f);//-5
        paintHuan.setColor(tempColors.get(25));
        canvas.drawRect(-(centerImg.getWidth()/2*15/210)-10,(centerImg.getWidth()/2*275/210),(centerImg.getWidth()/2*10/210),(centerImg.getWidth()/2*325/210),paintHuan);
        canvas.rotate(4.5f);//-4
        paintHuan.setColor(tempColors.get(26));
        canvas.drawRect(-(centerImg.getWidth()/2*15/210)-10,(centerImg.getWidth()/2*275/210),(centerImg.getWidth()/2*10/210),(centerImg.getWidth()/2*325/210),paintHuan);
        canvas.rotate(4.5f);//-3
        paintHuan.setColor(tempColors.get(27));
        canvas.drawRect(-(centerImg.getWidth()/2*15/210)-10,(centerImg.getWidth()/2*275/210),(centerImg.getWidth()/2*10/210),(centerImg.getWidth()/2*325/210),paintHuan);
        canvas.rotate(4.5f);//-2
        paintHuan.setColor(tempColors.get(28));
        canvas.drawRect(-(centerImg.getWidth()/2*15/210)-10,(centerImg.getWidth()/2*275/210),(centerImg.getWidth()/2*10/210),(centerImg.getWidth()/2*325/210),paintHuan);
        canvas.rotate(4.5f);//-1
        paintHuan.setColor(tempColors.get(29));
        canvas.drawRect(-(centerImg.getWidth()/2*15/210)-10,(centerImg.getWidth()/2*275/210),(centerImg.getWidth()/2*10/210),(centerImg.getWidth()/2*325/210),paintHuan);
        canvas.rotate(4.5f);//0
        paintHuan.setColor(tempColors.get(30));
        canvas.drawRect(-(centerImg.getWidth()/2*15/210)-10,(centerImg.getWidth()/2*275/210),(centerImg.getWidth()/2*10/210),(centerImg.getWidth()/2*325/210),paintHuan);
        canvas.rotate(4.5f);//1
        paintHuan.setColor(tempColors.get(31));
        canvas.drawRect(-(centerImg.getWidth()/2*15/210)-10,(centerImg.getWidth()/2*275/210),(centerImg.getWidth()/2*10/210),(centerImg.getWidth()/2*325/210),paintHuan);
        canvas.rotate(4.5f);//2
        paintHuan.setColor(tempColors.get(32));
        canvas.drawRect(-(centerImg.getWidth()/2*15/210)-10,(centerImg.getWidth()/2*275/210),(centerImg.getWidth()/2*10/210),(centerImg.getWidth()/2*325/210),paintHuan);
        canvas.rotate(4.5f);//3
        paintHuan.setColor(tempColors.get(33));
        canvas.drawRect(-(centerImg.getWidth()/2*15/210)-10,(centerImg.getWidth()/2*275/210),(centerImg.getWidth()/2*10/210),(centerImg.getWidth()/2*325/210),paintHuan);
        canvas.rotate(4.5f);//4
        paintHuan.setColor(tempColors.get(34));
        canvas.drawRect(-(centerImg.getWidth()/2*15/210)-10,(centerImg.getWidth()/2*275/210),(centerImg.getWidth()/2*10/210),(centerImg.getWidth()/2*325/210),paintHuan);
        canvas.rotate(4.5f);//5
        paintHuan.setColor(tempColors.get(35));
        canvas.drawRect(-(centerImg.getWidth()/2*15/210)-10,(centerImg.getWidth()/2*275/210),(centerImg.getWidth()/2*10/210),(centerImg.getWidth()/2*325/210),paintHuan);
        canvas.rotate(4.5f);//6
        paintHuan.setColor(tempColors.get(36));
        canvas.drawRect(-(centerImg.getWidth()/2*15/210)-10,(centerImg.getWidth()/2*275/210),(centerImg.getWidth()/2*10/210),(centerImg.getWidth()/2*325/210),paintHuan);
        canvas.rotate(4.5f);//7
        paintHuan.setColor(tempColors.get(37));
        canvas.drawRect(-(centerImg.getWidth()/2*15/210)-10,(centerImg.getWidth()/2*275/210),(centerImg.getWidth()/2*10/210),(centerImg.getWidth()/2*325/210),paintHuan);
        canvas.rotate(4.5f);//8
        paintHuan.setColor(tempColors.get(38));
        canvas.drawRect(-(centerImg.getWidth()/2*15/210)-10,(centerImg.getWidth()/2*275/210),(centerImg.getWidth()/2*10/210),(centerImg.getWidth()/2*325/210),paintHuan);
        canvas.rotate(4.5f);//9
        paintHuan.setColor(tempColors.get(39));
        canvas.drawRect(-(centerImg.getWidth()/2*15/210)-10,(centerImg.getWidth()/2*275/210),(centerImg.getWidth()/2*10/210),(centerImg.getWidth()/2*325/210),paintHuan);
        canvas.rotate(4.5f);//10
        paintHuan.setColor(tempColors.get(40));
        canvas.drawRect(-(centerImg.getWidth()/2*15/210)-10,(centerImg.getWidth()/2*275/210),(centerImg.getWidth()/2*10/210),(centerImg.getWidth()/2*325/210),paintHuan);
        canvas.rotate(4.5f);//11
        paintHuan.setColor(tempColors.get(41));
        canvas.drawRect(-(centerImg.getWidth()/2*15/210)-10,(centerImg.getWidth()/2*275/210),(centerImg.getWidth()/2*10/210),(centerImg.getWidth()/2*325/210),paintHuan);
        canvas.rotate(4.5f);//12
        paintHuan.setColor(tempColors.get(42));
        canvas.drawRect(-(centerImg.getWidth()/2*15/210)-10,(centerImg.getWidth()/2*275/210),(centerImg.getWidth()/2*10/210),(centerImg.getWidth()/2*325/210),paintHuan);
        canvas.rotate(4.5f);//13
        paintHuan.setColor(tempColors.get(43));
        canvas.drawRect(-(centerImg.getWidth()/2*15/210)-10,(centerImg.getWidth()/2*275/210),(centerImg.getWidth()/2*10/210),(centerImg.getWidth()/2*325/210),paintHuan);
        canvas.rotate(4.5f);//14
        paintHuan.setColor(tempColors.get(44));
        canvas.drawRect(-(centerImg.getWidth()/2*15/210)-10,(centerImg.getWidth()/2*275/210),(centerImg.getWidth()/2*10/210),(centerImg.getWidth()/2*325/210),paintHuan);
        canvas.rotate(4.5f);//15
        paintHuan.setColor(tempColors.get(45));
        canvas.drawRect(-(centerImg.getWidth()/2*15/210)-10,(centerImg.getWidth()/2*275/210),(centerImg.getWidth()/2*10/210),(centerImg.getWidth()/2*325/210),paintHuan);
        canvas.rotate(4.5f);//16
        paintHuan.setColor(tempColors.get(46));
        canvas.drawRect(-(centerImg.getWidth()/2*15/210)-10,(centerImg.getWidth()/2*275/210),(centerImg.getWidth()/2*10/210),(centerImg.getWidth()/2*325/210),paintHuan);
        canvas.rotate(4.5f);//17
        paintHuan.setColor(tempColors.get(47));
        canvas.drawRect(-(centerImg.getWidth()/2*15/210)-10,(centerImg.getWidth()/2*275/210),(centerImg.getWidth()/2*10/210),(centerImg.getWidth()/2*325/210),paintHuan);
        canvas.rotate(4.5f);//18
        paintHuan.setColor(tempColors.get(48));
        canvas.drawRect(-(centerImg.getWidth()/2*15/210)-10,(centerImg.getWidth()/2*275/210),(centerImg.getWidth()/2*10/210),(centerImg.getWidth()/2*325/210),paintHuan);
        canvas.rotate(4.5f);//19
        paintHuan.setColor(tempColors.get(49));
        canvas.drawRect(-(centerImg.getWidth()/2*15/210)-10,(centerImg.getWidth()/2*275/210),(centerImg.getWidth()/2*10/210),(centerImg.getWidth()/2*325/210),paintHuan);
        canvas.rotate(4.5f);//20
        paintHuan.setColor(tempColors.get(50));
        canvas.drawRect(-(centerImg.getWidth()/2*15/210)-10,(centerImg.getWidth()/2*275/210),(centerImg.getWidth()/2*10/210),(centerImg.getWidth()/2*325/210),paintHuan);
        canvas.rotate(4.5f);//21
        paintHuan.setColor(tempColors.get(51));
        canvas.drawRect(-(centerImg.getWidth()/2*15/210)-10,(centerImg.getWidth()/2*275/210),(centerImg.getWidth()/2*10/210),(centerImg.getWidth()/2*325/210),paintHuan);
        canvas.rotate(4.5f);//22
        paintHuan.setColor(tempColors.get(52));
        canvas.drawRect(-(centerImg.getWidth()/2*15/210)-10,(centerImg.getWidth()/2*275/210),(centerImg.getWidth()/2*10/210),(centerImg.getWidth()/2*325/210),paintHuan);
        canvas.rotate(4.5f);//23
        paintHuan.setColor(tempColors.get(53));
        canvas.drawRect(-(centerImg.getWidth()/2*15/210)-10,(centerImg.getWidth()/2*275/210),(centerImg.getWidth()/2*10/210),(centerImg.getWidth()/2*325/210),paintHuan);
        canvas.rotate(4.5f);//24
        paintHuan.setColor(tempColors.get(54));
        canvas.drawRect(-(centerImg.getWidth()/2*15/210)-10,(centerImg.getWidth()/2*275/210),(centerImg.getWidth()/2*10/210),(centerImg.getWidth()/2*325/210),paintHuan);
        canvas.rotate(4.5f);//25
        paintHuan.setColor(tempColors.get(55));
        canvas.drawRect(-(centerImg.getWidth()/2*15/210)-10,(centerImg.getWidth()/2*275/210),(centerImg.getWidth()/2*10/210),(centerImg.getWidth()/2*325/210),paintHuan);
        canvas.rotate(4.5f);//26
        paintHuan.setColor(tempColors.get(56));
        canvas.drawRect(-(centerImg.getWidth()/2*15/210)-10,(centerImg.getWidth()/2*275/210),(centerImg.getWidth()/2*10/210),(centerImg.getWidth()/2*325/210),paintHuan);
        canvas.rotate(4.5f);//27
        paintHuan.setColor(tempColors.get(57));
        canvas.drawRect(-(centerImg.getWidth()/2*15/210)-10,(centerImg.getWidth()/2*275/210),(centerImg.getWidth()/2*10/210),(centerImg.getWidth()/2*325/210),paintHuan);
        canvas.rotate(4.5f);//28
        paintHuan.setColor(tempColors.get(58));
        canvas.drawRect(-(centerImg.getWidth()/2*15/210)-10,(centerImg.getWidth()/2*275/210),(centerImg.getWidth()/2*10/210),(centerImg.getWidth()/2*325/210),paintHuan);
        canvas.rotate(4.5f);//29
        paintHuan.setColor(tempColors.get(59));
        canvas.drawRect(-(centerImg.getWidth()/2*15/210)-10,(centerImg.getWidth()/2*275/210),(centerImg.getWidth()/2*10/210),(centerImg.getWidth()/2*325/210),paintHuan);
        canvas.rotate(4.5f);//30
        paintHuan.setColor(tempColors.get(60));
        canvas.drawRect(-(centerImg.getWidth()/2*0/210),(centerImg.getWidth()/2*275/210),(centerImg.getWidth()/2*10/210),(centerImg.getWidth()/2*325/210),paintHuan);
        canvas.restore();
    }

    private void drawOthers(Canvas canvas) { //画中间温度状况
        canvas.save();
        canvas.drawText(stringLKWDZK,getWidth()/2-centerImg.getWidth()/2,getHeight()/2+0.7f*arcRadius+(centerImg.getWidth()/2*70/240),paintOthers);
        String str =null;
        if(temperature<middleTemp){
            if(temperature<minTemp){
                temperature=minTemp;
            }
            int abs = Math.abs(temperature);
            paintOthers.setColor(tempColors.get((int) ((maxTemp-abs)*(135f/maxTemp)*60/270)));
            if(temperature<=precent02Temp){
                str = stringY;
            }else if(temperature<=precent01Temp){
                str = stringL;
            }else {
                str = stringZD;
            }
        }else if(temperature==middleTemp){
            paintOthers.setColor(tempColors.get(30));
            str = stringZD;
        }else if(temperature>middleTemp){
            if(temperature>maxTemp){
                temperature=maxTemp;
            }
            if(temperature>=precent2Temp){
                str = stringWX;
            }else if(temperature>=precent1Temp){
                str = stringGR;
            }else {
                str = stringZD;
            }
            paintOthers.setColor(tempColors.get(60-(int) ((maxTemp-temperature)*(135f/maxTemp)*60/270)));
        }
        canvas.drawRect(getWidth()/2-centerImg.getWidth()/2+rLengKu.width()+(centerImg.getWidth()/2*20/240),getHeight()/2+0.7f*arcRadius+(centerImg.getWidth()/2*60/240)-rLengKu.height()/3*2,getWidth()/2-centerImg.getWidth()/2+rLengKu.width()+(centerImg.getWidth()/2*100/240),getHeight()/2+0.7f*arcRadius+(centerImg.getWidth()/2*60/240)+rLengKu.height()/3,paintOthers);
        paintOthers.setColor(paintColors.get(5));
        canvas.drawText(str,getWidth()/2-centerImg.getWidth()/2+rLengKu.width()+(centerImg.getWidth()/2*120/240),getHeight()/2+0.7f*arcRadius+(centerImg.getWidth()/2*70/240),paintOthers);
        canvas.restore();
    }

    private void drawTemp(Canvas canvas) { //画中间温度和小圆点
        canvas.save();
        canvas.translate(getWidth() / 2, getHeight() / 2);
        float tempWidth = paintTemp.measureText(temperature + "");
        float tempHeight = (paintTemp.ascent() + paintTemp.descent()) / 2;
        if(temperature<middleTemp){
            if(temperature<minTemp){
                temperature=minTemp;
            }
            int abs = Math.abs(temperature);
            paintTemp.setColor(tempColors.get((int) ((maxTemp-abs)*(135f/maxTemp)*60/270)));
        }else if(temperature==middleTemp){
            paintTemp.setColor(tempColors.get(30));
        }else if(temperature>middleTemp){
            if(temperature>maxTemp){
                temperature=maxTemp;
            }
            paintTemp.setColor(tempColors.get(60-(int) ((maxTemp-temperature)*(135f/maxTemp)*60/270)));
        }
        canvas.drawText(temperature + stringDegree, -tempWidth / 2 - dp2px(5), -tempHeight, paintTemp);
        canvas.rotate(rotateAngle);
        canvas.drawCircle(0-centerImg.getWidth()/2+0.7f*(centerImg.getWidth()/2*150/240),0+centerImg.getWidth()/2-0.7f*(centerImg.getWidth()/2*150/240),(centerImg.getWidth()/2*10/240),paintTemp);
        canvas.restore();
    }

    private void caculateAngle(int currentTemp){ //根据温度计算角度
        if(currentTemp<middleTemp){
            if(currentTemp<minTemp){
                rotateAngle=middleTemp;
            }else {
                rotateAngle = (currentTemp-minTemp)*(135f/maxTemp);
            }
        }else if(currentTemp==middleTemp){
            rotateAngle=135;
        }else if(currentTemp>middleTemp){
            if(currentTemp>maxTemp){
                rotateAngle=270;
            }else {
                rotateAngle = 270-(maxTemp-currentTemp)*(135f/maxTemp);
            }
        }
    }

    private void drawBitmap(Canvas canvas) { //画中间圆
        canvas.save();
        matrix.setTranslate((getWidth()-centerImg.getWidth()) / 2, (getHeight()-centerImg.getHeight()) / 2);
        caculateAngle(temperature);
        matrix.postRotate(-22.5f+rotateAngle,getWidth()/2,getHeight()/2);
        canvas.drawBitmap(centerImg,matrix,paintBitmap);
        canvas.restore();
    }

    private void draw2Line(Canvas canvas) { //画两边封闭线段
        canvas.save();
        canvas.translate(getWidth()/2,getHeight()/2);
        canvas.rotate(-135);
        canvas.drawLine(0, -(getHeight()/2)+(centerImg.getWidth()/2*202/210), 0, -(getHeight()/2)+(centerImg.getWidth()/2*102/210), paint2Line);
        canvas.rotate(-90);
        canvas.drawLine(0, -(getHeight()/2)+(centerImg.getWidth()/2*202/210), 0, -(getHeight()/2)+(centerImg.getWidth()/2*102/210), paint2Line);
        canvas.restore();
    }

    private void drawCircle(Canvas canvas) { //画中间阴影圆
        canvas.save();
        canvas.translate(getWidth()/2,getHeight()/2);
        if(linearGradient==null){
            linearGradient = new LinearGradient(getWidth()/2,getHeight()/2-arcRadius+(centerImg.getWidth()/2*100/210),getWidth()/2,getHeight()/2+arcRadius-(centerImg.getWidth()/2*100/210),Color.parseColor("#FFFFFFFF"),Color.parseColor("#e7e7e7"), Shader.TileMode.MIRROR);
            paintCircle.setShader(linearGradient);
        }
        canvas.drawCircle(0,0,arcRadius-(centerImg.getWidth()/2*110/210),paintCircle);
        canvas.restore();
    }

    private void drawText(Canvas canvas) { //画温度刻度
        canvas.save();
        canvas.drawText(string030,getWidth()/2-0.7f*arcRadius-(centerImg.getWidth()/2*80/210),getHeight()/2+0.7f*arcRadius+(centerImg.getWidth()/2*70/210),paint);
        canvas.drawText(stringY,getWidth()/2-0.924f*arcRadius-(centerImg.getWidth()/2*60/210),getHeight()/2+0.383f*arcRadius+(centerImg.getWidth()/2*35/210),paintText);
        canvas.drawText(string020,getWidth()/2-1.0f*arcRadius-(centerImg.getWidth()/2*35/210)-r30.width(),getHeight()/2+r30.height()/2,paint);
        canvas.drawText(stringL,getWidth()/2-0.924f*arcRadius-(centerImg.getWidth()/2*60/210),getHeight()/2-0.383f*arcRadius-(centerImg.getWidth()/2*20/210),paintText);
        canvas.drawText(string010,getWidth()/2-0.7f*arcRadius-(centerImg.getWidth()/2*80/210),getHeight()/2-0.7f*arcRadius-(centerImg.getWidth()/2*40/210),paint);
        canvas.drawText(stringZD,getWidth()/2-0.383f*arcRadius-rNormal.width(),getHeight()/2-0.924f*arcRadius-(centerImg.getWidth()/2*30/210),paintText);
        canvas.drawText(string0,getWidth()/2-r0.width()/2,getHeight()/2-arcRadius-r0.height()-(centerImg.getWidth()/2*20/210),paint);
        canvas.drawText(stringZD,getWidth()/2+0.383f*arcRadius,getHeight()/2-0.924f*arcRadius-(centerImg.getWidth()/2*30/210),paintText);
        canvas.drawText(string10,getWidth()/2+0.7f*arcRadius+r10.width()/2,getHeight()/2-0.7f*arcRadius-(centerImg.getWidth()/2*40/210),paint);
        canvas.drawText(stringGR,getWidth()/2+0.924f*arcRadius+rNormal.width()/2,getHeight()/2-0.383f*arcRadius-(centerImg.getWidth()/2*20/210),paintText);
        canvas.drawText(string20,getWidth()/2+arcRadius+(centerImg.getWidth()/2*35/210),getHeight()/2+r30.height()/2,paint);
        canvas.drawText(stringWX,getWidth()/2+0.924f*arcRadius+rNormal.width()/2,getHeight()/2+0.383f*arcRadius+(centerImg.getWidth()/2*35/210),paintText);
        canvas.drawText(string30,getWidth()/2+0.7f*arcRadius+r10.width()/2,getHeight()/2+0.7f*arcRadius+(centerImg.getWidth()/2*70/210),paint);
        canvas.restore();
    }

    private void drawScale(Canvas canvas) { //画刻度
        canvas.save();
        canvas.translate(getWidth()/2,getHeight()/2);
        canvas.rotate(-120);
        for(int i=0;i<17;i++){
            if(i==2||i==5||i==8||i==11||i==14){
                canvas.drawLine(0, -(width/2-dp2px(25)), 0, -(width/2-dp2px(30)) + dp2px(10), paintScale);
            }else {
                canvas.drawLine(0, -(width/2-dp2px(35)), 0, -(width/2-dp2px(30)) + dp2px(10), paintScale);
            }
            canvas.rotate(15f);
        }
        canvas.restore();
    }

    private void drawArc(Canvas canvas) { //画最外圈弧
        canvas.save();
        canvas.translate(getWidth()/2,getHeight()/2);
        canvas.rotate(135);
        if(rect==null){
            rect = new RectF(-arcRadius,-arcRadius,arcRadius,arcRadius);
        }
        canvas.drawArc(rect,0,270,false,paintArc);
        canvas.restore();
    }

    private int dp2px(float dp) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp,
                getResources().getDisplayMetrics());
    }

    private int sp2px(float sp) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, sp,
                getResources().getDisplayMetrics());
    }

    public void setTemperature(int temp){ //设置当前温度
        this.temperature = temp;
        postInvalidate();
    }
    public void setPrecentTemp(int minT,int maxT,int p2T,int p02T,int p1T,int p01T){ //设置最大最小左2左1右2右1温度
        this.minTemp = minT;
        this.maxTemp = maxT;
        this.precent2Temp = p2T;
        this.precent02Temp = p02T;
        this.precent1Temp = p1T;
        this.precent01Temp = p01T;
        string030 = minTemp+stringDegree;
        string30 = maxTemp+stringDegree;
        string20 = precent2Temp+stringDegree;
        string020 = precent02Temp+stringDegree;
        string10 = precent1Temp+stringDegree;
        string010 = precent01Temp+stringDegree;
        postInvalidate();
    }

}
