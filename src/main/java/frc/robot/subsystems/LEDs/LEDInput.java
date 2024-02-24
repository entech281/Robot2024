package frc.robot.subsystems.LEDs;

import org.littletonrobotics.junction.LogTable;
import edu.wpi.first.wpilibj.util.Color;
import entech.subsystems.SubsystemInput;

public class LEDInput implements SubsystemInput {

  private Color color = Color.kWhite;

  @Override
  public void toLog(LogTable table) {}

  @Override
  public void fromLog(LogTable table) {}

  public Color getColor() {
    return color;
  }

  public void setColor(Color color) {
    this.color = color;
  }

}
