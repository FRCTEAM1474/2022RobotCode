package frc.robot;

import edu.wpi.first.wpilibj2.command.CommandBase;

public class ClimberCommand extends CommandBase {

    double m_Speed;

    public ClimberCommand(double speed){

        m_Speed = speed;
        
    }
    
    @Override

    public void initialize() {

        Robot.climber.setSpeed(0);

    }

    @Override

    public void execute() {

        Robot.climber.setSpeed(m_Speed);

    }

    @Override

    public void end(boolean interup){

        Robot.climber.setSpeed(0);

    }
}