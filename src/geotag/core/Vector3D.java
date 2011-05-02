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
}
