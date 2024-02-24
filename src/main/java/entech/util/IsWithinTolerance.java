package entech.util;

public class IsWithinTolerance {

  public static boolean isWithinTolerance(double tolerance, double value,
      double requestedPosition) {
    if (Math.abs(requestedPosition) - tolerance < value) {
      return true;
    } else {
      return false;
    }
  }
}
