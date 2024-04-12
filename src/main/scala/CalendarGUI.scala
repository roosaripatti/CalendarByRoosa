import scalafx.application.JFXApp3
import scalafx.geometry.{Insets, Pos}
import scalafx.scene.Scene
import scalafx.scene.image.{Image, ImageView}
import scalafx.scene.layout.{Background, Border, ColumnConstraints, GridPane, HBox, RowConstraints, VBox}
import scalafx.scene.control.{Button, Label, Menu, MenuBar, MenuItem, TextField}
import scalafx.scene.paint.Color.*
import scalafx.scene.paint.Color
import scalafx.scene.text.{Font, FontPosture, FontWeight}
import scalafx.scene.input.MouseEvent

import java.time.format.DateTimeFormatter

object CalendarGUI extends JFXApp3:

  val calendarData = CalendarData()
  val weeklyView = CalendarView()

  def start() =

    // sets the view and its size
    stage = new JFXApp3.PrimaryStage:
      title = "Calendar by Roosa"
      width = 1000
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

    // THE CONTENTS OF THE WEEKLY VIEW
    // creates the rows and columns as boxes
    val headerBox = new HBox:
      padding = Insets(40, 0, 40, 0)
      spacing = 40
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
    // creates the label
    val headerText = Label(s"${weeklyView.currentMonth} WEEK ${weeklyView.weekNumber}")
    // menu symbol
    val menuImage = ImageView(Image("file:src/resources/icons/icons8-menu-50.png"))
    menuImage.setFitHeight(35)
    menuImage.preserveRatio = true

    // creating the menu items
    val menuItemDay = new MenuItem("Day"):
      onAction = _ =>
        stage.scene = dayScene
    val menuItemWeek = new MenuItem("Week"):
      onAction = _ =>
        stage.scene = startScene
    val menuItemMonth = new MenuItem("Month"):
      onAction = _ =>
        stage.scene = new Scene(rootMonth)
    // creating the main menu
    val mainMenu = new Menu("", menuImage):
      items = Array(menuItemDay, menuItemWeek, menuItemMonth)
    val mainMenuBar = new MenuBar:
      menus = Array(mainMenu)

// creating the items for the daily view
    val goBackBox = new VBox()
    val dailyTimeHead = new VBox():
      border = Border.stroke(Black)
    val dailyContentHead = new VBox():
      border = Border.stroke(Black)
    val dailyMenuBox = new VBox()
    val dailyTimeBox = new HBox:
      border = Border.stroke(Black)
    val dailyContent = new HBox:
     border = Border.stroke(Black)

    // styling the header box in daily view
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

    def updateDailyHeaderText() =
      dailyHeaderText.text = s"${weeklyView.chosenWeekday} ${weeklyView.chosenDayString}"
    // weekday texts
    val mondayText = new Label("monday  " + weeklyView.mondayNumber + "."):
      onMouseClicked = (event) =>
        weeklyView.chosenDay = weeklyView.startTime
        weeklyView.chosenDayString = weeklyView.chosenDay.format(DateTimeFormatter.ofPattern("dd.MM.yyyy"))
        updateDailyHeaderText()
        stage.scene = dayScene
    val tuesdayText = new Label("tuesday  " + weeklyView.tuesdayNumber + "." ):
      onMouseClicked = (event) =>
        weeklyView.chosenDay = weeklyView.startTime.plusDays(1)
        weeklyView.chosenDayString = weeklyView.chosenDay.format(DateTimeFormatter.ofPattern("dd.MM.yyyy"))
        updateDailyHeaderText()
        stage.scene = dayScene
    val wednesdayText = new Label("wednesday  " + weeklyView.wednesdayNumber + "."):
      onMouseClicked = (event) =>
        weeklyView.chosenDay = weeklyView.startTime.plusDays(2)
        weeklyView.chosenDayString = weeklyView.chosenDay.format(DateTimeFormatter.ofPattern("dd.MM.yyyy"))
        updateDailyHeaderText()
        stage.scene = dayScene
    val thursdayText = new Label("thursday  " + weeklyView.thursdayNumber + "."):
      onMouseClicked = (event) =>
        weeklyView.chosenDay = weeklyView.startTime.plusDays(3)
        weeklyView.chosenDayString = weeklyView.chosenDay.format(DateTimeFormatter.ofPattern("dd.MM.yyyy"))
        updateDailyHeaderText()
        stage.scene = dayScene
    val fridayText = new Label("friday  " + weeklyView.fridayNumber + "."):
      onMouseClicked = (event) =>
        weeklyView.chosenDay = weeklyView.startTime.plusDays(4)
        weeklyView.chosenDayString = weeklyView.chosenDay.format(DateTimeFormatter.ofPattern("dd.MM.yyyy"))
        updateDailyHeaderText()
        stage.scene = dayScene
    val saturdayText = new Label("saturday  " + weeklyView.saturdayNumber + "."):
      onMouseClicked = (event) =>
        weeklyView.chosenDay = weeklyView.startTime.plusDays(5)
        weeklyView.chosenDayString = weeklyView.chosenDay.format(DateTimeFormatter.ofPattern("dd.MM.yyyy"))
        updateDailyHeaderText()
        stage.scene = dayScene
    val sundayText = new Label("sunday  " + weeklyView.sundayNumber + "."):
      onMouseClicked = (event) =>
        weeklyView.chosenDay = weeklyView.startTime.plusDays(6)
        weeklyView.chosenDayString = weeklyView.chosenDay.format(DateTimeFormatter.ofPattern("dd.MM.yyyy"))
        updateDailyHeaderText()
        stage.scene = dayScene

    val weekButton =  Label("week  " + weeklyView.weekNumber + " of " + weeklyView.currentYear)
    // functions that updates the weekly labels

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

    // items to change the week

    val rightButton = new Button(">"):
      onAction = (event) =>
        weeklyView.oneWeekForward()
        updateLabels()
    val leftButton = new Button("<"):
      onAction = (event) =>
        weeklyView.oneWeekBackwards()
        updateLabels()


    // aligning weekday headers
    mondayHead.setAlignment(Pos.Center)
    tuesdayHead.setAlignment(Pos.Center)
    wednesdayHead.setAlignment(Pos.Center)
    thursdayHead.setAlignment(Pos.Center)
    fridayHead.setAlignment(Pos.Center)
    saturdayHead.setAlignment(Pos.Center)
    sundayHead.setAlignment(Pos.Center)

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

    // ratios of the boxes in the view
    val timeColumn = new ColumnConstraints:
      percentWidth = 8
    val weekDayColumn = new ColumnConstraints:
      percentWidth = 11
    val menuColumn = new ColumnConstraints:
      percentWidth = 15
    val headerRow = new RowConstraints:
      percentHeight = 27
    val weekRow = new RowConstraints:
      percentHeight = 65
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

    rootWeek.add(mondayHead, 1, 1)
    rootWeek.add(tuesdayHead, 2, 1)
    rootWeek.add(wednesdayHead, 3, 1)
    rootWeek.add(thursdayHead, 4, 1)
    rootWeek.add(fridayHead, 5, 1)
    rootWeek.add(saturdayHead, 6, 1)
    rootWeek.add(sundayHead, 7, 1)

    // adds the rows and columns in the right order in the weekly root
    rootWeek.columnConstraints = Array(timeColumn, weekDayColumn, weekDayColumn, weekDayColumn, weekDayColumn, weekDayColumn, weekDayColumn, weekDayColumn, menuColumn) // Add constraints in order
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


