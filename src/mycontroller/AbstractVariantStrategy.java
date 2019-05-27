package mycontroller;

import routeAlgorithm.test.Test;
import tiles.MapTile;
import tiles.ParcelTrap;
import utilities.Coordinate;
import world.WorldSpatial.Direction;
import java.util.HashMap;
import java.util.Map;

import static mycontroller.ActionState.*;

public abstract class AbstractVariantStrategy implements IVariantStrategy {
    //TODO 单独建一个类
//    LEAVE代表收集了足够的包裹，直接前往终点。
//    PICKUP表示没有收集到足够的包裹，此时在视野中出现了可到达的包裹，前去收集。
//    START代表默认去终点的探索过程。
//    EXPLOIT代表已经到了终点，但是包裹不够，这时进入随机探索模式。
    private boolean atTargetPosition = false;
    private boolean firstSearch = true;
    private ActionState currentState = START;
    /*
     * @para TODO 作者，介绍，整体思路
     * */

    public abstract int[][] updateSimulateMap(int[][] simulateMap, HashMap<Coordinate, MapTile> exploredMap);

    @Override
    public Direction nextStep(int mapWidth, int mapHeight, Coordinate currentPos, HashMap<Coordinate, MapTile> currentView, HashMap<Coordinate, MapTile> exploredMap, int foundParcels, int neededParcels) {
        //Update map by the insight of car
        UpdateMapByView.updateExploredMap(mapWidth, mapHeight, currentView, exploredMap);

        //Initialized simulate map
        int[][] simulateMap = new int[mapWidth][mapHeight];
        simulateMap = updateSimulateMap(simulateMap, exploredMap);


        //If have enough parcel, change state to LEAVE
        if (foundParcels >= neededParcels) {
            changeState(LEAVE);

        }

        //Get LEAVE coordinate
        Coordinate finishPosition = getFinishCoordinate(exploredMap);

        //Get parecl coordinate within view(if any)
        Coordinate parcelPosition = getParcelCoordinate(exploredMap);

        //Check if we at the LEAVE point
        changeAtTargetPosition(currentPos.toString().equals(finishPosition.toString()));

        // Check whether the car is at the destinition
        if (atTargetPosition == true) {
            changeFirstSearch(false);
        }

        // Filter unreachable parcels
        filterUnavailableParcel(simulateMap, exploredMap, currentPos);


        //If we don't have enough parcels and there are parcels within view, go to pick up the reachable parcel
        if (foundParcels < neededParcels && (getParcelCoordinate(exploredMap) != null)) {
            changeState(PICKUP);
        }

        //If you reached destination without enough parcels, explore map randomly.
        if (atTargetPosition && foundParcels < neededParcels) {
            changeState(ActionState.EXPLORE);
        }

        // Continue default exploring
        if (currentState == PICKUP && (getParcelCoordinate(exploredMap) == null)) {
            if (firstSearch == false) {
                changeState(ActionState.EXPLORE);
            } else {
                changeState(START);
            }
        }
        Direction outputDirection = null;
        switch (currentState) {
            case LEAVE:
                outputDirection = Test.test(simulateMap, currentPos.x, currentPos.y, finishPosition.x, finishPosition.y);
                if (outputDirection == null) {
                    outputDirection = Direction.NORTH;
                }
                break;
            case START:
                outputDirection = Test.test(simulateMap, currentPos.x, currentPos.y, finishPosition.x, finishPosition.y);
                break;

            case PICKUP:
                outputDirection = Test.test(simulateMap, currentPos.x, currentPos.y, parcelPosition.x, parcelPosition.y);
                break;

            case EXPLORE:
                do {
                    Coordinate randomPosition = getRandomCoordinate(currentPos, mapWidth, mapHeight, exploredMap);
                    outputDirection = Test.test(simulateMap, currentPos.x, currentPos.y, randomPosition.x, randomPosition.y);
                } while (outputDirection != Direction.EAST && outputDirection != Direction.WEST && outputDirection != Direction.NORTH && outputDirection != Direction.SOUTH);

                break;
        }
        return outputDirection;
    }

    private Coordinate getFinishCoordinate(HashMap<Coordinate, MapTile> exploredMap) {
        //default coordinate
        Coordinate finish = new Coordinate(1, 1);
        for (Map.Entry<Coordinate, MapTile> entry : exploredMap.entrySet()) {
            if (entry.getValue().isType(MapTile.Type.FINISH)) {
                finish = entry.getKey();
                break;
            }
        }
        return finish;
    }

    private Coordinate getRandomCoordinate(Coordinate currentPos, int mapWidth, int mapHeight, HashMap<Coordinate, MapTile> exploredMap) {
        Coordinate random = null;
        int randomX = (int) (0 + Math.random() * ((mapWidth - 1) - 0 + 1));
        int randomY = (int) (0 + Math.random() * ((mapHeight - 1) - 0 + 1));
        if (randomX == currentPos.x && randomY == currentPos.y) {
            randomX = 1;
        }
        random = new Coordinate(randomX, randomY);
        return random;
    }

    private Coordinate getParcelCoordinate(HashMap<Coordinate, MapTile> exploredMap) {
        //default coordinate
        Coordinate parcel = null;
        for (Map.Entry<Coordinate, MapTile> entry : exploredMap.entrySet()) {
            if (entry.getValue() instanceof ParcelTrap) {
                parcel = entry.getKey();
                break;
            }
        }
        return parcel;
    }

    public void filterUnavailableParcel(int[][] simulateMap, HashMap<Coordinate, MapTile> exploredMap, Coordinate currentPos) {
        Coordinate parcelPosition = getParcelCoordinate(exploredMap);
        while (parcelPosition != null) {
            Direction direction = null;
            direction = Test.test(simulateMap, currentPos.x, currentPos.y, parcelPosition.x, parcelPosition.y);
            if (direction != Direction.EAST && direction != Direction.WEST && direction != Direction.NORTH && direction != Direction.SOUTH) {
                exploredMap.put(parcelPosition, new MapTile(MapTile.Type.WALL));
                break;
            } else {
                break;
            }
        }
    }

    public void changeAtTargetPosition(boolean atTarget) {
        this.atTargetPosition = atTarget;
    }

    public void changeState(ActionState state) {
        this.currentState = state;
    }

    public void changeFirstSearch(boolean search) {
        this.firstSearch = search;
    }

}
