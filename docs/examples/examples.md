# API Examples

In the following, some examples of using the TriReg2 API are provided.

## Curl

This section presents some [`curl`](https://curl.se/) commands as examples of calling the TriReg2 server running in TCS (development environment) via the TriReg2 API.

Those curl commands, that specifies basic auth in the request, use a test user named `testuser` with default password. This test user has already been created in the test system, which means you can run the commands. But be aware that the data of this test user is available to everyone running commands for that user.

Also, at the moment, the TriReg2 server running in TCS dev. env. has no database yet, so every time registration created via the API will only be kept in memory, unless you export the time registration data to the old trireg server via the export API. 

The old trireg server, which is used in this setup, is running in a test environment, and is only used by the TriReg2 server.


### Create 

Create a time registration using basic auth for a test user with the curl command:

```
curl -u "testuser:triregpassword1" --location 'https://trireg2.tcs.trifork.dev/time-registration' \
--header 'Content-Type: application/json' \
--data '{
    "taskId": "sometask",
    "date": "2024-05-02",
    "duration": "PT1H30M"
}' -w "\n"
```

### Get

Get time registrations for a test user using basic auth with the curl command:

```
curl -u "testuser:triregpassword1" --location 'https://trireg2.tcs.trifork.dev/time-registration' -w "\n"
```

Using OpenID Connect as identity authentication protocol, get the access token after login and replace the access token in the following command: 

```
curl --location 'https://trireg2.tcs.trifork.dev/time-registration' \
--header 'Authorization: Bearer <access_token>'
```

## Export 

Export existing time registrations for a test user (using basic auth) to the old trireg server with the curl command:

```
curl -u "testuser:triregpassword1" --location --request POST 'https://trireg2.tcs.trifork.dev/export?start=2024-05-01&end=2024-05-09' -w "\n"
```

## Import

Import time registration data for a test user (using basic auth) into the TriReg2 server with the curl command:

```
curl -u "testuser:triregpassword1" --location 'https://trireg2.tcs.trifork.dev/import' \
--header 'Content-Type: application/json' \
--data '[
    {
        "userId": "123",
        "taskId": "hello",
        "date": "2024-05-03",
        "duration": "PT1H20M",
        "tags": [
            {
                "tagConfigurationId": "noteId",
                "value": "note"
            }
        ]
    }
]'
```