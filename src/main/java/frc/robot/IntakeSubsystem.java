package frc.robot;

import edu.wpi.first.wpilibj.motorcontrol.PWMSparkMax;

import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class IntakeSubsystem extends SubsystemBase {

    //TalonSRX _talon0 = new TalonSRX(1);

    private final PWMSparkMax m_intakeMotor = new PWMSparkMax(0);

    public void setSpeed(double speed) {

        m_intakeMotor.set(speed);
        
    }
}
