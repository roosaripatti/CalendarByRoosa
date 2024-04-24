
import scalafx.application.JFXApp3
import scalafx.collections.ObservableBuffer

import scala.collection.mutable.Buffer
import scalafx.geometry.{Insets, Pos}
import scalafx.scene.Scene
import scalafx.scene.control.Alert.AlertType
import scalafx.scene.image.{Image, ImageView}
import scalafx.scene.layout.{Background, BackgroundFill, Border, BorderPane, ColumnConstraints, GridPane, HBox, RowConstraints, VBox}
import scalafx.scene.control.{Alert, Button, CheckBox, ChoiceBox, ChoiceDialog, Label, Menu, MenuBar, MenuItem, TextField, TextInputDialog}
import javafx.scene.input.MouseEvent
import scalafx.scene.paint.Color.*
import scalafx.scene.paint.Color
import scalafx.scene.text.Font
import java.time.{LocalDate, LocalTime}
import java.time.format.{DateTimeFormatter, DateTimeParseException}
import scala.collection.mutable

object CalendarGUI extends JFXApp3:

  // stores some information in variables
  val openSansFont = Font("Open Sans", 10)
  var eventNames = CalendarData.eventsMap.keys
  var categoryNames = CalendarData.currentCategories.map(_.getName)

  def start() =

    // sets the view and its size
    stage = new JFXApp3.PrimaryStage:
      title = "Calendar by Roosa"
      width = 1400
      height = 850

    // sets the view as a grid
    val rootWeek = GridPane()
    val rootDay = GridPane()
    val rootEvent = new BorderPane()
    val rootDeleteEvent = new BorderPane()
    val rootList = new BorderPane()

    val startScene = Scene(parent = rootWeek)
    val dayScene = Scene(parent = rootDay)
    val eventScene = Scene(parent = rootEvent)
    val deleteScene = Scene(parent = rootDeleteEvent)
    val listScene = Scene(parent = rootList)

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
    val headerText = Label(s"${CalendarView.currentMonth} WEEK ${CalendarView.weekNumber}")
    // menu symbol
    val menuImage = ImageView(Image("file:src/resources/icons/icons8-menu-50.png"))
    menuImage.setFitHeight(35)
    menuImage.preserveRatio = true

    // the function that updates the events of week we are looking at to the GUI:
    // first it erases the events of the previously shown week, then iterates through the buffer containing all events
    // and finally filters the events of the current week and adds them to the corresponding weekday container
    // if the user wants to filter events, the name of the category is given to this function in a Some wrapper. If all of the events of the week will be shown the filter is None
    def updateEventsOnWeeklyView(filter: Option[String]): Unit =
    // clears the containers
      monday.children.clear()
      tuesday.children.clear()
      wednesday.children.clear()
      thursday.children.clear()
      friday.children.clear()
      saturday.children.clear()
      sunday.children.clear()
      reminder.children.clear()

      // checks whether there is a category with the name of the filter and then calls the updateEventsOfWeek function
      filter match
        case Some(categoryName) =>
          val filteredEvents: Buffer[Event] = Buffer()
          for event <- CalendarData.currentEvents do
            event.getCategory match
              case Some(category) => if category.getName == categoryName then filteredEvents += event
              case None => println("no category")
          updateEventsOfWeek(filteredEvents)
        case None => updateEventsOfWeek(CalendarData.currentEvents ++ CalendarData.publicHolidays)

    // goes trough all of the events in the parameter Buffer and adds them in the right form in the right container
    def updateEventsOfWeek(allEvents: Buffer[Event]): Unit =
      val weekdays = Array(monday, tuesday, wednesday, thursday, friday, saturday, sunday)
      val addedReminders = mutable.Set.empty[String]

      for (event <- allEvents) do
        val eventStartingDate = LocalDate.parse(event.getStart, DateTimeFormatter.ofPattern("dd.MM.yyyy"))
        val eventEndingDate = LocalDate.parse(event.getEnd, DateTimeFormatter.ofPattern("dd.MM.yyyy"))

        val startingDayOfWeek = eventStartingDate.getDayOfWeek
        val endingDayOfWeek = eventEndingDate.getDayOfWeek
        var categoryColor = LightGoldrenrodYellow

        event.getCategory match
          case Some(category) =>
            categoryColor = category.getColor
          case None =>
            categoryColor = LightGoldrenrodYellow
    // iterates through each day between starting and ending date of the week
        var currentDate = eventStartingDate
        while (!currentDate.isAfter(eventEndingDate)) do
          if (!currentDate.isBefore(CalendarView.startTime) && !currentDate.isAfter(CalendarView.endTime)) then
           val dayOfWeek = currentDate.getDayOfWeek
           val eventName = event.getName

           val label = new Label(s"${eventName}"):
             margin = Insets(2)
             background = new Background(Array(new BackgroundFill(categoryColor, null, null)))
           label.setFont(openSansFont)

           weekdays(dayOfWeek.getValue - 1).children.add(label)

           if (event.getReminder && !addedReminders.contains(eventName)) then
             val reminderLabel = new Label(s"Remember to do the task:\n  ${eventName}"):
                margin = Insets(2)
             reminderLabel.setFont(openSansFont)
             reminder.children.add(reminderLabel)
             addedReminders.add(eventName)

          currentDate = currentDate.plusDays(1) // moves to the next day

    // when starting the function, the events on weekly view are updated with no filter
    updateEventsOnWeeklyView(None)

    // returns an observablebuffer with all of the category names + "show all" option
    def categoryNamesPlusShow(categoryNames: ObservableBuffer[String]): ObservableBuffer[String] =
      var newCategoryNames = categoryNames.clone()
      newCategoryNames += "show all"
      newCategoryNames

    // This following part creates the variables and functions for the list that appears when the user searches for events
    val listBox = new VBox {
      alignment = Pos.Center
      background = Background.fill(Color.rgb(226,200,246))
      border = Border.stroke(Black)
      margin = Insets(60)
      spacing = 10
    }

    val listGoBackButton = new Button("Cancel"):
      onAction = _ => stage.scene = startScene

    val listHeader = Label("Here are the events containing your keyword:")
    rootList.center = listBox
    listBox.children.addAll(listHeader, listGoBackButton)

    def makeEventsList(keyword: String): Unit =
      listBox.children.clear()
      listBox.children.addAll(listHeader, listGoBackButton)
      val notesBuffer = Buffer[Event]()
      for event <- CalendarData.currentEvents do
        event.getNotes match
          case Some(note) => if note.contains(keyword) then notesBuffer += event
          case None => println("no notes") // for debugging
      if notesBuffer.isEmpty then
        listBox.children.add(Label("no events containing your keyword found")) // for debugging
      else
        val labels = notesBuffer.map(n => Label(s"${n.getName} ${n.getStart} to ${n.getEnd}"))
        labels.foreach(name => listBox.children.add(name))

    // the two dialogs that appear from the menu: the first one is for filtering events and the second is for searching event
    val dialog = new ChoiceDialog(defaultChoice = "show all", choices = categoryNamesPlusShow(categoryNames)) {
      initOwner(stage)
      title = "Filter events based on categories"
      contentText = "Choose your category:"
      }
    val dialog2 = new TextInputDialog() {
      initOwner(stage)
      title = "Search for event with keywords"
      contentText = "Write your keyword here"
      }

    // creates the menu items to change view, add event, filter events and search events
    val menuItemDay = new MenuItem("Day"):
      onAction = _ =>
        stage.scene = dayScene
    val menuItemAdd = new MenuItem("Add event"):
      onAction = _ =>
        stage.scene = eventScene
    val menuItemDelete = new MenuItem("Delete event"):
      onAction = _ =>
        eventNames = CalendarData.eventsMap.keys
        stage.scene = deleteScene
    val menuItemFilter = new MenuItem("Filter events"):
      onAction = _ =>
        val result = dialog.showAndWait()
        result match
          case Some(choice) =>
            if choice == "show all" then updateEventsOnWeeklyView(None)
            else updateEventsOnWeeklyView(Some(choice))
          case None         => println("No selection") // for debugging
    val menuItemSearch = new MenuItem("Search for events"):
      onAction = _ =>
        val result = dialog2.showAndWait()
        result match
          case Some(choice) =>
             makeEventsList(choice)
          case None         => println("No search") // for debugging
        stage.scene = listScene
    // creates the main menu / navigation
    val mainMenu = new Menu("", menuImage):
      items = Array(menuItemDay, menuItemAdd, menuItemDelete, menuItemFilter, menuItemSearch)
    val mainMenuBar = new MenuBar:
      menus = Array(mainMenu)


    // creating the containers for the daily view
    val goBackBox = new VBox()
    val dailyTimeHead = new VBox():
      border = Border.stroke(Black)
    val dailyContentHead = new VBox():
      border = Border.stroke(Black)
    val dailyMenuBox = new VBox()
    // creates first 25 rows for headers and then containers for each hour + allday events
    val dailyTimeBoxes = Array.fill(25)(new HBox {
      border = Border.stroke(Black)
      alignment = Pos.Center})
    val dailyContentBoxes = Array.fill(25)(new HBox {
      border = Border.stroke(Black)})

    // styling the header boxes in daily view
    val dailyHeaderText = Label(s"${CalendarView.chosenWeekday}  ${CalendarView.chosenDayString}")
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
      dailyHeaderText.text = s"${CalendarView.chosenWeekday} ${CalendarView.chosenDayString}"

    // when called, this function updates
    def updateEventsOnDailyView(chosenDay: LocalDate): Unit =
      // Clear all containers
      for hour <- 0 to 24 do
        val slotContainer = dailyContentBoxes(hour)
        slotContainer.setStyle("")
        slotContainer.getChildren.clear()

      // Filter events for the chosen day
      val allEvents = CalendarData.currentEvents ++ CalendarData.publicHolidays
      val filteredEvents = allEvents.filter(event =>
        val eventStartDate = LocalDate.parse(event.getStart, DateTimeFormatter.ofPattern("dd.MM.yyyy"))
        val eventEndDate = LocalDate.parse(event.getEnd, DateTimeFormatter.ofPattern("dd.MM.yyyy"))
        eventStartDate.isEqual(chosenDay) || eventEndDate.isEqual(chosenDay) ||
          (eventStartDate.isBefore(chosenDay) && eventEndDate.isAfter(chosenDay))
      )

      // iterate over filtered events
      filteredEvents.foreach(event =>
        val startingTime = event.startingTimeFormat
        val startingHour = startingTime.getHour
        val endingTime = event.endingTimeFormat
        val endingHour = endingTime.getHour

        // selects the color of the category, and if there is no category, sets the background color as LightGoldrenrodYellow
        def setCategoryColor(label: Label) =
          event.getCategory match
           case Some(category) =>
             val color = category.getColor
             label.setBackground(new Background(Array(new BackgroundFill(color, null, null))))
           case None =>
              label.setBackground(new Background(Array(new BackgroundFill(LightGoldrenrodYellow, null, null))))
        // if the event is allday or if the event has started before this day and lasts after this day (i.e. it lasts the whole day) the event label is added to allday container
        if (event.allDay || (startingTime.isBefore(chosenDay.atStartOfDay()) && endingTime.isAfter(chosenDay.atStartOfDay().plusDays(1)))) then
          val slotContainer = dailyContentBoxes(24)
          val label = new Label(event.getName):
            margin = Insets(4)
          slotContainer.getChildren.add(label)
          setCategoryColor(label)
        // if the event starts and ends today, each hourly container between start and end are filled with the event label
        else if (startingTime.toLocalDate.isEqual(chosenDay) && endingTime.toLocalDate.isEqual(chosenDay)) then
          for (hour <- startingHour to endingHour) do
            val slotContainer = dailyContentBoxes(hour)
            val label = new Label(event.getName):
              margin = Insets(4)
            setCategoryColor(label)
            slotContainer.getChildren.add(label)
        // if the starting time is today but ending date is not, every hourly container from starting time to 23 are filled
        else if (startingTime.toLocalDate.isEqual(chosenDay)) then
          for (hour <- startingHour to 23) do
            val slotContainer = dailyContentBoxes(hour)
            val label = new Label(event.getName):
              margin = Insets(4)
            setCategoryColor(label)
            slotContainer.getChildren.add(label)
        // if the ending time is today but starting date is not, every hourly container from 0 time to ending time are filled
        else if (endingTime.toLocalDate.isEqual(chosenDay)) then
          for (hour <- 0 to endingHour) do
            val slotContainer = dailyContentBoxes(hour)
            val label = new Label(event.getName):
              margin = Insets(4)
            setCategoryColor(label)
            slotContainer.getChildren.add(label)

      )

    // updates the dialy view for the wanted day
    def updateDailyView(): Unit =
      updateEventsOnDailyView(CalendarView.chosenDay)

    // labels of each day in the daily view
    val mondayText = new Label("monday  " + CalendarView.mondayNumber + "."):
      onMouseClicked = (event) =>
        CalendarView.chosenDay = CalendarView.startTime
        CalendarView.chosenDayString = CalendarView.chosenDay.format(DateTimeFormatter.ofPattern("dd.MM.yyyy"))
        updateDailyHeaderText()
        updateDailyView()
        stage.scene = dayScene
    val tuesdayText = new Label("tuesday  " + CalendarView.tuesdayNumber + "." ):
      onMouseClicked = (event) =>
        CalendarView.chosenDay = CalendarView.startTime.plusDays(1)
        CalendarView.chosenDayString = CalendarView.chosenDay.format(DateTimeFormatter.ofPattern("dd.MM.yyyy"))
        updateDailyHeaderText()
        updateDailyView()
        stage.scene = dayScene
    val wednesdayText = new Label("wednesday  " + CalendarView.wednesdayNumber + "."):
      onMouseClicked = (event) =>
        CalendarView.chosenDay = CalendarView.startTime.plusDays(2)
        CalendarView.chosenDayString = CalendarView.chosenDay.format(DateTimeFormatter.ofPattern("dd.MM.yyyy"))
        updateDailyHeaderText()
        updateDailyView()
        stage.scene = dayScene
    val thursdayText = new Label("thursday  " + CalendarView.thursdayNumber + "."):
      onMouseClicked = (event) =>
        CalendarView.chosenDay = CalendarView.startTime.plusDays(3)
        CalendarView.chosenDayString = CalendarView.chosenDay.format(DateTimeFormatter.ofPattern("dd.MM.yyyy"))
        updateDailyHeaderText()
        updateDailyView()
        stage.scene = dayScene
    val fridayText = new Label("friday  " + CalendarView.fridayNumber + "."):
      onMouseClicked = (event) =>
        CalendarView.chosenDay = CalendarView.startTime.plusDays(4)
        CalendarView.chosenDayString = CalendarView.chosenDay.format(DateTimeFormatter.ofPattern("dd.MM.yyyy"))
        updateDailyHeaderText()
        updateDailyView()
        stage.scene = dayScene
    val saturdayText = new Label("saturday  " + CalendarView.saturdayNumber + "."):
      onMouseClicked = (event) =>
        CalendarView.chosenDay = CalendarView.startTime.plusDays(5)
        CalendarView.chosenDayString = CalendarView.chosenDay.format(DateTimeFormatter.ofPattern("dd.MM.yyyy"))
        updateDailyHeaderText()
        updateDailyView()
        stage.scene = dayScene
    val sundayText = new Label("sunday  " + CalendarView.sundayNumber + "."):
      onMouseClicked = (event) =>
        CalendarView.chosenDay = CalendarView.startTime.plusDays(6)
        CalendarView.chosenDayString = CalendarView.chosenDay.format(DateTimeFormatter.ofPattern("dd.MM.yyyy"))
        updateDailyHeaderText()
        updateDailyView()
        stage.scene = dayScene
    val remindersText = Label("reminders of")
    val remindersText2 = Label("this week")

    val weekButton =  Label("week  " + CalendarView.weekNumber + " of " + CalendarView.currentYear)


   // function that updates the weekly labels in the weekly view
    def updateLabels() =
      mondayText.text = s"monday  ${CalendarView.mondayNumber}."
      tuesdayText.text = s"tuesday  ${CalendarView.tuesdayNumber}."
      wednesdayText.text = s"wednesday  ${CalendarView.wednesdayNumber}."
      thursdayText.text = s"thursday  ${CalendarView.thursdayNumber}."
      fridayText.text = s"friday  ${CalendarView.fridayNumber}."
      saturdayText.text = s"saturday  ${CalendarView.saturdayNumber}."
      sundayText.text = s"sunday  ${CalendarView.sundayNumber}."
      weekButton.text = s"week  ${CalendarView.weekNumber} of ${CalendarView.currentYear}"
      headerText.text = s"${CalendarView.currentMonth} WEEK ${CalendarView.weekNumber}"

    // buttons to change the week currently viewing
    val rightButton = new Button(">"):
      onAction = (event) =>
        CalendarView.oneWeekForward()
        updateLabels()
        updateEventsOnWeeklyView(None)
    val leftButton = new Button("<"):
      onAction = (event) =>
        CalendarView.oneWeekBackwards()
        updateLabels()
        updateEventsOnWeeklyView(None)

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

    // adds and styles the labels for the daily time boxes 
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
    dailyTimeBoxes(24).children.add(new Label("all day events") { font = Font.loadFont("file:src/resources/Evafiya-Font/Evafiya.ttf", 14) })

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
    rootDay.add(dailyTimeBoxes(24), 1, 25, 1, 1)
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
    rootDay.add(dailyContentBoxes(24), 2, 25, 1, 1)

    // adds constraints in order
    rootDay.columnConstraints = Array(goBackColumn, dailyTimeColumn, dailyContentColumn, dailyMenuColumn) 
    rootDay.rowConstraints = Array(dailyHeaderRow, dailyContentRow, dailyContentRow,dailyContentRow,dailyContentRow,dailyContentRow,dailyContentRow,dailyContentRow,dailyContentRow,dailyContentRow,dailyContentRow,dailyContentRow,dailyContentRow, dailyContentRow,dailyContentRow,dailyContentRow,dailyContentRow,dailyContentRow,dailyContentRow,dailyContentRow,dailyContentRow,dailyContentRow,dailyContentRow,dailyContentRow,dailyContentRow,dailyContentRow)

    // creates the content for the "add event" view
    val eventBox  = new VBox {
      alignment = Pos.Center
      background = Background.fill(Color.rgb(226,200,246))
      border = Border.stroke(Black)
      margin = Insets(60)
      spacing = 10
    }
    val eventHeader = Label("Add an event. Required fields are marked with a *")
    val eventNameHeader = Label("Name of your event*")
    val eventName = TextField()
    eventName.promptText = "Name of your event*"
    val eventStartHeader = Label("Starting date of your event in the format dd.mm.yyyy*")
    val eventStart = TextField()
    eventStart.promptText = "Starting date of your event*"
    val eventStartTimeHeader = Label("Starting time of your event in the format hh.mm or 'allday'*")
    val eventStartTime = TextField()
    eventStartTime.promptText = "Starting time of your event*"
    val eventEndHeader = Label("Ending date of your event in the format dd.mm.yyyy*")
    val eventEnd = TextField()
    eventEnd.promptText = "Ending date of your event "
    val eventEndTimeHeader = Label("Ending time of your event in the format hh.mm or 'allday'*")
    val eventEndTime = TextField()
    eventEndTime.promptText = "Ending time of your event*"
    val categoryHeader = Label("Choose the category of your event")

    // returns the names of the categories plus the options "no category" and "new category"
    def categoryNamesPlusNew(categoryNames: ObservableBuffer[String]): ObservableBuffer[String] =
      var newCategoryNames = categoryNames.clone()
      newCategoryNames += "no category"
      newCategoryNames += "new category (write below)"
      newCategoryNames

    val categoryChoiceBox = new ChoiceBox[String]:
      items = categoryNamesPlusNew(categoryNames)
    var chosenCategory: Option[Category] = None

    val newEventCategory = TextField()
    newEventCategory.promptText = "Create a new category and add the new event to it by writing the name of the new category here"

    val eventNotesHeader = Label("Additional information")
    val eventNotes = TextField()
    eventNotes.promptText = "Additional information"

    val reminderHeader = Label("Do you want to be reminded of the event?")
    val reminderCheckBox = CheckBox("I want a reminder")
    var reminderBoolean = false
    reminderCheckBox.selectedProperty().addListener((_, _, newValue) => reminderBoolean = newValue)


    // makes five different error messages for different kinds of invalid content
    val errorMessage1 = new Alert(AlertType.Error) {
      initOwner(stage)
      title = "Error: can't add the event"
      contentText = "All of the required fields have to be filled"
      }

    val errorMessage2 = new Alert(AlertType.Error) {
      initOwner(stage)
      title = "Error: can't add the event"
      contentText = "Dates or times are in the wrong format. Check that the dates are in format dd.mm.yyyy and times in format hh.mm or 'allday'"
      }

    val errorMessage3 = new Alert(AlertType.Error) {
      initOwner(stage)
      title = "Error: can't add the event"
      contentText = "Ending time of the event can't be before the starting time of the event."
      }

    val errorMessage4 = new Alert(AlertType.Error) {
      initOwner(stage)
      title = "Error: can't add the event"
      contentText = "An event can only be allday if both starting time and ending time are marked as 'allday'."
      }

     val errorMessage5 = new Alert(AlertType.Error) {
      initOwner(stage)
      title = "Error: can't delete the event"
      contentText = "An event of this name, starting date and ending date does not exist."
      }

    // clears the name, category text and checkbox of the add event view.
    def updateAddEventWindow(): Unit =
      eventName.text = ""
      newEventCategory.text = ""
      reminderCheckBox.selected = false

    // when the save button is pressed, this part checks that the content is valid and if not, displays the correct error messafe
    val saveAndAddButton = new Button("Save event"):
      onAction = _ =>
        val requiredFieldsEmpty = Seq(eventName, eventStart, eventStartTime, eventEnd, eventEndTime).exists(_.text().trim.isEmpty)
        if requiredFieldsEmpty then
          errorMessage1.showAndWait()
        else if eventStartTime.text().equalsIgnoreCase("allday") != eventEndTime.text().equalsIgnoreCase("allday") then
          errorMessage4.showAndWait()
        else
          try
            val startDate = LocalDate.parse(eventStart.text(), DateTimeFormatter.ofPattern("dd.MM.yyyy"))
            val endDate = LocalDate.parse(eventEnd.text(), DateTimeFormatter.ofPattern("dd.MM.yyyy"))

            if !eventStartTime.text().equalsIgnoreCase("allday") && !eventEndTime.text().equalsIgnoreCase("allday") then
              val startTime = LocalTime.parse(eventStartTime.text(), DateTimeFormatter.ofPattern("HH.mm"))
              val endTime = LocalTime.parse(eventEndTime.text(), DateTimeFormatter.ofPattern("HH.mm"))

              if endDate.isBefore(startDate) || (endDate.isEqual(startDate) && endTime.isBefore(startTime)) then
                errorMessage3.showAndWait()
              else if endDate.isBefore(startDate)  then
                errorMessage3.showAndWait()
          catch
            case _: DateTimeParseException =>
              errorMessage2.showAndWait()

          // checks if there has been a new category added and in this case adds this category to the CalendarData
          newEventCategory.text() match
            case "" =>
               val category = categoryChoiceBox.getSelectionModel.getSelectedItem
               if (category != null) && (category != "no category") then
                 val categoryColor = CalendarData.categoriesMap(category)
                 chosenCategory = Some(Category(category, categoryColor))
               else chosenCategory = None
            case newValue =>
               chosenCategory = Some(Category(newValue, LightGoldrenrodYellow))
               CalendarData.addCategory(chosenCategory)
               categoryNames += newValue
               categoryChoiceBox.items = categoryNamesPlusNew(categoryNames)
          // checks if there are notes in the event
          var notesOption: Option[String] = None
           eventNotes.text() match
              case "" => notesOption = None
              case notes => notesOption = Some(notes)
          // based on the previous data, creates a new event object and adds that to the file, calendardata and updates the views 
           val newEvent = Event(eventName.text(), eventStart.text(), eventEnd.text(), eventStartTime.text(), eventEndTime.text(), chosenCategory, notesOption, reminderBoolean)
           FileReader.addEventToFile(newEvent, "src/resources/userData.ics")
           CalendarData.addEvent(newEvent)
           updateAddEventWindow()
           stage.scene = startScene
           updateEventsOnWeeklyView(None)
           
    val eventGoBackButton = new Button("Cancel"):
      onAction = _ => stage.scene = startScene

    // adds the content to the "add event" view 
    rootEvent.center = eventBox
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

    // creates the content to the "delete event" view
    val deleteEventBox = new VBox {
      alignment = Pos.Center
      background = Background.fill(Color.rgb(226,200,246))
      border = Border.stroke(Black)
      margin = Insets(60)
      spacing = 10
    }
    val deleteEventHeader = Label("Delete an event. Required fields are marked with a *")
    val deleteEventNameHeader = Label("Choose the event you want to delete*")
    val delEventName = TextField()
    delEventName.promptText = "Name of your event*"
    val deleventStartHeader = Label("Starting date of your event in the format dd.mm.yyyy*")
    val deleventStart = TextField()
    deleventStart.promptText = "Starting date of your event*"
    val deleventEndHeader = Label("Ending date of your event in the format dd.mm.yyyy*")
    val deleventEnd = TextField()
    deleventEnd.promptText = "Ending date of your event "
    // when the user clicks "save" the event is deleted both from the file and from the calendar data, or an error message is displayed
    val saveAndDeleteButton = new Button("Delete event"):
      onAction = _ =>
        try
          val deletedEvent = CalendarData.eventsMap(s"${delEventName.text()}_${deleventStart.text()}_${deleventEnd.text()}")
          CalendarData.removeEvent(deletedEvent)
          FileReader.deleteEventFromFile(deletedEvent, "src/resources/userData.ics")
          updateEventsOnWeeklyView(None)
          stage.scene = startScene
        catch
          case _: NoSuchElementException => errorMessage5.showAndWait()

    val deleteGoBackButton = new Button("Cancel"):
      onAction = _ => stage.scene = startScene

    rootDeleteEvent.center = deleteEventBox
    deleteEventBox.children.addAll(deleteEventHeader, deleteEventNameHeader, delEventName, deleventStartHeader, deleventStart, deleventEndHeader, deleventEnd,saveAndDeleteButton, deleteGoBackButton)


    // this code adds the feature where you can paint with the mouse in the daily view to add an event
    // however i didn't have time to complete it so now this only works partly: you can drag the mouse and it creates a new event with the correct starting day&time, 
    // but the ending time is the same
    var dragStart: Int = 0
    var dragEnd: Int = 0
    for ((box, index) <- dailyContentBoxes.zipWithIndex) do
      box.setOnMousePressed(event => 
        dragStart = index )
      box.setOnMouseReleased(event => 
        dragEnd = index
        stage.scene = eventScene
        def formattedHour =
          if dragStart.toString.length == 1 then s"0$dragEnd.00"
          else s"$dragEnd.00"

        eventStart.setText(CalendarView.chosenDayString)
        eventStartTime.setText(formattedHour)
        eventEnd.setText(CalendarView.chosenDayString)
        eventEndTime.setText(formattedHour)
      )

  end start

end CalendarGUI

