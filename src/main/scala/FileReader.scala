import scala.collection.mutable.Buffer
import scala.io.Source
import java.time.{LocalDate, LocalDateTime, LocalTime}
import java.time.format.DateTimeFormatter
import scalafx.scene.paint.Color.*

import java.io.FileWriter

// object filereader handles both writing and reading from a file
object FileReader:

  // a helper function that gets a date as a string dd.mm.yyyy and changes it to yyyyMMdd aka the format of all day events in the file
  def parseAndFormatDate(dateString: String): String =
    val dateFormatterInput: DateTimeFormatter = DateTimeFormatter.ofPattern("yyyyMMdd")
    val dateFormatterOutput: DateTimeFormatter = DateTimeFormatter.ofPattern("dd.MM.yyyy")
    val localDate = LocalDate.parse(dateString, dateFormatterInput)
    localDate.format(dateFormatterOutput)

  // a helper function that gets a date as a string dd.mm.yyyy and changes it to yyyyMMdd'T'HHmmss'Z' aka the format of normal events in the file
  def parseAndFormatDateTimeDate(datetimeString: String): String =
    val formatter = DateTimeFormatter.ofPattern("yyyyMMdd'T'HHmmss'Z'")
    val dateTime = LocalDate.parse(datetimeString, formatter)
    val formattedDate = DateTimeFormatter.ofPattern("dd.MM.yyyy").format(dateTime)
    formattedDate

  // a helper function that gets a tim as a string hh.mm and changes it to yyyyMMdd'T'HHmmss'Z' aka the format of normal events in the file
  def parseAndFormatDateTimeTime(datetimeString: String): String =
    val formatter = DateTimeFormatter.ofPattern("yyyyMMdd'T'HHmmss'Z'")
    val dateTime = LocalTime.parse(datetimeString.replace("yyyyMMdd'T'", "").replace("ss'Z'", ""), formatter)
    val formattedTime = DateTimeFormatter.ofPattern("HH.mm").format(dateTime)
    formattedTime

  // a function that goes through a given file, and returns the events that it contains to a buffer of events
  def parseFile(filePath: String): Buffer[Event] =
    // creates an empty buffer and stores the lines of the ics file to a list
    val eventsBuffer = Buffer[Event]()
    val file = Source.fromFile(filePath)
    val fileContents = file.getLines().toList
    file.close()

    var name: Option[String] = None
    var startingDate: Option[String] = None
    var startingTime: Option[String] = None
    var endingDate: Option[String] = None
    var endingTime: Option[String] = None
    var category: Option[Category] = None

    // goes through all of the lines fron the file one by one and searches for keywords such as "summary" or "dtstart"
    // then stores and parses the wanted text of that line into the variables listed above
    for (line <- fileContents) do
      if (line.startsWith("BEGIN:VEVENT")) then
        name = None
        startingDate = None
        startingTime = None
        endingDate = None
        endingTime = None
        category = None
      else if (line.startsWith("SUMMARY:")) then
        name = Some(line.substring("SUMMARY:".length).trim)
      else if (line.startsWith("DTSTART;VALUE=DATE:")) then
        val startDate = line.substring("DTSTART;VALUE=DATE:".length).trim
        startingDate = Some(parseAndFormatDate(startDate))
        startingTime = Some("ALLDAY")
      else if (line.startsWith("DTSTART:")) then
        val startDate = line.substring("DTSTART:".length).trim
        startingDate = Some(parseAndFormatDateTimeDate(startDate))
        startingTime = Some(parseAndFormatDateTimeTime(startDate))
      else if (line.startsWith("DTEND;VALUE=DATE:")) then
        val endDate = line.substring("DTEND;VALUE=DATE:".length).trim
        endingDate = Some(parseAndFormatDate(endDate))
        endingTime = Some("ALLDAY")
      else if (line.startsWith("DTEND:")) then
        val endDate = line.substring("DTEND:".length).trim
        endingDate = Some(parseAndFormatDateTimeDate(endDate))
        endingTime = Some(parseAndFormatDateTimeTime(endDate))
      else if (line.startsWith("CATEGORIES:")) then
        val content = line.substring("CATEGORIES:".length).trim
        if content == "school" then category = Some(Category("school", LightSalmon))
        else if content == "work" then category = Some(Category("work", LightPink))
        else if content == "hobby" then category = Some(Category("hobby", LightGreen))
        else category = Some(Category(content, LightGreen))
      // when the end: vevent line is read, a new event with the stored information is created and added to the buffer
      else if (line.startsWith("END:VEVENT")) then
        for {
          eventName <- name
          startDate <- startingDate
          startTime <- startingTime
          endDate <- endingDate
          endTime <- endingTime
        } do
          eventsBuffer += Event(eventName, startDate, endDate, startTime, endTime, category, None, false)
    eventsBuffer

  // very similiar as the previous function, but as  holidays are all day events and have their own cateogires etc, it was easier to make an own function for this
  def parseHolidayFile(filePath: String): Buffer[Event] =
    val eventsBuffer = Buffer[Event]()
    val file = Source.fromFile(filePath)
    val fileContents = file.getLines().toList
    file.close()

    var name: Option[String] = None
    var startingDate: Option[String] = None
    var endingDate: Option[String] = None

    for (line <- fileContents) do
      if (line.startsWith("BEGIN:VEVENT")) then
        name = None
        startingDate = None
        endingDate = None
      else if (line.startsWith("SUMMARY:")) then
        name = Some(line.substring("SUMMARY:".length).trim)
      else if (line.startsWith("DTSTART;VALUE=DATE:")) then
        val startDate = line.substring("DTSTART;VALUE=DATE:".length).trim
        startingDate = Some(parseAndFormatDate(startDate))
        endingDate = Some(parseAndFormatDate(startDate))
      else if (line.startsWith("END:VEVENT")) then
        for {
          eventName <- name
          startDate <- startingDate
          endDate <- endingDate
        } do
          eventsBuffer += Event(eventName, startDate, endDate, "ALLDAY", "ALLDAY", Some(Category(eventName, LightSkyBlue)), None, false)
    eventsBuffer

  // this function is given an event & path to file as a parameter, and it adds that event to the given file
  def addEventToFile(event: Event, pathToFile: String): Unit =
    // calls for the method generateEventString and stores that generated string in a variable
    val eventString = generateEventString(event)
    val file = Source.fromFile(pathToFile)
    val lines = file.getLines().toList
    file.close()
    // to find the right location where to add the event, this function searches for the line that starts with "METHOD" and always adds the data of the event below that
    val methodPublishIndex = lines.indexWhere(_.startsWith("METHOD"))
    if (methodPublishIndex != -1) then
      val updatedLines = lines.patch(methodPublishIndex + 1, Seq(eventString), 0)
      val updatedContent = updatedLines.mkString("\n")
      val writer = new FileWriter(pathToFile)
      try
        writer.write(updatedContent)
      finally
        writer.close()
    else
      println("ERROR: line 'METHOD' not found in the file.")


  // generates the icalendar format string of the wanted event
  def generateEventString(event: Event): String =
    val formatter = DateTimeFormatter.ofPattern("yyyyMMdd'T'HHmm00'Z'")
    val dateStamp = "20240423T2024"
    val startDate =
        if (event.allDay) then
          val start = LocalDate.parse(event.getStart, DateTimeFormatter.ofPattern("dd.MM.yyyy"))
          s"DTSTART;VALUE=DATE:${start.format(DateTimeFormatter.BASIC_ISO_DATE)}"
        else
          s"DTSTART:${event.startingTimeFormat.format(formatter)}"
    val endDate =
      if (event.allDay) then
        val end = LocalDate.parse(event.getEnd, DateTimeFormatter.ofPattern("dd.MM.yyyy"))
        s"DTEND;VALUE=DATE:${end.format(DateTimeFormatter.BASIC_ISO_DATE)}"
      else
        s"DTEND:${event.endingTimeFormat.format(formatter)}"
    val categoryString = event.getCategory.map(category => s"CATEGORIES:${category.getName}\n").getOrElse("")
    val notesString = event.getNotes.map(notes => s"DESCRIPTION:$notes\n").getOrElse("")
    s"""BEGIN:VEVENT\nSUMMARY:${event.getName}\nUID:user1234\nDTSTAMP:$dateStamp\n$startDate\n$endDate\n$categoryString${notesString}END:VEVENT"""

  // deletes the wanted event from the given file: first it generates the event string and searches it from the file and if it is found, it is deleted
  def deleteEventFromFile(deletedEvent: Event, filepath: String): Unit =
    val file = Source.fromFile(filepath)
    val lines = file.getLines().toList
    file.close()
    val eventString = generateEventString(deletedEvent)
    val eventLines = eventString.split("\n")
    val eventIndices = eventLines.map(eventLine => lines.indexOf(eventLine))
    val eventNotFound = eventIndices.contains(-1)

    if (!eventNotFound) then
      val updatedLines = lines.zipWithIndex.filterNot { case (_, index) => eventIndices.contains(index) }.map(_._1)
      val updatedContent = updatedLines.mkString("\n")
      val writer = new FileWriter(filepath)
      try
        writer.write(updatedContent)
      finally
        writer.close()
    else
      println("ERROR: Event not found in the file.")


