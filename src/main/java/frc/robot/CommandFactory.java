package frc.robot;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import entech.commands.EntechCommandBase;
import frc.robot.commands.ConeDeployCommand;
import frc.robot.commands.GyroReset;
import frc.robot.commands.HomeLimbCommand;
import frc.robot.commands.PositionArmCommand;
import frc.robot.commands.PositionElbowCommand;
import frc.robot.commands.ToggleGripperCommand;
import frc.robot.commands.nudge.NudgeArmBackwardsCommand;
import frc.robot.commands.nudge.NudgeArmForwardCommand;
import frc.robot.commands.nudge.NudgeElbowDownCommand;
import frc.robot.commands.nudge.NudgeElbowUpCommand;
import frc.robot.subsystems.ArmSubsystem;
import frc.robot.subsystems.DriveSubsystem;
import frc.robot.subsystems.ElbowSubsystem;
import frc.robot.subsystems.GripperSubsystem;
import frc.robot.subsystems.VisionSubsystem;

public class CommandFactory {
    private DriveSubsystem driveSubsystem;
    private VisionSubsystem visionSubsystem;
    private ArmSubsystem armSubsystem;
    private ElbowSubsystem elbowSubsystem;
    private GripperSubsystem gripperSubsystem;

    public CommandFactory(RobotContainer robotContainer) {
        this.driveSubsystem = robotContainer.getDriveSubsystem();
        this.visionSubsystem = robotContainer.getVisionSubsystem();
        this.armSubsystem = robotContainer.getArmSubsystem();
        this.elbowSubsystem = robotContainer.getElbowSubsystem();
        this.gripperSubsystem = robotContainer.getGripperSubsystem();
    }

    public EntechCommandBase gyroResetCommand() {
        return new GyroReset(driveSubsystem);
    }

    public EntechCommandBase homeLimbCommand() {
        return new HomeLimbCommand(elbowSubsystem, armSubsystem);
    }

    // All Gripper only Commands
    public void setDefaultGripperCommand(EntechCommandBase newDefaultCommand) {
        gripperSubsystem.setDefaultCommand(newDefaultCommand);
    }

    public EntechCommandBase toggleGripperCommand() {
        return new ToggleGripperCommand(gripperSubsystem);
    }

    // All Elbow only Commands
    public EntechCommandBase groundRetractedPosition() {
        return new PositionElbowCommand(elbowSubsystem, 12, true);
    }

    public EntechCommandBase highScoringElbowCommand() {
        return createNamedElbowPositionCommand(RobotConstants.ELBOW.POSITION_PRESETS.SCORE_HIGH_DEGREES,
                "highScoringElbowCommand");
    }

    public EntechCommandBase middleScoringElbowCommand() {
        return createNamedElbowPositionCommand(RobotConstants.ELBOW.POSITION_PRESETS.SCORE_MIDDLE_DEGREES,
                "middleScoringElbowCommand");
    }

    public EntechCommandBase groundScoringElbowCommand() {
        return createNamedElbowPositionCommand(RobotConstants.ELBOW.POSITION_PRESETS.SCORE_LOW_DEGREES,
                "groundScoringElbowCommand");
    }

    public EntechCommandBase loadingElbowCommand() {
        return createNamedElbowPositionCommand(RobotConstants.ELBOW.POSITION_PRESETS.LOAD_STATION_DEGREES,
                "loadingElbowCommand");
    }

    public EntechCommandBase carryElbowCommand() {
        return createNamedElbowPositionCommand(RobotConstants.ELBOW.POSITION_PRESETS.CARRY_DEGREES,
                "carryElbowCommand");
    }

    public EntechCommandBase minElbowCommand() {
        return createNamedElbowPositionCommand(RobotConstants.ELBOW.POSITION_PRESETS.MIN_POSITION_DEGREES,
                "minElbowCommand");
    }

    private EntechCommandBase createNamedElbowPositionCommand(double position, String name) {
        EntechCommandBase p = new PositionElbowCommand(elbowSubsystem, position, true);
        p.setName(name);
        return p;
    }

    public EntechCommandBase nudgeArmForwardCommand() {
        return new NudgeArmForwardCommand(armSubsystem, false);
    }

    public EntechCommandBase nudgeArmBackwardCommand() {
        return new NudgeArmBackwardsCommand(armSubsystem, false);
    }

    public EntechCommandBase armPositionHome() {
        return new PositionArmCommand(armSubsystem, RobotConstants.ARM.POSITION_PRESETS.MIN_METERS, true);
    }

    public EntechCommandBase armPositionFullExtension() {
        return new PositionArmCommand(armSubsystem, RobotConstants.ARM.POSITION_PRESETS.MAX_METERS, true);
    }

    public Command dialCarryPosition() {
        return new SequentialCommandGroup(
                armPositionHome(),
                minElbowCommand());
    }

    public Command dialHighPosition() {
        return new SequentialCommandGroup(
                highScoringElbowCommand(),
                armPositionFullExtension());
    }

    public Command dialMiddlePosition() {
        return new SequentialCommandGroup(
                middleScoringElbowCommand(),
                new PositionArmCommand(armSubsystem, 0.16, true));
    }

    public Command dialLoadPosition() {
        return new SequentialCommandGroup(
                armPositionHome(),
                loadingElbowCommand());
    }

    public Command scoreHighCommand() {
        return new SequentialCommandGroup(
                new ConeDeployCommand(elbowSubsystem, gripperSubsystem),
                dialCarryPosition());
    }

    public EntechCommandBase nudgeElbowUpCommand() {
        return new NudgeElbowUpCommand(elbowSubsystem, false);
    }

    public EntechCommandBase nudgeElbowDownCommand() {
        return new NudgeElbowDownCommand(elbowSubsystem, false);
    }

    public Command getAutoCommand() {
        SequentialCommandGroup auto = new SequentialCommandGroup();
        auto.addCommands(homeLimbCommand());
        auto.addCommands(new WaitCommand(3));
        auto.addCommands(dialHighPosition());
        return auto;
    }
}
