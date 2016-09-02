import java.awt.*;
import java.util.ArrayList;

class Snake {
    private ArrayList<Point> snake = new ArrayList<>();
    private int direction;
    private boolean live = true;

    // конструктор змеи:
    Snake( int x, int y, int length, int direction ) {
        // начальное направлени движения:
        this.direction = direction;
        // голова змейки:
        snake.add( 0, new Point( x, y ) );
        // змейка выползает из норы :)
        for( int i = 0; i < length; i++ ) move();
    }

    // меняем направление движения змеи:
    void setDirection( int dir ) {
        if( dir < Point.LEFT || dir > Point.UP ) return;
        if( dir == Point.LEFT && direction == Point.RIGHT ) return;
        if( dir == Point.RIGHT && direction == Point.LEFT ) return;
        if( dir == Point.UP && direction == Point.DOWN ) return;
        if( dir == Point.DOWN && direction == Point.UP ) return;
        direction = dir;
    }

    // метод рисования объекта:
    void paint( Graphics g ) {
        // перебираем элементы объекта в цикле:
        for( Point point: snake ) {
            point.paint( g );
        }
    }

    // решаем вопросы питания, жизни и смерти :)
    boolean live( Garden garden ) {
        if( !live ) { snake.remove(0); return live; }
        if( !garden.feed() )
            snake.remove( snake.size() - 1 );
        return live;
    }

    // змейка ползёт :)
    void move() {
        // добавляем новую голову:
        snake.add( 0, new Point( this ) );
    }

    // получаем голову змейки:
    Point head() { return snake.get(0); }

    // получаем длину змейки:
    int length() { return snake.size(); }

    // получаем направление движения змейки:
    int getDirection() { return direction; }

    // проверяем - является ли точка телом змейки:
    boolean isBody( Point check ) {
        for( Point point: snake )
            if ( point.isOne( check ) ) return true;
        return false;
    }

    // останавливаем змейку :(
    void stop() { live = false; }

}