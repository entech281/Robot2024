## Description

A basic template to help other teams set up their swerve before the completion of EntechLib 1.0.

## Instructions for setup

1. Go to .wpilib\wpilib_preferences.json and change your team number
2. Go to src\main\java\frc\robot\RobotConstants.java go to ports and configure your motor ports.
3. Set your encoder ports in your using can coders it's the can id, if your using thrifty encoders it should be the analog port id.
4. If your using cancoders go to src\main\java\frc\robot\swerve\SwerveModule.java and change the m_turningAbsoluteEncoder = new ThriftyEncoder(turningEncoderPort); (line 52) to create a cancoder instead, don't forget the import line.
5. Go to src\main\java\frc\robot\OI\OperatorInterface.java and set up the controls as you want to remove twist lock or x brake lock from the controls remove the line and if it is twist lck you are removing go to src\main\java\frc\robot\OI\UserPolicy.java and set twistable = true;.
6. Line up all your modules with the socket head bolt facing left of the robot.
7. Deploy the code and enable if it doesn't work try to trouble shoot simple problems else contact 281 again.
8. Get the values from "Raw abs encoders" off network tables contact 281 if you need help with this step.
9. Put the value from network tables as your virtual offsets in src\main\java\frc\robot\RobotConstants.java they are ordered on network tables as FrontLeft, FrontRight, RearLeft, RearRight.
10. Redeploy and check if everything works for gyro direction trouble try messing with kGyroReversed in src\main\java\frc\robot\RobotConstants.java or GYRO_ORIENTATION in src\main\java\frc\robot\subsystems\DriveSubsystem.java or contact 281 for further issues.