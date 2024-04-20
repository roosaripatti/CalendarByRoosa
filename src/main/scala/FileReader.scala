import scala.collection.mutable.Buffer
import scala.io.Source
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import scalafx.scene.paint.Color.*

import java.io.FileWriter

object FileReader:
  def parseFile(filePath: String): Buffer[Event] =
    val eventsBuffer = Buffer[Event]()
    val fileContents = Source.fromFile(filePath).getLines().toList
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

  def parseAndFormatDate(dateString: String): String =
    val dateFormatterInput: DateTimeFormatter = DateTimeFormatter.ofPattern("yyyyMMdd")
    val dateFormatterOutput: DateTimeFormatter = DateTimeFormatter.ofPattern("dd.MM.yyyy")
    val localDate = LocalDate.parse(dateString, dateFormatterInput)
    localDate.format(dateFormatterOutput)

  def addEventToFile(event: Event, pathToFile: String): Unit =
    val eventString = generateEventString(event)

    val file = Source.fromFile(pathToFile)
    val lines = file.getLines().toList
    file.close()

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


  def generateEventString(event: Event): String =
    val formatter = DateTimeFormatter.ofPattern("yyyyMMdd'T'HHmm00'Z'")
    val startDate = if (event.allDay) LocalDate.parse(event.getStart, DateTimeFormatter.ofPattern("dd.MM.yyyy")).format(DateTimeFormatter.BASIC_ISO_DATE)
      else event.startingTimeFormat.format(formatter)
    val endDate = if (event.allDay) LocalDate.parse(event.getEnd, DateTimeFormatter.ofPattern("dd.MM.yyyy")).format(DateTimeFormatter.BASIC_ISO_DATE)
      else event.endingTimeFormat.format(formatter)

    val categoryString = event.getCategory.map(category => s"CATEGORIES:${category.getName}\n").getOrElse("")
    val notesString = event.getNotes.map(notes => s"DESCRIPTION:$notes\n").getOrElse("")

    s"""BEGIN:VEVENT\nSUMMARY:${event.getName}\nSEQUENCE:0\nSTATUS:CONFIRMED\nTRANSP:TRANSPARENT\nDTSTART:$startDate\nDTEND:$endDate\n$categoryString${notesString}END:VEVENT """

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



@main def filetester() =
  val event = Event("treenit", "12.09.2003", "13.09.2003", "ALLDAY", "ALLDAY", Some(Category("hobby", Blue)), Some("lisätietoja"), false)
  println(FileReader.generateEventString(Event("treenit", "12.09.2003", "13.09.2003", "ALLDAY", "ALLDAY", Some(Category("hobby", Blue)), Some("lisätietoja"), false)))
  println(FileReader.generateEventString(Event("työhaastattelu", "12.09.2003", "13.09.2003", "12.00", "23.59", Some(Category("work", Blue)), Some("lisätietoja"), true)))
  FileReader.addEventToFile(event, "src/resources/userData.ics")
  FileReader.deleteEventFromFile(event, "src/resources/userData.ics")