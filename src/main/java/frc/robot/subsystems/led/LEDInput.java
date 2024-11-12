package frc.robot.subsystems.led;

import org.littletonrobotics.junction.LogTable;
import edu.wpi.first.wpilibj.util.Color;
import frc.entech.subsystems.SubsystemInput;

public class LEDInput implements SubsystemInput {

  private Color color = Color.kGreen;
  private Color secondaryColor = Color.kBlack;
  private boolean blinking = false;

  public Color getSecondaryColor() {
    return secondaryColor;
  }

  public void setSecondaryColor(Color secondaryColor) {
    this.secondaryColor = secondaryColor;
  }

  @Override
  public void toLog(LogTable table) {
    table.put("Blinking", blinking);
    table.put("CurrentColor", color + "");
  }

  @Override
  public void fromLog(LogTable table) {
    blinking = table.get("Blinking", false);
  }

  public Color getColor() {
    return color;
  }

  public void setColor(Color color) {
    this.color = color;
  }

  public boolean getBlinking() {
    return blinking;
  }

  public void setBlinking(boolean blinking) {
    this.blinking = blinking;
  }

}
