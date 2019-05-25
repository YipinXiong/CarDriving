package mycontroller;

import tiles.*;
import utilities.Coordinate;

import java.util.HashMap;
import java.util.Map;

public class UpdateMapByView {

    public static void updateExploredMap( HashMap<Coordinate, MapTile> currentView, HashMap<Coordinate, MapTile> exploredMap){
        //Update map information
        for (Map.Entry<Coordinate, MapTile> entry : currentView.entrySet()) {
            exploredMap.put(entry.getKey(), entry.getValue());
        }
        return;
    }
}
