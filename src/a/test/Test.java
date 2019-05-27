package a.test;

import a.*;
import world.WorldSpatial;

public class Test
{

	public static WorldSpatial.Direction test(int [][]maps, int currentX, int currentY, int finishX, int finishY)
	{
		WorldSpatial.Direction direction=null;

		MapInfo info=new MapInfo(maps,maps[0].length, maps.length,new Node(currentY,currentX),new Node(finishY,finishX));
		if(currentX==finishX&&currentY==finishY){
			return null;
		}
		direction= new AStar().start(info);
		return direction;
	}


}
