# PBC - Pittsburgh Buddhist Center android app
This is a Android application build upon kotlin. This consumes REST-API to access data from Google Firestore.
PBC provides consumers to connect with Pittsburgh Buddhist Center.

### Technologies & Tools
* Kotlin -- v2.2.0
* Android Gradle plugin -- v8.12.0
* Gradle -- v8.14.3

### Build
Build the code by running following command.
<br />
````
$ ./gradlew clean --no-build-cache build -x lint
````

### New Development
In this application development, we are trying to follow trunk-base approach for branching. Therefore, if there is any new development or bug-fx or hot-fix available, our development branch will be `main` branch.

### Release Naming
Application is following a pattern to name the release versions & version-codes. Version name built with release year, major or feature drop version, minor or bug-fix. 
Version-code built with release year, release month and even incremental code [2, 4, 6, 8, 10]
````
Version name pattern : <release-year>.<major/feature-drop>.<minor/bug-fx> 
Example              : 2025.1.0

Version code pattern : <release-year><release-month><even-incremental-code>
Example              : 20250102
````

### Steps to follow in prod release
1. This project following truck-base model. Assume that all feature development branches merged to main branch when we are planing a release.
2. Create a release branch from `main` branch according to this naming template `release/<release-version-name>`. Eg: `release/2025.1.0`.
3. Update the application versionName and versionCode to new values in `gradle.properties` file.
4. If there are any bugfix branches created to following `release` branch, them merge them to `release` branch.
5. Create a pull request to `main` branch from release branch.
6. Merge pull request to `main` and fetch `main` branch change to local.
7. Run following command to build `.apk` file.
    ````
    $ ./gradlew clean --no-build-cache build -x lint
    $ ./gradlew clean --no-build-cache assembleRelease
    ````
8. If you want to create a `.aab` file, then use Android Studio -> Build -> Generate Signed Bundle / APK and then select Android App Bundle.
9. Create a `git tag` in bitbucket for PROD release as `v<version-name>`. Eg: `v2025.1.0`
10. Keep the tag description.

### New .aab to Google Play submission
1. Login into Google play console. 
2. From left side menu, select `Release -> Testing -> Internal Testing`.
3. Create a new release by click on `Create new release` button on top right corner.
4. Update load the new `.aab` file.
5. Verify the build details after uploading completed.
6. Update the Release Details by adding `Release name` & `Release notes`.
7. Click on `Review Release` button to continue.
8. To share on internal testers click on `Start rollout` button from review page.

### Rollout for Alpha or Beta testing
1. Login into Google play console.
2. From left side menu, select `Release -> Testing -> Internal Testing`.
3. In `Internal Testing` there should be a release already rolling-out for internal testing.
4. Click `Promote release` button in specific release.
5. Select the which level need to promote the build from following options

| Option          | Testing           | Description                                                                |
|-----------------|-------------------|----------------------------------------------------------------------------|
| `Close Testing` | **Alpha Testing** | Available only for internal testing group but build is verified by Google. |
| `Open Testing`  | **Beta Testing**  | Available for open Beta users build is verified by Google.                 |
| `Production`    | **Production**    | Available for general public.                                              |

