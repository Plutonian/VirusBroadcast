import java.util.Random

import javax.swing._

object Main {
  def main(args: Array[String]) = {
    val p = new MyPanel
    val frame = new JFrame
    frame.add(p)
    frame.setSize(1000, 800)
    frame.setLocationRelativeTo(null)
    frame.setVisible(true)
    frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE)


    val panelThread = new Thread(p)
    panelThread.start()
    val people = PersonPool.personList
    var i = 0
    while ( {
      i < Constants.ORIGINAL_COUNT
    }) {
      var index = new Random().nextInt(people.size - 1)
      var person = people(index)
      while ( {
        person.isInfected
      }) {
        index = new Random().nextInt(people.size - 1)
        person = people(index)
      }
      person.beInfected()
      i += 1
    }
  }
}