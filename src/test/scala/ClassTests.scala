val testiviikko = CalendarView()
//val testipäivä = DailyView()

def testataanviikko() =
  println("viikon eka päivä on " + testiviikko.mondayNumber)
  println("viikon vika päivä on " + testiviikko.sundayNumber)
  println("eletään viikkoa " + testiviikko.weekNumber)
  println("tänään on " + testiviikko.chosenWeekday)
  println("päivä on " + testiviikko.currentDate)

