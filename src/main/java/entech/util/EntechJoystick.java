package entech.util;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj2.command.button.CommandJoystick;
import entech.commands.EntechCommandBase2023;

public class EntechJoystick extends CommandJoystick {
    private final Joystick hid;

    public EntechJoystick(int port) {
        super(port);
        hid = this.getHID();
    }

    public void WhilePressed(int button, EntechCommandBase2023 command) {
        this.button(button).whileTrue(command);
    }

    public void WhenPressed(int button, EntechCommandBase2023 command) {
        this.button(button).onTrue(command);
    }

    public void WhenReleased(int button, EntechCommandBase2023 command) {
        this.button(button).onFalse(command);
    }

    public void WhileReleased(int button, EntechCommandBase2023 command) {
        this.button(button).whileFalse(command);
    }

    public void whileSwitch(int button, EntechCommandBase2023 commandOnTrue, EntechCommandBase2023 commandOnFalse) {
        this.WhilePressed(button, commandOnTrue);
        this.WhileReleased(button, commandOnFalse);
    }

    public void whenSwitch(int button, EntechCommandBase2023 commandOnTrue, EntechCommandBase2023 commandOnFalse) {
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