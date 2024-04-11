import scala.collection.mutable.Buffer
import java.time.LocalDateTime
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.temporal.TemporalAdjusters
import java.time.temporal.WeekFields
import java.util.Locale
import java.time.temporal.ChronoUnit

class CalendarView:
  var currentTime = LocalDateTime.now
  var currentDate = LocalDate.now

  var startTime = currentDate.`with`(TemporalAdjusters.previousOrSame(java.time.DayOfWeek.MONDAY))
  var endTime = currentDate.`with`(TemporalAdjusters.nextOrSame(java.time.DayOfWeek.SUNDAY))

  val weekFields = WeekFields.of(Locale.getDefault())
  def weekNumber = startTime.get(weekFields.weekOfWeekBasedYear())
  def currentYear = endTime.getYear
  def currentMonth = startTime.getMonth

  def mondayNumber: Int = startTime.getDayOfMonth
  def tuesdayNumber: Int = startTime.plusDays(1).getDayOfMonth
  def wednesdayNumber: Int = startTime.plusDays(2).getDayOfMonth
  def thursdayNumber: Int = startTime.plusDays(3).getDayOfMonth
  def fridayNumber: Int = startTime.plusDays(4).getDayOfMonth
  def saturdayNumber: Int = startTime.plusDays(5).getDayOfMonth
  def sundayNumber: Int = startTime.plusDays(6).getDayOfMonth

  def oneWeekForward(): Unit =
    startTime = startTime.plus(7, ChronoUnit.DAYS)
    endTime = endTime.plus(7, ChronoUnit.DAYS)

  def oneWeekBackwards(): Unit =
    startTime = startTime.minus(7, ChronoUnit.DAYS)
    endTime = endTime.minus(7, ChronoUnit.DAYS)

  var chosenDay = currentDate
  var chosenDayString = chosenDay.format(DateTimeFormatter.ofPattern("dd.MM.yyyy"))
  def chosenWeekday = chosenDay.getDayOfWeek

