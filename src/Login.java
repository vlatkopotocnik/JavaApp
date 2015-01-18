import java.awt.Font;
import java.awt.Toolkit;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.JButton;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import javax.swing.JTextField;
import javax.swing.JLabel;

import java.awt.Color;

import javax.swing.ImageIcon;

import java.sql.*;

public class Login extends JFrame implements Runnable {

    /**
     * 
     */
    private static Login frame;
    private static final long serialVersionUID = 1L;
    private JPanel contentPane;
    private JTextField usernameText;
    private JPasswordField passwordText;
    private JLabel userExists;    
    private JLabel usernameError;
    private JLabel passwordError;

    static Connection conn;
    private Statement st;
    private ResultSet rs;
    private JLabel label;
    public static String driver = "sun.jdbc.odbc.JdbcOdbcDriver";
    public static String db = "jdbc:odbc:MainDatabase";
    

   


    /**
     * Create the frame.
     */
    public Login() {
	    try {
		Thread.sleep(100);
		conn = DriverManager.getConnection(db);
		Class.forName(driver);
	    } catch (Exception ex) {
		 ex.printStackTrace();
	    }
	    
	setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	setIconImage(Toolkit.getDefaultToolkit().getImage(Login.class.getResource("/javax/swing/plaf/metal/icons/ocean/question.png")));
	setTitle("Login");
	setResizable(false);
	setBounds(100, 100, 320, 220);
	contentPane = new JPanel();
	contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
	setContentPane(contentPane);
	contentPane.setLayout(null);

	JButton btnLogin = new JButton("LOGIN");
	btnLogin.addActionListener(new ActionListener() {
	    public void actionPerformed(ActionEvent e) {
		int userId = 0;
		if (checkIsValid()) {
		    if ((userId = userExists()) != 0) {
			MusicStore.Invoke(userId);
			frame.setVisible(false);
		    } else {
			userExists.setText("We could not find an account");
			userExists.setOpaque(true);
			contentPane.paintImmediately(207, 162, 107, 30);
		    }
		}
	    }
	});
	btnLogin.setBounds(65, 117, 89, 23);
	contentPane.add(btnLogin);

	JButton btnRegister = new JButton("REGISTER");
	btnRegister.addActionListener(new ActionListener() {
	    public void actionPerformed(ActionEvent arg0) {
		Register.Invoke();
		userExists.setText("");
		userExists.setOpaque(false);
	    }
	});
	btnRegister.setBounds(164, 117, 113, 23);
	contentPane.add(btnRegister);

	usernameText = new JTextField();
	usernameText.setBounds(65, 24, 132, 20);
	contentPane.add(usernameText);
	usernameText.setColumns(10);

	TextPrompt tp = new TextPrompt("Username", usernameText);
	tp.setForeground(Color.BLACK);
	tp.changeAlpha(0.5f);
	tp.changeStyle(Font.BOLD + Font.ITALIC);

	passwordText = new JPasswordField();
	passwordText.setBounds(65, 68, 132, 20);
	contentPane.add(passwordText);
	passwordText.setColumns(10);

	TextPrompt tp1 = new TextPrompt("Password", passwordText);
	tp1.setForeground(Color.BLACK);
	tp1.changeAlpha(0.5f);
	tp1.changeStyle(Font.BOLD + Font.ITALIC);

	userExists = new JLabel("", SwingConstants.CENTER);
	userExists.setForeground(new Color(255, 0, 0));
	userExists.setBackground(new Color(0, 0, 0));
	userExists.setBounds(46, 151, 243, 30);
	contentPane.add(userExists);

	usernameError = new JLabel("", SwingConstants.CENTER);
	usernameError.setForeground(new Color(255, 0, 0));
	usernameError.setBackground(new Color(0, 0, 0));
	usernameError.setBounds(207, 27, 97, 14);
	contentPane.add(usernameError);

	passwordError = new JLabel("", SwingConstants.CENTER);
	passwordError.setForeground(new Color(255, 0, 0));
	passwordError.setBackground(new Color(0, 0, 0));
	passwordError.setBounds(207, 71, 97, 14);
	contentPane.add(passwordError);

	label = new JLabel("");
	label.setIcon(new ImageIcon(Login.class.getResource("/Images/bgImageLogin.png")));
	label.setBounds(0, 0, 314, 192);
	contentPane.add(label);
    }

    private int userExists() {
	int userId = 0;
	try {
	    st = conn.createStatement();
	    StringBuilder stringBuilder = new StringBuilder();
	    stringBuilder.append("SELECT * FROM users WHERE username = \'");
	    stringBuilder.append(usernameText.getText());
	    stringBuilder.append("\' AND password = \'");
	    stringBuilder.append(passwordText.getPassword());
	    stringBuilder.append("\'");
	    String sql = stringBuilder.toString();
	    rs = st.executeQuery(sql);
	    while (rs.next())
		userId = rs.getInt("userId");
	    rs.close();
	} catch (Exception e) {
	    e.printStackTrace();
	}
	return userId;
    }

    public boolean checkIsValid() {
	if (usernameText.getText().equals("")) {
	    usernameError.setText("Required");
	    usernameError.setOpaque(true);
	    return false;
	} else {
	    usernameError.setText("");
	    usernameError.setOpaque(false);
	}
	if (passwordText.getPassword().equals("")) {
	    passwordError.setText("Required");
	    passwordError.setOpaque(true);
	    return false;
	} else {
	    passwordError.setText("");
	    passwordError.setOpaque(false);
	}
	if (!usernameText.getText().equals("")
		&& !passwordText.getPassword().equals("")) {
	    userExists.setText("");
	    userExists.setOpaque(false);
	    contentPane.paintImmediately(46, 151, 320, 220);
	    return true;
	}
	return true;
    }
    /**
     * Launch the application.
     * @return 
     */
    @Override
    public void run() {
	try {
	    frame = new Login();
	    windowCommand.setCenter(frame);
	    frame.setVisible(true);
	    Thread.sleep(100);
	} catch (Exception e) {
	    e.printStackTrace();
	}
	
    }
}
