// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.wpilibj.Joystick;

import edu.wpi.first.wpilibj.TimedRobot;

import edu.wpi.first.wpilibj.drive.DifferentialDrive;

import edu.wpi.first.wpilibj.motorcontrol.MotorControllerGroup;

import edu.wpi.first.wpilibj.motorcontrol.PWMSparkMax;

import edu.wpi.first.wpilibj2.command.CommandScheduler;

import edu.wpi.first.wpilibj2.command.button.JoystickButton;

public class Robot extends TimedRobot {

  // maps the MOTORS to the MOTOR CONTROLLERS

  private final PWMSparkMax m_motorZero = new PWMSparkMax(0);

  private final PWMSparkMax m_motorOne = new PWMSparkMax(1);

  private final PWMSparkMax m_motorTwo = new PWMSparkMax(2);

  private final PWMSparkMax m_motorThree = new PWMSparkMax(3);

  MotorControllerGroup m_Right = new MotorControllerGroup(m_motorZero, m_motorOne);

  MotorControllerGroup m_Left = new MotorControllerGroup(m_motorTwo, m_motorThree);

  private final DifferentialDrive m_robotDrive = new DifferentialDrive(m_Left, m_Right);

  private final Joystick m_stick = new Joystick(0);

  public static IntakeSubsystem intake;

  public static SecondaryIntakeSubsystem secondaryintake;

  public static FlywheelSubsystem flywheel;

  public static Bling bling;

  double flywheelSpeedLimit = 1;
  
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

  public void teleopPeriodic() {

    // Drive with arcade drive.
    // That means that the Y axis drives forward
    // and backward, and the X turns left and right.

    m_robotDrive.arcadeDrive(m_stick.getY(), -m_stick.getX());

    //flywheelSpeedLimit = (m_stick.getRawAxis(3)+1)/2.0;

    //JoystickButton flywheelButton = new JoystickButton(m_stick, 1);

    //flywheelButton.whileActiveOnce(new FlywheelCommand(flywheelSpeedLimit));
    
    //flywheelButton.whileActiveOnce(new FlywheelCommand(1*flywheelSpeedLimit));
    
  }

  @Override

  public void robotPeriodic() {

    CommandScheduler.getInstance().run();
    
  }
}

