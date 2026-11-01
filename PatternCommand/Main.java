public class Main {
    public static void main(String[] args) {
        RemoteControl remote = new RemoteControl();

        // 1. Cihazları (Receivers) oluştur
        Light livingRoomLight = new Light("Oturma Odası");
        GarageDoor garageDoor = new GarageDoor();

        // 2. Komutları (Commands) oluştur
        LightOnCommand livingRoomLightOn = new LightOnCommand(livingRoomLight);
        LightOffCommand livingRoomLightOff = new LightOffCommand(livingRoomLight);
        GarageDoorOpenCommand garageDoorOpen = new GarageDoorOpenCommand(garageDoor);

        // 3. Kumandanın yuvalarına komutları ata
        remote.setCommand(0, livingRoomLightOn, livingRoomLightOn);
        remote.setCommand(1, garageOpen, new NoCommand());

        // 4. Kumanda butonlarını test et
        System.out.println("--- Salon Işığı Açma & Kapama ---");
        remote.onButtonWasPushed(0);
        remote.offButtonWasPushed(0);

        System.out.println("\n--- Geri Al (Undo) Testi ---");
        remote.undoButtonWasPushed(); // En son kapatılmıştı, tekrar açılmalı!

        System.out.println("\n--- Garaj Kapısı Testi ---");
        remote.onButtonWasPushed(1);
        remote.undoButtonWasPushed();
    }
}
