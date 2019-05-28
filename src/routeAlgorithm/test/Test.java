package routeAlgorithm.test;

import routeAlgorithm.*;
import world.WorldSpatial;

/**
 * @Description: This is the entry of route simulator.
 *               The detail of route simulator is being hidden.
 * @Author: Guoen Jin    935833
 *          Yipin Xiong  924608
 */

public class Test
{

	public static WorldSpatial.Direction test(int [][]maps, int currentX, int currentY, int finishX, int finishY)
	{
		WorldSpatial.Direction direction=null;
		MapInfo info=new MapInfo(maps,maps[0].length, maps.length,new Node(currentY,currentX),new Node(finishY,finishX));
		if(currentX==finishX&&currentY==finishY){
			return null;
		}
		direction= new Algorithm().start(info);
		return direction;
	}


}
