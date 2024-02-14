package frc.robot.io;

import frc.robot.subsystems.DriveOutput;
import frc.robot.subsystems.NavxOutput;
import frc.robot.subsystems.VisionOutput;
import org.littletonrobotics.junction.Logger;
import org.littletonrobotics.junction.inputs.LoggableInputs;

public class RobotIO {

    public static RobotIO state = new RobotIO();


    public static void processInput(LoggableInputs in){
        Logger.processInputs(in.getClass().getName(),in);
    }

    private RobotIO(){}

    public VisionOutput getLatestVisionOutput(){
        return latestVisionOutput;
    }

    public DriveOutput getLatestDriveOutput(){
        return latestDriveOutput;
    }
    public NavxOutput getLatestNavxOutput(){
        return latestNavxOutput;
    }

    public void updateVision(VisionOutput vo){
        latestVisionOutput = vo;
        vo.log();
    }
    public void updateNavx(NavxOutput no){
        latestNavxOutput = no;
        no.log();
    }
    public void updateDrive ( DriveOutput dro){
        latestDriveOutput = dro;
        dro.log();
    }

    private VisionOutput latestVisionOutput;
    private NavxOutput latestNavxOutput;
    private DriveOutput latestDriveOutput;


}
