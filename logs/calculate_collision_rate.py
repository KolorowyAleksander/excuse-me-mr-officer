from typing import List

from cassandra.cqlengine import columns
from cassandra.cqlengine import connection
from cassandra.cqlengine.models import Model


class Logs(Model):
    officerid = columns.Text(primary_key=True)
    reportid = columns.Text(primary_key=True)
    departure = columns.DateTime()
    arrival = columns.DateTime()


connection.setup(['150.254.32.71'], 'reports')

logs: List[Logs] = Logs.objects.all().limit(0)

number_of_logs = logs.count()
reports_taken = {}
min_departure = None
max_arrival = None
for log in logs:
    reportId = log.reportid
    if reportId in reports_taken:
        reports_taken[reportId] += 1
    else:
        reports_taken[reportId] = 1

    if not min_departure or log.departure < min_departure:
        min_departure = log.departure

    if not max_arrival or log.arrival > max_arrival:
        max_arrival = log.arrival

number_of_reports = len(reports_taken)
collision_rate = number_of_logs / number_of_reports - 1
print(f'Number of logs: {number_of_logs}')
print(f'Number of reports: {number_of_reports}')
print(f'Collision rate: {collision_rate}')
print(f'{min_departure} - {max_arrival}')
