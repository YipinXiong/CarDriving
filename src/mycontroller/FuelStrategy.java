package mycontroller;

import tiles.*;
import utilities.Coordinate;
import world.WorldSpatial;
import world.WorldSpatial.Direction;
import a.test.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FuelStrategy implements IVariantStrategy {

    public static enum actionState { LEAVE, START, PICKUP, EXPLOIT };
    private actionState currentState=actionState.START ;
    private static int MAX_FUEL = 250;
    //private boolean randomMode = false;
    private boolean atTargetPosition = false;
   // private boolean parcelAtView = false;
    private boolean firstSearch = true;

    @Override
    public Direction nextStep(int mapWidth, int mapHeight, Coordinate currentPos, HashMap<Coordinate, MapTile> currentView, HashMap<Coordinate, MapTile> exploredMap, int foundParcels, int neededParcels) {
        //Update map by the insight of car
        UpdateMapByView.updateExploredMap(mapWidth, mapHeight, currentView, exploredMap);

        //Initialized simulate map
        int[][] simulateMap = new int[mapWidth][mapHeight];
        simulateMap = updateSimulateMap(simulateMap,exploredMap);



        if(foundParcels>=neededParcels){
            //changeMode(false);
            changeState(actionState.LEAVE);

        }





        Coordinate finishPosition = getFinishCoordinate(exploredMap);

        Coordinate parcelPosition = getParcelCoordinate(exploredMap);

         changeAtTargetPosition(currentPos.toString().equals(finishPosition.toString()));

         if(atTargetPosition==true){
             changeFirstSearch(false);
         }


         // Some parcels are unreachable
         filterUnavailableParcel(simulateMap,exploredMap,currentPos);


        if(foundParcels<neededParcels&&(getParcelCoordinate(exploredMap)!=null)){
            changeState(actionState.PICKUP);
        }


        if(atTargetPosition&&foundParcels<neededParcels){
            changeState(actionState.EXPLOIT);
        }

        if(currentState==actionState.PICKUP&&(getParcelCoordinate(exploredMap)==null)){
            if(firstSearch==false){
                changeState(actionState.EXPLOIT);
            }
            else{
                changeState(actionState.START);
            }
        }


//        while(parcelPosition!=null){
//
//
//            Direction direction=null;
//            direction = Test.test(simulateMap, currentPos.x, currentPos.y, parcelPosition.x, parcelPosition.y);
//            if(direction!=Direction.EAST&&direction!=Direction.WEST&&direction!=Direction.NORTH&&direction!=Direction.SOUTH){
//                exploredMap.put(parcelPosition,new MapTile(MapTile.Type.WALL) );
//                break;
//            }
//            else{
//                changeMode(false);
//                break;
//            }
//        }
        Direction outputDirection = null;
           switch(currentState) {
               case LEAVE:
                   outputDirection = Test.test(simulateMap, currentPos.x, currentPos.y, finishPosition.x, finishPosition.y);
                   if(outputDirection == null){
                       outputDirection=Direction.NORTH;
                   }
                   break;

               case START:
                   outputDirection = Test.test(simulateMap, currentPos.x, currentPos.y, finishPosition.x, finishPosition.y);
                   break;

               case PICKUP:
                   outputDirection = Test.test(simulateMap, currentPos.x, currentPos.y, parcelPosition.x, parcelPosition.y);
                   break;

               case EXPLOIT:
                   do {

                       Coordinate randomPosition = getRandomCoordinate(currentPos, mapWidth, mapHeight, exploredMap);

                       outputDirection = Test.test(simulateMap, currentPos.x, currentPos.y, randomPosition.x, randomPosition.y);
                   }while(outputDirection!=Direction.EAST&&outputDirection!=Direction.WEST&&outputDirection!=Direction.NORTH&&outputDirection!=Direction.SOUTH);


                   break;





           }

        return outputDirection;


//
//
//
//        if(atTargetPosition||randomMode==true) {
//
//
//            changeMode(true);
//            Direction direction=null;
//            do {
//
//                Coordinate randomPosition = getRandomCoordinate(currentPos, mapWidth, mapHeight, exploredMap);
//
//                direction = Test.test(simulateMap, currentPos.x, currentPos.y, randomPosition.x, randomPosition.y);
//            }while(direction!=Direction.EAST&&direction!=Direction.WEST&&direction!=Direction.NORTH&&direction!=Direction.SOUTH);
//
//
//                return direction;
//            }
//
////
////            Direction direction = null;
////
////            Coordinate randomPosition = getRandomCoordinate(mapWidth,mapHeight,exploredMap);
////
////            Boolean isWall = exploredMap.get(randomPosition).isType(MapTile.Type.WALL);
////
////            System.out.println(isWall);
////
////            while(Test.test(simulateMap, currentPos.x, currentPos.y,randomPosition.x ,randomPosition.y)==null) {
////
////                while (isWall == true) {
////                    randomPosition = getRandomCoordinate(mapWidth, mapHeight, exploredMap);
////
////                    isWall = exploredMap.get(randomPosition).isType(MapTile.Type.WALL);
////                }
////            }
////            System.out.println(isWall);
////           // do{
////               //
////               // System.out.println(randomPosition.toString());
////              //  System.out.println(currentPos.toString());
////              //  direction = Test.test(simulateMap, currentPos.x, currentPos.y, 3,3);
////              //  if(direction!=null) break;
////               // System.out.println(direction);
////           // }while(true);
////
////
////            return Test.test(simulateMap, currentPos.x, currentPos.y,randomPosition.x ,randomPosition.y);
//      //  }
//
//
//
//
//        if (parcelPosition == null||foundParcels>=neededParcels) {
//
//
//            Direction direction = null;
//            direction = Test.test(simulateMap, currentPos.x, currentPos.y, finishPosition.x, finishPosition.y);
//
//
//            return direction;
//        }
//
//        else {
//            Direction direction = null;
//            direction = Test.test(simulateMap, currentPos.x, currentPos.y, parcelPosition.x, parcelPosition.y);
//            return direction;
//        }
    }



        private Coordinate getFinishCoordinate (HashMap < Coordinate, MapTile > exploredMap){
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


    private Coordinate getRandomCoordinate (Coordinate currentPos,int mapWidth, int mapHeight,HashMap < Coordinate, MapTile > exploredMap) {
        //default coordinate

        Coordinate random = null;
            int randomX = (int) (0 + Math.random() * ((mapWidth-1) - 0 + 1));
            int randomY = (int) (0 + Math.random() * ((mapHeight-1) - 0 + 1));

            if(randomX==currentPos.x&&randomY==currentPos.y){
                randomX=1;
            }

            random = new Coordinate(randomX, randomY);

        return random;

    }
        private Coordinate getParcelCoordinate (HashMap < Coordinate, MapTile > exploredMap){
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

    // Initialized simulate map
    // 1 represents wall, 0 represents other factors because we don't care health in this mode
    public int[][] updateSimulateMap(int[][] simulateMap,HashMap<Coordinate, MapTile> exploredMap){
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



    public void filterUnavailableParcel(int[][] simulateMap,HashMap<Coordinate, MapTile> exploredMap,Coordinate currentPos){
        Coordinate parcelPosition = getParcelCoordinate(exploredMap);
        while(parcelPosition!=null){


            Direction direction=null;
            direction = Test.test(simulateMap, currentPos.x, currentPos.y, parcelPosition.x, parcelPosition.y);
            if(direction!=Direction.EAST&&direction!=Direction.WEST&&direction!=Direction.NORTH&&direction!=Direction.SOUTH){
                exploredMap.put(parcelPosition,new MapTile(MapTile.Type.WALL) );
                break;
            }
            else{
               // changeMode(false);
                break;
            }
        }
    }


//    public void changeMode(boolean mode){
//        this.randomMode= mode;
//        }

    public void changeAtTargetPosition(boolean atTarget){
        this.atTargetPosition= atTarget;
    }

//    public void changeParcelAtView(boolean atView){
//        this.parcelAtView= atView;
//    }

    public void changeState(actionState state){
        this.currentState=state;
    }

    public void changeFirstSearch(boolean search){
        this.firstSearch=search;
    }
    }
