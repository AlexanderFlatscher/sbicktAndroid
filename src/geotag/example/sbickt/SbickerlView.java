package geotag.example.sbickt;

import geotag.core.GeoTag;
import geotag.core.Point3D;
import geotag.core.R;
import geotag.core.Vector3D;
import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.RelativeLayout;

public class SbickerlView extends RelativeLayout {
	
	public GeoTag content;
	public Vector3D worldDirection, phoneDirection, screenPosition;
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

}
