import scala.collection.mutable.Buffer
import java.time.LocalDateTime
import java.time.LocalDate
import java.time.format.DateTimeFormatter



class CalendarData {
  
  private var currentDateTime = LocalDateTime.now
  private var currentDate = LocalDate.now
  private var currentMonth = LocalDate.now
  val currentEvents = Buffer[Event]()

  def addEvent(event: Event) = currentEvents += event

  
}
