package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.ArmSubsystem;
import frc.robot.subsystems.VisionSubsystem;

public  class FancyCommandFactoryOption2 {

    public static void init( VisionSubsystem visionSubsystem, ArmSubsystem armSubsystem){
        Vision.subsystem = visionSubsystem;
        Arm.subsystem = armSubsystem;
    }
    public static Command combo(){
        return new NeedsBothCommand(Vision.subsystem, Arm.subsystem);
    }
    public static class Vision {
        public static VisionSubsystem subsystem;
        public static Command driveForward(){
            return new DriveForwardCommand(subsystem);
        }
    }
    public static class Arm {
        public static ArmSubsystem subsystem;
        public static Command moveArm(){
            return new MoveArmCommand(subsystem);
        }
    }
}
