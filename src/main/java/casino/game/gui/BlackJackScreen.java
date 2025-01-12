package casino.game.gui;

import casino.game.model.Position;
import casino.game.model.game.elements.Card;
import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.graphics.TextGraphics;
import com.googlecode.lanterna.input.KeyStroke;
import com.googlecode.lanterna.input.KeyType;
import com.googlecode.lanterna.screen.Screen;
import com.googlecode.lanterna.screen.TerminalScreen;
import com.googlecode.lanterna.terminal.DefaultTerminalFactory;
import com.googlecode.lanterna.terminal.Terminal;
import com.googlecode.lanterna.terminal.swing.AWTTerminalFontConfiguration;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;


public class BlackJackScreen implements GUI {

    private final Screen screen;
    private int width;
    private int height;
    private boolean isFirstTrue = true;

    public BlackJackScreen(Screen screen) {
        this.screen = screen;
    }

    public BlackJackScreen(int width, int height) throws IOException, URISyntaxException, FontFormatException {
        AWTTerminalFontConfiguration fontConfig = loadFont();
        Terminal terminal = createTerminal(width, height, fontConfig);
        this.screen = createScreen(terminal);
        this.width = width;
        this.height = height;
    }

    @Override
    public int getWidth() {
        return width;
    }

    @Override
    public int getHeight() {
        return height;
    }

    private Screen createScreen(Terminal terminal) throws IOException {
        final Screen screen;
        screen = new TerminalScreen(terminal);

        screen.setCursorPosition(null);
        screen.startScreen();
        screen.doResizeIfNecessary();
        return screen;
    }

    private Terminal createTerminal(int width, int height, AWTTerminalFontConfiguration fontConfig) throws IOException {
        TerminalSize terminalSize = new TerminalSize(width, height + 1);
        DefaultTerminalFactory terminalFactory = new DefaultTerminalFactory()
                .setInitialTerminalSize(terminalSize);
        terminalFactory.setForceAWTOverSwing(true);
        terminalFactory.setTerminalEmulatorFontConfiguration(fontConfig);
        return terminalFactory.createTerminal();
    }

    private AWTTerminalFontConfiguration loadFont() throws URISyntaxException, FontFormatException, IOException {
        URL resource = getClass().getClassLoader().getResource("fonts/DejaVuSansMono.ttf");
        File fontFile = null;
        if (resource != null) {
            fontFile = new File(resource.toURI());
        }
        Font font;
        Font loadedFont = null;

        if (fontFile != null) {
            font = Font.createFont(Font.TRUETYPE_FONT, fontFile);
            GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
            ge.registerFont(font);

            loadedFont = font.deriveFont(Font.PLAIN, 25);
        }

        return AWTTerminalFontConfiguration.newInstance(loadedFont);
    }

    @Override
    public ACTION getNextAction() throws IOException {
        if (isFirstTrue) {
            isFirstTrue = false;
            return ACTION.NONE;
        } else {
            while (true) {
                KeyStroke keyStroke = screen.readInput();
                if (keyStroke == null) return ACTION.NONE;

                if (keyStroke.getKeyType() == KeyType.EOF) return ACTION.QUIT;
                if (keyStroke.getKeyType() == KeyType.Character && keyStroke.getCharacter() == 'q') return ACTION.QUIT;

                if (keyStroke.getKeyType() == KeyType.ArrowUp) return ACTION.UP;
                if (keyStroke.getKeyType() == KeyType.ArrowDown) return ACTION.DOWN;
                if (keyStroke.getKeyType() == KeyType.Enter) return ACTION.ENTER;
                if (keyStroke.getKeyType() == KeyType.Character && (keyStroke.getCharacter().equals('h') || keyStroke.getCharacter().equals('H')))
                    return ACTION.HIT;
                if (keyStroke.getKeyType() == KeyType.Character && (keyStroke.getCharacter().equals('s') || keyStroke.getCharacter().equals('S')))
                    return ACTION.STAY;
                if (keyStroke.getKeyType() == KeyType.Character && (keyStroke.getCharacter().equals('q') || keyStroke.getCharacter().equals('Q')))
                    return ACTION.EXIT;
                if (keyStroke.getKeyType() == KeyType.Character && Character.isDigit(keyStroke.getCharacter()))
                    return ACTION.NUMBER;
            }
        }
    }


