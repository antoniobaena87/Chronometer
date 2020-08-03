package main;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.Border;

public class ChronoPanel extends JPanel implements Runnable{
	
	JLabel[] digits;
	ImageIcon[] icons;
	
	boolean running = false;
	boolean killThread = false;
	
	int hours, minutes, seconds;
	String currentTime;
	
	public ChronoPanel() {
		
		Border loweredbevel = BorderFactory.createLoweredBevelBorder();
		this.setBorder(loweredbevel);
		this.setLayout(new BoxLayout(this, 0));
		
		digits = new JLabel[8];
		icons = new ImageIcon[11];
		// Load the digit images
		icons[10] = new ImageIcon(getClass().getResource("/assets/separador.gif"));
		for(int i = 0; i < icons.length - 1; i++) {
			icons[i] = new ImageIcon(getClass().getResource("/assets/" + i + ".gif"));
			
			if(i < digits.length) {
				digits[i] = new JLabel();
				if(i == 2 || i == 5) {
					digits[i].setIcon(icons[10]);
				}else {
					digits[i].setIcon(icons[0]);
				}
				this.add(digits[i]);
			}
		}
		
		currentTime = new String();
	}
	
	public void run() {
		while(!killThread) {
			System.out.print("");
			if(running) {
				try{
					addSecond();
					showTime();
					Thread.sleep(500);
				}catch(InterruptedException ex) {
					
				}
			}
		}
	}
	
	public void killThread() {
		killThread = true;
	}
	
	public void toggleRunning() {
		running = !running;
	}
	
	private void addSecond() {
		seconds++;
		if(seconds == 60) {
			seconds = 0;
			minutes++;
		}
		if(minutes == 60) {
			minutes = 0;
			hours++;
		}
		
		generateCurrentTimeString();
	}
	
	private void generateCurrentTimeString() {
		String secondsStr = new String();
		if(seconds < 10) {
			secondsStr = "0" + String.valueOf(seconds);
		}else {
			secondsStr = String.valueOf(seconds);
		}
		String minutesStr = new String();
		if(minutes < 10) {
			minutesStr = "0" + String.valueOf(minutes);
		}else {
			minutesStr = String.valueOf(minutes);
		}
		String hoursStr = new String();
		if(hours < 10) {
			hoursStr = "0" + String.valueOf(hours);
		}else {
			hoursStr = String.valueOf(hours);
		}
		currentTime = hoursStr + ":" + minutesStr + ":" + secondsStr;
	}
	
	private void showTime() {
		for(int i = 0; i < currentTime.length(); i++) {
			if(i != 2 && i != 5) {
				digits[i].setIcon(icons[Character.getNumericValue(currentTime.charAt(i))]);
			}
		}
	}
	
	public void reset() {
		for(int i = 0; i < digits.length; i++) {
			if(i != 2 && i != 5) {
				digits[i].setIcon(icons[0]);
			}
			hours = 0;
			minutes = 0;
			seconds = 0;
			generateCurrentTimeString();
			showTime();
			running = false;
		}
	}
}
