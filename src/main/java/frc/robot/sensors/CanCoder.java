// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.sensors;

import com.ctre.phoenix.sensors.CANCoder;

import edu.wpi.first.math.util.Units;

/**
 * The {@code CanCoder} class contains fields and methods pertaining to the
 * function of the absolute encoder.
 */
public class CanCoder implements AbsoluteEncoder {
	private CANCoder _canCoder;
	private boolean inverted;
	private double positionOffset;

	public CanCoder(int port) {
		this._canCoder = new CANCoder(port);
		this.inverted = false;
		this.positionOffset = 0.0;
	}

	@Override
	public double getPosition() {
		return (inverted ? -1.0 : 1.0) * Units.degreesToRadians(_canCoder.getAbsolutePosition());
	}

	@Override
	public void setInverted(boolean inverted) {
		this.inverted = inverted;
	}

	@Override
	public void setPositionOffset(double offset) {
		positionOffset = offset;
	}

	@Override
	public double getPositionOffset() {
		return positionOffset;
	}

	@Override
	public double getVirtualPosition() {
		return getPosition() - positionOffset;
	}

	@Override
	public void resetVirtualPosition() {
		positionOffset = getPosition();
	}
}
