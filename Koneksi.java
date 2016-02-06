import java.sql.*;
import java.sql.DriverManager;
import java.util.Date;
import java.text.SimpleDateFormat;
import javax.swing.JOptionPane;

public class Koneksi {
	private static Connection koneksi;
	/**
	 * Setting database mysql
	 * Sesuaikan dengan data Anda
	* **/
	public static String db_host      = "localhost", //host dbnya
											 db_nama      = "siparkir",  //nama dbnya
											 db_user      = "root",      //user dbnya
											 db_pass      = "",          //pass dbnya
											 url          = "jdbc:mysql://" + db_host +"/" + db_nama; //url dbnya

	 /**
 	 * Ini buat custom tabel di database
 	 * Jika ini di edit, edit juga data pada berkas 'siparkir.sql'
 	 * **/
	public static String tabel_akun   = "akun",      //tabel akun user
											 tabel_parkir = "parkir";    //tabel parkir kendaraan



	public static Connection getKoneksi() {
		if(koneksi == null) {
			try {
				DriverManager.registerDriver(new com.mysql.jdbc.Driver()); //reg. koneksinya
				koneksi = DriverManager.getConnection(url, db_user, db_pass); //coba konekin
			} catch (Exception ex) {
				JOptionPane.showMessageDialog(null, ex); //print errornya ke message dialog
			}
		}
		return koneksi; //return hasil coba koneksi. konek atau gagal?
	}


	/**
	 * Method buat bikin tanggal pake bahasa indo
	 * biar lebih indo aja sih :p
	 * Metodenya dengan mengganti string 'nama hari' berbahasa inggris di string tanggal
	 * dengan padanannya di bahasa indo
	 * **/
	public static  String getTanggal() {
		Date tgl = new Date();
		SimpleDateFormat formatnya = new SimpleDateFormat("E dd/MM/yyyy hh:mm:ss"); //misal: Sun 30/10/2016 11:30:25 (ultah saya dong :P)
		String tanggalnya = formatnya.format(tgl); //simpen dulu, nanti dimodif

		//yuk, modif
		String tggl[] = tanggalnya.split(" ");
		String hr     = tggl[0], //hari
					 tbt    = tggl[1], //tanggal/bulan/tahun
					 jms    = tggl[2]; //jam:menit:detik
		String hari = "";
		switch(hr) {
			case "Sun" : hari = "Min"; break;
			case "Mon" : hari = "Sen"; break;
			case "Tue" : hari = "Sel"; break;
			case "Wed" : hari = "Rab"; break;
			case "Thu" : hari = "Kam"; break;
			case "Fri" : hari = "Jum"; break;
			case "Sat" : hari = "Sab"; break;
			default:;
		}
		return hari + ", " + tbt + " " + jms; //jadinya: Min, 30/10/2016 11:30:25 (ultah saya lagi dong :p)
	}
}
