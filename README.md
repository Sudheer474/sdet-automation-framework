# SDET Automation Framework

![CI](https://github.com/Sudheer474/sdet-automation-framework/actions/workflows/ci.yml/badge.svg)

A layered **Java test automation framework** that combines UI and API testing in one suite, runs in parallel, reports through Allure, and executes on every push through GitHub Actions.

| Concern | Tooling |
|---|---|
| Language / build | Java 17, Maven |
| Test runner | TestNG (groups, data providers, parallel classes) |
| UI automation | Selenium WebDriver 4 + Page Object Model |
| API automation | REST Assured, Jackson (typed payloads), JSON Schema validation |
| Reporting | Allure (steps, request/response logs, failure screenshots) |
| Logging | Log4j2 |
| CI | GitHub Actions (headless Chrome) |

## Systems under test (public demo apps)
- UI: [Sauce Demo](https://www.saucedemo.com) - login, cart, checkout
- API: [Restful-Booker](https://restful-booker.herokuapp.com) - auth, create/read/update/delete bookings

## Architecture

```
src/main/java/.../framework
  config/      ConfigReader         config.properties + -Dkey=value overrides
  driver/      DriverFactory        ThreadLocal WebDriver (safe for parallel runs)
  pages/       BasePage + pages     Page Object Model with explicit waits
  api/         BookingClient        API layer; RequestSpecFactory = base URI/headers/filters
  models/      Booking (records)    typed request/response payloads
  utils/       TestDataFactory      unique data per test (parallel-safe)
  listeners/   TestListener         logging + screenshot on failure
               RetryAnalyzer        configurable retry, visible in the report

src/test/java/.../framework
  base/        BaseUiTest           browser setup/teardown per test
  tests/ui/    Login, Cart, Checkout E2E
  tests/api/   Booking CRUD, contract (schema), negative tests
```

### Design decisions (good interview talking points)
- **Layers don't leak.** Tests call page objects / API clients; they never touch locators or `given()` directly.
- **Thread-safe by design.** One `WebDriver` per thread via `ThreadLocal`; every API test creates its own data, so classes run in parallel with no shared state.
- **Explicit waits only**, no `Thread.sleep` and no implicit waits.
- **Contract testing.** The GET booking response is validated against a JSON Schema, so shape changes fail loudly.
- **Retries are visible, not hidden.** One retry is configured by default (`retryCount`); retried tests show up in Allure so flaky tests get fixed.
- **Config over code.** `mvn test -Dbrowser=firefox -Dheadless=false` changes behaviour without editing source.

## Running

Prerequisites: JDK 17+, Maven 3.9+, Chrome (or Firefox). Selenium Manager downloads the browser driver automatically.

```bash
mvn clean test                          # full suite, headless Chrome
mvn test -Dheadless=false               # watch the browser
mvn test -Dbrowser=firefox              # another browser
mvn test -Dgroups=smoke                 # only smoke tests
mvn test -Dgroups=api                   # only API tests
```

### View the Allure report
```bash
# one-time install: https://allurereport.org/docs/install/
allure serve target/allure-results
```

## CI
`.github/workflows/ci.yml` runs the suite on every push and pull request and uploads Allure results and Surefire reports as build artifacts.

## Test inventory
| Area | Tests |
|---|---|
| UI | valid login, 4 invalid-login cases (data-driven), cart badge + contents, full checkout E2E |
| API | create + payload echo, schema/contract check, update, delete then 404, delete without token (403), unknown id (404) |

## Possible next steps
Publish the Allure report to GitHub Pages, add Docker/Selenium Grid, add a Playwright module.
