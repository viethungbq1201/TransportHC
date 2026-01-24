# TransportHC

## Giới thiệu

**TransportHC** là dự án backend được xây dựng trong quá trình **thực tập tại công ty Inter ITS**, nhằm phát triển một hệ thống quản lý vận tải và kho hàng phục vụ cho doanh nghiệp logistics.

Hệ thống hỗ trợ toàn diện các nghiệp vụ:

* Quản lý nhập – xuất kho và tồn kho sản phẩm
* Quản lý sản phẩm, danh mục
* Quản lý tài xế, xe tải và tuyến đường vận chuyển
* Tạo và xử lý giao dịch vận chuyển hàng hóa
* Lập lịch, theo dõi trạng thái vận chuyển
* Quản lý chi phí phát sinh trong quá trình vận chuyển
* Báo cáo, thống kê phục vụ công tác quản lý

Dự án được xây dựng theo mô hình **RESTful API**, sử dụng **Spring Boot**, áp dụng kiến trúc **3-layer (Controller – Service – Repository)** và các chuẩn bảo mật hiện đại.

---

## Công nghệ sử dụng

* Java 17
* Spring Boot
* Spring Data JPA
* Spring Security + JWT
* Query DSL
* Maven
* MySQL
* Lombok

---

## Ứng dụng sủ  dụng

* IntelliJ
* Postman
* Docker Desktop
* DBeaver

---

## Kiến trúc hệ thống

Dự án áp dụng **Layered Architecture**:

* **Controller**: Tiếp nhận request, trả response cho client
* **Service**: Xử lý logic nghiệp vụ
* **Repository**: Truy vấn và thao tác dữ liệu
* **Entity / DTO**: Đại diện dữ liệu và truyền dữ liệu giữa các layer

Hệ thống được thiết kế theo chuẩn **RESTful**, dễ mở rộng và bảo trì.

---

## Authentication & Authorization

Hệ thống sử dụng **JWT (JSON Web Token)** để xác thực và phân quyền:

* Người dùng đăng nhập nhận access token và refresh token
* Access token dùng để truy cập các API bảo vệ
* Refresh token dùng để làm mới access token khi hết hạn
* Phân quyền truy cập API dựa trên vai trò người dùng

---

## Validation dữ liệu

* Kiểm tra dữ liệu bắt buộc
* Kiểm tra định dạng và giá trị hợp lệ
* Trả về thông báo lỗi rõ ràng khi dữ liệu không hợp lệ

Giúp đảm bảo tính toàn vẹn dữ liệu và hạn chế lỗi trong quá trình xử lý nghiệp vụ.

---

## Database Migration

Hệ thống áp dụng cơ chế migration để:

* Quản lý version database
* Đồng bộ cấu trúc dữ liệu giữa các môi trường
* Hỗ trợ mở rộng và bảo trì hệ thống

---

## Repository & Query DSL

* Repository được tách riêng theo từng domain (User, Inventory, Transaction, ...)
* Sử dụng Query DSL để xây dựng truy vấn động
* Hỗ trợ lọc, tìm kiếm và thống kê dữ liệu linh hoạt

---

## Chức năng chính

### 1. Authenticate

* Đăng nhập
* Đăng xuất
* Làm mới token

### 2. User

* Tạo người dùng
* Xem danh sách / chi tiết
* Cập nhật thông tin
* Cập nhật trạng thái
* Xóa người dùng

### 3. Truck

* Thêm xe tải
* Xem danh sách / chi tiết
* Cập nhật thông tin
* Cập nhật trạng thái
* Xóa xe

### 4. Route

* Thêm tuyến đường
* Xem danh sách
* Cập nhật
* Xóa

### 5. Category

* Thêm danh mục
* Xem danh sách
* Cập nhật
* Xóa

### 6. Product

* Thêm sản phẩm
* Xem danh sách / chi tiết
* Cập nhật
* Xóa

### 7. Inventory

* Nhập kho
* Xem tồn kho
* Tìm kiếm theo sản phẩm
* Cập nhật tồn kho
* Xóa dữ liệu
* Lọc dữ liệu
* Import / Export dữ liệu kho

### 8. Transaction

* Tạo giao dịch
* Xem danh sách
* Cập nhật giao dịch
* Duyệt / Từ chối giao dịch
* Xóa giao dịch

### 9. Transaction Detail

* Thêm chi tiết giao dịch
* Xem chi tiết
* Cập nhật
* Xóa

### 10. Schedule

* Tạo lịch vận chuyển
* Xem lịch
* Cập nhật
* Duyệt / Từ chối lịch
* Kết thúc lịch
* Hủy lịch
* Xóa lịch

### 11. Cost Type

* Thêm loại chi phí
* Xem danh sách
* Cập nhật
* Xóa

### 12. Cost

* Thêm chi phí
* Xem danh sách
* Cập nhật
* Duyệt / Từ chối chi phí
* Xóa

### 13. Salary Report

* Tạo báo cáo lương cho 1 tài xế
* Tạo báo cáo lương cho tất cả tài xế
* Xem danh sách / chi tiết
* Cập nhật
* Xóa
* Kiểm tra trạng thái hoàn thành

### 14. Report

* Báo cáo chi phí theo xe
* Báo cáo chi phí toàn bộ xe
* Báo cáo thưởng theo xe
* Báo cáo thưởng toàn bộ xe
* Báo cáo lịch vận chuyển
* Thống kê số chuyến
* Báo cáo chi phí theo tài xế

---

## Logic nghiệp vụ chính

### Quản lý giao dịch

* Tạo giao dịch mới
* Thêm nhiều sản phẩm vào một giao dịch
* Giao dịch cần được duyệt trước khi thực hiện
* Khi giao dịch được chấp nhận:

  * Phân công tài xế, xe tải và tuyến đường
  * Tạo lịch vận chuyển tương ứng

### Quản lý lịch vận chuyển

* Lịch vận chuyển có thể:

  * Được duyệt và chuyển sang trạng thái **đang giao hàng**
  * Bị từ chối hoặc hủy
* Khi lịch kết thúc, xác nhận trạng thái hoàn thành

### Quản lý chi phí vận chuyển

* Trong quá trình vận chuyển:

  * Tài xế có thể ghi nhận các chi phí phát sinh theo chuyến đi
  * Chi phí cần được duyệt trước khi tính toán

### Cập nhật tồn kho

* Khi đơn hàng giao thành công:

  * Trừ số lượng sản phẩm trong kho
* Khi đơn hàng bị hủy hoặc hoàn:

  * Hoàn trả số lượng tồn kho về giá trị ban đầu

Đảm bảo dữ liệu kho luôn chính xác theo trạng thái đơn hàng.

---

## Tác giả

**Việt Hùng**
Sinh viên thực tập tại **Inter ITS**
