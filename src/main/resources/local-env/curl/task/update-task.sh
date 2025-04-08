curl --location --request PUT 'http://localhost:8080/api/v1/tasks/2c18dbee-e719-4e65-abdc-2b7de95908a0' \
--header 'Content-Type: application/json' \
--header 'Authorization: Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJwYWJsby5tYXJtb2wiLCJpYXQiOjE3NDQxNDc5MTMsImV4cCI6MTc0NDIzNDMxM30.igkgoNg9S_JbvroGSAaImwZVyTdquD7nk-kbHlsXPJg' \
--data '{
    "title":"Tarea de prueba MODIFICADA",
    "description":"Otra description",
    "status": "done"
}'