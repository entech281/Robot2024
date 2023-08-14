package frc.robot.vision;

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
import edu.wpi.first.math.geometry.Pose3d;
import edu.wpi.first.math.geometry.Transform3d;
import edu.wpi.first.networktables.NetworkTableInstance;
import entech.util.EntechGeometryUtils;
import frc.robot.RobotConstants;

public class CameraContainer {
    private final PhotonCamera camera;
    private final PhotonPoseEstimator estimator;

    private final CameraContainer base;

    public CameraContainer(String cameraName, Transform3d robotToCamera, AprilTagFieldLayout fieldLayout, CameraContainer base) {
        camera = new PhotonCamera(cameraName);
        estimator = new PhotonPoseEstimator(fieldLayout, PoseStrategy.MULTI_TAG_PNP, camera, robotToCamera);
        estimator.setMultiTagFallbackStrategy(PoseStrategy.LOWEST_AMBIGUITY);
        this.base = base;
    }

    public CameraContainer(String cameraName, Transform3d robotToCamera, AprilTagFieldLayout fieldLayout, CameraContainer base, NetworkTableInstance ni) {
        camera = new PhotonCamera(ni, cameraName);
        estimator = new PhotonPoseEstimator(fieldLayout, PoseStrategy.MULTI_TAG_PNP, camera, robotToCamera);
        estimator.setMultiTagFallbackStrategy(PoseStrategy.LOWEST_AMBIGUITY);
        this.base = base;
    }

    public FilteredPipelineResult getFilteredResult() {
        PhotonPipelineResult result = camera.getLatestResult();

        FilteredPipelineResult filteredResult =  new FilteredPipelineResult(result.getLatencyMillis());

        for (PhotonTrackedTarget target : result.getTargets()) {
            if (target.getPoseAmbiguity() > RobotConstants.Vision.Filters.MAX_AMBIGUITY) {
                filteredResult.setBadPoseAmbiguity(true);
                continue;
            }
            if (target.getArea() < RobotConstants.Vision.Filters.MIN_AREA) {
                filteredResult.setBadAreaSize(true);
                continue;
            }
            if (Math.abs(target.getBestCameraToTarget().getX()) > RobotConstants.Vision.Filters.MAX_DISTANCE) {
                filteredResult.setBadDistance(true);
                continue;
            }

            filteredResult.addedFilteredObject(target);
        }
        filteredResult.setTimestampSeconds(result.getTimestampSeconds());

        return filteredResult;
    }

    public Optional<Pose3d> getEstimatedPose() {
        List<Pose3d> estPoses = new ArrayList<Pose3d>();
        Optional<EstimatedRobotPose> thisEstPose = estimator.update(getFilteredResult());
        if (thisEstPose.isPresent()) estPoses.add(thisEstPose.get().estimatedPose);
        if (base != null) {
            Optional<Pose3d> baseEstPose = base.getEstimatedPose();
            if (baseEstPose.isPresent()) estPoses.add(baseEstPose.get());
        }
        switch (estPoses.size()) {
            case 1:
                return Optional.of(estPoses.get(0));
            case 2:
                return Optional.of(EntechGeometryUtils.averagePose3d(estPoses.get(0), estPoses.get(1)));
            default:
                return Optional.empty();
        }
    }

    public double getLatency() {
        if (base == null) return getFilteredResult().getLatencyMillis();
        return (base.getLatency() + getFilteredResult().getLatencyMillis()) / 2;
    }

    public boolean hasTargets() {
        if (base == null) return getFilteredResult().hasTargets();
        return (base.hasTargets() || getFilteredResult().hasTargets());
    }

    public int getTargetCount() {
        if (base == null) return getFilteredResult().getTargets().size();
        return (base.getTargetCount() + getFilteredResult().getTargets().size());
    }
}
