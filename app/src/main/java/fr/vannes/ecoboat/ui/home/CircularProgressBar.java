package fr.vannes.ecoboat.ui.home;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class CircularProgressBar extends View {

    private int mIndex = 0;
    private int progress = 0;
    private final Paint paint = new Paint();

    public CircularProgressBar(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        paint.setStrokeWidth(10);
        paint.setStyle(Paint.Style.STROKE);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int width = getWidth();
        int height = getHeight();
        int radius = Math.min(width, height) / 2 - 10;
        int cx = width / 2;
        int cy = height / 2;

        // Draw the outer half circle
        paint.setColor(0xFF000000); // Black color
        canvas.drawArc(cx - radius, cy - radius, cx + radius, cy + radius,
                360, 360, false, paint);

        // Draw the inner half circle
        int innerRadius = radius - 20; // Make the inner circle smaller

        // Change color based on the progress value
        if (progress <= 20) {
            paint.setColor(0xFFFF0000); // Red color
        } else if (progress <= 40) {
            paint.setColor(0xFFFF7F00); // Orange color
        } else if (progress <= 60) {
            paint.setColor(0xFFFFFF00); // Yellow color
        } else if (progress <= 80) {
            paint.setColor(0xFF00FF00); // Green color
        } else {
            paint.setColor(0xFF007FFF); // Blue color
        }

        float sweepAngle = (float) progress / 100 * 180;
        canvas.drawArc(cx - innerRadius, cy - innerRadius, cx + innerRadius, cy + innerRadius,
                180, sweepAngle, false, paint);

        // Draw the needle
        double angle = Math.toRadians(sweepAngle + 180); // Adjust the angle to start from the top of the circle
        float needleLengthFactor = 0.8f; // Reduce the needle length by 20%
        float needleX = cx + (float) (innerRadius * Math.cos(angle) * needleLengthFactor);
        float needleY = cy + (float) (innerRadius * Math.sin(angle) * needleLengthFactor);
        paint.setColor(0xFF000000); // Black color
        canvas.drawLine(cx, cy, needleX, needleY, paint);

        // Draw the progress text
        paint.setColor(0xFF000000); // Black color
        paint.setTextSize(80);
        String progressText = String.valueOf(progress);
        float textWidth = paint.measureText(progressText);

        // Draw background
        float textHeight = paint.descent() - paint.ascent();
        float textOffset = (textHeight / 2) - paint.descent();
        float backgroundMargin = 10f; // Adjust this value to change the background size
        float left = cx - textWidth / 2 - backgroundMargin;
        float top = cy - textOffset - backgroundMargin;
        float right = cx + textWidth / 2 + backgroundMargin;
        float bottom = cy + textOffset + backgroundMargin;
        paint.setColor(0xFFFFFFFF); // White color for the background
        canvas.drawRect(left, top, right, bottom, paint);

        // Draw text over the background
        paint.setColor(0xFF000000); // Black color
        canvas.drawText(progressText, cx - textWidth / 2, cy + textOffset, paint);


    }

    public void setProgress(int progress) {
        this.progress = progress;
        invalidate(); // Redraw the view
    }
}