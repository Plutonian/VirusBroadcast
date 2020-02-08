package com.virus

import java.util.concurrent.{Executors, TimeUnit}

import javafx.animation.AnimationTimer
import javafx.fxml.FXML
import javafx.scene.canvas.Canvas
import javafx.scene.control.{Button, Label}
import javafx.scene.paint.Color
import model.State.{CONFIRMED, FREEZE, NORMAL, SHADOW}
import model.{City, Hospital, World}

class ViewController {
  @FXML private var btnStart: Button = _
  @FXML private var btnStop: Button = _
  @FXML private var btnReset: Button = _
  @FXML private var lbworldTime: Label = _

  @FXML private var worldCanvas: Canvas = _

  var timer: AnimationTimer = _

  def initialize(): Unit = {


    timer = (_: Long) => {
      lbworldTime.setText(s"${World.now}")
      paint(worldCanvas)
    }

    val service = Executors.newSingleThreadScheduledExecutor()

    btnStart.setOnAction { _ =>
      timer.start()
      service.scheduleAtFixedRate(() => {

        val people = City.people()
        //                people(pIndex).update()

        people.groupBy { p => p.state }.foreach {
          case (k, v) =>
            print(s"$k ==>${v.size}\t")
        }
        println()

        for (person <- people) {
          person.update()
        }

        World.run()
      }, 0, 200, TimeUnit.MILLISECONDS)

    }
    btnStop.setOnAction { _ =>
      timer.stop()

    }

    btnReset.setOnAction { _ =>
      timer.stop()
      World.resetTime()
    }


  }

  private def paint(canvas: Canvas): Unit = {
    val g = canvas.getGraphicsContext2D

    //draw City
    g.setFill(Color.valueOf("444444"))
    g.fillRect(0, 0, canvas.getWidth, canvas.getHeight)

    //draw Hospital
    g.setFill(Color.BLACK)
    g.fillRect(Hospital.x, Hospital.y, Hospital.width, Hospital.height)

    val people = City.people()
    //        people(pIndex).update()

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
      g.fillOval(person.x, person.y, 3, 3)
    }

    //        pIndex += 1
    //        if (pIndex >= people.size) pIndex = 0
  }
}