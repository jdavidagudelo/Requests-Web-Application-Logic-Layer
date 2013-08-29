/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package co.edu.udea.logic.generictree;

import java.applet.Applet;
import java.awt.*;

/**
 *
 * @author Daniel F Agudelo
 */
public class Aple extends Applet {

    /**
     * Initialization method that will be called after the applet is loaded
     * into the browser.
     */
    @Override
    public void init() 
    {
        this.setBackground(Color.red);
    }
    @Override
    public void paint(Graphics g)
    {
        g.drawString("Hello world this is a bad thing", 5, 35);
    }
}
