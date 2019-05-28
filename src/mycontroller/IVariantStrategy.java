package mycontroller;

import tiles.MapTile;
import utilities.Coordinate;
import world.WorldSpatial.Direction;

import java.util.HashMap;

/**
 * @Description: Interface of strategy
 * @Author: Guoen Jin    935833
 *          Yipin Xiong  924608
 */

public interface IVariantStrategy {

    public Direction nextStep(int mapWidth,int mapHeight,Coordinate currentPos, HashMap<Coordinate, MapTile> currentView, HashMap<Coordinate, MapTile> exploredMap, int foundParcels, int neededParcels);

}
