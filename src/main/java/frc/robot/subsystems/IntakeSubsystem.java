package frc.robot.subsystems;

import com.revrobotics.CANSparkBase.IdleMode;
import com.revrobotics.CANSparkLowLevel.MotorType;
import com.revrobotics.CANSparkMax;

import entech.subsystems.EntechSubsystem;
import frc.robot.RobotConstants;

public class IntakeSubsystem extends EntechSubsystem<IntakeInput, IntakeOutput> {

    private boolean ENABLED = true;
    private boolean coastModeEnabled;
    private boolean active = false;

    CANSparkMax intakeMotor;

    @Override
    public void initialize() {

        intakeMotor = new CANSparkMax(RobotConstants.Ports.CAN.INTAKE, MotorType.kBrushless);

        intakeMotor.setInverted(false);
    }

    public void periodic() {
        if (active) {
            intakeMotor.set(RobotConstants.INTAKE.INTAKE_SPEED);
        } else {
            intakeMotor.set(0);
        }

        if(coastModeEnabled) {
            intakeMotor.setIdleMode(IdleMode.kCoast);
        } else {
            intakeMotor.setIdleMode(IdleMode.kBrake);
        }
    }

    @Override
    public boolean isEnabled() {
        return ENABLED;
    }

    @Override
    public void updateInputs(IntakeInput input) {
        active = input.active;
        coastModeEnabled = input.coastModeEnabled;
    }

    @Override
    public IntakeOutput getOutputs() {
        return new IntakeOutput();
    }

}
