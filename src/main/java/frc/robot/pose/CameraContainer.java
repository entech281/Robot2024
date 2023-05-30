package frc.robot.pose;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.photonvision.EstimatedRobotPose;
import org.photonvision.PhotonCamera;
import org.photonvision.PhotonPoseEstimator;
import org.photonvision.PhotonPoseEstimator.PoseStrategy;
import org.photonvision.targeting.PhotonPipelineResult;

import edu.wpi.first.apriltag.AprilTagFieldLayout;
import edu.wpi.first.math.geometry.Pose3d;
import edu.wpi.first.math.geometry.Transform3d;
import entech.util.EntechGeometryUtils;

public class CameraContainer {
    private PhotonCamera camera;
    private PhotonPoseEstimator estimator;

    private CameraContainer base;

    public CameraContainer(String cameraName, Transform3d robotToCamera, AprilTagFieldLayout fieldLayout, CameraContainer base) {
        camera = new PhotonCamera(cameraName);
        estimator = new PhotonPoseEstimator(fieldLayout, PoseStrategy.MULTI_TAG_PNP, camera, robotToCamera);
        estimator.setMultiTagFallbackStrategy(PoseStrategy.CLOSEST_TO_LAST_POSE);
        this.base = base;
    }

    private PhotonPipelineResult getResult() {
        return camera.getLatestResult();
    }

    public Optional<Pose3d> getEstimatedPose() {
        List<Pose3d> estPoses = new ArrayList<Pose3d>();
        Optional<EstimatedRobotPose> thisEstPose = estimator.update();
        if (thisEstPose.isPresent()) estPoses.add(thisEstPose.get().estimatedPose);
        Optional<Pose3d> baseEstPose = base.getEstimatedPose();
        if (baseEstPose.isPresent()) estPoses.add(baseEstPose.get());
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
        if (base == null) return getResult().getLatencyMillis();
        return (base.getLatency() + getResult().getLatencyMillis()) / 2;
    }

    public boolean hasTargets() {
        if (base == null) return getResult().hasTargets();
        return (base.hasTargets() || getResult().hasTargets());
    }

    public int getTargetCount() {
        if (base == null) return getResult().getTargets().size();
        return (base.getTargetCount() + getResult().getTargets().size());
    }
}
