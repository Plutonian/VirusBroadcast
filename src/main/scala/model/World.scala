package model

import java.util.concurrent.atomic.AtomicInteger

object World {
  private val worldTime = new AtomicInteger(0)

  def now = {
    worldTime.get()
  }

  def run() = {
    worldTime.incrementAndGet()
  }

  def resetTime() = {
    worldTime.set(0)
  }
}
