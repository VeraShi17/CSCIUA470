
public class Main {

	public static void main(String[] args) {
		DatabaseConnect.createTables();
		MainGUI maingui = new MainGUI();
		maingui.show();
	}
	
}
