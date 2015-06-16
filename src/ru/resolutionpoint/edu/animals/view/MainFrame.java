package ru.resolutionpoint.edu.animals.view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import ru.resolutionpoint.edu.animals.model.Environment;

/**
 * Class <code>MainFrame</code> is main application frame
 */
public class MainFrame extends JFrame {

	private boolean started = false;
	private boolean paused = false;

    /**
     * Constructs new frame
     *
     * @param environment environment
     */
    public MainFrame(final Environment environment) {
		super("Animal World");
		setLayout(new GridBagLayout());
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		GridBagConstraints constraints = new GridBagConstraints();
		constraints.gridx = 0;
		constraints.gridy = 0;
		constraints.weightx = 0.1;
		constraints.weighty = 0.1;
		constraints.fill = GridBagConstraints.BOTH;
        add(new JScrollPane(new EntitiesPanel(environment)), constraints);
		constraints.gridy = 1;
		constraints.weightx = 0.0;
		constraints.weighty = 0.0;
		constraints.fill = GridBagConstraints.NONE;
		constraints.anchor = GridBagConstraints.SOUTH;
		add(getControlPanel(environment), constraints);
		pack();
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		Dimension frameSize = getSize();
		setLocation((screenSize.width - frameSize.width) >> 1,
				(screenSize.height - frameSize.height) >> 1);
		setVisible(true);
	}

    private JPanel getControlPanel(final Environment environment) {
        JPanel panel = new JPanel(new BorderLayout());
        final JButton startButton = new JButton("Start");
        final JButton pauseButton = new JButton("Pause");
        startButton.addActionListener(new ActionListener() {
            @Override
			public void actionPerformed(ActionEvent e) {
                started = !started;
                if (started) {
                    try {
                        environment.start();
                    } catch (InterruptedException e1) {
                        e1.printStackTrace();
                    }
                    startButton.setText("Stop");
                } else {
                    environment.stop();
                    startButton.setText("Start");
                    paused = false;
                    pauseButton.setText("Pause");
                }
                pauseButton.setEnabled(started);
            }
        });
        panel.add(startButton, BorderLayout.LINE_START);
        pauseButton.addActionListener(new ActionListener() {
            @Override
			public void actionPerformed(ActionEvent e) {
                paused = !paused;
                if (paused) {
                    environment.stop();
                    pauseButton.setText("Resume");
                } else {
                    try {
                        environment.start();
                    } catch (InterruptedException e1) {
                        e1.printStackTrace();
                    }
                    pauseButton.setText("Pause");
                }
            }
        });
        panel.add(new JLabel(" "));
        pauseButton.setEnabled(false);
        panel.add(pauseButton, BorderLayout.LINE_END);
        return panel;
    }
}
