import java.awt.*;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.WindowEvent;
import java.sql.*;
import java.util.Arrays;



public class Register extends JFrame {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    private JPanel contentPane;
    private JTextField usernameText;
    private JPasswordField passwordText;
    private JPasswordField confirmpasswordText;
    private JLabel usernameErorr;
    private JLabel passwordErorr;
    private JLabel confirmpasswordError;
    private static Register registerFrame;
    private JLabel userExists;
    private JLabel label;

    private Connection conn;
    private Statement st;
    private ResultSet rs;
    private JLabel lblInfo;
    /**
     * Launch the application.
     */
     public static void Invoke() {
	EventQueue.invokeLater(new Runnable() {
	    public void run() {
		try {
		    registerFrame = new Register();
		    registerFrame.setVisible(true);
		    windowCommand.setCenter(registerFrame);
		    
		} catch (Exception e) {
		    e.printStackTrace();
		}
	    }
	});
    }

    /**
     * Create the frame.
     */
    public Register() {
    	setResizable(false);
    	setTitle("Register");
    	setIconImage(Toolkit.getDefaultToolkit().getImage(Register.class.getResource("/javax/swing/plaf/metal/icons/ocean/question.png")));
	setBounds(100, 100, 450, 300);
	contentPane = new JPanel();
	contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
	setContentPane(contentPane);
	contentPane.setLayout(null);
	
	usernameText = new JTextField();
	usernameText.setBounds(52, 46, 156, 20);
	contentPane.add(usernameText);
	usernameText.setColumns(10);
	
	TextPrompt tp = new TextPrompt("Username", usernameText);
	tp.setForeground( Color.BLACK );
	tp.changeAlpha(0.5f);
	tp.changeStyle(Font.BOLD + Font.ITALIC);
	
	
	passwordText = new JPasswordField();
	passwordText.setBounds(52, 99, 156, 20);
	contentPane.add(passwordText);
	passwordText.setColumns(10);
	
	TextPrompt tp1 = new TextPrompt("Password", passwordText);
	tp1.setForeground( Color.BLACK );
	tp1.changeAlpha(0.5f);
	tp1.changeStyle(Font.BOLD + Font.ITALIC);
	
	confirmpasswordText = new JPasswordField();
	confirmpasswordText.setBounds(52, 153, 156, 20);
	contentPane.add(confirmpasswordText);
	confirmpasswordText.setColumns(10);
	
	TextPrompt tp2 = new TextPrompt("Confirm password", confirmpasswordText);
	tp2.setForeground( Color.BLACK );
	tp2.changeAlpha(0.5f);
	tp2.changeStyle(Font.BOLD + Font.ITALIC);
	
	JButton btnRegister = new JButton("REGISTER");
	btnRegister.addActionListener(new ActionListener() {
		public void actionPerformed(ActionEvent arg0) {
		    if(checkIsValid()){
			lblInfo.setOpaque(true);
			lblInfo.setText("Checking...");
			userExists.setOpaque(false);
			userExists.setText("");
			contentPane.paintImmediately(241, 237, 444, 272);
			if(userExists() == 0) {
			    try {	
				String driver = "sun.jdbc.odbc.JdbcOdbcDriver";
				Class.forName(driver);
				String db = "jdbc:odbc:MainDatabase";
				conn = DriverManager.getConnection(db);
				st = conn.createStatement();
				String createPassword = String.valueOf(passwordText.getPassword());
				String sql = "INSERT INTO users VALUES(\'" + usernameText.getText() +  "\',\'" + createPassword + "\')";
				st.executeUpdate(sql);
				conn.commit();
			    }
			    catch (Exception e) {
				e.printStackTrace();
			    }			  			    
			    lblInfo.setText("Registration succesful");   
			    usernameText.setText("");
			    passwordText.setText("");
			    confirmpasswordText.setText("");
			}
			else {
			    userExists.setText("Username is not available");
			    userExists.setOpaque(false);
			    lblInfo.setText("");
			    lblInfo.setOpaque(false);
			}
		    }
		}
	});
	btnRegister.setBounds(241, 203, 116, 23);
	contentPane.add(btnRegister);
	
	usernameErorr = new JLabel("",SwingConstants.CENTER);
	usernameErorr.setBackground(new Color(0, 0, 0));
	usernameErorr.setForeground(new Color(255, 0, 0));
	usernameErorr.setBounds(218, 49, 139, 14);
	contentPane.add(usernameErorr);
	
	passwordErorr = new JLabel("",SwingConstants.CENTER);
	passwordErorr.setBackground(new Color(0, 0, 0));
	passwordErorr.setForeground(new Color(255, 0, 0));
	passwordErorr.setBounds(218, 102, 139, 14);
	contentPane.add(passwordErorr);
	
	confirmpasswordError = new JLabel("",SwingConstants.CENTER);
	confirmpasswordError.setBackground(new Color(0, 0, 0));
	confirmpasswordError.setForeground(new Color(255, 0, 0));
	confirmpasswordError.setBounds(218, 156, 188, 14);
	contentPane.add(confirmpasswordError);
	
	userExists = new JLabel("",SwingConstants.CENTER);
	userExists.setForeground(new Color(255, 0, 0));
	userExists.setBackground(new Color(0, 0, 0));
	userExists.setBounds(241, 237, 193, 14);
	contentPane.add(userExists);
	
	lblInfo = new JLabel("", SwingConstants.CENTER);
	lblInfo.setForeground(Color.WHITE);
	lblInfo.setBackground(Color.BLACK);
	lblInfo.setBounds(280, 237, 164, 35);
	contentPane.add(lblInfo);
	
	label = new JLabel("");
	label.setIcon(new ImageIcon(Register.class.getResource("/Images/bgImageRegister.png")));
	label.setBounds(0, 0, 444, 272);
	contentPane.add(label);
	
    }
    
