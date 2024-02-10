package frc.robot.subsystems;

import entech.subsystems.SubsystemInput;

public class ShooterInput implements SubsystemInput {

    public double maxSpeed;
    public boolean shooterStatus;

    public void shooterInput(double maxSpeed, boolean shooterStatus) {
        this.maxSpeed = maxSpeed;
        this.shooterStatus = shooterStatus;
    }
}
