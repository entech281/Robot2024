package entech.util;

import java.util.Optional;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj2.command.button.CommandJoystick;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import frc.robot.RobotConstants;
import frc.robot.operation.UserPolicy;

public class DriverControllerUtils {

  public static final double TRIGGER_THRESHOLD = 0.2;

  public static boolean currentControllerIsXbox() {
    return DriverStation.getJoystickIsXbox(RobotConstants.PORTS.CONTROLLER.DRIVER_CONTROLLER);
  }

  public static boolean controllerIsPresent(int port) {
    return DriverStation.isJoystickConnected(port);
  }

  public static Optional<CommandJoystick> findCurrentController() {
    if (DriverStation.getJoystickIsXbox(0)) {
      return Optional.of(new CommandJoystick(0));
    } else {
      return Optional.empty();
    }
  }

  public static double getXboxRotation(CommandXboxController xboxController) {
    if (xboxController.getLeftTriggerAxis() > TRIGGER_THRESHOLD) {
      UserPolicy.getInstance().setIsTwistable(true);
      return xboxController.getLeftTriggerAxis();
    } else if (xboxController.getRightTriggerAxis() > TRIGGER_THRESHOLD) {
      UserPolicy.getInstance().setIsTwistable(true);
      return -xboxController.getRightTriggerAxis();
    } else {
      UserPolicy.getInstance().setIsTwistable(false);
      return 0.0;
    }
  }

  private DriverControllerUtils() {}
}
