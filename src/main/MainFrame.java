package main;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.Border;

public class MainFrame {
	
	JFrame frame;
	JPanel mainPanel;
	ChronoPanel chronoPanel;
	JPanel buttonsPanel;
	
	JButton startStopButton, resetButton;
	
	Thread chronoThread;
	
	public MainFrame() {
		frame = new JFrame("Prueba del componente cronómetro");
		frame.setLocation(250, 250);
		frame.setLayout(new BoxLayout(frame.getContentPane(), 0));
		frame.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				chronoPanel.killThread();
				super.windowClosing(e);
			}
		});
		
		initChronoPanel();
		initButtonsPanel();
		
		mainPanel = new JPanel();
		Border border = BorderFactory.createLineBorder(Color.black);
		mainPanel.setBorder(border);
		mainPanel.setLayout(new BoxLayout(mainPanel, 1));
		
		mainPanel.add(chronoPanel);
		mainPanel.add(buttonsPanel);
		
		frame.add(Box.createHorizontalGlue());
		frame.add(Box.createVerticalGlue());
		frame.add(mainPanel);
		frame.add(Box.createHorizontalGlue());
		frame.add(Box.createVerticalGlue());
		frame.pack();
		frame.setVisible(true);
	}
	
	private void initChronoPanel() {
		chronoPanel = new ChronoPanel();
		chronoThread = new Thread(chronoPanel);
		chronoThread.start();
	}
	
	private void initButtonsPanel() {
		buttonsPanel = new JPanel();
		
		startStopButton = new JButton("Arrancar/Parar");
		startStopButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				startStopChrono();
			}
		});
		
		resetButton = new JButton("Reiniciar");
		resetButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				resetChrono();
			}
		});
		
		buttonsPanel.add(startStopButton);
		buttonsPanel.add(resetButton);
	}
	
	private void startStopChrono() {
		chronoPanel.toggleRunning();
	}
	
	private void resetChrono() {
		chronoPanel.reset();
	}
}
