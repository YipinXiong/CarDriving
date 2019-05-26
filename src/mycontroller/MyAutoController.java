package mycontroller;

import controller.CarController;
import swen30006.driving.Simulation;
import tiles.*;
import world.Car;
import java.util.HashMap;
import utilities.Coordinate;
import world.WorldSpatial.Direction;

public class MyAutoController extends CarController {

    private static int STOP = 0;
    private static HashMap<Coordinate, MapTile> exploredMap = null;
    private VariantStrategyFactory variantStrategyFactory;
    private IVariantStrategy strategy;
    private HashMap<Direction, Direction> oppositeDirection;
    private HashMap<Direction, Direction> relativeRightDirection;
    private Boolean isReverse;


    public MyAutoController(Car car) {
        super(car);
        this.oppositeDirection = initOppositeDirection();
        this.relativeRightDirection = initRelativeRightDirection();
        this.isReverse = false;
        this.variantStrategyFactory = new VariantStrategyFactory();
        this.strategy = variantStrategyFactory.getStrategy(Simulation.toConserve());
    }

    // Coordinate initialGuess;
    @Override
    public void update() {
        
        HashMap<Coordinate, MapTile> currentView = getView();
        HashMap<Coordinate, MapTile> exploredMap = getExploredMap();
        int foundParcels = numParcelsFound();
        int neededParcels = numParcels();
        Coordinate currentPos = new Coordinate(getPosition());

        Boolean isStop = (int) getSpeed() == STOP? true: false;
        Direction currentDir = getOrientation();

        Direction nextDirection = strategy.nextStep(currentPos, currentView, exploredMap, foundParcels, neededParcels);

        isReverse = toNextDirAndCheckReverse(nextDirection, currentDir, isStop, isReverse);
    }

    private Boolean toNextDirAndCheckReverse(Direction nextDir, Direction currentDir, Boolean isStop, Boolean isReverse) {

        //#warning: algorithm must return the same result if input the same coordinate
        Boolean isOppoDir = nextDir.equals(oppositeDirection.get(currentDir));

        //This logic has the highest priority to start the car
        if(currentDir.equals(nextDir)) {
            applyForwardAcceleration();
            return false;
        }

        //Not the opposite direction, forwarding to start.
        if(isStop && !isOppoDir) {
            applyForwardAcceleration();
            return false;
        }

        // Only opposite directive can alter the isReverse.
        if(isOppoDir){
            applyReverseAcceleration();
            return true;
        } else {
            if(isReverse){
                if(relativeRightDirection.get(currentDir).equals(nextDir)){
                    turnLeft();
                } else {
                    turnRight();
                }
                return true;
            } else {
                if(relativeRightDirection.get(currentDir).equals(nextDir)){
                    turnRight();
                } else {
                    turnLeft();
                }
                return false;
            }
        }
    }

    private HashMap<Direction, Direction> initOppositeDirection() {
        HashMap<Direction, Direction> oppositeDirection = new HashMap<Direction, Direction>();
        oppositeDirection.put(Direction.EAST, Direction.WEST);
        oppositeDirection.put(Direction.SOUTH, Direction.NORTH);
        oppositeDirection.put(Direction.WEST, Direction.EAST);
        oppositeDirection.put(Direction.NORTH, Direction.SOUTH);
        return oppositeDirection;
    }

    private HashMap<Direction, Direction> initRelativeRightDirection() {
        HashMap<Direction, Direction> relativeLeftDir = new HashMap<Direction, Direction>();
        relativeLeftDir.put(Direction.EAST,Direction.SOUTH);
        relativeLeftDir.put(Direction.SOUTH, Direction.WEST);
        relativeLeftDir.put(Direction.WEST, Direction.NORTH);
        relativeLeftDir.put(Direction.NORTH,Direction.EAST);
        return relativeLeftDir;
    }

    public HashMap<Coordinate, MapTile> getExploredMap() {
        if (exploredMap == null) {
            exploredMap = getMap();
        }
        return exploredMap;
    }

}
