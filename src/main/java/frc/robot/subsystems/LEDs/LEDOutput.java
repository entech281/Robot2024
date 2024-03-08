package frc.robot.subsystems.LEDs;

import org.littletonrobotics.junction.Logger;
import edu.wpi.first.wpilibj.util.Color;
import entech.subsystems.SubsystemOutput;

public class LEDOutput extends SubsystemOutput {

  private Color color = Color.kWhite;
  private boolean blinking;

  @Override
  public void toLog() {
    Logger.recordOutput("LEDOutput/CurrentCollor", color + "");
    Logger.recordOutput("LEDOutput/Blinking", blinking);
  }

  public Color getColor() {
    return this.color;
  }

  public void setColor(Color color) {
    this.color = color;
  }

  public boolean isBlinking() {
    return this.blinking;
  }

  public void setBlinking(boolean blinking) {
    this.blinking = blinking;
  }

}