    @Override
    public void drawCardUp(Position position, Card card) {
        String value = card.getValue();
        String suit = card.getSuit();

        String LINE_COLOR = "black";
        String BACKGROUND_COLOR = "#F5F5DC";

        int x = position.getX();
        int y = position.getY();

        if (value.equals("10")) {
            drawText(new Position(x, y), "┌─────────┐", LINE_COLOR, BACKGROUND_COLOR);
            drawText(new Position(x, y + 1), "│ " + value + "      │", LINE_COLOR, BACKGROUND_COLOR);
            drawText(new Position(x, y + 2), "│         │", LINE_COLOR, BACKGROUND_COLOR);
            drawText(new Position(x, y + 3), "│    " + suit + "    │", LINE_COLOR, BACKGROUND_COLOR);
            drawText(new Position(x, y + 4), "│         │", LINE_COLOR, BACKGROUND_COLOR);
            drawText(new Position(x, y + 5), "│      " + value + " │", LINE_COLOR, BACKGROUND_COLOR);
            drawText(new Position(x, y + 6), "└─────────┘", LINE_COLOR, BACKGROUND_COLOR);
        } else {
            drawText(new Position(x, y), "┌─────────┐", LINE_COLOR, BACKGROUND_COLOR);
            drawText(new Position(x, y + 1), "│ " + value + "       │", LINE_COLOR, BACKGROUND_COLOR);
            drawText(new Position(x, y + 2), "│         │", LINE_COLOR, BACKGROUND_COLOR);
            drawText(new Position(x, y + 3), "│    " + suit + "    │", LINE_COLOR, BACKGROUND_COLOR);
            drawText(new Position(x, y + 4), "│         │", LINE_COLOR, BACKGROUND_COLOR);
            drawText(new Position(x, y + 5), "│       " + value + " │", LINE_COLOR, BACKGROUND_COLOR);
            drawText(new Position(x, y + 6), "└─────────┘", LINE_COLOR, BACKGROUND_COLOR);
        }
    }

    @Override
    public void drawCardDown(Position position) {

        String LINE_COLOR = "black";
        String BACKGROUND_COLOR = "#F5F5DC";

        int x = position.getX();
        int y = position.getY();

        drawText(new Position(x, y), "┌─────────┐", LINE_COLOR, BACKGROUND_COLOR);
        drawText(new Position(x, y + 1), "│         │", LINE_COLOR, BACKGROUND_COLOR);
        drawText(new Position(x, y + 2), "│         │", LINE_COLOR, BACKGROUND_COLOR);
        drawText(new Position(x, y + 3), "│   ???   │", LINE_COLOR, BACKGROUND_COLOR);
        drawText(new Position(x, y + 4), "│         │", LINE_COLOR, BACKGROUND_COLOR);
        drawText(new Position(x, y + 5), "│         │", LINE_COLOR, BACKGROUND_COLOR);
        drawText(new Position(x, y + 6), "└─────────┘", LINE_COLOR, BACKGROUND_COLOR);
    }

    @Override
    public void drawText(Position position, String text, String color, String background) {
        TextGraphics tg = screen.newTextGraphics();
        tg.setForegroundColor(TextColor.Factory.fromString(color));
        tg.setBackgroundColor(TextColor.Factory.fromString(background));
        tg.putString(position.getX(), position.getY(), text);
    }

