import scalafx.application.JFXApp3
import scalafx.geometry.{Insets, Pos}
import scalafx.scene.Scene
import scalafx.scene.image.{Image, ImageView}
import scalafx.scene.layout.{Background, ColumnConstraints, GridPane, HBox, RowConstraints, VBox, Border}
import scalafx.scene.control.{Label, Button, Menu, MenuItem, MenuBar}
import scalafx.scene.paint.Color.*
import scalafx.scene.paint.Color
import scalafx.scene.text.{Font, FontWeight, FontPosture}

object CalendarGUI extends JFXApp3:

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

    val startScene = Scene(parent = rootWeek)
    val dayScene = Scene(parent = rootDay)

    stage.scene = startScene

    // THE CONTENTS OF THE WEEKLY VIEW
    // creates the rows and columns as boxes
    val headerBox = new HBox:
      padding = Insets(40, 0, 40, 0)
      spacing = 40
    val timeBox = VBox()
    val menuBox =  VBox()
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
    val headerText = Label("FEBRUARY WEEK 8")
    // menu symbol
    val menuImage = ImageView(Image("file:src/resources/icons/icons8-menu-50.png"))
    menuImage.setFitHeight(35)
    menuImage.preserveRatio = true
    // items to change the week
    val weekButton = new Button("week 2"):
      onAction = (event) => println("Click!")
    val rightButton = new Button(">"):
      onAction = (event) => println("Click!")
    val leftButton = new Button("<"):
      onAction = (event) => println("Click!")


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

    // weekday texts
    val mondayText = Label("monday")
    val tuesdayText = Label("tuesday")
    val wednesdayText = Label("wednesday")
    val thursdayText = Label("thursday")
    val fridayText = Label("friday")
    val saturdayText = Label("saturday")
    val sundayText = Label("sunday")

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

   // CONTENTS OF THE DAILY VIEW

    // creates the rows and columns as boxes
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


// setting the "go back" button
    val goBackImage = ImageView(Image("file:src/resources/icons/back-button.png"))
    goBackImage.setFitHeight(35)
    goBackImage.preserveRatio = true
    val goBackButton = new Button("", goBackImage):
      onAction = _ => stage.scene = startScene

    goBackBox.children.add(goBackButton)

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
      percentHeight = 90



    rootDay.add(goBackBox, 0, 0, 1, 1)
    rootDay.add(dailyTimeHead, 1, 0, 1, 1)
    rootDay.add(dailyContentHead, 2, 0, 1, 1)
    rootDay.add(dailyMenuBox, 3, 0, 1, 2)
    rootDay.add(dailyTimeBox, 1, 1, 1, 1)
    rootDay.add(dailyContent, 2, 1, 1, 1)

    //dailyMenuBox.children.add(mainMenuBar)

    rootDay.columnConstraints = Array(goBackColumn, dailyTimeColumn, dailyContentColumn, dailyMenuColumn) // Add constraints in order
    rootDay.rowConstraints = Array(dailyHeaderRow, dailyContentRow)
/**
    * rootWeek.add(timeBox, 0, 0, 1, 3)
   * rootWeek.add(headerBox, 1, 0, 8, 1)
   * rootWeek.add(menuBox, 8, 0, 8, 3)
   * rootWeek.add(monday, 1, 2, 1, 1)
   * rootWeek.add(tuesday, 2, 2, 1, 1)
   * rootWeek.add(wednesday, 3, 2, 1, 1)
   * rootWeek.add(thursday, 4, 2, 1, 1)
   * rootWeek.add(friday, 5, 2, 1, 1)
   * rootWeek.add(saturday, 6, 2, 1, 1)
   * rootWeek.add(sunday, 7, 2, 1, 1)
   *
   * rootWeek.add(mondayHead, 1, 1)
   * rootWeek.add(tuesdayHead, 2, 1)
   * rootWeek.add(wednesdayHead, 3, 1)
   * rootWeek.add(thursdayHead, 4, 1)
   * rootWeek.add(fridayHead, 5, 1)
   * rootWeek.add(saturdayHead, 6, 1)
   * rootWeek.add(sundayHead, 7, 1)
   * // creates the label

   * val mondayText = Label("monday")
   * val tuesdayText = Label("tuesday")
   * val wednesdayText = Label("wednesday")
   * val thursdayText = Label("thursday")
   * val fridayText = Label("friday")
   * val saturdayText = Label("saturday")
   * val sundayText = Label("sunday")
   *
   * // aligning weekday headers
   * mondayHead.setAlignment(Pos.Center)
   * tuesdayHead.setAlignment(Pos.Center)
   * wednesdayHead.setAlignment(Pos.Center)
   * thursdayHead.setAlignment(Pos.Center)
   * fridayHead.setAlignment(Pos.Center)
   * saturdayHead.setAlignment(Pos.Center)
   * sundayHead.setAlignment(Pos.Center)
   *
   * // inheritances
   * headerBox.children.addAll(headerText, leftButton, weekButton, rightButton)
   * menuBox.children.add(mainMenuBar)
   * mondayHead.children.add(mondayText)
   * tuesdayHead.children.add(tuesdayText)
   * wednesdayHead.children.add(wednesdayText)
   * thursdayHead.children.add(thursdayText)
   * fridayHead.children.add(fridayText)
   * saturdayHead.children.add(saturdayText)
   * sundayHead.children.add(sundayText)
   *
   * // ratios of the boxes in the view
   * val timeColumn = new ColumnConstraints:
   * percentWidth = 8
   * val weekDayColumn = new ColumnConstraints:
   * percentWidth = 11
   * val menuColumn = new ColumnConstraints:
   * percentWidth = 15
   * val headerRow = new RowConstraints:
   * percentHeight = 27
   * val weekRow = new RowConstraints:
   * percentHeight = 65
   * val weekLabelRow = new RowConstraints:
   * percentHeight = 8
   *
   * // adds the boxes to the right places in the weekly root
   *
   * // adds the rows and columns in the right order in the weekly root
   * rootWeek.columnConstraints = Array(timeColumn, weekDayColumn, weekDayColumn, weekDayColumn, weekDayColumn, weekDayColumn, weekDayColumn, weekDayColumn, menuColumn) // Add constraints in order
   * rootWeek.rowConstraints = Array(headerRow, weekLabelRow, weekRow)
   *
   * // styles the texts
   * headerText.font = Font.loadFont("file:src/resources/Evafiya-Font/Evafiya.ttf", 55)
   * headerText.textFill = Black
   *
   * mondayText.font = Font.font("Open sans", 10)
   * tuesdayText.font = Font.font("Open sans", 10)
   * wednesdayText.font = Font.font("Open sans", 10)
   * thursdayText.font = Font.font("Open sans", 10)
   * fridayText.font = Font.font("Open sans", 10)
   * saturdayText.font = Font.font("Open sans", 10)
   * sundayText.font = Font.font("Open sans", 10)
   *
   *
   *
   */

  end start

end CalendarGUI

