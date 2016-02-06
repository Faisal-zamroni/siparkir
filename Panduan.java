import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JButton;
import java.awt.Color;
import javax.swing.JTextPane;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JOptionPane;


@SuppressWarnings("serial")
public class Panduan extends JFrame {

	private JPanel contentPane;



	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Panduan frame = new Panduan();
					frame.setVisible(true);
				} catch (Exception ex) {
				JOptionPane.showMessageDialog(null, ex); //print errornya ke message dialog
				}
			}
		});
	}


	public Panduan() {
		setResizable(false);
		setTitle("Panduan Penggunaan");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 624, 469);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JButton btnTutup = new JButton("Tutup");
		btnTutup.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//aksi ketika btnTutup diklik
				btnTutup();
			}
		});
		btnTutup.setForeground(Color.WHITE);
		btnTutup.setBackground(new Color(0, 153, 51));
		btnTutup.setBounds(441, 394, 169, 32);
		contentPane.add(btnTutup);

		JTextPane textPane = new JTextPane();
		textPane.setEditable(false);
		textPane.setBounds(12, 12, 598, 370);
		textPane.setText("Panduannya belum dibikin\nNanti diupdate lagi kalau sempat :P");
		contentPane.add(textPane);
	}


	//method buat nutup frame panduan dan tentang app.
	public void btnTutup() {
		super.dispose();
	}
}
