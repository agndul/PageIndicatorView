package com.rd.draw.drawer.type;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.support.annotation.NonNull;
import com.rd.animation.type.AnimationType;
import com.rd.draw.data.Indicator;

public class BasicDrawer extends BaseDrawer {

    private Paint strokePaint;

    public BasicDrawer(@NonNull Paint paint, @NonNull Indicator indicator) {
        super(paint, indicator);

        strokePaint = new Paint();
        strokePaint.setStyle(Paint.Style.STROKE);
        strokePaint.setAntiAlias(true);
        strokePaint.setStrokeWidth(indicator.getStroke());
    }

    public void draw(
            @NonNull Canvas canvas,
            int position,
            boolean isSelectedItem,
            int coordinateX,
            int coordinateY) {

        float radius = indicator.getRadius();
        float wormWidth = indicator.getWormWidth();
        float wormHeight = indicator.getWormHeight();
        float wormRound = indicator.getWormRound();
        int strokePx = indicator.getStroke();
        float scaleFactor = indicator.getScaleFactor();

        int selectedColor = indicator.getSelectedColor();
        int unselectedColor = indicator.getUnselectedColor();
        int selectedPosition = indicator.getSelectedPosition();
        AnimationType animationType = indicator.getAnimationType();

        if (radius != 0) {
            if (animationType == AnimationType.SCALE && !isSelectedItem) {
                radius *= scaleFactor;

            } else if (animationType == AnimationType.SCALE_DOWN && isSelectedItem) {
                radius *= scaleFactor;
            }
        } else if (wormWidth != 0 && wormHeight != 0) {
            if (animationType == AnimationType.SCALE && !isSelectedItem) {
                wormWidth *= scaleFactor;
                wormHeight *= scaleFactor;
            } else if (animationType == AnimationType.SCALE_DOWN && isSelectedItem) {
                wormWidth *= scaleFactor;
                wormHeight *= scaleFactor;
            }
        }

        int color = unselectedColor;
        if (position == selectedPosition) {
            color = selectedColor;
        }

        Paint paint;
        if (animationType == AnimationType.FILL && position != selectedPosition) {
            paint = strokePaint;
            paint.setStrokeWidth(strokePx);
        } else {
            paint = this.paint;
        }

        paint.setColor(color);
        if (radius != 0) {
            canvas.drawCircle(coordinateX, coordinateY, radius, paint);
        } else if (wormWidth != 0 && wormHeight != 0) {
            RectF rectangle = new RectF(coordinateX-wormWidth/2, coordinateY + wormHeight/2, coordinateX+wormWidth/2, coordinateY-wormHeight/2);
            canvas.drawRoundRect(rectangle, wormRound, wormRound, paint);
        }
    }
}
