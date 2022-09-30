import requests
import json

url = 'http://localhost:8080/api/v1/notify'

file = open('notificacoes.txt', 'r')
for line in file:
 if line.strip():
  payload = json.loads(line.replace('\'', '"'))
  response = requests.post(url, json = payload)
  print(response.text)

file.close()