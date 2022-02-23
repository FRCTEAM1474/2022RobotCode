package frc.robot;

import edu.wpi.first.wpilibj.motorcontrol.Talon;

import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class IntakeSubsystem extends SubsystemBase {

    //TalonSRX _talon0 = new TalonSRX(1);

    private final Talon m_intakeMotor = new Talon(1);

    public void setSpeed(double speed) {

        m_intakeMotor.set(speed);
        
    }
}
