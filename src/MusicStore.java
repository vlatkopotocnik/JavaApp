import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Toolkit;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.DefaultListModel;
import javax.swing.JLabel;
import javax.swing.ImageIcon;
import javax.swing.JList;
import javax.swing.JButton;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Font;
import java.awt.Color;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.LinkedList;

import javax.swing.JComboBox;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

import sun.audio.*;
import javax.swing.JScrollPane;



public class MusicStore extends JFrame {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    private JPanel contentPane;
    private JList<String> listSongs;
    private JList<String> listAddSongs;
    private JLabel lblPlaySampleStatus;
    private JLabel lblTotalPriceValue;
    private Statement st;
    private ResultSet rs;
    private static String[] category = {"All","Rock","Pop","Blues","Rap"};
    private static LinkedList<Track> listTrack = new LinkedList<Track>();
    private static LinkedList<Track> boughtTracks = new LinkedList<Track>();
    private DefaultListModel<String> addedItems = new DefaultListModel<String>();
    private float totalPrice = 0;
    
    /**
     * Launch the application.
     */
    public static void Invoke(final int userId) {
	EventQueue.invokeLater(new Runnable() {
	    public void run() {
		try {		    
		    MusicStore frame = new MusicStore(userId);
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
    public MusicStore(final int userId) {
    	setIconImage(Toolkit.getDefaultToolkit().getImage(MusicStore.class.getResource("/javax/swing/plaf/metal/icons/Inform.gif")));
	initialiseListOfTracks();
    	setTitle("Music Shop");
    	setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
	setBounds(0, 0, (int)screenSize.getWidth(), (int)screenSize.getHeight());
	setResizable(false);
	contentPane = new JPanel();
	contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
	setContentPane(contentPane);
	contentPane.setLayout(null);
	
	listSongs = new JList<String>();
	listSongs.setBounds(39, 84, 350, 385);
	listSongs.setVisibleRowCount(4);
	listSongs.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);	
	listSongs.setFixedCellHeight(80);
	listSongs.setBorder(new EmptyBorder(10, 10, 10, 10));	
	populateList("All");
	//contentPane.add(listSongs);
	
	listAddSongs = new JList<String>();
	listAddSongs.setBounds(973, 84, 350, 385);
	listAddSongs.setVisibleRowCount(4);
	listAddSongs.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
	//contentPane.add(listAddSongs);
	
	
	JButton btnAdd = new JButton("ADD ->");
	btnAdd.addActionListener(new ActionListener() {
		public void actionPerformed(ActionEvent arg0) {
		    addedItems.addElement(listSongs.getSelectedValue());
		    String song[] = listSongs.getSelectedValue().split(" ");
		    int songId = Integer.parseInt(song[1]); 
		    for(int cnt = 0; cnt < listTrack.size(); cnt++) {
			if(listTrack.get(cnt).SongId == songId) {
			    totalPrice += listTrack.get(cnt).Price;
			    lblTotalPriceValue.setText(String.format("%.2f", (float)totalPrice) + " €");
			    boughtTracks.add(listTrack.get(cnt));
			}
		    }
		    listAddSongs.setModel(addedItems);	    
		}
	});
	btnAdd.setBounds(49, 480, 120, 23);
	contentPane.add(btnAdd);
	
	JButton btnRemove = new JButton("<- REMOVE");
	btnRemove.addActionListener(new ActionListener() {
		public void actionPerformed(ActionEvent arg0) {
		    if(!boughtTracks.isEmpty()) {
			if(listAddSongs.getSelectedIndex() != -1) {
			    addedItems.removeElementAt(listAddSongs.getSelectedIndex());
			    listAddSongs.setModel(addedItems);
			    String song[] = listSongs.getSelectedValue().split(" ");
			    int songId = Integer.parseInt(song[1]); 
			    for(int cnt = 0; cnt < listTrack.size(); cnt++) {
				if(listTrack.get(cnt).SongId == songId) {
				    totalPrice -= listTrack.get(cnt).Price;
				    lblTotalPriceValue.setText(Float.toString(totalPrice));
				    boughtTracks.remove(listTrack.get(cnt));
				}
			    }
			}
		    }
		    if(boughtTracks.isEmpty()) {
			lblTotalPriceValue.setText("0.00");
		    }
		}
	});
	btnRemove.setBounds(983, 480, 120, 23);
	contentPane.add(btnRemove);
	
	JButton btnNewButton = new JButton("BUY");
	btnNewButton.setFont(new Font("Wide Latin", Font.PLAIN, 11));
	btnNewButton.addActionListener(new ActionListener() {
		public void actionPerformed(ActionEvent arg0) {
		    if(!boughtTracks.isEmpty()){
			Cashout.Invoke(boughtTracks, userId, totalPrice);
		    }
		}
	});
	btnNewButton.setBounds(1013, 593, 280, 119);
	btnNewButton.setFont(btnNewButton.getFont().deriveFont(18.0f));
	contentPane.add(btnNewButton);
	
	JLabel listOfSongs = new JLabel("LIST OF SONGS",SwingConstants.CENTER);
	listOfSongs.setFont(new Font("Arial Black", Font.PLAIN, 18));
	listOfSongs.setBackground(Color.WHITE);
	listOfSongs.setBounds(39, 40, 350, 33);
	listOfSongs.setOpaque(true);
	contentPane.add(listOfSongs);
	
	JLabel addedSongs = new JLabel("ADDED SONGS",SwingConstants.CENTER);
	addedSongs.setFont(new Font("Arial Black", Font.PLAIN, 18));
	addedSongs.setBackground(Color.WHITE);
	addedSongs.setBounds(973, 40, 350, 33);
	addedSongs.setOpaque(true);
	contentPane.add(addedSongs);
	
	final JComboBox comboBox = new JComboBox(category);
	comboBox.addActionListener(new ActionListener() {
		public void actionPerformed(ActionEvent e) {
		    String category = comboBox.getSelectedItem().toString();
		    populateList(category);
		    contentPane.repaint();
		}
	});
	comboBox.setBounds(179, 537, 207, 36);
	contentPane.add(comboBox);
	
	JLabel lblGenire = new JLabel("Category",SwingConstants.CENTER);
	lblGenire.setBackground(Color.WHITE);
	lblGenire.setForeground(Color.BLACK);
	lblGenire.setOpaque(true);
	lblGenire.setFont(new Font("Arial Black", Font.PLAIN, 16));
	lblGenire.setBounds(49, 537, 120, 33);
	contentPane.add(lblGenire);
	
	JLabel lblTotalPrice = new JLabel("Total:",SwingConstants.CENTER);
	lblTotalPrice.setBackground(Color.WHITE);
	lblTotalPrice.setFont(new Font("Arial Black", Font.PLAIN, 16));
	lblTotalPrice.setBounds(1025, 551, 101, 23);
	lblTotalPrice.setOpaque(true);
	contentPane.add(lblTotalPrice);
	
	JLabel lblInfoClickSample = new JLabel("Choose from list of songs and click play sample",SwingConstants.CENTER);
	lblInfoClickSample.setFont(new Font("Arial Black", Font.PLAIN, 14));
	lblInfoClickSample.setBounds(418, 84, 521, 52);
	lblInfoClickSample.setOpaque(true);
	contentPane.add(lblInfoClickSample);
	
	lblTotalPriceValue = new JLabel("",SwingConstants.CENTER);
	lblTotalPriceValue.setBackground(Color.WHITE);
	lblTotalPriceValue.setFont(new Font("Arial Black", Font.PLAIN, 16));
	lblTotalPriceValue.setBounds(1124, 551, 147, 23);
	lblTotalPriceValue.setOpaque(true);
	lblTotalPriceValue.setText("0.00");
	contentPane.add(lblTotalPriceValue);
	
	lblPlaySampleStatus = new JLabel("",SwingConstants.CENTER);
	lblPlaySampleStatus.setBackground(Color.BLACK);
	lblPlaySampleStatus.setForeground(Color.WHITE);
	lblPlaySampleStatus.setBounds(523, 355, 350, 23);
	contentPane.add(lblPlaySampleStatus);
	
	JButton btnNewButton_1 = new JButton("");
	btnNewButton_1.setMultiClickThreshhold(5000);
	btnNewButton_1.addActionListener(new ActionListener() {
		public void actionPerformed(ActionEvent arg0) {
		    if(listSongs.getSelectedIndex() != -1) {
			String song[] = listSongs.getSelectedValue().split(" ");
			int songId = Integer.parseInt(song[1]); 
			String songTitle = "";
			for(int cnt = 0; cnt < listTrack.size(); cnt++) {
			    if(listTrack.get(cnt).SongId == songId) {
				songTitle = listTrack.get(cnt).Title;
			    }
			}
			lblPlaySampleStatus.setText("Playing..." + songTitle);
			lblPlaySampleStatus.setOpaque(true);
			URL fullPath = MusicStore.class.getResource("/WavSample/" + songTitle + ".wav");
			String path = fullPath.toString().substring(6).replaceAll("%20", " ");
			try {
			    InputStream inputStr = new FileInputStream(new File(path));
			    AudioStream audioStr = new AudioStream(inputStr);			    
			    AudioPlayer.player.start(audioStr);							    
			}
			catch(Exception e){
			    e.printStackTrace(); 
			}
		    }
		}
	});
	btnNewButton_1.setBounds(523, 147, 350, 183);
	btnNewButton_1.setIcon(new ImageIcon(MusicStore.class.getResource("Images/play_reverse.png")));
	contentPane.add(btnNewButton_1);
	
	JScrollPane scrollPane = new JScrollPane(listSongs);
	scrollPane.setBounds(39, 84, 350, 385);
	contentPane.add(scrollPane);
	
	JScrollPane scrollPane_1 = new JScrollPane(listAddSongs);
	scrollPane_1.setBounds(973, 84, 350, 385);
	contentPane.add(scrollPane_1);
	
	JLabel backGround = new JLabel("");
	backGround.setIcon(new ImageIcon(MusicStore.class.getResource("/Images/bgImageMusicStore.png")));
	backGround.setBounds(0, 0, 1360, 740);
	contentPane.add(backGround);
	

	
	
		
    }
    
    private void initialiseListOfTracks(){
	try {
	    st = Login.conn.createStatement();
	    String sql = "SELECT * FROM track";
	    rs = st.executeQuery(sql);
	    while(rs.next()) {
		Track oneTrack = new Track(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getFloat(5), rs.getString(6));	
		listTrack.add(oneTrack);
	    }
	}
	catch(Exception e){
	    e.printStackTrace(); 
	}
    }
    
    private void populateList(String category) {
	DefaultListModel<String> DLM = new DefaultListModel<String>();
	if(category == "All") {
	    // NAKON KAJ NAPRAVIM SCROLL BAR OVDJE PROVJERITI DALI UCITA ZADNJU PJESMU!!!!!!!!!!!!!!!!
	    
	    
	    
	    
	    for(int cnt=0; cnt<listTrack.size(); cnt++) {
		String track ="<html>" + "<p> " + listTrack.get(cnt).SongId + " " + listTrack.get(cnt).Title + "</p>" + "<p>" + "Author: " + listTrack.get(cnt).Author + "</p>"+ "<p>" + "Length: " + listTrack.get(cnt).Length + "</p>"+ "<p>" + "Price: " + listTrack.get(cnt).Price + " €" + "</p>" + "</html>";
		DLM.addElement(track);
	    }
	}
	else {
	    for(int cnt=0; cnt<listTrack.size(); cnt++) {
		if(listTrack.get(cnt).Category.equals(category)) {
		String track ="<html>" + "<p> " + listTrack.get(cnt).SongId + " " + listTrack.get(cnt).Title + "</p>" + "<p>" + "Author: " + listTrack.get(cnt).Author + "</p>"+ "<p>" + "Length: " + listTrack.get(cnt).Length + "</p>"+ "<p>" + "Price: " + listTrack.get(cnt).Price + " €" + "</p>" + "</html>";
		DLM.addElement(track);
		}
	    }
	}
	listSongs.setModel(DLM);
    }
}
