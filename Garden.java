import java.awt.*;
import java.util.ArrayList;

class Garden {

    // конструктор сада:
    Garden( Snake snake ) {
        this.snake = snake;
        this.food = get( true );
        cycle = 0;
    }

    private final static int CYCLE_LENGTH = 3;

    private Snake snake;
    private int cycle;
    private Point food = new Point( true );
    private ArrayList<Point> danger = new ArrayList<>();

    // смотрим, куда попала змейка:
    boolean feed() {
        if( food.isOne( snake.head() ) ) {
            eat();
            return true;
        }
        if( !free() ) snake.stop();
        return false;
    }

    // рисуем сад и всё что в нём:
    void paint( Graphics g ) {
        food.paint( g );
        snake.paint( g );
        for( Point point : danger) point.paint( g );
    }

    // сад растёт и защищается :)
    void grow() {
        if( cycle < CYCLE_LENGTH ) return;
        cycle = 0;
        danger.add( get( false ) );
    }

    // появляется новая еда:
    private void eat() {
        cycle++;
        food = get( true );
    }

    // получаем новую точку для сада:
    private Point get( boolean isFood ) {
        Point point;
        do {
            point = new Point( isFood );
        } while( !free( point ) );
        return point;
    }

    // проверяем, попала ли голова змеи на опасность:
    private boolean free() {
        for( Point check : danger) {
            if( check.isOne( snake.head() ) ) return false;
        }
        return true;
    }

    // проверяем, свободна ли данная точка:
    private boolean free( Point point ) {
        if( food.isOne( point ) ) return false;
        if( snake.isBody( point ) ) return false;
        for( Point check : danger) {
            if( check.isOne( point ) ) return false;
        }
        return true;
    }

}