class Event(name: String, date: String, startingTime: String, endingTime: String, category: Option[Category], notes: String) {
// comment: times will be changed to DateTime format but currently String as a placeholder
def getCategory = this.category
def getName = this.name
def getDate = this.date
def getStart = this.startingTime
def getEnd = this.endingTime
}

