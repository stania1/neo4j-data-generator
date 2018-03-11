import $ivy.`com.github.tototoshi::scala-csv:1.3.5`, com.github.tototoshi.csv._
import java.io.File
import $file.random

val nodeCount = 15
val relationshipsPerNode = 10
val propertiesPerNode = 50
val propertiesPerRelationship = 10
val propertyKeyValueLength = 20

@main
def main(nodeCount: Int = nodeCount, relationshipsPerNode: Int = relationshipsPerNode, propertiesPerNode: Int = propertiesPerNode, propertiesPerRelationship: Int = propertiesPerRelationship, propertyKeyValueLength: Int = propertyKeyValueLength) = {
  println("Neo4j Data Generator")
  println(s"Generating ${nodeCount} dummy nodes and ${relationshipsPerNode} relationships per node")
  writeNodes(nodeCount, propertiesPerNode, propertyKeyValueLength)
  writeRelationships(nodeCount, relationshipsPerNode, propertiesPerRelationship, propertyKeyValueLength)
}




private def writeCsv(filename: String)(rows: List[List[String]]) = {
  val f = new File(filename)
  val writer = CSVWriter.open(f)
  rows.foreach { writer.writeRow(_) }
}

private def writeNodes(nodeCount: Int, numberOfProperties: Int, propertyKeyValueLength: Int, flush: List[List[String]] => Unit = writeCsv("nodes.csv") _): Unit = {
  val header = List("id:ID") ::: random.stringList(numberOfProperties, propertyKeyValueLength) ::: List(":LABEL")

  val nodes = (1 to nodeCount).map { id =>
    List(random.formattedId(id)) ::: random.stringList(numberOfProperties, propertyKeyValueLength) ::: List("Person")
  }.toList

  val rows = List(header) ::: nodes
  flush(rows)
  println(s"${nodeCount} nodes written, each with ${numberOfProperties} properties of length ${propertyKeyValueLength}")
}

private def writeRelationships(nodeCount: Int, relationshipsPerNode: Int, numberOfProperties: Int, propertyKeyValueLength: Int, flush: List[List[String]] => Unit = writeCsv("relationships.csv") _): Unit = {
  val header = List(":START_ID", ":END_ID", ":TYPE") ::: random.stringList(numberOfProperties, propertyKeyValueLength)

  val relationships = (1 to nodeCount).map { nodeId =>
    (1 to relationshipsPerNode).map { _ =>
      List(random.formattedId(nodeId), random.formattedId(random.id(nodeCount)), "RELATED_TO") ::: random.stringList(numberOfProperties, propertyKeyValueLength)
    }.toList
  }.toList.reduce(_ ::: _)

  val rows =  List(header) ::: relationships
  flush(rows)
  println(s"${nodeCount * relationshipsPerNode} relationships written, each with ${numberOfProperties} properties of length ${propertyKeyValueLength}")
}
