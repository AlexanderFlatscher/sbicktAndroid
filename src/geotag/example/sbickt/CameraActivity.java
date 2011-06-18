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

import geotag.core.GeoTag;
import geotag.core.Point3D;
import geotag.core.R;
import geotag.core.Vector3D;

import java.util.LinkedList;
import java.util.Queue;

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
    private Sensor mOrientation;
    
    private Location currentLocation;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		getWindow().
		//getWindow().setFormat(PixelFormat.TRANSLUCENT);
		
//		List<NameValuePair> myData = new ArrayList<NameValuePair>();
//
//		myData.add(new BasicNameValuePair("nickname", "isi"));
//		myData.add(new BasicNameValuePair("password", "sbickt"));
//		
//		try {
//			SbicktAPI.login(myData);
//		}
//		catch (Exception e) {}

		setContentView(R.layout.camera_view);
		((ImageButton) findViewById(R.id.live_view)).setVisibility(View.INVISIBLE);
		messagesOverlay = (CameraViewMessagesOverlay) findViewById(R.id.live_view_messages_overlay);
		locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
		
		mSensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        mAccelerometer = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        mOrientation = mSensorManager.getDefaultSensor(Sensor.TYPE_ORIENTATION);
        
	}
	
	@Override
	protected void onResume() {
		super.onResume();
		locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, GPS_REFRESH_TIME_IN_MS, GPS_REFRESH_DISTANCE_IN_M, locationListener);
		mSensorManager.registerListener(accelerometerEventListener, mAccelerometer, SensorManager.SENSOR_DELAY_NORMAL);
		mSensorManager.registerListener(orientationEventListener, mOrientation, SensorManager.SENSOR_DELAY_NORMAL);
		
		currentLocation = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
		if(currentLocation != null){
			Toast.makeText(getBaseContext(), "currentlocation: " + currentLocation.toString(), Toast.LENGTH_LONG).show();
		
			new InitSbicktMessages().execute(currentLocation);
		}
	}
	
	@Override
	protected void onPause() {
		super.onPause();
		locationManager.removeUpdates(locationListener);
		mSensorManager.unregisterListener(accelerometerEventListener);
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
			//new UpdateSbicktMessages().execute(location);
			currentLocation = location;
			messagesOverlay.calculateSbickerlWorldDirection(new Point3D(currentLocation));
			// TODO execute run with new location; download new messages from server
		}
	};
	
	private final SensorEventListener accelerometerEventListener = new SensorEventListener() {
		
		public void onSensorChanged(SensorEvent event) {
			Log.v("accel", "acceleration x: " + event.values[0] + " y: " + event.values[1] + " z: " + event.values[2]);
			messagesOverlay.accelVector = new Vector3D(event.values);
		}
		
		public void onAccuracyChanged(Sensor sensor, int accuracy) {}
	};
	
	private final SensorEventListener orientationEventListener = new SensorEventListener() {
		
		public void onSensorChanged(SensorEvent event) {
			Log.v("orient", "orientation azimuth: " + event.values[0] + " pitch: " + event.values[1] + " roll: " + event.values[2]);
			messagesOverlay.heading = event.values[0];
		}
		
		public void onAccuracyChanged(Sensor sensor, int accuracy) {}
	};
	
	private class UpdateSbicktMessages extends AsyncTask<Location, Void, Queue<GeoTag>>{

		@Override
		protected Queue<GeoTag> doInBackground(Location... params) {
			Queue<GeoTag> geoTags = new LinkedList<GeoTag>();
			
			try {
				geoTags = SbicktAPI.getGeoTags(new Point3D(params[0]));
				//Log.v("alex", geoTags.toString());
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
				messagesOverlay.clear();
				for(GeoTag g : result){
					messagesOverlay.add(g);
				}
				Toast.makeText(getBaseContext(), "received " + result.size() + " elements", Toast.LENGTH_LONG).show();
				messagesOverlay.calculateSbickerlWorldDirection(new Point3D(currentLocation));
				messagesOverlay.start();
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
