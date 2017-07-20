#!/usr/bin/env bash

FILE=$1
OUT=$2

if [ -f ${OUT} ]; then
    rm ${OUT}
fi

touch ${OUT}
echo "id,agentType,agentId,elementType,elementId,type,timestamp,weight,context" >> ${OUT}
cat ${FILE} | jq -r '. | [.id, .agent.type, .agent.id, .element.type, .element.id, .type, .timestamp, .weight, .context] | @csv' >> ${OUT}