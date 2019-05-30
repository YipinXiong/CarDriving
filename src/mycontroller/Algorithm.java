package mycontroller;
import world.WorldSpatial;

import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;

/**
 * @Description: This class contains the logic about the route algorithm
 * @Author: Guoen Jin    935833
 *          Yipin Xiong  924608
 */

public class Algorithm
{
	private  static final int WALL = 1;
	private  static final int ROAD = 2;
	private  static final int ROAD_COST = 10;
	private  static final int TRAP_COST = 100;
	private  static final int HEAL_COST = 1;
	
	Queue<Node> openList = new PriorityQueue<Node>();
	List<Node> closeList = new ArrayList<Node>();
	

	public WorldSpatial.Direction start(MapInfo mapInfo)
	{
		if(mapInfo==null){
			return null;
		}
		openList.clear();
		closeList.clear();
		openList.add(mapInfo.getStart());
		return moveNodes(mapInfo);
	}

	private WorldSpatial.Direction moveNodes(MapInfo mapInfo)
	{
		WorldSpatial.Direction direction=null;
		String win="no";
		while (!openList.isEmpty())
		{
			if (isCoordInClose(mapInfo.getEnd().getCoord())) {
				direction=drawPath(mapInfo.getMaps(), mapInfo.getEnd(),mapInfo.getStart());
				win="yes";
				break;
			}
			Node current = openList.poll();
			closeList.add(current);
			addNeighborNodeInOpen(mapInfo,current);
		}
		if(win.equals("no")) {
			return null;
	}else {
			return direction;
	}
	}

	private WorldSpatial.Direction drawPath(int[][] maps, Node end,Node start)
	{List<WorldSpatial.Direction> route = new ArrayList<WorldSpatial.Direction>();
		if(end==null||maps==null) {
			return null;
		}
		while (end != null)
		{
			Coord c = end.getCoord();
			maps[c.getY()][c.getX()] = ROAD;
			if(end!=start) {
			if(end.getParent().getCoord().getX()-end.getCoord().getX()==1) {
				route.add(WorldSpatial.Direction.SOUTH);
			}
			else if(end.getParent().getCoord().getX()-end.getCoord().getX()==-1) {
				route.add(WorldSpatial.Direction.NORTH);
			}
			else if(end.getParent().getCoord().getY()-end.getCoord().getY()==1) {
				route.add(WorldSpatial.Direction.WEST);
			}
			else if(end.getParent().getCoord().getY()-end.getCoord().getY()==-1) {
				route.add(WorldSpatial.Direction.EAST);
			}
			}
			end = end.getParent();
		}
		return (route.get(route.size()-1));
	}


	private void addNeighborNodeInOpen(MapInfo mapInfo,Node current)
	{
		int x = current.getCoord().getX();
		int y = current.getCoord().getY();
		addNeighborNodeInOpen(mapInfo,current, x - 1, y, ROAD_COST);
		addNeighborNodeInOpen(mapInfo,current, x, y - 1, ROAD_COST);
		addNeighborNodeInOpen(mapInfo,current, x + 1, y, ROAD_COST);
		addNeighborNodeInOpen(mapInfo,current, x, y + 1, ROAD_COST);
	}


	private void addNeighborNodeInOpen(MapInfo mapInfo,Node current, int x, int y, int value)
	{
		if (canAddNodeToOpen(mapInfo,x, y)) {
			Node end=mapInfo.getEnd();
			Coord coord = new Coord(x, y);
			int G = current.getG();
			if (mapInfo.getMapContent(y,x) == 3) {
				G = current.getG() + TRAP_COST;
			}
			else if(mapInfo.getMapContent(y,x) == 4) {
				G = current.getG() + HEAL_COST;
			}
			else {
				G = current.getG() + value;
			}
			Node child = findNodeInOpen(coord);
			if (child == null) {
				int H=calcH(end.getCoord(),coord);
				if(isEndNode(end.getCoord(),coord)) {
					child=end;
					child.setParent(current);
					child.setG(G);
					child.setH(H);
				}
				else {
					child = new Node(coord, current, G, H);
				}
				openList.add(child);
			}
			else if (child.getG() > G) {
				child.setG(G);
				child.setParent(current);
				openList.add(child);
			}
		}
	}


	private Node findNodeInOpen(Coord coord)
	{
		if (coord == null || openList.isEmpty()) {
			return null;
		}
		for (Node node : openList)
		{
			if (node.getCoord().equals(coord)) {
				return node;
			}
		}
		return null;
	}



	private int calcH(Coord end,Coord coord)
	{
		return Math.abs(end.getX() - coord.getX())
				+ Math.abs(end.getY() - coord.getY());
	}

	private boolean isEndNode(Coord end,Coord coord)
	{
		return coord != null && end.equals(coord);
	}


	private boolean canAddNodeToOpen(MapInfo mapInfo,int x, int y)
	{
		if (x < 0 || x >= mapInfo.getWidth() || y < 0 || y >= mapInfo.getHight()) {
			return false;
		}
		if (mapInfo.getMapContent(y,x) == WALL) {
			return false;
		}
		if (isCoordInClose(x, y)) {
			return false;
		}
		return true;
	}


	private boolean isCoordInClose(Coord coord)
	{
		return coord!=null&&isCoordInClose(coord.getX(), coord.getY());
	}


	private boolean isCoordInClose(int x, int y)
	{
		if (closeList.isEmpty()) {
			return false;
		}
		for (Node node : closeList)
		{
			if (node.getCoord().getX() == x && node.getCoord().getY() == y) {
				return true;
			}
		}
		return false;
	}
}
