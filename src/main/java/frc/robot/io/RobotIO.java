package frc.robot.io;

import org.littletonrobotics.junction.Logger;
import org.littletonrobotics.junction.inputs.LoggableInputs;

import frc.robot.subsystems.drive.DriveOutput;
import frc.robot.subsystems.intake.IntakeOutput;
import frc.robot.subsystems.navx.NavXOutput;
import frc.robot.subsystems.shooter.ShooterOutput;
import frc.robot.subsystems.transfer.TransferOutput;
import frc.robot.subsystems.vision.VisionOutput;

public class RobotIO {
    private static RobotIO instance = new RobotIO();

    public static RobotIO getInstance() {
        return instance;
    }

    public static void processInput(LoggableInputs in){
        Logger.processInputs(in.getClass().getSimpleName(), in);
    }

    private RobotIO() {
    }

    public VisionOutput getVisionOutput() {
        return latestVisionOutput;
    }

    public DriveOutput getDriveOutput() {
        return latestDriveOutput;
    }
    public NavXOutput getNavXOutput() {
        return latestNavXOutput;
    }

    public IntakeOutput getIntakeOutput() {
        return latestIntakeOutput;
    }

    public ShooterOutput getShooterOutput() {
        return latestShooterOutput;
    }

    public TransferOutput getTransferOutput() {
        return latestTransferOutput;
    }

    public void updateVision(VisionOutput vo) {
        latestVisionOutput = vo;
        vo.log();
    }
    public void updateNavx(NavXOutput no) {
        latestNavXOutput = no;
        no.log();
    }
    public void updateDrive ( DriveOutput dro) {
        latestDriveOutput = dro;
        dro.log();
    }

    public void updateTransfer (TransferOutput tro) {
        latestTransferOutput = tro;
        tro.log();
    }

    public void updateShooter (ShooterOutput sho) {
        latestShooterOutput = sho;
        sho.log();
    }

    public void updateIntake (IntakeOutput ito) {
        latestIntakeOutput = ito;
        ito.log();
    }

    private VisionOutput latestVisionOutput;
    private NavXOutput latestNavXOutput;
    private DriveOutput latestDriveOutput;
    private IntakeOutput latestIntakeOutput;
    private TransferOutput latestTransferOutput;
    private ShooterOutput latestShooterOutput;
}
