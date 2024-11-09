# Лабараторная 6 (модифицированная  лб 3)

## Обзор
Приложение позволяет пользователям создавать, просматривать и управлять напоминаниями, которые сохраняются в локальной базе данных. Пользователи могут задавать время и дату для напоминаний 
и получать настраиваемые уведомления. При нажатии на уведомление оно открывает приложение, отображая полные данные напоминания

## Использованные технологии
1. Язык разработки - Java
2. База данных - SQLite
3. Распределённая система управления версиями - GitHub

## Функциональные возможности
1. **Создание напоминаний**: Пользователи могут добавлять напоминания при нажатии на кнопку "добавить запись и уведомление", тогда добавляется в бд новый студент и напоминание просмотреть измененный список в соотвествующие для этого таблицы

2. **Просмотр напоминаний**: Список всех установленных напоминаний, сохранённых в базе данных, отображается в приложении после нажатия на кнопку "просмотр уведомлений"

3. **Удаление напоминаний**: После нажатии на напоминание его можно удалить, убирая их из базы данных и отменяя соответствующие уведомления, но перед этим необходимо подтвердить событие 

4. **Установка даты и времени**: Для выбора даты и времени напоминания используются **TimePickerDialog** и **DatePickerDialog**.

5. **Настройка уведомлений**: Уведомления стилизованы для отображения с кастомным логотипом в Центре уведомлений и Статус-баре устройства

6. **Переход к напоминанию**: При нажатии на уведомление в Центре уведомлений открывается активити приложения, где отображается полный текст напоминания

## Основные классы
Приложение использует следующие классы для создания и управления напоминаниями:
- **Notification** и **NotificationManager** для создания и управления уведомлениями
- **PendingIntent** для обработки действий при нажатии на уведомление
- **BroadcastReceiver** для получения сигнала от **AlarmManager**
- **AlarmManager** для планирования времени срабатывания уведомлений

## Инструкция по использованию
1. Откройте приложение и создайте новое напоминание, задав дату
2. Выберите нужное время и дату с помощью TimePickerDialog и DatePickerDialog
3. Сохраните напоминание
4. Просматривайте список активных напоминаний и удаляйте ненужные
5. Получите уведомление в заданное время. При нажатии на него откроется полное описание напоминания в приложении