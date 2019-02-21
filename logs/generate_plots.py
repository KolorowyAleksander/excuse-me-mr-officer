import json
from pathlib import Path
import matplotlib.pyplot as plt

RESULTS_FILE_PATH = Path('results.json')

with RESULTS_FILE_PATH.open() as results_file:
    results = json.load(results_file)

x_labels = []
no_partition_data = []
partition_data = []

no_partition_results = results['noPartition']
partition_results = results['partition']

for result in no_partition_results:
    x_labels.append(result['numberOfOfficers'])
    no_partition_data.append(result['collisions'])

for result in partition_results:
    partition_data.append(result['collisions'])

print(x_labels, no_partition_data, partition_data)
plt.title('Collisions [number of reports = 100]')
plt.xlabel('Number of officers')
plt.ylabel('Rate of collisions')
plt.plot(x_labels, no_partition_data, 'bs--', x_labels, partition_data, 'rs--')
plt.legend(['No partition', 'Partition'])
plt.show()
