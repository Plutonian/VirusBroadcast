package com.virus

import javafx.animation.AnimationTimer
import javafx.fxml.FXML
import javafx.scene.canvas.{Canvas, GraphicsContext}
import javafx.scene.control.Button
import javafx.scene.paint.Color
import model.Person.State.{CONFIRMED, FREEZE, NORMAL, SHADOW}
import model.{AllPerson, Hospital, World}

class ViewController {
  @FXML private var btnStart: Button = _
  @FXML private var btnStop: Button = _
  @FXML private var btnReset: Button = _

  @FXML private var worldCanvas: Canvas = _

  var timer: AnimationTimer = _

  def initialize(): Unit = {


    timer = (_: Long) => {
      println("draw")
      paint(worldCanvas.getGraphicsContext2D)
      World.worldTime += 1
    }

    btnStart.setOnAction { _ =>
      timer.start()
    }
    btnStop.setOnAction { _ =>
      timer.stop()
    }

    btnReset.setOnAction { _ =>
      timer.stop()
      World.worldTime = 0
    }


  }

  private def paint(g: GraphicsContext): Unit = {

    //draw bg
    g.setFill(Color.valueOf("444444"))
    g.fillRect(0, 0, 1200, 800)

    //draw border
    //    g.setColor(new Color(0x00ff00))

    g.setFill(Color.RED)
    g.fillRect(Hospital.x, Hospital.y, Hospital.width, Hospital.height)

    val people = AllPerson()
    //    people(pIndex).update()

    for (person <- people) {
      person.state match {
        case NORMAL =>
          g.setFill(Color.valueOf("dddddd"))
        case SHADOW =>
          g.setFill(Color.valueOf("ffee00"))
        case CONFIRMED =>
          g.setFill(Color.valueOf("ff0000"))
        case FREEZE =>
          g.setFill(Color.valueOf("ff0000"))
      }
      person.update()
      g.fillOval(person.x, person.y, 3, 3)
    }
    //    pIndex += 1
    //    if (pIndex >= people.size) pIndex = 0
  }
}