    @Override
    public void drawRules(Position position) throws IOException {

        String LINE_COLOR = "#FFFFFF";
        String BACKGROUND_COLOR = "#45150d";
        setBackgroundColor(BACKGROUND_COLOR);

        drawText(new Position(5, 2), " ___________________________________________________", LINE_COLOR, BACKGROUND_COLOR);
        drawText(new Position(5, 3), "/ \\                                                  \\", LINE_COLOR, BACKGROUND_COLOR);
        drawText(new Position(5, 4), "|  |                                                 |", LINE_COLOR, BACKGROUND_COLOR);
        drawText(new Position(5, 5), "\\_|                                                 |", LINE_COLOR, BACKGROUND_COLOR);
        drawText(new Position(5, 6), "   | Blackjack Rules:                              |", LINE_COLOR, BACKGROUND_COLOR);
        drawText(new Position(5, 7), "   |                                               |", LINE_COLOR, BACKGROUND_COLOR);
        drawText(new Position(5, 8), "   | - Goal: Get as close to 21 as possible        |", LINE_COLOR, BACKGROUND_COLOR);
        drawText(new Position(5, 9), "   |   without going over.                         |", LINE_COLOR, BACKGROUND_COLOR);
        drawText(new Position(5, 10), "   |                                               |", LINE_COLOR, BACKGROUND_COLOR);
        drawText(new Position(5, 11), "   | - Each card has a value:                      |", LINE_COLOR, BACKGROUND_COLOR);
        drawText(new Position(5, 12), "   |   • Number cards = face value                 |", LINE_COLOR, BACKGROUND_COLOR);
        drawText(new Position(5, 13), "   |   • Face cards = 10                           |", LINE_COLOR, BACKGROUND_COLOR);
        drawText(new Position(5, 14), "   |   • Ace = 1 or 11                             |", LINE_COLOR, BACKGROUND_COLOR);
        drawText(new Position(5, 15), "   |                                               |", LINE_COLOR, BACKGROUND_COLOR);
        drawText(new Position(5, 16), "   | - Beat the dealer by having a higher total    |", LINE_COLOR, BACKGROUND_COLOR);
        drawText(new Position(5, 17), "   |   value under 21.                             |", LINE_COLOR, BACKGROUND_COLOR);
        drawText(new Position(5, 18), "   |                                               |", LINE_COLOR, BACKGROUND_COLOR);
        drawText(new Position(5, 19), "   |                                               |", LINE_COLOR, BACKGROUND_COLOR);
        drawText(new Position(5, 20), "   |  Good Luck :)                                 |", LINE_COLOR, BACKGROUND_COLOR);
        drawText(new Position(5, 21), "   |                                               |", LINE_COLOR, BACKGROUND_COLOR);
        drawText(new Position(5, 22), "   |   ____________________________________________|_____", LINE_COLOR, BACKGROUND_COLOR);
        drawText(new Position(5, 23), "   |  /                                                 /", LINE_COLOR, BACKGROUND_COLOR);
        drawText(new Position(5, 24), "   \\_/_________________________________________________/", LINE_COLOR, BACKGROUND_COLOR);

        drawText(new Position(20, 27), "Press ENTER to place a bet.", LINE_COLOR, BACKGROUND_COLOR);
    }

    @Override
    public void clear() {
        screen.clear();
    }

    @Override
    public void refresh() throws IOException {
        screen.refresh();
    }

    @Override
    public void close() throws IOException {
        screen.close();
    }

    @Override
    public void setBackgroundColor(String index) throws IOException {
        TextColor color = TextColor.Factory.fromString(index);
        TextGraphics tg = screen.newTextGraphics();
        clear();

        for (int y = 0; y < getHeight(); y++) {
            for (int x = 0; x < getWidth(); x++) {
                tg.setBackgroundColor(color); // Set background color for each cell
                tg.putString(x, y, " "); // Fill the cell with a space character
            }
        }
        refresh();
    }

    @Override
    public void setBackgroundPNG(String path) throws IOException {
        URL resource = getClass().getClassLoader().getResource(path);
        if(resource != null) {
            BufferedImage image = ImageIO.read(resource);
            clear();
            for (int x = 0; x < image.getWidth(); x++) {
                for (int y = 0; y < image.getHeight(); y++) {
                    int value = image.getRGB(x, y);
                    if ((value >> 24) == 0)   // transparent pixel
                        continue;
                    TextGraphics tg = screen.newTextGraphics();
                    tg.setBackgroundColor(getRGB(value));
                    tg.setCharacter(x , y, ' ');
                }
            }
            refresh();
        }
    }

