package ua.nure.kn.muzyka.usermanagement.util;
import java.util.ResourceBundle;

public class Messages {
    private static final String BUNDLE_NAME = "messages";
    private  static final ResourceBundle resourceBundle = ResourceBundle.getBundle(BUNDLE_NAME);

    private Messages() {}

    public static String getString(String element) {
        return resourceBundle.getString(element);
    }
}