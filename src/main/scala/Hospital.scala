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
  val column = Constants.BED_COUNT / 100

  if (Constants.BED_COUNT == 0) {
    width = 0
    height = 0
  }


  width = column * 6

  for (i <- 0 until column) {
    var j = 10
    while ( {
      j <= 610
    }) {
      val bed = new Bed(800 + i * 6, 100 + j)
      beds.addOne(bed)
      j += 6
    }
  }

  def pickBed: Bed = {

    for (bed <- beds) {
      if (bed.empty) return bed
    }
    null
  }
}