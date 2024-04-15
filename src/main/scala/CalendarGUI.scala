import javafx.collections.FXCollections
import scalafx.Includes._
import scalafx.application.JFXApp3
import scalafx.geometry.{Insets, Pos}
import scalafx.scene.Scene
import scalafx.scene.image.{Image, ImageView}
import scalafx.scene.layout.{Background, Border, ColumnConstraints, GridPane, HBox, RowConstraints, VBox}
import scalafx.scene.control.{Button, CheckBox, ChoiceBox, ComboBox, Label, Menu, MenuBar, MenuItem, ScrollPane, TextField}
import scalafx.scene.paint.Color.*
import scalafx.scene.paint.Color
import scalafx.scene.text.{Font, FontPosture, FontWeight}
import scalafx.scene.input.MouseEvent

import java.time.{DayOfWeek, LocalDate}
import java.time.format.DateTimeFormatter

object CalendarGUI extends JFXApp3:

  // creates the objects containing the calendar data and the view
  val calendarData = CalendarData()
  val weeklyView = CalendarView()
  val fileReader = FileReader
  val openSansFont = Font("Open Sans", 10)

  def start() =

    // sets the view and its size
    stage = new JFXApp3.PrimaryStage:
      title = "Calendar by Roosa"
      width = 1400
      height = 850

    // sets the view as a grid
    val rootWeek = GridPane()
    val rootDay = GridPane()
    val rootMonth = GridPane()
    val rootEvent = GridPane()

    val startScene = Scene(parent = rootWeek)
    val dayScene = Scene(parent = rootDay)
    val eventScene = Scene(parent = rootEvent)

    stage.scene = startScene

    // creates the rows and columns of the weekly view as boxes
    val headerBox = new HBox:
      padding = Insets(40, 0, 20, 0)
      spacing = 20
    val timeBox = VBox()
    val menuBox =  HBox()
    // weekdays containers
    val monday = new VBox:
       margin = Insets(2, 2, 35, 2)
       border = Border.stroke(Black)
    val tuesday = new VBox():
      margin = Insets(2, 2, 35, 2)
      border = Border.stroke(Black)
    val wednesday = new VBox():
      margin = Insets(2, 2, 35, 2)
      border = Border.stroke(Black)
    val thursday = new VBox():
      margin = Insets(2, 2, 35, 2)
      border = Border.stroke(Black)
    val friday = new VBox():
      margin = Insets(2, 2, 35, 2)
      border = Border.stroke(Black)
    val saturday = new VBox():
      margin = Insets(2, 2, 35, 2)
      border = Border.stroke(Black)
    val sunday = new VBox():
      margin = Insets(2, 2, 35, 2)
      border = Border.stroke(Black)
    val reminder = new VBox():
      margin = Insets(2, 2, 35, 2)
      border = Border.stroke(Black)

    // week headers containers
    val mondayHead = new VBox():
      margin = Insets(2)
      background = Background.fill(Color.rgb(226,200,246))
      border = Border.stroke(Black)
    val tuesdayHead = new VBox():
      margin = Insets(2)
      background = Background.fill(Color.rgb(226,200,246))
      border = Border.stroke(Black)
    val wednesdayHead = new VBox():
      margin = Insets(2)
      background = Background.fill(Color.rgb(226,200,246))
      border = Border.stroke(Black)
    val thursdayHead = new VBox():
      margin = Insets(2)
      background = Background.fill(Color.rgb(226,200,246))
      border = Border.stroke(Black)
    val fridayHead = new VBox():
      margin = Insets(2)
      background = Background.fill(Color.rgb(226,200,246))
      border = Border.stroke(Black)
    val saturdayHead = new VBox():
      margin = Insets(2)
      background = Background.fill(Color.rgb(226,200,246))
      border = Border.stroke(Black)
    val sundayHead = new VBox():
      margin = Insets(2)
      background = Background.fill(Color.rgb(226,200,246))
      border = Border.stroke(Black)
    val reminderHead = new VBox():
      margin = Insets(2)
      background = Background.fill(LightSkyBlue)
      border = Border.stroke(Black)
    // creates the header label
    val headerText = Label(s"${weeklyView.currentMonth} WEEK ${weeklyView.weekNumber}")
    // menu symbol
    val menuImage = ImageView(Image("file:src/resources/icons/icons8-menu-50.png"))
    menuImage.setFitHeight(35)
    menuImage.preserveRatio = true

    // the function that updates the events of week we are looking at to the GUI:
    // first it erases the events of the previously shown week, then iterates through the buffer containing all events
    // and finally filters the events of the current week and adds them to the corresponding weekday container
    def updateEventsOnWeeklyView(): Unit =
      monday.children.clear()
      tuesday.children.clear()
      wednesday.children.clear()
      thursday.children.clear()
      friday.children.clear()
      saturday.children.clear()
      sunday.children.clear()
      reminder.children.clear()

      for (event <- calendarData.currentEvents ++ calendarData.publicHolidays) do
        val eventStartingDate = LocalDate.parse(event.getStart, DateTimeFormatter.ofPattern("dd.MM.yyyy"))
        val eventEndingDate = LocalDate.parse(event.getEnd, DateTimeFormatter.ofPattern("dd.MM.yyyy"))

        if (!eventStartingDate.isBefore(weeklyView.startTime) && !eventEndingDate.isAfter(weeklyView.endTime))
          val startingDayOfWeek = eventStartingDate.getDayOfWeek
          val eventName = event.getName

          val label = new Label(s"${eventName}"):
            margin = Insets(2)
          label.setFont(openSansFont)

          startingDayOfWeek match
            case DayOfWeek.MONDAY    => monday.children.add(label)
            case DayOfWeek.TUESDAY   => tuesday.children.add(label)
            case DayOfWeek.WEDNESDAY => wednesday.children.add(label)
            case DayOfWeek.THURSDAY  => thursday.children.add(label)
            case DayOfWeek.FRIDAY    => friday.children.add(label)
            case DayOfWeek.SATURDAY  => saturday.children.add(label)
            case DayOfWeek.SUNDAY    => sunday.children.add(label)

          if (event.getReminder) then
            val reminderLabel = new Label(s"Remember to do the task:\n  ${eventName}"):
              margin = Insets(2)
            reminderLabel.setFont(openSansFont)
            reminder.children.add(reminderLabel)



    // creates the menu items
    val menuItemDay = new MenuItem("Day"):
      onAction = _ =>
        stage.scene = dayScene
    val menuItemWeek = new MenuItem("Week"):
      onAction = _ =>
        stage.scene = startScene
        updateEventsOnWeeklyView()
    val menuItemMonth = new MenuItem("Month"):
      onAction = _ =>
        stage.scene = new Scene(rootMonth)
    // creates the main menu
    val mainMenu = new Menu("", menuImage):
      items = Array(menuItemDay, menuItemWeek, menuItemMonth)
    val mainMenuBar = new MenuBar:
      menus = Array(mainMenu)

