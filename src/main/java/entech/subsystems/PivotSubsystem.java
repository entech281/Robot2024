package entech.subsystems;

import com.revrobotics.RelativeEncoder;
import com.revrobotics.SparkPIDController;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkLowLevel.MotorType;
import frc.robot.RobotConstants.PIVOT;

public class PivotSubsystem extends EntechSubsystem {

    private CANSparkMax sparkLeft;
    private CANSparkMax sparkRight;
    private SparkPIDController pidcLeft;
    private SparkPIDController pidcRight;
    private RelativeEncoder encoderLeft;
    private RelativeEncoder encoderRight;

    public void initialize() {
        sparkLeft = new CANSparkMax(PIVOT.CAN.PIVOT_LEFT, MotorType.kBrushless);
        sparkRight = new CANSparkMax(PIVOT.CAN.PIVOT_RIGHT, MotorType.kBrushless);
        pidcLeft = sparkLeft.getPIDController();
        pidcRight = sparkRight.getPIDController();
        encoderLeft = sparkLeft.getEncoder();
        encoderRight = sparkRight.getEncoder();
        pidcLeft.setP(PIVOT.TUNING.KP);
        pidcLeft.setI(PIVOT.TUNING.KI);
        pidcLeft.setD(PIVOT.TUNING.KD);
        pidcLeft.setFF(PIVOT.TUNING.KFF);
        pidcRight.setP(PIVOT.TUNING.KP);
        pidcRight.setI(PIVOT.TUNING.KI);
        pidcRight.setD(PIVOT.TUNING.KD);
        pidcRight.setFF(PIVOT.TUNING.KFF);    
    }

    public void home() {
        encoderLeft.setPosition(0);
        encoderRight.setPosition(0);
    }

    @Override
    public void updateInputs(SubsystemInput pi) { // should be PivotInput
        pidcLeft.setReference(0, CANSparkMax.ControlType.kPosition);
        pidcRight.setReference(0, CANSparkMax.ControlType.kPosition);
    }

    public PivotOutput getOutputs() {
        return new PivotOutput();
    }

    public boolean isEnabled() {
        return false;
    }



}
