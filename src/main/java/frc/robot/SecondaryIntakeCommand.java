package frc.robot;

import edu.wpi.first.wpilibj2.command.CommandBase;

public class SecondaryIntakeCommand extends CommandBase {
    double m_Speed;
    public SecondaryIntakeCommand(double speed){
        m_Speed = speed;
    }
    @Override
    public void initialize() {

        Robot.secondaryintake.setSpeed(m_Speed);
    }

    @Override
    public void execute() {

        Robot.secondaryintake.setSpeed(m_Speed);
    }

    @Override
    public void end(boolean interup){

        Robot.secondaryintake.setSpeed(0);
    }
}