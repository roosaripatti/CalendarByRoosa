import java.time.LocalDateTime
import java.time.LocalDate

class Event(name: String,  startingTime: String, endingTime: String, category: Option[String], notes: Option[String]) {
  def getCategory = this.category
  def getName = this.name
  def getStart = this.startingTime
  def getEnd = this.endingTime
}

