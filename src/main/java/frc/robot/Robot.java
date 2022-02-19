// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj.GenericHID;

//import edu.wpi.first.wpilibj.motorcontrol.Talon;

//import edu.wpi.first.wpilibj.motorcontrol.PWMMotorController;

//import edu.wpi.first.wpilibj.motorcontrol.Spark;

import edu.wpi.first.wpilibj.Joystick;

import edu.wpi.first.wpilibj.TimedRobot;

import edu.wpi.first.wpilibj.Timer;

import edu.wpi.first.wpilibj.drive.DifferentialDrive;

import edu.wpi.first.wpilibj.motorcontrol.MotorControllerGroup;

//import edu.wpi.first.wpilibj.motorcontrol.PWMSparkMax;

import edu.wpi.first.wpilibj2.command.CommandScheduler;

import edu.wpi.first.wpilibj2.command.button.JoystickButton;

public class Robot extends TimedRobot {

  // maps the MOTORS to the MOTOR CONTROLLERS

  //private final PWMSparkMax m_motorZero = new PWMSparkMax(0);

  private final WPI_TalonSRX m_motorZero = new WPI_TalonSRX(1);

  //private final static MotorController m_motorZero = (MotorController) new TalonSRX(0);

  //private final PWMSparkMax m_motorOne = new PWMSparkMax(1);

  private final WPI_TalonSRX m_motorOne = new WPI_TalonSRX(2);
  

  //private final static MotorController m_motorOne = (MotorController) new TalonSRX(1);

  //private final PWMSparkMax m_motorTwo = new PWMSparkMax(2);

  private final WPI_TalonSRX m_motorTwo = new WPI_TalonSRX(3);

  //private final static MotorController m_motorTwo = (MotorController) new TalonSRX(2);

  //private final PWMSparkMax m_motorThree = new PWMSparkMax(3);

  private final WPI_TalonSRX m_motorThree = new WPI_TalonSRX(4);

  //private final static MotorController m_motorThree = (MotorController) new TalonSRX(3);

  MotorControllerGroup m_Right = new MotorControllerGroup(m_motorZero, m_motorOne);

  MotorControllerGroup m_Left = new MotorControllerGroup(m_motorTwo, m_motorThree);

  private final DifferentialDrive m_robotDrive = new DifferentialDrive(m_Left, m_Right);

  private final Joystick m_stick = new Joystick(0);

  public static IntakeSubsystem intake;

  public static SecondaryIntakeSubsystem secondaryintake;

  public static FlywheelSubsystem flywheel;

  public static Bling bling;

  double flywheelSpeedLimit = 1;

  private double startTime;
  
  @Override

  public void robotInit() {

    // We need to invert one side of the drivetrain so that positive voltages
    // result in both sides moving forward. Depending on how your robot's
    // gearbox is constructed, you might have to invert the left side instead.

    m_Left.setInverted(true);

    bling = new Bling();

    intake = new IntakeSubsystem();

    secondaryintake = new SecondaryIntakeSubsystem();

    flywheel = new FlywheelSubsystem();

    JoystickButton intakeInButton = new JoystickButton(m_stick, 2);

    JoystickButton outtakeButton = new JoystickButton(m_stick, 5);

    JoystickButton secondaryIntakeButton = new JoystickButton(m_stick, 4);

    JoystickButton secondaryOuttakeButton = new JoystickButton(m_stick, 6);

    JoystickButton slowFlywheelButton = new JoystickButton(m_stick, 1);

    JoystickButton fastFlywheelButton = new JoystickButton(m_stick, 3);

    intakeInButton.whileActiveOnce(new IntakeCommand(.5));

    outtakeButton.whileActiveOnce(new IntakeCommand(-0.5));

    secondaryIntakeButton.whileActiveOnce(new SecondaryIntakeCommand(.5));

    secondaryOuttakeButton.whileActiveOnce(new SecondaryIntakeCommand(-0.5));

    slowFlywheelButton.whileActiveOnce(new FlywheelCommand(0.25));

    fastFlywheelButton.whileActiveOnce(new FlywheelCommand(1));

  }

  @Override
  public void autonomousInit() {
    startTime = Timer.getFPGATimestamp();
  }

  @Override
  public void autonomousPeriodic() {
    double time = Timer.getFPGATimestamp();
    //System.out.println(time - startTime);
    if (time - startTime < 3) {

      m_robotDrive.arcadeDrive(0.8, 0.2);

    }
    
    
    /*if (time - startTime < 3) {
      //m_Right.set(0.1);
      //m_Left.set(0.1);
      m_robotDrive.arcadeDrive(0.4, 0.4);
      //m_motorZero.set(0.5);
      //System.out.println(Timer.getFPGATimestamp());
    } else {
      //m_Right.set(0);
      //m_Left.set(0);
      m_robotDrive.arcadeDrive(0, 0);
      //m_motorZero.set(0);
      //System.out.println(Timer.getFPGATimestamp());
    }*/
  }

  @Override

  public void teleopPeriodic() {

    // Drive with arcade drive.
    // That means that the Y axis drives forward
    // and backward, and the X turns left and right.

    m_robotDrive.arcadeDrive(m_stick.getY(), -m_stick.getX());

    //m_robotDrive.arcadeDrive(m_controller.getRawAxis(1), -m_controller.getRawAxis(0));

    //flywheelSpeedLimit = (m_stick.getRawAxis(3)+1)/2.0;

    //JoystickButton flywheelButton = new JoystickButton(m_stick, 1);

    //flywheelButton.whileActiveOnce(new FlywheelCommand(flywheelSpeedLimit));
    
    //flywheelButton.whileActiveOnce(new FlywheelCommand(1*flywheelSpeedLimit));

    GenericHID intakeInControllerButton = new GenericHID(2);

    if (intakeInControllerButton.getRawButtonPressed(2)){

      new IntakeCommand(.5);

    }
    
  }

  @Override

  public void robotPeriodic() {

    CommandScheduler.getInstance().run();
    
  }
}

