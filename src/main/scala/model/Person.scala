package model

import java.util.Random

import com.virus.Constants
import com.virus.Constants.{BROAD_RATE, HOSPITAL_RECEIVE_TIME, SHADOW_TIME}
import model.State._

/**
 * @ClassName: Person
 * @Description: TODO
 * @author: Bruce Young
 * @date: 2020年02月02日 17:05
 */
class Person(var x: Int, var y: Int) {
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

  def infected = state.value >= SHADOW.value

  def infect() = {
    state = SHADOW
    infectedTime = World.now
  }

  def distance(person: Person) = Math.sqrt(Math.pow(x - person.x, 2) + Math.pow(y - person.y, 2))

  private def freezy() = state = FREEZE

  private def moveTo(x: Int, y: Int) = {
    this.x += x
    this.y += y
  }

  private def action(): Unit = {
    if (state eq FREEZE) return
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

    // if udx == 0 dx< length
    // if udx != 0 that dy equals 0
    var udX = (dX / length).toInt // 0 or 1,-1

    if (udX == 0 && dX != 0)
      if (dX > 0) udX = 1
      else udX = -1

    var udY = (dY / length).toInt
    if (udY == 0 && dY != 0)
      if (dY > 0) udY = 1
      else udY = -1

    if (x > City.maxX) {
      moveTarget = null
      if (udX > 0)
        udX = -udX
    }

    if (y > City.maxY) {
      moveTarget = null

      if (udY > 0)
        udY = -udY
    }
    moveTo(udX, udY)
  }

  def update(): Unit = { //@TODO找时间改为状态机

    //already FREEZE or CURED
    if (state.value >= FREEZE.value) return

    if ((state eq CONFIRMED) && World.now - confirmedTime >= HOSPITAL_RECEIVE_TIME) {
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

    if ((state eq SHADOW) && World.now - infectedTime > SHADOW_TIME) {
      state = CONFIRMED
      confirmedTime = World.now
    }

    action()

    if (state.value >= SHADOW.value) return

    City.people().to(LazyList).filter { p =>
      (p.state eq SUSPECTED) || (p.state eq SHADOW)
    }.foreach { person =>
      val random = new Random().nextFloat

      if (random < BROAD_RATE && distance(person) < SAFE_DIST)
        this.infect()
    }

  }
}