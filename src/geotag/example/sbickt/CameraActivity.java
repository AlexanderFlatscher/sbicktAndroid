/**
 * #################################################
 * #            s'bickt Android Client             #
 * # Bildungseinrichtung:  Fachhochschule Salzburg #
 * #         Studiengang:  MultiMediaTechnology    #
 * #               Zweck:  Qualifikationsprojekt   #
 * #################################################
 *
 * This is the client for the augmented reality and social community app s'bickt
 * Copyright Alexander Flatscher, Ismail Hanli
 * 
 * This file is part of s'bickt.
 * 
 * S'bickt is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * S'bickt is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 * 
 * You should have received a copy of the GNU Affero General Public License
 * along with s'bickt.  If not, see <http://www.gnu.org/licenses/>.
 */

package geotag.example.sbickt;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.Overlay;
import com.google.android.maps.OverlayItem;

import geotag.core.GeoTag;
import geotag.core.Point3D;
import geotag.core.R;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;


public class CameraActivity extends Activity {
	
	private static final int GPS_REFRESH_TIME_IN_MS = 60000;
	private static final int GPS_REFRESH_DISTANCE_IN_M = 50;
	
	private CameraViewMessagesOverlay messagesOverlay;

	private LocationManager locationManager;

	private SensorManager mSensorManager;
    private Sensor mAccelerometer;
    
    private Location currentLocation;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//getWindow().setFormat(PixelFormat.TRANSLUCENT);

		setContentView(R.layout.camera_view);
		((ImageButton) findViewById(R.id.live_view)).setVisibility(View.INVISIBLE);
		messagesOverlay = (CameraViewMessagesOverlay) findViewById(R.id.live_view_messages_overlay);
		locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
		
		mSensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        mAccelerometer = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
	}
	
	@Override
	protected void onResume() {
		super.onResume();
		locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, GPS_REFRESH_TIME_IN_MS, GPS_REFRESH_DISTANCE_IN_M, locationListener);
		mSensorManager.registerListener(sensorEventListener, mAccelerometer, SensorManager.SENSOR_DELAY_NORMAL);
		
		currentLocation = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
		new InitSbicktMessages().execute(currentLocation);
	}
	
	@Override
	protected void onPause() {
		super.onPause();
		locationManager.removeUpdates(locationListener);
		mSensorManager.unregisterListener(sensorEventListener);
	}
	
	private final LocationListener locationListener = new LocationListener() {
		
		public void onStatusChanged(String provider, int status, Bundle extras) {
			// TODO warning message: GPS not available (maybe because of the building you are in...)
		}
		
		public void onProviderEnabled(String provider) {}
		
		public void onProviderDisabled(String provider) {
			// TODO error message: GPS not enabled
		}
		
		public void onLocationChanged(Location location) {
			new UpdateSbicktMessages().execute(location);
			currentLocation = location;
			// TODO execute run with new location; download new messages from server
		}
	};
	
	private final SensorEventListener sensorEventListener = new SensorEventListener() {
		
		public void onSensorChanged(SensorEvent event) {
			messagesOverlay.invalidate();
			//Log.v("alex", "x: " + event.values[0]/SensorManager.GRAVITY_EARTH + " y: " + event.values[1]/SensorManager.GRAVITY_EARTH + " z: " + event.values[2]/SensorManager.GRAVITY_EARTH);
			
		}
		
		public void onAccuracyChanged(Sensor sensor, int accuracy) {
			// TODO Auto-generated method stub
			
		}
	};
	
	private class UpdateSbicktMessages extends AsyncTask<Location, Void, Queue<GeoTag>>{

		@Override
		protected Queue<GeoTag> doInBackground(Location... params) {
			Queue<GeoTag> geoTags = new LinkedList<GeoTag>();
			
			try {
				geoTags = SbicktAPI.getGeoTags(new Point3D(params[0]));
				Log.v("alex", geoTags.toString());
			} catch (Exception e) {
				e.printStackTrace();
			}
			return geoTags;
		}
		
		@Override
		protected void onPostExecute(Queue<GeoTag> result) {
			if(result.isEmpty()){
				Toast.makeText(getBaseContext(), "no messages received", Toast.LENGTH_LONG).show();
			}
			else {
				// TODO add messages to camera view messages overlay
			}
		}
		
	}
	
	private class InitSbicktMessages extends UpdateSbicktMessages {

		private ProgressDialog sbicktMessagesDialog = new ProgressDialog(CameraActivity.this);
		
		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			sbicktMessagesDialog.setMessage("lade s'bickt Nachrichten...");
			sbicktMessagesDialog.show();
		}
		
		@Override
		protected void onPostExecute(Queue<GeoTag> result) {
			super.onPostExecute(result);
			sbicktMessagesDialog.dismiss();
		}
		
	}
}
