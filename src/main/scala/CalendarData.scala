import scalafx.collections.ObservableBuffer
import scalafx.scene.paint.Color
import scalafx.scene.paint.Color.{LightBlue, LightGreen, LightPink, LightSalmon}

import scala.collection.mutable.Buffer
import java.time.LocalDateTime
import java.time.LocalDate
import java.time.format.DateTimeFormatter



class CalendarData {
  
  private var currentDateTime = LocalDateTime.now
  private var currentDate = LocalDate.now
  private var currentMonth = LocalDate.now
  
  val schoolCategory = Category("school", LightSalmon)
  val workCategory = Category("work", LightPink)
  val hobbyCategory = Category("hobby", LightGreen)
  
  val currentEvents = Buffer[Event]()
  val publicHolidays: Buffer[Event] = FileReader.parseFile("src/resources/basic.ics")
  val currentCategories = ObservableBuffer[Category](schoolCategory, workCategory, hobbyCategory)
  val categoriesMap: Map[String, Color] = currentCategories.map(_.getName).zip(Array(LightSalmon, LightPink, LightGreen)).toMap

  def addEvent(event: Event) = currentEvents += event
  def addCategory(category: Option[Category]) = 
    category match
      case Some(cat) => 
        currentCategories += cat
      case None => currentCategories

  
}
