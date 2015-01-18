public class Main{

    public static void main(String[] args) {
	Thread load = new Thread(new Loading());
	load.start();
	Thread login = new Thread(new Login());
	login.start();
	try {
	    login.join();
	} catch (InterruptedException e) {
	    e.printStackTrace();
	}
	if(!load.isAlive()) {
	    Loading.frame.setVisible(false);
	}
    }
}
