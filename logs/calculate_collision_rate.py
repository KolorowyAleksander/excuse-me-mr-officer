from typing import List

from cassandra.cqlengine import columns
from cassandra.cqlengine import connection
from cassandra.cqlengine.models import Model


class Logs(Model):
    officerid = columns.Text(primary_key=True)
    reportid = columns.Text(primary_key=True)
    departure = columns.Date()
    arrival = columns.Date()


connection.setup(['192.168.15.15'], 'reports')

logs: List[Logs] = Logs.objects.all().limit(0)

number_of_logs = logs.count()
reports_taken = {}
for log in logs:
    print(log)
    reportId = log.reportid
    if reportId in reports_taken:
        reports_taken[reportId] += 1
    else:
        reports_taken[reportId] = 1

collision_rate = number_of_logs / len(reports_taken) - 1
print(collision_rate)
