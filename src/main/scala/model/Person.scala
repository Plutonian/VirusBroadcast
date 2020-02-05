package model

import java.util.Random

import com.virus.Constants
import com.virus.Constants.{BROAD_RATE, HOSPITAL_RECEIVE_TIME, SHADOW_TIME}
import model.Person.State.{CONFIRMED, FREEZE, NORMAL, SHADOW}

/**
 * @ClassName: Person
 * @Description: TODO
 * @author: Bruce Young
 * @date: 2020年02月02日 17:05
 */
object Person {

  object State {
    val NORMAL = 0
    val SUSPECTED = NORMAL + 1
    val SHADOW = SUSPECTED + 1
    val CONFIRMED = SHADOW + 1
    val FREEZE = CONFIRMED + 1
    val CURED = FREEZE + 1
  }

}

class Person(var city: City, var x: Int, var y: Int) {
  val sig = 1
  var targetXU = 100 * new Random().nextGaussian + x
  var targetYU = 100 * new Random().nextGaussian + y
  val targetSig = 50
  var infectedTime = 0
  var confirmedTime = 0

  var state = NORMAL

  private var moveTarget: MoveTarget = _
  private val SAFE_DIST = 2f


  def wantMove = {
    sig * new Random().nextGaussian + Constants.u > 0
  }

  def infected = state >= SHADOW

  def infect() = {
    state = SHADOW
    infectedTime = World.worldTime
  }

  def distance(person: Person) = Math.sqrt(Math.pow(x - person.x, 2) + Math.pow(y - person.y, 2))

  private def freezy() = state = FREEZE

  private def moveTo(x: Int, y: Int) = {
    this.x += x
    this.y += y
  }

  private def action(): Unit = {
    if (state == FREEZE) return
    if (!wantMove) return

    if (moveTarget == null || moveTarget.arrived) {
      val targetX = targetSig * new Random().nextGaussian + targetXU
      val targetY = targetSig * new Random().nextGaussian + targetYU
      moveTarget = new MoveTarget(targetX.toInt, targetY.toInt)
    }

    val dX = moveTarget.x - x
    val dY = moveTarget.y - y

    val length = Math.sqrt(Math.pow(dX, 2) + Math.pow(dY, 2))

    if (length < 1) {
      moveTarget.arrived = true
      return
    }

    //    val udX = {
    //      val tx = (dX / length).toInt
    //      if (tx == 0 && dX != 0)
    //        if (dX > 0) 1 else -1
    //      else tx
    //    }

    var udX = (dX / length).toInt
    if (udX == 0 && dX != 0)
      if (dX > 0) udX = 1
      else udX = -1

    var udY = (dY / length).toInt
    if (udY == 0 && dY != 0)
      if (dY > 0) udY = 1
      else udY = -1

    if (x > 700) {
      moveTarget = null
      if (udX > 0)
        udX = -udX
    }

    moveTo(udX, udY)
  }

  def update(): Unit = { //@TODO找时间改为状态机
    if (state >= FREEZE) return

    if (state == CONFIRMED && World.worldTime - confirmedTime >= HOSPITAL_RECEIVE_TIME) {
      Hospital.pickBed() match {
        case Some(bed) =>
          state = FREEZE
          x = bed.x
          y = bed.y
          bed.empty = false
        case None =>
        //          println("隔离区没有空床位")
      }
    }

    if (World.worldTime - infectedTime > SHADOW_TIME && state == SHADOW) {
      state = CONFIRMED
      confirmedTime = World.worldTime
    }

    action()
    if (state >= SHADOW) return


    AllPerson().to(LazyList).filter {
      _.state != NORMAL
    }.foreach { person =>
      val random = new Random().nextFloat

      if (random < BROAD_RATE && distance(person) < SAFE_DIST)
        this.infect()
    }

    //    val people = AllPerson()
    //    for (person <- people) {
    //      if (person.state != NORMAL) {
    //        val random = new Random().nextFloat
    //        if (random < BROAD_RATE && distance(person) < SAFE_DIST) this.infect()
    //      }
    //    }
  }
}