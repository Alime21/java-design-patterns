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

## 4. Factory Patterns (Fabrika Desenleri)

> **Kategori:** Yaratımsal (Creational)  
> **Temel Felsefe:** Doğrudan `new` anahtar kelimesiyle nesne oluşturmak, istemciyi somut sınıflara sıkı sıkıya bağlar (Tight Coupling). Değişen nesne yaratım süreçlerini değişmeyen iş kurallarından soyutlamak gerekir.

Nesne yaratımının yönetimi projelerin ölçeğine ve gereksinimlerine göre 3 aşamalı bir mimari evrimle ele alınır:

---

### A. Simple Factory (Basit Fabrika)
* **Amaç:** Kod içine dağılmış `new` çağrılarını tek bir merkezi sınıfa taşımak.
* **Mekanizma:** `switch-case` veya `if-else` blokları içeren statik bir yardımcı metot (`TransportFactory.createTransport(type)` veya `SimplePizzaFactory.createPizza(type)`) aracılığıyla istemciye arayüz tipinde somut ürün döner.
* **Durum:** Resmi bir Gang of Four (GoF) deseni değildir; kod kalitesini artıran yaygın bir programlama alışkanlığıdır (idiom).

---

### B. Factory Method Pattern (Fabrika Metodu Deseni - GoF)

> **GoF Tanımı:** *"Nesne oluşturmak için bir arayüz tanımlar, ancak hangi sınıfın örnekleneceğine alt sınıfların karar vermesine izin verir. Factory Method, bir sınıfın örneklemeyi alt sınıflara ertelemesini sağlar."*

#### 📌 Senaryo & Problem
Pizza dükkanının New York (`NYPizzaStore`) ve Chicago (`ChicagoPizzaStore`) gibi farklı bölgelerde şubeler açması.
* Her iki şubede de sipariş karşılama, fırınlama, dilimleme ve kutulama akışı aynıdır.
* Ancak New York şubesi ince hamurlu pizza (`NYStyleCheesePizza`), Chicago şubesi ise kalın hamurlu pizza (`ChicagoStyleCheesePizza`) üretmek zorundadır.

#### ❌ Kötü Tasarım (Anti-pattern)
`orderPizza` metodunun içinde tüm bölgesel varyasyonları devasa `if-else` bloklarıyla kontrol etmeye çalışmak veya her yeni şube için tüm sipariş akışını baştan yazmak.

#### ✅ Factory Method Çözümü (Kalıtım / Inheritance)
* Üst sınıf (`PizzaStore`), sipariş sürecini (`orderPizza`) sabit tutar; fakat pizzayı oluşturan metodu soyut tanımlar: `abstract Pizza createPizza(String item);`.
* Hangi somut pizzanın `new` edileceği kararı, bu metodu ezen alt sınıflara (`NYPizzaStore`, `ChicagoPizzaStore`) devredilir.
* **Dependency Inversion Principle (DIP):** Hem üst seviye dükkan sınıfı hem de alt sınıflar somut implementasyonlara değil, soyut `Pizza` sınıfına bağımlı hale gelir.

#### 🏗️ Sınıf Rolleri
1. **Product (`Pizza`):** Fabrika metodunun ürettiği nesnelerin ortak soyut sınıfı/arayüzü.
2. **Concrete Product (`NYStyleCheesePizza`, `ChicagoStyleCheesePizza`):** Arayüzü uygulayan gerçek nesneler.
3. **Creator (`PizzaStore`):** Sipariş akışını yöneten ve soyut fabrika metodunu (`createPizza`) barındıran temel sınıf.
4. **Concrete Creator (`NYPizzaStore`, `ChicagoPizzaStore`):** Fabrika metodunu ezerek ilgili somut ürünü üreten alt sınıflar.

---

### C. Abstract Factory Pattern (Soyut Fabrika Deseni - GoF)

> **GoF Tanımı:** *"Birbiriyle ilişkili veya bağımlı nesne ailelerini, somut sınıflarını belirtmeden oluşturmak için bir arayüz sağlar."*

#### 📌 Senaryo & Problem
Farklı şubelerin pizzalarında kullandığı malzemelerin (Hamur, Sos, Peynir) standart ve birbiriyle uyumlu olmasının sağlanması. 
* New York: İnce hamur (`ThinCrustDough`), Marinara sos (`MarinaraSauce`), Reggiano peynir (`ReggianoCheese`).
* Malzemelerin tek tek elle seçilmesi kombinasyon hatalarına ve kalite uyumsuzluklarına yol açar.

#### ❌ Kötü Tasarım (Anti-pattern)
Her pizza sınıfının constructor'ına onlarca parametre geçmek veya pizzanın kendi içinde rastgele malzemeleri doğrudan `new` etmesi.

#### ✅ Abstract Factory Çözümü (Bileşim / Composition)
* Birbiriyle uyumlu ürün grubunu tek çatı altında toplayan bir fabrika arayüzü tanımlanır (`PizzaIngredientFactory`). Bu arayüz birden fazla yaratım metodu barındırır: `createDough()`, `createSauce()`, `createCheese()`.
* Bölgesel malzeme aileleri somut fabrikalarda (`NYPizzaIngredientFactory`) paketlenir.
* `CheesePizza` sınıfı artık malzemeyi doğrudan seçmez; içine enjekte edilen malzeme fabrikasına (`PizzaIngredientFactory`) sorarak malzemelerini temin eder (`dough = ingredientFactory.createDough()`).

