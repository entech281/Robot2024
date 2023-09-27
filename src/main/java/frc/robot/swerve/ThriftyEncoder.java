package frc.robot.swerve;

import edu.wpi.first.wpilibj.AnalogEncoder;
import edu.wpi.first.wpilibj.AnalogInput;

public class ThriftyEncoder {
    private AnalogEncoder encoder;

    public ThriftyEncoder(int analogPort) {
        encoder = new AnalogEncoder(new AnalogInput(analogPort));
    }

    public double getPosition() {
        return encoder.getAbsolutePosition();
    }
}
