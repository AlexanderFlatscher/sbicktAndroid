package geotag.example.sbickt;

import geotag.core.GeoTag;
import geotag.core.Matrix3D;
import geotag.core.Point3D;
import geotag.core.Vector3D;

import java.util.Vector;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.location.Location;
import android.os.AsyncTask;
import android.util.AttributeSet;
import android.view.ViewGroup;

public class CameraViewMessagesOverlay extends ViewGroup implements Runnable {
	
	public Vector<SbickerlView> sbickerls;
	public Vector3D phoneLocation;
	public Vector3D accelVector;
	public double heading;

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
	
	public void add(GeoTag geotag){
		SbickerlView newSbickerl = new SbickerlView(getContext());
		newSbickerl.content = geotag;
		sbickerls.add(newSbickerl);
		addView(newSbickerl); // ???
	}
	
	public void clear(){
		sbickerls.clear();
	}

	@Override
	protected void onLayout(boolean changed, int l, int t, int r, int b) {
		if(changed){
			/*for(final SbickerlView sbickerl : sbickerls){
				// TODO draw with new position
			}*/
		}
	}
	
	public void run() {
		// TODO calculate the position of each sbickerl and then call invalidate
		while(true){
			
			double phi = heading * Math.PI / 180;
			
			Vector3D right = new Vector3D(0, Math.sin(phi), - Math.cos(phi));
			
			Vector3D z = accelVector;
			z.normalize();
			
			Vector3D y = z.cross(right);
			y.normalize();
			
			Vector3D x = y.cross(z);
			x.normalize();
			
//			Matrix3D matrix = new Matrix3D(x, y, z);
//			matrix.transpose();
//			
//			// TODO get screen size
//			
//			double angularFOV = 47.5 * Math.PI / 180;
//			double focalLength = screenSize.width / 2 / Math.tan(angularFOV / 2);
//			
////			for(SbickerlView s : sbickerls){
//			
//			Vector3D worldDirection = new Vector3D(100, 0, 0);
//			Vector3D phoneDirection = matrix.transformVector(worldDirection);
//			
//			
//			Vector3D screenPosition = new Vector3D();
//			screenPosition.y = screenSize.height / 2 - phoneDirection.x / (-phoneDirection.z) * focalLength;
//			screenPosition.x = screenSize.width / 2 - phoneDirection.y / (-phoneDirection.z) * focalLength;
//			screenPosition.z = -phoneDirection.z;
			
//			}
			
			postInvalidate();
		}
	}
	
}
