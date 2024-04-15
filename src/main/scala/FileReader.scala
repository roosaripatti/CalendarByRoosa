import scala.collection.mutable.Buffer
import scala.io.Source
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import scalafx.scene.paint.Color._

object FileReader:
  def parseFile(filePath: String): Buffer[Event] =
    val eventsBuffer = Buffer[Event]()
    // Read the contents of the file
    val fileContents = Source.fromFile(filePath).getLines().toList
    // Initialize variables to store event details
    var name: Option[String] = None
    var startingDate: Option[String] = None
    var endingDate: Option[String] = None

    // Iterate through each line in the file
    for (line <- fileContents) do
      // Check if the line contains event information
      if (line.startsWith("BEGIN:VEVENT")) then
        // Reset event details
        name = None
        startingDate = None
        endingDate = None
      else if (line.startsWith("SUMMARY:")) then
        name = Some(line.substring("SUMMARY:".length).trim)
      else if (line.startsWith("DTSTART;VALUE=DATE:")) then
        val startDate = line.substring("DTSTART;VALUE=DATE:".length).trim
        startingDate = Some(parseAndFormatDate(startDate))
      else if (line.startsWith("DTEND;VALUE=DATE:")) then
        val endDate = line.substring("DTEND;VALUE=DATE:".length).trim
        endingDate = Some(parseAndFormatDate(endDate))
      else if (line.startsWith("END:VEVENT")) then
        // Create an Event object and add it to the buffer
        for {
          eventName <- name
          startDate <- startingDate
          endDate <- endingDate
        } {
          eventsBuffer += Event(eventName, startDate, endDate, "ALLDAY", "ALLDAY", Some(Category(eventName, Blue)), None, false)
        }

    eventsBuffer

  def parseAndFormatDate(dateString: String): String =
    val dateFormatterInput: DateTimeFormatter = DateTimeFormatter.ofPattern("yyyyMMdd")
    val dateFormatterOutput: DateTimeFormatter = DateTimeFormatter.ofPattern("dd.MM.yyyy")
    val localDate = LocalDate.parse(dateString, dateFormatterInput)
    localDate.format(dateFormatterOutput)
