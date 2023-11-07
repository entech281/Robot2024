package frc.robot.subsystems;

import edu.wpi.first.util.sendable.SendableBuilder;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.PneumaticsModuleType;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import entech.subsystems.EntechSubsystem;
import frc.robot.RobotConstants;

public class GripperSubsystem extends EntechSubsystem {

    private DoubleSolenoid gripperSolenoid;

    private int gripperSolenoidCounter;
    private GripperState gripperState;

    public GripperState getGripperState() {
        return gripperState;
    }

    private static final boolean ENABLED = true;
    private final int SOLENOID_HIT_COUNT = 20;

    public enum GripperState {
        kClose, kOpen, kUnknown
    }

    @Override
    public void initialize() {
        if (ENABLED) {
            gripperSolenoid = new DoubleSolenoid(PneumaticsModuleType.REVPH,
                    RobotConstants.PNEUMATICS.GRIPPER_OPEN,
                    RobotConstants.PNEUMATICS.GRIPPER_CLOSE);
            gripperState = GripperState.kClose;
        }

    }

    @Override
    public void periodic() {
        if (ENABLED) {
            handleSolenoid();

            // SmartDashboard.putString("Gripper Command", getCurrentCommand().toString());
            SmartDashboard.putString("Gripper State", gripperState.toString());
        }
    }

    public void setSolenoids(DoubleSolenoid.Value newValue) {
        gripperSolenoid.set(newValue);
    }

    public void toggleGripper() {
        if (getGripperState() == GripperState.kOpen) {
            setGripperState(GripperState.kClose);
        } else {
            setGripperState(GripperState.kOpen);
        }
    }

    @Override
    public void initSendable(SendableBuilder builder) {
        if (ENABLED) {
            builder.setSmartDashboardType(getName());
            builder.addBooleanProperty("GripperOpen", this::isOpen, this::setOpen);
        }
    }

    private void handleSolenoid() {
        if (gripperSolenoidCounter < SOLENOID_HIT_COUNT) {
            gripperSolenoidCounter += 1;
            if (gripperState == GripperState.kOpen) {
                setSolenoids(DoubleSolenoid.Value.kForward);
            } else if (gripperState == GripperState.kClose) {
                setSolenoids(DoubleSolenoid.Value.kReverse);
            } else {
                setSolenoids(DoubleSolenoid.Value.kOff);
            }
        } else {
            setSolenoids(DoubleSolenoid.Value.kOff);
        }
    }

    public void setGripperState(GripperState state) {
        // this guards against shorting a solenoid by holding it too long/often
        if (state != gripperState) {
            gripperSolenoidCounter = 0;
            gripperState = state;
        }
    }

    public boolean isOpen() {
        return gripperState == GripperState.kOpen;
    }

    public void setOpen(boolean open) {
        // VERY IMPORTANT to use setGripperState here,
        // so we only trigger solendoids on a changed value
        if (open) {
            setGripperState(GripperState.kOpen);
        } else {
            setGripperState(GripperState.kClose);
        }
    }

    @Override
    public boolean isEnabled() {
        return ENABLED;
    }
}
