package frc.robot.subsystems.led;

// import org.littletonrobotics.junction.LogTable;
import edu.wpi.first.wpilibj.util.Color;
import frc.entech.subsystems.SubsystemInput;

public class LEDInput implements SubsystemInput {

  private Color color = Color.kDarkOrange;
  private Color secondaryColor = Color.kBlue;
  private boolean blinking = true;
  private boolean running = false;
  private int running_length = 10;
  private int running_offset = 0;

  public Color getSecondaryColor() {
    return secondaryColor;
  }

  public void setSecondaryColor(Color secondaryColor) {
    this.secondaryColor = secondaryColor;
  }

  // @Override
  // public void toLog(LogTable table) {
  //   table.put("Blinking", blinking);
  //   table.put("CurrentColor", color + "");
  // }

  // @Override
  // public void fromLog(LogTable table) {
  //   blinking = table.get("Blinking", false);
  // }

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
    this.running = false;
  }

  public boolean getRunning() {
    return running;
  }

  public void setRunning(boolean running) {
    this.running = running;
    this.blinking = false;
  }

  public int getRunningLength() {
    return this.running_length;
  }

  public void setRunningLength(int length) {
    this.running_length = length;
  }

  public int getRunningOffset() {
    return this.running_offset;
  }

  public void setRunningOffset(int offset) {
    this.running_offset = offset;
  }
}
