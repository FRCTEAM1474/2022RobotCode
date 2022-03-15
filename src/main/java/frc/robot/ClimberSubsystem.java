package frc.robot;

import com.ctre.phoenix.motorcontrol.VictorSPXControlMode;

import com.ctre.phoenix.motorcontrol.can.VictorSPX;

import edu.wpi.first.wpilibj.DigitalInput;

import edu.wpi.first.wpilibj.Joystick;

import edu.wpi.first.wpilibj2.command.SubsystemBase;

import edu.wpi.first.wpilibj2.command.button.JoystickButton;

public class ClimberSubsystem extends SubsystemBase {

    private final VictorSPX m_climberMotor = new VictorSPX(3);

    private final Joystick m_overrideJoystick = new Joystick(0);

    JoystickButton climberDownLimitSwitchOverrideButton = new JoystickButton(m_overrideJoystick, 8);

    DigitalInput climberDownLimitSwitch = new DigitalInput(0);

    public void setSpeed(double speed) {
        if (climberDownLimitSwitch.get()) {

            m_climberMotor.set(VictorSPXControlMode.PercentOutput, speed);

        } else {

            if (m_overrideJoystick.getRawButton(8)){

                m_climberMotor.set(VictorSPXControlMode.PercentOutput, speed);

            } else {

                m_climberMotor.set(VictorSPXControlMode.PercentOutput, 0);

            }
        }
    }
}