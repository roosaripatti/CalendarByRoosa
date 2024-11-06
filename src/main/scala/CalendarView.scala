import scala.collection.mutable.Buffer
import java.time.LocalDateTime
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.temporal.TemporalAdjusters
import java.time.temporal.WeekFields
import java.util.Locale
import java.time.temporal.ChronoUnit

// this object keeps count of which week / day the user is currently watching 
object CalendarView:
  private var currentTime = LocalDateTime.now
  private var currentDate = LocalDate.now
  // gets the "LocalDate" of the current weeks monday and sunday. these are not private since CalendarGUI needs to be able to change them according to the currently viewed week.
  private var startTime = currentDate.`with`(TemporalAdjusters.previousOrSame(java.time.DayOfWeek.MONDAY))
  private var endTime = currentDate.`with`(TemporalAdjusters.nextOrSame(java.time.DayOfWeek.SUNDAY))
  
  def getStartTime = startTime
  def getEndTime = endTime

  val weekFields = WeekFields.of(Locale.getDefault())
  def weekNumber = startTime.get(weekFields.weekOfWeekBasedYear())
  def currentYear = endTime.getYear
  def currentMonth = startTime.getMonth

  // stores the number of current weeks weekdays
  def mondayNumber: Int = startTime.getDayOfMonth
  def tuesdayNumber: Int = startTime.plusDays(1).getDayOfMonth
  def wednesdayNumber: Int = startTime.plusDays(2).getDayOfMonth
  def thursdayNumber: Int = startTime.plusDays(3).getDayOfMonth
  def fridayNumber: Int = startTime.plusDays(4).getDayOfMonth
  def saturdayNumber: Int = startTime.plusDays(5).getDayOfMonth
  def sundayNumber: Int = startTime.plusDays(6).getDayOfMonth

  // moves the viewed week one week forward or backwards
  def oneWeekForward(): Unit =
    startTime = startTime.plus(7, ChronoUnit.DAYS)
    endTime = endTime.plus(7, ChronoUnit.DAYS)

  def oneWeekBackwards(): Unit =
    startTime = startTime.minus(7, ChronoUnit.DAYS)
    endTime = endTime.minus(7, ChronoUnit.DAYS)

  // again, cannot be private because calendarGUI needs to be able to update them
  var chosenDay = currentDate
  var chosenDayString = chosenDay.format(DateTimeFormatter.ofPattern("dd.MM.yyyy"))
  def chosenWeekday = chosenDay.getDayOfWeek

