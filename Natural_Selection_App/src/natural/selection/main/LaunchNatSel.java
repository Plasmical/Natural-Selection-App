package natural.selection.main;

import java.awt.Dimension;
import java.awt.Toolkit;

public class LaunchNatSel {

	public static void main(String[] args) {
		Toolkit toolkit = Toolkit.getDefaultToolkit();
		Dimension screenDimensions = toolkit.getScreenSize();
		String versionOfGame = "0.1";
		MainApp game = new MainApp("Natural Selection Simulator v" + versionOfGame + " Alpha", screenDimensions.width, screenDimensions.height);
		game.start();
	}
}