import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

public class ScanDialog extends JPanel {

	private static final long serialVersionUID = 1L;
	private JTextField textField;
	private PhotoHandler handler = new PhotoHandler();
	private File[] allPhotos;
	private HashMap<File, ArrayList<File>> duplicates;
	private BildVorschau zweites;
	private DefaultListModel<String> listModel;
	private JList<String> list;
	private Object[] originals;
	private JLabel label;
	int selectedIndex = 0;

	public ScanDialog() {

		setBackground(Color.WHITE);
		setForeground(Color.BLACK);
		setBounds(0, 0, 1184, 661);
		setLayout(null);

		JButton btnOrdnerWhlen = new JButton("Ordner w\u00E4hlen");
		btnOrdnerWhlen.setFont(new Font("Calibri", Font.BOLD, 10));
		btnOrdnerWhlen.setBackground(Color.LIGHT_GRAY);
		btnOrdnerWhlen.setBounds(330, 124, 107, 21);
		add(btnOrdnerWhlen);

		textField = new JTextField();
		textField.setEditable(false);
		textField.setFont(new Font("Calibri", Font.BOLD, 10));
		textField.setBounds(107, 124, 213, 19);
		add(textField);
		textField.setColumns(10);

		JLabel lblPfad = new JLabel("Pfad:");
		lblPfad.setFont(new Font("Calibri", Font.BOLD, 12));
		lblPfad.setBounds(64, 127, 45, 13);
		add(lblPfad);

		JLabel lblPhotocompare = new JLabel("Settings");
		lblPhotocompare.setHorizontalAlignment(SwingConstants.CENTER);
		lblPhotocompare.setFont(new Font("Calibri", Font.BOLD, 16));
		lblPhotocompare.setBounds(29, 53, 107, 13);
		add(lblPhotocompare);

		JButton btnScan = new JButton("Scan");
		btnScan.setFont(new Font("Calibri", Font.BOLD, 12));
		btnScan.setBackground(Color.LIGHT_GRAY);
		btnScan.setBounds(127, 257, 96, 37);
		add(btnScan);

		JLabel lblAnzahlFotos = new JLabel("Anzahl Fotos:");
		lblAnzahlFotos.setFont(new Font("Calibri", Font.BOLD, 12));
		lblAnzahlFotos.setBounds(233, 257, 72, 37);
		add(lblAnzahlFotos);

		JLabel numberFotosLabel = new JLabel("");
		numberFotosLabel.setFont(new Font("Calibri", Font.BOLD, 12));
		numberFotosLabel.setBounds(306, 257, 64, 37);
		add(numberFotosLabel);

		JSeparator separator = new JSeparator();
		separator.setOrientation(SwingConstants.VERTICAL);
		separator.setBounds(500, 10, 2, 618);
		add(separator);

		JButton btnStart = new JButton("Start");
		btnStart.setEnabled(false);
		btnStart.setBackground(Color.LIGHT_GRAY);
		btnStart.setFont(new Font("Calibri", Font.BOLD, 12));
		btnStart.setBounds(180, 396, 140, 56);
		add(btnStart);

		JLabel lblFortschritt = new JLabel("Fortschritt:");
		lblFortschritt.setFont(new Font("Calibri", Font.BOLD, 12));
		lblFortschritt.setBounds(193, 473, 72, 13);
		add(lblFortschritt);

		label = new JLabel("");
		label.setFont(new Font("Calibri", Font.BOLD, 12));
		label.setBounds(275, 472, 45, 13);
		add(label);
		zweites = new BildVorschau();
		zweites.setBounds(572, 55, 477, 309);
		add(zweites);

		JButton btnAlleDuplikateEntfernen = new JButton("Alle Duplikate entfernen");
		btnAlleDuplikateEntfernen.setEnabled(false);
		btnAlleDuplikateEntfernen.setFont(new Font("Calibri", Font.BOLD, 12));
		btnAlleDuplikateEntfernen.setBackground(Color.LIGHT_GRAY);
		btnAlleDuplikateEntfernen.setBounds(572, 618, 163, 21);
		add(btnAlleDuplikateEntfernen);

		listModel = new DefaultListModel<>();

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(572, 415, 520, 186);
		add(scrollPane);

		list = new JList<>();
		scrollPane.setViewportView(list);

		JButton btnNchstes = new JButton("N\u00E4chstes");
		btnNchstes.setEnabled(false);
		btnNchstes.setFont(new Font("Calibri", Font.BOLD, 12));
		btnNchstes.setBackground(Color.LIGHT_GRAY);
		btnNchstes.setBounds(1007, 618, 85, 21);
		add(btnNchstes);

		JLabel lblOriginal = new JLabel("Original:");
		lblOriginal.setHorizontalAlignment(SwingConstants.LEFT);
		lblOriginal.setFont(new Font("Calibri", Font.BOLD, 13));
		lblOriginal.setBounds(572, 32, 107, 13);
		add(lblOriginal);

		JLabel lblDuplikate = new JLabel("Duplikate");
		lblDuplikate.setHorizontalAlignment(SwingConstants.LEFT);
		lblDuplikate.setFont(new Font("Calibri", Font.BOLD, 13));
		lblDuplikate.setBounds(572, 396, 107, 13);
		add(lblDuplikate);

		JButton btnMarkierteLschen = new JButton("Markierte l\u00F6schen");
		btnMarkierteLschen.setEnabled(false);
		btnMarkierteLschen.setFont(new Font("Calibri", Font.BOLD, 12));
		btnMarkierteLschen.setBackground(Color.LIGHT_GRAY);
		btnMarkierteLschen.setBounds(738, 618, 132, 21);
		add(btnMarkierteLschen);

		JButton btnOriginalLschen = new JButton("Original l\u00F6schen");
		btnOriginalLschen.setEnabled(false);
		btnOriginalLschen.setFont(new Font("Calibri", Font.BOLD, 12));
		btnOriginalLschen.setBackground(Color.LIGHT_GRAY);
		btnOriginalLschen.setBounds(872, 618, 132, 21);
		add(btnOriginalLschen);
		
		btnOriginalLschen.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				File f = new File(zweites.getToolTipText());
				f.delete();
				zweites.removeAll();
				zweites.validate();
				zweites.repaint();
			}
		});
		
		btnMarkierteLschen.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				for (String s : list.getSelectedValuesList()) {
					File f = new File(s);
					listModel.removeElement(s);
					f.delete();
				}
			}
		});

		btnNchstes.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				nextSelection();
			}
		});

		btnOrdnerWhlen.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				JFileChooser directory = new JFileChooser();
				directory.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
				int choice = directory.showOpenDialog(null);
				if (choice != 1) {
					File root = directory.getSelectedFile();
					textField.setText(root.getAbsolutePath());
					btnStart.setEnabled(false);
					numberFotosLabel.setText("");
				}
			}
		});

		btnStart.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				startSearch();
				btnAlleDuplikateEntfernen.setEnabled(true);
				btnMarkierteLschen.setEnabled(true);
				btnNchstes.setEnabled(true);
				btnOriginalLschen.setEnabled(true);
			}
		});

		btnScan.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (textField.getText().length() != 0) {
					allPhotos = handler.loadAllPhotos(textField.getText());
					numberFotosLabel.setText(String.valueOf(allPhotos.length));
					btnStart.setEnabled(true);
				} else {
					JOptionPane.showMessageDialog(null, "Sie müssen einen Ordner angeben!", "Fehler",
							JOptionPane.ERROR_MESSAGE);
				}
			}
		});

		btnAlleDuplikateEntfernen.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				int choice = JOptionPane.showConfirmDialog(null, "Möchten Sie wirklich alle Duplikate entfernen?",
						"Meldung", JOptionPane.YES_NO_OPTION);
				if (choice == 0) {
					for (File f : duplicates.keySet()) {
						for (File delete : duplicates.get(f)) {
							delete.delete();
						}
					}
				}
			}
		});
	}

	public void saveResults(HashMap<File, ArrayList<File>> duplicates) {
		this.duplicates = duplicates;
		originals = this.duplicates.keySet().toArray();
	}

	public void setSelection(int index) {
		selectedIndex = index;
		File f = (File) originals[index];
		zweites.setImage(f);
		listModel.removeAllElements();
		for (File file : duplicates.get(f)) {
			listModel.addElement(file.getAbsolutePath());
		}
		list.setModel(listModel);
		validate();
	}

	public void nextSelection() {
		if (selectedIndex + 1 < originals.length) {
			setSelection(selectedIndex + 1);
		} else {
			JOptionPane.showMessageDialog(null, "Alle Duplikate wurden bearbeitet!", "Meldung",
					JOptionPane.INFORMATION_MESSAGE);
		}
	}

	public void startSearch() {
		duplicates = new HashMap<>();
		FinderThread t = new FinderThread(label, handler, allPhotos, this);
		t.start();
	}
}
