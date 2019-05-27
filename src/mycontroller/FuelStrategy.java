package mycontroller;

import tiles.*;
import utilities.Coordinate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class FuelStrategy extends AbstractVariantStrategy {
    // Initialized simulate map
    // 1 represents wall, 0 represents other factors because we don't care health in this mode
    public int[][] updateSimulateMap(int[][] simulateMap, HashMap<Coordinate, MapTile> exploredMap) {
        List<Coordinate> mapKeyList = new ArrayList<Coordinate>(exploredMap.keySet());
        for (Coordinate key : mapKeyList) {
            int xAxis = key.x;
            int yAxis = key.y;
            if (exploredMap.get(key).getType() == tiles.MapTile.Type.WALL) {
                simulateMap[xAxis][yAxis] = 1;
            } else {
                simulateMap[xAxis][yAxis] = 0;
            }
        }
        return simulateMap;
    }
}
