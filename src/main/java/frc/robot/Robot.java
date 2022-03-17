// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.cameraserver.CameraServer;

import edu.wpi.first.wpilibj.GenericHID;

import edu.wpi.first.wpilibj.Joystick;

import edu.wpi.first.wpilibj.TimedRobot;

import edu.wpi.first.wpilibj.Timer;

import edu.wpi.first.wpilibj.drive.DifferentialDrive;

import edu.wpi.first.wpilibj.motorcontrol.MotorControllerGroup;

import edu.wpi.first.wpilibj2.command.CommandScheduler;

import edu.wpi.first.wpilibj2.command.button.JoystickButton;

public class Robot extends TimedRobot {

  // maps the MOTORS to the MOTOR CONTROLLERS

  private final WPI_TalonSRX m_motorZero = new WPI_TalonSRX(2);

  private final WPI_TalonSRX m_motorOne = new WPI_TalonSRX(4);

  private final WPI_TalonSRX m_motorTwo = new WPI_TalonSRX(1);

  private final WPI_TalonSRX m_motorThree = new WPI_TalonSRX(7);

  MotorControllerGroup m_Right = new MotorControllerGroup(m_motorZero, m_motorOne);

  MotorControllerGroup m_Left = new MotorControllerGroup(m_motorTwo, m_motorThree);

  private final DifferentialDrive m_robotDrive = new DifferentialDrive(m_Left, m_Right);

  private final Joystick m_stick = new Joystick(0);

  private final Joystick m_stickTwo = new Joystick(1);

  public static IntakeSubsystem intake;

  public static SecondaryIntakeSubsystem secondaryintake;

  public static FlywheelSubsystem flywheel;

  public static Bling bling;

  public static ClimberSubsystem climber;

  //public static ServoSubsystem servo;

  double flywheelSpeedLimit = 1;

  private double startTime;
  
  @Override

  public void robotInit() {

    CameraServer.startAutomaticCapture();

    // We need to invert one side of the drivetrain so that positive voltages
    // result in both sides moving forward. Depending on how your robot's
    // gearbox is constructed, you might have to invert the left side instead.

    m_Left.setInverted(true);

    bling = new Bling();

    intake = new IntakeSubsystem();

    secondaryintake = new SecondaryIntakeSubsystem();

    flywheel = new FlywheelSubsystem();

    climber = new ClimberSubsystem();

    //servo = new ServoSubsystem();

    JoystickButton intakeInButton = new JoystickButton(m_stick, 5);

    JoystickButton outtakeButton = new JoystickButton(m_stick, 3);

    JoystickButton secondaryIntakeButton = new JoystickButton(m_stickTwo, 6);

    JoystickButton secondaryOuttakeButton = new JoystickButton(m_stickTwo, 4);

    JoystickButton slowFlywheelButton = new JoystickButton(m_stickTwo, 1);

    JoystickButton fastFlywheelButton = new JoystickButton(m_stickTwo, 2);

    JoystickButton reverseFlywheelButton = new JoystickButton(m_stickTwo, 8);

    JoystickButton climberUp = new JoystickButton(m_stick, 11);

    JoystickButton climberDown = new JoystickButton(m_stick, 12);

    JoystickButton climberStay = new JoystickButton(m_stick, 7);

    //JoystickButton servoButton = new JoystickButton(m_stickTwo, 8);

    //servoButton.whileActiveContinuous(new ServoCommand(0.5));

    intakeInButton.whileActiveOnce(new IntakeCommand(-1));

    outtakeButton.whileActiveOnce(new IntakeCommand(1));

    secondaryIntakeButton.whileActiveOnce(new SecondaryIntakeCommand(-1));

    secondaryOuttakeButton.whileActiveOnce(new SecondaryIntakeCommand(1));

    slowFlywheelButton.whileActiveOnce(new FlywheelCommand(-0.50));

    fastFlywheelButton.whileActiveOnce(new FlywheelCommand(-1));

    reverseFlywheelButton.whileActiveOnce(new FlywheelCommand(1));

    climberUp.whileActiveOnce(new ClimberCommand(1));

    climberDown.whileActiveOnce(new ClimberCommand(-1));

    climberDown.whenInactive(new ClimberCommand(0));

    climberUp.whenInactive(new ClimberCommand(0));

    climberStay.whileActiveOnce(new ClimberCommand(0));

  }

  @Override

  public void autonomousInit() {

    startTime = Timer.getFPGATimestamp();

  }

  @Override

  public void autonomousPeriodic() {

    double time = Timer.getFPGATimestamp();

    //System.out.println(time - startTime);

    if (time - startTime < 5 && time - startTime > 0) {

      //m_robotDrive.arcadeDrive(-1, -0.1);

      SecondaryIntakeSubsystem.setSpeed(-1);

      FlywheelSubsystem.setSpeed(-0.5);

      m_robotDrive.arcadeDrive(0, 0);

    }

    else if (time - startTime < 10.5 && time - startTime > 5) {

      SecondaryIntakeSubsystem.setSpeed(0);

      FlywheelSubsystem.setSpeed(0);

      m_robotDrive.arcadeDrive(-0.65, 0);

    }

    else if (time - startTime < 14 && time - startTime > 10.5) {

      SecondaryIntakeSubsystem.setSpeed(0);

      FlywheelSubsystem.setSpeed(0);

      m_robotDrive.arcadeDrive(0, 0);

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

    m_robotDrive.arcadeDrive(-m_stick.getY(), -m_stick.getX());

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