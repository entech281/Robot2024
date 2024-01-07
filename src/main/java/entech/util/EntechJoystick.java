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

    public void WhilePressed(int button, EntechCommand command) {
        this.button(button).whileTrue(command);
    }

    public void WhenPressed(int button, EntechCommand command) {
        this.button(button).onTrue(command);
    }

    public void WhenReleased(int button, EntechCommand command) {
        this.button(button).onFalse(command);
    }

    public void WhileReleased(int button, EntechCommand command) {
        this.button(button).whileFalse(command);
    }

    public void whileSwitch(int button, EntechCommand commandOnTrue, EntechCommand commandOnFalse) {
        this.WhilePressed(button, commandOnTrue);
        this.WhileReleased(button, commandOnFalse);
    }

    public void whenSwitch(int button, EntechCommand commandOnTrue, EntechCommand commandOnFalse) {
        this.WhenPressed(button, commandOnTrue);
        this.WhenReleased(button, commandOnFalse);
    }

    public double getX() {
        return hid.getX();
    }

    public double getY() {
        return hid.getY();
    }

    public double getZ() {
        return hid.getZ();
    }
}