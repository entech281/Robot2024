package frc.robot.processors.filters;

import java.util.Optional;
import org.opencv.core.Point;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.wpilibj.DriverStation.Alliance;
import frc.robot.subsystems.drive.DriveInput;
import frc.robot.subsystems.visionnotedetector.NoteDetectorOutput;

public class TestNoteAlignFilter {
  public static final double TOLERANCE = 0.00001;
  private static final double[] ANGLES = {0, 45, -45, 90, -90, 135, -135, 180, -180};
  private static final double[] CORRECT_X_POS = {1.0, 0.7071067811865476, 0.7071067811865476, 0.0,
      0.0, 0.7071067811865476, 0.7071067811865476, 1.0, 1.0};
  private static final double[] CORRECT_X_NEG = {-1.0, -0.7071067811865476, -0.7071067811865476,
      0.0, 0.0, -0.7071067811865476, -0.7071067811865476, -1.0, -1.0};

  private static final double[] CORRECT_Y_POS = {0.0, 0.7071067811865476, 0.7071067811865476, 1.0,
      1.0, 0.7071067811865476, 0.7071067811865476, 0.0, 0.0};
  private static final double[] CORRECT_Y_NEG = {0.0, -0.7071067811865476, -0.7071067811865476,
      -1.0, -1.0, -0.7071067811865476, -0.7071067811865476, 0.0, 0.0};

  // @ParameterizedTest
  // @ValueSource(ints = {0, 1, 2, 3, 4, 5, 6, 7, 8})
  // void testNoteAlignFilterX(int index) {
  // DriveInput pdiRed = process(1.0, 0.0, ANGLES[index], Alliance.Red, 0.0);
  // DriveInput ndiRed = process(-1.0, 0.0, ANGLES[index], Alliance.Red, 0.0);
  // DriveInput pdiBlue = process(1.0, 0.0, ANGLES[index], Alliance.Blue, 0.0);
  // DriveInput ndiBlue = process(-1.0, 0.0, ANGLES[index], Alliance.Blue, 0.0);
  // assertEquals(CORRECT_X_POS[index], pdiRed.getXSpeed(), TOLERANCE);
  // assertEquals(CORRECT_X_NEG[index], ndiRed.getXSpeed(), TOLERANCE);
  // assertEquals(CORRECT_X_POS[index], pdiBlue.getXSpeed(), TOLERANCE);
  // assertEquals(CORRECT_X_NEG[index], ndiBlue.getXSpeed(), TOLERANCE);
  // }

  // @ParameterizedTest
  // @ValueSource(ints = {0, 1, 2, 3, 4, 5, 6, 7, 8})
  // void testNoteAlignFilterY(int index) {
  // DriveInput pdiRed = process(0.0, 1.0, ANGLES[index], Alliance.Red, 0.0);
  // DriveInput ndiRed = process(0.0, -1.0, ANGLES[index], Alliance.Red, 0.0);
  // DriveInput pdiBlue = process(0.0, 1.0, ANGLES[index], Alliance.Blue, 0.0);
  // DriveInput ndiBlue = process(0.0, -1.0, ANGLES[index], Alliance.Blue, 0.0);
  // assertEquals(CORRECT_Y_POS[index], pdiRed.getYSpeed(), TOLERANCE);
  // assertEquals(CORRECT_Y_NEG[index], ndiRed.getYSpeed(), TOLERANCE);
  // assertEquals(CORRECT_Y_POS[index], pdiBlue.getYSpeed(), TOLERANCE);
  // assertEquals(CORRECT_Y_NEG[index], ndiBlue.getYSpeed(), TOLERANCE);
  // }

  private DriveInput process(double x, double y, double angle, Alliance team, double noteYaw) {
    DriveInput di = new DriveInput();
    di.setXSpeed(x);
    di.setYSpeed(y);
    di.setRotation(0.0);
    di.setGyroAngle(Rotation2d.fromDegrees(0.0));
    di.setLatestOdometryPose(new Pose2d(0.0, 0.0, Rotation2d.fromDegrees(angle)));

    NoteDetectorOutput ndo = new NoteDetectorOutput();
    ndo.setMidpoint(Optional.of(new Point()));
    ndo.setYaw(noteYaw);

    NoteAlignmentFilter naf = new NoteAlignmentFilter();

    return naf.process(di, ndo, team);
  }
}
