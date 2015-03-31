
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.URL;
import java.text.DecimalFormat;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import org.parabot.environment.api.interfaces.Paintable;
import org.parabot.environment.api.utils.Time;
import org.parabot.environment.api.utils.Timer;
import org.parabot.environment.scripts.Category;
import org.parabot.environment.scripts.Script;
import org.parabot.environment.scripts.ScriptManifest;
import org.parabot.environment.scripts.framework.Strategy;
import org.rev317.min.api.methods.Skill;

@ScriptManifest(author = "Brookpc",
category = Category.PRAYER, 
description = "Uses Choice of Bone/Ash on altar",
name = "USPrayer",
servers = { "Ultimate Scape" },
version = 1.2)

public class USPrayer extends Script implements Paintable {

	ArrayList<Strategy> strategies = new ArrayList<Strategy>();
	//Bones,BigBones,DragBones,InfAsh
	final int[] BONE_ID = {527,533,537,20269};
	public static int useBone;
			
	//Paint
	private final Color textColor = new Color(255, 255, 255);
	private final Font textFont = new Font("Arial", 0, 14);
	private final Timer RUNTIME = new Timer();
	private static Image img;
	public static int blessCount;
	private static int gainedLvl;
	private static int startLvl;
	
	//GUI
	Gui x = new Gui();
	boolean guiWait = true;

	/***************************************************************************************************************************************/
	
	@Override
	public boolean onExecute() {
		x.setVisible(true);
		while (x.isRunning && guiWait) {
			Time.sleep(200);
		}
		
	    //startLvl = Skill.PRAYER.getLevel();
		img = getImage("http://i.imgur.com/BlTvmiW.png");
		
		strategies.add(new Relog());
		strategies.add(new DoBank());
		strategies.add(new PrayAlter());
		
		

		provide(strategies);
		return true;
	}

	/***************************************************************************************************************************************/
	
	@Override
	public void paint(Graphics arg0) {
		
		Graphics2D g = (Graphics2D) arg0;
		g.drawImage(img, 4, 23, null);
		g.setFont(textFont);
		g.setColor(textColor);
		g.drawString("----", 82, 57);
		g.drawString("----", 85, 70);
		g.drawString("" + RUNTIME, 82, 83);

	}
	
	/***************************************************************************************************************************************/
	
	public static int gainedLevel() {
		
		//gainedLvl = Skill.COOKING.getLevel() - startLvl;
		
		return gainedLvl;
	}
	
	/***************************************************************************************************************************************/
	
	public static Image getImage(String url) {
		try {
			return ImageIO.read(new URL(url));
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}

	/***************************************************************************************************************************************/

	public String addDecimals(int i) {
		DecimalFormat x = new DecimalFormat("#,###");
		return "" + x.format(i);
	}

	/***************************************************************************************************************************************/
	
	public class Gui extends JFrame {
		private static final long serialVersionUID = -6241803601296202605L;
		public boolean isRunning = true;
		private JPanel contentPane;

		public void main(String[] args) {
			EventQueue.invokeLater(new Runnable() {
				public void run() {
					try {
						Gui frame = new Gui();
						frame.setVisible(true);
					} catch (Exception e) {
						System.out.println("Line 136");
					}
				}
			});
		}

		@SuppressWarnings({ "rawtypes", "unchecked" })
		public Gui() {
			setResizable(false);
			setTitle("USPrayer");
			setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
			setBounds(100, 100, 177, 139);
			contentPane = new JPanel();
			contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
			setContentPane(contentPane);
			contentPane.setLayout(null);
			
			JComboBox comboBox = new JComboBox();
			comboBox.setModel(new DefaultComboBoxModel(new String[] {"Bones", "Big Bones", "Dragon Bones", "Infernal Ashes"}));
			comboBox.setBounds(26, 46, 109, 20);
			contentPane.add(comboBox);
			
			JLabel lblUsprayer = new JLabel("USPrayer");
			lblUsprayer.setFont(new Font("Trebuchet MS", Font.PLAIN, 16));
			lblUsprayer.setBounds(47, 0, 78, 48);
			contentPane.add(lblUsprayer);

			//Start Button
			JButton btnStart = new JButton("Start");
			btnStart.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {

					if(comboBox.getSelectedIndex() == 0) {
						useBone = BONE_ID[0];
					}
					else if(comboBox.getSelectedIndex() == 1) {
						useBone = BONE_ID[1];
					}
					else if(comboBox.getSelectedIndex() == 2) {
						useBone = BONE_ID[2];
					}
					else if(comboBox.getSelectedIndex() == 3) {
						useBone = BONE_ID[3];
					}

					guiWait = false;
					isRunning = false;
					x.dispose();
				}

			});
			btnStart.setBounds(36, 77, 89, 23);
			contentPane.add(btnStart);
		}

	}
}