package tmcit.freedom.UI.ProblemEdit;

import java.awt.Color;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFormattedTextField;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.JTextField;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import tmcit.freedom.System.Problem;
import tmcit.freedom.Util.NumberFormatterFactory;

public class DataSliderPanel extends JPanel implements ChangeListener, ActionListener {

	private JTextField title;
	
	private Problem problem;

	private JFormattedTextField limIText, limLText, limXText;
	private JSlider limISlider, limLSlider, limXSlider;

	private int spaceNum;
	private int limI, limL, limX;
	private int sgNum;

	public DataSliderPanel(){
		this.setLayout(null);
		this.setBounds(730, 365, 158, 360);
		this.setBackground(Color.GRAY);

		this.setTextField();
		this.setSlider();

		this.setVisible(true);
	}

	public void initilize(){
		this.spaceNum = 0;
		this.limI = 0;
		this.limL = 0;
		this.limX = 0;
		this.sgNum = 0;
		this.setDataToProblem();
		this.setDataToSlider();
		this.setDataToText();
	}

	
	@Override
	public void stateChanged(ChangeEvent e) {
		Object o = e.getSource();
		if(o instanceof JSlider){
			JSlider js = (JSlider) o;
			this.setLimitorValue(js);
		}
	}
	
	private void setLimitorValue(JSlider js){
		if(js.equals(limISlider)){
			int n = js.getValue();
			this.limIText.setText(String.valueOf(n));
			if(js.getValueIsAdjusting() == false){
				this.limI = n;
			}
		}else if(js.equals(limLSlider)){
			int n = js.getValue();
			this.limLText.setText(String.valueOf(n));
			if(js.getValueIsAdjusting() == false){
				this.limL = n;
			}
		}else if(js.equals(limXSlider)){
			int n = js.getValue();
			this.limXText.setText(String.valueOf(n));
			if(js.getValueIsAdjusting() == false){
				this.limX = n;
			}
		}

		if(js.getValueIsAdjusting() == false){
			this.setDataToProblem();
			this.setDataToText();
		}

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		Object o = e.getSource();
		if(o instanceof JFormattedTextField){
			JFormattedTextField jftf = (JFormattedTextField) o;
			this.setTextValue(jftf);
		}
	}
	
	private void setTextValue(JFormattedTextField jftf){
		String value = jftf.getText();
		if(value.isEmpty())return;
		int n = Integer.parseInt(value);
		if(n < 0)n = 0;
		else if(1000 < n)n = 1000;

		if(jftf.equals(this.limIText)){
			this.limI = n;

		}else if(jftf.equals(this.limLText)){
			this.limL = n;
			
		}else if(jftf.equals(this.limXText)){
			this.limX = n;
			
		}

		this.setDataToProblem();
		this.setDataToSlider();

		this.repaint();
	}
	
	private void setDataToText(){
		this.limIText.setText(String.valueOf(limI));
		this.limLText.setText(String.valueOf(limL));
		this.limXText.setText(String.valueOf(limX));
	}

	private void setDataToSlider(){
		this.limISlider.setValue(limI);
		this.limLSlider.setValue(limL);
		this.limXSlider.setValue(limX);
		this.repaint();
	}
	
	private void setDataToProblem(){
		this.problem.setLimI(limI);
		this.problem.setLimL(limL);
		this.problem.setLimX(limX);
	}

	public void setProblem(){
		if(this.problem == null)return;
		this.setProblem(this.problem);
	}

	public void setProblem(Problem problem){
		this.problem = problem;
		this.setProblemToData(problem);
		this.setProblemToText(problem);
		this.setProblemToSlider(problem);
	}

	private void setProblemToData(Problem problem){
		this.spaceNum = problem.getSpaceNum();
		this.limI = problem.getLimI();
		this.limL = problem.getLimL();
		this.limX = problem.getLimX();
		this.sgNum = problem.getSGNum();
	}

