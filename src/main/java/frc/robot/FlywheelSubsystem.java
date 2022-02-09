package frc.robot;

import com.ctre.phoenix.motorcontrol.ControlMode;

import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class FlywheelSubsystem extends SubsystemBase {

    TalonSRX _talon3 = new TalonSRX(3);

    public void setSpeed(double speed) {

        _talon3.set(ControlMode.PercentOutput, speed);
        
    }
}