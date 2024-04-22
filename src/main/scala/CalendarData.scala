import scalafx.collections.ObservableBuffer
import scalafx.scene.paint.Color
import scalafx.scene.paint.Color.{LightGreen, LightPink, LightSalmon}

import scala.collection.mutable.Buffer

object CalendarData:
  
  val schoolCategory = Category("school", LightSalmon)
  val workCategory = Category("work", LightPink)
  val hobbyCategory = Category("hobby", LightGreen)
  
  val currentEvents: Buffer[Event] = FileReader.parseFile("src/resources/userData.ics")
  val publicHolidays: Buffer[Event] = FileReader.parseHolidayFile("src/resources/basic.ics")
  val currentCategories = ObservableBuffer[Category](schoolCategory, workCategory, hobbyCategory)
  
  def eventsMap: Map[String, Event] = currentEvents.map(event =>
    s"${event.getName}_${event.getStart}_${event.getEnd}" -> event).toMap
  def categoriesMap: Map[String, Color] = currentCategories.map(_.getName).zip(currentCategories.map(_.getColor)).toMap
  def categoriesMap2: Map[String, Category] = currentCategories.map(_.getName).zip(currentCategories).toMap

  def addEvent(event: Event) = currentEvents += event
  def addCategory(category: Option[Category]) = 
    category match
      case Some(cat) =>
        currentCategories += cat
      case None => currentCategories

  def removeEvent(event: Event): Unit =
    currentEvents -= event


