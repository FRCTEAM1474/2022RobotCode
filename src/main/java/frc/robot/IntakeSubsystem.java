package frc.robot;

import com.ctre.phoenix.motorcontrol.can.WPI_VictorSPX;

import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class IntakeSubsystem extends SubsystemBase {

    private final static WPI_VictorSPX m_intakeMotor = new WPI_VictorSPX(8);

    public static void setSpeed(double speed) {

        m_intakeMotor.set(speed);
        
    }
}
