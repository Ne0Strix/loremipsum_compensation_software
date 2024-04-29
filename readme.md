# LoremIpsum Compensation Software

> [!IMPORTANT]
> This is _neither_ a production ready software nor environment. This codebase is just a showcase and provides a minimal development environment with some caveats.

## How to start the software

`git clone` the repository and execute `docker compose up --build` in the project root. Then you can:

- visit [localhost](http://localhost:4200) in your browser to check out the UI

## Checking the tests

> [!WARNING]
> Some tests will fail on subsequent runs, please check the details below!

- run `npx cypress run` or `npx cypress start` in `./frontend` to check out the e2e and component tests in cypress
  - there is one e2e-testcase that tests book-creation; this test will fail in subsequent runs as the book from the tests then already exists in the database
- run `npx jest --coverage` in `./frontend` to see some unit-tests and the coverage of the project (html report is available in `./frontend/coverage/lcov-report/index.html`)
- run `./gradlew test` and check out the details in `./books/build/reports/tests/test/index.html`

## Functionality

> [!IMPORTANT]
> This software comes with a pre-loaded database to see the frontend in action. Subsequent `docker compose up` commands will always reset the database to its original state. Once it's up and running you can

- see a list of all books in the database and
- check out their details.
- create new books by entering their 'Title' and 'ISBN' [in the form](http://localhost:4200/create)
- have some of the books details fetched in the background from [OpenLibrary](https://openlibrary.org/) (`Publisher`, `Pages`, `Language`)
  - this update runs every 5s and checks 10 books at a time

## Known Limitations

> [!NOTE]
> There are some limitations to the software that are known but weren't resolved due to the developer's resource-restrictions.

- Visual form validation indicator for required fields
  - there were issues with the component-library used ([PrimeNG](https://primeng.org/installation)) that caused unwanted behaviour together with the FormControl `Validator`
- Languages and Genres are hardcoded in the backend to ensure choices are only from the given set.
  - A separate database-table could hold these values, but this is nothing that should be directly exposed to the end-user and rather be managed by an admin for the same reason these are now hardcoded.
- The container (especially for the frontend) only serve a development-version of the code. For production several adaptions would have to be made.
- Some of the frontend tests rely on display-text for validation which could cause issues when introducing nationalisation. These should be changed for appropriate tags in future revisions.

## Development Notes

- Whenever the `Genre`, `Language`, or `BookEntity` are altered the corresponding entries in the `schema.sql` have to be altered.
