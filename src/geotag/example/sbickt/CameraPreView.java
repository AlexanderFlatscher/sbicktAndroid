package geotag.example.sbickt;

import java.io.IOException;

import android.content.Context;
import android.hardware.Camera;
import android.util.AttributeSet;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class CameraPreView extends SurfaceView implements SurfaceHolder.Callback {

	private Camera mCamera;
	private SurfaceHolder mSurfaceHolder;
	private boolean mPreviewRunning;

	
	public CameraPreView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		init();
	}

	public CameraPreView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();
	}

	public CameraPreView(Context context) {
		super(context);
		init();
	}
	
	private void init() {
		mSurfaceHolder = getHolder();
		mSurfaceHolder.addCallback(this);
		mSurfaceHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
	}


	public void surfaceChanged(SurfaceHolder holder, int format, int width,	int height) {
		if(mPreviewRunning)
			mCamera.stopPreview();
		
		Camera.Parameters p = mCamera.getParameters();
		//p.setPreviewSize(width, height);
		mCamera.setParameters(p);
		
		try {
			mCamera.setPreviewDisplay(mSurfaceHolder);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		mCamera.startPreview();
		mPreviewRunning = true;
		
		
	}

	public void surfaceCreated(SurfaceHolder holder) {
		mCamera = Camera.open();
		
	}

	public void surfaceDestroyed(SurfaceHolder holder) {
		mCamera.stopPreview();
		mPreviewRunning = false;
		mCamera.release();
	}

}
