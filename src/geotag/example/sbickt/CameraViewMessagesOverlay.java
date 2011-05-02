package geotag.example.sbickt;

import geotag.core.Point3D;

import java.util.Vector;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.os.AsyncTask;
import android.util.AttributeSet;
import android.view.ViewGroup;

public class CameraViewMessagesOverlay extends ViewGroup implements Runnable {
	
	public Vector<SbickerlView> sbickerls;

	public CameraViewMessagesOverlay(Context context) {
		super(context);
		init(context);
	}
	
	public CameraViewMessagesOverlay(Context context, AttributeSet attrs) {
		super(context, attrs);
		init(context);
	}
	
	public CameraViewMessagesOverlay(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		init(context);
	}
	
	private void init(Context context){
		sbickerls = new Vector<SbickerlView>();
	}

	@Override
	protected void onDraw(Canvas canvas) {
		Paint p = new Paint();
		p.setARGB(100, 255, 0, 255);
		canvas.drawCircle(100, 100, 50, p);
		super.onDraw(canvas);
	}
	
	public void add(double x, double y, double z){
		SbickerlView newSbickerl = new SbickerlView(getContext());
		newSbickerl.setX(x);
		newSbickerl.setY(y);
		newSbickerl.setZ(z);
		sbickerls.add(newSbickerl);
		addView(newSbickerl); // ???
	}

	@Override
	protected void onLayout(boolean changed, int l, int t, int r, int b) {
		if(changed){
			for(final SbickerlView sbickerl : sbickerls){
				// TODO draw with new position
			}
		}
	}
	
	public void run() {
		// TODO calculate the position of each sbickerl and then call invalidate
		
	}
	
}
