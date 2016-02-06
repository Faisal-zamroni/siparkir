import java.awt.EventQueue;
import java.awt.Image;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.Color;
import javax.swing.JTextField;
import javax.swing.JPasswordField;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import java.awt.event.ActionListener;
import java.sql.*;
import java.awt.event.ActionEvent;
import javax.swing.SwingConstants;
import java.awt.Font;

@SuppressWarnings("serial")
public class Login extends JFrame {

	private JPanel contentPane;
	private JTextField textUser;
	private JPasswordField textPass;
	private JLabel pesan;
	int userAda;
	private JLabel lblSistemInformasiParkir;
	private JLabel lblOlehKelompok;



	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Login frame = new Login();
					frame.setVisible(true);
				} catch (Exception ex) {
				JOptionPane.showMessageDialog(null, ex); //print errornya ke message dialog
				}
			}
		});
	}



	public Login() {
		setTitle("Login");
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(255, 255, 255));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JPanel panel = new JPanel();
		panel.setBackground(new Color(0, 153, 204));
		panel.setBounds(0, 0, 448, 80);
		contentPane.add(panel);
		panel.setLayout(null);

		lblSistemInformasiParkir = new JLabel("Sistem Informasi Parkir Unsika");
		lblSistemInformasiParkir.setFont(new Font("Dialog", Font.BOLD, 20));
		lblSistemInformasiParkir.setHorizontalAlignment(SwingConstants.CENTER);
		lblSistemInformasiParkir.setForeground(new Color(255, 255, 255));
		lblSistemInformasiParkir.setBounds(42, 0, 373, 37);
		panel.add(lblSistemInformasiParkir);

		lblOlehKelompok = new JLabel("Oleh Kelompok 1");
		lblOlehKelompok.setHorizontalAlignment(SwingConstants.CENTER);
		lblOlehKelompok.setForeground(new Color(255, 255, 255));
		lblOlehKelompok.setBounds(112, 37, 239, 15);
		panel.add(lblOlehKelompok);

		textUser = new JTextField();
		textUser.setBounds(195, 107, 192, 30);
		contentPane.add(textUser);
		textUser.setColumns(10);

		textPass = new JPasswordField();
		textPass.setBounds(195, 149, 192, 30);
		contentPane.add(textPass);

		JButton btnLogin = new JButton("Login");
		btnLogin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				//btnLogin diklik
				String iptUser = textUser.getText(); //string input user
				char iptPass[] = textPass.getPassword();//string input pass
				if(!iptUser.isEmpty() && iptPass.length > 0) { //jika input user dan pass tidak kosong
					cekLogin(); //cek username dan pass-nya
				} else { //jika kosong
					textUser.setText("");
					textPass.setText("");
					pesan.setText("Isi semua kolom dengan benar!");
				}
			}
		});
		btnLogin.setBackground(new Color(0, 153, 204));
		btnLogin.setForeground(new Color(255, 255, 255));
		btnLogin.setBounds(270, 194, 117, 37);
		contentPane.add(btnLogin);

		JLabel label = new JLabel("");
		label.setBounds(35, 92, 157, 127);
		Image gmbr = new ImageIcon(this.getClass().getResource("ikon/gembok.png")).getImage();
		label.setIcon(new ImageIcon(gmbr));
		contentPane.add(label);

		pesan = new JLabel("");
		pesan.setForeground(new Color(255, 0, 0));
		pesan.setBounds(12, 231, 261, 28);
		contentPane.add(pesan);
	}


	//method buat ngecek data login
	public void cekLogin() {
		try {
			String sql = "select * from " + Koneksi.tabel_akun + " where nama = '" + textUser.getText() + "' and pass = '" + String.valueOf(textPass.getPassword()) + "'"; //ambil semua data dari user yang cocok di tabel siparkir.akun
			Connection konek = Koneksi.getKoneksi(); //konek
			Statement siapin = konek.createStatement(); //siapin perintanya
			ResultSet hasil = siapin.executeQuery(sql); //jalanin perintahnya

			while(hasil.next()) userAda++; //jika berhasil dan selama data 'hasil' masih ada (has next)

			if(userAda == 1) { //cocok dan cuma ada satu
				super.dispose(); //umpetin form login
				Utama u = new Utama();
				u.setVisible(true);
			} else if(userAda > 1) { //cocok tapi lebih dari satu
				JOptionPane.showMessageDialog(null, "Waduw, user dengan data login ini kok banyak amat??", "Kesalahan", JOptionPane.ERROR_MESSAGE);
			} else { //nggak ada yang cocok
				pesan.setText("Username atau password salah!");
				textUser.setText("");
				textPass.setText("");
			}
			hasil.close(); //hentikan query
			siapin.close(); //tutup statement
		} catch (Exception ex) {//gagal
			JOptionPane.showMessageDialog(null, ex, "Kesalahan", JOptionPane.ERROR_MESSAGE); //print errornya ke message dialog
		}
	}
}
