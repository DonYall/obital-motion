public class Mass {
	// Starting position
	private int startX;
	private int startY;

	// Gravitational field strength of mass to orbit around
	protected Mass origin;

	// Altitude based on mass to orbit around
	protected int d;

	// Gravitational field strength
	protected double g;

	// Radius of self
	protected int r;

	// Position
	protected int x;
	protected int y;
	protected double theta = 0;

	public Mass(Mass origin, int d, int r, double g) {
		if (origin == null) {
			// We are a stationary mass, do not orbit
			startX = 400;
			startY = 400;
		} else {
			// We are a mass that orbits around another mass
			startX = (int) (origin.x - origin.r - d);
			startY = origin.y;
			this.origin = origin;
		}
		this.g = g;

		this.r = r;
		this.d = d;

		x = startX;
		y = startY;
	}

	protected void move() {
		if (origin == null) return;
		theta += Math.sqrt(origin.g * (origin.r + d));
		x = (int) (origin.x - (origin.r + d) * Math.cos(Math.toRadians(theta)));
		y = (int) (origin.y - (origin.r + d) * Math.sin(Math.toRadians(theta)));
	}

}
