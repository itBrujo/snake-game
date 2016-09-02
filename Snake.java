import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

class Snake {
    private ArrayList<Point> snake = new ArrayList<>();
    private int direction;
    private boolean live = true;

    // конструктор змеи:
    Snake( int x, int y, int length, int direction ) {
        for( int i = 0; i < length; i++ ) {
            Point point = new Point( x - i, y );
            snake.add( point );
        }
        this.direction = direction;
    }

    // меняем направление движения змеи:
    void setDirection( int dir ) {
        if( dir < GameSnake.LEFT || dir > GameSnake.UP ) return;
        if( dir == GameSnake.LEFT && direction == GameSnake.RIGHT ) return;
        if( dir == GameSnake.RIGHT && direction == GameSnake.LEFT ) return;
        if( dir == GameSnake.UP && direction == GameSnake.DOWN ) return;
        if( dir == GameSnake.DOWN && direction == GameSnake.UP ) return;
        direction = dir;
    }

    // метод рисования объекта:
    void paint(Graphics g) {
        // перебираем элементы объекта в цикле:
        for( Point point: snake ) {
            point.paint( g );
        }
    }

    // решаем вопросы питания, жизни и смерти :)
    boolean live( Garden garden, JFrame frame ) {
        if( !live ) { snake.remove(0); return live; }
        if( garden.feed() ) {
            frame.setTitle( GameSnake.PROGRAM_TITLE + " : " + snake.size() );
        } else {
            snake.remove( snake.size() - 1 );
        }
        return live;
    }

    // змейка ползёт :)
    void move() {
        // добавляем новую точку со смещением обязательно в нулевую позицию коллекции:
        snake.add( 0, new Point( snake.get(0), direction, this ) );
    }

    // получаем голову змейки
    Point head() {
        return snake.get(0);
    }

    // проверяем - является ли точка телом змейки:
    boolean isBody( Point check ) {
        for( Point point: snake )
            if ( point.isOne( check ) ) return true;
        return false;
    }

    // останавливаем змейку :(
    void stop() { live = false; }

}