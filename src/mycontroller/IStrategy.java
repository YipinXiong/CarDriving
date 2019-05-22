package mycontroller;

import tiles.MapTile;
import utilities.Coordinate;

import java.util.HashMap;

public interface IStrategy {

    public void action(int parcelsFound, int numParcels, HashMap<Coordinate, MapTile> currentView, Coordinate currentPosition );
}
