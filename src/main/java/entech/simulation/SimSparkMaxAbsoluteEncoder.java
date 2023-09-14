// package entech.simulation;

// import com.revrobotics.RelativeEncoder;

// // package-private to the revrobotics package
// public class SimSparkMaxAbsoluteEncoder implements RelativeEncoder {
// // // package-private to the revrobotics package
// // final CANSparkMax sparkMax;

// // private SimDevice m_simDevice;
// // private SimDouble m_simPosition;
// // private SimDouble m_simVelocity;
// // private SimDouble m_simPositionConversionFactor;
// // private SimDouble m_simVelocityConversionFactor;
// // private SimInt m_simAverageDepth;
// // private SimInt m_simMeasurementPeriod;
// // private SimBoolean m_simInverted;

// // // package-private to the revrobotics package
// // public SimSparkMaxAbsoluteEncoder(CANSparkMax sparkMax) {

// // m_simDevice = SimDevice.create("CANSparkMax[" + sparkMax.getDeviceId() +
// "] - AlternateEncoder");

// // if (m_simDevice != null) {
// // m_simPosition = m_simDevice.createDouble("Position", Direction.kOutput,
// 0.0);
// // m_simVelocity = m_simDevice.createDouble("Velocity", Direction.kOutput,
// 0.0);
// // m_simPositionConversionFactor = m_simDevice.createDouble("Position
// Conversion Factor", Direction.kOutput,
// // 1.0);
// // m_simVelocityConversionFactor = m_simDevice.createDouble("Idle Mode",
// Direction.kOutput, 1.0);
// // m_simAverageDepth = m_simDevice.createInt("Average Depth",
// Direction.kOutput, 1);
// // m_simMeasurementPeriod = m_simDevice.createInt("Measurement Period",
// Direction.kOutput, 0);
// // m_simInverted = m_simDevice.createBoolean("Inverted", Direction.kOutput,
// sparkMax.getInverted());
// // }

// // this.sparkMax = sparkMax;
// // }

// // public double getPosition() {
// // if (m_simDevice != null) {
// // return m_simPosition.get();
// // } else {
// // return CANSparkMaxJNI.c_SparkMax_GetAltEncoderPosition(sparkMax.);
// // }
// // }

// // public double getVelocity() {
// // if (m_simDevice != null) {
// // return m_simVelocity.get();
// // } else {
// // return
// CANSparkMaxJNI.c_SparkMax_GetAltEncoderVelocity(sparkMax.m_sparkMax);
// // }
// // }

// // public REVLibError setPosition(double position) {
// // if (m_simDevice != null) {
// // m_simPosition.set(position);
// // return REVLibError.kOk;
// // } else {
// // return REVLibError
// //
// .fromInt(CANSparkMaxJNI.c_SparkMax_SetAltEncoderPosition(sparkMax.m_sparkMax,
// (float) position));
// // }
// // }

// // public REVLibError setPositionConversionFactor(double factor) {
// // if (m_simDevice != null) {
// // m_simPositionConversionFactor.set(factor);
// // return REVLibError.kOk;
// // } else {
// // return REVLibError.fromInt(
// // CANSparkMaxJNI.c_SparkMax_SetAltEncoderPositionFactor(sparkMax.m_sparkMax,
// (float) factor));
// // }
// // }

// // public REVLibError setVelocityConversionFactor(double factor) {
// // if (m_simDevice != null) {
// // m_simVelocityConversionFactor.set(factor);
// // return REVLibError.kOk;
// // } else {
// // return REVLibError.fromInt(
// // CANSparkMaxJNI.c_SparkMax_SetAltEncoderVelocityFactor(sparkMax.m_sparkMax,
// (float) factor));
// // }
// // }

// // public double getPositionConversionFactor() {
// // sparkMax.throwIfClosed();
// // if (m_simDevice != null) {
// // return m_simPositionConversionFactor.get();
// // } else {
// // return
// CANSparkMaxJNI.c_SparkMax_GetAltEncoderPositionFactor(sparkMax.m_sparkMax);
// // }
// // }

// // public double getVelocityConversionFactor() {
// // sparkMax.throwIfClosed();
// // if (m_simDevice != null) {
// // return m_simVelocityConversionFactor.get();
// // } else {
// // return
// CANSparkMaxJNI.c_SparkMax_GetAltEncoderVelocityFactor(sparkMax.m_sparkMax);
// // }
// // }

// // public REVLibError setAverageDepth(int depth) {
// // sparkMax.throwIfClosed();
// // if (m_simDevice != null) {
// // m_simAverageDepth.set(depth);
// // return REVLibError.kOk;
// // } else {
// // return
// REVLibError.fromInt(CANSparkMaxJNI.c_SparkMax_SetAltEncoderAverageDepth(sparkMax.m_sparkMax,
// depth));
// // }
// // }

// // public int getAverageDepth() {
// // sparkMax.throwIfClosed();
// // if (m_simDevice != null) {
// // return m_simAverageDepth.get();
// // } else {
// // return
// CANSparkMaxJNI.c_SparkMax_GetAltEncoderAverageDepth(sparkMax.m_sparkMax);
// // }
// // }

// // public REVLibError setMeasurementPeriod(int period_us) {
// // sparkMax.throwIfClosed();
// // if (m_simDevice != null) {
// // m_simMeasurementPeriod.set(period_us);
// // return REVLibError.kOk;
// // } else {
// // return REVLibError
// //
// .fromInt(CANSparkMaxJNI.c_SparkMax_SetAltEncoderMeasurementPeriod(sparkMax.m_sparkMax,
// period_us));
// // }
// // }

// // public int getMeasurementPeriod() {
// // sparkMax.throwIfClosed();
// // if (m_simDevice != null) {
// // return countsPerRev;
// // } else {
// // return
// CANSparkMaxJNI.c_SparkMax_GetAltEncoderMeasurementPeriod(sparkMax.m_sparkMax);
// // }
// // }

// // public int getCountsPerRevolution() {
// // sparkMax.throwIfClosed();
// // if (m_simDevice != null) {
// // return countsPerRev;
// // } else {
// // return
// CANSparkMaxJNI.c_SparkMax_GetAltEncoderCountsPerRevolution(sparkMax.m_sparkMax);
// // }
// // }

// // @Override
// // public REVLibError setInverted(boolean inverted) {
// // sparkMax.throwIfClosed();
// // if (m_simDevice != null) {
// // m_simInverted.set(inverted);
// // return REVLibError.kOk;
// // } else {
// // return
// REVLibError.fromInt(CANSparkMaxJNI.c_SparkMax_SetAltEncoderInverted(sparkMax.m_sparkMax,
// inverted));
// // }
// // }

// // @Override
// // public boolean getInverted() {
// // sparkMax.throwIfClosed();
// // if (m_simDevice != null) {
// // return m_simInverted.get();
// // } else {
// // return
// CANSparkMaxJNI.c_SparkMax_GetAltEncoderInverted(sparkMax.m_sparkMax);
// // }
// // }

// // // package-private to the revrobotics package
// // int getSparkMaxFeedbackDeviceId() {
// // return FeedbackSensorType.kQuadrature.value;
// // }
// }