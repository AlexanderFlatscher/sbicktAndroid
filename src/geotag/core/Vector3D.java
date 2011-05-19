package geotag.core;

public class Vector3D {

	public double x, y, z;
	
	public Vector3D(){
		x = y = z = 0;
	}
	
	public Vector3D(double x, double y, double z){
		this.x = x;
		this.y = y;
		this.z = z;
	}
	
	public Vector3D(double[] a){
		this.x = a[0];
		this.y = a[1];
		this.z = a[2];
	}
	
	public Vector3D(Point3D point){
		double radius = 6371000.785 + point.alt;
		
		this.x = (double) (radius * Math.cos(point.lat) * Math.cos(point.lng));
		this.y = (double) (radius * Math.cos(point.lat) * Math.sin(point.lng));
		this.z = (double) (radius * Math.sin(point.lat));
	}
	
	public Vector3D(float[] a) {
		this.x = a[0];
		this.y = a[1];
		this.z = a[2];
	}
	
	public void worldDirection(Point3D point){
		double radius = 6371000.785 + point.alt;
		
		this.x = (double) (radius * Math.cos(point.lat) * Math.cos(point.lng));
		this.y = (double) (radius * Math.cos(point.lat) * Math.sin(point.lng));
		this.z = (double) (radius * Math.sin(point.lat));
	}
	
	public double dot(Vector3D v){
		return x * v.x + y * v.y + z * v.z;
		
	}
	
	public Vector3D div(double k){
		Vector3D a = new Vector3D();
		
		if(k != 0){
			a.x = x / k;
			a.y = y / k;
			a.z = z / k;
		}
		
		return a;
	}
	
	public Vector3D cross(Vector3D v){
		return new Vector3D(
				y * v.z - z* v.y,
				z * v.x - x * v.z,
				x * v.y - y * v.x);
	}
	
	public double normSquared(){
		return dot(this);
	}
	
	public double norm(){
		return Math.sqrt(normSquared());
	}

	public void normalize(){
		Vector3D norm = div(norm());
		x = norm.x;
		y = norm.y;
		z = norm.z;
	}
}
