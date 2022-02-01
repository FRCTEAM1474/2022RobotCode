package frc.robot;

import edu.wpi.first.wpilibj.AddressableLED;
import edu.wpi.first.wpilibj.AddressableLEDBuffer;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class LightingSubsystem extends SubsystemBase{

    public AddressableLED m_led = new AddressableLED(5);
    public static AddressableLEDBuffer m_ledBuffer = new AddressableLEDBuffer(60);
    public static int m_redflux_FirstPixelHue;
    public static int m_blueflux_FirstPixelHue;


    //public static m_led = new AddressableLED(5);
  
    //public static m_ledBuffer = new AddressableLEDBuffer(60);

    //public static m_led.setLength(m_ledBuffer.getLength());
    
    //public static m_led.setData(m_ledBuffer);

    public static String lightColor = "red";
    public static String colorOfLight = "red";
    public static void setColor(String colorOfLight) {
        System.out.println("settingcolor");
        if (colorOfLight == "blue") {
            blueflux();
            System.out.println("changing to blue");

        }
        else{
            redflux();
            System.out.println("changing to red");


        }


        
    }
    
    public static void blueflux(){
        for (var i = 0; i < m_ledBuffer.getLength(); i++) {
          final var blue = (m_blueflux_FirstPixelHue + (i * 180 / m_ledBuffer.getLength())) % 180;
          //m_ledBuffer.setHSV(i, 196, sat, 84);
          m_ledBuffer.setRGB(i, 0, 0, blue);
        }
        m_blueflux_FirstPixelHue++;
    
        m_blueflux_FirstPixelHue %= 180;
        //for (var i = 0; i < m_ledBuffer.getLength(); i++) {
          //m_ledBuffer.setRGB(i, 0, 0, 255);
        //}
      }
      public static void redflux(){
        for (var i = 0; i < m_ledBuffer.getLength(); i++) {
          final var red = (m_redflux_FirstPixelHue + (i * 180 / m_ledBuffer.getLength())) % 180;
          //m_ledBuffer.setHSV(i, 196, sat, 84);
          m_ledBuffer.setRGB(i, red, 0, 0);
        }
        m_redflux_FirstPixelHue++;
    
        m_redflux_FirstPixelHue %= 180;
        //for (var i = 0; i < m_ledBuffer.getLength(); i++) {
          //m_ledBuffer.setRGB(i, 0, 0, 255);
        //}
      }
}
