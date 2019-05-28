package mycontroller;
import swen30006.driving.Simulation.StrategyMode;
import tiles.MapTile;
import utilities.Coordinate;

import java.util.HashMap;

/**
 * @Description: This factory will determine which strategy to use based
 *               on conserved variant.
 * @Author: Guoen Jin    935833
 *          Yipin Xiong  924608
 */

public class VariantStrategyFactory {

    public IVariantStrategy getStrategy(StrategyMode mode) {
        IVariantStrategy strategy;

        switch (mode) {
            case FUEL:
                strategy = new FuelStrategy();
                break;
            default:
                strategy = new HealthStrategy();
        }
        return strategy;
    }
}
