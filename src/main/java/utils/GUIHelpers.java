package main.java.utils;

import javax.swing.JButton;
import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

public class GUIHelpers {

    /**
     * Checks if all JTextField in the given array are not empty.
     * Returns {@code}true{@code} when all JTextFields are not empty,
     * otherwise returns {@code}false{@code}.
     * @param tfs the array of JTextFields to check
     * @return {@code}true{@code} if all JTextFields are not empty,
     * {@code}false{@code} if any JTextField is empty
     */
    public static void setButtonTriggerOnAllFields(JButton button, JTextField[] tfs) {
        for (JTextField tf : tfs) {
            tf.getDocument().addDocumentListener(new DocumentListener() {
                public void changedUpdate(DocumentEvent event) {
                    changed();
                }

                public void removeUpdate(DocumentEvent event) {
                    changed();
                }

                public void insertUpdate(DocumentEvent event) {
                    changed();
                }

                public void changed() {
                    if (checkEmptyTextFields(tfs)) {
                        button.setEnabled(false);
                    } else {
                        button.setEnabled(true);
                    }
                }
            });
        }
    }

    /**
     * Checks if any JTextField in the given array is empty.
     * Returns {@code}true{@code} when one or more JTextField is
     * empty, otherwise returns {@code}false{@code}.
     * @param tfs the array of JTextFields to check
     * @return {@code}true{@code} if one or more JTextField is
     * empty, {@code}false{@code} if all JTextFields are not
     * empty
     */
    public static boolean checkEmptyTextFields(JTextField[] tfs) {
        for (JTextField textField : tfs) {
            if (textField.getText().equals("")) {
                return true;
            }
        }
        return false;
    }
}
