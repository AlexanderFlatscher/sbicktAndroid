package geotag.example.sbickt;

import geotag.core.GeoTag;
import geotag.core.R;
import geotag.core.Vector3D;
import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.widget.RelativeLayout;
import android.widget.TextView;

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
	
	public void setGeoTag(GeoTag g) {
		content = g;
		((TextView) findViewById(R.id.sbickerl_text_view)).setText(content.getContent());
	}
	
	@Override
	protected void onLayout(boolean changed, int l, int t, int r, int b) {
		super.onLayout(changed, l, t, r, b);
	}
	
	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
	}

}
