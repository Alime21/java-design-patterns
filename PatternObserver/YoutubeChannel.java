import java.util.ArrayList;
import java.util.List;

public class YoutubeChannel implements Subject {
   private String channelName;
   private List<Observer> subscribers = new ArrayList<>();
   private String latestVideoTitle;

    public YoutubeChannel(String channelName) {
        this.channelName = channelName;
    }

    @Override 
    public void registerObserver(Observer observer) {
        subscribers.add(observer);
    }

    @Override
    public void removeObserver(Observer observer) {
        subscribers.remove(observer);
    }

    @Override
    public void notifyObservers() {
        for (Observer subscriber : subscribers) {
            subscriber.update(latestVideoTitle);
        }
    }

    public void uploadVideo(String videoTitle) {
        this.latestVideoTitle = videoTitle;
        System.out.println("\n[" + channelName + "] Yeni video yüklendi: " + videoTitle);
        notifyObservers();
    }
}
