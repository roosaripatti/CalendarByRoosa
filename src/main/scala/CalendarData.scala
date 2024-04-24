import scalafx.collections.ObservableBuffer
import scalafx.scene.paint.Color
import scalafx.scene.paint.Color.{LightGreen, LightPink, LightSalmon}

import scala.collection.mutable.Buffer

// this class stores the data "locally" so that everything doesn't have to be read from the file
object CalendarData:
  
  // the starting categories 
  val schoolCategory = Category("school", LightSalmon)
  val workCategory = Category("work", LightPink)
  val hobbyCategory = Category("hobby", LightGreen)
  
  // stores the events: in the start they are loaded from the file 
  val currentEvents: Buffer[Event] = FileReader.parseFile("src/resources/userData.ics")
  // stores the public holidays: they are loaded from the file 
  val publicHolidays: Buffer[Event] = FileReader.parseHolidayFile("src/resources/basic.ics")
  // stores the categories as a list
  val currentCategories = ObservableBuffer[Category](schoolCategory, workCategory, hobbyCategory)
  // returns a Map where you can search for events based on a string that contains their name, starting&ending date
  def eventsMap: Map[String, Event] = currentEvents.map(event =>
    s"${event.getName}_${event.getStart}_${event.getEnd}" -> event).toMap
  // Maps that return the right color/category based on their name
  def categoriesMap: Map[String, Color] = currentCategories.map(_.getName).zip(currentCategories.map(_.getColor)).toMap
  def categoriesMap2: Map[String, Category] = currentCategories.map(_.getName).zip(currentCategories).toMap

  // functions to edit the current events / categories of calendarData from outside
  def addEvent(event: Event) = currentEvents += event
  def addCategory(category: Option[Category]) = 
    category match
      case Some(cat) =>
        currentCategories += cat
      case None => currentCategories
  def removeEvent(event: Event): Boolean =
    if currentEvents.contains(event) then 
      currentEvents -= event
      true
    else false


