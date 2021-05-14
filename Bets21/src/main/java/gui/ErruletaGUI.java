package gui;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Timer;
import javax.swing.border.EmptyBorder;

import businessLogic.BLFacade;
import domain.Bezero;

import javax.swing.JLabel;
import java.awt.Font;
import java.awt.Color;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.util.Random;
import java.util.ResourceBundle;
import java.awt.event.ActionEvent;
import javax.swing.SwingConstants;
import javax.swing.JTextField;
import javax.swing.JComboBox;
import javax.swing.JTextArea;

public class ErruletaGUI extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JButton btnPlay;
	private JLabel[] numbersArray;
	private Timer rollerTimer;

	private JLabel lblTitle;
	private JLabel lblSelect;
	private JLabel lblBalanceText;
	private JLabel lblBalance;
	private JLabel lblAmount;
	private JLabel lblError;
	private JLabel lblMessage;
	private JTextField textFieldAmount;
	private JButton btnBack;
	private JButton btnLogOut;
	
	private JFrame frame;
	private MainGUI main;
	private JFrame atzera;
	private Bezero b;
	
	private JComboBox<String> comboNumbers;
	private DefaultComboBoxModel<String> nums = new DefaultComboBoxModel<String>();
	
	private int rollerCount=0;
	private int green = 1;
	private int userEmaitza;
	private int waitSeconds;
	private Random random;
	private JLabel lbl0;
	private JLabel lbl1;
	private JLabel lbl2;
	private JLabel lbl7;
	private JLabel lbl9;
	private JLabel lbl8;
	private JLabel lbl6;
	private JLabel lbl5;
	private JLabel lbl4;
	private JLabel lbl3;
	private JLabel lblRuleta;
	private JTextArea textArea;
	

	public ErruletaGUI(MainGUI main, JFrame atzera, Bezero b) {
		this.frame = this;
		this.main=main;
		this.atzera=atzera;
		this.b=b;
		BLFacade facade = MainGUI.getBusinessLogic();
		this.setTitle(ResourceBundle.getBundle("Etiquetas").getString("Roulette"));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 605, 602);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		lblTitle = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("Roulette"));
		lblTitle.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblTitle.setHorizontalAlignment(SwingConstants.CENTER);
		lblTitle.setBounds(152, 23, 277, 25);
		contentPane.add(lblTitle);
		
		textArea = new JTextArea();
		textArea.setText(ResourceBundle.getBundle("Etiquetas").getString("RouletteExp"));
		textArea.setEditable(false);
		textArea.setBounds(40, 50, 486, 48);
		contentPane.add(textArea);
		
		lblBalanceText = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("Saldo"));
		lblBalanceText.setBounds(232, 108, 55, 13);
		contentPane.add(lblBalanceText);
		
		lblBalance = new JLabel();
		lblBalance.setHorizontalAlignment(SwingConstants.CENTER);
		lblBalance.setText(String.valueOf(facade.getDirua(b)));
		lblBalance.setBounds(308, 108, 39, 13);
		contentPane.add(lblBalance);
		
		lblSelect = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("Choose"));
		lblSelect.setHorizontalAlignment(SwingConstants.CENTER);
		lblSelect.setBounds(152, 131, 277, 13);
		contentPane.add(lblSelect);
		
		lblAmount = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("Amount"));
		lblAmount.setBounds(196, 191, 72, 13);
		contentPane.add(lblAmount);
		
		textFieldAmount = new JTextField();
		textFieldAmount.setBounds(278, 188, 96, 19);
		contentPane.add(textFieldAmount);
		textFieldAmount.setColumns(10);
		
		numbersArray = new JLabel[10];
		
		btnPlay = new JButton(ResourceBundle.getBundle("Etiquetas").getString("Jokatu"));
		btnPlay.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				lblError.setText("");
				lblMessage.setText("");
				for (int i = 0; i<10; i++) {
					numbersArray[i].setForeground(Color.BLACK);
				}
				
				if (textFieldAmount.getText().equals("")) {
					lblError.setText(ResourceBundle.getBundle("Etiquetas").getString("Hutsa"));
					return;
				} 
				if (!facade.validAmount(textFieldAmount.getText())) {
					lblError.setText(ResourceBundle.getBundle("Etiquetas").getString("AmountError"));
					return;
				}
				if (Double.valueOf(textFieldAmount.getText()) <= 0) {
					lblError.setText(ResourceBundle.getBundle("Etiquetas").getString("Negatiboa"));
					return;
				} 
				if (facade.getDirua(b)<Float.parseFloat(textFieldAmount.getText())){
					lblError.setText(ResourceBundle.getBundle("Etiquetas").getString("LessThanBalance"));
					return;
				}
				
				
				userEmaitza=comboNumbers.getSelectedIndex();
				rollerCount=0;
				random = new Random();
				green=-1;	
				waitSeconds=random.nextInt(9)+30;
				rollerTimer.start();
			}
		});
		btnPlay.setBounds(248, 237, 85, 21);
		contentPane.add(btnPlay);
		
		rollerTimer = new Timer(100, new ActionListener() {
	        @Override
	        public void actionPerformed(ActionEvent e) {
	        	if (rollerCount==waitSeconds-1) { //hobe ikusteko
	        		rollerCount++;
	        		return;
	        	}
	        	if (rollerCount<waitSeconds) {
	        		green++;
	        		green = green % 10;
	        		for (int i=0; i<10; i++) {
	        			if (i==green)
	        				numbersArray[i].setForeground(Color.GREEN);
	        			else
	        				numbersArray[i].setForeground(Color.BLACK);
	        		}
	        	}
	            if(rollerCount==waitSeconds) {
	                rollerTimer.stop();
	                if (userEmaitza==green) {
	                	lblMessage.setText(ResourceBundle.getBundle("Etiquetas").getString("Congratulations"));
						String izenburua = ResourceBundle.getBundle("Etiquetas").getString("MIrabazi");
						facade.minijokoaIrabazi(Float.parseFloat(textFieldAmount.getText()),izenburua,b, 7);
						textFieldAmount.setText("");
	                } else {
	                	lblMessage.setText(ResourceBundle.getBundle("Etiquetas").getString("Sorry"));
	                	String izenburua = ResourceBundle.getBundle("Etiquetas").getString("MGaldu");
	                	facade.minijokoaGaldu(Float.parseFloat(textFieldAmount.getText()),izenburua,b);
	                	textFieldAmount.setText("");
	                }
	                lblBalance.setText(String.valueOf(facade.getDirua(b)));
				} else {
					rollerCount++;
				}
	        }
	    });
		
		lblMessage = new JLabel();
		lblMessage.setHorizontalAlignment(SwingConstants.CENTER);
		lblMessage.setBounds(152, 489, 277, 13);
		contentPane.add(lblMessage);
		
		btnBack = new JButton(ResourceBundle.getBundle("Etiquetas").getString("Back"));
		btnBack.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frame.setVisible(false);
				atzera.setVisible(true);
			}
		});
		btnBack.setBounds(464, 522, 115, 21);
		contentPane.add(btnBack);
		
		btnLogOut = new JButton(ResourceBundle.getBundle("Etiquetas").getString("LogOut"));
		btnLogOut.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				frame.setVisible(false);
				main.setVisible(true);
			}
		});
		btnLogOut.setBounds(464, 10, 115, 21);
		contentPane.add(btnLogOut);
		
		lblError = new JLabel();
		lblError.setForeground(new Color(255, 0, 0));
		lblError.setHorizontalAlignment(SwingConstants.CENTER);
		lblError.setBounds(106, 214, 374, 13);
		contentPane.add(lblError);
		
		lbl0 = new JLabel("0");
		lbl0.setFont(new Font("Tahoma", Font.BOLD, 14));
		lbl0.setHorizontalAlignment(SwingConstants.CENTER);
		lbl0.setBounds(300, 272, 25, 21);
		numbersArray[0]=lbl0;
		contentPane.add(lbl0);
		
		lbl1 = new JLabel("1");
		lbl1.setFont(new Font("Tahoma", Font.BOLD, 14));
		lbl1.setHorizontalAlignment(SwingConstants.CENTER);
		lbl1.setBounds(343, 307, 45, 13);
		numbersArray[1]=lbl1;
		contentPane.add(lbl1);
		
		lbl2 = new JLabel("2");
		lbl2.setFont(new Font("Tahoma", Font.BOLD, 14));
		lbl2.setHorizontalAlignment(SwingConstants.CENTER);
		lbl2.setBounds(367, 356, 45, 13);
		numbersArray[2]=lbl2;
		contentPane.add(lbl2);
		
		lbl3 = new JLabel("3");
		lbl3.setFont(new Font("Tahoma", Font.BOLD, 14));
		lbl3.setHorizontalAlignment(SwingConstants.CENTER);
		lbl3.setBounds(341, 407, 45, 13);
		numbersArray[3]=lbl3;
		contentPane.add(lbl3);
		
		lbl4 = new JLabel("4");
		lbl4.setFont(new Font("Tahoma", Font.BOLD, 14));
		lbl4.setHorizontalAlignment(SwingConstants.CENTER);
		lbl4.setBounds(300, 441, 25, 13);
		numbersArray[4]=lbl4;
		contentPane.add(lbl4);
		
		lbl5 = new JLabel("5");
		lbl5.setFont(new Font("Tahoma", Font.BOLD, 14));
		lbl5.setHorizontalAlignment(SwingConstants.CENTER);
		lbl5.setBounds(240, 441, 45, 13);
		numbersArray[5]=lbl5;
		contentPane.add(lbl5);
		
		lbl6 = new JLabel("6");
		lbl6.setFont(new Font("Tahoma", Font.BOLD, 14));
		lbl6.setHorizontalAlignment(SwingConstants.CENTER);
		lbl6.setBounds(194, 407, 45, 13);
		numbersArray[6]=lbl6;
		contentPane.add(lbl6);
		
		lbl7 = new JLabel("7");
		lbl7.setFont(new Font("Tahoma", Font.BOLD, 14));
		lbl7.setHorizontalAlignment(SwingConstants.CENTER);
		lbl7.setBounds(166, 356, 45, 13);
		numbersArray[7]=lbl7;
		contentPane.add(lbl7);
		
		lbl8 = new JLabel("8");
		lbl8.setFont(new Font("Tahoma", Font.BOLD, 14));
		lbl8.setHorizontalAlignment(SwingConstants.CENTER);
		lbl8.setBounds(194, 307, 45, 13);
		numbersArray[8]=lbl8;
		contentPane.add(lbl8);
		
		lbl9 = new JLabel("9");
		lbl9.setFont(new Font("Tahoma", Font.BOLD, 14));
		lbl9.setHorizontalAlignment(SwingConstants.CENTER);
		lbl9.setBounds(240, 276, 45, 13);
		numbersArray[9]=lbl9;
		contentPane.add(lbl9);
		
		lblRuleta = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("Roulette"));
		lblRuleta.setHorizontalAlignment(SwingConstants.CENTER);
		lblRuleta.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblRuleta.setBounds(232, 358, 115, 13);
		contentPane.add(lblRuleta);
		
		comboNumbers = new JComboBox<String>();
		comboNumbers.setBounds(232, 157, 115, 21);
		comboNumbers.setModel(nums);
		contentPane.add(comboNumbers);
		
		nums.addElement("0");
		nums.addElement("1");
		nums.addElement("2");
		nums.addElement("3");
		nums.addElement("4");
		nums.addElement("5");
		nums.addElement("6");
		nums.addElement("7");
		nums.addElement("8");
		nums.addElement("9");
		comboNumbers.setSelectedIndex(0);

	}
}
