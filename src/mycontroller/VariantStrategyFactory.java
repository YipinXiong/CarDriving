package mycontroller;
import swen30006.driving.Simulation.StrategyMode;


/*
    this factory will determine which strategy to use based on conserved variant.
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
