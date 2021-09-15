package pl.magdalena.brejna.colourtheworldapp.utils;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import pl.magdalena.brejna.colourtheworldapp.App;

import java.util.Locale;
import static javax.swing.JComponent.getDefaultLocale;

public final class ResourceBundleFactory {

    private static final ObjectProperty<Locale> locale;
    static {
        locale = new SimpleObjectProperty<>(getDefaultLocale());
        locale.addListener((observable, oldValue, newValue) -> Locale.setDefault(newValue));
    }

    public static Locale getLocale() {
        return locale.get();
    }

    public static void setLocale(Locale locale) {
        localeProperty().set(locale);
        Locale.setDefault(locale);
        reloadLayout();
    }
    public static ObjectProperty<Locale> localeProperty() {
        return locale;
    }

    private static void reloadLayout(){
        App.refresh();
    }
}
