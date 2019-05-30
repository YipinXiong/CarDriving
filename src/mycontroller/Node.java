package mycontroller;

/**
 * @Description: This class represents the nodes used for simulating
 * @Author: Guoen Jin    935833
 *          Yipin Xiong  924608
 */

public class Node implements Comparable<Node>
{
	private Coord coord;
	private Node parent;
	private int G;
	private int H;
	public Node(int x, int y) {
		this.coord = new Coord(x, y);
	}
	public Node(Coord coord, Node parent, int g, int h)
	{
		this.coord = coord;
		this.parent = parent;
		G = g;
		H = h;
	}

	public Coord getCoord() {
		return coord;
	}

	public void setCoord(Coord coord) {
		this.coord = coord;
	}

	public Node getParent() {
		return parent;
	}

	public void setParent(Node parent) {
		this.parent = parent;
	}

	public int getG() {
		return G;
	}

	public void setG(int g) {
		G = g;
	}

	public int getH() {
		return H;
	}

	public void setH(int h) {
		H = h;
	}



	@Override
	public int compareTo(Node node)
	{
		if (node == null) return -1;
		if (G + H > node.G + node.H)
			return 1;
		else if (G + H < node.G + node.H) return -1;
		return 0;
	}



}