// creating the containers for the daily view
    val goBackBox = new VBox()
    val dailyTimeHead = new VBox():
      border = Border.stroke(Black)
    val dailyContentHead = new VBox():
      border = Border.stroke(Black)
    val dailyMenuBox = new VBox()

    val dailyTimeBoxes = Array.fill(24)(new HBox {
      border = Border.stroke(Black)
      alignment = Pos.Center})

// Assign individual variables to each daily time box

    val dailyContentBoxes = Array.fill(24)(new HBox {
      border = Border.stroke(Black)})

    // styling the header boxes in daily view
    val dailyHeaderText = Label(s"${weeklyView.chosenWeekday}  ${weeklyView.chosenDayString}")
    dailyHeaderText.font = Font.loadFont("file:src/resources/Evafiya-Font/Evafiya.ttf", 30)
    dailyHeaderText.textFill = Black
    dailyContentHead.setAlignment(Pos.Center)
    dailyContentHead.children.add(dailyHeaderText)

    val dailyTimeText = Label("time")
    dailyTimeText.font = Font.loadFont("file:src/resources/Evafiya-Font/Evafiya.ttf", 20)
    dailyTimeText.textFill = Black
    dailyTimeHead.setAlignment(Pos.Center)
    dailyTimeHead.children.add(dailyTimeText)

    // function that updates the header of the daily view when the day is changed
    def updateDailyHeaderText() =
      dailyHeaderText.text = s"${weeklyView.chosenWeekday} ${weeklyView.chosenDayString}"

    def updateEventsOnDailyView(chosenDay: LocalDate): Unit =
      for hour <- 0 until 23 do
          val slotContainer = dailyContentBoxes(hour)
          slotContainer.setStyle("")
          slotContainer.getChildren.clear()

      val filteredEvents = calendarData.currentEvents.filter(event =>
        LocalDate.parse(event.getStart, DateTimeFormatter.ofPattern("dd.MM.yyyy")).isEqual(chosenDay))
      filteredEvents.foreach(event =>
        val startingTime = event.startingTimeFormat

        val hour = startingTime.getHour
        val slotContainer = dailyContentBoxes(hour - 1)

        event.getCategory match
          case Some(category) =>
            val color = category.getColor
            val colorString = s"rgb(${color.getRed * 255},${color.getGreen * 255},${color.getBlue * 255})"
            slotContainer.setStyle(s"-fx-background-color: $colorString")
          case None =>
            slotContainer.setStyle("-fx-background-color: LightBlue")

        val label = new Label(event.getName)
        slotContainer.getChildren.add(label))

    def updateDailyView(): Unit =
      updateEventsOnDailyView(weeklyView.chosenDay)

    // labels of each day in the daily view
    val mondayText = new Label("monday  " + weeklyView.mondayNumber + "."):
      onMouseClicked = (event) =>
        weeklyView.chosenDay = weeklyView.startTime
        weeklyView.chosenDayString = weeklyView.chosenDay.format(DateTimeFormatter.ofPattern("dd.MM.yyyy"))
        updateDailyHeaderText()
        updateDailyView()
        stage.scene = dayScene
    val tuesdayText = new Label("tuesday  " + weeklyView.tuesdayNumber + "." ):
      onMouseClicked = (event) =>
        weeklyView.chosenDay = weeklyView.startTime.plusDays(1)
        weeklyView.chosenDayString = weeklyView.chosenDay.format(DateTimeFormatter.ofPattern("dd.MM.yyyy"))
        updateDailyHeaderText()
        updateDailyView()
        stage.scene = dayScene
    val wednesdayText = new Label("wednesday  " + weeklyView.wednesdayNumber + "."):
      onMouseClicked = (event) =>
        weeklyView.chosenDay = weeklyView.startTime.plusDays(2)
        weeklyView.chosenDayString = weeklyView.chosenDay.format(DateTimeFormatter.ofPattern("dd.MM.yyyy"))
        updateDailyHeaderText()
        updateDailyView()
        stage.scene = dayScene
    val thursdayText = new Label("thursday  " + weeklyView.thursdayNumber + "."):
      onMouseClicked = (event) =>
        weeklyView.chosenDay = weeklyView.startTime.plusDays(3)
        weeklyView.chosenDayString = weeklyView.chosenDay.format(DateTimeFormatter.ofPattern("dd.MM.yyyy"))
        updateDailyHeaderText()
        updateDailyView()
        stage.scene = dayScene
    val fridayText = new Label("friday  " + weeklyView.fridayNumber + "."):
      onMouseClicked = (event) =>
        weeklyView.chosenDay = weeklyView.startTime.plusDays(4)
        weeklyView.chosenDayString = weeklyView.chosenDay.format(DateTimeFormatter.ofPattern("dd.MM.yyyy"))
        updateDailyHeaderText()
        updateDailyView()
        stage.scene = dayScene
    val saturdayText = new Label("saturday  " + weeklyView.saturdayNumber + "."):
      onMouseClicked = (event) =>
        weeklyView.chosenDay = weeklyView.startTime.plusDays(5)
        weeklyView.chosenDayString = weeklyView.chosenDay.format(DateTimeFormatter.ofPattern("dd.MM.yyyy"))
        updateDailyHeaderText()
        updateDailyView()
        stage.scene = dayScene
    val sundayText = new Label("sunday  " + weeklyView.sundayNumber + "."):
      onMouseClicked = (event) =>
        weeklyView.chosenDay = weeklyView.startTime.plusDays(6)
        weeklyView.chosenDayString = weeklyView.chosenDay.format(DateTimeFormatter.ofPattern("dd.MM.yyyy"))
        updateDailyHeaderText()
        updateDailyView()
        stage.scene = dayScene
    val remindersText = Label("reminders of")
    val remindersText2 = Label("this week")

    val weekButton =  Label("week  " + weeklyView.weekNumber + " of " + weeklyView.currentYear)


   // function that updates the weekly labels in the weekly view
    def updateLabels() =
      mondayText.text = s"monday  ${weeklyView.mondayNumber}."
      tuesdayText.text = s"tuesday  ${weeklyView.tuesdayNumber}."
      wednesdayText.text = s"wednesday  ${weeklyView.wednesdayNumber}."
      thursdayText.text = s"thursday  ${weeklyView.thursdayNumber}."
      fridayText.text = s"friday  ${weeklyView.fridayNumber}."
      saturdayText.text = s"saturday  ${weeklyView.saturdayNumber}."
      sundayText.text = s"sunday  ${weeklyView.sundayNumber}."
      weekButton.text = s"week  ${weeklyView.weekNumber} of ${weeklyView.currentYear}"
      headerText.text = s"${weeklyView.currentMonth} WEEK ${weeklyView.weekNumber}"

    // buttons to change the week currently viewing
    val rightButton = new Button(">"):
      onAction = (event) =>
        weeklyView.oneWeekForward()
        updateLabels()
        updateEventsOnWeeklyView()
    val leftButton = new Button("<"):
      onAction = (event) =>
        weeklyView.oneWeekBackwards()
        updateLabels()
        updateEventsOnWeeklyView()

    // aligning weekday headers
    mondayHead.setAlignment(Pos.Center)
    tuesdayHead.setAlignment(Pos.Center)
    wednesdayHead.setAlignment(Pos.Center)
    thursdayHead.setAlignment(Pos.Center)
    fridayHead.setAlignment(Pos.Center)
    saturdayHead.setAlignment(Pos.Center)
    sundayHead.setAlignment(Pos.Center)
    reminderHead.setAlignment(Pos.Center)

    // inheritances
    headerBox.children.addAll(headerText, leftButton, weekButton, rightButton)
    menuBox.children.add(mainMenuBar)
    mondayHead.children.add(mondayText)
    tuesdayHead.children.add(tuesdayText)
    wednesdayHead.children.add(wednesdayText)
    thursdayHead.children.add(thursdayText)
    fridayHead.children.add(fridayText)
    saturdayHead.children.add(saturdayText)
    sundayHead.children.add(sundayText)
    reminderHead.children.add(remindersText)
    reminderHead.children.add(remindersText2)

    // ratios of the boxes in the view
    val timeColumn = new ColumnConstraints:
      percentWidth = 8
    val weekDayColumn = new ColumnConstraints:
      percentWidth = 10
    val menuColumn = new ColumnConstraints:
      percentWidth = 15
    val headerRow = new RowConstraints:
      percentHeight = 27
    val weekRow = new RowConstraints:
      percentHeight = 70
    val weekLabelRow = new RowConstraints:
      percentHeight = 8

    // adds the boxes to the right places in the weekly root
    rootWeek.add(timeBox, 0, 0, 1, 3)
    rootWeek.add(headerBox, 1, 0, 8, 1)
    rootWeek.add(menuBox, 8, 0, 8, 3)

    rootWeek.add(monday, 1, 2, 1, 1)
    rootWeek.add(tuesday, 2, 2, 1, 1)
    rootWeek.add(wednesday, 3, 2, 1, 1)
    rootWeek.add(thursday, 4, 2, 1, 1)
    rootWeek.add(friday, 5, 2, 1, 1)
    rootWeek.add(saturday, 6, 2, 1, 1)
    rootWeek.add(sunday, 7, 2, 1, 1)
    rootWeek.add(reminder, 8, 2, 1, 1)

    rootWeek.add(mondayHead, 1, 1)
    rootWeek.add(tuesdayHead, 2, 1)
    rootWeek.add(wednesdayHead, 3, 1)
    rootWeek.add(thursdayHead, 4, 1)
    rootWeek.add(fridayHead, 5, 1)
    rootWeek.add(saturdayHead, 6, 1)
    rootWeek.add(sundayHead, 7, 1)
    rootWeek.add(reminderHead, 8, 1)

    // adds the rows and columns in the right order in the weekly root
    rootWeek.columnConstraints = Array(timeColumn, weekDayColumn, weekDayColumn, weekDayColumn, weekDayColumn, weekDayColumn, weekDayColumn, weekDayColumn, weekDayColumn, menuColumn) // Add constraints in order
    rootWeek.rowConstraints = Array(headerRow, weekLabelRow, weekRow)

    // styles the texts
    headerText.font = Font.loadFont("file:src/resources/Evafiya-Font/Evafiya.ttf", 55)
    headerText.textFill = Black

    mondayText.font = Font.font("Open sans", 10)
    tuesdayText.font = Font.font("Open sans", 10)
    wednesdayText.font = Font.font("Open sans", 10)
    thursdayText.font = Font.font("Open sans", 10)
    fridayText.font = Font.font("Open sans", 10)
    saturdayText.font = Font.font("Open sans", 10)
    sundayText.font = Font.font("Open sans", 10)
    remindersText.font = Font.font("Open sans", 10)
    remindersText2.font = Font.font("Open sans", 10)

    // setting the "go back" button
    val goBackImage = ImageView(Image("file:src/resources/icons/back-button.png"))
    goBackImage.setFitHeight(35)
    goBackImage.preserveRatio = true
    val goBackButton = new Button("", goBackImage):
      onAction = _ => stage.scene = startScene

    // creates the add event -image and adds it to the daily view
    val addEventImage = ImageView(Image("file:src/resources/icons/add.png"))
    addEventImage.setFitHeight(35)
    addEventImage.preserveRatio = true
    val addEventButton = new Button("", addEventImage):
      onAction = _ => stage.scene = eventScene

    goBackBox.children.add(goBackButton)
    menuBox.children.add(addEventButton)

    // creates the ratios of the containers in the daily view
    val goBackColumn = new ColumnConstraints:
      percentWidth = 25
    val dailyTimeColumn = new ColumnConstraints:
      percentWidth = 10
    val dailyContentColumn = new ColumnConstraints:
       percentWidth = 40
    val dailyMenuColumn = new ColumnConstraints:
      percentWidth = 25
    val dailyHeaderRow = new RowConstraints:
      percentHeight = 10
    val dailyContentRow = new RowConstraints:
      percentHeight = 7.5

    // styles the buttons to be transparent
    leftButton.style = "-fx-background-color: transparent"
    rightButton.style = "-fx-background-color: transparent"
    goBackButton.style = "-fx-background-color: transparent"
    mainMenuBar.style = "-fx-background-color: transparent"
    addEventButton.style = "-fx-background-color: transparent"

    val customFont = Font.loadFont("file:src/resources/Evafiya-Font/Evafiya.ttf", 20)

    dailyTimeBoxes(0).children.add(new Label("0:00"){ font = customFont })
    dailyTimeBoxes(1).children.add(new Label("1:00"){ font = customFont })
    dailyTimeBoxes(2).children.add(new Label("2:00"){ font = customFont })
    dailyTimeBoxes(3).children.add(new Label("3:00"){ font = customFont })
    dailyTimeBoxes(4).children.add(new Label("4:00"){ font = customFont })
    dailyTimeBoxes(5).children.add(new Label("5:00"){ font = customFont })
    dailyTimeBoxes(6).children.add(new Label("6:00"){ font = customFont })
    dailyTimeBoxes(7).children.add(new Label("7:00"){ font = customFont })
    dailyTimeBoxes(8).children.add(new Label("8:00"){ font = customFont })
    dailyTimeBoxes(9).children.add(new Label("9:00"){ font = customFont })
    dailyTimeBoxes(10).children.add(new Label("10:00"){ font = customFont })
    dailyTimeBoxes(11).children.add(new Label("11:00"){ font = customFont })
    dailyTimeBoxes(12).children.add(new Label("12:00") { font = customFont })
    dailyTimeBoxes(13).children.add(new Label("13:00") { font = customFont })
    dailyTimeBoxes(14).children.add(new Label("14:00") { font = customFont })
    dailyTimeBoxes(15).children.add(new Label("15:00") { font = customFont })
    dailyTimeBoxes(16).children.add(new Label("16:00") { font = customFont })
    dailyTimeBoxes(17).children.add(new Label("17:00") { font = customFont })
    dailyTimeBoxes(18).children.add(new Label("18:00") { font = customFont })
    dailyTimeBoxes(19).children.add(new Label("19:00") { font = customFont })
    dailyTimeBoxes(20).children.add(new Label("20:00") { font = customFont })
    dailyTimeBoxes(21).children.add(new Label("21:00") { font = customFont })
    dailyTimeBoxes(22).children.add(new Label("22:00") { font = customFont })
    dailyTimeBoxes(23).children.add(new Label("23:00") { font = customFont })

    // adds the content to the dialy view
    rootDay.add(goBackBox, 0, 0, 1, 1)
    rootDay.add(dailyTimeHead, 1, 0, 1, 1)
    rootDay.add(dailyContentHead, 2, 0, 1, 1)
    rootDay.add(dailyMenuBox, 3, 0, 1, 2)
    rootDay.add(dailyTimeBoxes(0), 1, 1, 1, 1)
    rootDay.add(dailyTimeBoxes(1), 1, 2, 1, 1)
    rootDay.add(dailyTimeBoxes(2), 1, 3, 1, 1)
    rootDay.add(dailyTimeBoxes(3), 1, 4, 1, 1)
    rootDay.add(dailyTimeBoxes(4), 1, 5, 1, 1)
    rootDay.add(dailyTimeBoxes(5), 1, 6, 1, 1)
    rootDay.add(dailyTimeBoxes(6), 1, 7, 1, 1)
    rootDay.add(dailyTimeBoxes(7), 1, 8, 1, 1)
    rootDay.add(dailyTimeBoxes(8), 1, 9, 1, 1)
    rootDay.add(dailyTimeBoxes(9), 1, 10, 1, 1)
    rootDay.add(dailyTimeBoxes(10), 1, 11, 1, 1)
    rootDay.add(dailyTimeBoxes(11), 1, 12, 1, 1)
    rootDay.add(dailyTimeBoxes(12), 1, 13, 1, 1)
    rootDay.add(dailyTimeBoxes(13), 1, 14, 1, 1)
    rootDay.add(dailyTimeBoxes(14), 1, 15, 1, 1)
    rootDay.add(dailyTimeBoxes(15), 1, 16, 1, 1)
    rootDay.add(dailyTimeBoxes(16), 1, 17, 1, 1)
    rootDay.add(dailyTimeBoxes(17), 1, 18, 1, 1)
    rootDay.add(dailyTimeBoxes(18), 1, 19, 1, 1)
    rootDay.add(dailyTimeBoxes(19), 1, 20, 1, 1)
    rootDay.add(dailyTimeBoxes(20), 1, 21, 1, 1)
    rootDay.add(dailyTimeBoxes(21), 1, 22, 1, 1)
    rootDay.add(dailyTimeBoxes(22), 1, 23, 1, 1)
    rootDay.add(dailyTimeBoxes(23), 1, 24, 1, 1)
    rootDay.add(dailyContentBoxes(0), 2, 1, 1, 1)
    rootDay.add(dailyContentBoxes(1), 2, 2, 1, 1)
    rootDay.add(dailyContentBoxes(2), 2, 3, 1, 1)
    rootDay.add(dailyContentBoxes(3), 2, 4, 1, 1)
    rootDay.add(dailyContentBoxes(4), 2, 5, 1, 1)
    rootDay.add(dailyContentBoxes(5), 2, 6, 1, 1)
    rootDay.add(dailyContentBoxes(6), 2, 7, 1, 1)
    rootDay.add(dailyContentBoxes(7), 2, 8, 1, 1)
    rootDay.add(dailyContentBoxes(8), 2, 9, 1, 1)
    rootDay.add(dailyContentBoxes(9), 2, 10, 1, 1)
    rootDay.add(dailyContentBoxes(10), 2, 11, 1, 1)
    rootDay.add(dailyContentBoxes(11), 2, 12, 1, 1)
    rootDay.add(dailyContentBoxes(12), 2, 13, 1, 1)
    rootDay.add(dailyContentBoxes(13), 2, 14, 1, 1)
    rootDay.add(dailyContentBoxes(14), 2, 15, 1, 1)
    rootDay.add(dailyContentBoxes(15), 2, 16, 1, 1)
    rootDay.add(dailyContentBoxes(16), 2, 17, 1, 1)
    rootDay.add(dailyContentBoxes(17), 2, 18, 1, 1)
    rootDay.add(dailyContentBoxes(18), 2, 19, 1, 1)
    rootDay.add(dailyContentBoxes(19), 2, 20, 1, 1)
    rootDay.add(dailyContentBoxes(20), 2, 21, 1, 1)
    rootDay.add(dailyContentBoxes(21), 2, 22, 1, 1)
    rootDay.add(dailyContentBoxes(22), 2, 23, 1, 1)
    rootDay.add(dailyContentBoxes(23), 2, 24, 1, 1)

    rootDay.columnConstraints = Array(goBackColumn, dailyTimeColumn, dailyContentColumn, dailyMenuColumn) // Add constraints in order
    rootDay.rowConstraints = Array(dailyHeaderRow, dailyContentRow, dailyContentRow,dailyContentRow,dailyContentRow,dailyContentRow,dailyContentRow,dailyContentRow,dailyContentRow,dailyContentRow,dailyContentRow,dailyContentRow,dailyContentRow, dailyContentRow,dailyContentRow,dailyContentRow,dailyContentRow,dailyContentRow,dailyContentRow,dailyContentRow,dailyContentRow,dailyContentRow,dailyContentRow,dailyContentRow,dailyContentRow)

    // creates the content for the "add event" view
    val eventBox = VBox()
    val eventHeader = Label("Add an event. Required fields are marked with a *")

    val eventNameHeader = Label("Name of your event*")
    val eventName = TextField()
    eventName.promptText = "Name of your event*"

    val eventStartHeader = Label("Starting date of your event in the format dd.mm.yyyy*")
    val eventStart = TextField()
    eventStart.promptText = "Starting date of your event*"
    val eventStartTimeHeader = Label("Starting time of your event in the format hh.mm*")
    val eventStartTime = TextField()
    eventStartTime.promptText = "Starting time of your event*"

    val eventEndHeader = Label("Ending date of your event in the format dd.mm.yyyy*")
    val eventEnd = TextField()
    eventEnd.promptText = "Ending date of your event "
    val eventEndTimeHeader = Label("Ending time of your event in the format hh.mm*")
    val eventEndTime = TextField()
    eventEndTime.promptText = "Ending time of your event*"

    val categoryHeader = Label("Choose the category of your event")
    var categoryNames = calendarData.currentCategories.map(_.getName) += "new category (write below)"
    val categoryChoiceBox = new ChoiceBox[String]:
      items = categoryNames
    var chosenCategory: Option[Category] = None

    val newEventCategory = TextField()
    newEventCategory.promptText = "Create a new category and add the new event to it by writing the name of the new category here"

    val eventNotesHeader = Label("Additional information")
    val eventNotes = TextField()
    eventNotes.promptText = "Additional information"

    val reminderHeader = Label("Do you want to be reminded of the event one hour prior to its start?")
    val reminderCheckBox = CheckBox("I want a reminder")
    var reminderBoolean = false
    reminderCheckBox.selectedProperty().addListener((_, _, newValue) => reminderBoolean = newValue)


    def updateAddEventWindow(): Unit =
      // clearing the text areas will be done in the final calendar but easier to test like this
      //eventName.text = ""
      //eventStart.text = ""
      //eventEnd.text = ""
      //eventStartTime.text = ""
      //eventEndTime.text = ""
      //eventNotes.text = ""
      newEventCategory.text = ""
      //reminderCheckBox.selected = false

    val saveAndAddButton = new Button("Save event"):
      onAction = _ =>

        newEventCategory.text() match
          case "" =>
            val category = categoryChoiceBox.getSelectionModel.getSelectedItem
            if (category != null) then
              val categoryColor = calendarData.categoriesMap(category)
              chosenCategory = Some(Category(category, categoryColor))
            else chosenCategory = None
          case newValue =>
            chosenCategory = Some(Category(newValue, LightBlue))
            calendarData.addCategory(chosenCategory)
            categoryNames += newValue
            categoryChoiceBox.items = categoryNames
            // nykyisellään ei lisää categories bufferiin,ei käytä addCategory-funktiota, pelkästään lisää items listaan, pitääkö korjata?

        val newEvent = Event(eventName.text(), eventStart.text(), eventEnd.text(), eventStartTime.text(), eventEndTime.text(), chosenCategory, Some(eventNotes.text()), reminderBoolean)
        calendarData.addEvent(newEvent)
        updateAddEventWindow()
        stage.scene = startScene
        updateEventsOnWeeklyView()
    val eventGoBackButton = new Button("Cancel"):
      onAction = _ => stage.scene = startScene

    rootEvent.add(eventBox, 0, 0, 1, 1)
    eventBox.children.addAll(eventHeader,
      eventNameHeader,
      eventName,
      eventStartHeader,
      eventStart,
      eventStartTimeHeader,
      eventStartTime,
      eventEndHeader,
      eventEnd,
      eventEndTimeHeader,
      eventEndTime,
      categoryHeader,
      categoryChoiceBox,
      newEventCategory,
      eventNotesHeader,
      eventNotes,
      reminderHeader,
      reminderCheckBox,
      saveAndAddButton,
      eventGoBackButton)

  end start

end CalendarGUI

