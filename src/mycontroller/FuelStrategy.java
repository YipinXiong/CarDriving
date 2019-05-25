package mycontroller;

import tiles.MapTile;
import utilities.Coordinate;
import world.WorldSpatial.Direction;

import java.util.HashMap;

public class FuelStrategy implements IVariantStrategy{
    private static int MAX_FUEL = 250;

    @Override
    public Direction nextStep(Coordinate currentPos, HashMap<Coordinate, MapTile> currentView, HashMap<Coordinate, MapTile> exploredMap, int foundParcels, int neededParcels) {
        return null;
    }
}
