package frc.robot;

import com.revrobotics.CANSparkMax;

import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class FlywheelSubsystem extends SubsystemBase {

    //TalonSRX _talon3 = new TalonSRX(3);

    private final CANSparkMax m_flywheelMotor = new CANSparkMax(5, MotorType.kBrushless);

    public void setSpeed(double speed) {

        m_flywheelMotor.set(speed);
        
    }
}