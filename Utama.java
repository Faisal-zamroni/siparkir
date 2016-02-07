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
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import java.awt.SystemColor;
import java.awt.event.ActionListener;
import java.sql.*;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

@SuppressWarnings("serial")
public class Utama extends JFrame {

	private JPanel contentPane;
	private JTextField txtNopol;
	@SuppressWarnings("rawtypes")
	private JComboBox cmbSortir;
	private JTable table;
	final JLabel lblTerpakai, lblTotalTempat, lblTersedia;
	int TotalTempat = 100,
			jmlData = 0;

	String header[] = {"ID", "Nomor Polisi", "Tipe Kendaraan", "Tgl. Masuk"};
	String tipe[] = {"Motor", "Mobil"};
	DefaultTableModel tableModel;

	@SuppressWarnings("rawtypes")
	JComboBox cmbTipe;
	private JTextField txtID;
	private JTextField txtTanggal;
	private JTextField txtCari;


	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Utama frame = new Utama();
					frame.setVisible(true);
				} catch (Exception ex) {
					JOptionPane.showMessageDialog(null, ex); //print errornya ke message dialog
				}
			}
		});
	}


	@SuppressWarnings({ "rawtypes", "unchecked" })
	public Utama() {
		setTitle("SIParkir Unsika");
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 778, 600);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(255, 255, 255));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JPanel panel = new JPanel();
		panel.setLayout(null);
		panel.setBackground(new Color(0, 153, 204));
		panel.setBounds(0, 0, 776, 80);
		contentPane.add(panel);

		JLabel label = new JLabel("Sistem Informasi Parkir Unsika");
		label.setHorizontalAlignment(SwingConstants.CENTER);
		label.setForeground(Color.WHITE);
		label.setFont(new Font("Dialog", Font.BOLD, 20));
		label.setBounds(216, 4, 373, 37);
		panel.add(label);

		JLabel label_1 = new JLabel("Oleh Kelompok 1");
		label_1.setHorizontalAlignment(SwingConstants.CENTER);
		label_1.setForeground(Color.WHITE);
		label_1.setBounds(268, 41, 239, 15);
		panel.add(label_1);

		JLabel lblNomorPolisi = new JLabel("Nomor Polisi: ");
		lblNomorPolisi.setBounds(24, 92, 109, 15);
		contentPane.add(lblNomorPolisi);

		JLabel lblTipeKendaraan = new JLabel("Tipe Kendaraan: ");
		lblTipeKendaraan.setBounds(24, 140, 143, 15);
		contentPane.add(lblTipeKendaraan);

		txtNopol = new JTextField();
		txtNopol.setBounds(192, 92, 356, 27);
		contentPane.add(txtNopol);
		txtNopol.setColumns(10);

		cmbTipe = new JComboBox();
		cmbTipe.setModel(new DefaultComboBoxModel(new String[] {"Motor", "Mobil"}));
		cmbTipe.setBounds(192, 134, 356, 27);
		contentPane.add(cmbTipe);

		JButton btnSimpan = new JButton("Masuk");
		btnSimpan.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				//aksi ketika btnSimpan diklik
				String tipenya = "";
				//baca inputan cmbTipe
				if(cmbTipe.getSelectedIndex() == 0)      tipenya = "MOTOR";
				else if(cmbTipe.getSelectedIndex() == 1) tipenya = "MOBIL";

				//coba insert data
				try {
					if(getJmlData() < TotalTempat) {
						Connection konek = Koneksi.getKoneksi(); //konek ke mysql
						String sql = "insert into " + Koneksi.tabel_parkir + "(nopol, tipe, tgl_masuk, keluar, tgl_keluar) values(?, ?, ?, ? , ?)"; //perintah sql buat insert datanya
						PreparedStatement siapin = konek.prepareStatement(sql); //siapin perintahnya
						siapin.setString(1, txtNopol.getText().toUpperCase()); //isi parameter 1 "values(1,?,?)" dengan inputan txtNopol
						siapin.setString(2, tipenya); //isi parameter 1 "values(?,2,?)" dengan inputan cmbTipe
						siapin.setString(3, Koneksi.getTanggal()); ////isi parameter 1 "values(?,?,3)" dengan tanggal saat ini
						siapin.setString(4, "tdk");
						siapin.setString(5, "-");
						//ok, saatnya insert data ke mysql
						siapin.executeUpdate();
					JOptionPane.showMessageDialog(null, "Data berhasil ditambahkan!"); //kasih pesan berhasil
					txtNopol.setText("");
					} else {
						JOptionPane.showMessageDialog(null, "Parkiran sudah penuh!"); //kasih pesan gagal
					}
					lblTotalTempat.setText("Total tempat: " + TotalTempat);
					lblTerpakai.setText("Tempat Terpakai: " + getJmlData());
					lblTersedia.setText("Tempat tersedia: " + (TotalTempat - getJmlData()));
				} catch (Exception ex) { //gagal
					JOptionPane.showMessageDialog(null, "Data gagal ditambahkan!"); //kasih pesan gagal
					JOptionPane.showMessageDialog(null, ex); //print errornya ke message dialog
				}
				finally { //setelah diinsert, langsung tampilin datanya ke tabel guinya
					getDataTable(); //methodnya saya taruh dibawah ya.. :)
					getJmlData();
				}
			}
		});
		btnSimpan.setBackground(new Color(51, 153, 51));
		btnSimpan.setForeground(new Color(255, 255, 255));
		btnSimpan.setBounds(24, 188, 117, 32);
		contentPane.add(btnSimpan);

		JButton btnUbah = new JButton("Ubah");
		btnUbah.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				//aksi petika btnEdit diklik
				String tipe = "";
				if(cmbTipe.getSelectedIndex() == 0)      tipe = "MOTOR";
				else if(cmbTipe.getSelectedIndex() == 1) tipe = "MOBIL";

				try {
					Connection konek = Koneksi.getKoneksi();
					String sql = "update " + Koneksi.tabel_parkir + " set nopol = ?, tipe = ?, keluar = ?, tgl_keluar = ? where id = ?";
					PreparedStatement siapin = konek.prepareStatement(sql);
					siapin.setString(1, txtNopol.getText().toUpperCase());
					siapin.setString(2, tipe);
					siapin.setString(3, "tdk");
					siapin.setString(4, "-");
					siapin.setString(5, txtID.getText());

					siapin.executeUpdate();
					JOptionPane.showMessageDialog(null, "Data berhasil diupdate!");
					getDataTable();
					siapin.close();
					txtNopol.setText("");
					txtID.setText("");
					txtTanggal.setText("");

					lblTotalTempat.setText("Total tempat: " + TotalTempat);
					lblTerpakai.setText("Tempat Terpakai: " + getJmlData());
					lblTersedia.setText("Tempat tersedia: " + (TotalTempat - getJmlData()));

				} catch(Exception ex) {
					JOptionPane.showMessageDialog(null, "Data gagal diedit!");
					JOptionPane.showMessageDialog(null, ex); //print errornya ke message dialog
				}
				finally {
					getDataTable();
				}
			}
		});
		btnUbah.setForeground(new Color(255, 255, 255));
		btnUbah.setBackground(new Color(204, 0, 153));
		btnUbah.setBounds(153, 188, 117, 32);
		contentPane.add(btnUbah);

		JButton btnKeluar = new JButton("Keluar");
		btnKeluar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//aksi saat btnKeluar diklik
				try {
					Connection konek = Koneksi.getKoneksi();
					String sql = "update " + Koneksi.tabel_parkir + " set keluar= ?, tgl_keluar= ? where id= ?";
					PreparedStatement siapin = konek.prepareStatement(sql);
					siapin.setString(1, "ya");
					siapin.setString(2, Koneksi.getTanggal());
					siapin.setString(3, txtID.getText());

					siapin.executeUpdate();
					JOptionPane.showMessageDialog(null, "Kendaraan telah keluar");
					txtID.setText("");
					txtTanggal.setText("");
				} catch(Exception ex) {
					JOptionPane.showMessageDialog(null, "Kendaraan gagal keluar!");
					JOptionPane.showMessageDialog(null, ex); //print errornya ke message dialog
				}
				finally {
					getDataTable();
					getJmlData();
					lblTotalTempat.setText("Total tempat: " + TotalTempat);
					lblTerpakai.setText("Tempat Terpakai: " + getJmlData());
					lblTersedia.setText("Tempat tersedia: " + (TotalTempat - getJmlData()));
				}
			}
		});
		btnKeluar.setBackground(new Color(255, 0, 0));
		btnKeluar.setForeground(new Color(255, 255, 255));
		btnKeluar.setBounds(431, 188, 117, 32);
		contentPane.add(btnKeluar);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(24, 276, 727, 240);
		contentPane.add(scrollPane);

		tableModel = new DefaultTableModel(null, header);
		table = new JTable();
		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				getData();
			}
		});
		table.setModel(tableModel);
		scrollPane.setViewportView(table);

		JPanel panel_1 = new JPanel();
		panel_1.setBackground(SystemColor.window);
		panel_1.setBounds(576, 92, 175, 128);
		contentPane.add(panel_1);
		panel_1.setLayout(null);

		lblTotalTempat = new JLabel("");
		lblTotalTempat.setBounds(12, 49, 151, 22);
		panel_1.add(lblTotalTempat);

		lblTerpakai = new JLabel("");
		lblTerpakai.setBounds(12, 77, 151, 22);
		panel_1.add(lblTerpakai);

		lblTersedia = new JLabel("");
		lblTersedia.setBounds(12, 101, 151, 22);
		panel_1.add(lblTersedia);

		txtID = new JTextField();
		txtID.setHorizontalAlignment(SwingConstants.CENTER);
		txtID.setEditable(false);
		txtID.setBounds(24, 232, 60, 32);
		contentPane.add(txtID);
		txtID.setColumns(10);

		txtTanggal = new JTextField();
		txtTanggal.setEditable(false);
		txtTanggal.setColumns(10);
		txtTanggal.setBounds(96, 232, 174, 32);
		contentPane.add(txtTanggal);
		lblTotalTempat.setText("Total tempat: " + TotalTempat);
		lblTerpakai.setText("Tempat Terpakai: " + getJmlData());
		lblTersedia.setText("Tempat tersedia: " + (TotalTempat - getJmlData()));
		getDataTable(); //biar langsung dibaca data dari dbnya pas app dibuka

		JLabel lblStatistik = new JLabel("STATISTIK");
		lblStatistik.setBounds(12, 12, 70, 15);
		panel_1.add(lblStatistik);

		JLabel label_2 = new JLabel("-----------------------------");
		label_2.setBounds(12, 22, 151, 15);
		panel_1.add(label_2);

		txtCari = new JTextField();
		txtCari.setColumns(10);
		txtCari.setBounds(24, 522, 233, 32);
		contentPane.add(txtCari);

		JButton btnCari = new JButton("Cari");
		btnCari.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				//aksi saat btnCari diklik
				getDataCari();
			}
		});
		btnCari.setForeground(Color.WHITE);
		btnCari.setBackground(Color.ORANGE);
		btnCari.setBounds(262, 521, 78, 32);
		contentPane.add(btnCari);

		cmbSortir = new JComboBox();
		cmbSortir.setBounds(472, 234, 183, 30);
		cmbSortir.setModel(new DefaultComboBoxModel(new String[] {"Semua data", "Motor", "Mobil"}));
		contentPane.add(cmbSortir);
		JButton btnSortir = new JButton("Sortir");
		btnSortir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				//ketika btnSortir di klik
				getDataSortir();
			}
		});
		btnSortir.setForeground(Color.WHITE);
		btnSortir.setBackground(Color.ORANGE);
		btnSortir.setBounds(667, 232, 84, 32);
		contentPane.add(btnSortir);

		JButton btnDataKendaraanKeluar = new JButton("Parkir Keluar");
		btnDataKendaraanKeluar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				//aksi ketika btn parkir keluar diklik
				DataKeluar dk = new DataKeluar();
				dk.setVisible(true);
				dk.setAutoRequestFocus(true);
			}
		});
		btnDataKendaraanKeluar.setForeground(Color.WHITE);
		btnDataKendaraanKeluar.setBackground(new Color(102, 0, 51));
		btnDataKendaraanKeluar.setBounds(385, 521, 126, 32);
		contentPane.add(btnDataKendaraanKeluar);

		JButton btnPanduan = new JButton("Panduan");
		btnPanduan.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//aksi saat btnPanduan diklik
				Panduan p = new Panduan();
				p.setVisible(true);
			}
		});
		btnPanduan.setForeground(Color.WHITE);
		btnPanduan.setBackground(new Color(0, 153, 204));
		btnPanduan.setBounds(523, 521, 108, 32);
		contentPane.add(btnPanduan);

		JButton btnTentang = new JButton("Tentang");
		btnTentang.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				TentangApp t = new TentangApp();
				t.setVisible(true);
			}
		});
		btnTentang.setForeground(Color.WHITE);
		btnTentang.setBackground(new Color(0, 153, 204));
		btnTentang.setBounds(643, 521, 108, 32);
		contentPane.add(btnTentang);
	}



	//method getData buat ngambil data dari mysql dan nampilin di txtNopol dan cmbTipe (buat ngedit data maksud saya.)
	public void getData() {
		int pilih = table.getSelectedRow(); //ambil area baris tabel yang di klik
		if(pilih == -1) return; //kalo nggak ada yg diklik, kasih return value null

		Integer id = Integer.parseInt(table.getValueAt(pilih, 0).toString());
		txtID.setText("" + id);
		String nopol = (String) tableModel.getValueAt(pilih, 1); //ambil objek pada baris yang di klik di indeks kolom ke 1 (kolom kedua (kolom Nomor Polisi))
		txtNopol.setText(nopol); //isikan ke txtNopol
		String tipe = (String) tableModel.getValueAt(pilih, 2); //ambil objek pada baris yang di klik di indeks kolom ke 2 (kolom ketiga (kolom Tipe Kendaraan))
		if(tipe.equals("MOBIL"))      tipe = "Mobil";
		else if(tipe.equals("MOTOR")) tipe = "Motor";
		cmbTipe.setSelectedItem(tipe); //isikan ke cmbNopol
		String tanggal = (String) tableModel.getValueAt(pilih, 3);
		txtTanggal.setText(tanggal);
	}


	//buat ngambil jumlah semua data (nanti buat nge-set jumlah stok parkir)
	public int getJmlData() {
		try {
			String sql = "select count(*) as jumlahnya from " + Koneksi.tabel_parkir + " where keluar = 'tdk'";
			Connection konek = Koneksi.getKoneksi();
			Statement siapin = konek.createStatement();
			ResultSet hasil = siapin.executeQuery(sql);
			while(hasil.next()) {
				jmlData = hasil.getInt("jumlahnya");
			}
			return  jmlData;

		} catch(Exception ex) {
			JOptionPane.showMessageDialog(null, ex); //print errornya ke message dialog
		}
	}


	public void getDataTable() {
		tableModel.getDataVector().removeAllElements();
		tableModel.fireTableDataChanged();
		try {
			String sql = "select * from " + Koneksi.tabel_parkir + " where keluar = 'tdk'"; //ambil semua data dari tabel siparkir
			Connection konek = Koneksi.getKoneksi(); //konek
			Statement siapin = konek.createStatement(); //siapin perintanya
			ResultSet hasil = siapin.executeQuery(sql); //jalanin perintahnya

			while(hasil.next()) { //jika berhasil dan selama data 'hasil' masih ada (has next)
				Object data[] = new Object[4]; //bikin data objek, dia array, jumlahnya 4, dinamai 'data'. nanti buat nampung hasil select dari mysql
				//yuk, ambil datanya dari mysql
				data[0] = hasil.getInt(1); //ini baris id, dia auto-increment ya..
				data[1] = hasil.getString(2); //ini nopol
				data[2] = hasil.getString(3); //ini tipe
				data[3] = hasil.getString(4); //ini tgl_masuk
				tableModel.addRow(data); //tambahin ke tabel gui kita
			}
			hasil.close(); //hentikan query
			siapin.close(); //tutup statement
		} catch (Exception ex) {//gagal
			JOptionPane.showMessageDialog(null, ex); //print errornya ke message dialog
		}
	}


	//cariiiiiiiiii
	public void getDataCari() {
		tableModel.getDataVector().removeAllElements();
		tableModel.fireTableDataChanged();
		try {
			String sql = "select * from " + Koneksi.tabel_parkir + " where nopol like '%" + txtCari.getText() + "%' or tipe like '%" + txtCari.getText().toUpperCase() + "%' and keluar='tdk'"; //ambil semua data dari tabel siparkir
			Connection konek = Koneksi.getKoneksi(); //konek
			Statement siapin = konek.createStatement(); //siapin perintanya
			ResultSet hasil = siapin.executeQuery(sql); //jalanin perintahnya

			while(hasil.next()) { //jika berhasil dan selama data 'hasil' masih ada (has next)
				Object data[] = new Object[4]; //bikin data objek, dia array, jumlahnya 4, dinamai 'data'. nanti buat nampung hasil select dari mysql
				//yuk, ambil datanya dari mysql
				data[0] = hasil.getInt(1); //ini baris id, dia auto-increment ya..
				data[1] = hasil.getString(2); //ini nopol
				data[2] = hasil.getString(3); //ini tipe
				data[3] = hasil.getString(4); //ini tgl_masuk

				tableModel.addRow(data); //tambahin ke tabel gui kita
			}
			hasil.close(); //hentikan query
			siapin.close(); //tutup statement
		} catch (Exception ex) {//gagal
			JOptionPane.showMessageDialog(null, ex); //print errornya ke message dialog
		}
	}


	//sortirrrrrr
		public void getDataSortir() {
			tableModel.getDataVector().removeAllElements();
			tableModel.fireTableDataChanged();
			try {
				String sql = "";
				if(cmbSortir.getSelectedIndex() == 0)      sql = "select * from " + Koneksi.tabel_parkir + " where keluar = 'tdk'"; //ambil semua data dari tabel yang kolom 'keluar'-nya berisi "tdk"
				else if(cmbSortir.getSelectedIndex() == 1) sql = "select * from " + Koneksi.tabel_parkir + " where tipe =  'MOTOR' and keluar = 'tdk'"; //ambil semua data dari tabel parkir yang motor
				else if(cmbSortir.getSelectedIndex() == 2) sql = "select * from " + Koneksi.tabel_parkir + " where tipe =  'MOBIL' and keluar = 'tdk'"; //ambil semua data dari tabel parkir yang motor
				Connection konek = Koneksi.getKoneksi(); //konek
				Statement siapin = konek.createStatement(); //siapin perintanya
				ResultSet hasil = siapin.executeQuery(sql); //jalanin perintahnya

				while(hasil.next()) { //jika berhasil dan selama data 'hasil' masih ada (has next)
					Object data[] = new Object[4]; //bikin data objek, dia array, jumlahnya 4, dinamai 'data'. nanti buat nampung hasil select dari mysql
					//yuk, ambil datanya dari mysql
					data[0] = hasil.getInt(1); //ini baris id, dia auto-increment ya..
					data[1] = hasil.getString(2); //ini nopol
					data[2] = hasil.getString(3); //ini tipe
					data[3] = hasil.getString(4); //ini tgl_masuk

					tableModel.addRow(data); //tambahin ke tabel gui kita
				}
				hasil.close(); //hentikan query
				siapin.close(); //tutup statement
			} catch (Exception ex) {//gagal
				JOptionPane.showMessageDialog(null, ex); //print errornya ke message dialog
			}
		}
}
