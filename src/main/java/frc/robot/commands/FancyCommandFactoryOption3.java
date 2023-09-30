package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.ArmSubsystem;
import frc.robot.subsystems.VisionSubsystem;

public class FancyCommandFactoryOption3 {

    public static VisionSubsystem vision;
    public static ArmSubsystem arm;
    public static void init( VisionSubsystem visionSubsystem, ArmSubsystem armSubsystem){
        vision = visionSubsystem;
        arm = armSubsystem;
    }
    public static Command combo(){
        return new NeedsBothCommand(vision, arm);
    }

}
