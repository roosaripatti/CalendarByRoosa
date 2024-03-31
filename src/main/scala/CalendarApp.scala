import scala.collection.mutable.Buffer

class CalendarApp {
  private val allEvents: Buffer[Event] = Buffer[Event]()
  private val categories: Buffer[Category] = Buffer[Category]()
  private val currentView: CalendarView = CalendarView()
  private var currentTime = ???

  def changeView(wantedView: CalendarView): Unit = ???
  def addEvent(newEvent: Event): Boolean = ???
  def deleteEvent(deletedEvent: Event): Boolean = ???
  def getAllEvents: Buffer[Event] = ???
  def addCategory(category: Category): Unit = ???
  def eventsFromFiles(file: String): Unit = ???
  def eventsToFiles(event: Event, file: String): Unit = ???
  def filterEvents(category: Category): Buffer[Event] = ???
  def sortByTheme(theme: String): Buffer[Event] = ???
  def sendReminder(event: Event): Boolean = ???
  def eventsByCategories: Map[Category, Event] = ???
  
}
