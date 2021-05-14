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

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.util.Random;
import java.util.ResourceBundle;
import java.awt.event.ActionEvent;
import javax.swing.SwingConstants;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.JTextArea;

public class AurpegiaGurutzeaGUI extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JLabel lblCara;
	private JLabel lblCruz;
	private JButton btnPlay;
	private JLabel[] caracruz;
	private Timer rollerTimer;

	private JLabel lblTitle;
	private JTextArea textArea;
	private JLabel lblSelect;
	private JRadioButton rdbtnHeads;
	private JRadioButton rdbtnTails;
	private ButtonGroup hotGroup;
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
	
	
	private int rollerCount=0;
	private int green = 1;
	private int userEmaitza;
	private int waitSeconds;
	private Random random;
	

	public AurpegiaGurutzeaGUI(MainGUI main, JFrame atzera, Bezero b) {
		this.frame = this;
		this.main=main;
		this.atzera=atzera;
		this.b=b;
		BLFacade facade = MainGUI.getBusinessLogic();
		this.setTitle(ResourceBundle.getBundle("Etiquetas").getString("HeadsOrTail"));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 603, 400);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		lblTitle = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("HeadsOrTail"));
		lblTitle.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblTitle.setHorizontalAlignment(SwingConstants.CENTER);
		lblTitle.setBounds(152, 23, 277, 25);
		contentPane.add(lblTitle);
		
		textArea = new JTextArea();
		textArea.setEditable(false);
		textArea.setText(ResourceBundle.getBundle("Etiquetas").getString("HeadsOrTailsExp"));
		textArea.setBounds(35, 50, 516, 41);
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
		
		rdbtnHeads = new JRadioButton(ResourceBundle.getBundle("Etiquetas").getString("Heads"));
		rdbtnHeads.setBounds(202, 150, 85, 21);
		contentPane.add(rdbtnHeads);
		
		rdbtnTails = new JRadioButton(ResourceBundle.getBundle("Etiquetas").getString("Tails"));
		rdbtnTails.setBounds(308, 150, 86, 21);
		contentPane.add(rdbtnTails);
		
		hotGroup = new ButtonGroup();
		hotGroup.add(rdbtnHeads);
		hotGroup.add(rdbtnTails);
		
		lblAmount = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("Amount"));
		lblAmount.setBounds(196, 191, 72, 13);
		contentPane.add(lblAmount);
		
		textFieldAmount = new JTextField();
		textFieldAmount.setBounds(278, 188, 96, 19);
		contentPane.add(textFieldAmount);
		textFieldAmount.setColumns(10);
		
		lblCara = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("Heads"));
		lblCara.setHorizontalAlignment(SwingConstants.CENTER);
		lblCara.setFont(new Font("Tahoma", Font.BOLD, 16));
		lblCara.setBounds(196, 264, 75, 32);
		contentPane.add(lblCara);
		
		lblCruz = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("Tails"));
		lblCruz.setHorizontalAlignment(SwingConstants.CENTER);
		lblCruz.setFont(new Font("Tahoma", Font.BOLD, 16));
		lblCruz.setBounds(308, 268, 86, 25);
		contentPane.add(lblCruz);
		
		caracruz = new JLabel[2];
		caracruz[0]=lblCara;
		caracruz[1]=lblCruz;
		
		btnPlay = new JButton(ResourceBundle.getBundle("Etiquetas").getString("Jokatu"));
		btnPlay.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				lblError.setText("");
				lblMessage.setText("");
				caracruz[0].setForeground(Color.BLACK);
				caracruz[1].setForeground(Color.BLACK);
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
				if (!rdbtnHeads.isSelected()&&!rdbtnTails.isSelected()) {
					lblError.setText(ResourceBundle.getBundle("Etiquetas").getString("NoChosenError"));
					return;
				}
				if (rdbtnHeads.isSelected()) {
					userEmaitza=0;
				}else {
					userEmaitza=1;
				}
				rollerCount=0;
				random = new Random();
				green=random.nextInt(2);	
				waitSeconds=random.nextInt(2)+10;
				rollerTimer.start();
			}
		});
		btnPlay.setBounds(248, 237, 85, 21);
		contentPane.add(btnPlay);
		
		rollerTimer = new Timer(200, new ActionListener() {
	        @Override
	        public void actionPerformed(ActionEvent e) {
					if (green==1) {
						caracruz[0].setForeground(Color.GREEN);
						caracruz[1].setForeground(Color.BLACK);
						green=0;
					} else {
						caracruz[1].setForeground(Color.GREEN);
						caracruz[0].setForeground(Color.BLACK);
						green=1;
					}
	            if(rollerCount==waitSeconds) {
	                rollerTimer.stop();
	                if (userEmaitza==green) {
	                	lblMessage.setText(ResourceBundle.getBundle("Etiquetas").getString("Congratulations"));
						String izenburua = ResourceBundle.getBundle("Etiquetas").getString("MIrabazi");
						facade.minijokoaIrabazi(Float.parseFloat(textFieldAmount.getText()),izenburua,b, 0.7);
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
		lblMessage.setBounds(152, 306, 277, 13);
		contentPane.add(lblMessage);
		
		btnBack = new JButton(ResourceBundle.getBundle("Etiquetas").getString("Back"));
		btnBack.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frame.setVisible(false);
				atzera.setVisible(true);
			}
		});
		btnBack.setBounds(474, 320, 105, 21);
		contentPane.add(btnBack);
		
		btnLogOut = new JButton(ResourceBundle.getBundle("Etiquetas").getString("LogOut"));
		btnLogOut.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				frame.setVisible(false);
				main.setVisible(true);
			}
		});
		btnLogOut.setBounds(474, 10, 105, 21);
		contentPane.add(btnLogOut);
		
		lblError = new JLabel();
		lblError.setForeground(new Color(255, 0, 0));
		lblError.setHorizontalAlignment(SwingConstants.CENTER);
		lblError.setBounds(106, 214, 374, 13);
		contentPane.add(lblError);
	}
}
