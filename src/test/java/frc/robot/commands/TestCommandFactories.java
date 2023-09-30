package frc.robot.commands;

import frc.robot.subsystems.ArmSubsystem;
import frc.robot.subsystems.VisionSubsystem;
import edu.wpi.first.wpilibj2.command.Command;

public class TestCommandFactories {

    public void testFancyCommandFactoryOption1(){
        FancyCommandFactoryOption1 ffo = new FancyCommandFactoryOption1(
                new VisionSubsystem(),
                new ArmSubsystem()
        );

        Command c1 = ffo.arm.moveArm();
        Command c2 = ffo.vision.driveForward();
        Command c3 = ffo.combo();
    }

    public void testCommandFactoryOption2(){
;
        FancyCommandFactoryOption2.init(new VisionSubsystem(), new ArmSubsystem());

        Command c1 = FancyCommandFactoryOption2.Arm.moveArm();
        Command c2 = FancyCommandFactoryOption2.Vision.driveForward();
        Command c3 = FancyCommandFactoryOption2.combo();

    }

    public void testCommandFactoryOption3(){
        VisionSubsystem vision = new VisionSubsystem();
        ArmSubsystem arm = new ArmSubsystem();
        FancyCommandFactoryOption3.init(vision , arm);
        Command c1 = arm.moveArm();
        Command c2 = vision.driveForward();
        Command c3 = FancyCommandFactoryOption2.combo();
    }
}
