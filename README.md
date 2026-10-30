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
1. **Strategy (`PaymentStrategy`):** Algoritmaların uyması gereken ortak arayüz sözleşmesi (`pay(amount)`).
2. **Concrete Strategy (`CreditCardPayment`, `PayPalPayment`, `CryptoPayment`):** Kendi verisini tutup algoritmayı somutlaştıran sınıflar.
3. **Context (`ShoppingCart`):** Seçilen stratejiyi bünyesinde barındıran ve ödeme işini bu stratejiye delege eden sınıf.
4. **Client (`Main`):** Somut stratejiyi seçip Context'e enjekte eden ve akışı başlatan istemci.

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
1. **Subject Interface (`Subject`):** Abone kaydı, silme ve bildirim sözleşmesi.
2. **Concrete Subject (`YoutubeChannel`):** Takip edilen durumu saklayan ve değişim anında listedeki tüm aboneleri haberdar eden yayıncı sınıf.
3. **Observer Interface (`Observer`):** Bildirimleri yakalamak için tanımlanan ortak dinleyici arayüzü (`update(title)`).
4. **Concrete Observer (`EmailSubscriber`, `MobileAppSubscriber`):** Haberi aldığında kendi bildirim yöntemini (mail, push vb.) çalıştıran somut sınıflar.
---

## 3. Decorator Pattern (Dekoratör Deseni)

> **Kategori:** Yapısal (Structural)  
> **Temel Amaç:** Mevcut nesnelerin kodunu değiştirmeden veya kalıtım (inheritance) yoluyla sınıf patlamasına yol açmadan, çalışma anında (runtime) nesnelere dinamik olarak yeni sorumluluklar ve davranışlar eklemek.

### 📌 Senaryo & Problem
Bir kahve dükkanı sipariş sistemi (Starbucks modeli) düşünelim. Müşteri temel bir kahve (Espresso, Filtre Kahve) seçtikten sonra üzerine çeşitli ek malzemeler ekletmek isteyebilir:
* Süt
* Karamel Şurubu
* Çikolata Sosu (Mocha)
* Fındık Şurubu

#### ❌ Kötü Tasarım (Anti-pattern - Sınıf Patlaması / Class Explosion)
Her varyasyon için kalıtım (`extends`) kullanarak ayrı bir alt sınıf türetmeye çalışmak:
* `EspressoWithMilk`
* `EspressoWithMilkAndCaramel`
* `FilterCoffeeWithMochaAndMilk`
* Malzeme ve kahve çeşitleri arttıkça onlarca/yüzlerce kombinasyonel sınıf oluşur; kod yönetilemez ve katı hale gelir.

#### ✅ Decorator Deseni Çözümü (Composition over Inheritance)
Temel bileşen (`Espresso`) bir çekirdek nesne olarak kalır. Ek malzemeler ise bu nesneyi bir **soğan kabuğu veya matruşka bebek gibi katman katman sarmalayan (wrap eden)** dekoratörlerdir. 

Dekoratör sınıfı (`BeverageDecorator`), sarmaladığı nesneyle **aynı arayüzü (`Beverage`) uygular (implements)** ve içinde bir `Beverage` referansı barındırır. Bu sayede sarmalanmış nesne de dışarıdan hâlâ bir kahve gibi davranır ve istenen sayıda dekoratör ardı ardına zincirlenebilir.

### 🏗️ Sınıf Rolleri
1. **Component Interface (`Beverage`):** Hem çekirdek kahvelerin hem de dekoratörlerin paylaştığı ortak sözleşme (`getDescription()`, `getCost()`).
2. **Concrete Component (`Espresso`, `FilterCoffee`):** Üzerine ek özellikler eklenebilen temel, somut sınıflar.
3. **Decorator (`BeverageDecorator`):** `Component` arayüzünü uygulayan ve bünyesinde sarmalanacak bir `Component` referansı barındıran soyut köprü sınıf.
4. **Concrete Decorator (`MilkDecorator`, `CaramelDecorator`):** Çekirdek nesneye çalışma anında ek maliyet ve yeni açıklamalar kazandıran somut sarmalayıcı sınıflar.

---

## 4. Factory Pattern (Fabrika Deseni)

> **Kategori:** Yaratımsal (Creational)  
> **Temel Amaç:** Nesne oluşturma mantığını istemci kodundan ayırarak, hangi somut sınıfın örnekleneceğine karar verme işini tek bir noktada (fabrikada) toplamak.

### 📌 Senaryo & Problem
Farklı taşıma yöntemlerine (Kamyon, Gemi, Uçak) sahip bir kargo lojistik platformu düşünelim.

#### ❌ Kötü Tasarım (Anti-pattern)
İstemcinin (`Main`, `OrderService` vb.) nesneleri doğrudan kodun içine dağılmış `new Truck()`, `new Ship()` çağrılarıyla üretmesi:
* **Tight Coupling (Sıkı Bağımlılık):** İstemci, tüm somut sınıfların constructor detaylarına bağımlı hale gelir.
* **Değişim Zorluğu:** Yeni bir taşıma aracı (`Drone`) eklendiğinde projenin farklı yerlerindeki tüm `if-else` bloklarını tek tek arayıp değiştirmek gerekir.

#### ✅ Factory Deseni Çözümü
Nesne üretimi `TransportFactory` sınıfına delege edilir. İstemci yalnızca genel ürün arayüzünü (`Transport`) bilir ve fabrikadan parametre ile nesne talep eder. Yeni bir araç eklendiğinde sadece fabrika sınıfı güncellenir; istemci koduna dokunulmaz.

### 🏗️ Sınıf Rolleri
1. **Product Interface (`Transport`):** Üretilecek tüm nesnelerin ortak davranışını tanımlayan sözleşme (`deliver()`).
2. **Concrete Products (`Truck`, `Ship`, `Plane`):** Arayüzü uygulayan gerçek nesneler.
3. **Creator / Factory (`TransportFactory`):** İstemciden gelen parametreye göre uygun somut nesneyi üretip `Product` arayüzü tipinde döndüren sınıf.
4. **Client (`Main`):** Fabrikayı çağırıp dönen nesneyi soyut arayüzü üzerinden kullanan istemci.
