import java.util.Random

import scala.collection.mutable

/**
 * @ClassName: PersonPool
 * @Description: TODO
 * @author: Bruce Young
 * @date: 2020年02月02日 17:21
 */

object AllPerson {
  private val personPool = mutable.ListBuffer[Person]()

  def apply() = personPool

  val city = new City(400, 400)

  for (_ <- 0 until 5000) {
    val random = new Random
    var x = (100 * random.nextGaussian + city.centerX).asInstanceOf[Int]
    val y = (100 * random.nextGaussian + city.centerY).asInstanceOf[Int]
    if (x > 700) x = 700
    val person = new Person(city, x, y)
    personPool.addOne(person)
  }

}