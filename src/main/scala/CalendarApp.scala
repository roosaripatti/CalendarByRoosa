import scala.collection.mutable.Buffer
import java.time.LocalDateTime
import java.time.LocalDate
import java.time.format.DateTimeFormatter



class CalendarApp {
  private val allEvents: Buffer[Event] = Buffer[Event]()
  private val categories: Buffer[Category] = Buffer[Category]()
  private var currentView: CalendarView = ???

  private var currentDateTime = LocalDateTime.now
  private var currentDate = LocalDate.now
  private var currentMonth = LocalDate.now

  val dateStr = "2021-06-13"
  val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
  val localDate = LocalDate.parse(dateStr, formatter)
  println(localDate.getDayOfWeek) // prints "SUNDAY"

  def addEvent(newEvent: Event): Unit =
    this.allEvents += newEvent

  def deleteEvent(deletedEvent: Event): Unit =
    this.allEvents.remove(this.allEvents.indexOf(deletedEvent))

  def getAllEvents: Buffer[Event] = this.allEvents

  def addCategory(category: Category): Unit =
    // luo uusi kategoria nimeltä category
    this.categories += category

  def eventsFromFiles(file: String): Unit = ???
  def eventsToFiles(event: Event, file: String): Unit = ???

  def filterEvents(category: Category): Buffer[Event] =
    allEvents.filter(_.getCategory == category)

  def sortByTheme(theme: String): Buffer[Event] = ???

  def eventsByCategories: Map[Option[Category], Event] =
    val currentCategories = allEvents.map(_.getCategory)
    currentCategories.zip(allEvents).toMap
  
}
