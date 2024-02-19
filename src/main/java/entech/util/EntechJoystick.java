package entech.util;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj2.command.button.CommandJoystick;
import entech.commands.EntechCommand;

public class EntechJoystick extends CommandJoystick {
  private final Joystick hid;

  public EntechJoystick(int port) {
    super(port);
    hid = this.getHID();
  }

  public void whilePressed(int button, EntechCommand command) {
    this.button(button).whileTrue(command);
  }

  public void whenPressed(int button, EntechCommand command) {
    this.button(button).onTrue(command);
  }

  public void whenReleased(int button, EntechCommand command) {
    this.button(button).onFalse(command);
  }

  public void whileReleased(int button, EntechCommand command) {
    this.button(button).whileFalse(command);
  }

  public void whileSwitch(int button, EntechCommand commandOnTrue, EntechCommand commandOnFalse) {
    this.whilePressed(button, commandOnTrue);
    this.whileReleased(button, commandOnFalse);
  }

  public void whenSwitch(int button, EntechCommand commandOnTrue, EntechCommand commandOnFalse) {
    this.whenPressed(button, commandOnTrue);
    this.whenReleased(button, commandOnFalse);
  }

  @Override
  public double getX() {
    return hid.getX();
  }

  @Override
  public double getY() {
    return hid.getY();
  }

  @Override
  public double getZ() {
    return hid.getZ();
  }
}
