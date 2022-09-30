import requests
import json

url = 'http://localhost:8080/api/v1/notify'

p = [
  {'notification_type' : 'SUBSCRIPTION_PURCHASED', 'subscription': '5793cf6b3fd833521db8c420955e6f01'},
  {'notification_type' : 'SUBSCRIPTION_PURCHASED', 'subscription': '5793cf6b3fd833521db8c420955e6f03'},
  {'notification_type' : 'SUBSCRIPTION_PURCHASED', 'subscription': '5793cf6b3fd833521db8c420955e6f02'},
  {'notification_type' : 'SUBSCRIPTION_PURCHASED', 'subscription': '5793cf6b3fd833521db8c420955e6f04'},
  {'notification_type' : 'SUBSCRIPTION_PURCHASED', 'subscription': '5793cf6b3fd833521db8c420955e6f05'},
  {'notification_type' : 'SUBSCRIPTION_PURCHASED', 'subscription': '5793cf6b3fd833521db8c420955e6f06'},
  {'notification_type' : 'SUBSCRIPTION_PURCHASED', 'subscription': '5793cf6b3fd833521db8c420955e6f07'},
  {'notification_type' : 'SUBSCRIPTION_PURCHASED', 'subscription': '5793cf6b3fd833521db8c420955e6f08'},
  {'notification_type' : 'SUBSCRIPTION_PURCHASED', 'subscription': '5793cf6b3fd833521db8c420955e6f09'},
  {'notification_type' : 'SUBSCRIPTION_PURCHASED', 'subscription': '5793cf6b3fd833521db8c420955e6f00'},
  {'notification_type' : 'SUBSCRIPTION_CANCELED', 'subscription': '5793cf6b3fd833521db8c420955e6f06'},
  {'notification_type' : 'SUBSCRIPTION_CANCELED', 'subscription': '5793cf6b3fd833521db8c420955e6f03'},
  {'notification_type' : 'SUBSCRIPTION_CANCELED', 'subscription': '5793cf6b3fd833521db8c420955e6f08'},
  {'notification_type' : 'SUBSCRIPTION_CANCELED', 'subscription': '5793cf6b3fd833521db8c420955e6f00'},
  {'notification_type' : 'SUBSCRIPTION_RESTARTED', 'subscription': '5793cf6b3fd833521db8c420955e6f06'},
  {'notification_type' : 'SUBSCRIPTION_RESTARTED', 'subscription': '5793cf6b3fd833521db8c420955e6f00'},
]

file = open('notificacoes.txt', 'r')
payload = {'notification_type' : 'SUBSCRIPTION_PURCHASED', 'subscription': '5793cf6b3fd833521db8c420955e6f01'}
# for line in file:
#  if line.strip():
  # payload = json.loads(line.replace('\'', '"'))

for l in p:
  payload = l
  response = requests.post(url, json = payload)
  print(response.text)

file.close()