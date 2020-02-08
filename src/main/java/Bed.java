/**
 * @ClassName: Bed
 * @Description: TODO
 * @author: Bruce Young
 * @date: 2020年02月02日 21:00
 */
public class Bed extends Point {
    private boolean isEmpty = true;

    public Bed(int x, int y) {
        super(x, y);
    }

    public boolean isEmpty() {
        return isEmpty;
    }

    public void setEmpty(boolean empty) {
        isEmpty = empty;
    }
}
