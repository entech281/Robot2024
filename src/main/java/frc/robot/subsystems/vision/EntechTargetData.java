package frc.robot.subsystems.vision;

import java.util.List;
import edu.wpi.first.util.protobuf.ProtobufSerializable;

public class EntechTargetData implements ProtobufSerializable {
  private final List<Integer> ids;
  private final String cameraName;

  public EntechTargetData(List<Integer> ids, String cameraName) {
    this.ids = ids;
    this.cameraName = cameraName;
  }

  public List<Integer> getIds() {
    return this.ids;
  }

  public String getCameraName() {
    return this.cameraName;
  }
}
