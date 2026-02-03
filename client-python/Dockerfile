FROM python:3.9-slim

WORKDIR /app

# 1. Pfad zum Unterordner hinzufügen
COPY client-python/requirements.txt .
RUN pip install --no-cache-dir -r requirements.txt

# 2. Proto-Datei liegt im api-Ordner (vom Hauptverzeichnis aus)
COPY api /app/api
RUN python -m grpc_tools.protoc -I/app/api --python_out=. --grpc_python_out=. /app/api/ordering.proto

# 3. Pfad zum Unterordner für das Skript hinzufügen
COPY client-python/client.py .

CMD ["python", "client.py"]