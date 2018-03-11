  import scala.util.Random

  def id(ceiling: Int): Int = {
    Stream.continually(Random.nextInt(ceiling)).dropWhile(_ < 1).head
  }

  def formattedId(id: Int, length: Int = 10): String = {
    s"%${length}.0f".format(id.asInstanceOf[Float]).replace(" ", "-")
  }

  def stringList(count: Int, stringLength: Int) = {
    def randomString(length: Int): String = {
      Random.alphanumeric.filter(!_.isDigit).take(length).mkString
    }

    (1 to count).toList.map { _ => randomString(stringLength) }
  }