	private void setProblemToText(Problem problem){
		this.limIText.setText(String.valueOf(problem.getLimI()));
		this.limLText.setText(String.valueOf(problem.getLimL()));
		this.limXText.setText(String.valueOf(problem.getLimX()));
	}
	
	private void setProblemToSlider(Problem problem){
		this.limISlider.setValue(problem.getLimI());
		this.limLSlider.setValue(problem.getLimL());
		this.limXSlider.setValue(problem.getLimX());
		this.repaint();
	}


	private void setTextField() {
		this.title = new JTextField("Parameters");
		this.title.setLayout(null);
		this.title.setBounds(10, 10, 138, 30);
		this.title.setBorder(null);
		this.title.setMargin(new Insets(10, 10, 10, 10));
		this.title.setEnabled(false);
		this.title.setDisabledTextColor(Color.BLACK);
		this.title.setBackground(Color.GRAY);
		this.add(this.title);

	}

	private void setSlider(){
		this.limISlider = new JSlider(JSlider.VERTICAL, 0, 1000, 0);
		this.limLSlider = new JSlider(JSlider.VERTICAL, 0, 1000, 0);
		this.limXSlider = new JSlider(JSlider.VERTICAL, 0, 1000, 0);

		this.limISlider.setBounds(10, 40, 40, 280);
		this.limLSlider.setBounds(60, 40, 40, 280);
		this.limXSlider.setBounds(110, 40,40, 280);

		this.limISlider.setBackground(Color.LIGHT_GRAY);
		this.limLSlider.setBackground(Color.LIGHT_GRAY);
		this.limXSlider.setBackground(Color.LIGHT_GRAY);

		this.limISlider.addChangeListener(this);
		this.limLSlider.addChangeListener(this);
		this.limXSlider.addChangeListener(this);

		this.limISlider.setVisible(true);
		this.limLSlider.setVisible(true);
		this.limXSlider.setVisible(true);

		this.add(this.limISlider);
		this.add(this.limLSlider);
		this.add(this.limXSlider);
		
		this.limIText = new JFormattedTextField(limI);
		this.limLText = new JFormattedTextField(limL);
		this.limXText = new JFormattedTextField(limX);

		this.limIText.setLayout(null);
		this.limLText.setLayout(null);
		this.limXText.setLayout(null);
		
		this.limIText.setBounds(10, 330, 40, 20);
		this.limLText.setBounds(60, 330, 40, 20);
		this.limXText.setBounds(110, 330, 40, 20);
				
		this.limIText.setFormatterFactory(new NumberFormatterFactory());
		this.limLText.setFormatterFactory(new NumberFormatterFactory());
		this.limXText.setFormatterFactory(new NumberFormatterFactory());
		
		this.limIText.addActionListener(this);
		this.limLText.addActionListener(this);
		this.limXText.addActionListener(this);

		this.limIText.setHorizontalAlignment(JTextField.RIGHT);
		this.limLText.setHorizontalAlignment(JTextField.RIGHT);
		this.limXText.setHorizontalAlignment(JTextField.RIGHT);
		
		this.limIText.setVisible(true);
		this.limLText.setVisible(true);
		this.limXText.setVisible(true);
		
		this.add(this.limIText);
		this.add(this.limLText);
		this.add(this.limXText);

	}
	
	public int[] getParameter(){
		int[] num = new int[5];
		
		num[0] = this.limI;
		num[1] = this.limL;
		num[2] = this.limX;
		num[3] = this.spaceNum;
		num[4] = this.sgNum;
		
		return num;
	}
	
	public int getLimI(){
		return this.limI;
	}
	
	public int getLimL(){
		return this.limL;
	}
	
	public int getLimX(){
		return this.limX;
	}
	
	public int getSpaceNum(){
		return this.spaceNum;
	}
	
	public int getSGNum(){
		return this.sgNum;
	}


}

