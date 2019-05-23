package mycontroller;

import tiles.MapTile;
import utilities.Coordinate;
import world.WorldSpatial;

import java.util.HashMap;

public class StrategyFactory {

    private HashMap<Coordinate, MapTile> currentView;
    private HashMap<Coordinate, MapTile> exploredMap;
    private WorldSpatial.Direction prevDirection;

    public StrategyFactory(WorldSpatial.Direction prevDirection, HashMap<Coordinate, MapTile> currentView, HashMap<Coordinate, MapTile> exploredMap) {
        this.currentView = currentView;
        this.exploredMap = exploredMap;
        this.prevDirection = prevDirection;
    }

    public IStrategy getStrategy(int currentParcelsNum, int neededParcelsNum, Coordinate currentPosition) {

        Boolean isParcelInView = isParcelInView(currentView);
        Boolean isEnough = currentParcelsNum == neededParcelsNum;
        Coordinate destination = null;
        IStrategy strategy = null;

        if (isEnough || isParcelInView) {

            if (isEnough) {
                // destination: exit
                destination = getExitPosition();
            } else {
                // destination: collect parcel
                destination = getParcelPosition();
            }

            strategy = new HasTargetStrategy(currentPosition, destination, prevDirection, currentView);

        }

        // following walls to explore the map
        strategy = new NoTargetStrategy();

        return strategy;
    }

    // TODO: loop through the currentView to check whether there is a parcel in view.
    private Boolean isParcelInView(HashMap<Coordinate, MapTile> currentView) {
        return false;
    }

    private Coordinate getParcelPosition() {
        return new Coordinate(1, 2);
    }

    //TODO: get one of exit positions
    private Coordinate getExitPosition() {
        return new Coordinate(1, 2);
    }
}
