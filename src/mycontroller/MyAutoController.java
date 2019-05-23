package mycontroller;

import controller.CarController;
import world.Car;

import java.util.HashMap;
import java.util.Map.Entry;

import tiles.HealthTrap;
import tiles.LavaTrap;
import tiles.MapTile;
import tiles.MapTile.Type;
import tiles.ParcelTrap;
import tiles.TrapTile;
import tiles.WaterTrap;
import utilities.Coordinate;
import world.WorldSpatial;

public class MyAutoController extends CarController {

    //Store the map information
    private static HashMap<Coordinate, MapTile> exploredMap = null;

    public HashMap<Coordinate, MapTile> getExploredMap() {
        if (exploredMap == null) {
            exploredMap = getMap();
        }
        return exploredMap;
    }

    // How many minimum units the wall is away from the player.
    private int wallSensitivity = 1;

    private boolean isFollowingWall = false; // This is set to true when the car starts sticking to a wall.

    // Car Speed to move at
    private final int CAR_MAX_SPEED = 1;

    //Use singleton pattern here

    public MyAutoController(Car car) {
        super(car);
        //Initialize explored map
    }

    // Coordinate initialGuess;
    // boolean notSouth = true;
    @Override
    public void update() {

        //update map information by view
        HashMap<Coordinate, MapTile> currentView = getView();
        UpdateMapByView updateMapByView = new UpdateMapByView();
        updateMapByView.updateExploredMap(currentView, getExploredMap(), mapWidth(), mapHeight());

        // checkStateChange();
        if (getSpeed() < CAR_MAX_SPEED) {       // Need speed to turn and progress toward the exit
            applyForwardAcceleration();   // Tough luck if there's a wall in the way
        }
        if (isFollowingWall) {
            // If wall no longer on left, turn left
            if (!checkFollowingWall(getOrientation(), currentView)) {
                turnLeft();
            } else {
                // If wall on left and wall straight ahead, turn right
                if (checkWallAhead(getOrientation(), currentView)) {
                    turnRight();
                }
            }
        } else {
            // Start wall-following (with wall on left) as soon as we see a wall straight ahead
            if (checkWallAhead(getOrientation(), currentView)) {
                turnRight();
                isFollowingWall = true;
            }
        }
    }


    /**
     * Check if you have a wall in front of you!
     *
     * @param orientation the orientation we are in based on WorldSpatial
     * @param currentView what the car can currently see
     * @return
     */
    private boolean checkWallAhead(WorldSpatial.Direction orientation, HashMap<Coordinate, MapTile> currentView) {
        switch (orientation) {
            case EAST:
                return checkEast(currentView);
            case NORTH:
                return checkNorth(currentView);
            case SOUTH:
                return checkSouth(currentView);
            case WEST:
                return checkWest(currentView);
            default:
                return false;
        }
    }

    /**
     * Check if the wall is on your left hand side given your orientation
     *
     * @param orientation
     * @param currentView
     * @return
     */
    private boolean checkFollowingWall(WorldSpatial.Direction orientation, HashMap<Coordinate, MapTile> currentView) {
        switch (orientation) {
            case EAST:
                return checkNorth(currentView);
            case NORTH:
                return checkWest(currentView);
            case SOUTH:
                return checkEast(currentView);
            case WEST:
                return checkSouth(currentView);
            default:
                return false;
        }
    }

    /**
     * Method below just iterates through the list and check in the correct coordinates.
     * i.e. Given your current position is 10,10
     * checkEast will check up to wallSensitivity amount of tiles to the right.
     * checkWest will check up to wallSensitivity amount of tiles to the left.
     * checkNorth will check up to wallSensitivity amount of tiles to the top.
     * checkSouth will check up to wallSensitivity amount of tiles below.
     */
    public boolean checkEast(HashMap<Coordinate, MapTile> currentView) {
        // Check tiles to my right
        Coordinate currentPosition = new Coordinate(getPosition());
        for (int i = 0; i <= wallSensitivity; i++) {
            MapTile tile = currentView.get(new Coordinate(currentPosition.x + i, currentPosition.y));
            if (tile.isType(MapTile.Type.WALL)) {
                return true;
            }
        }
        return false;
    }

    public boolean checkWest(HashMap<Coordinate, MapTile> currentView) {
        // Check tiles to my left
        Coordinate currentPosition = new Coordinate(getPosition());
        for (int i = 0; i <= wallSensitivity; i++) {
            MapTile tile = currentView.get(new Coordinate(currentPosition.x - i, currentPosition.y));
            if (tile.isType(MapTile.Type.WALL)) {
                return true;
            }
        }
        return false;
    }

    public boolean checkNorth(HashMap<Coordinate, MapTile> currentView) {
        // Check tiles to towards the top
        Coordinate currentPosition = new Coordinate(getPosition());
        for (int i = 0; i <= wallSensitivity; i++) {
            MapTile tile = currentView.get(new Coordinate(currentPosition.x, currentPosition.y + i));
            if (tile.isType(MapTile.Type.WALL)) {
                return true;
            }
        }
        return false;
    }

    public boolean checkSouth(HashMap<Coordinate, MapTile> currentView) {
        // Check tiles towards the bottom
        Coordinate currentPosition = new Coordinate(getPosition());
        for (int i = 0; i <= wallSensitivity; i++) {
            MapTile tile = currentView.get(new Coordinate(currentPosition.x, currentPosition.y - i));
            if (tile.isType(MapTile.Type.WALL)) {
                return true;
            }
        }
        return false;
    }

}
