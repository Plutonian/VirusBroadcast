import java.awt._

import javax.swing._

/**
 * @ClassName: MyPanel
 * @Description: TODO
 * @author: Bruce Young
 * @date: 2020年02月02日 17:03
 */
object MyPanel {
  var worldTime = 0
}

class MyPanel() extends JPanel with Runnable {
  private var pIndex = 0

  this.setBackground(new Color(0x444444))

  override def paint(g: Graphics): Unit = {
    super.paint(g)
    //draw border
    g.setColor(new Color(0x00ff00))
    g.drawRect(Hospital.x, Hospital.y, Hospital.width, Hospital.height)
    val people = PersonPool.personList
    if (people == null) return

    people(pIndex).update()

    //    import scala.collection.JavaConversions._
    for (person <- people) {
      person.state match {
        case Person.State.NORMAL =>
          g.setColor(new Color(0xdddddd))
        case Person.State.SHADOW =>
          g.setColor(new Color(0xffee00))
        case Person.State.CONFIRMED =>
        case Person.State.FREEZE =>
          g.setColor(new Color(0xff0000))
      }
      person.update()
      g.fillOval(person.x, person.y, 3, 3)
    }
    pIndex += 1
    if (pIndex >= people.size) pIndex = 0
  }

  override def run() =
    while ( {
      true
    }) {
      SwingUtilities.invokeLater { () =>
        this.repaint()
      }

      try {
        Thread.sleep(100)
        MyPanel.worldTime += 1
      } catch {
        case e: InterruptedException =>
          e.printStackTrace()
      }
    }
}