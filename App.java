import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class App {

    // заголовки окна:
    private final static String PROGRAM_TITLE = "Classic App Snake";
    private final static String PROGRAM_OVER_TITLE = "GAME OVER!";

    // параметры поля:
    final static int AREA_WIDTH = 30;
    final static int AREA_HEIGHT = 20;
    private final static int AREA_DX = 6;
    private final static int AREA_DY = 28;

    // задержка отображения:
    private final static int SHOW_DELAY = 150;

    // параметры змеи:
    private final static int START_LOCATION = 200;
    private final static int START_SNAKE_LENGTH = 6;
    private final static int START_SNAKE_X = 10;
    private final static int START_SNAKE_Y = 10;
    private final static int START_DIRECTION = Point.RIGHT;

    // служебные переменные:
    private Garden garden;
    private Snake snake;
    private final ThreadLocal<JFrame> frame = new ThreadLocal<>();
    private Canvas canvasPanel;
    private boolean start = true;

    public static void main( String[] args ) {
        new App().init();
    }

    private void init() {

        // создаём новое окно:
        frame.set(new JFrame(PROGRAM_TITLE + " : " + START_SNAKE_LENGTH));
        // устанавливаем закрытие окна при нажатии на крестик окна:
        frame.get().setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
        // устанавливаем размеры окна:
        frame.get().setSize(
                AREA_WIDTH * Point.RADIUS + AREA_DX,
                AREA_HEIGHT * Point.RADIUS + AREA_DY
        );
        // стартовое положение змеи:
        frame.get().setLocation( START_LOCATION, START_LOCATION );
        // блокируем изменения размеров окна:
        frame.get().setResizable( false );

        canvasPanel = new Canvas();
        canvasPanel.setBackground( Color.white );

        // BorderLayout - менеджер размещений
        // добавляем нашу канву на панель:
        frame.get().getContentPane().add( BorderLayout.CENTER, canvasPanel );
        frame.get().addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed( KeyEvent e ) {
                super.keyPressed( e );
                snake.setDirection( e.getKeyCode() );
                if ( e.getKeyCode() == 10 ) start = true;
            }
        });
        // устанавливаем видимость экрана:
        frame.get().setVisible( true );
        do {
            if( start ) game();
            delay();
        } while( true );
    }

    private void game() {
        // игра начинается:
        start = false;
        boolean gameOver = false;
        frame.get().setTitle( PROGRAM_TITLE + " : " + START_SNAKE_LENGTH );
        // создаём змейку:
        snake = new Snake( START_SNAKE_X, START_SNAKE_Y, START_SNAKE_LENGTH, START_DIRECTION );
        // создаём сад:
        garden = new Garden( snake );
        // запускаем змейку:
        while( !gameOver ) {
            canvasPanel.repaint();
            delay();
            garden.grow();
            snake.move();
            gameOver = !snake.live( garden, frame.get());
        }
        frame.get().setTitle( PROGRAM_OVER_TITLE );
    }

    private void delay() {
        try {
            Thread.sleep( SHOW_DELAY );
        } catch ( InterruptedException e ) {
            e.printStackTrace();
        }
    }

    private class Canvas extends JPanel {
        @Override
        public void paint( Graphics g ) {
            super.paint(g);
            garden.paint(g);
        }
    }

}