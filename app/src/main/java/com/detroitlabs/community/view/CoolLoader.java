package com.detroitlabs.community.view;

import android.animation.Animator;
import android.animation.Animator.AnimatorListener;
import android.animation.ValueAnimator;
import android.animation.ValueAnimator.AnimatorUpdateListener;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Cap;
import android.graphics.Paint.Style;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewTreeObserver.OnPreDrawListener;

public class CoolLoader extends View{

    private boolean redraw        = false;
    private float   startAngle    = 0;
    private float   sweepAngle    = 90f;
    private float   moveSweep     = 60f;

    private ValueAnimator animator;

    private RectF frame;
    private Paint paint;

    public CoolLoader(Context context){
        super(context);
        init();
    }

    public CoolLoader(Context context, AttributeSet attrs){
        super(context, attrs);
        init();
    }

    public CoolLoader(Context context, AttributeSet attrs, int defStyle){
        super(context, attrs, defStyle);
        init();
    }

    private void init(){

        getViewTreeObserver().addOnPreDrawListener(new OnPreDrawListener(){
            private int width = 0;
            private int height = 0;

            @Override
            public boolean onPreDraw(){
                if (getWidth() != width || getHeight() != height){
                    onSizeChanged();
                }
                return true;
            }
        });

        animator = ValueAnimator.ofFloat(startAngle, startAngle + sweepAngle);
        animator.setDuration(1000);
        animator.addUpdateListener(new AnimatorUpdateListener(){
            @Override
            public void onAnimationUpdate(ValueAnimator animation){
                float val = (Float)animation.getAnimatedValue();
                startAngle = val;
            }
        });

        animator.addListener(new AnimatorListener(){
            @Override
            public void onAnimationStart(Animator animation){}

            @Override
            public void onAnimationEnd(Animator animation){

            }

            @Override
            public void onAnimationCancel(Animator animation){}

            @Override
            public void onAnimationRepeat(Animator animation){}
        });

        paint = new Paint();
        paint.setStyle(Style.STROKE);
        paint.setColor(Color.CYAN);
        paint.setStrokeCap(Cap.ROUND);
        paint.setAntiAlias(true);
        redraw();
    }

    private void redraw(){
        redraw = true;
        invalidate();
        animator.start();
    }

    private void onSizeChanged(){
        paint.setStrokeWidth(0.1f*getWidth());
        frame = new RectF(0, 0, getWidth(), getHeight());
    }

    @Override
    protected void onDraw(Canvas canvas){
        super.onDraw(canvas);

        canvas.drawArc(frame, startAngle, sweepAngle, false, paint);

        if (redraw)
            invalidate();
    }

    @Override
    protected void onVisibilityChanged(View changedView, int visibility){
        super.onVisibilityChanged(changedView, visibility);

        if (changedView == this && getVisibility() == View.VISIBLE){
            redraw();
        }
    }
}