// setting the "go back" button
    val goBackImage = ImageView(Image("file:src/resources/icons/back-button.png"))
    goBackImage.setFitHeight(35)
    goBackImage.preserveRatio = true
    val goBackButton = new Button("", goBackImage):
      onAction = _ => stage.scene = startScene

    // add event image
    val addEventImage = ImageView(Image("file:src/resources/icons/add.png"))
    addEventImage.setFitHeight(35)
    addEventImage.preserveRatio = true
    val addEventButton = new Button("", addEventImage):
      onAction = _ => stage.scene = eventScene

    goBackBox.children.add(goBackButton)
    menuBox.children.add(addEventButton)

    val goBackColumn = new ColumnConstraints:
      percentWidth = 25
    val dailyTimeColumn = new ColumnConstraints:
      percentWidth = 10
    val dailyContentColumn = new ColumnConstraints:
       percentWidth = 40 // tää jaettuna 24 on yksittäisen tunnin
    val dailyMenuColumn = new ColumnConstraints:
      percentWidth = 25
    val dailyHeaderRow = new RowConstraints:
      percentHeight = 10
    val dailyContentRow = new RowConstraints:
      percentHeight = 90

    leftButton.style = "-fx-background-color: transparent"
    rightButton.style = "-fx-background-color: transparent"
    goBackButton.style = "-fx-background-color: transparent"
    mainMenuBar.style = "-fx-background-color: transparent"
    addEventButton.style = "-fx-background-color: transparent"

    rootDay.add(goBackBox, 0, 0, 1, 1)
    rootDay.add(dailyTimeHead, 1, 0, 1, 1)
    rootDay.add(dailyContentHead, 2, 0, 1, 1)
    rootDay.add(dailyMenuBox, 3, 0, 1, 2)
    rootDay.add(dailyTimeBox, 1, 1, 1, 1)
    rootDay.add(dailyContent, 2, 1, 1, 1)

    rootDay.columnConstraints = Array(goBackColumn, dailyTimeColumn, dailyContentColumn, dailyMenuColumn) // Add constraints in order
    rootDay.rowConstraints = Array(dailyHeaderRow, dailyContentRow)

    // creating the items for the add event scene

    val eventBox = VBox()
    val eventHeader = Label("Add an event")

    val eventNameHeader = Label("Name of your event*")
    val eventName = TextField()
    eventName.promptText = "Name of your event*"
    val eventStartHeader = Label("Starting time of your event*")
    val eventStart = TextField()
    eventStart.promptText = "Starting time of your event*"
    val eventEndHeader = Label("Ending time of your event*")
    val eventEnd = TextField()
    eventEnd.promptText = "Ending time of your event*"
    val eventCategoryHeader = Label("Category of your event")
    val eventCategory = TextField()
    eventCategory.promptText = "Category of the event"
    val eventNotesHeader = Label("Additional information")
    val eventNotes = TextField()
    eventNotes.promptText = "Additional information"
    val saveAndAddButton = new Button("Save event"):
      onAction = _ =>
        calendarData.addEvent(Event(eventName.text(), eventStart.text(), eventEnd.text(), Some(eventCategory.text()), Some(eventNotes.text())))
        stage.scene = startScene
    val eventGoBackButton = new Button("Cancel"):
      onAction = _ => stage.scene = startScene

    rootEvent.add(eventBox,0,0,1,1)
    eventBox.children.add(eventHeader)
    eventBox.children.add(eventNameHeader)
    eventBox.children.add(eventName)
    eventBox.children.add(eventStartHeader)
    eventBox.children.add(eventStart)
    eventBox.children.add(eventEndHeader)
    eventBox.children.add(eventEnd)
    eventBox.children.add(eventCategoryHeader)
    eventBox.children.add(eventCategory)
    eventBox.children.add(eventNotesHeader)
    eventBox.children.add(eventNotes)
    eventBox.children.add(saveAndAddButton)
    eventBox.children.add(eventGoBackButton)


  end start

end CalendarGUI

