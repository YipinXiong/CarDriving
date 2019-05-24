package mycontroller;

import controller.CarController;
import world.Car;

import java.util.HashMap;

import tiles.MapTile;
import utilities.Coordinate;
import world.WorldSpatial;

public class MyAutoController extends CarController {

    //Store the map information
    private static HashMap<Coordinate, MapTile> exploredMap = null;

    //Use singleton pattern here

    public MyAutoController(Car car) {
        super(car);
    }

    // Coordinate initialGuess;
    // boolean notSouth = true;
    @Override
    public void update() {

        HashMap<Coordinate, MapTile> currentView = getView();
        int foundParcels = numParcelsFound();
        int neededParcels = numParcels();
        Coordinate currentPos = new Coordinate(getPosition());

        //update map information by view
        updateExploredMapByView(currentView);

        //based on current variables execute different strategy.
        StrategyFactory factory = new StrategyFactory(currentPos, currentView, getExploredMap());
        IStrategy strategy = factory.getStrategy(foundParcels, neededParcels, currentPos);
        strategy.nextStep(this);
    }

    // Information Expert
    private void updateExploredMapByView(HashMap<Coordinate, MapTile> currentView){
        UpdateMapByView.updateExploredMap(currentView, getExploredMap());
    }

    public HashMap<Coordinate, MapTile> getExploredMap() {
        if (exploredMap == null) {
            exploredMap = getMap();
        }
        return exploredMap;
    }
}
