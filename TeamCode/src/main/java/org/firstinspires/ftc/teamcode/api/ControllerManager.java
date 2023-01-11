
public abstract class ControllerManager {

    private DriveManager dm;
    public ControllerManager (DriveManager dm) {
        this.dm = dm;
    }

    private ControllerManager () {}

    public void manage() {
        //TODO FIXME
    }

    abstract void xButton();
    abstract void yButton();
    abstract void aButton();
    abstract void bButton();
    abstract void leftTrigger();
    abstract void rightTrigger();
    abstract void leftStick();
    abstract void rightStick();
    abstract void leftStickIn();
    abstract void rightStickIn();
    abstract void dpadUp();
    abstract void dpadDown();
    abstract void dpadLeft();
    abstract void dpadRight();
    abstract void leftBumper();
    abstract void rightBumper();
}
