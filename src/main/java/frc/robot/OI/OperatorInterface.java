package frc.robot.OI;

import entech.util.EntechJoystick;
import frc.robot.CommandFactory;
import frc.robot.RobotConstants;
import frc.robot.io.RobotIO;
import frc.robot.SubsystemManager;
import frc.robot.commands.DriveCommand;
import frc.robot.commands.GyroReset;
import frc.robot.commands.TwistCommand;
import frc.robot.commands.XCommand;
import frc.robot.io.*;
import frc.robot.subsystems.DriveInput;


public final class OperatorInterface implements DriveInputSupplier, DebugInputSupplier, OperatorInputSupplier {
    private static final OperatorInterface _instance = new OperatorInterface();
    private static final EntechJoystick driveJoystick = new EntechJoystick(RobotConstants.Ports.CONTROLLER.JOYSTICK);
    private static final EntechJoystick operatorPanel = new EntechJoystick(RobotConstants.Ports.CONTROLLER.PANEL);


    public static void create(CommandFactory commandFactory, SubsystemManager subsystemManager) {
        driveJoystick.WhilePressed(1, new TwistCommand());
        driveJoystick.WhenPressed(11, new GyroReset(subsystemManager.getDriveSubsystem()));
        driveJoystick.WhenPressed(9, new XCommand());

        subsystemManager.getDriveSubsystem()
                .setDefaultCommand(new DriveCommand(subsystemManager.getDriveSubsystem(), _instance));

    }

    private OperatorInterface() {
    }

    /*
      These force commands to accept inputs, not raw joysticks and stuff
      also here we log any inputs handed to consumers, so they dont have to
     */
    @Override
    public DebugInput getDebugInput() {
        DebugInput di =  new DebugInput();
        RobotIO.processInput(di);
        return di;
    }

    @Override
    public DriveInput getDriveInput() {
        DriveInput di =  new DriveInput(driveJoystick.getX(), driveJoystick.getY(), driveJoystick.getZ());
        RobotIO.processInput(di);
        return di;
    }

    @Override
    public OperatorInput getOperatorInput() {
        OperatorInput oi =  new OperatorInput();
        RobotIO.processInput(oi);
        return oi;
    }
}