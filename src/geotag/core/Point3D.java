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

package geotag.core;

import android.location.Location;

public class Point3D {
	public double lat;
	public double lng;
	public double alt;
	
	public Point3D(double d, double e, double f){
		lat = d;
		lng = e;
		alt = f;
	}
	
	public Point3D(double coordinates[]){
		lat = coordinates[0];
		lng = coordinates[1];
		alt = coordinates[2];
	}
	
	public Point3D(Location location){
		lat = location.getLatitude();
		lng = location.getLongitude();
		alt = location.getAltitude();
	}
	
//	private void calculateWorldVector(){
//		double radius = 6371000.785 + alt;
//		
//		x = (double) (radius * Math.cos(lat) * Math.cos(lng));
//		y = (double) (radius * Math.cos(lat) * Math.sin(lng));
//		z = (double) (radius * Math.sin(lat));
//	}
	
	public Point3DCart getVectorFromMeTo(Location destination){
		double radius = 6371000.785;
		
		double x = (destination.getLatitude() - lat) * radius * Math.PI / 180;
		double y = (destination.getLongitude() - lng) * radius * Math.PI / 180;
		double z = destination.getAltitude() - alt;
		
		return new Point3DCart(x, y, z);
	}
	
	public class Point3DCart {
		public double x, y, z;
		
		public Point3DCart(double d, double e, double f){
			x = d;
			z = e;
			z = f;
		}
	}
}