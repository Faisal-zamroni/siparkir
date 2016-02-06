import javax.swing.JOptionPane;
import java.sql.*;

public class SiParkir {

	public static void main(String[] args) {
		try {
			Connection konek = Koneksi.getKoneksi();
			if(!konek.isClosed()) { //cek koneksi mysql
				Login login = new Login(); //bikin objek login buat dipanggil pertama kali
				login.setVisible(true); //panggil!
			}
		} catch (Exception ex) {
			JOptionPane.showMessageDialog(null, "Koneksi ke database gagal.\nMySQLnya udah diidupin belum?", "Kesalahan", JOptionPane.ERROR_MESSAGE);
		}
	}

}
