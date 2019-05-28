package routeAlgorithm;

/**
 * @Description: This class represents the attributes about the map
 * @Author: Guoen Jin    935833
 *          Yipin Xiong  924608
 */

public class MapInfo
{
	public int[][] maps;
	public int width;
	public int hight;
	public Node start;
	public Node end;
	
	public MapInfo(int[][] maps, int width, int hight, Node start, Node end)
	{
		this.maps = maps;
		this.width = width;
		this.hight = hight;
		this.start = start;
		this.end = end;
	}
}
