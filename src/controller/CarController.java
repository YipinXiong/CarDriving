package controller;

import java.util.HashMap;

import tiles.MapTile;
import utilities.Coordinate;
import world.Car;
import world.World;
import world.WorldSpatial;

public abstract class CarController {
	
	private Car car;
	
	/**
	 * Instantiates the car
	 * @param car
	 */
	public CarController(Car car){
		this.car = car;
	}
	
	/**
	 * Slows the car down
	 */
	public void applyBrake(){
		car.brake();
	}
	
	/**
	 * Speeds the car up in the forward direction
	 */
	public void applyForwardAcceleration(){
		car.applyForwardAcceleration();
	}
	
	/**
	 * Speeds the car up in the backwards direction
	 */
	public void applyReverseAcceleration(){
		car.applyReverseAcceleration();
	}
	
	/**
	 * Turns the car left
	 */
	public void turnLeft(){
		car.turnLeft();
	}
	
	/**
	 * Turns the car right
	 */
	public void turnRight(){
		car.turnRight();
	}
	
	/**
	 * Retrieves the car's current position
	 */
	public String getPosition(){
		return car.getPosition();
	}
	
	/**
	 * Returns the car's current velocity.
	 */
	public float getSpeed(){
		return car.getSpeed();
	}

//	/**
//	 * Returns the car's current velocity.
//	 */
//	public int getVelocity(){
//		return car.getVelocity();
//	}
	
	/**
	 * Returns the car's current health
	 */
	public float getHealth(){
		return car.getHealth();
	}
	
	/**
	 * Returns the view around your car (this is a VIEW_SQUARExVIEW_SQUARE area)
	 */
	public HashMap<Coordinate,MapTile> getView(){
		return car.getView();
	}
	
	/**
	 * Get the distance the car can see
	 */
	public int getViewSquare(){
		return Car.VIEW_SQUARE;
	}
	
	/**
	 * Number of parcels car needs to exit
	 */
	public int numParcels(){
		return car.targetParcels;
	}
	
	/**
	 * Parcels car already has
	 */
	public int numParcelsFound(){
		return car.numParcelsFound();
	}
	
	/**
	 * Get the current car orientation (North, West, East or South)
	 */
	public WorldSpatial.Direction getOrientation(){
		return car.getOrientation();
	}
	
	/*
	 * Get visible map
	 */
	public HashMap<Coordinate,MapTile> getMap(){
		return World.getMap();
	}
	
	/*
	 * Get map height
	 */
	public int mapHeight() {
		return World.MAP_HEIGHT;
	}
	
	/*
	 * Get map width
	 */
	public int mapWidth() {
		return World.MAP_WIDTH;
	}
	
	/**
	 * This is the required update step for a controller.
	 */
	public abstract void update();
}
//TODO  新建一个二维int数组（利用getmap方法）。 所有墙壁设为1，非墙壁设为0.
//TODO  随机探索：找一个empty进行可达性分析，可以的话就前往。不可以就再找一个前往。
//TODO  看到包裹后进行可达性分析，可以的话前往，不可以的话继续之前的探索。
//TODO  如果包裹满了，去终点