#### 🏗️ Sınıf Rolleri
1. **Abstract Factory (`PizzaIngredientFactory`):** Birbiriyle ilişkili nesneler ailesini üretmek için tanımlanan fabrika arayüzü.
2. **Concrete Factory (`NYPizzaIngredientFactory`):** Belirli bir bölgeye/temaya ait somut malzeme ailesini üreten fabrika sınıfı.
3. **Abstract Product (`Dough`, `Sauce`, `Cheese`):** Ürün ailesini oluşturan bileşenlerin temel arayüzleri.
4. **Concrete Product (`ThinCrustDough`, `MarinaraSauce`, `ReggianoCheese`):** Belirli aileye ait somut bileşen sınıfları.
5. **Client (`CheesePizza` / `PizzaStore`):** Yalnızca `Abstract Factory` ve `Abstract Product` arayüzlerini kullanarak çalışan tüketici.

---

### ⚖️ Factory Method vs. Abstract Factory Karşılaştırması

| Kriter | Factory Method | Abstract Factory |
| :--- | :--- | :--- |
| **Temel Mekanizma** | **Kalıtım (Inheritance):** Nesne üretim kararı alt sınıfların ezdiği (`override`) metoda bırakılır. | **Bileşim (Composition):** Nesne üretiminden sorumlu fabrika nesnesi başka bir nesneye enjekte edilir. |
| **Üretilen Nesne** | Tek bir ürün (`Pizza`). | Birbiriyle ilişkili **ürün ailesi** (`Dough`, `Sauce`, `Cheese`). |
| **Genişleme Odağı** | Yeni ürün çeşitleri türetmeye odaklanır. | Yeni ürün aileleri (ör. Chicago malzeme paketi) eklemeye odaklanır. |

---

## 5. Singleton Pattern (Tek Nesne Deseni)

> **Kategori:** Yaratımsal (Creational)  
> **Temel Amaç:** Bir sınıfın JVM (Java Virtual Machine) yaşam döngüsü boyunca yalnızca tek bir örneğinin (instance) var olmasını garanti altına almak ve bu nesneye küresel bir erişim noktası sağlamak.

### 📌 Senaryo & Problem
Veritabanı bağlantı havuzları (`DatabaseConnection`), loglama mekanizmaları (`Logger`) veya donanım sürücüleri gibi pahalı kaynakların yönetimi.

#### ❌ Kötü Tasarım (Anti-pattern)
İhtiyaç duyulan her sınıfta `new DatabaseConnection()` çağrısı yapmak:
* Gereksiz bellek kullanımı ve veritabanı soket sınırlarının tükenmesi.
* Paylaşılan kaynaklar üzerinde eşzamanlılık (concurrency) ve durum tutarsızlığı problemleri.

#### ✅ Singleton Çözümü
Constructor `private` yapılarak sınıfın dışarıdan örnekelenmesi engellenir. Sınıf kendi içinde `static` bir referans tutar ve `getInstance()` metodu aracılığıyla herkese bellekteki aynı tek örneği döner.

### 🏗️ Temel Bileşenler
1. **Private Constructor:** `new` anahtar kelimesiyle dışarıdan nesne türetilmesini engeller.
2. **Static Instance Variable:** Üretilen tek nesneyi bellekte (metaspace/static alanda) tutar.
3. **Global Access Method (`getInstance()`):** Nesneye ihtiyaç duyulduğunda onu temin eden veya ilk seferinde üreten statik erişim metodu.

---

## 6. Command Pattern (Komut Deseni)

> **Kategori:** Davranışsal (Behavioral)  
> **Temel Amaç:** Bir isteği bir nesne olarak kapsüllemek (encapsulate); böylece istemcileri farklı isteklerle parametrelendirmek, istekleri sıraya dizmek/kaydetmek ve geri alınabilir (undo) işlemler sunmak.

### 📌 Senaryo & Problem
Farklı cihazları (Işık, Garaj Kapısı, Müzik Sistemi) tek bir merkezden kontrol eden akıllı ev kumandası.

#### ❌ Kötü Tasarım (Anti-pattern)
Kumandanın her cihaz sınıfını (`Light`, `GarageDoor`) doğrudan bilmesi ve buton metotlarının içine donanımlara özel metot çağrılarını gömmesi:
* Kumanda ile cihazlar arasında yüksek bağlılık (Tight Coupling) oluşur.
* Yeni bir cihaz eklendiğinde kumandanın kaynak kodunu değiştirmek gerekir (OCP ihlali).
* İşlemleri geri almak (Undo) veya komut geçmişi tutmak imkansız hale gelir.

#### ✅ Command Çözümü
İş istekleri ortak bir arayüzü (`Command`) uygulayan nesneler haline getirilir. Kumanda (Invoker) yalnızca `execute()` ve `undo()` metotlarını bilir; alıcının (Receiver) kim olduğunu veya işin nasıl yapıldığını bilmez.

### 🏗️ Sınıf Rolleri
1. **Command Interface (`Command`):** Eylemin çalıştırılması (`execute`) ve geri alınması (`undo`) için ortak sözleşme.
2. **Concrete Command (`LightOnCommand`, `GarageDoorOpenCommand`):** Alıcı nesneyi içinde tutan ve komut tetiklendiğinde alıcının ilgili metodunu çağıran sınıf.
3. **Receiver (`Light`, `GarageDoor`):** İsteğin yerine getirilmesi için gerekli asıl iş mantığını barındıran hedef cihaz.
4. **Invoker (`RemoteControl`):** Komut nesnelerini üzerinde barındıran ve kullanıcı etkileşimiyle onları tetikleyen çağırıcı.
5. **Client (`Main`):** Alıcıları ve komutları üretip Invoker üzerine yerleştiren istemci.