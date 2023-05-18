package main;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class App extends JFrame {
	private JPanel orbitPanel;
	private JLabel sunMassLabel;
	private JTextField sunMassTextField;
	private JLabel earthAltitudeLabel;
	private JTextField earthAltitudeTextField;
	private JButton okButton;
	private ActionListener buttonPressListener;
	
	private Mass sun = new Mass(null, 0, 8);
	private Mass earth = new Mass(sun, 40, 4);
	
	public App() {
		orbitPanel = new JPanel() {
			@Override
			public void paintComponent(Graphics g) {
				super.paintComponent(g);
				Graphics2D g2d = (Graphics2D) g;
				//g.drawLine(sun.x - sun.r - earth.d, sun.y, sun.x + sun.r + earth.d, sun.y);
				earth.move();
				g2d.rotate(Math.toRadians(earth.getRotationAngle()), 400, 400);
				g2d.drawOval(sun.x - sun.r, sun.y - sun.r, sun.r * 2, sun.r * 2);
				g2d.drawOval(earth.x - earth.r, earth.y - earth.r, earth.r * 2, earth.r * 2);
				g2d.rotate(-Math.toRadians(earth.getRotationAngle()), 400, 400);
				try {
					Thread.sleep(16);
					repaint();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		};
		buttonPressListener = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				sun.g = (40.5 * Integer.parseInt(sunMassTextField.getText())) / (Math.pow(Integer.parseInt(earthAltitudeTextField.getText()) + sun.r + 4, 2));
				earth = new Mass(sun, Integer.parseInt(earthAltitudeTextField.getText()) + 39, 4);
			}
		};
		sunMassLabel = new JLabel("Sun Mass:");
		sunMassTextField = new JTextField("1", 2);
		earthAltitudeLabel = new JLabel("Earth Altitude:");
		earthAltitudeTextField = new JTextField("1", 2);
		okButton = new JButton("Ok");
		okButton.addActionListener(buttonPressListener);
		orbitPanel.add(sunMassLabel);
		orbitPanel.add(sunMassTextField);
		orbitPanel.add(earthAltitudeLabel);
		orbitPanel.add(earthAltitudeTextField);
		orbitPanel.add(okButton);
		setSize(800, 800);
		add(orbitPanel);
		setVisible(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
	public static void main(String[] args) {
		new App();
	}
}