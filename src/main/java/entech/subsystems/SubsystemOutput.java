package entech.subsystems;

public interface SubsystemOutput {

  public void log();

  public default String getLogName(String val) {
    return getClass().getSimpleName() + "/" + val;
  }

}
