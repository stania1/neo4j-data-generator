Neo4j Random Data Generator 
---

A little helper script to generate random data that can be imported into neo4j for the purpose of estimating graph size.

### Dependency 
- [ammonite](http://ammonite.io/)

### Fast-feedback Development Mode
`amm -watch data-generator.sc`

### Running

with default arguments
`amm data-generator.sc`

with custom arguments
`amm data-generator.sc --nodeCount 200 --relationshipsPerNode 15`

### Bulk import to neo4j using neo4j-admin
`neo4j-admin import --nodes path/to/nodes.csv --relationships path/to/relationships.csv`

