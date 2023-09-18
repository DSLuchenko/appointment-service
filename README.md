## Система записи пациентов на приём
#### Java 17, Spring Boot, Spring Data Jpa, Flyway 

## SOAP сервис :
За генерацию расписаний отвечает сервис `DoctorScheduleService` который расположен в пакете `ru.dsluchenko.appointment.service.soap.service`

Реализовано два метода `WSDL /soapWs/doctorSchedule.wsdl`:
1. Создание общего расписания для всех врачей на период до 7 дней
2. Создание индивидуального расписания для каждого врача на период до 7 дней


Расписание генерируется на основание правил, описанных в `src/main/resources/doctorSchedule.xsd`

Всего есть два правила:
1. Правило для генерации общего расписания для всех врачей(ruleForGeneralSchedule):
   - appointmentDate - дата приема
   - scheduleStartTime - время начала приема
   - durationOfTicket - продолжительность одного талона
   - countTickets - количество талонов в день приема
```xml
<xs:element name="generateGeneralScheduleRequest">
   <xs:complexType>
   <xs:sequence>
   <xs:element name="generalRule"
               type="tns:ruleForGeneralSchedule"
               maxOccurs="7"/>
   </xs:sequence>
   </xs:complexType>
</xs:element>

<xs:complexType name="ruleForGeneralSchedule">
   <xs:sequence>
   <xs:element name="appointmentDate" type="xs:date"/>
   <xs:element name="scheduleStartTime" type="xs:time"/>
   <xs:element name="durationOfTicket" type="xs:time"/>
   <xs:element name="countTickets" type="xs:int"/>
   </xs:sequence>
</xs:complexType>
```
2. Правило для генерации индивидуального расписания для каждого врача (ruleForIndividualSchedule)
- коллекция ruleForGeneralSchedule
- doctorUuid - идентификатор врача, для которого генерируется расписание на период на основании коллекции ruleForGeneralSchedule 

```xml

<xs:element name="generateIndividualScheduleRequest">
   <xs:complexType>
      <xs:sequence>
         <xs:element name="individualRule"
                     type="tns:ruleForIndividualSchedule"
                     maxOccurs="unbounded"/>
      </xs:sequence>
   </xs:complexType>
</xs:element>

<xs:complexType name="ruleForIndividualSchedule">
<xs:sequence>
   <xs:element name="generalRule"
               type="tns:ruleForGeneralSchedule"
               maxOccurs="7"/>
   <xs:element name="doctorUuid" type="xs:string"/>
</xs:sequence>
</xs:complexType>
```

### Примеры запросов:
```xml
<Envelope xmlns="http://schemas.xmlsoap.org/soap/envelope/">
    <Body>
        <generateGeneralScheduleRequest xmlns="http://dsluchenko.ru/appointment/service/soap/domain">
            <generalRule>
                <appointmentDate>2023-09-18</appointmentDate>
                <scheduleStartTime>08:00:00</scheduleStartTime>
                <durationOfTicket>00:25:00</durationOfTicket>
                <countTickets>10</countTickets>
            </generalRule>
            <generalRule>
                <appointmentDate>2023-09-19</appointmentDate>
                <scheduleStartTime>10:00:00</scheduleStartTime>
                <durationOfTicket>00:30:00</durationOfTicket>
                <countTickets>5</countTickets>
            </generalRule>
            <generalRule>
                <appointmentDate>2023-09-20</appointmentDate>
                <scheduleStartTime>13:00:00</scheduleStartTime>
                <durationOfTicket>00:15:00</durationOfTicket>
                <countTickets>20</countTickets>
            </generalRule>
        </generateGeneralScheduleRequest>
    </Body>
</Envelope>
```
```xml
<Envelope xmlns="http://schemas.xmlsoap.org/soap/envelope/">
    <Body>
        <generateIndividualScheduleRequest xmlns="http://dsluchenko.ru/appointment/service/soap/domain">
            <individualRule>
                <generalRule>
                    <appointmentDate>2023-09-18</appointmentDate>
                    <scheduleStartTime>08:00:00</scheduleStartTime>
                    <durationOfTicket>00:25:00</durationOfTicket>
                    <countTickets>10</countTickets>
                </generalRule>
                <generalRule>
                    <appointmentDate>2023-09-19</appointmentDate>
                    <scheduleStartTime>10:00:00</scheduleStartTime>
                    <durationOfTicket>00:30:00</durationOfTicket>
                    <countTickets>5</countTickets>
                </generalRule>
                <generalRule>
                    <appointmentDate>2023-09-20</appointmentDate>
                    <scheduleStartTime>13:00:00</scheduleStartTime>
                    <durationOfTicket>00:15:00</durationOfTicket>
                    <countTickets>20</countTickets>
                </generalRule>
                <doctorUuid>7021f496-eb0d-4b2f-8b66-639e572d15dd</doctorUuid>
            </individualRule>
            <individualRule>
                <generalRule>
                    <appointmentDate>2023-09-18</appointmentDate>
                    <scheduleStartTime>13:00:00</scheduleStartTime>
                    <durationOfTicket>00:25:00</durationOfTicket>
                    <countTickets>10</countTickets>
                </generalRule>
                <generalRule>
                    <appointmentDate>2023-09-19</appointmentDate>
                    <scheduleStartTime>08:00:00</scheduleStartTime>
                    <durationOfTicket>00:45:00</durationOfTicket>
                    <countTickets>10</countTickets>
                </generalRule>
                <generalRule>
                    <appointmentDate>2023-09-20</appointmentDate>
                    <scheduleStartTime>08:00:00</scheduleStartTime>
                    <durationOfTicket>00:30:00</durationOfTicket>
                    <countTickets>20</countTickets>
                </generalRule>
                <doctorUuid>5765d5de-9b44-40b5-9874-7bbc981db855</doctorUuid>
            </individualRule>
        </generateIndividualScheduleRequest>
    </Body>
</Envelope>
```
### Пример ответа:
```xml
<SOAP-ENV:Envelope xmlns:SOAP-ENV="http://schemas.xmlsoap.org/soap/envelope/">
    <SOAP-ENV:Header/>
    <SOAP-ENV:Body>
        <ns2:generateScheduleResultResponse xmlns:ns2="http://dsluchenko.ru/appointment/service/soap/domain">
            <ns2:status>OK</ns2:status>
            <ns2:ticketsCount>100</ns2:ticketsCount>
            <ns2:note>for this uuids: [7022f496-eb0d-4b2f-8b66-639e572d15dd] not generated, but doctors not founded</ns2:note>
        </ns2:generateScheduleResultResponse>
    </SOAP-ENV:Body>
</SOAP-ENV:Envelope>
```
---

