public class LightOffCommand {
    private Light light;

    public LightOffCommand(Ligth light) {
        this.light = light;
    }

    @Override 
    public void execute() {
        light.off();
    } 

    @Override
    public void undo() {
        light.on();
    }    
}
