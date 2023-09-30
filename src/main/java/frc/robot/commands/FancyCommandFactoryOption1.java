package frc.robot.commands;

import frc.robot.subsystems.ArmSubsystem;
import frc.robot.subsystems.VisionSubsystem;
import edu.wpi.first.wpilibj2.command.Command;
public class FancyCommandFactoryOption1 {
    public FancyCommandFactoryOption1(VisionSubsystem visionSubsystem,ArmSubsystem armSubsystem){
        vision = new VisionSubSystemCommandFactory(visionSubsystem);
        arm = new ArmSubsystemCommandFactory(armSubsystem);
    }
    public VisionSubSystemCommandFactory vision;
    public ArmSubsystemCommandFactory  arm;

    public Command combo(){
        return new NeedsBothCommand(vision.visionSubsystem, arm.armSubsystem);
    }

    public static class VisionSubSystemCommandFactory{

        public VisionSubSystemCommandFactory(VisionSubsystem visionSubsystem){
            this.visionSubsystem = visionSubsystem;
        }
        public VisionSubsystem visionSubsystem;

        public Command driveForward(){
            return new DriveForwardCommand(visionSubsystem);
        }
    }

    public static class ArmSubsystemCommandFactory{
        public ArmSubsystemCommandFactory(ArmSubsystem armSubsystem){
            this.armSubsystem = armSubsystem;
        }

        public ArmSubsystem armSubsystem;

        public Command moveArm(){
            return new MoveArmCommand(armSubsystem);
        }
    }
}
