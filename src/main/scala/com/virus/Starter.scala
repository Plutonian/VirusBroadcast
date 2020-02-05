package com.virus

import java.util.Random
import java.util.concurrent.{Executors, TimeUnit}

import javax.swing._
import model.{AllPerson, World}

object Starter {
  def main(args: Array[String]) = {
    val p = new MainView
    val frame = new JFrame
    frame.add(p)
    frame.setSize(1000, 800)
    frame.setLocationRelativeTo(null)
    frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE)

    SwingUtilities.invokeLater { () =>
      frame.setVisible(true)
    }

    val service1 = Executors.newSingleThreadScheduledExecutor()
    service1.scheduleAtFixedRate(() => {
      SwingUtilities.invokeLater { () =>
        p.repaint()
      }

      World.worldTime += 1
    }, 0, 100, TimeUnit.MILLISECONDS)


    val people = AllPerson()
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