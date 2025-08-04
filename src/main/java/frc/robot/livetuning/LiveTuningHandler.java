package frc.robot.livetuning;

import java.util.HashMap;
import java.util.Map;
import edu.wpi.first.networktables.DoublePublisher;
import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;
import frc.entech.util.PresetHandler;
import frc.robot.RobotConstants;

public class LiveTuningHandler {
  private static LiveTuningHandler instance;
  private NetworkTable table;

  public static LiveTuningHandler getInstance() {
    if (instance == null) {
      instance = new LiveTuningHandler();
    }
    return instance;
  }

  private LiveTuningHandler() {}

  /**
   * Loads default values from constants into network tables then loads JSON values.
   */
  public void init() {
    table = NetworkTableInstance.getDefault().getTable("LiveTuning");
    resetToDefaults();
    if (PresetHandler.isUSBStickConnected()) {
      if (PresetHandler.presetFileExists()) {
        resetToJSON();
      } else {
        saveToJSON();
      }
    }
  }

  public void resetToDefaults() {
    Map<String, Double> values = RobotConstants.LiveTuning.VALUES;
    loadMapToTables(values);
  }


  public void resetToJSON() {
    Map<String, Double> values = PresetHandler.readPresetsJson();
    loadMapToTables(values);
  }

  public void saveToJSON() {
    Map<String, Double> values = new HashMap<>();
    for (String key : RobotConstants.LiveTuning.VALUES.keySet()) {
      NetworkTableEntry entry = table.getEntry(key);
      values.put(key, entry.getDouble(0.0));
    }
    PresetHandler.writePresets(values);
  }

  private void loadMapToTables(Map<String, Double> values) {
    for (Map.Entry<String, Double> value : values.entrySet()) {
      DoublePublisher publisher = table.getDoubleTopic(value.getKey()).publish();
      publisher.accept(value.getValue());
    }
  }

  public double getValue(String key) {
    return table.getEntry(key).getDouble(0.0);
  }
}

