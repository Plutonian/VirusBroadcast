import javax.swing.*;
import java.awt.*;
import java.util.List;

/**
 * @ClassName: MyPanel
 * @Description: TODO
 * @author: Bruce Young
 * @date: 2020年02月02日 17:03
 */
public class MyPanel extends JPanel implements Runnable {


    public static int worldTime = 0;
    private int pIndex = 0;

    public MyPanel() {
        this.setBackground(new Color(0x444444));
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        //draw border
        g.setColor(new Color(0x00ff00));
        g.drawRect(Hospital.getInstance().getX(), Hospital.getInstance().getY(),
                Hospital.getInstance().getWidth(), Hospital.getInstance().getHeight());


        List<Person> people = PersonPool.getInstance().getPersonList();
        if (people == null) {
            return;
        }
        people.get(pIndex).update();
        for (Person person : people) {

            switch (person.getState()) {
                case Person.State.NORMAL: {
                    g.setColor(new Color(0xdddddd));
                    break;
                }
                case Person.State.SHADOW: {
                    g.setColor(new Color(0xffee00));
                    break;
                }
                case Person.State.CONFIRMED:
                case Person.State.FREEZE: {
                    g.setColor(new Color(0xff0000));
                    break;
                }
            }
            person.update();
            g.fillOval(person.getX(), person.getY(), 3, 3);

        }
        pIndex++;
        if (pIndex >= people.size()) {
            pIndex = 0;
        }

    }

    @Override
    public void run() {
        while (true) {

            this.repaint();

            try {
                Thread.sleep(100);
                worldTime++;
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }


}
