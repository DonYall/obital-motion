package main;

public class Mass {
	// Starting position
	private int startX;
	private int startY;
	
	// Gravitational field strength of mass to orbit around
	private double originG;
	
	// Altitude and radius based on mass to orbit around
	protected int d;
	private double originR;
	
	// Gravitational field strength
	protected double g;
	
	// Radius of self
	protected int r;
	
	// Position
	protected int x;
	protected int y;
//	private int mod = 1;
	private double theta = 0;
	
	public Mass(Mass origin, int d, int r) {
		if (origin == null) {
			// We are a stationary mass, do not orbit
			startX = 400;
			startY = 400;
			originG = 0;
			originR = 0;
			g = 0.4;
		} else {
			// We are a mass that orbits around another mass
			startX = (int) (origin.x - origin.r - d);
			startY = origin.y;
			originG = origin.g;
			originR = origin.r;
		}
		
		this.r = r;
		this.d = d;
		
		x = startX;
		y = startY;
	}
	
//	protected void move() {
//		final double v = Math.sqrt(originG * (originR + d));
//		x += mod * v;//(Math.pow(v, 2) - Math.pow(fPrime(), 2));
//		y = startY + mod * f();
//		if (x >= startX + 2 * (originR + d)) {
//			x = (int) (startX + 2 * (originR + d));
//			mod *= -1;
//		} else if (x <= startX) {
//			x = startX;
//			mod *= -1;
//		}
//		System.out.println(x + "," + y);
//	}
//	
//	private int f() {
//		return((int) (Math.sqrt(Math.pow((originR + d), 2) - Math.pow(x - (startX + originR + d), 2))));
//	}
	
	protected void move() {
		theta += Math.sqrt(originG * (originR + d));
	}
	
	protected double getRotationAngle() {
		return theta;
	}
}
