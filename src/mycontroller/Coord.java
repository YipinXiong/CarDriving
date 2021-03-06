package mycontroller;

/**
 * @Description: This class represents the coordinate of nodes
 * @Author: Guoen Jin    935833
 *          Yipin Xiong  924608
 */

public class Coord
{

	private int x;
	private int y;

	public Coord(int x, int y)
	{
		this.x = x;
		this.y = y;
	}

	@Override
	public boolean equals(Object obj)
	{
		if (obj == null) {
			return false;
		}
		if (obj instanceof Coord) {
			Coord c = (Coord) obj;
			return x == c.x && y == c.y;
		}
		return false;
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}
}
