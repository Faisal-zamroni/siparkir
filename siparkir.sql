create table if not exists `akun` (
	`id` int(11) not null auto_increment,
	`nama` varchar(20) not null,
	`pass` varchar(20) not null,
	primary key(`id`)
) engine=InnoDB default charset=latin1;



insert into `akun` (`id`, `nama`, `pass`) values
	(1, 'suyadi', 'suyadi'),
	(2, 'arif', 'arif'),
	(3, 'ardi', 'ardi'),
	(4, 'oki', 'oki'),
	(5, 'weni', 'weni');


create table if not exists `parkir` (
	`id` int(11) not null auto_increment,
	`nopol` varchar(20) not null,
	`tipe` varchar(20) not null,
	`tgl_masuk` varchar(50) not null,
	`keluar` enum('ya', 'tdk') not null default 'tdk',
	`tgl_keluar` varchar(50) not null,
	primary key(`id`)
) engine=InnoDB default charset=latin1;



insert into `parkir` (`id`, `nopol`, `tipe`, `tgl_masuk`, `keluar`, `tgl_keluar`) values 
	(1, 'AE 3132 TE', 'MOBIL', 'Min, 12/01/2016 04:00:01', 'tdk', '-'),
	(2, 'T 6612 JV', 'MOBIL', 'Min, 12/01/2016 04:00:02', 'tdk', '-'),
	(3, 'B 3132 CNW', 'MOTOR', 'Min, 12/01/2016 04:00:03', 'tdk', '-'),
	(4, 'AD 1022 FE', 'MOBIL', 'Min, 12/01/2016 04:00:04', 'tdk', '-'),
	(5, 'B 5122 TX', 'MOTOR', 'Min, 12/01/2016 04:00:11', 'tdk', '-'),
	(6, 'S 3900 NS', 'MOBIL', 'Min, 12/01/2016 04:00:21', 'ya', '12/01/2016 04:01:01'),
	(7, 'AB 1111 DA', 'MOTOR', 'Min, 12/01/2016 04:00:31', 'ya', '12/01/2016 04:02:33'),
	(8, 'F 2144 CX', 'MOTOR', 'Min, 12/01/2016 04:00:37', 'ya', '12/01/2016 04:08:00'),
	(9, 'N 8812 NC', 'MOBIL', 'Min, 12/01/2016 04:00:41', 'ya', '12/01/2016 04:09:01'),
	(10, 'A 7611 MK', 'MOTOR', 'Min, 12/01/2016 04:00:55', 'tdk', '-');
