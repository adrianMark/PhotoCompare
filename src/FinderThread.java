import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.JLabel;
import javax.swing.JOptionPane;

public class FinderThread extends Thread {
	private JLabel fortschritt;
	private PhotoHandler handler;
	private HashMap<File, ArrayList<File>> duplicates;
	private File[] allPhotos;
	private ScanDialog dialog;

	public FinderThread(JLabel fortschritt, PhotoHandler handler, File[] allPhotos, ScanDialog dialog) {
		this.fortschritt = fortschritt;
		this.handler = handler;
		this.allPhotos = allPhotos;
		this.dialog = dialog;
	}

	public void run() {
		duplicates = handler.findDuplicates(allPhotos, fortschritt);
		dialog.saveResults(duplicates);
		dialog.setSelection(0);
		JOptionPane.showMessageDialog(null,"Es wurde(n) "+duplicates.keySet().size()+" Bild(er) mit einem/mehreren Duplikat(en) gefunden!" ,"Meldung", JOptionPane.INFORMATION_MESSAGE);
	}
}
