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

public class GeoTag {
	private Point3D coordinates;
	private String content;
	private String owner;
	private TagVisibility visibility;
	private Integer id;

	public GeoTag(){
		setOwner(null);
		coordinates = new Point3D(0.0, 0.0, 0.0);
		content = null;
		visibility = TagVisibility.PRIVATE;
		id = null;
	}
	
	public GeoTag(Integer id, Point3D point, String owner, String content, TagVisibility visibility){
		this.id = id;
		coordinates = point;
		this.content = content;
		setOwner(owner);
		this.visibility = visibility;
	}
	
	public String getContent() {
		return content;
	}

	public void setContent(String text) {
		if(text == content){
			return;
		}
		
		content = text;
	}

	public TagVisibility getVisibility() {
		return visibility;
	}

	public void setVisibility(TagVisibility visibility) {
		if(visibility == this.visibility){
			return;
		}
		
		this.visibility = visibility;
	}

	public void setLat(Double lat){
		if(lat == coordinates.lat){
			return;
		}
		
		coordinates.lat = lat;
	}
	
	public Double getLat(){
		return coordinates.lat;
	}
	
	public void setLng(Double lng){
		if(lng == coordinates.lng){
			return;
		}
		
		coordinates.lng = lng;
	}
	
	public Double getLng(){
		return coordinates.lng;
	}
	
	public void setAlt(Double alt){
		if(alt == coordinates.alt){
			return;
		}
		
		coordinates.alt = alt;
	}
	
	public Double getAlt(){
		return coordinates.alt;
	}
	
	public String coordinatesToString(){
		return "lat: " + coordinates.lat + ", lng: " + coordinates.lng + ", alt: " + coordinates.alt;
	}

	public void setOwner(String owner) {
		if(owner == this.owner){
			return;
		}
		
		this.owner = owner;
	}

	public String getOwner() {
		return owner;
	}
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		if(this.id == id){
			return;
		}
		
		this.id = id;
	}

	public void prettyPrint(){
		System.out.println(getId());
		System.out.println(getOwner());
		System.out.println(getContent());
		System.out.println(coordinatesToString());
		System.out.println(getVisibility());
		System.out.print("\n");
	}
}
