import uuid
import random

from cassandra.cqlengine import columns
from cassandra.cqlengine import connection
from cassandra.cqlengine.models import Model

class Reports(Model):
    id = columns.Text(primary_key=True)
    x = columns.Integer()
    y = columns.Integer()

connection.setup(['150.254.32.71'], 'reports')


for i in range(10000):
    report = Reports(id=str(uuid.uuid4()), x=random.randint(0, 99), y=random.randint(0, 99))
    report.save()
