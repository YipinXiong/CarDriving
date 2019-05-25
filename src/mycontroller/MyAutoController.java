package mycontroller;

import controller.CarController;
import swen30006.driving.Simulation;
import tiles.*;
import world.Car;

import java.util.HashMap;
import java.util.Map;

import utilities.Coordinate;

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
//        System.out.println("getMap size: " + getMap().size());
//        System.out.println("the total size of real Map: " + mapWidth()*mapWidth());
        HashMap<Coordinate, MapTile> currentView = getView();
        int foundParcels = numParcelsFound();
        int neededParcels = numParcels();
        Coordinate currentPos = new Coordinate(getPosition());
        Coordinate nextPos;

        //update map information by view
        updateExploredMapByView(currentView);
        //TODO: remove controller dependency
        nextPos = strategy.nextStep();
        moveTowardsNextCoordinate(nextPos);

        for(Map.Entry<Coordinate, MapTile> ac: getExploredMap().entrySet()){
            System.out.print("(" + ac.getKey().x + "," + ac.getKey().y + ") " + ac.getValue().getType()+"      ");
        }
    }

    private void moveTowardsNextCoordinate(Coordinate nextPos) {
    }

    // Information Expert
    private void updateExploredMapByView(HashMap<Coordinate, MapTile> currentView) {
        UpdateMapByView.updateExploredMap(currentView, getExploredMap());
    }

    public HashMap<Coordinate, MapTile> getExploredMap() {
        if (exploredMap == null) {
            exploredMap = getMap();
        }
        return exploredMap;
    }

    private Coordinate getFinishCoordinate(){
        //default coordinate
        Coordinate finish = new Coordinate(1, 1);
        for(Map.Entry<Coordinate, MapTile> entry: getExploredMap().entrySet()){
            if(entry.getValue().isType(MapTile.Type.FINISH)){
                finish = entry.getKey();
                break;
            }
        }
        return finish;
    };
}
