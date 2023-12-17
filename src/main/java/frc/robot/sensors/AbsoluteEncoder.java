package frc.robot.sensors;

/**
 * Base Absolute Encoder interface for interchangeable encoders.
 */
public interface AbsoluteEncoder {

    /**
     * Returns the current raw position of the absolute encoder.
     *
     * @return the current raw position of the absolute encoder in radians.
     */
    double getPosition();

    /**
     * Inverts the absolute encoder.
     * 
     * @param inverted flag indicating if inverted.
     */
    void setInverted(boolean inverted);

    /**
     * Sets the position offset between the raw position and the virtual position.
     * 
     * @param offset offset in radians
     */
    void setPositionOffset(double offset);

    /**
     * Returns the position offset between the raw position and the virtual
     * position.
     *
     * @return the position offset in radians.
     */
    double getPositionOffset();

    /**
     * Returns the virtual position of the absolute encoder (raw position minus
     * offset).
     *
     * @return the virtual position in radians.
     */
    double getVirtualPosition();

    /**
     * Resets the virtual position to the current raw position.
     */
    void resetVirtualPosition();

}