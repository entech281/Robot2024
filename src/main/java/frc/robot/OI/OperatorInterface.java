package frc.robot.OI;

import edu.wpi.first.math.geometry.Rotation2d;
import entech.util.EntechJoystick;
import frc.robot.CommandFactory;
import frc.robot.RobotConstants;
import frc.robot.SubsystemManager;
import frc.robot.commands.DriveCommand;
import frc.robot.commands.GyroReset;
import frc.robot.commands.TwistCommand;
import frc.robot.processors.OdomtryProcessor;

@SuppressWarnings("unused")
public final class OperatorInterface {
    private static final EntechJoystick driveJoystick = new EntechJoystick(RobotConstants.Ports.CONTROLLER.JOYSTICK);
    private static final EntechJoystick operatorPanel = new EntechJoystick(RobotConstants.Ports.CONTROLLER.PANEL);

    /**
     * Connects commands to operator panel and joystick.
     * 
     * 
     * @param commandFactory
     * @param subsystemManager
     */
    public static void create(CommandFactory commandFactory, SubsystemManager subsystemManager, OdomtryProcessor odometry) {
        driveJoystick.WhilePressed(1, new TwistCommand());
        driveJoystick.WhenPressed(11, new GyroReset(subsystemManager.getNavXSubsystem(), odometry));
        subsystemManager.getDriveSubsystem()
                .setDefaultCommand(
                    new DriveCommand(
                        subsystemManager.getDriveSubsystem(),
                        driveJoystick,
                        () -> { return Rotation2d.fromDegrees(subsystemManager.getNavXSubsystem().getOutputs().yaw); },
                        odometry::getEstimatedPose
                    )
                );
    }

    private OperatorInterface() {
    }
}