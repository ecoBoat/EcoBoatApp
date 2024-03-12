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

        // Draw the progress text
        paint.setColor(0xFF000000); // Black color
        paint.setTextSize(80);
        String progressText = String.valueOf(progress);
        float textWidth = paint.measureText(progressText);
        canvas.drawText(progressText, cx - textWidth / 2, cy + 10, paint);
    }

    public void setProgress(int progress) {
        this.progress = progress;
        invalidate(); // Redraw the view
    }
}