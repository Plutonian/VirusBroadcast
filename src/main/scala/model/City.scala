package model

import java.util.Random

import com.virus.Constants

/**
 * @ClassName: City
 * @Description: TODO
 * @author: Bruce Young
 * @date: 2020年02月02日 17:48
 */
object City {
  val centerX = 400
  val centerY = 400

  val maxX = 700
  val maxY = 700

  private lazy val personPool = initPersonPool()

  private def initPersonPool() = {

    (0 until Constants.PEOPLE).to(LazyList).map { _ =>
      val random = new Random
      val x = Math.min((100 * random.nextGaussian + City.centerX).asInstanceOf[Int], City.maxX)
      val y = Math.min((100 * random.nextGaussian + City.centerY).asInstanceOf[Int], City.maxY)

      new Person(x, y)
    }.toArray
  }

  def people() = personPool
}