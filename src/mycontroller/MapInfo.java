package mycontroller;

/**
 * @Description: This class represents the attributes about the map
 * @Author: Guoen Jin    935833
 *          Yipin Xiong  924608
 */

public class MapInfo
{
	private int[][] maps;
	private int width;
	private int hight;
	private Node start;
	private Node end;
	
	public MapInfo(int[][] maps, int width, int hight, Node start, Node end)
	{
		this.maps = maps;
		this.width = width;
		this.hight = hight;
		this.start = start;
		this.end = end;
	}

	public int[][] getMaps() {
		return maps;
	}

	public int getMapContent(int i,int j){
		return maps[i][j];
	}


	public void setMaps(int[][] maps) {
		this.maps = maps;
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getHight() {
		return hight;
	}

	public void setHight(int hight) {
		this.hight = hight;
	}

	public Node getStart() {
		return start;
	}

	public void setStart(Node start) {
		this.start = start;
	}

	public Node getEnd() {
		return end;
	}

	public void setEnd(Node end) {
		this.end = end;
	}
}
