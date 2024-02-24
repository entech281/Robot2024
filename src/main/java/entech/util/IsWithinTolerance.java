package entech.util;

public class IsWithinTolerance {

  public static boolean isWithinTolerance(double tolerance, double value,
      double requestedPosition) {
    if (Math.abs(requestedPosition - value) < tolerance) {
      return true;
    } else {
      return false;
    }
  }
}
