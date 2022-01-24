package frc.robot;

import edu.wpi.first.wpilibj2.command.CommandBase;

public class IntakeCommand extends CommandBase {
    double m_Speed;
    public IntakeCommand(double speed){
        m_Speed = speed;
    }
    @Override
    public void initialize() {
        //System.out.println("im initializing");
        Robot.intake.setSpeed(m_Speed);
    }

    @Override
    public void execute() {
        //System.out.println("im executing");
        Robot.intake.setSpeed(m_Speed);
    }

    @Override
    public void end(boolean interup){
        //System.out.println("im ending");
        Robot.intake.setSpeed(0);
    }
}