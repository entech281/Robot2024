package frc.robot.subsystems;

import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.util.sendable.SendableBuilder;
import entech.subsystems.EntechSubsystem;
import frc.robot.RobotConstants;
import frc.robot.swerve.SwerveModule;

public class DriveSubsystem extends EntechSubsystem {
    private SwerveModule[] modules;

    private boolean enabled = true;

    @Override
    public void initialize() {
        if (enabled) {
            modules = new SwerveModule[] {
                    new SwerveModule(RobotConstants.Swerve.FRONT_LEFT),
                    new SwerveModule(RobotConstants.Swerve.FRONT_RIGHT)
            };
        }
    }

    public void drive(Translation2d translation, double rotation, boolean fieldRelative,
            Rotation2d yaw) {
        if (enabled) {
            double rawAngle = Math.atan2(translation.getX(), translation.getY());
            double angleForWheels;
            if (fieldRelative) {
                angleForWheels = rawAngle - yaw.getDegrees();
            } else {
                angleForWheels = rawAngle;
            }

            for (SwerveModule module : modules) {
                module.setDesiredAngle(angleForWheels);
            }
        }

        double drivePowerAll = (Math.abs(translation.getX()) + Math.abs(translation.getY())) / 2;

        for (SwerveModule module : modules) {
            module.setDrivePower(drivePowerAll);
        }
    }

    public void stop() {
        for (SwerveModule module : modules) {
            module.setDesiredAngle(0);
            module.setDrivePower(0);
        }
    }

    @Override
    public void periodic() {
        if (enabled) {
            for (SwerveModule module : modules) {
                module.periodic();
            }
        }
    }

    @Override
    public void initSendable(SendableBuilder builder) {
        builder.setSmartDashboardType(getName());
        if (enabled) {
            for (SwerveModule module : modules) {
                module.initSendable(builder);
            }
        }
    }

    @Override
    public boolean isEnabled() {
        return enabled;
    }
}