# Java Web MVC OAuth Sample

This sample demonstrates OAuth 2.0 Login using the Authorization Code grant
and Blizzard's OAuth Provider service. In the backend, it uses Spring Boot and Spring Security 5 on the back end with the
native OAuth 2.0 client support.

This would be useful if you wanted to allow users to log into your site using
their Battle.net account, then perform actions on behalf of that user (obtain profile data, etc).

## About This Sample
This sample relies significantly on the native Spring Boot conventions to set up your OAuth client
and OAuth login. This also uses Spring Web MVC to perform web requests (as opposed to Spring Webflux).

Each class and method should have documentation explaining the important information.

## Running
This sample can be imported into an IDE and run using the `main` method in `JavaWebMVCSampleOAuthClient`.
To use the command line, `mvn spring-boot:run` or build the jar file and run with `mvn package` and
`java -jar target/*.jar`. This requires both Maven and Java to be installed locally. This sample
uses Java 16, but can be compiled against Java 8/11 by changing the value in the `pom.xml`.

This sample runs on `localhost:8080` and all documentation and code will reflect as such. To run
on a different host or port, you will need to update the OAuth Client Redirect URIs using the
developer portal.

## Enable OAuth 2 Login
Spring Security 5 provides native OAuth 2 client support and OAuth 2 login. All the necessary
configuration can be found in the [application.yml] properties file, except for your OAuth client
ID and secret. **Fill in your OAuth client ID to the necessary field.** The secret should be treated
as a credential. To get started, pass this value in as a runtime argument.

`--spring.security.oauth2.client.registration.blizzard.client-secret=YOUR CLIENT SECRET`

You may also set those properties as environment variables, in a properties file, or any property
source supported by Spring Boot.

With the OAuth 2 client dependency in place and those properties set, your application will
now offer authentication via Battle.net. For the sake of this sample, all pages require authentication.
You may wish to have a welcome page before prompting the user with login.

Upon visiting a page that requires authentication, the user will be redirected to Battle.net. If
not already signed in, the user will be prompted to enter their credentials. The first time a user accesses
your application, they will also be presented with an authorization screen. If the user chooses to
continue, they will be redirected back to your application. Make sure to update your OAuth Client redirect URIs
in the developer portal as only these URIs will be treated as value return URIs.

## Accessing API Resources

Now that we have obtained a valid authorization for the user, we can use this to make API calls to obtain some user information.
When configured properly, the Spring HTTP [WebClient] will add an Authorization header containing a bearer token (Access
Token) for the currently authorized user. This will be used to authenticate against Blizzard APIs. This sample
interacts with some basic information using the [SC2 APIs].

### Reference Documentation

For further reference, please consider the following sections:

* [Official Apache Maven documentation](https://maven.apache.org/guides/index.html)
* [OAuth2 Client](https://docs.spring.io/spring-boot/docs/2.5.1/reference/htmlsingle/#boot-features-security-oauth2-client)
* [Spring Security](https://docs.spring.io/spring-boot/docs/2.5.1/reference/htmlsingle/#boot-features-security)
* [Thymeleaf](https://docs.spring.io/spring-boot/docs/2.5.1/reference/htmlsingle/#boot-features-spring-mvc-template-engines)

### Guides

The following guides illustrate how to use some features concretely:

* [Securing a Web Application](https://spring.io/guides/gs/securing-web/)
* [Spring Boot and OAuth2](https://spring.io/guides/tutorials/spring-boot-oauth2/)

[application.yml]: src/main/resources/application.yml
[SC2 APIs]: https://develop.battle.net/documentation/starcraft-2/community-apis
[WebClient]: src/main/java/com/blizzard/javawebmvcoauthsample/WebClientConfig.java