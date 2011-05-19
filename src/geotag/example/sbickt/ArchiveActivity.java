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

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import android.app.ListActivity;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class ArchiveActivity extends ListActivity {
	
	private SbicktMessagesListAdapter adapter;
	  
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.archive_view);
		
		new InitSbicktMessages().execute();
		
		adapter = new SbicktMessagesListAdapter(getApplicationContext());
		setListAdapter(adapter);
		
	}
	
	
	
	
	
	/* messages should later be saved on device, own class for updates => change sbicktAPI */
	private class UpdateSbicktMessages extends AsyncTask<Void, Void, LinkedList<GeoTag>>{

		@Override
		protected LinkedList<GeoTag> doInBackground(Void... params) {
			LinkedList<GeoTag> geoTags = new LinkedList<GeoTag>();
			
			try {
				geoTags = SbicktAPI.getGeoTags(new Point3D(47.787792, 12.967282, 0));
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			return geoTags;
		}
		
		@Override
		protected void onPostExecute(LinkedList<GeoTag> result) {
			if(result.isEmpty()){
				Toast.makeText(getBaseContext(), "no messages received", Toast.LENGTH_LONG).show();
			}
			else {				
				Toast.makeText(getBaseContext(), "received: " + result.size() + " messages", Toast.LENGTH_LONG).show();
				adapter.geotags = result;
				adapter.notifyDataSetChanged();
			}
		}
		
	}
	
	private class InitSbicktMessages extends UpdateSbicktMessages {

		private ProgressDialog sbicktMessagesDialog = new ProgressDialog(ArchiveActivity.this);
		
		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			sbicktMessagesDialog.setMessage("lade s'bickt Nachrichten...");
			sbicktMessagesDialog.show();
		}
		
		@Override
		protected void onPostExecute(LinkedList<GeoTag> result) {
			super.onPostExecute(result);
			sbicktMessagesDialog.dismiss();
		}
		
	}
	
	private class SbicktMessagesListAdapter extends BaseAdapter {
		
		public LinkedList<GeoTag> geotags;
		private Context context;
		
		public SbicktMessagesListAdapter(Context context){
			this.context = context;
			geotags = new LinkedList<GeoTag>();
		}

		public int getCount() {
			return geotags.size();
		}

		public Object getItem(int position) {
			return geotags.get(position);
		}

		public long getItemId(int position) {
			return geotags.get(position).getId();
		}

		public View getView(int position, View convertView, ViewGroup parent) {
			GeoTag geotag = geotags.get(position);
			
			TextView itemLayout = (TextView) LayoutInflater.from(context).inflate(R.layout.archive_list_item, parent, false);
			itemLayout.setText(geotag.getContent());
			
			return itemLayout;
		}
		
	} 
	
	
}
