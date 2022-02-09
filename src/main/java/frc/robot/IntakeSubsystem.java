package frc.robot;

import com.ctre.phoenix.motorcontrol.ControlMode;

import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class IntakeSubsystem extends SubsystemBase {

    TalonSRX _talon0 = new TalonSRX(1);

    public void setSpeed(double speed) {

        _talon0.set(ControlMode.PercentOutput, speed);
        
    }
}
