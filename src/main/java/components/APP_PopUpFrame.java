package main.java.components;

public class APP_PopUpFrame<T> extends APP_Frame {
    
    /**The parent frame of this pop up frame */
    private T parent;

    public APP_PopUpFrame(T parent) {
        super();
        this.parent = parent;
        compile();
    }

    public APP_PopUpFrame(T parent, String title) {
        super(title);
        this.parent = parent;
        compile();
    }

    public void prepare() {}

    public void prepareComponents() {}

    public void addComponents() {}

    public void finalizePrepare() {}

    public T getParentFrame() {
        return parent;
    }

}
