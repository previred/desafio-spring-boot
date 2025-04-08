curl --location 'http://localhost:8080/api/v1/tasks' \
--header 'Content-Type: application/json' \
--header 'Authorization: Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJwYWJsby5tYXJtb2wiLCJpYXQiOjE3NDQxNDc5MTMsImV4cCI6MTc0NDIzNDMxM30.igkgoNg9S_JbvroGSAaImwZVyTdquD7nk-kbHlsXPJg' \
--data '{
    "title":"Tarea de prueba",
    "description":"Descripción de ejemplo",
    "createdBy": "pablo.marmol"
}'