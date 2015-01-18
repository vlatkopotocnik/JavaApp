import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.ImageIcon;


public class Loading extends JFrame implements Runnable {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    public static Loading frame;
    private JPanel contentPane;

    /**
     * Create the frame.
     */
    public Loading() {
	setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	setBounds(100, 100, 350, 230);
	contentPane = new JPanel();
	contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
	setContentPane(contentPane);
	contentPane.setLayout(null);
	
	JLabel label = new JLabel("");
	label.setIcon(new ImageIcon(Login.class.getResource("/Images/splash.gif")));
	label.setBounds(0, 0, 334, 192);
	contentPane.add(label);
    }
    /**
     * Launch the application.
     * @return 
     */
    
    @Override
    public void run() {
	try {
	    frame = new Loading();
	    frame.setVisible(true);
	    windowCommand.setCenter(frame);
	    Thread.sleep(100);
	} catch (Exception e) {
	    e.printStackTrace();
	}
	
    }

}
