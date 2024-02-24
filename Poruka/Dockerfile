FROM python:3.11.4
WORKDIR /PorukaServer
COPY ./requirements.txt requirements.txt
RUN pip install --no-cache-dir --upgrade -r requirements.txt
COPY . .
CMD ["gunicorn", "--bind", "0.0.0.0:5000", "-k", "gevent", "-w", "1", "PorukaServer:app"]