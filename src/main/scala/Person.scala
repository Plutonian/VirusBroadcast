import java.util.Random

import Person.State.{CONFIRMED, FREEZE, NORMAL, SHADOW}

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
  var targetXU = .0
  var targetYU = .0
  val targetSig = 50
  var infectedTime = 0
  var confirmedTime = 0
  private var moveTarget: MoveTarget = _
  var state = NORMAL
  private val SAFE_DIST = 2f

  targetXU = 100 * new Random().nextGaussian + x
  targetYU = 100 * new Random().nextGaussian + y

  def wantMove = {
    val value = sig * new Random().nextGaussian + Constants.u
    value > 0
  }

  def isInfected = state >= SHADOW

  def beInfected() = {
    state = SHADOW
    infectedTime = MyPanel.worldTime
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
    var udX = (dX / length).toInt
    if (udX == 0 && dX != 0) if (dX > 0) udX = 1
    else udX = -1
    var udY = (dY / length).toInt
    if (udY == 0 && udY != 0) if (dY > 0) udY = 1
    else udY = -1
    if (x > 700) {
      moveTarget = null
      if (udX > 0) udX = -udX
    }
    moveTo(udX, udY)
    //        if(wantMove()){
    //        }
  }

  def update(): Unit = { //@TODO找时间改为状态机
    if (state >= FREEZE) return

    if (state == CONFIRMED && MyPanel.worldTime - confirmedTime >= Constants.HOSPITAL_RECEIVE_TIME) {
      val bed = Hospital.pickBed
      if (bed == null) println("隔离区没有空床位")
      else {
        state = FREEZE
        x = bed.x
        y = bed.y
        bed.empty = false
      }
    }

    if (MyPanel.worldTime - infectedTime > Constants.SHADOW_TIME && state == SHADOW) {
      state = CONFIRMED
      confirmedTime = MyPanel.worldTime
    }
    action()
    val people = PersonPool.personList
    if (state >= SHADOW) return
    for (person <- people) {
      if (person.state != NORMAL) {
        val random = new Random().nextFloat
        if (random < Constants.BROAD_RATE && distance(person) < SAFE_DIST) this.beInfected()
      }
    }
  }
}