package frc.robot;

import edu.wpi.first.wpilibj.motorcontrol.PWMSparkMax;

import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class FlywheelSubsystem extends SubsystemBase {

    //TalonSRX _talon3 = new TalonSRX(3);

    private final PWMSparkMax m_flywheelMotor = new PWMSparkMax(2);

    public void setSpeed(double speed) {

        m_flywheelMotor.set(speed);
        
    }
}