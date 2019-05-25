package mycontroller;

import controller.CarController;
import swen30006.driving.Simulation;
import tiles.*;
import world.Car;

import java.util.HashMap;
import java.util.Map;

import utilities.Coordinate;
import world.WorldSpatial;
import world.WorldSpatial.Direction;

public class MyAutoController extends CarController {

    //Store the map information
    private static HashMap<Coordinate, MapTile> exploredMap = null;
    private VariantStrategyFactory variantStrategyFactory;
    private IVariantStrategy strategy;
    private Simulation.StrategyMode variantToConserve;

    //Use singleton pattern here

    public MyAutoController(Car car) {
        super(car);
        //The factory will generate a strategy regarding variant to conserve.
        variantToConserve = Simulation.toConserve();
        this.variantStrategyFactory = new VariantStrategyFactory();
        this.strategy = variantStrategyFactory.getStrategy(variantToConserve);
    }

    // Coordinate initialGuess;
    @Override
    public void update() {

        HashMap<Coordinate, MapTile> currentView = getView();
        HashMap<Coordinate, MapTile> exploredMap = getExploredMap();
        int foundParcels = numParcelsFound();
        int neededParcels = numParcels();
        Coordinate currentPos = new Coordinate(getPosition());
        Direction nextDirection;

        nextDirection = strategy.nextStep(currentPos, currentView, exploredMap, foundParcels, neededParcels);

        //TODO: move based on current direction and next direction;
        moveTowardsNextCoordinate(nextDirection, getOrientation());
    }

    private void moveTowardsNextCoordinate(Direction nextDir,Direction currentDir) {
        if(nextDir.equals(currentDir)){
            applyForwardAcceleration();
        } else {

        }
    }


    public HashMap<Coordinate, MapTile> getExploredMap() {
        if (exploredMap == null) {
            exploredMap = getMap();
        }
        return exploredMap;
    }

}
