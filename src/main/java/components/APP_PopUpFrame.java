package main.java.components;

public abstract class APP_PopUpFrame<T> extends APP_Frame {
    
    /**The parent frame of this pop up frame */
    private T parent;

    public APP_PopUpFrame(T parent) {
        super();
        this.parent = parent;
    }

    public APP_PopUpFrame(T parent, String title) {
        super(title);
        this.parent = parent;
    }

    public abstract void prepare();

    public abstract void prepareComponents();

    public abstract void addComponents();

    public abstract void finalizePrepare();

    public T getParentFrame() {
        return parent;
    }

}
