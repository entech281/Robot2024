package frc.robot.commands;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import entech.commands.EntechCommand;
import frc.robot.processors.OdomtryProcessor;
import frc.robot.subsystems.NavXSubsystem;

public class GyroReset extends EntechCommand {
    private final Runnable reset;
    private final Runnable correctOdomtry;

    public GyroReset(NavXSubsystem navx, OdomtryProcessor odometry) {
        reset = navx::zeroYaw;
        correctOdomtry = () -> {
            Pose2d pose = new Pose2d(odometry.getEstimatedPose().getTranslation(), Rotation2d.fromDegrees(180));
            odometry.resetOdometry(pose, Rotation2d.fromDegrees(navx.getOutputs().yaw));
        };
    }

    @Override
    public void initialize() {
        reset.run();
        correctOdomtry.run();
    }

    @Override
    public boolean isFinished() {
        return true;
    }

    @Override
    public boolean runsWhenDisabled() {
        return true;
    }
}
