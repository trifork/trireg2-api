# API Examples

In the following, some examples of using the TriReg2 API are provided.

The base URL of the TriReg2 service running in development environment in TCS is: 

`https://trireg2.tcs.trifork.dev`

## Curl

This section presents some [`curl`](https://curl.se/) commands as examples of calling the TriReg2 server running in TCS (development environment) via the TriReg2 API.

The curl commands that specifies basic auth in the requests use a test user named `testuser` with default password `triregpassword1`. This test user has already been created in the test system, which means you can run the commands out of the box. But, be aware that the data of this test user is available to everyone running commands for that user.

Also, at the moment, the TriReg2 server running in TCS has no database configured yet, so every time registration created via the API will only be kept in memory in the TriReg2 server, unless you export the time registration data to the old trireg server via the export API. 

The old trireg server, which is used in this setup, is running in a test environment, and is only used by the TriReg2 server during test and development.

### Duration

The duration provided in some of the API requests or responses must be specified as an ISO 8601 formatted duration component, on the form `PnDTnHnMn.nS`. For example, `PT1H30M` representing 1 hour and 30 minutes.

### Add 

Add a time registration using basic auth for a test user with the curl command:

```
curl -u "testuser:triregpassword1" --location 'https://trireg2.tcs.trifork.dev/time-registration' \
--header 'Content-Type: application/json' \
--data '{
    "taskId": "1",
    "date": "2024-05-02",
    "duration": "PT1H30M"
}' -w "\n"
```

### Get

Get time registrations for a test user using basic auth with the curl command:

```
curl -u "testuser:triregpassword1" --location 'https://trireg2.tcs.trifork.dev/time-registration' -w "\n"
```

If you want to use OpenID Connect as identity authentication protocol get the `access token` after login and insert  the `access token` in the following command:

```
curl --location 'https://trireg2.tcs.trifork.dev/time-registration' \
--header 'Authorization: Bearer <access_token>'
```

## Export 

Export existing time registrations for a test user (using basic auth) to the old trireg server with the curl command:

```
curl -u "testuser:triregpassword1" --location --request POST 'https://trireg2.tcs.trifork.dev/export?start=2024-05-01&end=2024-05-09' -w "\n"
```

You can modify the `start` and `end` dates in the command's URL parameters to match the period of time you want to export data for.
## Import

Import time registration data for a test user (using basic auth) into the TriReg2 server with the curl command:

```
curl -u "testuser:triregpassword1" --location 'https://trireg2.tcs.trifork.dev/import' \
--header 'Content-Type: application/json' \
--data '[
    {
        "userId": "123",
        "taskId": "1",
        "date": "2024-05-03",
        "duration": "PT1H20M",
        "tags": [
            {
                "tagConfigurationId": "2",
                "value": "note"
            }
        ]
    }
]'
```

## Several time registrations in one request

Add several time registrations for a user in the same request, using basic auth, for a test user with the curl command:

```
curl -u "testuser:triregpassword1" --location 'https://trireg2.tcs.trifork.dev/time-registration/bulk' \
--header 'Content-Type: application/json' \
--data '[
    {
        "taskId": "2",
        "date": "2024-05-20",
        "duration": "PT1H"
    },
    {
        "taskId": "1",
        "date": "2024-05-19",
        "duration": "PT6H"
    },
    {
        "taskId": "2",
        "date": "2024-05-18",
        "duration": "PT5H"
    }
]' -w "\n"
```
