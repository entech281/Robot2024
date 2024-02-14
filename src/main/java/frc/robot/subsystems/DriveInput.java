package frc.robot.subsystems;

import entech.subsystems.SubsystemInput;
import org.littletonrobotics.junction.AutoLog;
import org.littletonrobotics.junction.LogTable;
import org.littletonrobotics.junction.inputs.LoggableInputs;

import java.util.Objects;

/**
 *
 *
 * @author aheitkamp
 */

public class DriveInput implements SubsystemInput{

    public static final String FORWARD="forward";
    public static final String RIGHT="right";
    public static final String ROTATION="rotation";
    public static final String YAW_ANGLE_DEGREES = "yawAngleDegrees";

    public double forward;
    public double right;
    public double rotation;
    public double yawAngleDegrees = 0.0;


    public DriveInput(double forward, double right, double rotation) {
        this.forward = forward;
        this.right = right;
        this.rotation = rotation;
    }

    public DriveInput(double forward, double right, double rotation, double yawAngleDegrees) {
        this(forward,right,rotation);
        this.yawAngleDegrees = yawAngleDegrees;
    }

    //copy constructor
    public DriveInput(DriveInput original) {
        super();
        this.forward = original.forward;
        this.right = original.right;
        this.rotation = original.rotation;
        this.yawAngleDegrees = original.yawAngleDegrees;
    }


    public double[] get() {
        double[] output = new double[3];

        output[0] = (forward);
        output[1] = (right);
        output[2] = (rotation);

        return output;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof DriveInput)) {
            return false;
        }
        DriveInput driveInput = (DriveInput) o;
        return forward == driveInput.forward && right == driveInput.right && rotation == driveInput.rotation
                && yawAngleDegrees == driveInput.yawAngleDegrees;
    }

    @Override
    public int hashCode() {
        return Objects.hash(forward, right, rotation,yawAngleDegrees);
    }

    @Override
    public void toLog(LogTable table) {
        table.put(FORWARD, forward);
        table.put(RIGHT, right);
        table.put(ROTATION, rotation);
        table.put(YAW_ANGLE_DEGREES, yawAngleDegrees);
    }

    @Override
    public void fromLog(LogTable table) {
        forward = table.get(FORWARD, forward);
        right = table.get(RIGHT, right);
        rotation = table.get(ROTATION, rotation);
        yawAngleDegrees = table.get(YAW_ANGLE_DEGREES, yawAngleDegrees);
    }
}
