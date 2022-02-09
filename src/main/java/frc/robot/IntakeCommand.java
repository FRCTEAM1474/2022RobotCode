package frc.robot;

import edu.wpi.first.wpilibj2.command.CommandBase;

public class IntakeCommand extends CommandBase {

    double m_Speed;

    public IntakeCommand(double speed){

        m_Speed = speed;

    }

    @Override

    public void initialize() {

        Robot.intake.setSpeed(m_Speed);

    }

    @Override

    public void execute() {

        Robot.intake.setSpeed(m_Speed);

    }

    @Override

    public void end(boolean interup){

        Robot.intake.setSpeed(0);
        
    }
}