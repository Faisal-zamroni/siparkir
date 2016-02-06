import java.awt.EventQueue;
import java.awt.Image;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JTabbedPane;
import javax.swing.JButton;
import java.awt.Color;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JTextPane;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.AbstractListModel;
import javax.swing.ImageIcon;
import javax.swing.SwingConstants;
import java.awt.Font;
import javax.swing.JOptionPane;

@SuppressWarnings("serial")
public class TentangApp extends JFrame {

	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					TentangApp frame = new TentangApp();
					frame.setVisible(true);
				} catch (Exception ex) {
				JOptionPane.showMessageDialog(null, ex); //print errornya ke message dialog;
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public TentangApp() {
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setResizable(false);
		setTitle("Tentang Aplikasi");
		setBounds(100, 100, 491, 386);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.setBackground(Color.WHITE);
		tabbedPane.setBounds(12, 12, 465, 281);
		contentPane.add(tabbedPane);

		JPanel panel_1 = new JPanel();
		panel_1.setBackground(Color.WHITE);
		tabbedPane.addTab("Tentang", null, panel_1, null);
		panel_1.setLayout(null);

		JLabel label = new JLabel("");
		label.setHorizontalAlignment(SwingConstants.CENTER);
		label.setBounds(148, 27, 162, 163);
		Image gmbr = new ImageIcon(this.getClass().getResource("ikon/gembok.png")).getImage();
		label.setIcon(new ImageIcon(gmbr));
		panel_1.add(label);

		JLabel lblSiparkirUnsika = new JLabel("SiParkir Unsika");
		lblSiparkirUnsika.setFont(new Font("Dialog", Font.BOLD, 14));
		lblSiparkirUnsika.setForeground(Color.BLACK);
		lblSiparkirUnsika.setHorizontalAlignment(SwingConstants.CENTER);
		lblSiparkirUnsika.setBounds(148, 175, 162, 36);
		panel_1.add(lblSiparkirUnsika);

		JLabel lblSistemInformasiParkir = new JLabel("Sistem Informasi Parkir Universitas Singaperbangsa");
		lblSistemInformasiParkir.setHorizontalAlignment(SwingConstants.CENTER);
		lblSistemInformasiParkir.setForeground(Color.DARK_GRAY);
		lblSistemInformasiParkir.setBounds(43, 205, 372, 24);
		panel_1.add(lblSistemInformasiParkir);

		JPanel panel_2 = new JPanel();
		panel_2.setBackground(Color.WHITE);
		tabbedPane.addTab("Kredit", null, panel_2, null);
		panel_2.setLayout(null);

		JList list = new JList();
		list.setModel(new AbstractListModel() {
			String[] values = new String[] {"Suyadi <suyadi@daunsalam.net>", "Arif A. Rachman <1441177004119@student.unsika.ac.id>", "Ardiyanto <1441177004002@student.unsika.ac.id>", "Oki Fernandes <1441177004135@student.unsika.ac.id>", "Weni Nuraeni <1441177004022@student.unsika.ac.id>"};
			public int getSize() {
				return values.length;
			}
			public Object getElementAt(int index) {
				return values[index];
			}
		});
		list.setBounds(12, 12, 436, 230);
		panel_2.add(list);

		JPanel panel = new JPanel();
		panel.setBackground(Color.WHITE);
		tabbedPane.addTab("Lisensi", null, panel, null);
		panel.setLayout(null);

		JTextPane isiLisensi = new JTextPane();
		isiLisensi.setEditable(false);
		isiLisensi.setText("Program ini dirilis secara opensource. Anda diperbolehkan menggandakan, menyalin atau mengutip sebagian maupun seluruh isi kode ini dengan catatan tidak digunakan untuk tujuan komersial, membahayakan dan/atau merugikan orang lain, serta tidak menghilangkan kredit dari penulis asli.\n\nSegala kerusakan dan/atau kerugian yang mungkin ditimbulkan dari penggunaan aplikasi ini bukan merupakan tanggung jawab dari penulis.\n\nDemikian berkas lisensi ini kami sampaikan,\nharap menjadi maklum.");
		isiLisensi.setBounds(12, 12, 436, 230);
		panel.add(isiLisensi);

		JButton btnTutup = new JButton("Tutup");
		btnTutup.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				//aksi ketika btnTutup diklik
				btnTutup();
			}
		});
		btnTutup.setForeground(Color.WHITE);
		btnTutup.setBackground(new Color(0, 153, 51));
		btnTutup.setBounds(308, 313, 169, 32);
		contentPane.add(btnTutup);
	}


	//method buat nutup frame panduan dan tentang app.
	public void btnTutup() {
		super.dispose();
	}
}
