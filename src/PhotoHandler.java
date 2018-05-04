import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import javax.imageio.ImageIO;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

public class PhotoHandler {

	public File[] loadAllPhotos(String root) {
		File file = new File(root);
		File[] allFiles = file.listFiles();
		int photoIndex = 0;
		int numberPhotos = 0;
		int dynamicSize = allFiles.length;
		for (int x = 0; x < dynamicSize; x++) {
			File temp = allFiles[x];
			if (temp.isDirectory() && temp.listFiles() != null) {
				allFiles = mergeArrays(allFiles, temp.listFiles());
				dynamicSize = allFiles.length;
			}
		}
		for(int x = 0; x < allFiles.length; x++) {
			File temp = allFiles[x];
			String path = temp.getAbsolutePath();
			if (isPhoto(path)) {
				numberPhotos++;
			}
		}
		File[] allPhotos = new File[numberPhotos];
		for (int x = 0; x < allFiles.length; x++) {
			File temp = allFiles[x];
			String path = temp.getAbsolutePath();
			if (isPhoto(path)) {
				allPhotos[photoIndex] = temp;
				photoIndex++;
			}
		}
		return allPhotos;

	}

	public File[] mergeArrays(File[] one, File[] two) {
		File[] result = new File[one.length + two.length];
		for (int x = 0; x < one.length; x++) {
			result[x] = one[x];
		}
		int offset = one.length;
		for (int x = 0; x < two.length; x++) {
			result[offset] = two[x];
			offset++;
		}
		return result;
	}
	
	public boolean isPhoto(String path) {
		File temp = new File(path);
		return temp.isFile() && (path.endsWith(".jpg") || path.endsWith(".jpeg") || path.endsWith(".png"));
	}
	
	public HashMap<File, ArrayList<File>> findDuplicates(File[]allPhotos, JLabel fortschritt){
		HashMap <File, ArrayList<File>> duplicates = new HashMap<>();
		for(int x= 0; x<allPhotos.length; x++) {
			double percentage = ((double)x)/allPhotos.length*100;
			int anzeige = (int) percentage; 
			fortschritt.setText(String.valueOf(anzeige)+" %");
			ArrayList<File> temp = new ArrayList<>();
			for(int y = x; y<allPhotos.length;y++) {
				if(x==y||allPhotos[y] == null||allPhotos[x] == null) {
					continue;
				}else {
					if(isSame(allPhotos[x], allPhotos[y])) {
						temp.add(allPhotos[y]);
						allPhotos[y] = null;
					}
				}
			}
			if(temp.size() > 0) {
				duplicates.put(allPhotos[x], temp);
			}
		}
		fortschritt.setText("100 %");
		return duplicates;
	}
	
	public boolean isSame(File one, File two) {
		BufferedImage imageOne;
		BufferedImage imageTwo;
		try {
			imageOne = ImageIO.read(one);
			imageTwo = ImageIO.read(two);
			if(imageOne.getWidth() == imageTwo.getWidth() && imageOne.getHeight() == imageTwo.getHeight()) {
				for(int x = 0; x< imageOne.getWidth(); x++) {
					for(int y = 0; y< imageOne.getHeight(); y++) {
						if(imageOne.getRGB(x, y) != imageTwo.getRGB(x, y)) {
							return false;
						}
					}
				}
			}
		}catch(IOException e) {
			JOptionPane.showMessageDialog(null, "Ein Fehler ist aufgetreten!","Fehler", JOptionPane.ERROR_MESSAGE);
		}
		return true;
		
	}
}
