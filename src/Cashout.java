import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Toolkit;
import java.util.List;

import javax.swing.DefaultListModel;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.ImageIcon;

import java.awt.Font;

import javax.swing.JList;
import javax.swing.JButton;
import javax.swing.border.BevelBorder;
import java.awt.Color;


public class Cashout extends JFrame {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    private JPanel contentPane;
    private DefaultListModel<String> cashoutItems = new DefaultListModel<String>();
    /**
     * Launch the application.
     */
    public static void Invoke(final List<Track> boughtTracks, final int userId,final float totalPrice) {
	EventQueue.invokeLater(new Runnable() {
	    public void run() {
		try {
		    Cashout frame = new Cashout(boughtTracks,userId,totalPrice);
		    frame.setVisible(true);
		} catch (Exception e) {
		    e.printStackTrace();
		}
	    }
	});
    }

    /**
     * Create the frame.
     */
    public Cashout(List<Track> boughtTracks, int userId,float totalPrice) {
    	setIconImage(Toolkit.getDefaultToolkit().getImage(Cashout.class.getResource("/javax/swing/plaf/metal/icons/ocean/warning.png")));
	setTitle("Cashout");
	Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
	setBounds(0, 0, (int)screenSize.getWidth(), (int)screenSize.getHeight());
	setResizable(false);
	contentPane = new JPanel();
	contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
	setContentPane(contentPane);
	contentPane.setLayout(null);
	
	JLabel lblUserId = new JLabel("USER ID:",SwingConstants.CENTER);
	lblUserId.setFont(new Font("Arial Black", Font.PLAIN, 16));
	lblUserId.setBounds(34, 41, 102, 33);
	contentPane.add(lblUserId);
	
	JLabel lblUserIdValue = new JLabel("",SwingConstants.CENTER);
	lblUserIdValue.setFont(new Font("Arial Black", Font.PLAIN, 16));
	lblUserIdValue.setBounds(146, 41, 117, 33);
	lblUserIdValue.setText(Integer.toString(userId));
	contentPane.add(lblUserIdValue);
	
	

	JList<String> list = new JList<String>();
	list.setBorder(new BevelBorder(BevelBorder.LOWERED, Color.BLACK, Color.BLACK, Color.BLACK, Color.BLACK));
	list.setBounds(566, 73, 381, 535);
	list.setFixedCellHeight(80);
	list.setBorder(new EmptyBorder(10, 10, 10, 10));
	for(int cnt=0; cnt<boughtTracks.size(); cnt++) {
		String track ="<html>" + "<p> " + boughtTracks.get(cnt).SongId + " " + boughtTracks.get(cnt).Title + "</p>" + "<p>" + "Author: " + boughtTracks.get(cnt).Author + "</p>"+ "<p>" + "Length: " + boughtTracks.get(cnt).Length + "</p>"+ "<p>" + "Price: " + boughtTracks.get(cnt).Price + " €" + "</p>" + "</html>";
		cashoutItems.addElement(track);
	}
	list.setModel(cashoutItems);
	contentPane.add(list);
	
	JLabel lvlTotal = new JLabel("Total:",SwingConstants.CENTER);
	lvlTotal.setFont(new Font("Arial Black", Font.PLAIN, 16));
	lvlTotal.setBounds(34, 85, 102, 33);
	contentPane.add(lvlTotal);
	
	JLabel lvlTotalValue = new JLabel("",SwingConstants.CENTER);
	lvlTotalValue.setFont(new Font("Arial Black", Font.PLAIN, 16));
	lvlTotalValue.setBounds(146, 85, 117, 33);
	lvlTotalValue.setText(Float.toString(totalPrice) + "€");
	contentPane.add(lvlTotalValue);
	
	JButton btnNewButton = new JButton("Cashout");
	btnNewButton.setFont(new Font("Wide Latin", Font.PLAIN, 18));
	btnNewButton.setBounds(614, 633, 291, 96);
	contentPane.add(btnNewButton);
	
	JLabel backGround = new JLabel("");
	backGround.setIcon(new ImageIcon(Cashout.class.getResource("/Images/bgImageCashout.jpg")));
	backGround.setBounds(0, 0, 1360, 740);
	contentPane.add(backGround);
		
    }
}
