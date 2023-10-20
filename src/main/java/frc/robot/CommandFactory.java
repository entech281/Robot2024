package frc.robot;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.commands.ConeDeployCommand;
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

    // All Gripper only Commands
    public void setDefaultGripperCommand(Command newDefaultCommand) {
        gripperSubsystem.setDefaultCommand(newDefaultCommand);
    }

    public Command toggleGripperCommand() {
        return new ToggleGripperCommand(gripperSubsystem);
    }

    // All Elbow only Commands
    public Command groundRetractedPosition() {
        return new PositionElbowCommand(elbowSubsystem, 12, true);
    }

    public Command highScoringElbowCommand() {
        return createNamedElbowPositionCommand(RobotConstants.ELBOW.POSITION_PRESETS.SCORE_HIGH_DEGREES,
                "highScoringElbowCommand");
    }

    public Command middleScoringElbowCommand() {
        return createNamedElbowPositionCommand(RobotConstants.ELBOW.POSITION_PRESETS.SCORE_MIDDLE_DEGREES,
                "middleScoringElbowCommand");
    }

    public Command groundScoringElbowCommand() {
        return createNamedElbowPositionCommand(RobotConstants.ELBOW.POSITION_PRESETS.SCORE_LOW_DEGREES,
                "groundScoringElbowCommand");
    }

    public Command loadingElbowCommand() {
        return createNamedElbowPositionCommand(RobotConstants.ELBOW.POSITION_PRESETS.LOAD_STATION_DEGREES,
                "loadingElbowCommand");
    }

    public Command carryElbowCommand() {
        return createNamedElbowPositionCommand(RobotConstants.ELBOW.POSITION_PRESETS.CARRY_DEGREES,
                "carryElbowCommand");
    }

    public Command minElbowCommand() {
        return createNamedElbowPositionCommand(RobotConstants.ELBOW.POSITION_PRESETS.MIN_POSITION_DEGREES,
                "minElbowCommand");
    }

    private Command createNamedElbowPositionCommand(double position, String name) {
        Command p = new PositionElbowCommand(elbowSubsystem, position, true);
        p.setName(name);
        return p;
    }

    // All Arm only Commands
    public Command nudgeArmForwardCommand() {
        return new NudgeArmForwardCommand(armSubsystem, false);
    }

    public Command nudgeArmBackwardCommand() {
        return new NudgeArmBackwardsCommand(armSubsystem, false);
    }

    public Command armPositionHome() {
        return new PositionArmCommand(armSubsystem, RobotConstants.ARM.POSITION_PRESETS.MIN_METERS, true);
    }

    public Command armPositionFullExtension() {
        return new PositionArmCommand(armSubsystem, RobotConstants.ARM.POSITION_PRESETS.MAX_METERS, true);
    }

    // Dial Position Commands
    public Command dialCarryPosition() {
        return new SequentialCommandGroup(
                armPositionHome(),
                carryElbowCommand());
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

    // Scoreing Commands
    public Command scoreHighCommand() {
        return new SequentialCommandGroup(
                new ConeDeployCommand(elbowSubsystem, gripperSubsystem),
                dialCarryPosition());
    }

    public Command nudgeElbowUpCommand() {
        return new NudgeElbowUpCommand(elbowSubsystem, false);
    }

    public Command nudgeElbowDownCommand() {
        return new NudgeElbowDownCommand(elbowSubsystem, false);
    }
}
