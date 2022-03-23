package frc.robot;

import com.ctre.phoenix.motorcontrol.can.WPI_VictorSPX;

import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class SecondaryIntakeSubsystem extends SubsystemBase {

    private final static WPI_VictorSPX m_secondaryIntakeMotor = new WPI_VictorSPX(6);

    public static void setSpeed(double speed) {

        m_secondaryIntakeMotor.set(speed);
        
    }
}


