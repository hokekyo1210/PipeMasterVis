package tmcit.freedom.UI.ProblemEdit;

import java.awt.Color;
import java.awt.Insets;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFormattedTextField;
import javax.swing.JPanel;
import javax.swing.JTextField;

import tmcit.freedom.Util.NumberFormatterFactory;

public class GenerateSet extends JPanel {
	private Color back;
	
	private JButton generate;
	
	private int count;
	private ArrayList<JFormattedTextField> setList;
	

	public GenerateSet(){
		this.setLayout(null);
		
		this.setBackground(back = Color.GRAY);
		this.setBounds(730, 210, 158, 151);
		this.setList = new ArrayList<JFormattedTextField>();

		this.count = 0;
		this.setTextBox();
		
		this.generate = new JButton("Generate");
		this.generate.setBounds(30, count*50 + 30, 98, 18);
		this.generate.setLayout(null);
		this.generate.setVisible(true);
		this.add(this.generate);

		this.setVisible(true);
	}
	
	private void setTextBox(){
		this.addJTF("N:(9 < N < 31)", count++);
		this.addJTF("SEED:(SEED > 0)", count++);
	}
	
	private void addJTF(String name, int count){
		JFormattedTextField ue = new JFormattedTextField(name);
		ue.setLayout(null);
		ue.setBounds(5, count*50 + 30, 148, 20);
		ue.setVisible(true);
		ue.setBorder(null);
		ue.setBackground(back);
		ue.setMargin(new Insets(10, 10, 10, 10));
		ue.setEnabled(false);
		ue.setDisabledTextColor(Color.BLACK);
		this.add(ue);
		this.setList.add(ue);

		JFormattedTextField shita = new JFormattedTextField();
		shita.setLayout(null);
		shita.setBounds(5, count*50 + 50, 148, 20);
		shita.setVisible(true);
		shita.setFormatterFactory(new NumberFormatterFactory());
		shita.setHorizontalAlignment(JTextField.RIGHT);

		this.add(shita);
		this.setList.add(shita);
	}
	
	public JButton getButton(){
		return this.generate;
	}
	
	public int getN(){
		JFormattedTextField jftf = setList.get(1);
		String str = jftf.getText();
		if(str.isEmpty()){
			jftf.setText("10");
			return 10;
		}
		int n = Integer.parseInt(str);
		if(n < 10){
			jftf.setText("10");
			return 10;
		}
		if(30 < n){
			jftf.setText("30");
			return 30;
		}
		return n;
	}

	public long getSeed(){
		JFormattedTextField jftf = setList.get(3);
		String str = jftf.getText();
		if(str.isEmpty()){
			jftf.setText("1");
			return 1;
		}
		long seed = Long.parseLong(str);
		if(seed <= 0){
			jftf.setText("1");
			return 1;
		}
		return seed;
	}
}
