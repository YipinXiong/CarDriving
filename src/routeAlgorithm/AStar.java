package routeAlgorithm;
import world.WorldSpatial;

import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;


public class AStar
{
	public  static final int WALL = 1;
	public  static final int ROAD = 2;
	public  static final int ROAD_COST = 10;
	public  static final int TRAP_COST = 100;
	public  static final int HEAL_COST = 1;
	
	Queue<Node> openList = new PriorityQueue<Node>();
	List<Node> closeList = new ArrayList<Node>();
	

	public WorldSpatial.Direction start(MapInfo mapInfo)
	{
		if(mapInfo==null) return null;

		openList.clear();
		closeList.clear();

		openList.add(mapInfo.start);
		return moveNodes(mapInfo);
	}

	private WorldSpatial.Direction moveNodes(MapInfo mapInfo)
	{
		WorldSpatial.Direction direction=null;
		String win="no";
		while (!openList.isEmpty())
		{
			if (isCoordInClose(mapInfo.end.coord))
			{
				direction=drawPath(mapInfo.maps, mapInfo.end,mapInfo.start);
				win="yes";
				break;
			}
			Node current = openList.poll();
			closeList.add(current);
			addNeighborNodeInOpen(mapInfo,current);
		}
		if(win.equals("no")) {
		//System.out.println("fail");
			return null;
	}else {
		//System.out.println("succeed");
			return direction;
	}
	}

	private WorldSpatial.Direction drawPath(int[][] maps, Node end,Node start)
	{List<WorldSpatial.Direction> route = new ArrayList<WorldSpatial.Direction>();
		if(end==null||maps==null) return null;
		//System.out.println("total cost" + end.G);
		while (end != null)
		{
			Coord c = end.coord;
			maps[c.y][c.x] = ROAD;
			
			
			
			if(end!=start) {
			
			if(end.parent.coord.x-end.coord.x==1) {
				route.add(WorldSpatial.Direction.SOUTH);
				
			}
			else if(end.parent.coord.x-end.coord.x==-1) {
				route.add(WorldSpatial.Direction.NORTH);
			}
			else if(end.parent.coord.y-end.coord.y==1) {
				route.add(WorldSpatial.Direction.WEST);
			}
			else if(end.parent.coord.y-end.coord.y==-1) {
				route.add(WorldSpatial.Direction.EAST);
			}
	
			}
			
			
			end = end.parent;
			
	
		
		}
		
		return (route.get(route.size()-1));
	}


	private void addNeighborNodeInOpen(MapInfo mapInfo,Node current)
	{
		int x = current.coord.x;
		int y = current.coord.y;

		addNeighborNodeInOpen(mapInfo,current, x - 1, y, ROAD_COST);

		addNeighborNodeInOpen(mapInfo,current, x, y - 1, ROAD_COST);

		addNeighborNodeInOpen(mapInfo,current, x + 1, y, ROAD_COST);

		addNeighborNodeInOpen(mapInfo,current, x, y + 1, ROAD_COST);

	}


	private void addNeighborNodeInOpen(MapInfo mapInfo,Node current, int x, int y, int value)
	{
		if (canAddNodeToOpen(mapInfo,x, y))
		{
			Node end=mapInfo.end;
			Coord coord = new Coord(x, y);
			int G = current.G;
			if (mapInfo.maps[y][x] == 3) {
				G = current.G + TRAP_COST;
			}
			else if(mapInfo.maps[y][x] == 4) {
				G = current.G + HEAL_COST;
			}
			else {
				G = current.G + value;
			}
			Node child = findNodeInOpen(coord);
			if (child == null)
			{
				int H=calcH(end.coord,coord);
				if(isEndNode(end.coord,coord))
				{
					child=end;
					child.parent=current;
					child.G=G;
					child.H=H;
				}
				else
				{
					child = new Node(coord, current, G, H);
				}
				openList.add(child);
			}
			else if (child.G > G)
			{
				child.G = G;
				child.parent = current;
				openList.add(child);
			}
		}
	}


	private Node findNodeInOpen(Coord coord)
	{
		if (coord == null || openList.isEmpty()) return null;
		for (Node node : openList)
		{
			if (node.coord.equals(coord))
			{
				return node;
			}
		}
		return null;
	}



	private int calcH(Coord end,Coord coord)
	{
		return Math.abs(end.x - coord.x)
				+ Math.abs(end.y - coord.y);
	}

	private boolean isEndNode(Coord end,Coord coord)
	{
		return coord != null && end.equals(coord);
	}


	private boolean canAddNodeToOpen(MapInfo mapInfo,int x, int y)
	{

		if (x < 0 || x >= mapInfo.width || y < 0 || y >= mapInfo.hight) return false;

		if (mapInfo.maps[y][x] == WALL) return false;

		if (isCoordInClose(x, y)) return false;

		return true;
	}


	private boolean isCoordInClose(Coord coord)
	{
		return coord!=null&&isCoordInClose(coord.x, coord.y);
	}


	private boolean isCoordInClose(int x, int y)
	{
		if (closeList.isEmpty()) return false;
		for (Node node : closeList)
		{
			if (node.coord.x == x && node.coord.y == y)
			{
				return true;
			}
		}
		return false;
	}
}
