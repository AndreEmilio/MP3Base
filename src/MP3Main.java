
public class MP3Main {

	public static void main(String[] args) {

		MP3Container con = new MP3Container();
		
		MP3GUI gui = new MP3GUI(con);
		
		gui.startGUI();	
	}

}
