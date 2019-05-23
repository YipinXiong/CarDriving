package mycontroller;

import tiles.MapTile;
import utilities.Coordinate;
import world.WorldSpatial;

import java.util.HashMap;

public class HasTargetStrategy implements IStrategy {

    private Coordinate currentPos;
    private Coordinate destination;
    private Coordinate prevCoordinate;
    private HashMap<Coordinate, MapTile> exploredMap;

    public HasTargetStrategy(Coordinate currentPos, Coordinate prevCoordinate, Coordinate destination, HashMap<Coordinate, MapTile> exploredMap) {
        this.currentPos = currentPos;
        this.destination = destination;
        this.prevCoordinate = prevCoordinate;
        this.exploredMap = exploredMap;
    }

    @Override
    public void nextStep(MyAutoController controller) {
        //TODO: Forward towards to the target but no traverse to last orientation;
        return;
    }
}
