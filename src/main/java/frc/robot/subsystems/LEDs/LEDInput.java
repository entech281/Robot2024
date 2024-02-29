package frc.robot.subsystems.LEDs;

import org.littletonrobotics.junction.LogTable;
import edu.wpi.first.wpilibj.util.Color;
import entech.subsystems.SubsystemInput;

public class LEDInput implements SubsystemInput {

  private Color color = Color.kWhite;
  private boolean blinking = false;

  @Override
  public void toLog(LogTable table) {
    table.put("Blinking", blinking);
    table.put("Current Color", color + "");
  }

  @Override
  public void fromLog(LogTable table) {}

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
