package model

import java.util.Random

/**
 * @ClassName: PersonPool
 * @Description: TODO
 * @author: Bruce Young
 * @date: 2020年02月02日 17:21
 */

object AllPerson {
  private lazy val personPool = initPersonPool()

  private def initPersonPool() = {
    val city = new City(400, 400)

    (0 until 5000).to(LazyList).map { _ =>
      val random = new Random
      var x = (100 * random.nextGaussian + city.centerX).asInstanceOf[Int]
      val y = (100 * random.nextGaussian + city.centerY).asInstanceOf[Int]
      if (x > 700) x = 700

      new Person(city, x, y)
    }.toArray
  }

  def apply() = personPool


  //  for (_ <- 0 until 5000) {
  //    val random = new Random
  //    var x = (100 * random.nextGaussian + city.centerX).asInstanceOf[Int]
  //    val y = (100 * random.nextGaussian + city.centerY).asInstanceOf[Int]
  //    if (x > 700) x = 700
  //    val person = new Person(city, x, y)
  //    personPool.addOne(person)
  //  }

}