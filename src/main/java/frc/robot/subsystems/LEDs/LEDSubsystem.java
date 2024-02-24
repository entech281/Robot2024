package frc.robot.subsystems.LEDs;

import edu.wpi.first.wpilibj.AddressableLED;
import edu.wpi.first.wpilibj.AddressableLEDBuffer;
import edu.wpi.first.wpilibj.util.Color;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import entech.subsystems.EntechSubsystem;
import frc.robot.RobotConstants;

/**
 *
 * @author dcowden
 */
public class LEDSubsystem extends EntechSubsystem<LEDInput, LEDOutput> {

  private boolean ENABLED;

  private AddressableLED leds;
  private AddressableLEDBuffer buffer;

  private LEDInput currentInput = new LEDInput();

  public LEDSubsystem() {
    leds = new AddressableLED(RobotConstants.LED.PORT);
    buffer = new AddressableLEDBuffer(RobotConstants.LED.NUM_LEDS);
    leds.setLength(buffer.getLength());
    leds.start();
  }

  @Override
  public void initialize() {
    setColor(Color.kWhite);
  }

  public void periodic() {
    setColor(currentInput.getColor());
  }

  public void setColor(Color c) {
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
    this.currentInput = input;
  }

  @Override
  public Command getTestCommand() {
    return Commands.none();
  }

  @Override
  public LEDOutput toOutputs() {
    LEDOutput output = new LEDOutput();
    output.setColor(currentInput.getColor());
    return output;
  }
}
