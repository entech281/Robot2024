package frc.robot.subsystems.led;

import entech.subsystems.EntechSubsystem;
import frc.robot.RobotConstants;
import edu.wpi.first.wpilibj.AddressableLED;
import edu.wpi.first.wpilibj.AddressableLEDBuffer;
import edu.wpi.first.wpilibj.util.Color;

public class LEDSubsystem extends EntechSubsystem<LEDInput, LEDOutput> {

  private static final boolean ENABLED = false;

  private static final int numOfSelectedTargetLeds = RobotConstants.LED.NumOfLEDs.NUM_SELECTED_TARGET_LEDS;
  private static final int numOfShootingAlignmentLeds = RobotConstants.LED.NumOfLEDs.NUM_SHOOTING_ALIGNMENT_LEDS;
  private static final int numOfIntakeLeds = RobotConstants.LED.NumOfLEDs.NUM_INTAKE_LEDS;

  private AddressableLED selectedTargetLeds;
  private AddressableLED shootingAlignmentLeds;
  private AddressableLED intakeLeds;
  private AddressableLEDBuffer selectedTargetLedBuffer;
  private AddressableLEDBuffer shootingAlignmentLedBuffer;
  private AddressableLEDBuffer intakeLedBuffer;

  @Override
  public void initialize() {
    selectedTargetLeds = new AddressableLED(RobotConstants.LED.LEDStrips.SELECTED_TARGET_LED_STRIP);
    shootingAlignmentLeds = new AddressableLED(RobotConstants.LED.LEDStrips.SHOOTING_ALIGNMENT_LED_STRIP);
    intakeLeds = new AddressableLED(RobotConstants.LED.LEDStrips.INTAKE_LED_STRIP);
    selectedTargetLedBuffer = new AddressableLEDBuffer(numOfSelectedTargetLeds);
    shootingAlignmentLedBuffer = new AddressableLEDBuffer(numOfShootingAlignmentLeds);
    intakeLedBuffer = new AddressableLEDBuffer(numOfIntakeLeds);
    selectedTargetLeds.setLength(selectedTargetLedBuffer.getLength());
    shootingAlignmentLeds.setLength(shootingAlignmentLedBuffer.getLength());
    intakeLeds.setLength(intakeLedBuffer.getLength());
    selectedTargetLeds.start();
    shootingAlignmentLeds.start();
    intakeLeds.start();
  }

  @Override
  public boolean isEnabled() {
    return ENABLED;
  }

  @Override
  public void updateInputs(LEDInput input) {
    
  }

  @Override
  public LEDOutput getOutputs() {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'getOutputs'");
  }

  public void setNormal() {

  }
  
}
