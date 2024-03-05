import scala.collection.mutable.Buffer

case class CalendarView():
  var currentTime = ???
  val events: Buffer[Event] = ???
  val startTime = ???
  val endTime = ???

  def getEvents: Buffer[Event] = this.events
  def getReminders: Buffer[Event] = ???

end CalendarView
