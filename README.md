# Char Analyzer REST API (Spring Boot)

Aplikasi REST API berbasis **Spring Boot** untuk melakukan analisis karakter pada kalimat (menghitung total huruf alfabet dan frekuensi kemunculan tiap karakter) serta menyimpan riwayat analisisnya ke database **PostgreSQL**.

---

## 🛠️ Teknologi & Kebutuhan Sistem

- **Java:** JDK 21
- **Framework:** Spring Boot (Spring Web MVC, Spring Data JPA)
- **Database:** PostgreSQL 17 (via Docker Compose)
- **Tools:** Lombok, Maven Wrapper (`mvnw`)
- **Port Aplikasi:** `8081`

---

## ⚙️ Konfigurasi Database (`application.yaml`)

Aplikasi menggunakan konfigurasi PostgreSQL berikut:

```yaml
spring:
  application:
    name: helloworld
  datasource:
    url: jdbc:postgresql://localhost:5432/belajar_db
    username: dev
    password: devpassword
  jpa:
    hibernate:
      ddl-auto: update
    show-sql: true

server:
  port: 8081
```

---

## 🚀 Cara Menjalankan Aplikasi

1. **Jalankan Database PostgreSQL (Docker):**
   ```powershell
   cd D:\Docker\databases
   docker compose up -d
   ```

2. **Jalankan Aplikasi Spring Boot:**
   ```powershell
   cd D:\coding\java\springboot\helloworld
   .\mvnw.cmd spring-boot:run
   ```

Aplikasi akan aktif dan dapat diakses di: `http://localhost:8081`

---

## 📚 Dokumentasi Endpoint API

Semua respons API dibungkus menggunakan format standar `BaseResponse<T>`:

```json
{
  "status": "success",
  "message": "OK",
  "data": { ... }
}
```

---

### 1. Simpan & Analisis Kalimat
Menganalisis kalimat (menghitung frekuensi huruf & total huruf), menyimpannya ke tabel `mst_char_analysis`, dan mencatat header ke log console.

- **Method:** `POST`
- **URL:** `/api/v1`
- **Headers:**
  - `Content-Type: application/json`
  - `X-Request-Id: req-001` *(Opsional)*
- **Request Body:**
  ```json
  {
    "kalimat": "gajah mati meninggalkan gading"
  }
  ```
- **Response (200 OK):**
  ```json
  {
    "status": "success",
    "message": "OK",
    "data": {
      "id": 1,
      "kalimat": "gajah mati meninggalkan gading",
      "totalHuruf": 27,
      "frekuensi": {
        "a": 6,
        "d": 1,
        "e": 1,
        "g": 5,
        "h": 1,
        "i": 3,
        "j": 1,
        "k": 1,
        "l": 1,
        "m": 2,
        "n": 4,
        "t": 1
      }
    }
  }
  ```

---

### 2. Cari Semua / Filter Kalimat
Mengambil seluruh data analisis atau memfilter berdasarkan kata tertentu (case-insensitive).

- **Method:** `GET`
- **URL:** 
  - Semua data: `/api/v1`
  - Dengan pencarian: `/api/v1?kalimat=gajah`
- **Response (200 OK):**
  ```json
  {
    "status": "success",
    "message": "OK",
    "data": [
      {
        "id": 1,
        "kalimat": "gajah mati meninggalkan gading",
        "totalHuruf": 27,
        "frekuensi": { "a": 6, "d": 1, ... }
      }
    ]
  }
  ```

---

### 3. Detail Analisis Berdasarkan ID
Mengambil data detail analisis berdasarkan ID tertentu.

- **Method:** `GET`
- **URL:** `/api/v1/{id}`  *(Contoh: `/api/v1/1`)*
- **Response Jika Ditemukan (200 OK):**
  ```json
  {
    "status": "success",
    "message": "OK",
    "data": {
      "id": 1,
      "kalimat": "gajah mati meninggalkan gading",
      "totalHuruf": 27,
      "frekuensi": { ... }
    }
  }
  ```
- **Response Jika Tidak Ditemukan (200 OK):**
  ```json
  {
    "status": "failed",
    "message": "Data dengan id 99 tidak ditemukan",
    "data": null
  }
  ```

---

### 4. Filter Minimal Total Huruf (Query JPQL)
Mengambil data analisis yang memiliki `totalHuruf` lebih besar atau sama dengan nilai `min`.

- **Method:** `GET`
- **URL:** `/api/v1/minimal?min=10`
- **Response (200 OK):**
  ```json
  {
    "status": "success",
    "message": "OK",
    "data": [
      {
        "id": 1,
        "kalimat": "gajah mati meninggalkan gading",
        "totalHuruf": 27,
        "frekuensi": { ... }
      }
    ]
  }
  ```

---

### 5. Ambil 5 Data Analisis Terbaru (Query Native SQL)
Mengambil 5 riwayat data analisis paling baru berdasarkan kolom `created_at`.

- **Method:** `GET`
- **URL:** `/api/v1/terbaru`
- **Response (200 OK):**
  ```json
  {
    "status": "success",
    "message": "OK",
    "data": [
      {
        "id": 1,
        "kalimat": "gajah mati meninggalkan gading",
        "totalHuruf": 27,
        "frekuensi": { ... }
      }
    ]
  }
  ```

---

## 📁 Struktur Paket Proyek

```text
src/main/java/com/bootcamp19/helloworld/
├── controller/
│   └── AnalyzerController.java       # Menangani routing HTTP & request/response
├── dto/
│   ├── request/
│   │   └── CharAnalysisRequest.java  # DTO input kalimat dari client
│   └── response/
│       ├── BaseResponse.java         # Pembungkus response standar API
│       └── CharAnalysisResponse.java # DTO output hasil analisis karakter
├── entity/
│   └── CharAnalysis.java             # Entity ORM JPA tabel mst_char_analysis
├── repository/
│   └── CharAnalysisRepository.java   # Spring Data JPA (Method query, JPQL, Native)
├── service/
│   ├── CharAnalysisService.java      # Interface kontrak logika bisnis
│   └── impl/
│       └── CharAnalysisServiceImpl.java # Implementasi logika bisnis & kalkulasi
└── util/
    └── CharCounter.java              # Helper utilitas perhitungan frekuensi karakter
```

---

## 🏛️ Ketentuan & Best Practice yang Diterapkan
1. **Interface-Driven Development:** Setiap method dideklarasikan di interface `CharAnalysisService` terlebih dahulu sebelum diimplementasikan di `CharAnalysisServiceImpl`.
2. **Separation of Concerns:** Controller hanya bertugas memanggil Service, tanpa menyentuh Repository atau class utilitas secara langsung.
3. **Constructor Injection:** Menggunakan `@RequiredArgsConstructor` dari Lombok untuk dependency injection yang aman dan bersih (*immutability*).
4. **Clean Code & Encapsulation:** DTO Request membatasi input client hanya pada field yang diizinkan (`kalimat`).
