import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

public class CompareGUI extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;

	public CompareGUI() {
		setTitle("PhotoCompare");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1200, 700);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		contentPane.add(new ScanDialog());
	}
}
