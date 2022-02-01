package frc.robot;

import edu.wpi.first.wpilibj2.command.CommandBase;

public class LightingCommand extends CommandBase{
    public String lightColor = "red";
    @Override
    public void initialize() {
        System.out.println("im initializing");
        if (lightColor == "red") {
            String colorOfLight = "blue";
            setColor("red");
            System.out.println("changing to blue");

        }
        else{
            String colorOfLight = "blue";
            setColor("red");
            System.out.println("changing to red");


        }
    }

    @Override
    public void execute() {
        System.out.println("im executing");

    }

    @Override
    public void end(boolean interup){
        System.out.println("im ending");
        
    }
}
