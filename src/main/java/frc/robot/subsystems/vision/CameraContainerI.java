package frc.robot.subsystems.vision;

import java.util.List;
import java.util.Optional;
import org.photonvision.targeting.PhotonPipelineResult;
import edu.wpi.first.math.geometry.Pose2d;

public interface CameraContainerI {
  PhotonPipelineResult getFilteredResult();

  Optional<Pose2d> getEstimatedPose();

  double getLatency();

  boolean hasTargets();

  int getTargetCount();

  List<EntechTargetData> getTargetData();

  boolean isDriverMode();

  void setDriverMode(boolean enabled);

  boolean isConnected();
}
