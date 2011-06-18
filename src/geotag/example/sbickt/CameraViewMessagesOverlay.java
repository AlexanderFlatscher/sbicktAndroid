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
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.util.Log;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.Toast;

public class CameraViewMessagesOverlay extends FrameLayout implements Runnable {
	
	public Vector<SbickerlView> sbickerls;
	//public Vector3D phoneLocation;
	public Vector3D accelVector;
	public double heading;
	public double focalLength, angularFOV;
	private Thread myThread;

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
		angularFOV = 47.5 * Math.PI / 180;
		
		myThread = new Thread(this);
		
	}
	
	public void start(){
		myThread.start();
		Log.v("alex", "thread started");
	}

//	@Override
//	protected void onDraw(Canvas canvas) {
//		Paint p = new Paint();
//		p.setARGB(100, 255, 0, 255);
//		canvas.drawCircle(100, 100, 50, p);
//		super.onDraw(canvas);
//	}
	
	public void add(GeoTag geotag){
		SbickerlView newSbickerl = new SbickerlView(getContext());
		newSbickerl.setGeoTag(geotag);
		sbickerls.add(newSbickerl);
		addView(newSbickerl); // ???
	}
	
	public void clear(){
		sbickerls.clear();
	}

	@Override
	protected void onLayout(boolean changed, int l, int t, int r, int b) {
		//Log.v("alex", "in on layout");
		super.onLayout(changed, l, t, r, b);
		updateSbickerlsPosition();
	}
	
	private void updateSbickerlsPosition() {
		int count = getChildCount();
		Log.v("alex", "in update sbickerls position with count: " + count);
		for(int i = 0; i < count; i++){
			SbickerlView current = (SbickerlView) getChildAt(i);
			//current.layout((int)current.screenPosition.x, (int)current.screenPosition.y, (int)current.screenPosition.x + current.getMeasuredWidth(), (int)current.screenPosition.y + current.getMeasuredHeight());
			current.layout(-100, -100, -100 + current.getMeasuredWidth(), -100 + current.getMeasuredHeight());
			Log.v("pos", "current x: " + current.screenPosition.x + " current y: " + current.screenPosition.y);
		}
	}
	
	public void calculateSbickerlWorldDirection(Point3D location){
		Toast.makeText(getContext(), "calculating sbickerl wolddirection", Toast.LENGTH_LONG).show();
		for(SbickerlView s : sbickerls){
			s.worldDirection = new Vector3D(location, s.content.getCoordinates());
			s.worldDirection.z = 0;
			//s.worldDirection = new Vector3D(100.0, 0.0, 0.0);
		}
		
	}
	
	public void run() {
		// TODO calculate the position of each sbickerl and then call invalidate
		while(true){
			
			if(sbickerls.isEmpty() || accelVector == null)
				continue;
			
			// has to be calculated only once, shift where getWidth() ready
			focalLength = this.getWidth() / 2 / Math.tan(angularFOV / 2);
			
			double phi = heading * Math.PI / 180;
			
			Vector3D right = new Vector3D(0, Math.sin(phi), - Math.cos(phi));
			
			Vector3D z = accelVector.mul(-1);
			z.normalize();
			
			Vector3D y = z.cross(right);
			y.normalize();
			
			Vector3D x = y.cross(z);
			x.normalize();
			
			Matrix3D matrix = new Matrix3D(x, y, z);
			matrix.transpose();
			
			for(SbickerlView s : sbickerls){
				
				s.phoneDirection = matrix.transformVector(s.worldDirection);
				
				s.screenPosition = new Vector3D();
				
				s.screenPosition.x = this.getWidth() / 2 + s.phoneDirection.y / s.phoneDirection.z * focalLength;
				s.screenPosition.y = this.getHeight() / 2 + s.phoneDirection.x / s.phoneDirection.z * focalLength;
				s.screenPosition.z = -s.phoneDirection.z;
			}
			
			requestLayoutHandler.sendEmptyMessage(1);
			try { Thread.sleep (10); }
			catch (InterruptedException e) {e.printStackTrace();}
		
		}
	}
	
	private Handler requestLayoutHandler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			//super.handleMessage(msg);
			Log.v("alex", "handle message, what: " + msg.what);
			requestLayout();
		}
		
		
	};
	
}
