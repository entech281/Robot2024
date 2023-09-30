package frc.robot;

/**
 * Contains the definitions of all the ports
 */
public class Ports {

	// IP (v4) addresses
	// The purpose of this section is to serve as a reminder of what static IP (v4)
	// addresses are used so they are consistent
	// between the competition and practice robots.
	//
	// The radio is automatically set to 10.24.95.1
	// The Rio is set to static 10.24.95.2, mask 255.255.255.0
	// The Limelight is set to 10.24.95.11, mask 255.255.255.0, gateway 10.24.95.1
	// but note that pressing the reset button will revert to DHCP.
	// The Raspberry Pi running FRCVision is set to static 10.24.95.12, mask
	// 255.255.255.0, gateway 10.24.95.1, DNS blank
	//
	// If a device cannot be accessed (e.g. because its address was somehow obtained
	// via DHCP and mDNS is not working),
	// use Angry IP Scanner to find it!

	/**
	 * Digital ports
	 */
	public static class Digital {
		public static final int CHECK_PRESSURE = 0;

	}

	/**
	 * Analog ports
	 */
	public static class Analog {
		// public static final int SONAR = 3;
		// public static final int PRESSURE_SENSOR = 1;

		// 2023 Off-season
		// SPARK MAX Absolute encoders
		public static final int FRONT_LEFT_TURNING_ABSOLUTE_ENCODER = 1;
		public static final int REAR_LEFT_TURNING_ABSOLUTE_ENCODER = 2;
		public static final int FRONT_RIGHT_TURNING_ABSOLUTE_ENCODER = 0;
		public static final int REAR_RIGHT_TURNING_ABSOLUTE_ENCODER = 3;
	}

	/**
	 * Relays
	 */
	public static class Relay {
		public static final int COMPRESSOR_RELAY = 0;
	}

	/**
	 * CAN Ids
	 */
	public static class CAN {

		// 2023 Off-season
		public static final int PCM = 1;
		public static final int PDP = 0;

		// SPARK MAX CAN IDs
		public static final int FRONT_LEFT_DRIVING = 10;
		public static final int REAR_LEFT_DRIVING = 6;
		public static final int FRONT_RIGHT_DRIVING = 21;
		public static final int REAR_RIGHT_DRIVING = 4;

		public static final int FRONT_LEFT_TURNING = 12;
		public static final int REAR_LEFT_TURNING = 5;
		public static final int FRONT_RIGHT_TURNING = 22;
		public static final int REAR_RIGHT_TURNING = 3;
	}

	/**
	 * USB ports
	 */
	public static class USB {
		public static final int RIGHT = 0;
		public static final int LEFT = 1;
		public static final int GAMEPAD = 2;
	}

	/**
	 * PCM ports
	 */
	public static class PCM {

		// 2023 robot
		public static final int GEAR_HIGH = 1;
		public static final int GEAR_LOW = 0;
		public static final int CLAW_CLOSED = 3;
		public static final int CLAW_OPEN = 2;
		public static final int BRAKE_ENGAGED = 5;
		public static final int BRAKE_RELEASED = 4;
	}

	/**
	 * PWM ports
	 */
	public static class PWM {
		public static final int LED_STRIP = 9;
	}

	/**
	 * USB cameras
	 */
	public static class UsbCamera {
		public static final int PRIMARY_CAMERA = 0;
		public static final int BOTTOM_CAMERA = 1;
		public static final int TOP_CAMERA = 2;
	}
}
