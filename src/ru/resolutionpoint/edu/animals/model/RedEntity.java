package ru.resolutionpoint.edu.animals.model;

/**
 * Class <code>RedEntity</code> is sample subclass of {@link Entity} class.
 */
public class RedEntity extends Entity {
	
	private int x;
	private int y;
	private int dx = 1;
	private int dy = 1;

    /**
     * Constructs new entity
     *
     * @param environment environment
     */
    public RedEntity(Environment environment) {
		super(environment);
	}

    /**
     * Constructs new entity
     *
     * @param environment environment
     * @param x           initial x-coordinate
     * @param y           initial y-coordinate
     */
    public RedEntity(Environment environment, int x, int y) {
        super(environment);
        this.x = x;
        this.y = y;
    }

	@Override
	public int getX() {
		return x;
	}

	@Override
	public int getY() {
		return y;
	}

    @Override
	public String getImagePath() {
        return "/images/red.gif";
    }

    @Override
	protected void move() {
		x += dx;
		y += dy;
		if (!checkHorizontal(x)) {
			x -= 2 * dx;
			dx = -dx;
		}
		if (!checkVertical(y)) {
			y -= 2 * dy;
			dy = -dy;
		}
	}
}
