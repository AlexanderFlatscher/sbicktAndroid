package geotag.core;

public class Quaternion {
	public double r, i, j, k;
	
    public Quaternion(double r, double i, double j, double k) {
        this.r = r;
        this.i = i;
        this.j = j;
        this.k = k;
    }

    public String toString() {
        return r + " + " + i + "i + " + j + "j + " + k + "k";
    }

    public double norm() {
        return Math.sqrt(r*r + i*i +j*j + k*k);
    }

    public Quaternion conjugate() {
        return new Quaternion(r, -i, -j, -k);
    }

    public Quaternion plus(Quaternion q2) {
        Quaternion q1 = this;
        return new Quaternion(q1.r+q2.r, q1.i+q2.i, q1.j+q2.j, q1.k+q2.k);
    }

    public Quaternion times(Quaternion q2) {
        Quaternion q1 = this;
        double nr = q1.r*q2.r - q1.i*q2.i - q1.j*q2.j - q1.k*q2.k;
        double ni = q1.r*q2.i + q1.i*q2.r + q1.j*q2.k - q1.k*q2.j;
        double nj = q1.r*q2.j - q1.i*q2.k + q1.j*q2.r + q1.k*q2.i;
        double nk = q1.r*q2.k + q1.i*q2.j - q1.j*q2.i + q1.k*q2.r;
        return new Quaternion(nr, ni, nj, nk);
    }

    public Quaternion inverse() {
        double d = r*r + i*i + j*j + k*k;
        return new Quaternion(r/d, -i/d, -j/d, -k/d);
    }

    public Quaternion divides(Quaternion q2) {
        return this.inverse().times(q2);
    }
}
