package com.virus

import java.util.Random

import javafx.application.Application
import javafx.scene.paint.Color
import javafx.scene.{Parent, Scene}
import javafx.stage.Stage
import model.City

object Starter {
  def main(args: Array[String]): Unit = {
    initWorld()
    println("Init world OK")

    Application.launch(classOf[App])
  }

  private def initWorld() = {
    //create infected person

    val people = City.people()

    (0 until Constants.ORIGINAL_COUNT).foreach { _ =>
      val index = new Random().nextInt(people.size - 1)


      var person = people(index)
      while ( {
        person.infected
      }) {
        //find next person
        val index = new Random().nextInt(people.size - 1)
        person = people(index)
      }
      person.infect()
    }
  }
}

class App extends Application {


  override def start(primaryStage: Stage): Unit = {
    primaryStage.setTitle("Virus")
    primaryStage.setWidth(1000)
    primaryStage.setHeight(800)


    val proxy = new FXMLLoaderProxy[Parent, ViewController](getClass.getResource("starter.fxml"))
    primaryStage.setScene(new Scene(proxy.node, Color.WHITE))
    primaryStage.show()
  }


  //  override def stop(): Unit = {
  //    timer.stop()
  //  }


}