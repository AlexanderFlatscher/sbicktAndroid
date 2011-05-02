package geotag.core;

public class Matrix3D {
	
	public Vector3D a, b, c;
	
	public Matrix3D(){
		a = new Vector3D(1, 0, 0);
		b = new Vector3D(0, 1, 0);
		c = new Vector3D(0, 0, 1);
	}
	
	public Matrix3D(double[] a, double[] b, double[] c){
		this.a = new Vector3D(a);
		this.b = new Vector3D(b);
		this.c = new Vector3D(c);
	}
	
	public Matrix3D(Matrix3D m2){
		this.a = m2.a;
		this.b = m2.b;
		this.c = m2.c;
	}
	
	public double[][] getAs2DArray(){
		return new double[][] {{a.x, b.x, c.x},{a.y, b.y, c.y},{a.z, b.z, c.z}};
	}
	
	public void transponse(){
		Matrix3D temp = new Matrix3D(this);
		
	}

}
