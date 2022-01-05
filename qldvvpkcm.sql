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
  `ngayTaoDH` datetime NOT NULL, #sửa
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
  
  INSERT INTO `loaihanghoa` (`tenloai`) VALUES ("Dịch vụ"),("Thức ăn cho chó"), ("Thức ăn cho mèo"), ("Phụ kiện");
  
  INSERT INTO `loaiuser` (`tenloai`) VALUES ("Quản lý trưởng"), ("Thủ kho"), ("Nhân viên");
  
  INSERT INTO `user` (`hoten`,`ngaysinh`,`gioitinh`,`cmnd`,`taikhoan`,`matkhau`,`ngayVaoLam`,`email`,`diachi`,`sdt`,`trangthai`,`loaiuser_id`) VALUES
  ("Trương Phong","1990-01-01","Nam","0222334455","truongphong","1","2018-03-02","truongphong@gmail.com","Quận 7, TP.HCM","0793278239",true,1);
  INSERT INTO `user` (`hoten`,`ngaysinh`,`gioitinh`,`cmnd`,`taikhoan`,`matkhau`,`ngayVaoLam`,`email`,`diachi`,`sdt`,`trangthai`,`loaiuser_id`) VALUES
  ("Phạm Luân","1980-04-27","Nam","033445567788","phamluan","1","2018-03-02","phamluan@gmail.com","Gò Vấp, TP.HCM","0987654321",true,2);
  INSERT INTO `user` (`hoten`,`ngaysinh`,`gioitinh`,`cmnd`,`taikhoan`,`matkhau`,`ngayVaoLam`,`email`,`diachi`,`sdt`,`trangthai`,`loaiuser_id`) VALUES
  ("Trần Như Tâm","1997-03-14","Nữ","0374832938","trannhutam","1","2018-03-02","trannhutam@gmail.com","TP.Thủ Đức","0792348918",true,2);
  INSERT INTO `user` (`hoten`,`ngaysinh`,`gioitinh`,`cmnd`,`taikhoan`,`matkhau`,`ngayVaoLam`,`email`,`diachi`,`sdt`,`trangthai`,`loaiuser_id`) VALUES
  ("Huỳnh Thị Thanh","1999-03-21","Nữ","0364687732","huynhthithanh","1","2018-03-02","huynhthithanh@gmail.com","Bình Dương","0382349726",true,3);
   INSERT INTO `user` (`hoten`,`ngaysinh`,`gioitinh`,`cmnd`,`taikhoan`,`matkhau`,`ngayVaoLam`,`email`,`diachi`,`sdt`,`trangthai`,`loaiuser_id`) VALUES
  ("Phùng Thiện Nhất","2000-04-03","Nam","0337422034","phungthiennhat","1","2020-05-03","phungthiennhat@gmail.com","Quận Bình Thạnh","0921648324",true,3);
  
  INSERT INTO `nhacungcap` (`tencongty`,`diachi`,`tinhthanh`,`quocgia`,`email`,`sodt`) VALUES ("Milu Shop","371 Nguyễn Kiệm, Phường 3, Quận Gò Vấp","TP Hồ Chí Minh","Việt Nam","sales@cityzoo.vn","0834502000");
  INSERT INTO `nhacungcap` (`tencongty`,`diachi`,`tinhthanh`,`quocgia`,`email`,`sodt`) VALUES ("City Zoo","146D4 Nguyễn Văn Hưởng, Phường Thảo Điền, Quận 2","TP Hồ Chí Minh","Việt Nam","sales@cityzoo.vn","0834502000");
  INSERT INTO `nhacungcap` (`tencongty`,`diachi`,`tinhthanh`,`quocgia`,`email`,`sodt`) VALUES ("Nutrience","366 Lê Văn Sỹ, Phường 14, Quận 3","TP Hồ Chí Minh", "Việt Nam","info@petpro.vn","0901636696");
  INSERT INTO `nhacungcap` (`tencongty`,`diachi`,`tinhthanh`,`quocgia`,`email`,`sodt`) VALUES ("DOCA","Lô 19 Đ.04, KCN Châu Đức, Xã Nghĩa Thành, Huyện Châu Đức,","Tỉnh Bà Rịa - Vũng Tàu","Việt Nam","docavn79.com@gmail.com","02546299797");
  INSERT INTO `nhacungcap` (`tencongty`,`diachi`,`tinhthanh`,`quocgia`,`email`,`sodt`) VALUES ("Fusion Group","Lô L1-06B-07B-08B Khu du lịch sinh thái cao cấp An Khánh, xã An Khánh, huyện Hoài Đức","Hà Nội","Việt Nam","Info@fusiongroup.vn","0436367676");
  
  INSERT INTO `hanghoa` (`tenhanghoa`,`thuonghieu`,`soluongtrongkho`,`gianiemyet`,`hinhanh`,`tinhtrang`,`loaihanghoa_id`) VALUES ("Dịch vụ vệ sinh","Milu Shop", 999,120000,NULL,1,1);
  INSERT INTO `hanghoa` (`tenhanghoa`,`thuonghieu`,`soluongtrongkho`,`gianiemyet`,`hinhanh`,`tinhtrang`,`loaihanghoa_id`) VALUES ("Dịch vụ cạo lông, cắt tỉa","Milu Shop", 999,150000,NULL,1,1);
  INSERT INTO `hanghoa` (`tenhanghoa`,`thuonghieu`,`soluongtrongkho`,`gianiemyet`,`hinhanh`,`tinhtrang`,`loaihanghoa_id`) VALUES ("Dịch vụ cắt móng, mài móng","Milu Shop", 1000,40000,NULL,1,1);
  INSERT INTO `hanghoa` (`tenhanghoa`,`thuonghieu`,`soluongtrongkho`,`gianiemyet`,`hinhanh`,`tinhtrang`,`loaihanghoa_id`) VALUES ("Dịch vụ vệ sinh tai, nhổ lông tai","Milu Shop", 999,40000,NULL,1,1);
  INSERT INTO `hanghoa` (`tenhanghoa`,`thuonghieu`,`soluongtrongkho`,`gianiemyet`,`hinhanh`,`tinhtrang`,`loaihanghoa_id`) VALUES ("Dịch vụ nhuộm 2 tai (1 màu)","Milu Shop", 999,200000,NULL,1,1);
  INSERT INTO `hanghoa` (`tenhanghoa`,`thuonghieu`,`soluongtrongkho`,`gianiemyet`,`hinhanh`,`tinhtrang`,`loaihanghoa_id`) VALUES ("Dịch vụ nhuộm 2 tai (2 màu)","Milu Shop", 999,250000,NULL,1,1);
  INSERT INTO `hanghoa` (`tenhanghoa`,`thuonghieu`,`soluongtrongkho`,`gianiemyet`,`hinhanh`,`tinhtrang`,`loaihanghoa_id`) VALUES ("Dịch vụ nhuộm 4 chân (1 màu)","Milu Shop", 1000,250000,NULL,1,1);
  INSERT INTO `hanghoa` (`tenhanghoa`,`thuonghieu`,`soluongtrongkho`,`gianiemyet`,`hinhanh`,`tinhtrang`,`loaihanghoa_id`) VALUES ("Dịch vụ nhuộm 4 chân (2 màu)","Milu Shop", 999,300000,NULL,1,1);
  INSERT INTO `hanghoa` (`tenhanghoa`,`thuonghieu`,`soluongtrongkho`,`gianiemyet`,`hinhanh`,`tinhtrang`,`loaihanghoa_id`) VALUES ("THỨC ĂN ƯỚT ROYAL CANIN MAXI ADULT","ROYAL CANIN", 10,408000,"/image/ThucAnUotRoyalCaninMaxiAdult.jpg",1,2);
  INSERT INTO `hanghoa` (`tenhanghoa`,`thuonghieu`,`soluongtrongkho`,`gianiemyet`,`hinhanh`,`tinhtrang`,`loaihanghoa_id`) VALUES ("BEAPHAR JUNIOR PASTE - GEL DINH DƯỠNG CHO CHÓ CON","beaphar",18,162000,"/image/BeapharJuniorPaste.jpg",1,2);
  INSERT INTO `hanghoa` (`tenhanghoa`,`thuonghieu`,`soluongtrongkho`,`gianiemyet`,`hinhanh`,`tinhtrang`,`loaihanghoa_id`) VALUES ("BEAPHAR MULTI VITAMIN TOP 10 - VITAMIN TỔNG HỢP CHO CHÓ","beaphar",11,236000,"/image/BeapharMultiVitaminTop10.gif",1,2);
  INSERT INTO `hanghoa` (`tenhanghoa`,`thuonghieu`,`soluongtrongkho`,`gianiemyet`,`hinhanh`,`tinhtrang`,`loaihanghoa_id`) VALUES ("BEAPHAR DUO ACTIVE JUNIOR PASTE CAT - GEL DINH DƯỠNG CHO MÈO CON","beaphar",11,162000,"/image/BeapharDuoActiveJuniorPasteCat.jpg",1,3);
  INSERT INTO `hanghoa` (`tenhanghoa`,`thuonghieu`,`soluongtrongkho`,`gianiemyet`,`hinhanh`,`tinhtrang`,`loaihanghoa_id`) VALUES ("BEAPHAR LACTOL KITTY MILK - SỮA CHO MÈO CON","beaphar",24,502000,"/image/BeapharLactolKittyMilk.jpg",1,3);
  INSERT INTO `hanghoa` (`tenhanghoa`,`thuonghieu`,`soluongtrongkho`,`gianiemyet`,`hinhanh`,`tinhtrang`,`loaihanghoa_id`) VALUES ("ROYAL CANIN URINARY CARE WET - HỖ TRỢ SỨC KHỎE TIẾT NIỆU","ROYAL CANIN", 10,391000,"/image/RoyalCaninUrinaryCareWet.jpg",1,3);
  INSERT INTO `hanghoa` (`tenhanghoa`,`thuonghieu`,`soluongtrongkho`,`gianiemyet`,`hinhanh`,`tinhtrang`,`loaihanghoa_id`) VALUES ("NT Grain Free cao cấp cho Chó - Gà tây, cá trích, trứng gà và rau củ quả tự nhiên","Nutrience",20,128000,"/image/NTGrainFreeCaoCapCho.png",1,2);
  INSERT INTO `hanghoa` (`tenhanghoa`,`thuonghieu`,`soluongtrongkho`,`gianiemyet`,`hinhanh`,`tinhtrang`,`loaihanghoa_id`) VALUES ("NT Subzero cho Chó - Thịt bò, cá hồi và rau củ quả tự nhiên (Cho mọi giống chó ở mọi lứa tuổi)","Nutrience",19,160000,"/image/NTSubzeroCho.png",1,2);
  INSERT INTO `hanghoa` (`tenhanghoa`,`thuonghieu`,`soluongtrongkho`,`gianiemyet`,`hinhanh`,`tinhtrang`,`loaihanghoa_id`) VALUES ("NT Original cho Chó con - Thịt gà và rau củ quả tự nhiên (Dưới 12 tháng tuổi)","Nutrience",12,120000,"/image/NTOriginalChoCon.png",1,2);
  INSERT INTO `hanghoa` (`tenhanghoa`,`thuonghieu`,`soluongtrongkho`,`gianiemyet`,`hinhanh`,`tinhtrang`,`loaihanghoa_id`) VALUES ("NT Original cho Mèo con - Thịt gà và rau củ quả tự nhiên (Dưới 12 tháng tuổi)","Nutrience",8,520000,"/image/NTOriginalMeoCon.png",1,3);
  INSERT INTO `hanghoa` (`tenhanghoa`,`thuonghieu`,`soluongtrongkho`,`gianiemyet`,`hinhanh`,`tinhtrang`,`loaihanghoa_id`) VALUES ("NT Original cho Mèo trưởng thành - Thịt gà và rau củ quả tự nhiên","Nutrience",7,900000,"/image/NTOriginalMeoTruongThanh.png",1,3);
  INSERT INTO `hanghoa` (`tenhanghoa`,`thuonghieu`,`soluongtrongkho`,`gianiemyet`,`hinhanh`,`tinhtrang`,`loaihanghoa_id`) VALUES ("NT Subzero cho Mèo - Thịt gà, cá hồi, cá trích và rau củ quả tự nhiên (Cho mọi giống mèo ở mọi lứa tuổi)","Nutrience",5,1400000,"/image/NTSubzeroMeo.png",1,3);
  INSERT INTO `hanghoa` (`tenhanghoa`,`thuonghieu`,`soluongtrongkho`,`gianiemyet`,`hinhanh`,`tinhtrang`,`loaihanghoa_id`) VALUES ("Thức Ăn Cho Chó Mọi Lứa Tuổi - Doca Dog 450gr","FRESH TRINO",5,45000,"/image/DocaDog450gr.jpg",1,2);
  INSERT INTO `hanghoa` (`tenhanghoa`,`thuonghieu`,`soluongtrongkho`,`gianiemyet`,`hinhanh`,`tinhtrang`,`loaihanghoa_id`) VALUES ("Thức Ăn Chó Trưởng Thành - Regular Dog 7 450gr","FRESH TRINO",16,40000,"/image/RegularDog7450gr.jpg",1,2);
  INSERT INTO `hanghoa` (`tenhanghoa`,`thuonghieu`,`soluongtrongkho`,`gianiemyet`,`hinhanh`,`tinhtrang`,`loaihanghoa_id`) VALUES ("Thức Ăn Chó Con - Alphatrino 450gr","FRESH TRINO",14,45900,"/image/AlphatrinoPuppy450gr.jpg",1,2);
  INSERT INTO `hanghoa` (`tenhanghoa`,`thuonghieu`,`soluongtrongkho`,`gianiemyet`,`hinhanh`,`tinhtrang`,`loaihanghoa_id`) VALUES ("Thức Ăn Mèo Trưởng Thành - Brutrino 450gr","FRESH TRINO",12,43200,"/image/BrutrinoCat450gr.jpg",1,3);
  INSERT INTO `hanghoa` (`tenhanghoa`,`thuonghieu`,`soluongtrongkho`,`gianiemyet`,`hinhanh`,`tinhtrang`,`loaihanghoa_id`) VALUES ("Thức Ăn Mèo Con - Neutrino Cat 450gr","FRESH TRINO",3,49400,"/image/NeutrinoCat450gr.jpg",1,3);
  INSERT INTO `hanghoa` (`tenhanghoa`,`thuonghieu`,`soluongtrongkho`,`gianiemyet`,`hinhanh`,`tinhtrang`,`loaihanghoa_id`) VALUES ("Sữa Mẹ Khô Cho Mèo - Msbilac Gold Cat 330gr","Châu Thành JSC",4,120000,"/image/MsbilacGoldCat330gr.jpg",1,3);
  INSERT INTO `hanghoa` (`tenhanghoa`,`thuonghieu`,`soluongtrongkho`,`gianiemyet`,`hinhanh`,`tinhtrang`,`loaihanghoa_id`) VALUES ("Thức ăn hạt hữu cơ cho chó ANF 6 Free vị cá hồi 2kg","ANF",5,440000,"/image/ANF6FreeViCaHoi2KgCho .jpg",1,2);
  INSERT INTO `hanghoa` (`tenhanghoa`,`thuonghieu`,`soluongtrongkho`,`gianiemyet`,`hinhanh`,`tinhtrang`,`loaihanghoa_id`) VALUES ("Hạt Dinh Dưỡng Không Ngũ Cốc Vị Vịt Nutriwell Dành Cho Chó Mọi Lứa Tuổi 1.5kg","Jeil PetFood",12,310000,"/image/NutriWellGrainFreeViThit1.5KgCho.jpg",1,2);
  INSERT INTO `hanghoa` (`tenhanghoa`,`thuonghieu`,`soluongtrongkho`,`gianiemyet`,`hinhanh`,`tinhtrang`,`loaihanghoa_id`) VALUES ("Hạt Thức ăn Chức Năng Tốt Cho Sức Khỏe đường Ruột Nature\"s Kitchen (dành Cho Chó Mọi Lứa Tuổi)","ANF",11,360000,"/image/Nature\"sKitchenDog.jpg",1,2);
  INSERT INTO `hanghoa` (`tenhanghoa`,`thuonghieu`,`soluongtrongkho`,`gianiemyet`,`hinhanh`,`tinhtrang`,`loaihanghoa_id`) VALUES ("Thức ăn cho mèo mọi lứa tuổi 5kg - Today\"s dinner","Farmsco Corporation",37,395000,"/image/Today\"sdinner5KgCat.png",1,3);
  INSERT INTO `hanghoa` (`tenhanghoa`,`thuonghieu`,`soluongtrongkho`,`gianiemyet`,`hinhanh`,`tinhtrang`,`loaihanghoa_id`) VALUES ("Bánh thưởng dinh dưỡng cho mèo Gozip vị cá ngừ","Jeil PetFood",10,48000,"/image/GozipViCaNgu60gMeo.jpg",1,3);
  INSERT INTO `hanghoa` (`tenhanghoa`,`thuonghieu`,`soluongtrongkho`,`gianiemyet`,`hinhanh`,`tinhtrang`,`loaihanghoa_id`) VALUES ("TRAVEL - Balo vận chuyển","Pawise",30,547000,"/image/DogBackPack.jpg",1,4);
  INSERT INTO `hanghoa` (`tenhanghoa`,`thuonghieu`,`soluongtrongkho`,`gianiemyet`,`hinhanh`,`tinhtrang`,`loaihanghoa_id`) VALUES ("Cát Vệ Sinh Mèo Siêu Khử Mùi - Diamond Feline 7kg","Diamond Feline",200,200000,"/image/CatVeSinhMeoSieuKhuMuiDiamondFeline7Kg.jpg",1,4);
  INSERT INTO `hanghoa` (`tenhanghoa`,`thuonghieu`,`soluongtrongkho`,`gianiemyet`,`hinhanh`,`tinhtrang`,`loaihanghoa_id`) VALUES ("Sữa Tắm Chó Mèo Mượt Da Lông - DC Guard 500ml","DC Guard",50,130000,"/image/SuaTamChoMeoMuotDaLongDCGuard500ml.jpg",1,4);
  
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
  VALUES ("Nguyễn Minh Việt","1983-06-28","Nam", "1111 đường 3/2, Phường 12, Quận 11","0932478390",43892);
  INSERT INTO `khachhang` (`hoten`,`ngaysinh`,`gioitinh`,`diachi`,`sdt`,`diemtichluy`)
  VALUES ("Hoàng Văn Thu","1968-04-27","Nữ", "333 đường Nguyễn Duy Dương, Phường 4, Quận 10","0793247628",28219);
  INSERT INTO `khachhang` (`hoten`,`ngaysinh`,`gioitinh`,`diachi`,`sdt`,`diemtichluy`)
  VALUES ("Nguyễn Hoàng","1994-04-25","Nam", "Quận 2","0934783203",300);
  INSERT INTO `khachhang` (`hoten`,`ngaysinh`,`gioitinh`,`diachi`,`sdt`,`diemtichluy`)
  VALUES ("Lại Văn Ba","1962-03-26","Nam", "Quận Gò Vấp","0932473592",1512);
  INSERT INTO `khachhang` (`hoten`,`ngaysinh`,`gioitinh`,`diachi`,`sdt`,`diemtichluy`)
  VALUES ("Huỳnh Thị Tuyết Nhi","2000-02-14","Nữ", "Ninh Thuận","0994732629",250);
  INSERT INTO `khachhang` (`hoten`,`ngaysinh`,`gioitinh`,`diachi`,`sdt`,`diemtichluy`)
  VALUES ("Trương Thu Thảo","1998-04-15","Nữ", "Bình Dương","0792348900",900);
  INSERT INTO `khachhang` (`hoten`,`ngaysinh`,`gioitinh`,`diachi`,`sdt`,`diemtichluy`)
  VALUES ("Phan Thị Ngọc","2002-04-14","Nữ", "Quận 3","0793274932",120);
  INSERT INTO `khachhang` (`hoten`,`ngaysinh`,`gioitinh`,`diachi`,`sdt`,`diemtichluy`)
  VALUES ("Võ Chí Năng","2003-09-28","Nam", "Quận Bình Tân","0932479592",99);
  INSERT INTO `khachhang` (`hoten`,`ngaysinh`,`gioitinh`,`diachi`,`sdt`,`diemtichluy`)
  VALUES ("Tô Văn Bé","2001-03-20","Nam", "Quận 8","0932567409",6080);
  
  
  INSERT INTO `thucung` (`ten`,`ngaysinh`,`gioitinh`,`mauLong`,`tinhtrangsuckhoe`,`khachhang_id`)
  VALUES ("Ken","2020-01-01","Đực","Trắng","Khỏe mạnh", 2);
  INSERT INTO `thucung` (`ten`,`ngaysinh`,`gioitinh`,`mauLong`,`tinhtrangsuckhoe`,`khachhang_id`)
  VALUES ("Susu","2020-12-03","Cái","Đen","Khỏe mạnh", 2);
  INSERT INTO `thucung` (`ten`,`ngaysinh`,`gioitinh`,`mauLong`,`tinhtrangsuckhoe`,`khachhang_id`)
  VALUES ("Oshi","2018-04-26","Đực","Vàng","Khỏe mạnh", 6);
  
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
  
  
