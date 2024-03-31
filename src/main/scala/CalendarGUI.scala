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
      title = "My Calendar"
      width = 1000
      height = 850

    // sets the view as a grid
    val root = GridPane()
    val scene = Scene(parent = root)
    stage.scene = scene


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

    // creates the wanted other elements
    val headerText = Label("FEBRUARY WEEK 8")

    val menuImage = ImageView(Image("file:src/resources/icons/icons8-menu-50.png"))
    menuImage.setFitHeight(35)
    menuImage.preserveRatio = true

    val weekButton = new Button("week 2"):
      onAction = (event) => println("Click!")
    val rightButton = new Button(">"):
      onAction = (event) => println("Click!")
    val leftButton = new Button("<"):
      onAction = (event) => println("Click!")
    //val menuButton = new Button("", menuImage):
      //onAction = (event) => println("Menu Click!")
    val mainMenu = new Menu("", menuImage):
      items = Array(MenuItem("New"), MenuItem("Open"), MenuItem("Save"))

    // The MenuBar itself. In this case, it only contains one menu
    val top = new MenuBar:
      menus = Array(mainMenu)


    val mondayText = Label("monday")
    val tuesdayText = Label("tuesday")
    val wednesdayText = Label("wednesday")
    val thursdayText = Label("thursday")
    val fridayText = Label("friday")
    val saturdayText = Label("saturday")
    val sundayText = Label("sunday")

    mondayHead.setAlignment(Pos.Center)
    tuesdayHead.setAlignment(Pos.Center)
    wednesdayHead.setAlignment(Pos.Center)
    thursdayHead.setAlignment(Pos.Center)
    fridayHead.setAlignment(Pos.Center)
    saturdayHead.setAlignment(Pos.Center)
    sundayHead.setAlignment(Pos.Center)

    headerBox.children.addAll(headerText, leftButton, weekButton, rightButton)
    menuBox.children.add(top)
    mondayHead.children.add(mondayText)
    tuesdayHead.children.add(tuesdayText)
    wednesdayHead.children.add(wednesdayText)
    thursdayHead.children.add(thursdayText)
    fridayHead.children.add(fridayText)
    saturdayHead.children.add(saturdayText)
    sundayHead.children.add(sundayText)


    // decides the ratios of the boxes in the view
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


    // adds the boxes to the right places in the root
    root.add(timeBox, 0, 0, 1, 3)
    root.add(headerBox, 1, 0, 8, 1)
    root.add(menuBox, 8, 0, 8, 3)

    root.add(monday, 1, 2, 1, 1)
    root.add(tuesday, 2, 2, 1, 1)
    root.add(wednesday, 3, 2, 1, 1)
    root.add(thursday, 4, 2, 1, 1)
    root.add(friday, 5, 2, 1, 1)
    root.add(saturday, 6, 2, 1, 1)
    root.add(sunday, 7, 2, 1, 1)

    root.add(mondayHead, 1, 1)
    root.add(tuesdayHead, 2, 1)
    root.add(wednesdayHead, 3, 1)
    root.add(thursdayHead, 4, 1)
    root.add(fridayHead, 5, 1)
    root.add(saturdayHead, 6, 1)
    root.add(sundayHead, 7, 1)

    // adds the rows and columns in the right order
    root.columnConstraints = Array(timeColumn, weekDayColumn, weekDayColumn, weekDayColumn, weekDayColumn, weekDayColumn, weekDayColumn, weekDayColumn, menuColumn) // Add constraints in order
    root.rowConstraints = Array(headerRow, weekLabelRow, weekRow)


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

  end start

end CalendarGUI

