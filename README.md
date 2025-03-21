### Permission
**Khi mở ứng dụng và click vào button (giả sử cần cấp quyền để thực hiện chức năng):**
- Nếu người dùng chọn "Allow when using app", hệ thống sẽ lưu quyền vào thiết bị và cập nhật thông tin lên giao diện khi click lại button (quyền sẽ được cấp mỗi khi mở app mà không yêu cầu cấp quyền cho lần sau).
- Nếu người dùng chọn "Only this time (một số quyền nhất định (Android 11+) như: location, camera, audio, ...)", quyền sẽ được cấp tạm thời và sẽ lấy lại quyền khi thoát ứng dụng hoặc trong thời gian quy định bởi hệ thống.
- Nếu người dùng chọn don't allow, hệ thống sẽ ghi nhận người dùng từ chối quyền. Nếu click thêm lần nữa hệ thống sẽ hiển thị hộp thoại cho người dùng đến cấp quyền thủ công.

**Ghi chú**
- Phiên bản Android 15 cho phép người dùng cấp quyền cho vị trí với 2 loại vị trí (chính xác và tương đối).
- Từ phiên bản Android 11+ nếu người dùng từ chối 2 lần liên tục được coi là từ chối vĩnh viễn (phải cấp quyền thủ công).
- Phiên bản dưới Android 9, nếu chọn mục "Dont show again" mới được xem là từ chối vĩnh viễn.