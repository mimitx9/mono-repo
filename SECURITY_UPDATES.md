# Security Updates - Cập Nhật Bảo Mật

## Tổng Quan

Dự án đã được cập nhật để khắc phục các lỗ hổng bảo mật nghiêm trọng trong dependencies.

## Các Lỗ Hổng Đã Được Khắc Phục

### 1. gRPC Netty Shaded - CVE-2025-55163 (GHSA-prj3-ccx8-p6x4)

**Mức độ:** 8.2 (High)

**Mô tả:**
- Lỗ hổng DDoS "MadeYouReset" trong giao thức HTTP/2
- Kẻ tấn công có thể gửi các khung điều khiển HTTP/2 bị lỗi để vượt qua giới hạn luồng đồng thời tối đa
- Dẫn đến cạn kiệt tài nguyên và tấn công từ chối dịch vụ

**Giải pháp:**
- ✅ Cập nhật từ `1.64.0` → `1.75.0`
- ✅ Phiên bản mới đã cập nhật Netty lên `4.1.124.Final` (đã vá lỗ hổng)

### 2. Protobuf Java - GHSA-735f-pc8j-v9w8

**Mức độ:** 7.5 (High)

**Mô tả:**
- Lỗ hổng tiềm ẩn về Denial of Service (DoS)
- Có thể dẫn đến tấn công từ chối dịch vụ

**Giải pháp:**
- ✅ Cập nhật từ `3.25.3` → `4.28.2`

## Các Thay Đổi Chi Tiết

### File: `api/build.gradle`

#### 1. gRPC Dependencies

**Trước:**
```gradle
force 'io.grpc:grpc-core:1.64.0'
force 'io.grpc:grpc-api:1.64.0'
force 'io.grpc:grpc-stub:1.64.0'
force 'io.grpc:grpc-protobuf:1.64.0'
force 'io.grpc:grpc-netty-shaded:1.64.0'
force 'io.grpc:grpc-inprocess:1.64.0'
force 'io.grpc:grpc-services:1.64.0'
force 'io.grpc:grpc-protobuf-lite:1.64.0'
force 'io.grpc:grpc-util:1.64.0'
force 'io.grpc:grpc-context:1.64.0'

implementation 'io.grpc:grpc-core:1.64.0'
implementation 'io.grpc:grpc-stub:1.64.0'
implementation 'io.grpc:grpc-protobuf:1.64.0'
implementation 'io.grpc:grpc-netty-shaded:1.64.0'
implementation 'io.grpc:grpc-services:1.64.0'
```

**Sau:**
```gradle
force 'io.grpc:grpc-core:1.75.0'
force 'io.grpc:grpc-api:1.75.0'
force 'io.grpc:grpc-stub:1.75.0'
force 'io.grpc:grpc-protobuf:1.75.0'
force 'io.grpc:grpc-netty-shaded:1.75.0'
force 'io.grpc:grpc-inprocess:1.75.0'
force 'io.grpc:grpc-services:1.75.0'
force 'io.grpc:grpc-protobuf-lite:1.75.0'
force 'io.grpc:grpc-util:1.75.0'
force 'io.grpc:grpc-context:1.75.0'

implementation 'io.grpc:grpc-core:1.75.0'
implementation 'io.grpc:grpc-stub:1.75.0'
implementation 'io.grpc:grpc-protobuf:1.75.0'
implementation 'io.grpc:grpc-netty-shaded:1.75.0'
implementation 'io.grpc:grpc-services:1.75.0'
```

#### 2. Protobuf Dependencies

**Trước:**
```gradle
implementation 'com.google.protobuf:protobuf-java:3.25.3'
protobuf 'com.google.protobuf:protoc:3.25.3'

protoc {
    artifact = 'com.google.protobuf:protoc:3.25.3'
}
plugins {
    grpc {
        artifact = 'io.grpc:protoc-gen-grpc-java:1.64.0'
    }
}
```

**Sau:**
```gradle
implementation 'com.google.protobuf:protobuf-java:4.28.2'
protobuf 'com.google.protobuf:protoc:4.28.2'

protoc {
    artifact = 'com.google.protobuf:protoc:4.28.2'
}
plugins {
    grpc {
        artifact = 'io.grpc:protoc-gen-grpc-java:1.75.0'
    }
}
```

## Lưu Ý Quan Trọng

### Breaking Changes

**Protobuf 3.x → 4.x:**
- Protobuf 4.x có một số breaking changes so với 3.x
- Cần kiểm tra và test kỹ các proto files và generated code
- Một số API có thể đã thay đổi

**Khuyến nghị:**
1. ✅ Chạy full test suite sau khi update
2. ✅ Kiểm tra các proto files có hoạt động đúng không
3. ✅ Kiểm tra generated code có compile được không
4. ✅ Test các gRPC services có hoạt động bình thường không

### Testing Checklist

Sau khi update, cần test:

- [ ] Build project thành công
- [ ] Proto files được generate đúng
- [ ] gRPC services hoạt động bình thường
- [ ] Tất cả unit tests pass
- [ ] Integration tests pass
- [ ] Không có runtime errors

## Cách Kiểm Tra

### 1. Kiểm tra dependencies đã được update

```bash
./gradlew dependencies --configuration runtimeClasspath | grep -E "(grpc|protobuf)"
```

### 2. Build project

```bash
./gradlew clean build
```

### 3. Chạy tests

```bash
./gradlew test
```

### 4. Kiểm tra security vulnerabilities

Sử dụng các tools như:
- OWASP Dependency-Check
- Snyk
- GitHub Dependabot
- Checkmarx

## Tài Liệu Tham Khảo

- [gRPC Security Advisory](https://github.com/grpc/grpc-java/security/advisories)
- [Protobuf Security Advisory](https://github.com/protocolbuffers/protobuf/security/advisories)
- [CVE-2025-55163](https://nvd.nist.gov/vuln/detail/CVE-2025-55163)
- [GHSA-prj3-ccx8-p6x4](https://github.com/advisories/GHSA-prj3-ccx8-p6x4)
- [GHSA-735f-pc8j-v9w8](https://github.com/advisories/GHSA-735f-pc8j-v9w8)

## Ngày Cập Nhật

**Ngày:** $(date +%Y-%m-%d)
**Phiên bản gRPC:** 1.64.0 → 1.75.0
**Phiên bản Protobuf:** 3.25.3 → 4.28.2

