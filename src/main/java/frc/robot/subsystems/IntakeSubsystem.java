package frc.robot.subsystems;

import com.revrobotics.CANSparkBase.IdleMode;
import com.revrobotics.CANSparkLowLevel.MotorType;
import com.revrobotics.CANSparkMax;

import entech.subsystems.EntechSubsystem;
import frc.robot.RobotConstants;

public class IntakeSubsystem extends EntechSubsystem<IntakeInput, IntakeOutput> {

    private boolean ENABLED = false;

    IntakeInput intakeInput = new IntakeInput();

    CANSparkMax intakeMotor;

    @Override
    public void initialize() {

        intakeMotor = new CANSparkMax(RobotConstants.Ports.CAN.INTAKE, MotorType.kBrushless);

        intakeMotor.setInverted(false);
    }

    public void periodic() {
        if (intakeInput.activate) {
            intakeMotor.set(RobotConstants.INTAKE.INTAKE_SPEED);
        } else {
            intakeMotor.set(0);
        }

        if(intakeInput.brakeModeEnabled) {
            intakeMotor.setIdleMode(IdleMode.kBrake);
        } else {
            intakeMotor.setIdleMode(IdleMode.kCoast);
        }
    }

    @Override
    public boolean isEnabled() {
        return ENABLED;
    }

    @Override
    public void updateInputs(IntakeInput input) {
        this.intakeInput = input;
    }

    @Override
    public IntakeOutput getOutputs() {
        IntakeOutput intakeOutput = new IntakeOutput();
        intakeOutput.active = intakeMotor.getEncoder().getVelocity() != 0;
        intakeOutput.brakeModeEnabled = IdleMode.kBrake == intakeMotor.getIdleMode();
        return intakeOutput;
    }

}
