package geotag.example.sbickt;

import geotag.core.Point3D;
import geotag.core.R;
import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.RelativeLayout;

public class SbickerlView extends RelativeLayout {

	private double x = 0, y = 0, z = 0;
	// TODO add text...
	
	public SbickerlView(Context context) {
		super(context);
		init(context);
	}

	public SbickerlView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init(context);
	}

	public SbickerlView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		init(context);
	}
	
	private void init(Context context){
		LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		inflater.inflate(R.layout.sbickerl, this, true);
	}

	public double getX() {
		return x;
	}

	public void setX(double x) {
		this.x = x;
	}

	public double getY() {
		return y;
	}

	public void setY(double y) {
		this.y = y;
	}

	public double getZ() {
		return z;
	}

	public void setZ(double z) {
		this.z = z;
	}

}
