import scala.collection.mutable.Buffer

class CalendarApp {
  private val allEvents: Buffer[Event] = Buffer[Event]()
  private val categories: Buffer[Category] = Buffer[Category]()
  private val currentView: CalendarView = CalendarView()
  private var currentTime = ???

  def changeView(wantedView: CalendarView): Unit = ???

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
