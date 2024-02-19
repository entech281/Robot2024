package entech.subsystems;

import org.littletonrobotics.junction.Logger;

public abstract class SubsystemOutputBasics implements SubsystemOutput {
    protected boolean active;
    protected double currentSpeed;
    protected boolean brakeModeEnabled;
    protected final String key;

    public SubsystemOutputBasics(String key) {
        this.key = key;
    }

    public boolean isActive() {
        return this.active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public double getCurrentSpeed() {
        return this.currentSpeed;
    }

    public void setCurrentSpeed(double currentSpeed) {
        this.currentSpeed = currentSpeed;
    }

    public boolean isBrakeModeEnabled() {
        return this.brakeModeEnabled;
    }

    public void setBrakeModeEnabled(boolean brakeModeEnabled) {
        this.brakeModeEnabled = brakeModeEnabled;
    }

    @Override
    public void log() {
        Logger.recordOutput(key + "/active", active);
        Logger.recordOutput(key + "/currentSpeed", currentSpeed);
        Logger.recordOutput(key + "/brakeModeEnabled", brakeModeEnabled);
    }
}
