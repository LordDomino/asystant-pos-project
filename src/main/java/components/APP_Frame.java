package main.java.components;
import java.awt.Color;

import javax.swing.JFrame;

import main.java.configs.ColorConfig;

public abstract class APP_Frame extends JFrame {

    /**
     * The background color used by this JFrame.
     */
    public Color bg = ColorConfig.BG;

    public APP_Frame() {
        super();
    }

    public APP_Frame(String title) {
        super(title);
    }

    /**
     * Convenience method to modify this component and its properties.
     */
    public abstract void prepare();

    /**
     * Convenience method to modify this component's children components
     * and their properties.
     */
    public abstract void prepareComponents();

    /**
     * Convenience method to add this component's children components to
     * this one.
     */
    public abstract void addComponents();

    /**
     * Convenience method to apply other modifications to this component
     * whenever some methods or modifications require the children
     * components to be added first (e.g. when packing the
     * component using {@code pack()}).
     */
    public abstract void finalizePrepare();

    /**
     * Executes the four convenience methods in sequential order:
     * {@code prepare()}, {@code prepareComponents()}, 
     * {@code addComponents()}, then {@code finalizePrepare()}.
     */
    public void compile() {
        prepare();
        prepareComponents();
        addComponents();
        finalizePrepare();
    }
}