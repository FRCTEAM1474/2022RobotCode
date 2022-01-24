package frc.robot;

import edu.wpi.first.wpilibj2.command.CommandBase;

public class SecondaryIntakeCommand extends CommandBase {
    double m_Speed;
    public SecondaryIntakeCommand(double speed){
        m_Speed = speed;
    }
    @Override
    public void initialize() {
        //System.out.println("im initializing");
        Robot.secondaryintake.setSpeed(m_Speed);
    }

    @Override
    public void execute() {
        //System.out.println("im executing");
        Robot.secondaryintake.setSpeed(m_Speed);
    }

    @Override
    public void end(boolean interup){
        //System.out.println("im ending");
        Robot.secondaryintake.setSpeed(0);
    }
}