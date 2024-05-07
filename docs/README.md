# Getting started guide

Overall description of the TriReg2 API and examples of its usage.

## Login 

For a quick start, you can use [basic authentication](#Basic-authentication), or you can go for full-blown [OpenID Connect authentication](#OpenID-Connect).

### Basic authentication
**Note that this will not be available in the final version of the API**, but it will allow you to focus on actual functionality during the Hackerdays event. Use the following credentials:

* username: **Anything you prefer**
* password: **triregpassword1**

All registrations and queries you perform will be associated with the username you log in as. This means that your registrations can clash with other users if you don't use distinct usernames, so your Trifork initials are probably a good choice.

### OpenID Connect
There is support for OpenID Connect (OIDC) authentication, but that will require more work from your side. You can log in using the endpoint specified in https://oidc.hosted.trifork.com/auth/realms/trifork/.well-known/openid-configuration, and then send the generated JWT in the authorization header of all your requests.

Notice that access tokens from the Trifork OIDC server expires after 5 minutes, so you need to refresh your access token regularly.

## APIs

### User API
From a user's perspective, the core data model consists of **tasks**, **time registrations** and **tags**. 

* A **task** is something you can associate time registrations with.
* A **time registration** is a record of some time spent on some task by some employee on some day.  
* The **tags** are there to do a more detailed categorization of the time registrations. Each task is configured with rules about the tags that can be associated with it, but that is more of an admin concern than a user concern.

The test setup has the following configurations by default:

* TaskId=1 "Friday Meeting"
  * Tag=Note: A string note, which can contain whatever you want.
* TaskId=2 "The Project"
  * Tag=Note: A string tag, which can contain whatever you want.
  * Tag=Billable: A boolean tag, which can contain "true" or "false".

### Import API
Allows importing data from other systems into TriReg2. There is currently only an endpoint receiving json, but we expect to expand with something that can handle XML/CSV blobs.

All of these will expect the same general structure - a list of time registrations, each with an associated list of tags. See [User api](#User-API) for details.

### Export API
Currently only allows exporting time registrations to a test-system running the old Trireg on an Azure server. This is available at https://triforktimeregmvcweb20240502133054.azurewebsites.net/ - if you need to be able to access this system, we need to create a user for you. Contact the team for this.

We plan to expand this with other export options, such as exporting to CSV/JSON/XML, or deep integrations with other systems.