// testing the FileReader
@main def filetester() =
  //println(FileReader.parseHolidayFile("src/resources/basic.ics").map(_.getName))
  //println(FileReader.parseHolidayFile("src/resources/basic.ics").map(_.getStart))

  println(FileReader.parseFile("src/resources/userData.ics").map(_.getName))
  println(FileReader.parseFile("src/resources/userData.ics").map(_.getStart))
  println(FileReader.parseFile("src/resources/userData.ics").map(_.getEnd))
  println(FileReader.parseFile("src/resources/userData.ics").map(_.startingTimeOnly))
  println(FileReader.parseFile("src/resources/userData.ics").map(_.endingTimeOnly))

  val event = Event("treenit", "12.09.2003", "13.09.2003", "ALLDAY", "ALLDAY", Some(Category("hobby", Blue)), Some("lisätietoja"), false)
  //println(FileReader.generateEventString(Event("treenit", "12.09.2003", "13.09.2003", "ALLDAY", "ALLDAY", Some(Category("hobby", Blue)), Some("lisätietoja"), false)))
  //println(FileReader.generateEventString(Event("työhaastattelu", "12.09.2003", "13.09.2003", "12.00", "23.59", Some(Category("work", Blue)), Some("lisätietoja"), true)))
  //FileReader.addEventToFile(event, "src/resources/userData.ics")
  //FileReader.deleteEventFromFile(event, "src/resources/userData.ics")

