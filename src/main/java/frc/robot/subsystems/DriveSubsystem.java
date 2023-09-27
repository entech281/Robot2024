package frc.robot.subsystems;

import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.util.sendable.SendableBuilder;
import entech.subsystems.EntechSubsystem;

public class DriveSubsystem extends EntechSubsystem {

    private boolean enabled = true;

    @Override
    public void initialize() {
        if (enabled) {

        }
    }

    public void drive(Translation2d translation, double rotation, boolean fieldRelative, boolean isOpenLoop,
            double yaw) {
        if (enabled) {

        }
    }

    @Override
    public void periodic() {
        if (enabled) {
        }
    }

    @Override
    public void initSendable(SendableBuilder builder) {
        if (enabled) {

        }
    }

    @Override
    public boolean isEnabled() {
        return enabled;
    }
}