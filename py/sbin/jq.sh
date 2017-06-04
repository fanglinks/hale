#!/usr/bin/env bash

FILE=$1
OUT=$2

rm ${OUT}
touch ${OUT}
echo "agent_type,agent_id,element_type,element_id,type,timestamp,weight,context" >> ${OUT}
cat ${FILE} | jq -r '. | [.agent.type, .agent.id, .element.type, .element.id, .type, .timestamp, .weight, .context] | @csv' >> ${OUT}