package frc.robot.subsystems.led;

import edu.wpi.first.wpilibj.AddressableLED;
import edu.wpi.first.wpilibj.AddressableLEDBuffer;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.util.Color;
import edu.wpi.first.wpilibj2.command.Command;
import entech.subsystems.EntechSubsystem;
import frc.robot.RobotConstants;
import frc.robot.commands.test.TestLEDCommand;
import frc.robot.io.RobotIO;

/**
 *
 * @author dcowden
 */
public class LEDSubsystem extends EntechSubsystem<LEDInput, LEDOutput> {

  private static final boolean ENABLED = true;

  private AddressableLED leds;
  private AddressableLEDBuffer buffer;
  private Color currentColor;

  private LEDInput currentInput = new LEDInput();
  private Timer blinkTimer = new Timer();

  public LEDSubsystem() {
    leds = new AddressableLED(RobotConstants.LED.PORT);
    buffer = new AddressableLEDBuffer(RobotConstants.LED.NUM_LEDS);
    leds.setLength(buffer.getLength());
    leds.start();
  }

  @Override
  public void initialize() {
    setColor(currentInput.getColor());
    blinkTimer.start();
  }

  @Override
  public void periodic() {
    if (ENABLED) {
      if (currentInput.getBlinking()) {
        if (blinkTimer.hasElapsed(0.25)) {
          toggleColor();
          blinkTimer.restart();
        }
      } else {
        setColor(currentInput.getColor());
      }
    }
  }

  private void toggleColor() {
    if (currentColor == currentInput.getSecondaryColor()) {
      setColor(currentInput.getColor());
      currentColor = currentInput.getColor();
    } else {
      setColor(currentInput.getSecondaryColor());
      currentColor = currentInput.getSecondaryColor();
    }
  }

  private void setColor(Color c) {
    for (var i = 0; i < buffer.getLength(); i++) {
      buffer.setLED(i, c);
    }
    leds.setData(buffer);
  }

  @Override
  public boolean isEnabled() {
    return ENABLED;
  }

  @Override
  public void updateInputs(LEDInput input) {
    RobotIO.processInput(input);
    this.currentInput = input;
  }

  @Override
  public Command getTestCommand() {
    return new TestLEDCommand(this);
  }

  @Override
  public LEDOutput toOutputs() {
    LEDOutput output = new LEDOutput();
    output.setColor(currentInput.getColor());
    output.setBlinking(currentInput.getBlinking());
    return output;
  }
}
