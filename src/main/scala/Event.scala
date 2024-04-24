import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.format.DateTimeFormatter
import java.time.DayOfWeek

// a class that models individual events
class Event(name: String,  startingDate: String, endingDate: String, startingTime: String, endingTime: String, category: Option[Category], notes: Option[String], setReminder: Boolean):
  // returns the values of the private parameters
  def getCategory = this.category
  def getName = this.name
  def getStart = this.startingDate
  def getEnd = this.endingDate
  def getNotes = this.notes
  def getReminder = this.setReminder
  
  // helps in formatting the times and dates
  private val dateFormatter: DateTimeFormatter = DateTimeFormatter.ofPattern("dd.MM.yyyy")
  private val timeFormatter: DateTimeFormatter = DateTimeFormatter.ofPattern("HH.mm")
  // stores a boolean value of whether the event is all day or not
  val allDay: Boolean = if (startingTime.equalsIgnoreCase("ALLDAY")) || (endingTime.equalsIgnoreCase("ALLDAY")) then true else false

  // stores the starting & ending times in Java Time LocalDateTime format
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

  // returns the starting & ending times in Java Time LocalDateTime format
  def startingTimeFormat: LocalDateTime = startingDateTime
  def endingTimeFormat: LocalDateTime = endingDateTime

  // returns the day of the week for the starting & ending times 
  def startingTimeDayOfWeek: DayOfWeek = startingDateTime.getDayOfWeek
  def endingTimeDayOfWeek: DayOfWeek = endingDateTime.getDayOfWeek

  // returns the hh.mm format of startin / ending times
  def startingTimeOnly: String = startingDateTime.toLocalTime.format(timeFormatter)
  def endingTimeOnly: String = endingDateTime.toLocalTime.format(timeFormatter)


