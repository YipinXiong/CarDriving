package mycontroller;

import tiles.MapTile;
import utilities.Coordinate;
import world.WorldSpatial.Direction;

import java.util.HashMap;

public class HealthStrategy implements IVariantStrategy {
    private static int MAX_HEALTH = 100;

    @Override
    public Direction nextStep(Coordinate currentPos, HashMap<Coordinate, MapTile> currentView, HashMap<Coordinate, MapTile> exploredMap, int foundParcels, int neededParcels) {
        return null;
    }
}
