# Software Design Patterns in Java 

Bu repo, Nesne Yönelimli Programlama (OOP) ilkelerine sadık kalarak sık karşılaşılan mimari problemleri çözmek, kodun esnekliğini artırmak ve bağımlılıkları yönetmek amacıyla uygulanan **Tasarım Desenleri (Design Patterns)** örneklerini içerir.

---

## 1. Strategy Pattern (Strateji Deseni)

> **Kategori:** Davranışsal (Behavioral)  
> **Temel Amaç:** Bir işi yapmanın birden fazla yolu olduğunda, bu algoritmaları ayrı sınıflara paketleyip çalışma anında (runtime) dinamik olarak değiştirilebilir kılmak.

### 📌 Senaryo & Problem
Bir e-ticaret ödeme sistemi düşünelim. Sepetteki ürünler onaylandığında kullanıcı farklı yöntemlerle ödeme yapmak isteyebilir:
* Kredi Kartı
* PayPal
* Kripto Para

#### ❌ Kötü Tasarım (Anti-pattern)
`PaymentService` adında dev bir sınıf açıp içinde `if-else` veya `switch-case` bloklarıyla `if (type == CREDIT_CARD)` şeklinde dallanmak:
* **Open/Closed Prensibi İhlali:** Yarın sisteme *Apple Pay* eklendiğinde var olan sınıfı açıp kodları değiştirmek gerekir.
* **Single Responsibility Prensibi İhlali:** Farklı sağlayıcıların API çağrıları, komisyon hesapları ve validasyonları tek bir sınıfta birbirine karışır.

#### ✅ Strategy Deseni Çözümü
Değişen davranış (ödeme yapma algoritması) `PaymentStrategy` isimli bir interface arkasına gizlenir. `ShoppingCart` (Context) sınıfı somut sınıflara değil, sadece bu arayüze bağımlı olur. Kullanıcı ödeme yöntemini çalışma anında `setPaymentStrategy(...)` ile değiştirir.

### 🏗️ Sınıf Rolleri
* **Strategy Interface (`PaymentStrategy`):** Algoritmaların uyması gereken ortak sözleşme (`pay(amount)`).
* **Concrete Strategies (`CreditCardPayment`, `PayPalPayment`, `CryptoPayment`):** Kendi verisini tutup davranışı somutlaştıran sınıflar.
* **Context (`ShoppingCart`):** Strateji nesnesine referans tutan ve ödemeyi bu arayüz üzerinden tetikleyen istemci katmanı.

---

## 2. Observer Pattern (Gözlemci Deseni)

> **Kategori:** Davranışsal (Behavioral)  
> **Temel Amaç:** Bir nesnede meydana gelen durum değişikliğini, onu takip eden tüm bağımlı nesnelere otomatik olarak bildiren bire-çok (one-to-many / publish-subscribe) ilişki kurmak.

### 📌 Senaryo & Problem
Bir YouTube kanalı ya da e-ticaretteki stok bildirim sistemini ele alalım. Kanalda yeni bir video yayınlandığında abone olan kullanıcılara anında haber verilmelidir.

#### ❌ Kötü Tasarım (Anti-pattern)
* **Polling (Sürekli Sorgulama):** Kullanıcıların her 5 saniyede bir kanala gidip *"Yeni video var mı?"* diye sorması (aşırı kaynak tüketimi).
* **Tight Coupling (Sıkı Bağımlılık):** `YoutubeChannel` sınıfının içinde `EmailUser`, `SmsUser` gibi somut sınıfları tek tek referans verip bir liste tutması. Yeni bir bildirim kanalı (ör. Telegram botu) geldiğinde kanal kodunun baştan yazılması gerekir.

#### ✅ Observer Deseni Çözümü
* **Subject (Yayıncı - `YoutubeChannel`):** Sadece kendisine kayıtlı `Observer` listesini tutar. Yeni video yüklendiğinde listedeki herkesin `update(...)` metodunu döngüyle tetikler.
* **Observer (Dinleyici - `Observer`):** Bildirim alma sözleşmesidir (`update(title)`).
* **Concrete Observers (`EmailSubscriber`, `MobileAppSubscriber`):** Mesaj geldiğinde kendi işini (mail atma, push bildirim basma) yapan sınıflardır. Yayıncı, bildirimin nasıl iletildiğiyle ilgilenmez.

### 🏗️ Sınıf Rolleri
* **Subject (`Subject` / `YoutubeChannel`):** Aboneleri kaydeder (`register`), siler (`remove`) ve haberi duyurur (`notify`).
* **Observer (`Observer`):** Haberi dinleyenlerin ortak arayüzü.
* **Concrete Observers (`EmailSubscriber`, `MobileAppSubscriber`):** Haberi alıp tüketiciye ileten somut sınıflar.

---
