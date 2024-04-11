import java.time.LocalDateTime
import java.time.LocalDate

class Event(name: String, date: LocalDate, startingTime: LocalDateTime, endingTime: LocalDateTime, category: Option[Category], notes: String) {
  def getCategory = this.category
  def getName = this.name
  def getDate = this.date
  def getStart = this.startingTime
  def getEnd = this.endingTime
}

