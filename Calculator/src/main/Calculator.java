package main;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;

	/**
	 * 
	 * @author Mitchell Zinck
	 *
	 */

public class Calculator extends JFrame {
	
	/*
	 * Initializing the variables.
	 */
	
	private JTextField output;
	private int integer = 0;
	private String equals;
	private double endValue = 0;
	private boolean afterEqual = false;
	private String[] check = new String[100];
	private JButton button;
	private List<String> value = new ArrayList<String>();
	private List<Double> valueInt = new ArrayList<Double>();
	private List<String> action = new ArrayList<String>();
	private String numbers[] = {"7", "8", "9", "4", "5", "6", "1", "2", "3", "0"};
	private String actions[] = {"C", "<--", "+", "-", "*", "/", ".", "="};
	private JPanel actionPanel = new JPanel();
	private JPanel numberPanel = new JPanel();
	private JPanel panel = new JPanel();
	
	/*
	 * Set up the JFrame.
	 */
	
	public Calculator() {
		super("Calculator");
		setLayout(new FlowLayout());
		output = new JTextField("0", 60);
		output.setEditable(false);
		
		NumberClass number = new NumberClass();
		ActionClass actionClass = new ActionClass();
		actionPanel.setLayout(new GridLayout(4, 4, 6 ,6));
		numberPanel.setLayout(new GridLayout(4, 4, 6, 6));

		for(int i = 0; i < numbers.length; i++){
			button = new JButton(numbers[i]);
			button.addActionListener(number);
			numberPanel.add(button);
		}
		
		for(int i = 0; i < actions.length; i++){
			button = new JButton(actions[i]);
			button.addActionListener(actionClass);
			actionPanel.add(button);
		}
	
		panel.setLayout(new BorderLayout(4, 4));
		panel.add(output, BorderLayout.NORTH);
		panel.add(actionPanel, BorderLayout.EAST);
		panel.add(numberPanel, BorderLayout.WEST);
		this.setContentPane(panel);
		this.pack();
		this.setTitle("Calculator");	
		
	}	
	
	/*
	 * The Number listener
	 */
	
	private class NumberClass implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			String field = output.getText();
			String s = e.getActionCommand();
			String total = (field + s);
			if(field.equals("0")) {
				output.setText(s);
			}
			else
			{
				output.setText(total);
			}
		}
	}
	
	/*
	 * The Action listener
	 */
	
	private class ActionClass implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			action.add(integer, e.getActionCommand());
			if(e.getActionCommand().equals("C")) {
				output.setText("0");
				action.clear();
				value.clear();
				valueInt.clear();
				endValue = 0;
			    integer = 0;	
			    afterEqual = false;
			}
			else if(action.get(integer).equals("=")) {
				getValue();
				calculate();
				action.clear();
				value.clear();
				valueInt.clear();
				endValue = 0;
			    integer = 0;
			}
			else if(action.get(integer).equals(".")) {
				String field = output.getText();
				output.setText(field+".");
			}
			else if(action.get(integer).equals("<--")) {
				String field = output.getText();
				if(field.contains(".")) {
					String[] str = field.split(".");
					String str2 = str[str[0].length()-1];
					output.setText(str2);
				}else {
					String str2 = field.substring(0, field.length() - 1);
					output.setText(str2);
				}
			}
			else {
				getValue();
				output.setText("0");
				integer++;
			}
		}
	}
	
	/*
	 * Gets the textfield value
	 */
	
	public void getValue() {
		value.add(integer, output.getText());
	}
	
	/*
	 * Calculates the problem after being called by Calculate
	 */
	
	public void calculateAction(String action, Double num) {
		char i = action.charAt(0);
		switch (i) {
			
			case '+':
				endValue = endValue + num;
			break;
			
			case '-':
				endValue = endValue - num;
			break;
			
			case '/':
				endValue = endValue / num;
			break;
			
			case '*':
				endValue = endValue * num;
			break;
		}
	}
	
	/*
	 * Gets the actions from the lists and sorts them
	 */
	
	public void calculate() {		
		for (String myInt : value) 
        { 
          valueInt.add(Double.valueOf(myInt)); 
        }
		
		Double[] newValue = valueInt.toArray(new Double[valueInt.size()]);		
		System.out.println("\n");
		for(int i = 0; i < action.size(); i++) {
		//	System.out.println(newValue[i]);
		}
		
		for(int i = 0; i < action.size(); i++) {
			int num = i + 1;
			if(action.get(i).equals("*")) {
				if(check[i] == null) {
					endValue = newValue[i] * newValue[i+1] + endValue;
					check[num] = "*";
				}
				else {
					calculateAction(action.get(i), newValue[num]);
					check[num] = "*";
				}
			}
			else if (action.get(i).equals("/")) {
				if(check[i] == null) {
					endValue = newValue[i] / newValue[i+1] + endValue;
					check[num] = "/";
				}
				else {
					calculateAction(action.get(i), newValue[num]);
					check[num] = "/";
				}
			}
			
		}
		
		for(int i = 0; i < action.size(); i++) {
			int num = i + 1;
			if(action.get(i).equals("+")) {
				if(check[i] == null){
					endValue = newValue[num] + newValue[i] + endValue;
					check[num] = "+";
				}
				else {
					calculateAction(action.get(i), newValue[num]);
					check[num] = "+";
				}
			}
			else if (action.get(i).equals("-")) {
				if(check[i] == null) {
					endValue = newValue[i] - newValue[num] + endValue;
					check[num] = "-";
				}
				else {
					calculateAction(action.get(i), newValue[num]);
					check[num] = "-";
				}
			}
		}
		
		String total = Double.toString(endValue);
		output.setText(total);
	}
	
	/*
	 * Executes the JFrame
	 */
	
	public static void main (String[] args) {
		
		Calculator c = new Calculator();
		c.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		c.setSize(270, 200);
		c.setVisible(true);
		c.setResizable(false);
		
	}
}
	