import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.format.DateTimeFormatter
import java.time.DayOfWeek

class Event(name: String,  startingDate: String, endingDate: String, startingTime: String, endingTime: String, category: Option[Category], notes: Option[String], setReminder: Boolean):
  def getCategory = this.category
  def getName = this.name
  def getStart = this.startingDate
  def getEnd = this.endingDate
  def getNotes = this.notes
  def getReminder = this.setReminder

  private val dateFormatter: DateTimeFormatter = DateTimeFormatter.ofPattern("dd.MM.yyyy")
  private val timeFormatter: DateTimeFormatter = DateTimeFormatter.ofPattern("HH.mm")
  val allDay: Boolean = if (startingTime.equalsIgnoreCase("ALLDAY")) || (endingTime.equalsIgnoreCase("ALLDAY")) then true else false

  private val startingDateTime: LocalDateTime =
    if allDay then
      LocalDate.parse(startingDate, dateFormatter).atStartOfDay()
    else
      LocalDate.parse(startingDate, dateFormatter).atTime(LocalTime.parse(startingTime, timeFormatter))

  private val endingDateTime: LocalDateTime =
    if (endingTime.equalsIgnoreCase("ALLDAY")) then
      LocalDate.parse(endingDate, dateFormatter).atTime(LocalTime.MAX)
    else
      LocalDate.parse(endingDate, dateFormatter).atTime(LocalTime.parse(endingTime, timeFormatter))

  def startingTimeFormat: LocalDateTime = startingDateTime
  def endingTimeFormat: LocalDateTime = endingDateTime

  def startingTimeDayOfWeek: DayOfWeek = startingDateTime.getDayOfWeek
  def endingTimeDayOfWeek: DayOfWeek = endingDateTime.getDayOfWeek

  def startingTimeOnly: String = startingDateTime.toLocalTime.format(timeFormatter)
  def endingTimeOnly: String = endingDateTime.toLocalTime.format(timeFormatter)


