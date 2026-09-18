# PROG5121 POE — Part 1: Registration and Login

A console-only Java Maven project implementing the registration/login feature
from the Part 1 task sheet.

## Structure

```
src/main/java/prog5121/Login.java   Core logic (checkUserName, checkPasswordComplexity,
                                     checkCellPhoneNumber, registerUser, loginUser,
                                     returnLoginStatus)
src/main/java/prog5121/Main.java    Console entry point (Scanner-based, no GUI)
src/test/java/prog5121/LoginTest.java  JUnit 5 tests using the exact test data
                                        from the task sheet
```

## Rules implemented

- **Username**: must contain `_` and be no more than 5 characters.
- **Password**: at least 8 characters, 1 capital letter, 1 number, 1 special character.
- **Cell number**: `+` + international country code + number (e.g. `+27838968976`).
- **Login**: checks the entered username/password against what was registered.

## Running locally (NetBeans / IntelliJ / VS Code)

1. Open the folder as a Maven project.
2. Run `Main.java` for the console app, or run the tests in `LoginTest.java`
   (right-click → Run Tests, or `mvn test`).

## Notes / assumptions

- The task sheet's Part 1 messaging table (page ~9) lists the *cell number*
  success/failure text slightly differently in two places ("Cell phone
  number successfully added." vs. "Cell number successfully captured.").
  This implementation uses the wording from the **unit-test table**
  ("Cell number successfully captured." / "Cell number is incorrectly
  formatted or does not contain an international code...") since that's
  what the `assertEquals` tests are marked against — double check this
  against your own copy of the brief before submitting.
- `registerUser()` only marks the user as registered once username,
  password **and** cell number all pass — this matches the login test
  data (`loginUser` only succeeds after a full valid registration).
- Still to do before submission: push to GitHub with 6+ commits, and
  record your unlisted YouTube voice-over walkthrough for Part 1.
