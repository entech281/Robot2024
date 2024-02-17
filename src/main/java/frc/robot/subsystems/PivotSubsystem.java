package frc.robot.subsystems;

import com.revrobotics.CANSparkBase.IdleMode;
import com.revrobotics.CANSparkLowLevel.MotorType;
import com.revrobotics.CANSparkMax;
import com.revrobotics.SparkPIDController;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import entech.subsystems.EntechSubsystem;
import frc.robot.RobotConstants;

public class PivotSubsystem extends EntechSubsystem<PivotInput, PivotOutput> {

    private boolean ENABLED = false;

    private PivotInput pivotInput = new PivotInput();

    private CANSparkMax pivotA;
    private CANSparkMax pivotB;

    private SparkPIDController pivotAPID;
    private SparkPIDController pivotBPID;

    @Override
    public void initialize() {
        if(ENABLED) {
            pivotA = new CANSparkMax(RobotConstants.Ports.CAN.PIVOT_A, MotorType.kBrushless);
            pivotB = new CANSparkMax(RobotConstants.Ports.CAN.PIVOT_B, MotorType.kBrushless);

            pivotA.getEncoder().setPositionConversionFactor(RobotConstants.PIVOT.POSITION_CONVERSION_FACTOR);
            pivotB.getEncoder().setPositionConversionFactor(RobotConstants.PIVOT.POSITION_CONVERSION_FACTOR);

            pivotA.setIdleMode(IdleMode.kBrake);
            pivotB.setIdleMode(IdleMode.kBrake);

            pivotA.setInverted(true);
            pivotB.setInverted(true);

            pivotAPID = pivotA.getPIDController();
            pivotAPID.setP(RobotConstants.PID.Pivot.KP);
            pivotAPID.setD(RobotConstants.PID.Pivot.KD);
            pivotAPID.setI(RobotConstants.PID.Pivot.KI);

            pivotBPID = pivotB.getPIDController();
            pivotBPID.setP(RobotConstants.PID.Pivot.KP);
            pivotBPID.setD(RobotConstants.PID.Pivot.KD);
            pivotBPID.setI(RobotConstants.PID.Pivot.KI);
        }
    }

    public void periodic() {

        SmartDashboard.putNumber("Pivot Requested Position", pivotInput.requestedPosition);
        SmartDashboard.putNumber("Pivot Top", pivotA.getEncoder().getPosition());
        SmartDashboard.putNumber("Pivot Bottom", pivotA.getEncoder().getPosition());
        SmartDashboard.putNumber("Transfer", pivotA.getEncoder().getPosition());

        if(ENABLED) {
            if(pivotInput.requestedPosition != pivotA.getEncoder().getPosition()) {
                if (pivotInput.requestedPosition <= 0) {
                    pivotAPID.setReference(0, CANSparkMax.ControlType.kPosition);
                    pivotBPID.setReference(0, CANSparkMax.ControlType.kPosition);

                    DriverStation.reportWarning("Pivot tried to go to " + pivotInput.requestedPosition + " value was changed to 0", null);

                } else if (pivotInput.requestedPosition >= RobotConstants.PIVOT.POSITION_CONVERSION_FACTOR) {
                    pivotAPID.setReference(RobotConstants.PIVOT.POSITION_CONVERSION_FACTOR, CANSparkMax.ControlType.kPosition);
                    pivotBPID.setReference(RobotConstants.PIVOT.POSITION_CONVERSION_FACTOR, CANSparkMax.ControlType.kPosition);

                    DriverStation.reportWarning("Pivot tried to go to " + pivotInput.requestedPosition + " value was changed to " + RobotConstants.PIVOT.POSITION_CONVERSION_FACTOR, null);
                }

            } else {
                pivotAPID.setReference(pivotInput.requestedPosition, CANSparkMax.ControlType.kPosition);
                pivotBPID.setReference(pivotInput.requestedPosition, CANSparkMax.ControlType.kPosition);
            }

            if (pivotInput.brakeModeEnabled) {
                pivotA.setIdleMode(IdleMode.kBrake);
                pivotB.setIdleMode(IdleMode.kBrake);
            } else {
                pivotA.setIdleMode(IdleMode.kCoast);
                pivotB.setIdleMode(IdleMode.kCoast);
            }
        }
    }

    @Override
    public boolean isEnabled() {
        return ENABLED;
    }

    @Override
    public void updateInputs(PivotInput input) {
        this.pivotInput = input;
    }

    @Override
    public PivotOutput getOutputs() {
        PivotOutput pivotOutput = new PivotOutput();
        pivotOutput.moving = pivotA.getEncoder().getVelocity() != 0;
        pivotOutput.brakeModeEnabled = IdleMode.kBrake == pivotA.getIdleMode() && IdleMode.kBrake == pivotB.getIdleMode();
        return pivotOutput;
    }

}
