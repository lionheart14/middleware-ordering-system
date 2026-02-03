FROM python:3.9-slim

WORKDIR /app

# Tools installieren
COPY requirements.txt .
RUN pip install --no-cache-dir -r requirements.txt

# Proto-Datei kopieren und Python-Code generieren
COPY ../api /app/api
RUN python -m grpc_tools.protoc -I/app/api --python_out=. --grpc_python_out=. /app/api/ordering.proto

# Client-Code kopieren
COPY client.py .

# Den Client starten (und am Leben halten für Tests)
CMD ["python", "client.py"]