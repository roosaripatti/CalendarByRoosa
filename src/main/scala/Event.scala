import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.format.DateTimeFormatter
import java.time.DayOfWeek

class Event(name: String,  startingDate: String, endingDate: String, startingTime: String, endingTime: String, category: Option[String], notes: Option[String], setReminder: Boolean) {
  def getCategory = this.category
  def getName = this.name
  def getStart = this.startingDate
  def getEnd = this.endingDate

  private val dateFormatter: DateTimeFormatter = DateTimeFormatter.ofPattern("dd.MM.yyyy")
  private val startingDateTime: LocalDateTime = LocalDate.parse(startingDate, dateFormatter).atTime(LocalTime.parse(startingTime, DateTimeFormatter.ofPattern("HH.mm")))
  private val endingDateTime: LocalDateTime = LocalDate.parse(endingDate, dateFormatter).atTime(LocalTime.parse(endingTime, DateTimeFormatter.ofPattern("HH.mm")))

  def startingTimeFormat: LocalDateTime = startingDateTime
  def endingTimeFormat: LocalDateTime = endingDateTime

  def startingTimeDayOfWeek: DayOfWeek = startingDateTime.getDayOfWeek
  def endingTimeDayOfWeek: DayOfWeek = endingDateTime.getDayOfWeek
  
  def startingTimeOnly: String = startingDateTime.toLocalTime.format(DateTimeFormatter.ofPattern("HH.mm"))
  def endingTimeOnly: String = endingDateTime.toLocalTime.format(DateTimeFormatter.ofPattern("HH.mm"))
}

