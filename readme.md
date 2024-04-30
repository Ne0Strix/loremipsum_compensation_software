# LoremIpsum Compensation Software

> [!IMPORTANT]
> This is _neither_ a production ready software nor environment. This codebase is just a showcase and provides a minimal development environment with some caveats.

## How to start the software

`git clone` the repository and execute `docker compose up --build` in the project root. Then you can:

- visit [localhost](http://localhost:4200) in your browser to check out the UI

## Functionality

> [!TIP]
> This software comes with a pre-loaded database to see the frontend in action. Subsequent `docker compose up` commands will always reset the database to its original state.

Once it's up and running you can

- see a list of all books in the database and
- check out their details.
- create new books by entering at least their 'Title' and 'ISBN' [in the form](http://localhost:4200/create)
- have some of the books details fetched in the background from [OpenLibrary](https://openlibrary.org/) (`Publisher`, `Pages`, `Language`)
  - this update runs every 5s and checks 10 books at a time

## Checking the tests

> [!INFO]
> `npx cypress run` uses the root `docker-compose.yml` to spin up the test-environment before each test and destroys it afterwards. No data will persist across tests.

- run `npx cypress run` in `.frontend` to let the e2e and component tests run
  - in order to check the tests out with the gui make sure to spin up the environment manually with `docker compose up`
- run `npx jest --coverage` in `./frontend` to see some unit-tests and the coverage of the project (html report is available in `./frontend/coverage/lcov-report/index.html`)
- run `./gradlew test` in `./books` and check out the details in `./books/build/reports/tests/test/index.html`

## Known Limitations

> [!NOTE]
> There are some limitations to the software that are known but weren't resolved due to the developer's resource-restrictions.

- Visual form validation indicator for required fields
  - there were issues with the component-library used ([PrimeNG](https://primeng.org/installation)) that caused unwanted behaviour together with the FormControl `Validator`
- There is no button to reload the table, to see results from the cronjob in the background please reload manually
- Languages and Genres are hardcoded in the backend to ensure choices are only from the given set.
- The container (especially for the frontend) only serve a development-version of the code.
- Some of the frontend tests rely on display-text for validation which could cause issues when introducing nationalisation. This should be changed in future revisions.
- There is no indication whether `BookEntity`-attributes are updated in the frontend by the `OpenLibrarySync` (except for new values being there). Each book is updated once in its lifetime.

## Development Notes

- Whenever the `Genre`, `Language`, or `BookEntity` are altered the corresponding entries in the `schema.sql` have to be altered as well.
