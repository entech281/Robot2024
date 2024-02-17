package frc.robot.subsystems;

import java.util.List;
import java.util.Optional;

import org.photonvision.targeting.PhotonTrackedTarget;

import edu.wpi.first.math.geometry.Pose2d;
import entech.subsystems.SubsystemOutput;


public class VisionOutput implements SubsystemOutput {
    public Optional<Double> timeStamp;
    public Optional<Pose2d> estimatedPose;
    public double latency;
    public int numberOfTarets;
    public boolean hasTargets;
    public List<PhotonTrackedTarget> targets;

    @Override
    public void log() {

    }
}
