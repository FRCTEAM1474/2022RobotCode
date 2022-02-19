package frc.robot;

import edu.wpi.first.wpilibj.motorcontrol.PWMSparkMax;

import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class SecondaryIntakeSubsystem extends SubsystemBase {

    //TalonSRX _talon2 = new TalonSRX(2);

    private final PWMSparkMax m_secondaryIntakeMotor = new PWMSparkMax(1);

    public void setSpeed(double speed) {

        m_secondaryIntakeMotor.set(speed);
        
    }
}
