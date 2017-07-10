# Ingest Data

You can ingest data into neo4j using your preferred method. One approach is to generate a CSV file with the data, and use the Neo4j command line shell to import it.
Here, we describe this process for ecommerce sample data. We'll create random data in JSON format, convert it to CSV, and use the Neo4j command line shell tool to import it.

Before we get started, there are two things you'll need to have:

  - *jq*: get it [here](https://stedolan.github.io/jq/)

## Generating random data

Using the [ecommerce](../py/hale/data/ecommerce.py) python module to generate random data.
From the *py* directory:

```
$ python -m hale.data.ecommerce -h
```

It prints out help information, which looks like this:

```
usage: ecommerce.py [-h] --nusers NUSERS --nproducts NPRODUCTS --nevents
                    NEVENTS --start_date START_DATE --end_date END_DATE
                    --output OUTPUT

Generate fake ecommerce data.

optional arguments:
  -h, --help            show this help message and exit
  --nusers NUSERS       Number of users to generate
  --nproducts NPRODUCTS
                        Number of users to generate
  --nevents NEVENTS     Number of events to generate
  --start_date START_DATE
                        Starting date
  --end_date END_DATE   End date
  --output OUTPUT       End date

```

Call the module specifying the parameters you want, including the `output`, cause that's where the data will be written to.
This will be a raw text file, where each line corresponds to a JSON object with the data. So, we'll need to convert that into CSV.

## Converting to CSV

We'll use a tool called [jq](https://stedolan.github.io/jq/). `jq` is a tool to process JSON files from the command line environment.
We have a bash script ready for our file, so all you have to do is make sure you have `jq` in your environment path.
The script is [jq.sh](../py/sbin/jq.sh). We call it with two parameters, the *source* and *target*:

```
$ bash py/sbin/jq.sh in_datafile.json out_datafile.csv
```

To learn more about `jq`, you can check out their [documentation](https://stedolan.github.io/jq/manual/). Their site also has a short [tutorial to help you get started](https://stedolan.github.io/jq/tutorial/).

## CSV Import

Now we are ready to import our data into Neo4j.
To import data, you can go to the Neo4j command line shell, and run the following query:

```
USING PERIODIC COMMIT 300

LOAD CSV WITH HEADERS FROM "file:///path/to/in_datafile.csv" AS e

MERGE (agent :Entity {type: e.agent_type, id: e.agent_id})
MERGE (element :Entity {type: e.element_type, id: e.element_id})
CREATE (event {type: e.type, weight: toFloat(e.weight), timestamp: toInt(e.timestamp), context: e.context})
CREATE (agent)-[:AGENT_EVENT]->(event)-[:EVENT_ELEMENT]->(element);
```

Where `in_datafile.csv` is the file you generated with the CSV data.
Note that you have to use these labels, and relationship type, but you can add additional labels to the nodes.
And you can set the periodic commit to whatever number goes well with your data volume, and machine.