    private TextColor getRGB(int value) {
        int red = value >> 16 & 0xFF;
        int green = value >> 8 & 0xFF;
        int blue = value & 0xFF;
        return new TextColor.RGB(red, green, blue);
    }


        @Override
    public void invalidAmount() throws IOException {
        String PRINCIPAL_LINE_COLOR = "#FFFF00";
        String SECOND_LINE_COLOR = "#FFFFFF";
        String BACKGROUND_COLOR = "#45150d";

        clear();
        setBackgroundColor(BACKGROUND_COLOR);
        String prompt = "Please enter a valid amount";
        int textLength = prompt.length();
        int positionX = (getWidth() - textLength) / 2;
        int positionY = 15;

        drawText(new Position(positionX - 5, positionY - 1), "-------------------------------------", SECOND_LINE_COLOR, BACKGROUND_COLOR);
        drawText(new Position(positionX, positionY), prompt, PRINCIPAL_LINE_COLOR, BACKGROUND_COLOR);
        drawText(new Position(positionX - 5, positionY + 1), "-------------------------------------", SECOND_LINE_COLOR, BACKGROUND_COLOR);
        refresh();

    }

    @Override
    public void drawSelected(Position position, int width, int height, String LINE_COLOR, String BACKGROUND_COLOR) {

        //Draw the top and bottom
        for (int x = position.getX() - 1; x < position.getX() + width + 1; x++) {
            drawText(new Position(x, position.getY()), "-", LINE_COLOR, BACKGROUND_COLOR);
            drawText(new Position(x, position.getY() + height + 1), "-", LINE_COLOR, BACKGROUND_COLOR);
        }

        // Draw the side
        for (int y = position.getY(); y < position.getY() + height; y++) {
            drawText(new Position(position.getX() - 1, y + 1), "|", LINE_COLOR, BACKGROUND_COLOR);
            drawText(new Position(position.getX() + width, y + 1), "|", LINE_COLOR, BACKGROUND_COLOR);
        }
    }


    @Override
    public int betInput(int balance) throws IOException {
        String LINE_COLOR = "#FFFFFF";
        String BACKGROUND_COLOR = "#45150d";

        clear();
        setBackgroundColor(BACKGROUND_COLOR);
        String prompt1 = "Your current Balance is: " + balance;
        String prompt2 = "Enter the amount you want to bet:";

        int textLength1 = prompt1.length();
        int textLength2 = prompt2.length();
        int positionX1 = (getWidth() - textLength1) / 2;
        int positionX2 = (getWidth() - textLength2) / 2;
        int positionY1 = 10;
        int positionY2 = 13;

        drawText(new Position(positionX1, positionY1), prompt1, LINE_COLOR, BACKGROUND_COLOR);
        drawText(new Position(positionX2, positionY2), prompt2, LINE_COLOR, BACKGROUND_COLOR);
        refresh();

        StringBuilder inputBuffer = new StringBuilder();
        String input = "0";

        while (true) {
            KeyStroke keyStroke = screen.readInput();

            if (keyStroke.getKeyType() == KeyType.Enter) {

                if (!inputBuffer.isEmpty()) {
                    input = inputBuffer.toString();
                }
                break;
            } else if (keyStroke.getKeyType() == KeyType.Backspace) {
                if (!inputBuffer.isEmpty()) {
                    inputBuffer.deleteCharAt(inputBuffer.length() - 1);
                }
            } else if (keyStroke.getKeyType() == KeyType.Character && Character.isDigit(keyStroke.getCharacter())) {
                inputBuffer.append(keyStroke.getCharacter());
            }

            clear();
            setBackgroundColor(BACKGROUND_COLOR);
            drawText(new Position(positionX1, positionY1), prompt1, LINE_COLOR, BACKGROUND_COLOR);
            drawText(new Position(positionX2, positionY2), prompt2, LINE_COLOR, BACKGROUND_COLOR);
            drawText(new Position(33, 15), inputBuffer.toString(), LINE_COLOR, BACKGROUND_COLOR);
            refresh();
        }

        return Integer.parseInt(input);

    }

}
