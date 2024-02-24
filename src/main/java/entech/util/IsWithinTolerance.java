package entech.util;

public class IsWithinTolerance {

  public static boolean isWithinTolerance(double tolerance, double value,
      double requestedPosition) {
    if (value < requestedPosition + tolerance || value > requestedPosition - tolerance) {
      return true;
    } else {
      return false;
    }
  }
}
