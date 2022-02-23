package frc.robot;

import edu.wpi.first.wpilibj.motorcontrol.Talon;

import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class SecondaryIntakeSubsystem extends SubsystemBase {

    //TalonSRX _talon2 = new TalonSRX(2);

    private final Talon m_secondaryIntakeMotor = new Talon(0);

    public void setSpeed(double speed) {

        m_secondaryIntakeMotor.set(speed);
        
    }
}
