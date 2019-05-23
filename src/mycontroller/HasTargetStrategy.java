package mycontroller;

import tiles.MapTile;
import utilities.Coordinate;
import world.WorldSpatial;

import java.util.HashMap;

public class HasTargetStrategy implements IStrategy {

    private Coordinate currentPos;
    private Coordinate targetPos;
    private WorldSpatial.Direction previousOrnt;
    private HashMap<Coordinate, MapTile> exploredMap;

    public HasTargetStrategy(Coordinate currentPos, Coordinate targetPos, WorldSpatial.Direction previousOrnt, HashMap<Coordinate, MapTile> exploredMap) {
        this.currentPos = currentPos;
        this.targetPos = targetPos;
        this.previousOrnt = previousOrnt;
        this.exploredMap = exploredMap;
    }

    @Override
    public void nextStep(MyAutoController controller) {
        //TODO: Forward towards to the target but no traverse to last orientation;
        return;
    }
}
