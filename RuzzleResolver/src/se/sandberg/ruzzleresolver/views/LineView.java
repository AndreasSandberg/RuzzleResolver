package se.sandberg.ruzzleresolver.views;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.view.View;

public class LineView extends View {

	private final float[] coordinates;
	private static final Paint linePaint = new Paint();
	
	static{
		linePaint.setColor(Color.YELLOW);
		linePaint.setStyle(Style.STROKE);
		linePaint.setStrokeWidth(3f);
		linePaint.setPathEffect(new DashPathEffect(new float[]{5,10,15,20}, 0));
	}
	
	//Constructor only used in edit mode (in Eclipse)
	public LineView(Context context) {
		super(context);
		coordinates = null;
	}
	
	public LineView(Context context, float[] coordinates) {
		super(context);
		this.coordinates = coordinates;

	}

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		if(!isInEditMode()){
			canvas.drawLines(coordinates, linePaint);
		}
	}
}
