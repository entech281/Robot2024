package frc.robot.vision;

import java.util.List;
import java.util.Optional;

import org.photonvision.targeting.PhotonTrackedTarget;

import edu.wpi.first.math.geometry.Pose2d;

public class VisionDataPacket {
    private Optional<Double> timeStamp;
    private Optional<Pose2d> estimatedPose;
    private double latency;
    private int numberOfTarets;
    private boolean hasTargets;
    private List<PhotonTrackedTarget> targets;


    public Optional<Double> getTimeStamp() {
        return this.timeStamp;
    }

    public void setTimeStamp(Optional<Double> timeStamp) {
        this.timeStamp = timeStamp;
    }

    public Optional<Pose2d> getEstimatedPose() {
        return this.estimatedPose;
    }

    public void setEstimatedPose(Optional<Pose2d> estimatedPose) {
        this.estimatedPose = estimatedPose;
    }

    public double getLatency() {
        return this.latency;
    }

    public void setLatency(double latency) {
        this.latency = latency;
    }

    public int getNumberOfTarets() {
        return this.numberOfTarets;
    }

    public void setNumberOfTarets(int numberOfTarets) {
        this.numberOfTarets = numberOfTarets;
    }

    public boolean isHasTargets() {
        return this.hasTargets;
    }

    public boolean getHasTargets() {
        return this.hasTargets;
    }

    public void setHasTargets(boolean hasTargets) {
        this.hasTargets = hasTargets;
    }

    public List<PhotonTrackedTarget> getTargets() {
        return this.targets;
    }

    public void setTargets(List<PhotonTrackedTarget> targets) {
        this.targets = targets;
    }
}
