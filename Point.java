import java.awt.*;
import java.util.Random;

class Point {

    // коды клавиш:
    final static int LEFT = 37;
    final static int DOWN = 38;
    final static int RIGHT = 39;
    final static int UP = 40;

    // настройки цикличности:
    private final static boolean CYCLE_AREA = true;

    // размеры точки:
    final static int RADIUS = 20;

    // цветовые параметры:
    private final static Color COLOR_BODY = Color.black;
    private final static Color COLOR_FOOD = Color.green;
    private final static Color COLOR_POISON = Color.red;

    // параметры точки:
    private int x, y;
    private Color color = COLOR_BODY;

    // конструктор для растущей змейки:
    Point( Snake snake ) {
        int direction = snake.getDirection();
        int x = snake.head().getX();
        int y = snake.head().getY();
        if( direction == LEFT ) x--;
        if( direction == RIGHT ) x++;
        if( direction == DOWN ) y--;
        if( direction == UP ) y++;
        if( x > App.AREA_WIDTH - 1 ) {
            if( !CYCLE_AREA ) snake.stop();
            x = 0;
        }
        if( x < 0 ) {
            if( !CYCLE_AREA ) snake.stop();
            x = App.AREA_WIDTH - 1;
        }
        if( y > App.AREA_HEIGHT - 1 ) {
            if( !CYCLE_AREA ) snake.stop();
            y = 0;
        }
        if( y < 0 ) {
            if( !CYCLE_AREA ) snake.stop();
            y = App.AREA_HEIGHT - 1;
        }
        this.set( x, y );
        this.check( snake );
    }

    // конструктор для сада:
    Point( boolean isFood ) {
        grow();
        if( isFood ) {
            color = COLOR_FOOD;
        } else {
            color = COLOR_POISON;
        }
    }

    // координатный конструктор:
    Point( int x, int y ) {
        this.set( x, y );
    }

    // рисуем точку:
    void paint( Graphics g ) {
        // устанавливаем цвет точки:
        g.setColor( color );
        // рисуем точку в виде закрашенного круга:
        g.fillOval( x * RADIUS, y * RADIUS, RADIUS, RADIUS );
    }

    // устанавливаем координаты точки:
    private void set( int x, int y ) {
        this.x = x;
        this.y = y;
    }

    // проверяем, не наехала ли змейка на саму себя:
    private void check( Snake snake ) {
        if( snake.isBody( this ) ) snake.stop();
    }

    // перемещение точки в случайное место экрана:
    private void grow() {
        Random random = new Random();
        int x = random.nextInt( App.AREA_WIDTH - 1 );
        int y = random.nextInt( App.AREA_HEIGHT - 1 );
        set( x, y );
    }

    // проверяем совпадение точек:
    boolean isOne( Point point ) {
        return ( ( this.x == point.getX() ) && ( this.y == point.getY() ) );
    }

    private int getX() { return x; }

    private int getY() { return y; }

}