## REST сервис:
1. Получить список талонов к врачу, с учетом фильтров:
   - талоны на конкретную дату
   - талоны на период
   - только свободные талоны или весь список(свободные+занятые)
2. Получить список талонов пациента, с постраничным выводом результатов(пагинация)
3. Записать пациента на прием

## Методы:
### GET `/api/tickets/byDoctor/{doctorUuid}`
### Возвращает список талонов к конкретному врачу
#### Обязательные параметры(path):
- *doctorUuid* - идентификатор врача
    тип: "string" формат: "uuid"


#### Необязательные параметры(queries):
1. *startDate* - дата начала периода, за который выбираем талоны.
   - тип: "string" формат: "date"
   - значение по умолчанию: текущий день
2. *period* - количество дней для определения конца периода(startDate + period)
   - тип: "integer" формат: "int64"
   - значение по умолчанию: 7 дней
3. *onlyAvailable* - отбираем только свободные талоны. 
   - тип: "boolean"
   - значение по умолчанию: "true"
#### Пример ответа (application/json):
```json
[
  {
    "id": 1,
    "doctor": {
      "uuid": "7021f496-eb0d-4b2f-8b66-639e572d15dd",
      "fullName": "TestD"
    },
    "appointmentDate": "2023-09-18",
    "appointmentTime": "10:20:00"
  }
]
```
#### HTTP коды ответов:
- 200 OK - если доктор найден, возвращается список талонов(если талонов нет, пустой список).
- 404 NOT FOUND - если не найден доктор с таким doctorUuid

---

### GET `/api/tickets/byPatient/{patientUuid}`
### Возвращает список талонов конкретного пациента
#### Обязательные параметры(path):
- *patientUuid* - идентификатор пациента
  тип: "string" формат: "uuid"


#### Необязательные параметры(queries):
1. *page* - текущая страница
    - тип: "integer" формат: "int32"
    - значение по умолчанию: 0
2. *size* - количество элементов на странице
    - тип: "integer" формат: "int32"
    - значение по умолчанию: 10
#### Пример ответа (application/json):
```json
{
  "totalElements": 1,
  "totalPages": 1,
  "hasNext": false,
  "isLast": true,
  "tickets": [
    {
      "id": 1,
      "doctor": {
        "uuid": "7021f496-eb0d-4b2f-8b66-639e572d15dd",
        "fullName": "TestD"
      },
      "patient": {
        "uuid": "3fa85f64-5717-4562-b3fc-2c963f66afa6",
        "fullName": "TestP",
        "dateOfBirth": "2023-09-18"
      },
      "appointmentDate": "2023-09-18",
      "appointmentTime": "09:30:00"
    }
  ]
}
```
HTTP коды ответов:
 - 200 OK - если пациент найден, возвращается список талонов(если талонов нет, пустой список).
 - 404 NOT FOUND - если не найден пациент с таким patientUuid
---

### PATCH `/api/tickets/{ticketId}`
### Запись пациента на прием по идентификатору талона
#### Обязательные параметры(path):
1. *ticketId* - идентификатор талона
   - тип: "integer" формат: "int64"
#### Тело запроса (application/json)
```json
{
  "patientUuid": "3fa85f64-5717-4562-b3fc-2c963f66afa6"
}
```
#### Пример ответа (application/json):
```json
{
  "id": 1,
  "doctor": {
    "uuid": "7021f496-eb0d-4b2f-8b66-639e572d15dd",
    "fullName": "TestD"
  },
  "patient": {
    "uuid": "3fa85f64-5717-4562-b3fc-2c963f66afa6",
    "fullName": "TestP",
    "dateOfBirth": "2023-09-18"
  },
  "appointmentDate": "2023-09-18",
  "appointmentTime": "08:00:00"
}

```
HTTP коды ответов:
- 200 OK - пациент записан на прием.
- 404 NOT FOUND - если не найден пациент с таким patientUuid или талон с ticketId 

---