    public boolean checkIsValid() {
	if(usernameText.getText().equals("")) {
	    usernameErorr.setText("Required");
	    usernameErorr.setOpaque(true);
	    return false;
	}
	else {
	    usernameErorr.setText("");
	    usernameErorr.setOpaque(false);
	}
	if(String.valueOf(passwordText.getPassword()).equals("")) {
	    passwordErorr.setText("Required");
	    passwordErorr.setOpaque(true);
	    return false;
	}
	else {
	    passwordErorr.setText("");
	    passwordErorr.setOpaque(false);
	}
	if(String.valueOf(confirmpasswordText.getPassword()).equals("")) {
	    confirmpasswordError.setText("Confirm password");
	    confirmpasswordError.setOpaque(true);
	    return false;
	}
	else {
	    confirmpasswordError.setText("");
	    confirmpasswordError.setOpaque(false);
	}
	if(!Arrays.equals(passwordText.getPassword(), confirmpasswordText.getPassword())) {
	    confirmpasswordError.setText("Passwords doesnt match");
	    confirmpasswordError.setOpaque(true);
	    return false;
	}
	else {
	    confirmpasswordError.setText("");
	    confirmpasswordError.setOpaque(false);
	}
	return true;
    }
    private int userExists() {
	int userId = 0;
	try {
	    String driver = "sun.jdbc.odbc.JdbcOdbcDriver";
	    Class.forName(driver);
	    String db = "jdbc:odbc:MainDatabase";
	    conn = DriverManager.getConnection(db);
	    st = conn.createStatement();
	    String createPassword = String.valueOf(passwordText.getPassword());
	    String sql = "SELECT * FROM users WHERE username = \'" + usernameText.getText() + "\' AND password = \'" + createPassword + "\'";
	    rs = st.executeQuery(sql);
	    if(rs.next())
		userId = rs.getInt("userId");
	}
	catch(Exception e){
	    e.printStackTrace(); 
	}
	return userId;
    }
    
    public void Exit() {
	WindowEvent winClosingevent = new WindowEvent(this,WindowEvent.WINDOW_CLOSING);
	Toolkit.getDefaultToolkit().getSystemEventQueue().postEvent(winClosingevent);
    }
}
