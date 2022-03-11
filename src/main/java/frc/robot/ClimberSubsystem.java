package frc.robot;

import com.ctre.phoenix.motorcontrol.VictorSPXControlMode;

import com.ctre.phoenix.motorcontrol.can.VictorSPX;

import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class ClimberSubsystem extends SubsystemBase {

    private final VictorSPX m_climberMotor = new VictorSPX(3);

    public void setSpeed(double speed) {

        m_climberMotor.set(VictorSPXControlMode.PercentOutput, speed);
        
    }
}