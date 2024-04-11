import scala.collection.mutable.Buffer
import java.time.LocalDateTime
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.temporal.TemporalAdjusters

abstract class CalendarView:
  var currentTime = LocalDateTime.now
  var currentDate = LocalDate.now
  var currentMonth = currentTime.getMonth

  val startTime: LocalDate
  val endTime: LocalDate

end CalendarView

class WeeklyView extends CalendarView:
  val startTime = currentDate.`with`(TemporalAdjusters.previousOrSame(java.time.DayOfWeek.MONDAY))
  val endTime = currentDate.`with`(TemporalAdjusters.nextOrSame(java.time.DayOfWeek.SUNDAY))

  val startDayNumber = startTime.getDayOfMonth
  val endDayNumber = endTime.getDayOfMonth

class MonthlyView extends CalendarView:
  val startTime = currentDate.`with`(TemporalAdjusters.firstDayOfMonth())
  val endTime = currentDate.`with`(TemporalAdjusters.lastDayOfMonth())

  val firstDayOfWeek = startTime.getDayOfWeek
  val firstDayOfMonth = endTime.getDayOfMonth
end MonthlyView


class DailyView extends CalendarView:
  val startTime = ???
  val endTime = ???
end DailyView

