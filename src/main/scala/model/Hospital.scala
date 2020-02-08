package model

import com.virus.Constants

import scala.collection.mutable

/**
 * @ClassName: Hospital
 * @Description: TODO
 * @author: Bruce Young
 * @date: 2020年02月02日 20:58
 */
object Hospital {
  val x = 800
  val y = 110
  var width = 0
  var height = 606

  private val beds = mutable.ListBuffer[Bed]()

  init()


  private def init() = {
    if (Constants.BED_COUNT == 0) {
      width = 0
      height = 0
    } else {

      //      val (baseX, baseY) = (Hospital.x, 100)
      //      val xSpan = 6
      //      val span = 10
      //      var column = 0
      for (_ <- 0 until Constants.BED_COUNT) {

        val bed = new Bed(0, 0)
        beds.addOne(bed)

      }

      //      val column = Math.max(Constants.BED_COUNT / 100, 1)
      //      width = column * 6
      //
      //      for (i <- 0 until column) {
      //        var j = 10
      //        while ( {
      //          j <= 610
      //        }) {
      //          val bed = new Bed(Hospital.x + i * 6, 100 + j)
      //          beds.addOne(bed)
      //          j += 6
      //        }
      //      }
    }
  }

  def pickBed() = {
    beds.find(bed => bed.empty)
  }
}

/**
 * @ClassName: Bed
 * @Description: TODO
 * @author: Bruce Young
 * @date: 2020年02月02日 21:00
 */
class Bed(val x: Int, val y: Int) {
  var empty = true
}