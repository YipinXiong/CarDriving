package mycontroller;

import tiles.MapTile;
import utilities.Coordinate;
import world.WorldSpatial.Direction;

import java.util.HashMap;
import java.util.Map;

public interface IVariantStrategy {
    // Information Expert

    //TODO: implements update explored map by view

    //TODO: integrated A* in this function
    public Direction nextStep(Coordinate currentPos, HashMap<Coordinate, MapTile> currentView, HashMap<Coordinate, MapTile> exploredMap, int foundParcels, int neededParcels);

    //TODO: you can use this function directly.
//    private Coordinate getFinishCoordinate(){
//        //default coordinate
//        Coordinate finish = new Coordinate(1, 1);
//        for(Map.Entry<Coordinate, MapTile> entry: exploredMap().entrySet()){
//            if(entry.getValue().isType(MapTile.Type.FINISH)){
//                finish = entry.getKey();
//                break;
//            }
//        }
//        return finish;
//    };
}
