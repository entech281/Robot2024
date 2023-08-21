package frc.robot.vision;

import java.util.ArrayList;

import org.photonvision.targeting.PhotonPipelineResult;
import org.photonvision.targeting.PhotonTrackedTarget;

public class FilteredPipelineResult extends PhotonPipelineResult {
    private boolean badPoseAmbiguity = false;
    private boolean badAreaSize = false;
    private boolean badDistance = false;

    public FilteredPipelineResult(double latency) {
        super(latency, new ArrayList<PhotonTrackedTarget>());
    }

    public void addedFilteredObject(PhotonTrackedTarget target) {
        targets.add(target);
    }

    public boolean isPoseAmbiguityBad() {
        return this.badPoseAmbiguity;
    }

    public void setBadPoseAmbiguity(boolean badPoseAmbiguity) {
        this.badPoseAmbiguity = badPoseAmbiguity;
    }

    public boolean isAreaSizeBad() {
        return this.badAreaSize;
    }

    public void setBadAreaSize(boolean badAreaSize) {
        this.badAreaSize = badAreaSize;
    }

    public boolean isDistanceBad() {
        return this.badDistance;
    }

    public void setBadDistance(boolean badDistance) {
        this.badDistance = badDistance;
    }
}
