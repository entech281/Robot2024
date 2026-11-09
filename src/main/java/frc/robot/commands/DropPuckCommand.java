package frc.robot.commands;

import edu.wpi.first.wpilibj.Timer;
import frc.entech.commands.EntechCommand;
import frc.entech.util.StoppingCounter;
import frc.robot.RobotConstants;
import frc.robot.subsystems.pivot.PivotInput;
import frc.robot.subsystems.pivot.PivotSubsystem;
import frc.robot.subsystems.shooter.ShooterInput;
import frc.robot.subsystems.shooter.ShooterSubsystem;

public class DropPuckCommand extends EntechCommand {

  private PivotInput pInput = new PivotInput();
  private ShooterInput sInput = new ShooterInput();
  private final PivotSubsystem pSubsystem;
  private final ShooterSubsystem sSubsystem;
  private boolean timerRunning;
  private Timer timer = new Timer();
  private StoppingCounter counter =
      new StoppingCounter(RobotConstants.TEST_CONSTANTS.STANDARD_TEST_LENGTH);

  public DropPuckCommand(PivotSubsystem pivot, ShooterSubsystem shooter) {
    super(pivot,shooter);
    this.pSubsystem = pivot;
    this.sSubsystem = shooter;
  }

  @Override
  public void initialize() {
    counter.reset();
    pInput.setActivate(true);
    pInput.setRequestedPosition(RobotConstants.PIVOT.PUCKDROP_AMP_POSITION_DEG);
    pSubsystem.updateInputs(pInput);

    timerRunning = false;
    timer.stop();
    timer.reset();
  }

  @Override
  public void execute() {
      if ( pSubsystem.getOutputs().isAtRequestedPosition() ) {
        timerRunning = true;
        timer.start();
      }
      if (timerRunning && (timer.get() > 2.0)) {
        sInput.setActivate(true);
        sInput.setSpeed(RobotConstants.SHOOTER.PUCKDROP_SPEED);
        sSubsystem.updateInputs(sInput);
      }

  }

  @Override
  public void end(boolean interupted) {
    counter.reset();
    pInput.setActivate(true);
    pInput.setRequestedPosition(RobotConstants.PIVOT.LOWER_SOFT_LIMIT_DEG);
    pSubsystem.updateInputs(pInput);

    sInput.setActivate(false);
    sInput.setSpeed(0.0);
    sSubsystem.updateInputs(sInput);
  }
}
