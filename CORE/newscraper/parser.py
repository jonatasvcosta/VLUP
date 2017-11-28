from dateutil.parser import parse as date_parser
from datetime import datetime


def extract_publish_date(article):
    if article.publish_date:
        return article.publish_date

    # extra parsing if newspaper doesn't find
    now = datetime.utcnow()
    # Dates before now
    possible_dates = {d for d in find_datestring(article.meta_data) if d is not None and d < now}

    if len(possible_dates) > 0:
        return sorted(possible_dates, reverse=True)[0]
    else:
        return now


def find_datestring(dictionary):
    possible_dates = set()
    for k, v in dictionary.items():
        if isinstance(v, dict):
            possible_dates = possible_dates | find_datestring(v)
        elif isinstance(v, str):
            possible_dates.add(parse_date(v))

    return possible_dates


def parse_date(datestring):
    try:
        d = date_parser(datestring)
        if d.utcoffset():
            d = d - d.utcoffset()
        return d.replace(tzinfo=None)
    except:
        return None
