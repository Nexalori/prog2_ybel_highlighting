package highlighting;

import java.awt.*;
import java.util.List;
import java.util.regex.Pattern;

import static java.awt.Color.*;
import static java.awt.Color.green;
import static java.util.regex.Pattern.DOTALL;

/** Configure the lexer and start the demo. */
public class Main {
    /**
     * Launch the demo.
     *
     * @param args arguments for the program (not used)
     */
    public static void main(String... args) {
        String defaultText =
                """
package controller;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

/* ApplicationListener that delegates to the MainGameController. Just some setup. */
public class LibgdxSetup extends Game {
    private final MainController mc;

    /**
     * The batch is necessary to draw ALL the stuff. Every object that uses draw need to know the
     * batch.
     */
    private SpriteBatch batch;
    // This batch is used to //draw the HUD /*elements*/ on it.\040
    private SpriteBatch hudBatch;

    /**
     * "ApplicationListener" that delegates to the "MainGameController". Just some setup.
     */
    public LibgdxSetup(MainController mc) {
        this.mc = mc;
    }

    @Over-ride 'someText'
    public void create() {
        // new ...
        char ch = new Character('a');
        return null;
    }
}
""";

        LexerUI.show(defaultText, Lexer.of(setupTokens()));
    }

    static Pattern p1 = Pattern.compile("\"(.*?)\"");
    static Pattern p2 = Pattern.compile("\'(.)\'");
    static Pattern p3 = Pattern.compile("\\b(package|import|class|public|private|final|return|null|new)\\b", 0x02);
    static Pattern p4 = Pattern.compile("@[\\w-]*");
    static Pattern p5 = Pattern.compile("//.*");
    static Pattern p6 = Pattern.compile("/\\* .*? \\*/", DOTALL);
    static Pattern p7 = Pattern.compile("/\\*\\*.*?\\*/", DOTALL);

    /**
     * TODO: Homework! Define the patterns for the individual tokens here (see comments).
     *
     * @return list of tokens for the syntax parts to be highlighted
     */
    private static List<Token> setupTokens() {
        return List.of(
            // Strings
            // Zeichenketten, die in '"' eingeschlossen sind
            Token.of(p1, 1, new Color(0,150, 0, 255)),

            // Einzelne Zeichen
            // Zeichen, die in "'" eingeschlossen sind
            Token.of(p2, 1, new Color(120, 166, 120, 255)),

            // KeyWords: package, import, class, public, private, final, return, null, new
            Token.of(p3, 1, orange),

            // Annotation
            // Fangen mit "@" an, beispielsweise "@Override"
            Token.of(p4, 0, yellow),

            // Einzeiliger Kommentar
            // Fängt mit "//" an und geht bis zum Ende der Zeile
            Token.of(p5, 0, red),

            // Mehrzeiliger Kommentar
            // Fängt mit "/*" and und bis zum nächsten "*/", kann potenziell mehrere Zeilen
            // umfassen
            Token.of(p6, 0, green),

            // Java-Doc-Kommentar
            // Wie ein mehrzeiliger Kommentar, beginnt aber mit "/**"
            Token.of(p7, 0,  new Color(90, 88, 186, 255))
        );
    }
}
