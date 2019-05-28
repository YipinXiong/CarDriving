package mycontroller;

import tiles.*;
import utilities.Coordinate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * @Description: In fuel optimize mode, we don't care about health cost and heal.
 *               When creating simulated map, wall is being set to 1, others 0.
 * @Author: Guoen Jin    935833
 *          Yipin Xiong  924608
 */

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
