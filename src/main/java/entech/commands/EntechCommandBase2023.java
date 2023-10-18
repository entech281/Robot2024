/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entech.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.EntechSubsystem;

public class EntechCommandBase2023 extends CommandBase {

    public static final double DEFAULT_TIMEOUT_SECONDS = 60.0;

    public EntechCommandBase2023() {
    	
    }
    public EntechCommandBase2023(EntechSubsystem subsystem) {
        this(subsystem, DEFAULT_TIMEOUT_SECONDS);
    }

    public EntechCommandBase2023(EntechSubsystem subsystem1, EntechSubsystem subsystem2) {
        addRequirements(subsystem1, subsystem2);
    }
  
    public EntechCommandBase2023(EntechSubsystem subsystem1, EntechSubsystem subsystem2, EntechSubsystem subsystem3) {
        addRequirements(subsystem1, subsystem2,subsystem3);
    }    
    
    public EntechCommandBase2023(EntechSubsystem subsystem, double timeout) {
        addRequirements(subsystem);
    }
}