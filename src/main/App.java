import java.awt.*;
import java.awt.event.*;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import java.util.ArrayList;
public class App extends JFrame {
	private JPanel orbitPanel;
	private JLabel titleLabel;
	private ArrayList<JLabel> gLabels = new ArrayList<>();
	private ArrayList<JTextField> gTextFields = new ArrayList<>();
	private ArrayList<JLabel> altitudeLabels = new ArrayList<>();
	private ArrayList<JTextField> altitudeTextFields = new ArrayList<>();
	private ArrayList<JLabel> radiusLabels = new ArrayList<>();
	private ArrayList<JTextField> radiusTextFields = new ArrayList<>();
	private JLabel newMassAltitudeLabel;
	private JTextField newMassAltitudeTextField;
	private JLabel newMassRadiusLabel;
	private JTextField newMassRadiusTextField;
	private JLabel newMassGLabel;
	private JTextField newMassGTextField;
	private JButton addMassButton;
	private JButton okButton;
	private JButton vectorArrowButton;
	private ActionListener buttonPressListener;
	private boolean vectorArrows = false;

	private ArrayList<Mass> masses = new ArrayList<>();

	public App() {
		orbitPanel = new JPanel() {
			@Override
			public void paintComponent(Graphics g) {
				super.paintComponent(g);
				Graphics2D g2d = (Graphics2D) g;
				final Stroke defaultStroke = g2d.getStroke();
				g2d.setColor(Color.WHITE);
				for (Mass m : masses) {
					m.move();
					g2d.fillOval(m.x - m.r, m.y - m.r, m.r * 2, m.r * 2);
					if (m.origin != null && vectorArrows) {
						g2d.setColor(Color.RED);
						g2d.setStroke(new BasicStroke(3));
						g2d.drawLine(m.x, m.y, m.x + (int)(3 * Math.sqrt(m.origin.g * (m.origin.r + m.d)) * Math.cos(Math.toRadians(-m.theta + 90))), m.y - (int)(3 * Math.sqrt(m.origin.g * (m.origin.r + m.d)) * Math.sin(Math.toRadians(-m.theta + 90))));
						g2d.setColor(Color.BLUE);
						g2d.drawLine(m.x, m.y, m.x + (int)(30 * (m.origin.g) * Math.cos(Math.toRadians(-m.theta))), m.y - (int)(30 * (m.origin.g) * Math.sin(Math.toRadians(-m.theta))));
						g2d.setColor(Color.WHITE);
						g2d.setStroke(defaultStroke);
					}
				}
				try {
					Thread.sleep(16);
					repaint();
					revalidate();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		};

		orbitPanel.setBackground(Color.GRAY);
		buttonPressListener = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				for (int i = 0; i < masses.size(); i++) {
					final int d = (600/(int)(Math.pow(i+1, 3))) * Integer.parseInt(altitudeTextFields.get(i).getText());
					final int r = (40/(int)(Math.pow(i+1, 2))) * Integer.parseInt(radiusTextFields.get(i).getText());
					final double g = 0.5 * (Double.parseDouble(gTextFields.get(i).getText()));
					Mass origin = null;
					if (i > 0) {
						origin = masses.get(i-1);
					}
					masses.set(i, new Mass(origin, d, r, g));
				}
			}
		};
		String title = "";
		for (int i = 0; i < 180; i++) {
			title += " ";
		}
		titleLabel = new JLabel(title + "Multipliers" + title);
		newMassAltitudeLabel = new JLabel("Altitude   ");
		newMassAltitudeTextField = new JTextField("1", 3);
		newMassRadiusLabel = new JLabel("Radius   ");
		newMassRadiusTextField = new JTextField("1", 3);
		newMassGLabel = new JLabel("Add new mass:   Field strength   ");
		newMassGTextField = new JTextField("1", 3);
		addMassButton = new JButton("Add new mass");
		addMassButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				addMass(Integer.parseInt(newMassAltitudeTextField.getText()), Integer.parseInt(newMassRadiusTextField.getText()), Double.parseDouble(newMassGTextField.getText()));
				okButton.doClick();
			}
		});
		okButton = new JButton("Refresh");
		okButton.addActionListener(buttonPressListener);
		okButton.doClick();
		vectorArrowButton = new JButton("Toggle Vectors");
		vectorArrowButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				vectorArrows = !vectorArrows;
			}
		});
		orbitPanel.add(newMassGLabel);
		orbitPanel.add(newMassGTextField);
		orbitPanel.add(newMassAltitudeLabel);
		orbitPanel.add(newMassAltitudeTextField);
		orbitPanel.add(newMassRadiusLabel);
		orbitPanel.add(newMassRadiusTextField);
		orbitPanel.add(addMassButton);
		orbitPanel.add(okButton);
		orbitPanel.add(vectorArrowButton);
		setSize(800, 800);
		add(orbitPanel);
		setVisible(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

	public static void main(String[] args) {
		new App();
	}

	public void addMass(int d, int r, double g) {
		if (masses.isEmpty()) {
			masses.add(new Mass(null, d, r, g));
		} else {
			masses.add(new Mass(masses.get(masses.size() - 1), d, r, g));
		}
		gLabels.add(new JLabel("Mass " + gLabels.size() + " field strength          "));
		gTextFields.add(new JTextField("" + g, 5));
		altitudeLabels.add(new JLabel("Mass " + altitudeLabels.size() + " altitude          "));
		altitudeTextFields.add(new JTextField("" + d, 5));
		radiusLabels.add(new JLabel("Mass " + radiusLabels.size() + " radius                       "));
		radiusTextFields.add(new JTextField("" + r, 5));
		updateComponents();
	}

	public void updateComponents() {
		int i = gLabels.size()-1;
		orbitPanel.add(gLabels.get(i));
		orbitPanel.add(gTextFields.get(i));
		orbitPanel.add(altitudeLabels.get(i));
		orbitPanel.add(altitudeTextFields.get(i));
		orbitPanel.add(radiusLabels.get(i));
		orbitPanel.add(radiusTextFields.get(i));
	}
}
