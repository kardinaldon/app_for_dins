короткий ролик по запуску/техническим аспектам приложения https://www.youtube.com/watch?v=ueq0BRJole8

Инструкция по запуску:
- запустить приложение из консоли java -jar kafka_app-0.0.1-SNAPSHOT.jar, дождаться завершения старта;
- ввести 1,нажать enter - приложение заполнит первую таблицу 1000 строками из файла random_names.txt, затем считает эти строки из этой таблицы и отправит в топик kafka;
- ввести 2,нажать enter - приложение считает все записи из топика и разместит их во второй таблице;
- ввести 3,нажать enter - приложение завершит работу;
