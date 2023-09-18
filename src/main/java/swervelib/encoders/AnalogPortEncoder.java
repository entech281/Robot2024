package swervelib.encoders;

import edu.wpi.first.wpilibj.AnalogEncoder;
import edu.wpi.first.wpilibj.AnalogInput;
import entech.util.EntechUtils;

public class AnalogPortEncoder extends SwerveAbsoluteEncoder {
    private AnalogInput analogInput;
    private AnalogEncoder analogEncoder;
    private boolean inverted = false;

    public AnalogPortEncoder(int port) {
        this.analogInput = new AnalogInput(port);
        this.analogEncoder = new AnalogEncoder(analogInput);
    }

    @Override
    public void clearStickyFaults() {
        // Do Nothing
    }

    @Override
    public void configure(boolean inverted) {
        this.inverted = inverted;
    }

    @Override
    public void factoryDefault() {
        // Do Nothing
    }

    @Override
    public Object getAbsoluteEncoder() {
        return analogInput;
    }

    @Override
    public double getAbsolutePosition() {
        // return (inverted ? -1.0 : 1.0)
        // * ((analogInput.getAverageVoltage() / RobotController.getVoltage5V()) *
        // (Math.PI * 2) - Math.PI);
        return EntechUtils.normalizeAngle2(analogEncoder.get() * (inverted ? -360.0 : 360.0));
    }
}
