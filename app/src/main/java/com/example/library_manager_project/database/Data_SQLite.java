package com.example.library_manager_project.database;

public class Data_SQLite {
    public static final  String INSERT_THU_THU= "insert into ThuThu(maTT, hoTen, matKhau) values" +
            "('admin','admin', 'admin')," +
            "('hungnt','Nguyễn Thái Hưng', '123456')," +
            "('datnv','Nguyễn Văn Đạt', '123456')," +
            "('phuongvtt','Vũ Thị Thu Phương', '123456')";

    public static final String INSERT_THANH_VIEN = "insert into thanhvien(hoTen, namSinh) values" +
            "('Nguyễn Đăng Hạnh','2000')," +
            "('Nguyễn Thảo Chi','2002')," +
            "('Đào Đức Thắng','2001')," +
            "('Đàm Ngọc Hà','2002')," +
            "('Đỗ Hà Nhi','1999')," +
            "('Nguyễn Văn Huy','2001')," +
            "('Đỗ Việt Hoàng','1988')," +
            "('Nguyễn Ngọc Hiếu','2006')," +
            "('Trịnh Viết Hiếu','2005')," +
            "('Đào Minh Hoàng','2003')," +
            "('Đỗ Thị Cúc','1987')," +
            "('Trần Xuân Bách','1997')," +
            "('Đỗ Ngọc Duy','2003')," +
            "('Chu Quang Long','2003')," +
            "('Nguyễn Quốc Khánh','2000')," +
            "('Nguyễn Long Vũ','2001')";
    public static final String INSERT_LOAI_SACH = "insert into LoaiSach(tenloai) values" +
            "('Ngoại ngữ')," +
            "('Khoa học')," +
            "('Toán')," +
            "('Lập trình')," +
            "('Tiểu thuyết')," +
            "('Truyện tranh')";
    public static final String INSERT_PHIEU_MUON = "insert into PhieuMuon(matt, matv, masach,tienthue, ngay, trasach) values" +
            "('admin','1','1','2000','2021/10/07',0)," +
            "('hungnt','2','3','2000','2023/12/15',1)," +
            "('datnv','3','3','3500','2023/12/11',1)," +
            "('phuongvtt','2','2','5500','2023/11/23',1)," +
            "('phuongvtt','4','2','1500','2024/01/18',0)," +
            "('hungnt','1','10','2000','2024/02/15',1)," +
            "('phuongvtt','2','10','5500','2024/2/16',0)," +
            "('datnv','6','11','3500','2023/12/28',1)," +
            "('phuongvtt','13','2','5500','2023/12/28',1)," +
            "('hungnt','1','14','2000','2024/01/10',1)," +
            "('datnv','7','12','3500','2024/03/11',0)," +
            "('hungnt','9','11','2000','2024/01/10',0)," +
            "('hungnt','9','1','2700','2023/12/2',1)";
    public static final String INSERT_SACH = "insert into Sach(tensach, giathue, maloai) values" +
            "('Tiếng anh course 1', '2000', '1')," +
            "('Tiếng anh course 2', '2000','1')," +
            "('Tiếng anh từ con số 0', '2000','1')," +
            "('Nguồn gốc các loài', '2500','2')," +
            "('Súng, vi trùng và thép', '2500','2')," +
            "('Bản thiết kế vĩ đại', '2500','2')," +
            "('10 vạn câu hỏi vì sao', '3000','2')," +
            "('Toán đại cương', '4000','3')," +
            "('Giải tích', '4000','3')," +
            "('Toán rời rạc', '4000','3')," +
            "('Lập trình Java', '4200','4')," +
            "('Lập trình C/C++', '2600','4')," +
            "('Lập trình Python', '4000','4'),"+
            "('Lập trình hướng đối tượng', '4000','4'),"+
            "('Đi tìm thời gian đã mất', '3300','4'),"+
            "('Anna Karenina', '2700','5'),"+
            "('Chiến tranh và Hòa bình', '8000','5'),"+
            "('Truyện ngắn của Anton Chekhov', '5000','5'),"+
            "('Bà Bovary', '2000','5'),"+
            "('Đấu trường môn nhiệt đới', '2400','6'),"+
            "('Cảnh Sát Kỳ Tài', '3000','6'),"+
            "('Siêu nhí Astro', '7000','6'),"+
            "('Thám tử học đường', '5000','6')";

}
