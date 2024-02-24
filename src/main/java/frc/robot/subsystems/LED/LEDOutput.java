package frc.robot.subsystems.LED;

import entech.subsystems.SubsystemOutput;
import edu.wpi.first.wpilibj.util.Color;

public class LEDOutput extends SubsystemOutput {

  private Color color;

  @Override
  public void toLog() {}

  public Color getColor() {
    return this.color;
  }

  public void setColor(Color color) {
    this.color = color;
  }

}
