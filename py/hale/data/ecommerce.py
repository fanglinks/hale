import random
import argparse
import random
import time
import uuid
import json

import faker
import numpy as np


def random_timestamp_within(start, end, format, prop):
    """Get a time at a proportion of a range of two formatted times.

    start and end should be strings specifying times formated in the
    given format (strftime-style), giving an interval [start, end].
    prop specifies how a proportion of the interval to be taken after
    start.  The returned time will be in the specified format.
    """

    stime = time.mktime(time.strptime(start, format))
    etime = time.mktime(time.strptime(end, format))

    ptime = stime + prop * (etime - stime)
    return ptime


EVENTS = ['view', 'rate', 'buy']
RATINGS = [x for x in range(1, 5+1)]

def get_event():

    event = np.random.choice(EVENTS, 1, p=[0.6, 0.1, 0.3])[0]

    context = None

    if event == 'rate':
        return event, np.random.choice(RATINGS, 1)[0], context
    else:
        return event, None, context


def main(n_users, n_products, n_events, start_date, end_date, output):

    with open(output, 'w') as fp:

        for _ in range(n_events):

            event_type, weight, context = get_event()

            event = {
                'id': str(uuid.uuid4()),
                'type': event_type,
                'weight': weight,
                'timestamp': int(random_timestamp_within(start_date, end_date, '%Y-%m-%d', np.random.uniform(0, 1))),
                'context': context,
                'agent': {
                    'id': 'user-%d' % np.random.randint(1, n_users),
                    'type': 'user'
                },
                'element': {
                    'id': 'item-%d' % np.random.randint(1, n_products),
                    'type': 'product'
                }
            }

            fp.write(json.dumps(event))
            fp.write('\n')


if __name__ == '__main__':

    parser = argparse.ArgumentParser(description='Generate fake ecommerce data.')
    parser.add_argument('--nusers', type=int, required=True, help='Number of users to generate')
    parser.add_argument('--nproducts', type=int, required=True, help='Number of users to generate')
    parser.add_argument('--nevents', type=int, required=True, help='Number of events to generate')
    parser.add_argument('--start_date', type=str, required=True, help='Starting date, format: yyyy-mm-dd')
    parser.add_argument('--end_date', type=str, required=True, help='End date, format: yyyy-mm-dd')
    parser.add_argument('--output', type=str, required=True, help='Output file, e.g. data.json')

    args = parser.parse_args()

    fake = faker.Factory()

    main(args.nusers, args.nproducts, args.nevents, args.start_date, args.end_date, args.output)
