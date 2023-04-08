package entech.sensors;

import com.revrobotics.REVLibError;
import com.revrobotics.RelativeEncoder;

public class EntechRelativeEncoder implements RelativeEncoder {
    private double resetOffset = 0.0;
    private RelativeEncoder encoder;

    public EntechRelativeEncoder(RelativeEncoder encoder) {
        this.encoder = encoder;
    }

    public boolean equals(Object obj) {
        return encoder.equals(obj);
    }

    public double getPosition() {
        return encoder.getPosition() - resetOffset;
    }

    public double getVelocity() {
        return encoder.getVelocity();
    }

    public REVLibError setPosition(double position) {
        resetOffset = getPosition();
        return encoder.setPosition(position);
    }

    public REVLibError setPositionConversionFactor(double factor) {
        return encoder.setPositionConversionFactor(factor);
    }

    public REVLibError setVelocityConversionFactor(double factor) {
        return encoder.setVelocityConversionFactor(factor);
    }

    public double getPositionConversionFactor() {
        return encoder.getPositionConversionFactor();
    }

    public double getVelocityConversionFactor() {
        return encoder.getVelocityConversionFactor();
    }

    public REVLibError setAverageDepth(int depth) {
        return encoder.setAverageDepth(depth);
    }

    public int getAverageDepth() {
        return encoder.getAverageDepth();
    }

    public REVLibError setMeasurementPeriod(int period_ms) {
        return encoder.setMeasurementPeriod(period_ms);
    }

    public int getMeasurementPeriod() {
        return encoder.getMeasurementPeriod();
    }

    public int getCountsPerRevolution() {
        return encoder.getCountsPerRevolution();
    }

    public int hashCode() {
        return encoder.hashCode();
    }

    public REVLibError setInverted(boolean inverted) {
        return encoder.setInverted(inverted);
    }

    public boolean getInverted() {
        return encoder.getInverted();
    }

    public String toString() {
        return encoder.toString();
    }
}
