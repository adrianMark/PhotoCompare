
import java.awt.AlphaComposite;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * Diese Klasse ist von JPanel abgeleitet und dient als Vorschaufenster für geladene Bilddateien.
 * 
 * @author Adrian
 *
 */

public class BildVorschau extends JPanel{

	private static final long serialVersionUID = 1L;
	private ImageIcon image;
	private JLabel label;

	
	/**
	 * Diese Methode entfernt das angezeigte Bild vom Panel.
	 */
	public void reset() {
		this.image = null;
	}
	
	public static BufferedImage groesseÄndern(Image original, int neueWeite, int neueHoehe) {
		//Bei Fehlerhaften Parametern soll eine Standardgröße erzeugt werden
		if(neueWeite == 0) {
			neueWeite = 100;
		}
		if(neueHoehe == 0) {
			neueHoehe = 50;
		}
		int typ = BufferedImage.TRANSLUCENT;
		BufferedImage verändert = new BufferedImage(neueWeite, neueHoehe, typ);
		Graphics2D g = verändert.createGraphics();
		g.setComposite(AlphaComposite.Src);
		g.drawImage(original, 0, 0, neueWeite, neueHoehe, null);
		g.dispose();
		return verändert;
	}
	
	//Getter und Setter
	public ImageIcon getImage() {
		return image;
	}
	
	public void setImage(File file) {
		setToolTipText(file.getAbsolutePath());
		BufferedImage buffer;
		try {
			buffer = ImageIO.read(file);
			buffer = groesseÄndern(buffer, getWidth(),getHeight());
			removeAll();
			this.image = new ImageIcon(buffer);
			label = new JLabel(image);
			add(label);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public JLabel getLabel() {
		return label;
	}

	public void setLabel(JLabel label) {
		this.label = label;
	}
	
}
