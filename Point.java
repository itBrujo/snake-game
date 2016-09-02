import java.awt.*;
import java.util.Random;

class Point {

    // цветовые параметры:
    private final static Color COLOR_BODY = Color.black;
    private final static Color COLOR_FOOD = Color.green;
    private final static Color COLOR_POISON = Color.red;
    // писать ли лог:
    private final static boolean WRITE_LOG = false;

    private int x, y;
    private Color color = COLOR_BODY;

    // конструктор:
    Point( Point point, int direction, Snake snake ) {
        int x = point.getX();
        int y = point.getY();
        this.log( "BEFORE: X: " + x + " Y: " + y );
        if( direction == GameSnake.LEFT ) { this.log( "LEFT!" ); x--; }
        if( direction == GameSnake.RIGHT ) { this.log( "RIGHT!" ); x++; }
        if( direction == GameSnake.DOWN ) { this.log( "DOWN!" ); y--; }
        if( direction == GameSnake.UP ) { this.log( "UP!" ); y++; }
        if( x > GameSnake.AREA_WIDTH - 1 ) {
            if( !GameSnake.CYCLE_AREA ) snake.stop();
            x = 0;
        }
        if( x < 0 ) {
            if( !GameSnake.CYCLE_AREA ) snake.stop();
            x = GameSnake.AREA_WIDTH - 1;
        }
        if( y > GameSnake.AREA_HEIGHT - 1 ) {
            if( !GameSnake.CYCLE_AREA ) snake.stop();
            y = 0;
        }
        if( y < 0 ) {
            if( !GameSnake.CYCLE_AREA ) snake.stop();
            y = GameSnake.AREA_HEIGHT - 1;
        }
        this.set( x, y );
        this.check( snake );
        this.log( "X: " + x + " Y: " + y);
    }

    Point( boolean isFood ) {
        grow();
        if( isFood ) {
            color = COLOR_FOOD;
        } else {
            color = COLOR_POISON;
        }
    }

    // конструктор:
    Point( int x, int y ) {
        this.set( x, y );
    }

    void paint( Graphics g ) {
        // устанавливаем цвет точки:
        g.setColor( color );
        // рисуем точку в виде закрашенного круга:
        g.fillOval(
            x * GameSnake.POINT_RADIUS,
            y * GameSnake.POINT_RADIUS,
            GameSnake.POINT_RADIUS,
            GameSnake.POINT_RADIUS
        );
    }

    private void set( int x, int y ) {
        this.x = x;
        this.y = y;
    }

    private void check( Snake snake ) {
        if( snake.isBody( this ) ) snake.stop();
    }

    private void grow() {
        Random random = new Random();
        int x = random.nextInt( GameSnake.AREA_WIDTH - 1 );
        int y = random.nextInt( GameSnake.AREA_HEIGHT - 1 );
        set( x, y );
    }

    boolean isOne( Point point ) {
        return ( ( this.x == point.getX() ) && ( this.y == point.getY() ) );
    }

    private int getX() { return x; }

    private int getY() { return y; }

    private void log( String mess ) {
        if(WRITE_LOG)
            System.out.print( mess + "\n" );
    }

}