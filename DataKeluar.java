import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.Color;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import java.awt.Font;
import javax.swing.SwingConstants;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import java.awt.event.ActionListener;
import java.sql.*;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;



@SuppressWarnings("serial")
class DataKeluar extends JFrame {
//Deklarasi objek2 publik
	private JPanel contentPane; //panel
	private JTable table; //tabel

	String header[] = {"ID", "Nomor Polisi", "Tipe Kendaraan", "Tgl. Masuk", "Tgl. Keluar"}; //array string buat nama kolom tabel
	String tipe[] = {"Motor", "Mobil"}; //array string buat combo box
	DefaultTableModel tableModel; //model tabelnya
	private JTextField txtCari; //textbox cari
	String id, nopol, tipenya;
	private JTextField textNopol; //sda
	private JTextField textID; //sda



	public static void main(String[] args) { //main method buat njalanin scriptnya
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					DataKeluar frame = new DataKeluar();
					frame.setVisible(true);
				} catch (Exception ex) {
				JOptionPane.showMessageDialog(null, ex); //print errornya ke message dialog
				}
			}
		});
	}



	public DataKeluar() { //method utama DataKeluar
		setTitle("Data Keluar"); //set judul frame
		setResizable(false); //ilangin tombol maximize
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); //saat di-close, dia ngumpet :)
		setBounds(100, 100, 980, 540); //nyetting lokasi frame (bacanya searah jarum jam ya)
		contentPane = new JPanel(); //bikin panel
		contentPane.setBackground(new Color(255, 255, 255)); //set bekgron panelnya (rgb putih)
		contentPane.setBorder(new EmptyBorder(5, 5, 265, 5)); //set border
		setContentPane(contentPane); //tampilin
		contentPane.setLayout(null); //layout default

		JPanel panel = new JPanel();
		panel.setLayout(null);
		panel.setBackground(new Color(0, 153, 204));
		panel.setBounds(0, 0, 978, 80);
		contentPane.add(panel);

		JLabel label = new JLabel("Sistem Informasi Parkir Unsika");
		label.setHorizontalAlignment(SwingConstants.CENTER);
		label.setForeground(Color.WHITE);
		label.setFont(new Font("Dialog", Font.BOLD, 20));
		label.setBounds(308, 0, 373, 37);
		panel.add(label);

		JLabel label_1 = new JLabel("Oleh Kelompok 1");
		label_1.setHorizontalAlignment(SwingConstants.CENTER);
		label_1.setForeground(Color.WHITE);
		label_1.setBounds(359, 39, 239, 15);
		panel.add(label_1);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(22, 138, 924, 310);
		contentPane.add(scrollPane);

		tableModel = new DefaultTableModel(null, header);
		table = new JTable();
		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				//aksi ketika salah satu baris dalam tabel diklik
				getBarisHapus();
			}
		});

		table.setModel(tableModel);
		scrollPane.setViewportView(table);

		JLabel lblNewLabel = new JLabel("Data Kendaraan Keluar");
		lblNewLabel.setFont(new Font("Dialog", Font.BOLD, 20));
		lblNewLabel.setBounds(22, 92, 298, 34);
		contentPane.add(lblNewLabel);

		JButton btnHapus = new JButton("Hapus");
		btnHapus.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				//aksi ketika btn hapus diklik
				try {
					Connection konek = Koneksi.getKoneksi();
					String sql = "delete from " + Koneksi.tabel_parkir + " where id =" + textID.getText();
					PreparedStatement siapin = konek.prepareStatement(sql);

					//NANTI DIKASIH PESAN KONFIRMASI
					int jawab = JOptionPane.showConfirmDialog(null, "Apakah anda ingin menghapus data ini?\n\n Nopol: " + nopol + "\nTipe: " + tipenya + "\n\n", "Konfirmasi",  JOptionPane.YES_NO_OPTION);
					if(jawab == 0) {
						siapin.executeUpdate();
						JOptionPane.showMessageDialog(null, "Kendaraan berhasil dihapus!");
						textID.setText("");
						textNopol.setText("");
					} else {
					JOptionPane.showMessageDialog(null, "Operasi dibatalkan");
					}
				} catch(Exception ex) {
				JOptionPane.showMessageDialog(null, ex); //print errornya ke message dialog
				}
				finally {
					getAllDataKeluar();
				}
			}
		});
		btnHapus.setForeground(Color.WHITE);
		btnHapus.setBackground(new Color(102, 0, 102));
		btnHapus.setBounds(22, 460, 117, 32);
		contentPane.add(btnHapus);

		JButton btnHapusSemua = new JButton("Hapus Semua Data");
		btnHapusSemua.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				//aksi ketika tombol hapus semua data diklik
				try {
					Connection konek = Koneksi.getKoneksi();
					String sql = "delete from " + Koneksi.tabel_parkir + " where keluar = 'ya'";
					PreparedStatement siapin = konek.prepareStatement(sql);

					//pesan konfirmasi
					int jawab = JOptionPane.showConfirmDialog(null, "Apakah anda ingin menghapus semua data?\n\nJika anda memilih YA, Semua data kendaraan keluar\nakan dihapus dari database", "Konfirmasi",  JOptionPane.YES_NO_OPTION);
					if(jawab == 0) {
						siapin.executeUpdate();
						JOptionPane.showMessageDialog(null, "Semua data kendaraan berhasil dihapus!");
					} else {
					JOptionPane.showMessageDialog(null, "Operasi dibatalkan");
					textID.setText("");
					textNopol.setText("");
					}
					textID.setText("");
					textNopol.setText("");
				} catch(Exception ex) {
				JOptionPane.showMessageDialog(null, ex); //print errornya ke message dialog
				}
				finally {
					getAllDataKeluar();
				}
			}
		});
		btnHapusSemua.setForeground(Color.WHITE);
		btnHapusSemua.setBackground(new Color(204, 51, 51));
		btnHapusSemua.setBounds(764, 94, 182, 32);
		contentPane.add(btnHapusSemua);

		txtCari = new JTextField();
		txtCari.setColumns(10);
		txtCari.setBounds(572, 461, 172, 32);
		contentPane.add(txtCari);

		JButton btnCari = new JButton("Cari");
		btnCari.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				//aksi ketika tombol cari diklik
				getDataCari();
				textID.setText("");
				textNopol.setText("");
			}
		});
		btnCari.setForeground(Color.WHITE);
		btnCari.setBackground(Color.ORANGE);
		btnCari.setBounds(752, 460, 78, 32);
		contentPane.add(btnCari);

		JLabel lblNewLabel_1 = new JLabel("Cari Berdasarkan Nomor Polisi:");
		lblNewLabel_1.setBounds(334, 460, 240, 32);
		contentPane.add(lblNewLabel_1);

		textNopol = new JTextField();
		textNopol.setHorizontalAlignment(SwingConstants.CENTER);
		textNopol.setEditable(false);
		textNopol.setColumns(10);
		textNopol.setBounds(190, 460, 108, 32);
		contentPane.add(textNopol);

		JButton btnRefresh = new JButton("Refresh");
		btnRefresh.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				//aksi saat btnRefresh diklik
				getAllDataKeluar();
				textID.setText("");
				textNopol.setText("");
			}
		});
		btnRefresh.setForeground(Color.WHITE);
		btnRefresh.setBackground(new Color(0, 153, 51));
		btnRefresh.setBounds(842, 460, 104, 32);
		contentPane.add(btnRefresh);

		textID = new JTextField();
		textID.setHorizontalAlignment(SwingConstants.CENTER);
		textID.setEditable(false);
		textID.setColumns(10);
		textID.setBounds(151, 460, 32, 32);
		contentPane.add(textID);
		getAllDataKeluar();
	}


	public void getAllDataKeluar() {
		tableModel.getDataVector().removeAllElements();
		tableModel.fireTableDataChanged();
		try {
			String sql = "select * from " + Koneksi.tabel_parkir + " where keluar = 'ya'"; //ambil semua data dari tabel siparkir
			Connection konek = Koneksi.getKoneksi(); //konek
			Statement siapin = konek.createStatement(); //siapin perintanya
			ResultSet hasil = siapin.executeQuery(sql); //jalanin perintahnya

			while(hasil.next()) { //jika berhasil dan selama data 'hasil' masih ada (has next)
				Object data[] = new Object[5]; //bikin data objek, dia array, jumlahnya 5, dinamai 'data'. nanti buat nampung hasil select dari mysql
				//yuk, ambil datanya dari mysql
				data[0] = hasil.getInt(1); //ini baris id, dia auto-increment ya..
				data[1] = hasil.getString(2); //ini nopol
				data[2] = hasil.getString(3); //ini tipe
				data[3] = hasil.getString(4); //ini tgl_masuk
				//hasil ke-5 di skip karena kolom 5 isinya cuma keluar=ya atau keluar=tdk
				data[4] = hasil.getString(6); // ini tgl keluar

				tableModel.addRow(data); //tambahin ke tabel gui kita
			}
			hasil.close(); //hentikan query
			siapin.close(); //tutup statement
		} catch (Exception ex) {//gagal
			JOptionPane.showMessageDialog(null, ex); //print errornya ke message dialog
		}
	}


	//cariiiiiiii
	public void getDataCari() {
		tableModel.getDataVector().removeAllElements();
		tableModel.fireTableDataChanged();
		try {
			String sql = "select * from " + Koneksi.tabel_parkir + " where nopol like '%" + txtCari.getText() + "%' and keluar='ya'"; //ambil semua data dari tabel siparkir
			Connection konek = Koneksi.getKoneksi(); //konek
			Statement siapin = konek.createStatement(); //siapin perintanya
			ResultSet hasil = siapin.executeQuery(sql); //jalanin perintahnya

			while(hasil.next()) { //jika berhasil dan selama data 'hasil' masih ada (has next)
				Object data[] = new Object[5]; //bikin data objek, dia array, jumlahnya 4, dinamai 'data'. nanti buat nampung hasil select dari mysql
				//yuk, ambil datanya dari mysql
				data[0] = hasil.getInt(1); //ini baris id, dia auto-increment ya..
				data[1] = hasil.getString(2); //ini nopol
				data[2] = hasil.getString(3); //ini tipe
				data[3] = hasil.getString(4); //ini tgl_masuk
				data[4] = hasil.getString(6); // ini tgl keluar

				tableModel.addRow(data); //tambahin ke tabel gui kita
			}
			hasil.close(); //hentikan query
			siapin.close(); //tutup statement
		} catch (Exception ex) {//gagal
			JOptionPane.showMessageDialog(null, ex); //print errornya ke message dialog
		}
	}



	//method getData buat ngambil data dari mysql dan nampilin di txtNopol dan cmbTipe (buat ngedit data maksud saya.)
	public void getBarisHapus() {
		int pilih = table.getSelectedRow(); //ambil area baris tabel yang di klik
		if(pilih == -1) {
			return; //kalo nggak ada yg diklik, kasih return value null
		}

		Integer id = Integer.parseInt(table.getValueAt(pilih, 0).toString());
		nopol   = (String) tableModel.getValueAt(pilih, 1); //nopol
		tipenya = (String) tableModel.getValueAt(pilih, 2); //tipe
		textNopol.setText(nopol);
		textID.setText("" + id);
	}
}
