CREATE DATABASE qldvvpkcm /*!40100 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci */ /*!80016 DEFAULT ENCRYPTION='N' */;

USE qldvvpkcm;

CREATE TABLE `loaihanghoa` (
  `loaihanghoa_id` int NOT NULL AUTO_INCREMENT,
  `tenloai` varchar(255) NOT NULL,
  
  PRIMARY KEY (`loaihanghoa_id`)
) ;

CREATE TABLE `loaiuser` (
  `loaiuser_id` int NOT NULL AUTO_INCREMENT,
  `tenloai` varchar(255) NOT NULL,
  
  PRIMARY KEY (`loaiuser_id`)
) ;

CREATE TABLE `user` (
  `user_id` int NOT NULL AUTO_INCREMENT,
  `hoten` varchar(255) NOT NULL,
  `ngaysinh` date NOT NULL,
  `gioitinh` varchar(4) NOT NULL,
  `cmnd` varchar(12) NOT NULL,
  `taikhoan` varchar(20) NOT NULL,
  `matkhau` varchar(16) NOT NULL,
  `ngayVaoLam` date NOT NULL,
  `email` varchar(255) NOT NULL,
  `diachi` varchar(255) NOT NULL,
  `sdt` varchar(11) NOT NULL,
  `trangthai` bool NOT NULL DEFAULT true,
  `loaiuser_id` int NOT NULL,
  
  PRIMARY KEY (`user_id`),
  KEY `FK_USER_LOAIUSER_idx` (`loaiuser_id`),
  CONSTRAINT `FK_USER_LOAIUSER` FOREIGN KEY (`loaiuser_id`) REFERENCES `loaiuser` (`loaiuser_id`)
) ;


CREATE TABLE `nhacungcap` (
  `nhacungcap_id` int NOT NULL AUTO_INCREMENT,
  `tencongty` varchar(255) NOT NULL,
  `diachi` varchar(255) NOT NULL,
  `tinhthanh` varchar(255) NOT NULL,
  `quocgia` varchar(255) NOT NULL,
  `email` varchar(255) NOT NULL,
  `sodt` varchar(11) NOT NULL,
  
  PRIMARY KEY (`nhacungcap_id`)
) ;

CREATE TABLE `hanghoa` (
  `hanghoa_id` int NOT NULL AUTO_INCREMENT,
  `tenhanghoa` varchar(255) NOT NULL,
  `thuonghieu` varchar(255) NOT NULL,
  `soluongtrongkho` int NULL,
  `gianiemyet` decimal(10,0) NOT NULL,
  `hinhanh` varchar(255) NULL,
  `tinhtrang` bool NOT NULL DEFAULT True,
  `loaihanghoa_id` int NULL,
  
  PRIMARY KEY (`hanghoa_id`),
  KEY `FK_HANGHOA_LOAIHANGHOA_idx` (`loaihanghoa_id`),
  CONSTRAINT `FK_HANGHOA_LOAIHANGHOA` FOREIGN KEY (`loaihanghoa_id`) REFERENCES `loaihanghoa` (`loaihanghoa_id`)
) ;

CREATE TABLE `nhacungcap_hanghoa` (
  `nhacungcap_id` int NOT NULL,
  `hanghoa_id` int NOT NULL,
  `soluong` int NOT NULL,
  `ngaynhap` datetime NOT NULL,
  `ngaysanxuat` date NULL,
  `ngayhethan` date NULL,
  `gianhap`  decimal(10,0) NOT NULL,
  `nhanvien_id` int NOT NULL,
  `ghichu` varchar(255) NULL,
  
  PRIMARY KEY (`nhacungcap_id`, `hanghoa_id`, `ngaynhap`),
  KEY `FK_NHACUNGCAP_HANGHOA_NHACUNGCAP_idx` (`nhacungcap_id`),
  KEY `FK_NHACUNGCAP_HANGHOA_HANGHOA_idx` (`hanghoa_id`),
  KEY `FK_NHACUNGCAP_HANGHOA_USER_idx` (`nhanvien_id`),
  CONSTRAINT `FK_NHACUNGCAP_HANGHOA_NHACUNGCAP` FOREIGN KEY (`nhacungcap_id`) REFERENCES `nhacungcap` (`nhacungcap_id`),
  CONSTRAINT `FK_NHACUNGCAP_HANGHOA_HANGHOA` FOREIGN KEY (`hanghoa_id`) REFERENCES `hanghoa` (`hanghoa_id`),
  CONSTRAINT `FK_NHACUNGCAP_HANGHOA_USER` FOREIGN KEY (`nhanvien_id`) REFERENCES `user` (`user_id`)
) ;

CREATE TABLE `khachhang` (
  `khachhang_id` int NOT NULL AUTO_INCREMENT,
  `hoten` varchar(255) NOT NULL,
  `ngaysinh` date NOT NULL,
  `gioitinh` varchar(4) NOT NULL,
  `diachi` varchar(255) NOT NULL,
  `sdt` varchar(11) NOT NULL,
  `diemtichluy` decimal NOT NULL DEFAULT 0,
  
  PRIMARY KEY (`khachhang_id`)
  ) ;

CREATE TABLE `thucung` (
  `thucung_id` int NOT NULL AUTO_INCREMENT,
  `ten` varchar(255) NOT NULL,
  `ngaysinh` date NOT NULL,
  `gioitinh` varchar(4) NOT NULL,
  `mauLong` varchar(255) NOT NULL,
  `tinhtrangsuckhoe` varchar(255) NOT NULL,
  `khachhang_id` int NULL,
  
  PRIMARY KEY (`thucung_id`),
  KEY `FK_THUCUNG_KHACHHANG_idx` (`khachhang_id`),
  CONSTRAINT `FK_THUCUNG_KHACHHANG` FOREIGN KEY (`khachhang_id`) REFERENCES `khachhang` (`khachhang_id`)
  ) ;

