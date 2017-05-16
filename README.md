## Securing backend APIs when developing Client side web applications [![Build Status](https://travis-ci.org/hhimanshu/google-oauth2-jwt-secure-api.svg?branch=master)](https://travis-ci.org/hhimanshu/google-oauth2-jwt-secure-api)

### Blog link

### How to run
clone the repository
```bash
git clone git@github.com:hhimanshu/google-oauth2-jwt-secure-api.git

```

compile, test, create package
```bash
mvn clean package
```

start the server
```bash
mvn spring-boot:run
```

- Go to [http://localhost:8080](http://localhost:8080)

- Sign In with Google
- Open Developer Tools and after login you your see an object structure under path `current user > Ab > Zi `
- locate `id_token` and copy the value

Authenticate with your application APIs
```bash
curl -v -X POST -H "Content-Type: application/json" -H "X-ID-TOKEN: <place your id_token here>" -d'{}' http://localhost:8080/login
```

If the requests succeeds, you would see token coming from your app in `Authorization` header similar to following
```bash
Authorization: Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJteVVuaXF1ZVVzZXIiLCJleHAiOjE0OTUwMDA3NjV9.B4Ax_BIkrW044rwVnN-qvLcT9r0JzP4VCECjExp3yTFqv4STNmEiG4LNBHU-BXjAOSgt9xuLV7LhVXPKLYApbQ
```

You can now use this token to further communicate the server.

