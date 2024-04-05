package frc.robot.subsystems.vision;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.photonvision.EstimatedRobotPose;
import org.photonvision.PhotonCamera;
import org.photonvision.PhotonPoseEstimator;
import org.photonvision.PhotonPoseEstimator.PoseStrategy;
import org.photonvision.targeting.PhotonPipelineResult;
import org.photonvision.targeting.PhotonTrackedTarget;
import edu.wpi.first.apriltag.AprilTagFieldLayout;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Transform3d;
import edu.wpi.first.networktables.NetworkTableInstance;
import frc.robot.RobotConstants;

public class SoloCameraContainer implements CameraContainerI {
  private final PhotonCamera camera;
  private final PhotonPoseEstimator estimator;

  public SoloCameraContainer(String cameraName, Transform3d robotToCamera,
      AprilTagFieldLayout fieldLayout) {
    camera = new PhotonCamera(cameraName);
    estimator = new PhotonPoseEstimator(fieldLayout, PoseStrategy.MULTI_TAG_PNP_ON_COPROCESSOR,
        camera, robotToCamera);
    estimator.setMultiTagFallbackStrategy(PoseStrategy.LOWEST_AMBIGUITY);
    camera.setDriverMode(false);
  }

  public SoloCameraContainer(String cameraName, Transform3d robotToCamera,
      AprilTagFieldLayout fieldLayout, NetworkTableInstance ni) {
    camera = new PhotonCamera(ni, cameraName);
    estimator = new PhotonPoseEstimator(fieldLayout, PoseStrategy.MULTI_TAG_PNP_ON_COPROCESSOR,
        camera, robotToCamera);
    estimator.setMultiTagFallbackStrategy(PoseStrategy.LOWEST_AMBIGUITY);
    camera.setDriverMode(false);
  }

  @Override
  public PhotonPipelineResult getFilteredResult() {
    PhotonPipelineResult result = camera.getLatestResult();

    List<PhotonTrackedTarget> filteredTargets = new ArrayList<>();

    for (PhotonTrackedTarget target : result.getTargets()) {
      if (target.getPoseAmbiguity() > RobotConstants.Vision.Filters.MAX_AMBIGUITY)
        continue;
      if (Math
          .abs(target.getBestCameraToTarget().getX()) > RobotConstants.Vision.Filters.MAX_DISTANCE)
        continue;
      boolean allowed = false;
      for (int id : RobotConstants.Vision.Filters.ALLOWED_TAGS) {
        if (target.getFiducialId() == id) {
          allowed = true;
          break;
        }
      }
      if (!allowed) {
        continue;
      }

      filteredTargets.add(target);
    }

    PhotonPipelineResult fliteredResult =
        new PhotonPipelineResult(result.getLatencyMillis(), filteredTargets);
    fliteredResult.setTimestampSeconds(result.getTimestampSeconds());

    return fliteredResult;
  }

  @Override
  public Optional<Pose2d> getEstimatedPose() {
    Optional<EstimatedRobotPose> estimatedPose = estimator.update(getFilteredResult());
    if (estimatedPose.isPresent()) {
      return Optional.of(estimatedPose.get().estimatedPose.toPose2d());
    } else {
      return Optional.empty();
    }
  }

  @Override
  public double getLatency() {
    return getFilteredResult().getLatencyMillis();
  }

  @Override
  public boolean hasTargets() {
    return getFilteredResult().hasTargets();
  }

  @Override
  public int getTargetCount() {
    return getFilteredResult().getTargets().size();
  }

  @Override
  public List<EntechTargetData> getTargetData() {
    List<PhotonTrackedTarget> targets = getFilteredResult().getTargets();
    List<Integer> targetIds = new ArrayList<>();
    for (PhotonTrackedTarget target : targets) {
      targetIds.add(target.getFiducialId());
    }

    List<EntechTargetData> data = new ArrayList<>();
    data.add(new EntechTargetData(targetIds, camera.getName()));
    return data;
  }

  @Override
  public boolean isDriverMode() {
    return camera.getDriverMode();
  }

  @Override
  public void setDriverMode(boolean enabled) {
    camera.setDriverMode(enabled);
  }
}