CREATE TABLE `donhang` (
  `donhang_id` int NOT NULL AUTO_INCREMENT,
  `ngayTaoDH` datetime NOT NULL, #s????a
  `nhanvien_id` int NOT NULL,
  `khachhang_id` int NULL,
  
  PRIMARY KEY (`donhang_id`),
  KEY `FK_DONHANG_NHANVIEN_idx` (`nhanvien_id`),
  CONSTRAINT `FK_DONHANG_NHANVIEN` FOREIGN KEY (`nhanvien_id`) REFERENCES `user` (`user_id`),
  KEY `FK_DONHANG_KHACHHANG_idx` (`khachhang_id`),
  CONSTRAINT `FK_DONHANG_KHACHHANG` FOREIGN KEY (`khachhang_id`) REFERENCES `khachhang` (`khachhang_id`)
  ) ;
  
  CREATE TABLE `chitietdonhang` (
  `donhang_id` int NOT NULL,
  `hanghoa_id` int NOT NULL,
  `soluong` int NOT NULL,
  `dongia` decimal(10,0) NOT NULL,
  `giamgia` double NOT NULL DEFAULT 0,
  
  PRIMARY KEY (`donhang_id`, `hanghoa_id`),
  KEY `FK_CHITIETDONHANG_DONHANG_idx` (`donhang_id`),
  CONSTRAINT `FK_CHITIETDONHANG_DONHANG` FOREIGN KEY (`donhang_id`) REFERENCES `donhang` (`donhang_id`),
  KEY `FK_CHITIETDONHANG_HANGHOA_idx` (`hanghoa_id`),
  CONSTRAINT `FK_CHITIETDONHANG_HANGHOA` FOREIGN KEY (`hanghoa_id`) REFERENCES `hanghoa` (`hanghoa_id`)
  ) ;
  
  CREATE TABLE `capnhathoadon` (
  `donhang_id` int NOT NULL,
  `nhanvien_id` int NOT NULL,
  `ngaygiocapnhat` datetime NOT NULL,
  `ghichu` longtext NULL,
  
  PRIMARY KEY (`donhang_id`, `nhanvien_id`, `ngaygiocapnhat`),
  KEY `FK_CAPNHATHOADON_DONHANG_idx` (`donhang_id`),
  CONSTRAINT `FK_CAPNHATHOADON_DONHANG` FOREIGN KEY (`donhang_id`) REFERENCES `donhang` (`donhang_id`),
  KEY `FK_CAPNHATHOADON_NHANVIEN_idx` (`nhanvien_id`),
  CONSTRAINT `FK_CAPNHATHOADON_NHANVIEN` FOREIGN KEY (`nhanvien_id`) REFERENCES `user` (`user_id`)
  ) ;
  
  INSERT INTO `loaihanghoa` (`tenloai`) VALUES ("Di??ch vu??"),("Th????c ??n cho cho??"), ("Th????c ??n cho me??o"), ("Phu?? ki????n");
  
  INSERT INTO `loaiuser` (`tenloai`) VALUES ("Qua??n ly?? tr??????ng"), ("Thu?? kho"), ("Nh??n vi??n");
  
  INSERT INTO `user` (`hoten`,`ngaysinh`,`gioitinh`,`cmnd`,`taikhoan`,`matkhau`,`ngayVaoLam`,`email`,`diachi`,`sdt`,`trangthai`,`loaiuser_id`) VALUES
  ("Tr????ng Phong","1990-01-01","Nam","0222334455","truongphong","1","2018-03-02","truongphong@gmail.com","Qu????n 7, TP.HCM","0793278239",true,1);
  INSERT INTO `user` (`hoten`,`ngaysinh`,`gioitinh`,`cmnd`,`taikhoan`,`matkhau`,`ngayVaoLam`,`email`,`diachi`,`sdt`,`trangthai`,`loaiuser_id`) VALUES
  ("Pha??m Lu??n","1980-04-27","Nam","033445567788","phamluan","1","2018-03-02","phamluan@gmail.com","Go?? V????p, TP.HCM","0987654321",true,2);
  INSERT INTO `user` (`hoten`,`ngaysinh`,`gioitinh`,`cmnd`,`taikhoan`,`matkhau`,`ngayVaoLam`,`email`,`diachi`,`sdt`,`trangthai`,`loaiuser_id`) VALUES
  ("Tr????n Nh?? T??m","1997-03-14","N????","0374832938","trannhutam","1","2018-03-02","trannhutam@gmail.com","TP.Thu?? ??????c","0792348918",true,2);
  INSERT INTO `user` (`hoten`,`ngaysinh`,`gioitinh`,`cmnd`,`taikhoan`,`matkhau`,`ngayVaoLam`,`email`,`diachi`,`sdt`,`trangthai`,`loaiuser_id`) VALUES
  ("Huy??nh Thi?? Thanh","1999-03-21","N????","0364687732","huynhthithanh","1","2018-03-02","huynhthithanh@gmail.com","Bi??nh D????ng","0382349726",true,3);
   INSERT INTO `user` (`hoten`,`ngaysinh`,`gioitinh`,`cmnd`,`taikhoan`,`matkhau`,`ngayVaoLam`,`email`,`diachi`,`sdt`,`trangthai`,`loaiuser_id`) VALUES
  ("Phu??ng Thi????n Nh????t","2000-04-03","Nam","0337422034","phungthiennhat","1","2020-05-03","phungthiennhat@gmail.com","Qu????n Bi??nh Tha??nh","0921648324",true,3);
  
  INSERT INTO `nhacungcap` (`tencongty`,`diachi`,`tinhthanh`,`quocgia`,`email`,`sodt`) VALUES ("Milu Shop","371 Nguy????n Ki????m, Ph??????ng 3, Qu????n Go?? V????p","TP H???? Chi?? Minh","Vi????t Nam","sales@cityzoo.vn","0834502000");
  INSERT INTO `nhacungcap` (`tencongty`,`diachi`,`tinhthanh`,`quocgia`,`email`,`sodt`) VALUES ("City Zoo","146D4 Nguy???n V??n H?????ng, Ph??????ng Th???o ??i???n, Qu???n 2","TP H???? Chi?? Minh","Vi????t Nam","sales@cityzoo.vn","0834502000");
  INSERT INTO `nhacungcap` (`tencongty`,`diachi`,`tinhthanh`,`quocgia`,`email`,`sodt`) VALUES ("Nutrience","366 L?? V??n S???, Ph?????ng 14, Qu???n 3","TP H???? Chi?? Minh", "Vi????t Nam","info@petpro.vn","0901636696");
  INSERT INTO `nhacungcap` (`tencongty`,`diachi`,`tinhthanh`,`quocgia`,`email`,`sodt`) VALUES ("DOCA","L?? 19 ??.04, KCN Ch??u ?????c, X?? Ngh??a Th??nh, Huy????n Ch??u ?????c,","T???nh B?? R???a - V??ng T??u","Vi????t Nam","docavn79.com@gmail.com","02546299797");
  INSERT INTO `nhacungcap` (`tencongty`,`diachi`,`tinhthanh`,`quocgia`,`email`,`sodt`) VALUES ("Fusion Group","L?? L1-06B-07B-08B Khu du l???ch sinh th??i cao c???p An Kh??nh, x?? An Kh??nh, huy???n Ho??i ?????c","Ha?? N????i","Vi????t Nam","Info@fusiongroup.vn","0436367676");
  
  INSERT INTO `hanghoa` (`tenhanghoa`,`thuonghieu`,`soluongtrongkho`,`gianiemyet`,`hinhanh`,`tinhtrang`,`loaihanghoa_id`) VALUES ("Di??ch vu?? v???? sinh","Milu Shop", 999,120000,NULL,1,1);
  INSERT INTO `hanghoa` (`tenhanghoa`,`thuonghieu`,`soluongtrongkho`,`gianiemyet`,`hinhanh`,`tinhtrang`,`loaihanghoa_id`) VALUES ("Di??ch vu?? ca??o l??ng, c????t ti??a","Milu Shop", 999,150000,NULL,1,1);
  INSERT INTO `hanghoa` (`tenhanghoa`,`thuonghieu`,`soluongtrongkho`,`gianiemyet`,`hinhanh`,`tinhtrang`,`loaihanghoa_id`) VALUES ("Di??ch vu?? c????t mo??ng, ma??i mo??ng","Milu Shop", 1000,40000,NULL,1,1);
  INSERT INTO `hanghoa` (`tenhanghoa`,`thuonghieu`,`soluongtrongkho`,`gianiemyet`,`hinhanh`,`tinhtrang`,`loaihanghoa_id`) VALUES ("Di??ch vu?? v???? sinh tai, nh???? l??ng tai","Milu Shop", 999,40000,NULL,1,1);
  INSERT INTO `hanghoa` (`tenhanghoa`,`thuonghieu`,`soluongtrongkho`,`gianiemyet`,`hinhanh`,`tinhtrang`,`loaihanghoa_id`) VALUES ("Di??ch vu?? nhu????m 2 tai (1 ma??u)","Milu Shop", 999,200000,NULL,1,1);
  INSERT INTO `hanghoa` (`tenhanghoa`,`thuonghieu`,`soluongtrongkho`,`gianiemyet`,`hinhanh`,`tinhtrang`,`loaihanghoa_id`) VALUES ("Di??ch vu?? nhu????m 2 tai (2 ma??u)","Milu Shop", 999,250000,NULL,1,1);
  INSERT INTO `hanghoa` (`tenhanghoa`,`thuonghieu`,`soluongtrongkho`,`gianiemyet`,`hinhanh`,`tinhtrang`,`loaihanghoa_id`) VALUES ("Di??ch vu?? nhu????m 4 ch??n (1 ma??u)","Milu Shop", 1000,250000,NULL,1,1);
  INSERT INTO `hanghoa` (`tenhanghoa`,`thuonghieu`,`soluongtrongkho`,`gianiemyet`,`hinhanh`,`tinhtrang`,`loaihanghoa_id`) VALUES ("Di??ch vu?? nhu????m 4 ch??n (2 ma??u)","Milu Shop", 999,300000,NULL,1,1);
  INSERT INTO `hanghoa` (`tenhanghoa`,`thuonghieu`,`soluongtrongkho`,`gianiemyet`,`hinhanh`,`tinhtrang`,`loaihanghoa_id`) VALUES ("TH???C ??N ?????T ROYAL CANIN MAXI ADULT","ROYAL CANIN", 10,408000,"/image/ThucAnUotRoyalCaninMaxiAdult.jpg",1,2);
  INSERT INTO `hanghoa` (`tenhanghoa`,`thuonghieu`,`soluongtrongkho`,`gianiemyet`,`hinhanh`,`tinhtrang`,`loaihanghoa_id`) VALUES ("BEAPHAR JUNIOR PASTE - GEL DINH D?????NG CHO CH?? CON","beaphar",18,162000,"/image/BeapharJuniorPaste.jpg",1,2);
  INSERT INTO `hanghoa` (`tenhanghoa`,`thuonghieu`,`soluongtrongkho`,`gianiemyet`,`hinhanh`,`tinhtrang`,`loaihanghoa_id`) VALUES ("BEAPHAR MULTI VITAMIN TOP 10 - VITAMIN T???NG H???P CHO CH??","beaphar",11,236000,"/image/BeapharMultiVitaminTop10.gif",1,2);
  INSERT INTO `hanghoa` (`tenhanghoa`,`thuonghieu`,`soluongtrongkho`,`gianiemyet`,`hinhanh`,`tinhtrang`,`loaihanghoa_id`) VALUES ("BEAPHAR DUO ACTIVE JUNIOR PASTE CAT - GEL DINH D?????NG CHO M??O CON","beaphar",11,162000,"/image/BeapharDuoActiveJuniorPasteCat.jpg",1,3);
  INSERT INTO `hanghoa` (`tenhanghoa`,`thuonghieu`,`soluongtrongkho`,`gianiemyet`,`hinhanh`,`tinhtrang`,`loaihanghoa_id`) VALUES ("BEAPHAR LACTOL KITTY MILK - S???A CHO M??O CON","beaphar",24,502000,"/image/BeapharLactolKittyMilk.jpg",1,3);
  INSERT INTO `hanghoa` (`tenhanghoa`,`thuonghieu`,`soluongtrongkho`,`gianiemyet`,`hinhanh`,`tinhtrang`,`loaihanghoa_id`) VALUES ("ROYAL CANIN URINARY CARE WET - H??? TR??? S???C KH???E TI???T NI???U","ROYAL CANIN", 10,391000,"/image/RoyalCaninUrinaryCareWet.jpg",1,3);
  INSERT INTO `hanghoa` (`tenhanghoa`,`thuonghieu`,`soluongtrongkho`,`gianiemyet`,`hinhanh`,`tinhtrang`,`loaihanghoa_id`) VALUES ("NT Grain Free cao c???p cho Ch?? - G?? t??y, c?? tr??ch, tr???ng g?? v?? rau c??? qu??? t??? nhi??n","Nutrience",20,128000,"/image/NTGrainFreeCaoCapCho.png",1,2);
  INSERT INTO `hanghoa` (`tenhanghoa`,`thuonghieu`,`soluongtrongkho`,`gianiemyet`,`hinhanh`,`tinhtrang`,`loaihanghoa_id`) VALUES ("NT Subzero cho Ch?? - Th???t b??, c?? h???i v?? rau c??? qu??? t??? nhi??n (Cho m???i gi???ng ch?? ??? m???i l???a tu???i)","Nutrience",19,160000,"/image/NTSubzeroCho.png",1,2);
  INSERT INTO `hanghoa` (`tenhanghoa`,`thuonghieu`,`soluongtrongkho`,`gianiemyet`,`hinhanh`,`tinhtrang`,`loaihanghoa_id`) VALUES ("NT Original cho Ch?? con - Th???t g?? v?? rau c??? qu??? t??? nhi??n (D?????i 12 th??ng tu???i)","Nutrience",12,120000,"/image/NTOriginalChoCon.png",1,2);
  INSERT INTO `hanghoa` (`tenhanghoa`,`thuonghieu`,`soluongtrongkho`,`gianiemyet`,`hinhanh`,`tinhtrang`,`loaihanghoa_id`) VALUES ("NT Original cho M??o con - Th???t g?? v?? rau c??? qu??? t??? nhi??n (D?????i 12 th??ng tu???i)","Nutrience",8,520000,"/image/NTOriginalMeoCon.png",1,3);
  INSERT INTO `hanghoa` (`tenhanghoa`,`thuonghieu`,`soluongtrongkho`,`gianiemyet`,`hinhanh`,`tinhtrang`,`loaihanghoa_id`) VALUES ("NT Original cho M??o tr?????ng th??nh - Th???t g?? v?? rau c??? qu??? t??? nhi??n","Nutrience",7,900000,"/image/NTOriginalMeoTruongThanh.png",1,3);
  INSERT INTO `hanghoa` (`tenhanghoa`,`thuonghieu`,`soluongtrongkho`,`gianiemyet`,`hinhanh`,`tinhtrang`,`loaihanghoa_id`) VALUES ("NT Subzero cho M??o - Th???t g??, c?? h???i, c?? tr??ch v?? rau c??? qu??? t??? nhi??n (Cho m???i gi???ng m??o ??? m???i l???a tu???i)","Nutrience",5,1400000,"/image/NTSubzeroMeo.png",1,3);
  INSERT INTO `hanghoa` (`tenhanghoa`,`thuonghieu`,`soluongtrongkho`,`gianiemyet`,`hinhanh`,`tinhtrang`,`loaihanghoa_id`) VALUES ("Th???c ??n Cho Ch?? M???i L???a Tu???i - Doca Dog 450gr","FRESH TRINO",5,45000,"/image/DocaDog450gr.jpg",1,2);
  INSERT INTO `hanghoa` (`tenhanghoa`,`thuonghieu`,`soluongtrongkho`,`gianiemyet`,`hinhanh`,`tinhtrang`,`loaihanghoa_id`) VALUES ("Th???c ??n Ch?? Tr?????ng Th??nh - Regular Dog 7 450gr","FRESH TRINO",16,40000,"/image/RegularDog7450gr.jpg",1,2);
  INSERT INTO `hanghoa` (`tenhanghoa`,`thuonghieu`,`soluongtrongkho`,`gianiemyet`,`hinhanh`,`tinhtrang`,`loaihanghoa_id`) VALUES ("Th???c ??n Ch?? Con - Alphatrino 450gr","FRESH TRINO",14,45900,"/image/AlphatrinoPuppy450gr.jpg",1,2);
  INSERT INTO `hanghoa` (`tenhanghoa`,`thuonghieu`,`soluongtrongkho`,`gianiemyet`,`hinhanh`,`tinhtrang`,`loaihanghoa_id`) VALUES ("Th???c ??n M??o Tr?????ng Th??nh - Brutrino 450gr","FRESH TRINO",12,43200,"/image/BrutrinoCat450gr.jpg",1,3);
  INSERT INTO `hanghoa` (`tenhanghoa`,`thuonghieu`,`soluongtrongkho`,`gianiemyet`,`hinhanh`,`tinhtrang`,`loaihanghoa_id`) VALUES ("Th???c ??n M??o Con - Neutrino Cat 450gr","FRESH TRINO",3,49400,"/image/NeutrinoCat450gr.jpg",1,3);
  INSERT INTO `hanghoa` (`tenhanghoa`,`thuonghieu`,`soluongtrongkho`,`gianiemyet`,`hinhanh`,`tinhtrang`,`loaihanghoa_id`) VALUES ("S???a M??? Kh?? Cho M??o - Msbilac Gold Cat 330gr","Ch??u Tha??nh JSC",4,120000,"/image/MsbilacGoldCat330gr.jpg",1,3);
  INSERT INTO `hanghoa` (`tenhanghoa`,`thuonghieu`,`soluongtrongkho`,`gianiemyet`,`hinhanh`,`tinhtrang`,`loaihanghoa_id`) VALUES ("Th???c ??n h???t h???u c?? cho ch?? ANF 6 Free v??? c?? h???i 2kg","ANF",5,440000,"/image/ANF6FreeViCaHoi2KgCho .jpg",1,2);
  INSERT INTO `hanghoa` (`tenhanghoa`,`thuonghieu`,`soluongtrongkho`,`gianiemyet`,`hinhanh`,`tinhtrang`,`loaihanghoa_id`) VALUES ("H???t Dinh D?????ng Kh??ng Ng?? C???c V??? V???t Nutriwell D??nh Cho Ch?? M???i L???a Tu???i 1.5kg","Jeil PetFood",12,310000,"/image/NutriWellGrainFreeViThit1.5KgCho.jpg",1,2);
  INSERT INTO `hanghoa` (`tenhanghoa`,`thuonghieu`,`soluongtrongkho`,`gianiemyet`,`hinhanh`,`tinhtrang`,`loaihanghoa_id`) VALUES ("H???t Th???c ??n Ch???c N??ng T???t Cho S???c Kh???e ???????ng Ru???t Nature\"s Kitchen (d??nh Cho Ch?? M???i L???a Tu???i)","ANF",11,360000,"/image/Nature\"sKitchenDog.jpg",1,2);
  INSERT INTO `hanghoa` (`tenhanghoa`,`thuonghieu`,`soluongtrongkho`,`gianiemyet`,`hinhanh`,`tinhtrang`,`loaihanghoa_id`) VALUES ("Th???c ??n cho m??o m???i l???a tu???i 5kg - Today\"s dinner","Farmsco Corporation",37,395000,"/image/Today\"sdinner5KgCat.png",1,3);
  INSERT INTO `hanghoa` (`tenhanghoa`,`thuonghieu`,`soluongtrongkho`,`gianiemyet`,`hinhanh`,`tinhtrang`,`loaihanghoa_id`) VALUES ("B??nh th?????ng dinh d?????ng cho m??o Gozip v??? c?? ng???","Jeil PetFood",10,48000,"/image/GozipViCaNgu60gMeo.jpg",1,3);
  INSERT INTO `hanghoa` (`tenhanghoa`,`thuonghieu`,`soluongtrongkho`,`gianiemyet`,`hinhanh`,`tinhtrang`,`loaihanghoa_id`) VALUES ("TRAVEL - Balo v???n chuy???n","Pawise",30,547000,"/image/DogBackPack.jpg",1,4);
  INSERT INTO `hanghoa` (`tenhanghoa`,`thuonghieu`,`soluongtrongkho`,`gianiemyet`,`hinhanh`,`tinhtrang`,`loaihanghoa_id`) VALUES ("C??t V??? Sinh M??o Si??u Kh??? M??i - Diamond Feline 7kg","Diamond Feline",200,200000,"/image/CatVeSinhMeoSieuKhuMuiDiamondFeline7Kg.jpg",1,4);
  INSERT INTO `hanghoa` (`tenhanghoa`,`thuonghieu`,`soluongtrongkho`,`gianiemyet`,`hinhanh`,`tinhtrang`,`loaihanghoa_id`) VALUES ("S???a T???m Ch?? M??o M?????t Da L??ng - DC Guard 500ml","DC Guard",50,130000,"/image/SuaTamChoMeoMuotDaLongDCGuard500ml.jpg",1,4);
  
  INSERT INTO `nhacungcap_hanghoa` (`nhacungcap_id`,`hanghoa_id`,`soluong`,`ngaysanxuat`,`ngayhethan`,`ngaynhap`,`gianhap`,`nhanvien_id`,`ghichu`) VALUES (1,1,1000,NULL,NULL,"2018-03-02",0,2,NULL);
  INSERT INTO `nhacungcap_hanghoa` (`nhacungcap_id`,`hanghoa_id`,`soluong`,`ngaysanxuat`,`ngayhethan`,`ngaynhap`,`gianhap`,`nhanvien_id`,`ghichu`) VALUES (1,2,1000,NULL,NULL,"2018-03-02",0,2,NULL);
  INSERT INTO `nhacungcap_hanghoa` (`nhacungcap_id`,`hanghoa_id`,`soluong`,`ngaysanxuat`,`ngayhethan`,`ngaynhap`,`gianhap`,`nhanvien_id`,`ghichu`) VALUES (1,3,1000,NULL,NULL,"2018-03-02",0,2,NULL);
  INSERT INTO `nhacungcap_hanghoa` (`nhacungcap_id`,`hanghoa_id`,`soluong`,`ngaysanxuat`,`ngayhethan`,`ngaynhap`,`gianhap`,`nhanvien_id`,`ghichu`) VALUES (1,4,1000,NULL,NULL,"2018-03-02",0,2,NULL);
  INSERT INTO `nhacungcap_hanghoa` (`nhacungcap_id`,`hanghoa_id`,`soluong`,`ngaysanxuat`,`ngayhethan`,`ngaynhap`,`gianhap`,`nhanvien_id`,`ghichu`) VALUES (1,5,1000,NULL,NULL,"2018-03-02",0,2,NULL);
  INSERT INTO `nhacungcap_hanghoa` (`nhacungcap_id`,`hanghoa_id`,`soluong`,`ngaysanxuat`,`ngayhethan`,`ngaynhap`,`gianhap`,`nhanvien_id`,`ghichu`) VALUES (1,6,1000,NULL,NULL,"2018-03-02",0,2,NULL);
  INSERT INTO `nhacungcap_hanghoa` (`nhacungcap_id`,`hanghoa_id`,`soluong`,`ngaysanxuat`,`ngayhethan`,`ngaynhap`,`gianhap`,`nhanvien_id`,`ghichu`) VALUES (1,7,1000,NULL,NULL,"2018-03-02",0,2,NULL);
  INSERT INTO `nhacungcap_hanghoa` (`nhacungcap_id`,`hanghoa_id`,`soluong`,`ngaysanxuat`,`ngayhethan`,`ngaynhap`,`gianhap`,`nhanvien_id`,`ghichu`) VALUES (1,8,1000,NULL,NULL,"2018-03-02",0,2,NULL);
  INSERT INTO `nhacungcap_hanghoa` (`nhacungcap_id`,`hanghoa_id`,`soluong`,`ngaysanxuat`,`ngayhethan`,`ngaynhap`,`gianhap`,`nhanvien_id`,`ghichu`) VALUES (2,9,100,"2020-12-31","2022-12-31","2021-03-22",326000,3,NULL);
  INSERT INTO `nhacungcap_hanghoa` (`nhacungcap_id`,`hanghoa_id`,`soluong`,`ngaysanxuat`,`ngayhethan`,`ngaynhap`,`gianhap`,`nhanvien_id`,`ghichu`) VALUES (2,10,18,"2021-03-20","2022-03-20","2021-05-11",188000,3,NULL);
  INSERT INTO `nhacungcap_hanghoa` (`nhacungcap_id`,`hanghoa_id`,`soluong`,`ngaysanxuat`,`ngayhethan`,`ngaynhap`,`gianhap`,`nhanvien_id`,`ghichu`) VALUES (2,11,41,"2021-05-09","2022-05-09","2021-03-22",130000,3,NULL);
  INSERT INTO `nhacungcap_hanghoa` (`nhacungcap_id`,`hanghoa_id`,`soluong`,`ngaysanxuat`,`ngayhethan`,`ngaynhap`,`gianhap`,`nhanvien_id`,`ghichu`) VALUES (2,12,125,"2021-03-20","2022-03-20","2021-01-23",331000,3,NULL);
  INSERT INTO `nhacungcap_hanghoa` (`nhacungcap_id`,`hanghoa_id`,`soluong`,`ngaysanxuat`,`ngayhethan`,`ngaynhap`,`gianhap`,`nhanvien_id`,`ghichu`) VALUES (2,13,24,"2021-01-21","2022-01-21","2021-02-19",312000,3,NULL);
  INSERT INTO `nhacungcap_hanghoa` (`nhacungcap_id`,`hanghoa_id`,`soluong`,`ngaysanxuat`,`ngayhethan`,`ngaynhap`,`gianhap`,`nhanvien_id`,`ghichu`) VALUES (3,14,32,"2021-02-17","2022-02-17","2021-01-14",102000,2,NULL);
  INSERT INTO `nhacungcap_hanghoa` (`nhacungcap_id`,`hanghoa_id`,`soluong`,`ngaysanxuat`,`ngayhethan`,`ngaynhap`,`gianhap`,`nhanvien_id`,`ghichu`) VALUES (3,15,20,"2022-01-12","2021-01-12","2021-01-14",128000,2,NULL);
  INSERT INTO `nhacungcap_hanghoa` (`nhacungcap_id`,`hanghoa_id`,`soluong`,`ngaysanxuat`,`ngayhethan`,`ngaynhap`,`gianhap`,`nhanvien_id`,`ghichu`) VALUES (3,16,111,"2022-01-12","2021-01-12","2021-01-14",80000,2,NULL);
  INSERT INTO `nhacungcap_hanghoa` (`nhacungcap_id`,`hanghoa_id`,`soluong`,`ngaysanxuat`,`ngayhethan`,`ngaynhap`,`gianhap`,`nhanvien_id`,`ghichu`) VALUES (3,17,12,"2022-01-12","2021-01-12","2021-01-14",401000,2,NULL);
  INSERT INTO `nhacungcap_hanghoa` (`nhacungcap_id`,`hanghoa_id`,`soluong`,`ngaysanxuat`,`ngayhethan`,`ngaynhap`,`gianhap`,`nhanvien_id`,`ghichu`) VALUES (3,18,10,"2022-01-12","2021-01-12","2021-01-14",684000,2,NULL);
  INSERT INTO `nhacungcap_hanghoa` (`nhacungcap_id`,`hanghoa_id`,`soluong`,`ngaysanxuat`,`ngayhethan`,`ngaynhap`,`gianhap`,`nhanvien_id`,`ghichu`) VALUES (3,19,15,"2022-01-12","2021-01-12","2021-01-14",1050000,2,NULL);
  INSERT INTO `nhacungcap_hanghoa` (`nhacungcap_id`,`hanghoa_id`,`soluong`,`ngaysanxuat`,`ngayhethan`,`ngaynhap`,`gianhap`,`nhanvien_id`,`ghichu`) VALUES (4,20,122,"2021-04-23","2022-04-23","2021-04-25",31500,3,NULL);
  INSERT INTO `nhacungcap_hanghoa` (`nhacungcap_id`,`hanghoa_id`,`soluong`,`ngaysanxuat`,`ngayhethan`,`ngaynhap`,`gianhap`,`nhanvien_id`,`ghichu`) VALUES (4,21,123,"2021-04-23","2022-04-23","2021-04-25",28000,3,NULL);
  INSERT INTO `nhacungcap_hanghoa` (`nhacungcap_id`,`hanghoa_id`,`soluong`,`ngaysanxuat`,`ngayhethan`,`ngaynhap`,`gianhap`,`nhanvien_id`,`ghichu`) VALUES (4,22,134,"2021-04-23","2022-04-23","2021-04-25",32000,3,NULL);
  INSERT INTO `nhacungcap_hanghoa` (`nhacungcap_id`,`hanghoa_id`,`soluong`,`ngaysanxuat`,`ngayhethan`,`ngaynhap`,`gianhap`,`nhanvien_id`,`ghichu`) VALUES (4,23,117,"2021-04-23","2022-04-23","2021-04-25",30000,3,NULL);
  INSERT INTO `nhacungcap_hanghoa` (`nhacungcap_id`,`hanghoa_id`,`soluong`,`ngaysanxuat`,`ngayhethan`,`ngaynhap`,`gianhap`,`nhanvien_id`,`ghichu`) VALUES (4,24,123,"2021-04-23","2022-04-23","2021-04-25",34000,3,NULL);
  INSERT INTO `nhacungcap_hanghoa` (`nhacungcap_id`,`hanghoa_id`,`soluong`,`ngaysanxuat`,`ngayhethan`,`ngaynhap`,`gianhap`,`nhanvien_id`,`ghichu`) VALUES (4,25,20,"2021-04-23","2022-04-23","2021-04-25",92000,3,NULL);
  INSERT INTO `nhacungcap_hanghoa` (`nhacungcap_id`,`hanghoa_id`,`soluong`,`ngaysanxuat`,`ngayhethan`,`ngaynhap`,`gianhap`,`nhanvien_id`,`ghichu`) VALUES (5,26,15,"2021-04-23","2022-04-23","2021-04-25",334000,3,NULL);
  INSERT INTO `nhacungcap_hanghoa` (`nhacungcap_id`,`hanghoa_id`,`soluong`,`ngaysanxuat`,`ngayhethan`,`ngaynhap`,`gianhap`,`nhanvien_id`,`ghichu`) VALUES (5,27,12,"2021-02-11","2022-02-11","2021-04-25",239000,3,NULL);
  INSERT INTO `nhacungcap_hanghoa` (`nhacungcap_id`,`hanghoa_id`,`soluong`,`ngaysanxuat`,`ngayhethan`,`ngaynhap`,`gianhap`,`nhanvien_id`,`ghichu`) VALUES (5,28,23,"2021-03-29","2022-03-29","2021-04-25",274000,3,NULL);
  INSERT INTO `nhacungcap_hanghoa` (`nhacungcap_id`,`hanghoa_id`,`soluong`,`ngaysanxuat`,`ngayhethan`,`ngaynhap`,`gianhap`,`nhanvien_id`,`ghichu`) VALUES (5,29,37,"2021-02-11","2022-02-11","2021-04-25",300000,3,NULL);
  INSERT INTO `nhacungcap_hanghoa` (`nhacungcap_id`,`hanghoa_id`,`soluong`,`ngaysanxuat`,`ngayhethan`,`ngaynhap`,`gianhap`,`nhanvien_id`,`ghichu`) VALUES (5,30,200,"2021-04-19","2022-04-19","2021-04-25",34000,3,NULL);
  INSERT INTO `nhacungcap_hanghoa` (`nhacungcap_id`,`hanghoa_id`,`soluong`,`ngaysanxuat`,`ngayhethan`,`ngaynhap`,`gianhap`,`nhanvien_id`,`ghichu`) VALUES (5,31,45,"2021-03-29","2022-03-29","2021-04-25",209000,3,NULL);
  INSERT INTO `nhacungcap_hanghoa` (`nhacungcap_id`,`hanghoa_id`,`soluong`,`ngaysanxuat`,`ngayhethan`,`ngaynhap`,`gianhap`,`nhanvien_id`,`ghichu`) VALUES (2,32,100,NULL,NULL,"2021-10-27",382900,2,NULL);
  INSERT INTO `nhacungcap_hanghoa` (`nhacungcap_id`,`hanghoa_id`,`soluong`,`ngaysanxuat`,`ngayhethan`,`ngaynhap`,`gianhap`,`nhanvien_id`,`ghichu`) VALUES (4,33,200,NULL,NULL,"2021-10-27",130000,2,NULL);
  INSERT INTO `nhacungcap_hanghoa` (`nhacungcap_id`,`hanghoa_id`,`soluong`,`ngaysanxuat`,`ngayhethan`,`ngaynhap`,`gianhap`,`nhanvien_id`,`ghichu`) VALUES (4,34,50,"2021-10-27","2022-10-27","2021-10-27",101400,2,NULL);
  
  INSERT INTO `khachhang` (`hoten`,`ngaysinh`,`gioitinh`,`diachi`,`sdt`)
  VALUES ("NULL","0000-00-00","NULL", "NULL","NULL");
  INSERT INTO `khachhang` (`hoten`,`ngaysinh`,`gioitinh`,`diachi`,`sdt`,`diemtichluy`)
  VALUES ("Nguy????n Minh Vi????t","1983-06-28","Nam", "1111 ????????ng 3/2, Ph??????ng 12, Qu????n 11","0932478390",43892);
  INSERT INTO `khachhang` (`hoten`,`ngaysinh`,`gioitinh`,`diachi`,`sdt`,`diemtichluy`)
  VALUES ("Hoa??ng V??n Thu","1968-04-27","N????", "333 ????????ng Nguy????n Duy D????ng, Ph??????ng 4, Qu????n 10","0793247628",28219);
  INSERT INTO `khachhang` (`hoten`,`ngaysinh`,`gioitinh`,`diachi`,`sdt`,`diemtichluy`)
  VALUES ("Nguy????n Hoa??ng","1994-04-25","Nam", "Qu????n 2","0934783203",300);
  INSERT INTO `khachhang` (`hoten`,`ngaysinh`,`gioitinh`,`diachi`,`sdt`,`diemtichluy`)
  VALUES ("La??i V??n Ba","1962-03-26","Nam", "Qu????n Go?? V????p","0932473592",1512);
  INSERT INTO `khachhang` (`hoten`,`ngaysinh`,`gioitinh`,`diachi`,`sdt`,`diemtichluy`)
  VALUES ("Huy??nh Thi?? Tuy????t Nhi","2000-02-14","N????", "Ninh Thu????n","0994732629",250);
  INSERT INTO `khachhang` (`hoten`,`ngaysinh`,`gioitinh`,`diachi`,`sdt`,`diemtichluy`)
  VALUES ("Tr????ng Thu Tha??o","1998-04-15","N????", "Bi??nh D????ng","0792348900",900);
  INSERT INTO `khachhang` (`hoten`,`ngaysinh`,`gioitinh`,`diachi`,`sdt`,`diemtichluy`)
  VALUES ("Phan Thi?? Ngo??c","2002-04-14","N????", "Qu????n 3","0793274932",120);
  INSERT INTO `khachhang` (`hoten`,`ngaysinh`,`gioitinh`,`diachi`,`sdt`,`diemtichluy`)
  VALUES ("Vo?? Chi?? N??ng","2003-09-28","Nam", "Qu????n Bi??nh T??n","0932479592",99);
  INSERT INTO `khachhang` (`hoten`,`ngaysinh`,`gioitinh`,`diachi`,`sdt`,`diemtichluy`)
  VALUES ("T?? V??n Be??","2001-03-20","Nam", "Qu????n 8","0932567409",6080);
  
  
  INSERT INTO `thucung` (`ten`,`ngaysinh`,`gioitinh`,`mauLong`,`tinhtrangsuckhoe`,`khachhang_id`)
  VALUES ("Ken","2020-01-01","??????c","Tr????ng","Kho??e ma??nh", 2);
  INSERT INTO `thucung` (`ten`,`ngaysinh`,`gioitinh`,`mauLong`,`tinhtrangsuckhoe`,`khachhang_id`)
  VALUES ("Susu","2020-12-03","Ca??i","??en","Kho??e ma??nh", 2);
  INSERT INTO `thucung` (`ten`,`ngaysinh`,`gioitinh`,`mauLong`,`tinhtrangsuckhoe`,`khachhang_id`)
  VALUES ("Oshi","2018-04-26","??????c","Va??ng","Kho??e ma??nh", 6);
  
  INSERT INTO `donhang`(`ngayTaoDH`,`nhanvien_id`,`khachhang_id`) VALUES ("2018-03-02",4,2);
  INSERT INTO `donhang`(`ngayTaoDH`,`nhanvien_id`,`khachhang_id`) VALUES ("2018-04-10",5,3);
  INSERT INTO `donhang`(`ngayTaoDH`,`nhanvien_id`,`khachhang_id`) VALUES ("2018-05-12",5,1);
  INSERT INTO `donhang`(`ngayTaoDH`,`nhanvien_id`,`khachhang_id`) VALUES ("2018-06-04",4,1);
  INSERT INTO `donhang`(`ngayTaoDH`,`nhanvien_id`,`khachhang_id`) VALUES ("2018-07-27",5,1);
  INSERT INTO `donhang`(`ngayTaoDH`,`nhanvien_id`,`khachhang_id`) VALUES ("2018-08-29",5,1);
  INSERT INTO `donhang`(`ngayTaoDH`,`nhanvien_id`,`khachhang_id`) VALUES ("2018-09-20",5,1);
  INSERT INTO `donhang`(`ngayTaoDH`,`nhanvien_id`,`khachhang_id`) VALUES ("2018-10-12",4,1);
  INSERT INTO `donhang`(`ngayTaoDH`,`nhanvien_id`,`khachhang_id`) VALUES ("2018-11-03",5,1);
  INSERT INTO `donhang`(`ngayTaoDH`,`nhanvien_id`,`khachhang_id`) VALUES ("2018-12-07",4,1);
  INSERT INTO `donhang`(`ngayTaoDH`,`nhanvien_id`,`khachhang_id`) VALUES ("2019-01-28",4,1);
  INSERT INTO `donhang`(`ngayTaoDH`,`nhanvien_id`,`khachhang_id`) VALUES ("2019-02-18",5,1);
  INSERT INTO `donhang`(`ngayTaoDH`,`nhanvien_id`,`khachhang_id`) VALUES ("2019-03-02",5,1);
  INSERT INTO `donhang`(`ngayTaoDH`,`nhanvien_id`,`khachhang_id`) VALUES ("2019-04-20",4,4);
  INSERT INTO `donhang`(`ngayTaoDH`,`nhanvien_id`,`khachhang_id`) VALUES ("2019-05-21",5,5);
  INSERT INTO `donhang`(`ngayTaoDH`,`nhanvien_id`,`khachhang_id`) VALUES ("2019-07-01",4,1);
  INSERT INTO `donhang`(`ngayTaoDH`,`nhanvien_id`,`khachhang_id`) VALUES ("2019-10-20",5,1);
  INSERT INTO `donhang`(`ngayTaoDH`,`nhanvien_id`,`khachhang_id`) VALUES ("2019-12-10",4,6);
  INSERT INTO `donhang`(`ngayTaoDH`,`nhanvien_id`,`khachhang_id`) VALUES ("2020-01-20",4,7);
  INSERT INTO `donhang`(`ngayTaoDH`,`nhanvien_id`,`khachhang_id`) VALUES ("2020-02-01",5,1);
  INSERT INTO `donhang`(`ngayTaoDH`,`nhanvien_id`,`khachhang_id`) VALUES ("2020-04-24",4,1);
  INSERT INTO `donhang`(`ngayTaoDH`,`nhanvien_id`,`khachhang_id`) VALUES ("2020-05-21",5,8);
  INSERT INTO `donhang`(`ngayTaoDH`,`nhanvien_id`,`khachhang_id`) VALUES ("2020-08-12",4,1);
  INSERT INTO `donhang`(`ngayTaoDH`,`nhanvien_id`,`khachhang_id`) VALUES ("2020-12-15",4,1);
  INSERT INTO `donhang`(`ngayTaoDH`,`nhanvien_id`,`khachhang_id`) VALUES ("2021-03-14",4,9);
  INSERT INTO `donhang`(`ngayTaoDH`,`nhanvien_id`,`khachhang_id`) VALUES ("2021-10-03",4,1);
  INSERT INTO `donhang`(`ngayTaoDH`,`nhanvien_id`,`khachhang_id`) VALUES ("2021-11-10",4,10);
  INSERT INTO `donhang`(`ngayTaoDH`,`nhanvien_id`,`khachhang_id`) VALUES ("2021-11-11",5,2);
  
  INSERT INTO `chitietdonhang`(`donhang_id`,`hanghoa_id`,`soluong`,`dongia`,`giamgia`) VALUES (1, 9, 90, 408000, 0.15);
  INSERT INTO `chitietdonhang`(`donhang_id`,`hanghoa_id`,`soluong`,`dongia`,`giamgia`) VALUES (1, 16, 100, 160000, 0.2);
  INSERT INTO `chitietdonhang`(`donhang_id`,`hanghoa_id`,`soluong`,`dongia`,`giamgia`) VALUES (1, 27, 2, 440000, 0);
  INSERT INTO `chitietdonhang`(`donhang_id`,`hanghoa_id`,`soluong`,`dongia`,`giamgia`) VALUES (2, 12, 114, 130000, 0.2);
  INSERT INTO `chitietdonhang`(`donhang_id`,`hanghoa_id`,`soluong`,`dongia`,`giamgia`) VALUES (2, 32, 70, 275000, 0.15);
  INSERT INTO `chitietdonhang`(`donhang_id`,`hanghoa_id`,`soluong`,`dongia`,`giamgia`) VALUES (3, 27, 1, 440000, 0);
  INSERT INTO `chitietdonhang`(`donhang_id`,`hanghoa_id`,`soluong`,`dongia`,`giamgia`) VALUES (4, 14, 22, 391000, 0.1);
  INSERT INTO `chitietdonhang`(`donhang_id`,`hanghoa_id`,`soluong`,`dongia`,`giamgia`) VALUES (5, 17, 5, 120000, 0);
  INSERT INTO `chitietdonhang`(`donhang_id`,`hanghoa_id`,`soluong`,`dongia`,`giamgia`) VALUES (6, 18, 2, 520000, 0);
  INSERT INTO `chitietdonhang`(`donhang_id`,`hanghoa_id`,`soluong`,`dongia`,`giamgia`) VALUES (7, 19, 7, 900000, 0);
  INSERT INTO `chitietdonhang`(`donhang_id`,`hanghoa_id`,`soluong`,`dongia`,`giamgia`) VALUES (8, 20, 113, 1400000, 0.2);
  INSERT INTO `chitietdonhang`(`donhang_id`,`hanghoa_id`,`soluong`,`dongia`,`giamgia`) VALUES (9, 21, 112, 45000, 0.2);
  INSERT INTO `chitietdonhang`(`donhang_id`,`hanghoa_id`,`soluong`,`dongia`,`giamgia`) VALUES (10, 22, 114, 40000, 0.2);
  INSERT INTO `chitietdonhang`(`donhang_id`,`hanghoa_id`,`soluong`,`dongia`,`giamgia`) VALUES (11, 23, 101, 459000, 0.2);
  INSERT INTO `chitietdonhang`(`donhang_id`,`hanghoa_id`,`soluong`,`dongia`,`giamgia`) VALUES (12, 24, 10, 43200, 0);
  INSERT INTO `chitietdonhang`(`donhang_id`,`hanghoa_id`,`soluong`,`dongia`,`giamgia`) VALUES (13, 25, 15, 49400, 0);
  INSERT INTO `chitietdonhang`(`donhang_id`,`hanghoa_id`,`soluong`,`dongia`,`giamgia`) VALUES (14, 08, 1, 300000, 0);
  INSERT INTO `chitietdonhang`(`donhang_id`,`hanghoa_id`,`soluong`,`dongia`,`giamgia`) VALUES (15, 31, 35, 48000, 0.1);
  INSERT INTO `chitietdonhang`(`donhang_id`,`hanghoa_id`,`soluong`,`dongia`,`giamgia`) VALUES (16, 11, 30, 236000, 0.1);
  INSERT INTO `chitietdonhang`(`donhang_id`,`hanghoa_id`,`soluong`,`dongia`,`giamgia`) VALUES (17, 10, 87, 162000, 0.15);
  INSERT INTO `chitietdonhang`(`donhang_id`,`hanghoa_id`,`soluong`,`dongia`,`giamgia`) VALUES (18, 06, 1, 250000, 0);
  INSERT INTO `chitietdonhang`(`donhang_id`,`hanghoa_id`,`soluong`,`dongia`,`giamgia`) VALUES (19, 19, 1, 900000, 0);
  INSERT INTO `chitietdonhang`(`donhang_id`,`hanghoa_id`,`soluong`,`dongia`,`giamgia`) VALUES (20, 20, 4, 1400000, 0);
  INSERT INTO `chitietdonhang`(`donhang_id`,`hanghoa_id`,`soluong`,`dongia`,`giamgia`) VALUES (21, 21, 6, 45000, 0);
  INSERT INTO `chitietdonhang`(`donhang_id`,`hanghoa_id`,`soluong`,`dongia`,`giamgia`) VALUES (22, 22, 3, 40000, 0);
  INSERT INTO `chitietdonhang`(`donhang_id`,`hanghoa_id`,`soluong`,`dongia`,`giamgia`) VALUES (23, 23, 2, 45900, 0);
  INSERT INTO `chitietdonhang`(`donhang_id`,`hanghoa_id`,`soluong`,`dongia`,`giamgia`) VALUES (24, 24, 101, 43200, 0.2);
  INSERT INTO `chitietdonhang`(`donhang_id`,`hanghoa_id`,`soluong`,`dongia`,`giamgia`) VALUES (25, 25, 2, 49400, 0);
  INSERT INTO `chitietdonhang`(`donhang_id`,`hanghoa_id`,`soluong`,`dongia`,`giamgia`) VALUES (26, 26, 11, 120000, 0);
  INSERT INTO `chitietdonhang`(`donhang_id`,`hanghoa_id`,`soluong`,`dongia`,`giamgia`) VALUES (27, 27, 4, 440000, 0);
  INSERT INTO `chitietdonhang`(`donhang_id`,`hanghoa_id`,`soluong`,`dongia`,`giamgia`) VALUES (27, 29, 12, 360000, 0);
  INSERT INTO `chitietdonhang`(`donhang_id`,`hanghoa_id`,`soluong`,`dongia`,`giamgia`) VALUES (28, 22, 1, 40000, 0.5);
  INSERT INTO `chitietdonhang`(`donhang_id`,`hanghoa_id`,`soluong`,`dongia`,`giamgia`) VALUES (28, 1, 1, 120000, 0.5);
  INSERT INTO `chitietdonhang`(`donhang_id`,`hanghoa_id`,`soluong`,`dongia`,`giamgia`) VALUES (28, 2, 1, 150000, 0.5);
  INSERT INTO `chitietdonhang`(`donhang_id`,`hanghoa_id`,`soluong`,`dongia`,`giamgia`) VALUES (28, 4, 1, 40000, 0.5);
  INSERT INTO `chitietdonhang`(`donhang_id`,`hanghoa_id`,`soluong`,`dongia`,`giamgia`) VALUES (28, 5, 1, 200000, 0.5);
  
  
