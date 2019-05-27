package mycontroller;

import tiles.*;
import utilities.Coordinate;

import java.util.HashMap;
import java.util.Map;

public class UpdateMapByView {

    public static void updateExploredMap(int mapWidth,int mapHeight, HashMap<Coordinate, MapTile> currentView, HashMap<Coordinate, MapTile> exploredMap){
        //Update map information
        for (Map.Entry<Coordinate, MapTile> entry : currentView.entrySet()) {
            if (entry.getKey().x >= 0 && entry.getKey().y >= 0 && entry.getKey().x < mapWidth - 1
                    && entry.getKey().y < mapHeight - 1) {
                exploredMap.put(entry.getKey(), entry.getValue());
            }
        }
        return;
    }
}
