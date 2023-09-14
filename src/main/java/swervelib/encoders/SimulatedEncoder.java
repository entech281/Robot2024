package swervelib.encoders;

public class SimulatedEncoder extends SwerveAbsoluteEncoder {

    public SimulatedEncoder() {
    }

    @Override
    public void clearStickyFaults() {
        return;
    }

    @Override
    public void configure(boolean inverted) {
    }

    @Override
    public void factoryDefault() {
        // Do nothing?
        return;
    }

    @Override
    public Object getAbsoluteEncoder() {
        return new Object();
    }

    @Override
    public double getAbsolutePosition() {
        return 0;
    }

}
